package com.testtask.currencyconverter.services;

import com.testtask.currencyconverter.entities.converter.HistoryEntry;
import com.testtask.currencyconverter.entities.converter.Value;
import com.testtask.currencyconverter.entities.converter.Valute;
import com.testtask.currencyconverter.repositories.HistoryRepo;
import com.testtask.currencyconverter.repositories.ValueRepo;
import com.testtask.currencyconverter.repositories.ValuteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Service
public class ConvertionService {

    final CBRDataExchangeService dataExchangeService;
    final XMLParseService parser;
    final ValuteRepo valuteRepo;
    final ValueRepo valueRepo;
    final HistoryRepo historyRepo;

    public ConvertionService(CBRDataExchangeService dataExchangeService,
                             XMLParseService parser,
                             ValuteRepo valuteRepo,
                             ValueRepo valueRepo,
                             HistoryRepo historyRepo) {
        this.dataExchangeService = dataExchangeService;
        this.parser = parser;
        this.valuteRepo = valuteRepo;
        this.valueRepo = valueRepo;
        this.historyRepo = historyRepo;
    }

    // method downloads exchange rates from cbr.ru
    public void downloadValuteInfo(Date date) throws IOException, ParserConfigurationException, SAXException {
        Document document = parser.getXMLStructure(dataExchangeService.getValutesXML(date));
        List<Valute> valutes = parser.getListOfValutes(document, date);

        for (Valute valute : valutes) {
            valuteRepo.save(valute);
        }

        Valute rub = new Valute("0", 643, "RUB", 1, "Российский рубль");
        Value value = new Value(rub, 1.0, date);
        valuteRepo.save(rub);
    }

    public Value getValue(Date date, String valuteCharCode) throws ParserConfigurationException, SAXException, IOException {
        if (!valueRepo.existsByDate(date)) {
            downloadValuteInfo(date);
        }

        return valueRepo.findByDateAndValute_CharCode(date, valuteCharCode);
    }

    public void convertValute(
            Model model,
            double originalAmount,
            String originalValuteCharCode,
            String targetValuteCharCode,
            Date date) throws IOException, SAXException, ParserConfigurationException {
        Value originalValuteValue = getValue(date, originalValuteCharCode);
        Value targetValuteValue = getValue(date, targetValuteCharCode);
        Valute originalValute = originalValuteValue.getValute();
        Valute targetValute = targetValuteValue.getValute();

        // conversion original valute amount into roubles
        double originalAmountInRoubles =
                originalAmount * originalValuteValue.getValue() / originalValute.getNominal();

        // final conversion into target valute
        double result = originalAmountInRoubles * targetValute.getNominal() / targetValuteValue.getValue();

        //rounding to 4 decimal places
        result = 10000 * result;
        int preroundedResult = (int) Math.round(result);
        double roundedResult = (double) preroundedResult / 10000.0;

        HistoryEntry convertion = new HistoryEntry(
              originalValute,
              targetValute,
              originalAmount,
              roundedResult,
              date
        );
        historyRepo.save(convertion);

        model.addAttribute("result", roundedResult);
        model.addAttribute("originalAmount", originalAmount);
        model.addAttribute("originalValuteCharCode", originalValuteCharCode);
        model.addAttribute("targetValuteCharCode", targetValuteCharCode);
    }

    // method gets history of conversions and gets list of today valute rates to take valute char codes from it
    public void formConverterPage(Model model)
            throws ParserConfigurationException, SAXException, IOException {

        Iterable<HistoryEntry> history = historyRepo.findAllByOrderByIdDesc();
        model.addAttribute("history", history);

        Date today = new Date(System.currentTimeMillis());

        if (!valueRepo.existsByDate(today)) {
            downloadValuteInfo(today);
        }

        Iterable<Value> values = valueRepo.findAllByDate(today);
        model.addAttribute("values", values);
    }

    // method loads exchange rates for given date from database or from cbr.ru if there isn't needed indo in DB
    public void getRates(Model model, Date date) throws ParserConfigurationException, SAXException, IOException {
        date = date == null ? new Date(System.currentTimeMillis()) : date;

        if (!valueRepo.existsByDate(date)) {
            downloadValuteInfo(date);
        }

        Iterable<Value> rates = valueRepo.findAllByDate(date);

        model.addAttribute("rates", rates);
    }
}

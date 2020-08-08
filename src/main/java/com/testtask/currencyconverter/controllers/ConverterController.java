package com.testtask.currencyconverter.controllers;

import com.testtask.currencyconverter.services.ConvertionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Date;

@Controller
@RequestMapping("/")
public class ConverterController {

    final ConvertionService convertionService;

    public ConverterController(ConvertionService convertionService) {
        this.convertionService = convertionService;
    }

    @GetMapping
    public String getConverterPage(
            Model model,
            @RequestParam(required = false) String originalValuteSearch,
            @RequestParam(required = false) String targetValuteSearch,
            @RequestParam(required = false) Date dateSearch
    ) throws IOException, SAXException, ParserConfigurationException {
        convertionService.formConverterPage(model);

        return "converter";
    }

    @PostMapping
    public String convert(
            Model model,
            @RequestParam Double originalValue,
            @RequestParam(name = "originalValute") String originalValuteCharCode,
            @RequestParam(name = "targetValute") String targetValuteCharCode
    ) throws ParserConfigurationException, SAXException, IOException {
        convertionService.convertValute(
                model,
                originalValue,
                originalValuteCharCode,
                targetValuteCharCode,
                new Date(System.currentTimeMillis())
        );
        convertionService.formConverterPage(model);

        return "converter";
    }

    @GetMapping("/rates")
    public String getRates(
            Model model,
            @RequestParam(required = false) Date date
    ) throws IOException, SAXException, ParserConfigurationException {
        convertionService.getRates(model, date);

        return "rates";
    }
}

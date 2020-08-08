package com.testtask.currencyconverter.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CBRDataExchangeService {

    public InputStream getValutesXML(Date date) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + dateFormat.format(date));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        return connection.getInputStream();
    }
}

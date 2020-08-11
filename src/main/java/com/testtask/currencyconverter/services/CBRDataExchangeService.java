package com.testtask.currencyconverter.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CBRDataExchangeService {

    @Value("${cbr.url}")
    private String CBRUrl;

    public InputStream getValutesXML(Date date) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        URL url = new URL(CBRUrl + dateFormat.format(date));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        return connection.getInputStream();
    }
}

package com.testtask.currencyconverter.entities.converter;

import javax.persistence.*;
import java.sql.Date;

/**
 * HistoryEntry class represents one history entity in ORM containing info about operation:
 * valutes, original valute amount, result and date.
 */
@Entity
public class HistoryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "original_valute_id", referencedColumnName = "id")
    private Valute originalValute;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST
    })
    @JoinColumn(name = "target_valute_id", referencedColumnName = "id")
    private Valute targetValute;

    private Double originalAmount;
    private Double totalAmount;
    private Date date;
    private Double exchangeRate;

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Valute getOriginalValute() {
        return originalValute;
    }

    public void setOriginalValute(Valute originalValute) {
        this.originalValute = originalValute;
    }

    public Valute getTargetValute() {
        return targetValute;
    }

    public void setTargetValute(Valute targetValute) {
        this.targetValute = targetValute;
    }

    public Double getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HistoryEntry(
            Valute originalValute,
            Valute targetValute,
            Double originalAmount,
            Double totalAmount,
            Date date) {
        this.originalValute = originalValute;
        this.targetValute = targetValute;
        this.originalAmount = originalAmount;
        this.totalAmount = totalAmount;
        this.date = date;
        double exchangeRate = totalAmount / originalAmount;
        exchangeRate *= 10000.0;
        int preroundedExchangeRate = (int) Math.round(exchangeRate);
        this.exchangeRate = preroundedExchangeRate / 10000.0;
    }

    public HistoryEntry() {
    }
}

package com.testtask.currencyconverter.entities.converter;

import javax.persistence.*;
import java.util.List;

/**
 * Valute class contains main info about valute: id, numeric code, char code, nominal, name of valute
 * and list of it exchange rates (field 'values', one value for each date, one to many relationship)
 */
@Entity
public class Valute {

    @Id
    private String id;

    private Integer numCode;
    private String charCode;
    private Integer nominal;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "valute", fetch = FetchType.LAZY)
    private List<Value> values;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "originalValute", fetch = FetchType.LAZY)
    private List<HistoryEntry> entriesContainingValuteAsOriginal;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "targetValute", fetch = FetchType.LAZY)
    private List<HistoryEntry> entriesContainingValuteAsTarget;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumCode() {
        return numCode;
    }

    public void setNumCode(Integer numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public List<HistoryEntry> getEntriesContainingValuteAsOriginal() {
        return entriesContainingValuteAsOriginal;
    }

    public void setEntriesContainingValuteAsOriginal(List<HistoryEntry> entriesContainingValuteAsOriginal) {
        this.entriesContainingValuteAsOriginal = entriesContainingValuteAsOriginal;
    }

    public List<HistoryEntry> getEntriesContainingValuteAsTarget() {
        return entriesContainingValuteAsTarget;
    }

    public void setEntriesContainingValuteAsTarget(List<HistoryEntry> entriesContainingValuteAsTarget) {
        this.entriesContainingValuteAsTarget = entriesContainingValuteAsTarget;
    }

    public Valute(
            String id,
            Integer numCode,
            String charCode,
            Integer nominal,
            String name) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
    }

    public Valute() {
    }
}

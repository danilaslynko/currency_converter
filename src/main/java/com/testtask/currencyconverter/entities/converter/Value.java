package com.testtask.currencyconverter.entities.converter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Value class represents the exchange rate against the ruble at a specific date.
 */
@Entity
public class Value {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST
    },
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "valute_id", referencedColumnName = "id")
    private Valute valute;

    private Double value;

    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Valute getValute() {
        return valute;
    }

    public void setValute(Valute valute) {
        this.valute = valute;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Value(
            Valute valute,
            Double value,
            Date date
    ) {
        this.valute = valute;
        this.value = value;
        this.date = date;

        if (valute.getValues() == null) {
            valute.setValues(new ArrayList<>());
        }

        valute.getValues().add(this);
    }

    public Value() {
    }
}

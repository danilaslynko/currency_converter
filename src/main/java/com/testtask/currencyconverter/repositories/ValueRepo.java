package com.testtask.currencyconverter.repositories;

import com.testtask.currencyconverter.entities.converter.Value;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface ValueRepo extends CrudRepository<Value, Long> {

    boolean existsByDate(Date date);

    Value findByDateAndValute_CharCode(Date date, String charCode);

    Iterable<Value> findAllByDate(Date date);
}

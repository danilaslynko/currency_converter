package com.testtask.currencyconverter.repositories;

import com.testtask.currencyconverter.entities.converter.Valute;
import org.springframework.data.repository.CrudRepository;

public interface ValuteRepo extends CrudRepository<Valute, String> {
}

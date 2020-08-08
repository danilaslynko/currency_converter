package com.testtask.currencyconverter.repositories;

import com.testtask.currencyconverter.entities.converter.HistoryEntry;
import org.springframework.data.repository.CrudRepository;

public interface HistoryRepo extends CrudRepository<HistoryEntry, Long> {

    Iterable<HistoryEntry> findAllByOrderByIdDesc();
}

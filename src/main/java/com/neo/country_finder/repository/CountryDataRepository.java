package com.neo.country_finder.repository;

import com.neo.country_finder.model.db.CountryData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryDataRepository extends CrudRepository<CountryData, String> {
}

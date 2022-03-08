package jkesle.crm.parser.country;

import jkesle.crm.dto.CountryDTO;

public interface CountryResponseParser<T> {

    public CountryDTO getCountryDTO(T responseType);

    public T getCountriesFromResponse(String response);

}

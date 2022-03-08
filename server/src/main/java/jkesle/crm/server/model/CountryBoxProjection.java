package jkesle.crm.server.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "combobox", types = {Country.class})
public interface CountryBoxProjection {

    int getCountryId();

    String getCountryName();
}

package jkesle.crm.server.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "combobox", types = {FirstLevelDivision.class})
public interface FirstLevelDivisionBoxProjection {

    @Value("#{target.getFirstLevelDivisionName()}")
    String getDivisionName();

    @Value("#{target.getFirstLevelDivisionId()}")
    int getDivisionId();

    @Value("#{target.getCountry().getCountryId()}")
    int getCountryId();

}

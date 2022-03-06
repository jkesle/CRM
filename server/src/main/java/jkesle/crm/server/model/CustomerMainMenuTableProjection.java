package jkesle.crm.server.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "tableview" ,types = {Customer.class})
public interface CustomerMainMenuTableProjection {

    public int getCustomerId();

    public String getCustomerName();

    public String getCustomerPhone();

    public String getCustomerAddress();

    @Value("#{target.getDivision().getFirstLevelDivisionName()}")
    public String getDivisionName();
}

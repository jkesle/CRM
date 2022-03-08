package jkesle.crm.parser.customer;

import jkesle.crm.dto.CustomerDTO;

public interface CustomerResponseParser<T> {

    public CustomerDTO getCustomerDTO(T responseType);

    public T getCustomersFromResponse(String response);

}

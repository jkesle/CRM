package jkesle.crm.parser.division;

import jkesle.crm.dto.DivisionDTO;

public interface DivisionResponseParser<T> {

        public DivisionDTO getDivisionDTO(T responseType);

        public T getDivisionsFromResponse(String response);

}

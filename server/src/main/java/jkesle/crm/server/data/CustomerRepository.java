package jkesle.crm.server.data;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import jkesle.crm.server.model.Customer;
import jkesle.crm.server.model.FirstLevelDivision;

@NoRepositoryBean
interface CustomerRepository extends CrudRepository<Customer, Integer> {

}

package jkesle.crm.server.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import jkesle.crm.server.model.Customer;

@RepositoryRestResource(path = "customers")
public interface CustomerJpaRepository extends JpaRepository<Customer, Integer>, CustomerRepository {

}

package jkesle.crm.server.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import jkesle.crm.server.model.Country;

@RepositoryRestResource(path = "countries")
interface CountryJpaRepository extends JpaRepository<Country, Integer> {

}

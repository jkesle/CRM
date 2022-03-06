package jkesle.crm.server.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import jkesle.crm.server.model.FirstLevelDivision;

@RepositoryRestResource(path = "divisions")
interface FirstLevelDivisionJpaRepository extends JpaRepository<FirstLevelDivision, Integer> {

}

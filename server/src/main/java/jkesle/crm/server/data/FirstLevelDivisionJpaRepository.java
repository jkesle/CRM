package jkesle.crm.server.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import jkesle.crm.server.model.FirstLevelDivision;

@RepositoryRestResource(path = "divisions")
interface FirstLevelDivisionJpaRepository extends JpaRepository<FirstLevelDivision, Integer> {

    @Query(value = "select * from first_level_division d where d.country_id = :countryId", nativeQuery = true)
    @RestResource(path = "country", rel = "country")
    public List<FirstLevelDivision> findByCountryId(@Param("countryId") int countryId);

}

package jkesle.crm.server.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import jkesle.crm.server.model.Appointment;

@RepositoryRestResource(path = "appointments")
interface AppointmentJpaRepository extends JpaRepository<Appointment, Integer> {

    @Query(value = "select * from appointment a where a.customer_id = :customerId",
    nativeQuery = true)
    @RestResource(path = "custid", rel = "custid")
    public List<Appointment> findByCustomerId(@Param("customerId") int customerId);

}

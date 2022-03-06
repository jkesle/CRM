package jkesle.crm.server.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import jkesle.crm.server.model.Appointment;

@RepositoryRestResource(path = "appointments")
interface AppointmentJpaRepository extends JpaRepository<Appointment, Integer> {

}

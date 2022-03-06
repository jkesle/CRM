package jkesle.crm.server.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import jkesle.crm.server.model.Contact;

@RepositoryRestResource(path = "contacts")
interface JpaContactRepository extends JpaRepository<Contact, Integer>, ContactRepository {

}

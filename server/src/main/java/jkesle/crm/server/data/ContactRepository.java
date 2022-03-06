package jkesle.crm.server.data;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import jkesle.crm.server.model.Contact;

@NoRepositoryBean
interface ContactRepository extends CrudRepository<Contact, Integer> {
}

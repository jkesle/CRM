package jkesle.crm.server.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import jkesle.crm.server.model.User;

@RepositoryRestResource
public interface UserJpaRepository extends JpaRepository<User, Integer>, UserRepository {

}

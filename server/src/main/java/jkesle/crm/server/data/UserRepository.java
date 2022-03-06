package jkesle.crm.server.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import jkesle.crm.server.model.User;

@NoRepositoryBean
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}

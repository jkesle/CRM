package jkesle.crm.server.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import jkesle.crm.server.model.User;

public interface AuthService {

    public abstract Result<User> authenticate(String username, String password);

}

package jkesle.crm.server.service;

import java.util.NoSuchElementException;

import org.apache.catalina.startup.PasswdUserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jkesle.crm.server.data.UserRepository;
import jkesle.crm.server.model.User;
import jkesle.crm.server.data.UserJpaRepository;

@Service
public class BCryptJpaAuthService implements AuthService {

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Result<User> authenticate(String username, String password)  {
        Result<User> result = new Result<>();
        try {
            User user = userRepository.findByUsername(username).get();
            if (encoder.matches(password, user.getPassword())) {
                result.setPayload(user);
                return result;
            } else throw new UsernameNotFoundException("Invalid username and password");

        } catch (NoSuchElementException | UsernameNotFoundException e) {
            result.addError("Invalid username or password");
        }

        return result;
    }

}

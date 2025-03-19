package com.electrik.electrik_ecomm.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.electrik.electrik_ecomm.entities.User;
import com.electrik.electrik_ecomm.exceptions.MyException;
import com.electrik.electrik_ecomm.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    private void CreateUser(String email, String name, String LastName, String password, String password2)
            throws MyException {

        ValidateUser(email, name, LastName, password, password2);

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setLastName(LastName);
        newUser.setPassword(new BCryptPasswordEncoder().encode(password));

        userRepository.save(newUser); // Save the new user to the repository
    }

    public void ValidateUser(String email, String name, String LastName, String password, String password2)
            throws MyException {
        if (name == null || name.trim().isEmpty()) {
            throw new MyException("The name cannot be empty or null");
        }
        if (LastName == null || LastName.trim().isEmpty()) {
            throw new MyException("The last name cannot be empty or null");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new MyException("The password cannot be empty or null");
        }
        if (password2 == null || password2.trim().isEmpty()) {
            throw new MyException("The password confirmation cannot be empty or null");
        }
        if (!password.equals(password2)) {
            throw new MyException("The passwords do not match");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new MyException("the email cannot be empty or null");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new MyException("The e-mail is already registered");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email)); // Handle user
                                                                                                          // not found

        List<GrantedAuthority> permissions = new ArrayList<>();
        GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());
        permissions.add(p);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("usuariosession", user);

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), permissions);
    }
}

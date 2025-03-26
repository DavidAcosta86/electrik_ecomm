package com.electrik.electrik_ecomm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;

import com.electrik.electrik_ecomm.entities.Image;
import com.electrik.electrik_ecomm.entities.User;
import com.electrik.electrik_ecomm.enums.Roles;
import com.electrik.electrik_ecomm.exceptions.MyException;
import com.electrik.electrik_ecomm.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Transactional
    public void CreateUser(String email, String name, String LastName, MultipartFile file, String password,
            String password2)
            throws MyException {
        ValidateUser(email, name, LastName, password, password2);

        System.out.println("paso la validacion");
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setLastName(LastName);
        Image image = imageService.SaveImage(file);
        newUser.setPicture(image);
        newUser.setPassword(new BCryptPasswordEncoder().encode(password));
        newUser.setRole(Roles.USER);

        userRepository.save(newUser); // Save the new user to the repository
        System.out.println("se creo el user?");
    }

    @Transactional
    public void DeleteUser(String email) throws MyException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new MyException("User not found with email: " + email));
        userRepository.delete(user);
    }

    @Transactional
    public void ModifyUser(String email, String name, String lastName, String password, String password2)
            throws MyException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new MyException("User not found with email: " + email));

        if (name != null && !name.trim().isEmpty()) {
            user.setName(name);
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            user.setLastName(lastName);
        }
        if (password != null && !password.trim().isEmpty()) {
            if (password2 == null || password2.trim().isEmpty()) {
                throw new MyException("The password confirmation cannot be empty or null");
            }
            if (!password.equals(password2)) {
                throw new MyException("The passwords do not match");
            }
            user.setPassword(new BCryptPasswordEncoder().encode(password));
        }

        userRepository.save(user);
    }

    @Transactional
    public List<User> ListUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public List<User> ListAllUsers() {
        return userRepository.findAll();
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

    @Transactional
    public User getOne(String id) throws MyException {
        try {
            return userRepository.findById(UUID.fromString(id)).orElse(null);
        } catch (IllegalArgumentException e) {
            throw new MyException("Invalid UUID format");
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

package com.charan.cinebook.service;

import com.charan.cinebook.models.Role;
import com.charan.cinebook.models.User;
import com.charan.cinebook.repository.UserRepository;
import com.charan.cinebook.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User register(String name,String mail,String password) {
        User user = new User();
        user.setEmail(mail);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.CUSTOMER);
        return userRepository.save(user);
    }

    public String login(String mail,String password) {
        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new RuntimeException("This user Not Exists"));

        if(!passwordEncoder.matches(password,user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        return jwtUtil.generateToken(user.getEmail(),user.getRole().name());
    }
}

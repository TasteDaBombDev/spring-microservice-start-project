package com.postme.coreAuthorisation.services;

import com.postme.coreAuthorisation.models.Role;
import com.postme.coreAuthorisation.models.User;
import com.postme.coreAuthorisation.models.enums.UserRolesEnum;
import com.postme.coreAuthorisation.models.requests.LoginRequest;
import com.postme.coreAuthorisation.models.requests.RegisterRequest;
import com.postme.coreAuthorisation.models.responses.Response;
import com.postme.coreAuthorisation.models.responses.resources.UserResource;
import com.postme.coreAuthorisation.repositories.RoleRepository;
import com.postme.coreAuthorisation.repositories.UserRepository;
import com.postme.coreAuthorisation.security.jwt.JwtUtils;
import com.postme.coreAuthorisation.security.services.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CoreAuthorisationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserResource(true, userDetails, "User authentificated"));
    }

    public ResponseEntity<?> register(RegisterRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new Response(false, null, "Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new Response(false, null, "Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(UserRolesEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new Response(true, null, "User registered successfully!"));
    }

    public ResponseEntity<?> logout() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new Response(true, null, "You've been signed out!"));
    }

    public ResponseEntity<?> getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return ResponseEntity.ok(new UserResource(true, userDetails, "User found"));
        } else {
            return ResponseEntity.ok(new UserResource(false, null, "User not authenticated"));
        }
    }
}

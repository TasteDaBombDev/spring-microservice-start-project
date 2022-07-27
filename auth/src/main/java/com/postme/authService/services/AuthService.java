package com.postme.authService.services;

import com.postme.authService.models.Role;
import com.postme.authService.models.User;
import com.postme.authService.models.enums.UserRolesEnum;
import com.postme.authService.models.requests.LoginRequest;
import com.postme.authService.models.requests.RegisterRequest;
import com.postme.authService.models.responses.Response;
import com.postme.authService.models.responses.resources.UserResource;
import com.postme.authService.repositories.RoleRepository;
import com.postme.authService.repositories.UserRepository;
import com.postme.authService.security.jwt.JwtUtils;
import com.postme.authService.security.services.UserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

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

        String role = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (role == null) {
            Role userRole = roleRepository.findByName(UserRolesEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            if ("admin".equals(role)) {
                Role adminRole = roleRepository.findByName(UserRolesEnum.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
            } else {
                Role userRole = roleRepository.findByName(UserRolesEnum.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
        }

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

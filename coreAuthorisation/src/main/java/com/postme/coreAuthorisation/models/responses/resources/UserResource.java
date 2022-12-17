package com.postme.coreAuthorisation.models.responses.resources;

import com.postme.coreAuthorisation.models.responses.Response;
import com.postme.coreAuthorisation.security.services.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UserResource extends Response {

    public UserResource(boolean success, UserDetails userDetails, String message) {
        super(success, null, message);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        HashMap<String, String> response = new HashMap<>();

        response.put("username", userDetails.getUsername());
        response.put("email", userDetails.getEmail());
        response.put("role", roles.get(0));

        super.setData(response);
    }
}

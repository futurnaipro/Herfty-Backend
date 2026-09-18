package artisanat.artisanat.model.DTOs;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import artisanat.artisanat.model.Entities.Role;

public class JwtResponseDTO {

    private final String token;
    private final Role role;
    public String username;

    public JwtResponseDTO(String token, Role role,String username) {
        this.token = token;
        this.role = role;
        this.username=username;
    }

    public String getUsername(){
        return username;
    }


    public String getToken() {
        return token;
    }

    public Role getRole() {
        return role;
    }
}


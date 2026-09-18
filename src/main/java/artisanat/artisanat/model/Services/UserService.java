package artisanat.artisanat.model.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import artisanat.artisanat.model.DTOs.JwtRequestDTO;
import artisanat.artisanat.model.DTOs.JwtResponseDTO;
import artisanat.artisanat.model.Entities.AppUser;
import artisanat.artisanat.model.Repositories.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    AppUserRepository userRepository;
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenService jwtTokenService;


    public AppUser loadUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public AppUser loadUserById(Long id){
        return userRepository.findAppUserById(id);
    }

    public JwtResponseDTO authenticte(JwtRequestDTO request){
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
       AppUser user = userRepository.findByUsername(request.getUsername());
       String token = jwtTokenService.generateToken(user);
       return new JwtResponseDTO(token,user.getRole(),user.getUsername());
    }

}

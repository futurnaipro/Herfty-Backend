package artisanat.artisanat.model.Services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import artisanat.artisanat.model.Entities.Admin;
import artisanat.artisanat.model.Entities.Artisan;
import artisanat.artisanat.model.Entities.Client;
import artisanat.artisanat.model.Entities.AppUser;
import artisanat.artisanat.model.Repositories.AppUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    AppUserRepository userRepository;

    public UserDetailsServiceImp(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       AppUser user = userRepository.findByUsername(username);
    if (user == null) {
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
    
    return new org.springframework.security.core.userdetails.User(
            user.getUsername(), 
            user.getPassword(), user.getAuthorities());
    }




   

}

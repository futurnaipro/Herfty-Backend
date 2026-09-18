package artisanat.artisanat.model.Services;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import artisanat.artisanat.model.DTOs.JwtRequestDTO;
import artisanat.artisanat.model.DTOs.JwtResponseDTO;
import artisanat.artisanat.model.Entities.AppUser;
import artisanat.artisanat.model.Entities.Artisan;
import artisanat.artisanat.model.Entities.Artisan.Badges;
import artisanat.artisanat.model.Entities.Artisan.Statut;
import artisanat.artisanat.model.Repositories.ArtisanRepository;
import jakarta.transaction.Transactional;

@Service
public class ArtisanService {

    @Autowired
    ArtisanRepository artisanRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    public JwtResponseDTO  addArtisan(Artisan artisan){
        artisan.setBadges(Badges.ArtisanDébutant);
        artisan.setPoints(0);
        artisan.setStatut(Statut.EnAttente);
        artisanRepository.save(artisan);
        String token=jwtTokenService.generateToken(artisan);
        return new JwtResponseDTO(token,artisan.getRole(),artisan.getUsername());
    }
    
    public Artisan loadArtisanById(Long id){
        return artisanRepository.findArtisanById(id);
    }

    public List<Artisan> getArtisansByStatut(Statut statut){
        return artisanRepository.findAllByStatut(statut);
    }

    public List<Artisan> getAllArtisans(){
        return artisanRepository.findAll();
    }

    public Artisan loadArtisanByUsername(String username){
        return artisanRepository.findArtisanByUsername(username);
         
    }

    public void deleteArtisanByUsername(String username){
        artisanRepository.deleteByUsername(username);
    }

    public void deleteArtisanById(Long id){
        artisanRepository.deleteById(id);
    }

    public Artisan updateArtisan(Artisan artisan,String username){
        Artisan oldArtisan=artisanRepository.findArtisanByUsername(username);
        artisan.setId(oldArtisan.getId());
        BeanUtils.copyProperties(artisan, oldArtisan, "id");
        oldArtisan.setPassword(passwordEncoder.encode(oldArtisan.getPassword()));
      return  artisanRepository.save(oldArtisan);
    }
    


}

package artisanat.artisanat.model.Services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import artisanat.artisanat.model.DTOs.JwtResponseDTO;
import artisanat.artisanat.model.Entities.Artisan;

import artisanat.artisanat.model.Entities.Client;
import artisanat.artisanat.model.Entities.Client.Badges;
import artisanat.artisanat.model.Repositories.ClientRepository;
import jakarta.transaction.Transactional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    PasswordEncoder passwordEncoder;


        public JwtResponseDTO addClient(Client client){
        client.setBadges(Badges.Nouveau);
        client.setPoints(0);
        clientRepository.save(client);
        String token=jwtTokenService.generateToken(client);
        return new JwtResponseDTO(token,client.getRole(),client.getUsername());
    }
    
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public Client loadClientByUsername(String username){
        return clientRepository.findClientByUsername(username);
         
    }

    public void deleteClientByUsername(String username){
        clientRepository.deleteByUsername(username);
    }

    public void deleteClientById(Long id){
        clientRepository.deleteById(id);
    }
    public Client updateClient(Client client,String username){
        Client oldClient=clientRepository.findClientByUsername(username);
        client.setId(oldClient.getId());
        BeanUtils.copyProperties(client, oldClient, "id");
        oldClient.setPassword(passwordEncoder.encode(oldClient.getPassword()));
      return  clientRepository.save(oldClient);
    }
}

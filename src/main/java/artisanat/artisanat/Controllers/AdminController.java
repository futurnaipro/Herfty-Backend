package artisanat.artisanat.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import artisanat.artisanat.model.DTOs.MessageDTO;
import artisanat.artisanat.model.DTOs.MessageSignaledDTO;
import artisanat.artisanat.model.Entities.AppUser;
import artisanat.artisanat.model.Entities.Artisan;
import artisanat.artisanat.model.Entities.Client;
import artisanat.artisanat.model.Entities.Commande;
import artisanat.artisanat.model.Entities.Competition;
import artisanat.artisanat.model.Entities.Message;
import artisanat.artisanat.model.Entities.Artisan.Statut;
import artisanat.artisanat.model.Repositories.AppUserRepository;
import artisanat.artisanat.model.Repositories.CommandeRepository;
import artisanat.artisanat.model.Repositories.CompetitionRepository;
import artisanat.artisanat.model.Services.ArtisanService;
import artisanat.artisanat.model.Services.ClientService;
import artisanat.artisanat.model.Services.MessageService;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ArtisanService artisanService;

    @Autowired
    ClientService clientService;

    @Autowired
    MessageService messageService;

    @Autowired
    CompetitionRepository competitionRepository;

    @Autowired
    CommandeRepository commandeRepository;

    @Autowired
    AppUserRepository appUserRepository;
    
    @GetMapping("/artisans")
    public ResponseEntity<List<Artisan>> listOfArtisan() {
        List<Artisan> artisans = artisanService.getAllArtisans();
        return ResponseEntity.ok(artisans);
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> listOfUsers(){
        List<AppUser> users = appUserRepository.findAll();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/users/count")
    public ResponseEntity<Long> getUserCount() {
       long userCount = appUserRepository.count();
       return ResponseEntity.ok(userCount);
    }

    @GetMapping("/commandes/count")
    public ResponseEntity<Long> getCommandeCount() {
        long commandeCount = commandeRepository.count();
        return ResponseEntity.ok(commandeCount);
    }
    @GetMapping("/revenue")
    public ResponseEntity<Double> getTotalRevenue() {
      List<Commande> commandes = commandeRepository.findAll();
      double totalRevenue = commandes.stream().mapToDouble(Commande::getPrixTotal).sum();
      return ResponseEntity.ok(totalRevenue);
    } 
    @GetMapping("/artisansEnAttente")
    public ResponseEntity<List<Artisan>> listOfArtisanEnAttente(){
        List<Artisan> artisans =artisanService.getArtisansByStatut(Statut.EnAttente);
        return ResponseEntity.ok(artisans);
    }

    @GetMapping("/showCarte/{id}")
    public ResponseEntity<String> showCarte(@PathVariable Long id){
        Artisan artisan= artisanService.loadArtisanById(id);
        return ResponseEntity.ok(artisan.getCarte());
    }

    @PutMapping("/validate/{id}")
    public ResponseEntity<Void> validate(@PathVariable Long id){
        Artisan artisan=artisanService.loadArtisanById(id);
        artisan.setStatut(Statut.Validé);
        artisanService.updateArtisan(artisan, artisan.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/signaledMessages")
    public ResponseEntity<List<MessageSignaledDTO>> signaledMessages(){
        List<Message> messages= messageService.getMessagesBySignaled();
        List<MessageSignaledDTO> messageDTOs = messages.stream()
                .map(message -> {
                    MessageSignaledDTO messageDTO = new MessageSignaledDTO();
                    messageDTO.setId(message.getId());
                    messageDTO.setSenderId(message.getSender().getId());
                    messageDTO.setReceiverId(message.getReceiver().getId());
                    messageDTO.setContent(message.getContent());
                    messageDTO.setCreatedAt(message.getCreatedAt());
                    messageDTO.setSignalCause(message.getSignalCause());
                    return messageDTO;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(messageDTOs);
    }
    
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> listOfClients() {
        List<Client> clients= clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @DeleteMapping("/deleteA/{id}")
    public void deleteArtisanCompte(@PathVariable Long id){
        artisanService.deleteArtisanById(id);
    }

    @DeleteMapping("/deleteC/{id}")
    public void deleteClientCompte(@PathVariable Long id){
        clientService.deleteClientById(id);
    }

    @PostMapping("/ajouterComp")
    public ResponseEntity<Void> ajouterCompetition(@RequestBody Competition competition) {
        if (competition.getNom() == null || competition.getNom().isEmpty() ||
            competition.getTheme() == null || competition.getTheme().isEmpty() ||
            competition.getDescription() == null || competition.getDescription().isEmpty() ||
            competition.getDateDebut() == null || competition.getDateFin() == null ||
            competition.getNombreParticipants() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        competitionRepository.save(competition);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/supprimerComp/{id}")
    public ResponseEntity<Void> supprimerCompetition(@PathVariable Long id) {
        Optional<Competition> competitionOptional = competitionRepository.findById(id);
        if (competitionOptional.isPresent()) {
          Competition competition = competitionOptional.get();
          competitionRepository.delete(competition);
          return ResponseEntity.ok().build();
        } else {
          return ResponseEntity.notFound().build();
        }
    }

    
}

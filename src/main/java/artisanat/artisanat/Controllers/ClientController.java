package artisanat.artisanat.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
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

import artisanat.artisanat.model.DTOs.CommandeDTO;
import artisanat.artisanat.model.DTOs.JwtResponseDTO;
import artisanat.artisanat.model.DTOs.PanierDTO;
import artisanat.artisanat.model.Entities.Article;
import artisanat.artisanat.model.Entities.Article.Categorie;
import artisanat.artisanat.model.Repositories.AppUserRepository;
import artisanat.artisanat.model.Repositories.ArticleRepository;
import artisanat.artisanat.model.Repositories.ClientRepository;
import artisanat.artisanat.model.Repositories.CompetitionRepository;
import artisanat.artisanat.model.Repositories.ParticipationCompRepository;
import artisanat.artisanat.model.Entities.Artisan;
import artisanat.artisanat.model.Entities.Client;
import artisanat.artisanat.model.Entities.Commande;
import artisanat.artisanat.model.Entities.Commande.Statut;
import artisanat.artisanat.model.Entities.Panier;
import artisanat.artisanat.model.Entities.PanierArticles;
import artisanat.artisanat.model.Entities.PanierArticlesPK;
import artisanat.artisanat.model.Entities.Role;
import artisanat.artisanat.model.Services.ArticleService;
import artisanat.artisanat.model.Services.ArtisanService;
import artisanat.artisanat.model.Services.ClientService;
import artisanat.artisanat.model.Services.CommandeService;
import artisanat.artisanat.model.Services.IdGeneratorService;
import artisanat.artisanat.model.Services.PanierService;
import artisanat.artisanat.model.Services.UserService;

@RestController
@CrossOrigin("http://localhost:3000")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    UserService userService;

    @Autowired
    IdGeneratorService idGeneratorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ArticleService articleService;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    CommandeService commandeService;

    @Autowired
    PanierService panierService;

    @Autowired
    CompetitionRepository competitionRepository;

    @Autowired
    ParticipationCompRepository participationCompRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArtisanService artisanService;
    



@PostMapping("/add/Client")
public ResponseEntity<?> addClient(@RequestBody Client client) {
    if (appUserRepository.existsByEmail(client.getEmail()) || appUserRepository.existsByUsername(client.getUsername())) {
        return ResponseEntity.badRequest().body("Email or username already exists");
    }

    client.setId(idGeneratorService.generateId());
    client.setRole(Role.CLIENT);
    client.setPassword(passwordEncoder.encode(client.getPassword()));
    Panier panier=new Panier(client.getId(),0,client ,new ArrayList<>());
    client.setPanier(panier);
    JwtResponseDTO response = clientService.addClient(client);
    return ResponseEntity.ok(response);
    }

    // @GetMapping("/articles/categorie")
    // public List<Article> listOfArticlesByCategorie(@RequestParam String categorie){
    //     return articleService.getAllByCategorie(Categorie.valueOf(categorie));
    // }

    // @GetMapping("/articles")
    // public List<Article> listOfAllArticles(){
    //     return articleRepository.findAll();
    // }

    @GetMapping("/articles")
public List<Article> getArticles(
    @RequestParam(required = false) String q,
    @RequestParam(required = false) String artisan,
    @RequestParam(required = false) String categorie
) {
    if (q != null) {
        return articleRepository.findAllByNomContainingIgnoreCase(q);
    } else if (artisan != null) {
        Artisan artisan1 = artisanService.loadArtisanByUsername(artisan);
        return articleRepository.findAllByArtisanId(artisan1.getId());
    } else if (categorie != null) {
        return articleRepository.findAllByCategorie(Categorie.valueOf(categorie));
    } else {
        return articleRepository.findAll();
    }
}



    @GetMapping("/client/{username}")
    public Client getByUsernameClient(@PathVariable String username){
        return clientService.loadClientByUsername(username);
    }

    @GetMapping("/client/delete")
    public void deleteClientCompte(@RequestParam String username){
        clientService.deleteClientByUsername(username);
    }

    @PostMapping("/client/voter/{competitionId}")
    public ResponseEntity<Void> voter(@PathVariable Long competitionId){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id){
        Article article= articleService.getArticleById(id);
        return ResponseEntity.ok(article);
    }

    @PutMapping("/client/updateClient")
    public Client updateProfil(@RequestBody Client client,@RequestParam String username){
        return clientService.updateClient(client,username);
    }

}
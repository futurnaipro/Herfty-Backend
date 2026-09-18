package artisanat.artisanat.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import artisanat.artisanat.model.DTOs.CompetitionParticipationDTO;
import artisanat.artisanat.model.DTOs.JwtResponseDTO;
import artisanat.artisanat.model.Entities.Article;
import artisanat.artisanat.model.Entities.Artisan;
import artisanat.artisanat.model.Entities.Role;
import artisanat.artisanat.model.Repositories.AppUserRepository;
import artisanat.artisanat.model.Repositories.ChildCommandeRepository;
import artisanat.artisanat.model.Repositories.CompetitionRepository;
import artisanat.artisanat.model.Repositories.ParticipationCompRepository;
import artisanat.artisanat.model.Entities.Article.Taille;
import artisanat.artisanat.model.Entities.Artisan.TypeArtisanat;
import artisanat.artisanat.model.Entities.ChildCommande;
import artisanat.artisanat.model.Entities.Commande;
import artisanat.artisanat.model.Entities.CommandeArticles;
import artisanat.artisanat.model.Entities.Competition;
import artisanat.artisanat.model.Entities.ParticipationComp;
import artisanat.artisanat.model.Services.ArticleService;
import artisanat.artisanat.model.Services.ArtisanService;
import artisanat.artisanat.model.Services.CommandeService;
import artisanat.artisanat.model.Services.IdGeneratorService;
import artisanat.artisanat.model.Services.UserDetailsServiceImp;
import artisanat.artisanat.model.Services.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@RestController
@CrossOrigin("http://localhost:3000")
public class ArtisanController {

    
    @Autowired
    ArtisanService artisanService;

    @Autowired
    UserService userService;

    @Autowired
    IdGeneratorService idGeneratorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ArticleService articleService;
    
    @Autowired
    UserDetailsServiceImp userDetailsService;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    CommandeService commandeService;

    @Autowired
    CompetitionRepository competitionRepository;

    @Autowired
    ParticipationCompRepository participationCompRepository;

    @Autowired
    private ChildCommandeRepository childCommandeRepository;
    
    @PostMapping("/add/Artisan")
    public ResponseEntity<?> addArtisan(@RequestBody Artisan artisan) {
        if (appUserRepository.existsByEmail(artisan.getEmail()) || appUserRepository.existsByUsername(artisan.getUsername())) {
            return ResponseEntity.badRequest().body("Email or username already exists");
        }    
        artisan.setId(idGeneratorService.generateId());
        artisan.setRole(Role.ARTISAN);
        artisan.setPassword(passwordEncoder.encode(artisan.getPassword()));
        JwtResponseDTO response = artisanService.addArtisan(artisan);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/artisan/totalRevenue")
    public Double getTotalRevenueForArtisan(@RequestParam String username) {
        Artisan artisan = artisanService.loadArtisanByUsername(username);
        return commandeService.getTotalRevenueForArtisan(artisan.getId());
    }

    @GetMapping("/artisan/{childCommandeId}/commande")
    public Commande getCommandeByChildCommandeId(@PathVariable Long childCommandeId) {
        ChildCommande childCommande = childCommandeRepository.findById(childCommandeId).get();
        return childCommande.getCommande();
    }

    @GetMapping("/artisan/{childCommandeId}/articles")
    public ResponseEntity<List<CommandeArticles>> getArticlesByChildCommandeId(@PathVariable Long childCommandeId) {
        Optional<ChildCommande> childCommandeOptional = childCommandeRepository.findById(childCommandeId);
        if (childCommandeOptional.isPresent()) {
            List<CommandeArticles> articles = childCommandeOptional.get().getCommandeArticles();
            return ResponseEntity.ok(articles);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/artisan/commandes")
    public List<ChildCommande> getCommandesForArtisan(@RequestParam String username) {
        Artisan artisan = artisanService.loadArtisanByUsername(username);
        return childCommandeRepository.findAllByArtisanId(artisan.getId());
    }

    @GetMapping("/artisan/articles")
    public ResponseEntity<List<Article>> listArticles(@RequestParam String username){
        Artisan artisan = artisanService.loadArtisanByUsername(username);
        List<Article> articles=articleService.getArticlesByArtisanId(artisan.getId());
        articles.forEach(article -> article.setArtisan(null));
        return ResponseEntity.ok(articles);
    }


    @PostMapping("/artisan/addArticle")
    public ResponseEntity<Void> addArticle(@RequestBody Article article,@RequestParam String username) {
        try {
            Artisan artisan = artisanService.loadArtisanByUsername(username);
            article.setArtisan(artisan);
            artisan.getArticles().add(article);
            if (article.getTaille() == null){
                article.setTaille(null);
            }
            articleService.addArticle(article);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/artisan/deleteArticle/{id}")
    public void deleteArticle(@PathVariable Long id){
        articleService.deleteArticleById(id);
    }

    @GetMapping("/artisan/totalCommandes")
    public Long getTotalCommandesForArtisan(@RequestParam String username) {
        Artisan artisan = artisanService.loadArtisanByUsername(username);
        return childCommandeRepository.countByArtisanId(artisan.getId());
    }

    @PutMapping("/artisan/updateArticle")
    public Article updateArticle(@RequestBody Article article,@RequestParam Long id){
        return articleService.updateArticle(article,id);
    }

    @PutMapping("/artisan/updateArtisan")
    public Artisan updateProfil(@RequestBody Artisan artisan,@RequestParam String username){
        return artisanService.updateArtisan(artisan,username);
    }

    @GetMapping("/artisan/{username}")
    public Artisan getByUsernameArtisan(@PathVariable String username){
        return artisanService.loadArtisanByUsername(username);
    }

    @DeleteMapping("/artisan/delete")
    public void deleteArtisanCompte(@RequestParam String username){
        artisanService.deleteArtisanByUsername(username);
    }

    @PostMapping("/artisan/participer/{competitionId}")
    public ResponseEntity<Void> participerCompetition(@PathVariable Long competitionId,@RequestBody CompetitionParticipationDTO competitionParticipationDTO,@RequestParam String username) {
        Optional<Competition> competitionOptional = competitionRepository.findById(competitionId);
        Artisan artisan = artisanService.loadArtisanByUsername(username);

        Competition competition = competitionOptional.get();
        ParticipationComp participationCheck = participationCompRepository.findByCompetitionAndArtisan(competition, artisan);
        if (participationCheck != null) {
            return ResponseEntity.badRequest().build();
        }

        Article article = articleService.getArticleById(competitionParticipationDTO.getArticleId());
        ParticipationComp participation = new ParticipationComp();
        participation.setCompetition(competition);
        participation.setArtisan(artisan);
        participation.setArticle(article);
        participation.setDescription(competitionParticipationDTO.getDescription());
        participation.setVotes(0);
        participationCompRepository.save(participation);

        competition.setNombreParticipants(competition.getNombreParticipants() + 1);
        competitionRepository.save(competition);

        return ResponseEntity.ok().build();
}


}

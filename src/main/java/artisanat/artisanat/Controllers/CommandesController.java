package artisanat.artisanat.Controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import artisanat.artisanat.model.DTOs.CartItemDTO;
import artisanat.artisanat.model.DTOs.CommandeDTO;
import artisanat.artisanat.model.DTOs.PanierDTO;
import artisanat.artisanat.model.Entities.Article;
import artisanat.artisanat.model.Entities.ChildPanier;
import artisanat.artisanat.model.Entities.Client;
import artisanat.artisanat.model.Entities.Commande;
import artisanat.artisanat.model.Entities.Panier;
import artisanat.artisanat.model.Entities.PanierArticles;
import artisanat.artisanat.model.Entities.PanierArticlesPK;
import artisanat.artisanat.model.Entities.Commande.Statut;
import artisanat.artisanat.model.Repositories.ClientRepository;
import artisanat.artisanat.model.Repositories.CommandeRepository;
import artisanat.artisanat.model.Repositories.PanierArticlesRepository;
import artisanat.artisanat.model.Repositories.PanierRepository;
import artisanat.artisanat.model.Services.ArticleService;
import artisanat.artisanat.model.Services.ArtisanService;
import artisanat.artisanat.model.Services.ClientService;
import artisanat.artisanat.model.Services.CommandeService;
import artisanat.artisanat.model.Services.PanierService;

@RestController
@CrossOrigin("http://localhost:3000")
public class CommandesController {

    @Autowired
    CommandeService commandeService;

    @Autowired
    PanierService panierService;

    @Autowired
    ClientService clientService;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArtisanService artisanService;

    @Autowired
    PanierRepository panierRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CommandeRepository commandeRepository;

    @Autowired
    PanierArticlesRepository panierArticlesRepository;

    @PostMapping("/client/panier")
    public ResponseEntity<Void> ajouterAuPanier(@RequestParam String username, @RequestBody PanierDTO panierDTO) {
        Client client = clientService.loadClientByUsername(username);
        Panier panier = client.getPanier();
    
        Article article = articleService.getArticleById(panierDTO.getId());
    
        // Check if the article is already in the cart
        ChildPanier existingChildPanier = getChildPanierByArtisanId(panier, article.getArtisan().getId());
    
        if (existingChildPanier != null) {
            PanierArticles existingPanierArticle = getPanierArticleByArticleId(existingChildPanier, article.getId());
            
            if (existingPanierArticle != null) {
                existingPanierArticle.setQuantite(existingPanierArticle.getQuantite() + panierDTO.getQuantite());
                existingPanierArticle.setPrixUnitaire(article.getPrix() * existingPanierArticle.getQuantite());
            } else {
                // Create a new PanierArticles entry if the article is not found in the existing ChildPanier
                PanierArticles newPanierArticle = new PanierArticles();
                newPanierArticle.setChildPanier(existingChildPanier);
                newPanierArticle.setArticle(article);
                newPanierArticle.setQuantite(panierDTO.getQuantite());
                newPanierArticle.setPrixUnitaire(article.getPrix() * newPanierArticle.getQuantite());
    
                existingChildPanier.getPanierArticles().add(newPanierArticle);
            }
    
            existingChildPanier.setPrixTotal(calculateTotalPrice(existingChildPanier));
            panierService.saveChildPanier(existingChildPanier);
        } else {
            // Add the article to the cart if it's not already present
            ChildPanier childPanier = new ChildPanier();
            childPanier.setArtisan(artisanService.loadArtisanById(article.getArtisan().getId()));
            panier.getChildPaniers().add(childPanier);
            childPanier.setPanier(panier);
    
            PanierArticles newPanierArticle = new PanierArticles();
            newPanierArticle.setChildPanier(childPanier);
            newPanierArticle.setArticle(article);
            newPanierArticle.setQuantite(panierDTO.getQuantite());
            newPanierArticle.setPrixUnitaire(article.getPrix() * newPanierArticle.getQuantite());
    
            childPanier.getPanierArticles().add(newPanierArticle);
            childPanier.setPrixTotal(calculateTotalPrice(childPanier));
            panierService.saveChildPanier(childPanier);
        }
    
        panier.setPrixTotal(calculateTotalPrice(panier));
        panierService.savePanier(panier);
    
        return ResponseEntity.ok().build();
    }

    public PanierArticles getPanierArticleByArticleId(ChildPanier childPanier, Long articleId) {
        for (PanierArticles panierArticle : childPanier.getPanierArticles()) {
            if (panierArticle.getArticle().getId().equals(articleId)) {
                return panierArticle;
            }
        }
        return null;
    }
    
    

@GetMapping("/client/cart")
public Panier getPanier(@RequestParam String username){
    Client client= clientService.loadClientByUsername(username);
    Panier panier= client.getPanier();
    return panier;
}

@GetMapping("/client/cart/articles")
public List<PanierArticles> getPanierArticles(@RequestParam Long id) {
    Panier panier = panierRepository.findById(id).get();
    List<ChildPanier> childPaniers = panier.getChildPaniers();
    
    List<PanierArticles> panierArticles = new ArrayList<>();
    for (ChildPanier childPanier : childPaniers ) {
        panierArticles.addAll(childPanier.getPanierArticles());
    }

    return panierArticles;
}

@GetMapping("/client/cart/articles/count")
public int getNumberOfArticlesInCart(@RequestParam Long id) {
    Panier panier = panierRepository.findById(id).get();
    List<ChildPanier> childPaniers = panier.getChildPaniers();
    
    int totalArticles = 0;
    for (ChildPanier childPanier : childPaniers) {
        totalArticles += childPanier.getPanierArticles().size();
    }

    return totalArticles;
}

@GetMapping("/client/cartId")
public Long getCartId(@RequestParam String username){
    Client client = clientService.loadClientByUsername(username);
    Panier panier = client.getPanier();
    return panier.getId();
}




private ChildPanier getChildPanierByArtisanId(Panier panier, Long artisanId) {
    return panier.getChildPaniers().stream()
            .filter(childPanier -> childPanier.getArtisan().getId().equals(artisanId))
            .findFirst()
            .orElse(null);
}

private double calculateTotalPrice(Panier panier) {
    return panier.getChildPaniers().stream()
            .mapToDouble(this::calculateTotalPrice)
            .sum();
}

private double calculateTotalPrice(ChildPanier childPanier) {
    return childPanier.getPanierArticles().stream()
            .mapToDouble(item -> item.getQuantite() * item.getPrixUnitaire())
            .sum();
}
@PostMapping("/client/commander")
public ResponseEntity<Commande> placeOrder(@RequestParam String username, @RequestBody CommandeDTO commandeInfo) {
    Client client = clientService.loadClientByUsername(username);
    client.setPoints(client.getPoints() + 20);
    clientRepository.save(client);
    Panier panier = client.getPanier();

        Commande commandeMain = new Commande();
        commandeMain.setStatut(Statut.EnPreparation);
        commandeMain.setClient(client);
        commandeMain.setDate(LocalDateTime.now());

    for (ChildPanier childPanier : panier.getChildPaniers()) {
        Commande commande = commandeService.createCommandeFromChildPanier(childPanier, client,commandeMain);
        commande.setAddress(commandeInfo.getAddress());
        commande.setNumeroTel(commandeInfo.getNumeroTel());
        commande.setWillaya(commandeInfo.getWillaya());
        commande.setTypeDePaiement(commandeInfo.getTypeDePaiement());
        commande.setPrixTotal(commandeInfo.getTotalAmount());

        childPanier.getPanierArticles().clear();
        childPanier.setPrixTotal(0.0);
        panierService.saveChildPanier(childPanier);
        commandeMain = commande;
    }
    commandeService.ajouterCommande(commandeMain);

    panier.getChildPaniers().clear();
    panier.setPrixTotal(0.0);
    panierService.savePanier(panier);

    return ResponseEntity.ok(commandeMain);
}
@GetMapping("/client/commandes")
public List<Commande> getCommandes(@RequestParam String username){
    Client client= clientService.loadClientByUsername(username);
    List<Commande> listCommandes = commandeRepository.findAllByClientId(client.getId());
    return listCommandes;
    
}

@PutMapping("/client/cart/articles/{id}")
public ResponseEntity<Void> updateCartItemQuantity(@PathVariable Long id, @RequestBody CartItemDTO cartItemDTO) {
    // Fetch the cart item by id
    PanierArticles panierArticle = panierArticlesRepository.findByArticleId(id);
    panierArticle.setQuantite(cartItemDTO.getQuantite());
    panierArticle.setPrixUnitaire(panierArticle.getArticle().getPrix() * panierArticle.getQuantite());
    panierArticlesRepository.save(panierArticle);
    return ResponseEntity.ok().build();
}

@DeleteMapping("/client/cart/articles/{id}")
public ResponseEntity<Void> removeCartItem(@PathVariable Long id) {
    PanierArticles panierArticle = panierArticlesRepository.findByArticleId(id);
    panierArticlesRepository.delete(panierArticle);
    return ResponseEntity.ok().build();
}
}
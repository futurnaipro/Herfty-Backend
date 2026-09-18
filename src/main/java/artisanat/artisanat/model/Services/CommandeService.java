package artisanat.artisanat.model.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import artisanat.artisanat.model.Entities.Article;
import artisanat.artisanat.model.Entities.Artisan;
import artisanat.artisanat.model.Entities.ChildCommande;
import artisanat.artisanat.model.Entities.ChildPanier;
import artisanat.artisanat.model.Entities.Client;
import artisanat.artisanat.model.Entities.Commande;
import artisanat.artisanat.model.Entities.CommandeArticles;
import artisanat.artisanat.model.Entities.Panier;
import artisanat.artisanat.model.Entities.PanierArticles;
import artisanat.artisanat.model.Repositories.ArtisanRepository;
import artisanat.artisanat.model.Repositories.ChildCommandeRepository;
import artisanat.artisanat.model.Repositories.CommandeRepository;

@Service
public class CommandeService {

    @Autowired
    CommandeRepository commandeRepository;

    @Autowired
    ChildCommandeRepository childCommandeRepository;

    @Autowired 
    ArtisanRepository artisanRepository;

    @Autowired
    ArtisanService artisanService;


    public Double getTotalRevenueForArtisan(Long artisanId) {
        List<ChildCommande> childCommandes = childCommandeRepository.findAllByArtisanId(artisanId);
        Double totalRevenue = childCommandes.stream()
                .mapToDouble(ChildCommande::getPrixTotalChildCommande)
                .sum();
        return totalRevenue;
    }

    public Commande ajouterCommande(Commande commande){
       return commandeRepository.save(commande);
    }

    public Commande createCommandeFromChildPanier(ChildPanier panier, Client client,Commande commande) {


        ChildCommande childCommande=new ChildCommande();

        List<CommandeArticles> commandeItems = new ArrayList<>();
        double totalChildCommande = 0.0;
        for (PanierArticles panierArticle : panier.getPanierArticles()) {
            CommandeArticles commandeItem = new CommandeArticles();
            commandeItem.setChildCommande(childCommande);
            commandeItem.setArticle(panierArticle.getArticle());
            commandeItem.setQuantite(panierArticle.getQuantite());
            commandeItem.setPrixUnitaire(panierArticle.getPrixUnitaire());
            commandeItems.add(commandeItem);
            totalChildCommande += panierArticle.getQuantite() * panierArticle.getPrixUnitaire();
        }

        childCommande.setCommandeArticles(commandeItems);
        childCommande.setPrixTotalChildCommande(totalChildCommande);
        childCommande.setArtisan(panier.getArtisan());
        childCommande.setCommande(commande);
        commande.getChildCommandes().add(childCommande);
        
        Artisan artisan = artisanRepository.findArtisanById(panier.getArtisan().getId());
        artisan.setPoints(artisan.getPoints() + 20);
        artisanRepository.save(artisan);
    double totalAmount = commande.getChildCommandes().stream()
            .mapToDouble(ChildCommande::getPrixTotalChildCommande)
            .sum();
    commande.setPrixTotal(totalAmount);

        return commande;
    }

    public List<CommandeArticles> getChildCommandeArticles(Long id){
        Optional<ChildCommande> childCommande = childCommandeRepository.findById(id);
      return childCommande.get().getCommandeArticles();

    }

    public List<CommandeArticles> getAllArticles(Long id){
        List<CommandeArticles> totalArticles= new ArrayList<>();
        List<ChildCommande> childCommandes=childCommandeRepository.findAllByCommandeId(id);
        for (ChildCommande childCommande: childCommandes) {
            for (CommandeArticles commandeArticle : childCommande.getCommandeArticles()) {
                totalArticles.add(commandeArticle);
            }
        }
        return totalArticles;
    }
}

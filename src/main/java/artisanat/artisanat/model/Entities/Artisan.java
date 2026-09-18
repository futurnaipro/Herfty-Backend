package artisanat.artisanat.model.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Artisan extends AppUser{
    

    public Artisan(TypeArtisanat typeArtisanat, int points, Badges badges, List<Article> articles) {
        this.typeArtisanat = typeArtisanat;
        this.points = points;
        this.badges = badges;
        this.articles = articles;
    }



public Artisan(Long id, String nom, String prenom, String email, String adresse, String username, String password,
            TypeArtisanat typeArtisanat, int points, Badges badges, List<Article> articles) {
        super(id, nom, prenom, email, adresse, username, password);
        this.typeArtisanat = typeArtisanat;
        this.points = points;
        this.badges = badges;
        this.articles = articles;
    }



public enum TypeArtisanat {
    Poterie,
Tapisserie,
 Accessoires,
 ArtisanatEnCuivre,
Broderie, Crochet,
Vetements,Autre
    }
    @Enumerated(EnumType.STRING)
    private TypeArtisanat typeArtisanat;
    

    private int points;
    public enum Badges {
    ArtisanDébutant,ArtisanConfirmé,MaitreArtisan
    }
    @Enumerated(EnumType.STRING)
    private Badges badges;

    private String carte;

    @Enumerated(EnumType.STRING)
    private Statut statut;
    public enum Statut {
        Validé,EnAttente
       }
    @JsonIgnore
    @OneToMany(mappedBy = "artisan", cascade = CascadeType.ALL)
    private List<Article> articles;

    @JsonIgnore
    @OneToMany(mappedBy = "artisan", cascade = CascadeType.ALL)
    private List<ChildCommande> childCommandes;

    @JsonIgnore
    @OneToMany(mappedBy = "artisan", cascade = CascadeType.ALL)
    private List<ChildPanier> paniers;

    
    
}

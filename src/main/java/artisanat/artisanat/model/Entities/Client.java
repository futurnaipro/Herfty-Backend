package artisanat.artisanat.model.Entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor 
public class Client extends AppUser {
    

    public Client(int points, Badges badges) {
        this.points = points;
        this.badges = badges;
    }
    public Client(Long id, String nom, String prenom, String email, String adresse, String username, String password,
            int points, Badges badges) {
        super(id, nom, prenom, email, adresse, username, password);
        this.points = points;
        this.badges = badges;
    }
    private int points;

    @Enumerated(EnumType.STRING)
    private Badges badges;
    public enum Badges {
       Nouveau,ClientRégulier,ClientPremium,ClientVIP
    }

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Commande> commandes;

    @JsonIgnore
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Panier panier;
}


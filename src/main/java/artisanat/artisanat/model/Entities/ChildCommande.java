package artisanat.artisanat.model.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChildCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double prixTotalChildCommande;
    
    @JsonSerialize(using = ArtisanSerializer.class)
    @ManyToOne
    @JoinColumn(name = "artisan_id")
    private Artisan artisan;

    @JsonSerialize(using = CommandeSerializer.class)
    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @OneToMany(mappedBy = "childCommande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommandeArticles> commandeArticles = new ArrayList<>();
}


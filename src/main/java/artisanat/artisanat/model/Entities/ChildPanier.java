package artisanat.artisanat.model.Entities;

import java.util.ArrayList;
import java.util.List;

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
public class ChildPanier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double prixTotal;

    @ManyToOne
    @JoinColumn(name = "artisan_id")
    private Artisan artisan;

    @ManyToOne
    @JoinColumn(name = "panier_id")
    private Panier panier;


    @OneToMany(mappedBy = "childPanier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PanierArticles> panierArticles = new ArrayList<>();
}
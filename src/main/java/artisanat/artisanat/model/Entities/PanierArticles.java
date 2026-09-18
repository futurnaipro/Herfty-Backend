package artisanat.artisanat.model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@IdClass(PanierArticlesPK.class)
public class PanierArticles {

    @JsonIgnore
    @Id
    @ManyToOne
    @JoinColumn(name = "child_panier_id")
    private ChildPanier childPanier;

    @Id
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    private int quantite;
    private double prixUnitaire;
}
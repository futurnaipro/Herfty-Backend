package artisanat.artisanat.model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
@IdClass(CommandeArticlesPK.class)
public class CommandeArticles {

    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "child_commande_id")
    private ChildCommande childCommande;

    @Id
    @JsonSerialize(using = ArticleSerializer.class)
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    private int quantite;
    private double prixUnitaire;
}

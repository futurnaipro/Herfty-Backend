package artisanat.artisanat.model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Article {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Categorie categorie;
    public enum Categorie{
        Poterie,
        Tapisserie,
        Accessoires,
        ArtisanatEnCuivre,
        Broderie, Crochet,
        Vetements,Autre
    }

    @Column(nullable = false)
    private double prix;
    private int quantite;

    
    private String image;
    private String video;


    @Enumerated(EnumType.STRING)
    private Taille taille;
    public enum Taille {
        S, M, L, XL, XXL, XXXL,Standard,None
    }

    private String description;

    @JsonSerialize(using = ArtisanSerializer.class)
    @ManyToOne
    @JoinColumn(name= "artisan_id")
    private Artisan artisan;

}

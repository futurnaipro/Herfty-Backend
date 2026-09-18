package artisanat.artisanat.model.Entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class PanierArticlesPK implements Serializable {
    private Long childPanier;
    private Long article;
}

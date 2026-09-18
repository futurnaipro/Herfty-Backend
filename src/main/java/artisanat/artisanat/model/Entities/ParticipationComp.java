package artisanat.artisanat.model.Entities;

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
@IdClass(ParticipationCompPK.class)
public class ParticipationComp {

    @Id
    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @Id
    @ManyToOne
    @JoinColumn(name = "artisan_id")
    private Artisan artisan;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    private String description;

    private int votes;

}

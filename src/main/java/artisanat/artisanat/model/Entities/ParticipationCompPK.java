package artisanat.artisanat.model.Entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ParticipationCompPK {

    private Long competition;
    private Long artisan;
}

package artisanat.artisanat.model.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import artisanat.artisanat.model.Entities.Artisan;
import artisanat.artisanat.model.Entities.Competition;
import artisanat.artisanat.model.Entities.ParticipationComp;
import artisanat.artisanat.model.Entities.ParticipationCompPK;

@Repository
public interface ParticipationCompRepository extends JpaRepository<ParticipationComp,ParticipationCompPK> {

   ParticipationComp findByCompetitionAndArtisan(Competition competition, Artisan artisan);

}

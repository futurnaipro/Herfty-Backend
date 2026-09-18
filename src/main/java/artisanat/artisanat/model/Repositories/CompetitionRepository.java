package artisanat.artisanat.model.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import artisanat.artisanat.model.Entities.Competition;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition,Long> {

}

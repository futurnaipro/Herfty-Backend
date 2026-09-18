package artisanat.artisanat.model.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import artisanat.artisanat.model.Entities.ChildCommande;

@Repository
public interface ChildCommandeRepository extends JpaRepository<ChildCommande,Long> {

    List<ChildCommande> findAllByCommandeId(Long id);

    Long countByArtisanId(Long artisanId);

    List<ChildCommande> findAllByArtisanId(Long artisanId);
}

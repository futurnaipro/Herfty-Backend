package artisanat.artisanat.model.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import artisanat.artisanat.model.Entities.Artisan;
import artisanat.artisanat.model.Entities.Artisan.Statut;

@Repository
public interface ArtisanRepository extends JpaRepository<Artisan,Long>{

    void deleteById(Long id);

    public Artisan findArtisanById(Long id);

    Artisan findArtisanByUsername(String username);
    void deleteByUsername(String username);

    List<Artisan> findAllByStatut(Statut statut);
}

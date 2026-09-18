package artisanat.artisanat.model.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import artisanat.artisanat.model.Entities.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande,Long> {

    List<Commande> findAllByClientId(Long id);
}

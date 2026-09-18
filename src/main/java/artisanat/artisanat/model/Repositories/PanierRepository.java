package artisanat.artisanat.model.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import artisanat.artisanat.model.Entities.Panier;

@Repository
public interface PanierRepository extends JpaRepository<Panier,Long>{

}

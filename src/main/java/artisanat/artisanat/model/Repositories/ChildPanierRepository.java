package artisanat.artisanat.model.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import artisanat.artisanat.model.Entities.ChildPanier;
@Repository
public interface ChildPanierRepository extends JpaRepository<ChildPanier,Long> {

}

package artisanat.artisanat.model.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import artisanat.artisanat.model.Entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long>{

    Client findClientByUsername(String username);
    boolean existsByEmail(String email);
    void deleteById(Long id);
    void deleteByUsername(String username);

}

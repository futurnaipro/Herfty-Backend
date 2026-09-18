package artisanat.artisanat.model.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import artisanat.artisanat.model.Entities.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

   AppUser findByUsername(String username);
   AppUser findAppUserById(Long id);
   boolean existsByEmail(String email);
   boolean existsByUsername(String username);
}

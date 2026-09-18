package artisanat.artisanat.model.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import artisanat.artisanat.model.Entities.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{

}

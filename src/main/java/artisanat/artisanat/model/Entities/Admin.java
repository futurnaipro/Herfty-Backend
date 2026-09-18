package artisanat.artisanat.model.Entities;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Admin extends AppUser{
    public Admin(Long id,String nom, String prenom, String email, String adresse,String username,String password) {
        super(id,nom, prenom, email, adresse,username,password);
    }
}

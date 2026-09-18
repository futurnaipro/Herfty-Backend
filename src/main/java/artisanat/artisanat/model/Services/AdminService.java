package artisanat.artisanat.model.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import artisanat.artisanat.model.Entities.Admin;
import artisanat.artisanat.model.Entities.Role;
import artisanat.artisanat.model.Repositories.AdminRepository;

@Service
public class AdminService {

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    IdGeneratorService idGeneratorService;

    @Autowired
    AdminRepository adminRepository;


    public Admin addAdmin(String nom,String prenom,String email,String adresse,String username,String password){
        Admin admin=new Admin(idGeneratorService.generateId(),nom,prenom,email,adresse,username,passwordEncoder.encode(password));
        admin.setRole(Role.ADMIN);
        return adminRepository.save(admin);
    }

}

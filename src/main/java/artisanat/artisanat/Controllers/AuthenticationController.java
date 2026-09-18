package artisanat.artisanat.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import artisanat.artisanat.model.DTOs.JwtRequestDTO;
import artisanat.artisanat.model.DTOs.JwtResponseDTO;
import artisanat.artisanat.model.Services.ArtisanService;
import artisanat.artisanat.model.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    ArtisanService artisanService;

    @PostMapping("login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody JwtRequestDTO request){
        return ResponseEntity.ok(userService.authenticte(request));
    }
    @PostMapping("logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successful");
    }
    @GetMapping("test")
    public Authentication function(Authentication auth){
        return auth;
    }

}


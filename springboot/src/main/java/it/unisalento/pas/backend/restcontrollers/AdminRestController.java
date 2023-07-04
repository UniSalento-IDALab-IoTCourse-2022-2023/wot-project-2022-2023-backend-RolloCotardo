package it.unisalento.pas.backend.restcontrollers;

import it.unisalento.pas.backend.domain.Admin;
import it.unisalento.pas.backend.dto.AdminDTO;
import it.unisalento.pas.backend.dto.AuthenticationResponseDTO;
import it.unisalento.pas.backend.dto.LoginDTO;
import it.unisalento.pas.backend.repositories.AdminRepository;
import it.unisalento.pas.backend.security.JwtUtilities;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import static it.unisalento.pas.backend.configuration.SecurityConfig.passwordEncoder;

@RestController
@CrossOrigin
@RequestMapping("/api/admins")
public class AdminRestController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilities jwtUtilities;

    @RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AdminDTO addAdmin(@RequestBody AdminDTO adminDTO) {

        Admin newAdmin = new Admin();
        newAdmin.setNome(adminDTO.getNome());
        newAdmin.setCognome(adminDTO.getCognome());
        newAdmin.setEmail(adminDTO.getEmail());
        newAdmin.setPassword(passwordEncoder().encode(adminDTO.getPassword()));

        newAdmin = adminRepository.save(newAdmin);

        adminDTO.setId(newAdmin.getId());
        adminDTO.setPassword(null);

        return adminDTO;
    }

    @RequestMapping(value="/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        Admin admin = adminRepository.findByEmail(authentication.getName());

        if(admin == null) {
            throw new UsernameNotFoundException(loginDTO.getEmail());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = jwtUtilities.generateToken(admin.getEmail());

        return ResponseEntity.ok(new AuthenticationResponseDTO(jwt));

    }


}

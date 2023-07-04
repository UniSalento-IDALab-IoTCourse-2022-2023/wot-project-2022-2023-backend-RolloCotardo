package it.unisalento.pas.backend.repositories;

import it.unisalento.pas.backend.domain.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdminRepository extends MongoRepository<Admin, String> {

    public List<Admin> findByCognome(String cognome);

    public Admin findByEmail(String email);
}

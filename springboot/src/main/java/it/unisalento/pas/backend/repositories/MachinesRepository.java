package it.unisalento.pas.backend.repositories;

import it.unisalento.pas.backend.domain.Alarm;
import it.unisalento.pas.backend.domain.Machine;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MachinesRepository extends MongoRepository<Machine, String> {

    public Optional<Machine> findById(String id);

    public Machine findByIdMacchinario(String machine);

}
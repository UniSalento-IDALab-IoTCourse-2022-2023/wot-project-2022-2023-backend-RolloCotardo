package it.unisalento.pas.backend.repositories;

import it.unisalento.pas.backend.domain.Alarm;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AlarmsRepository extends MongoRepository<Alarm, String> {

    public Optional<Alarm> findById(String id);

}
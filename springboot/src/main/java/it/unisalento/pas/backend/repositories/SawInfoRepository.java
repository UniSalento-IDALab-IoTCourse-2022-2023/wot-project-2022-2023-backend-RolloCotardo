package it.unisalento.pas.backend.repositories;

import it.unisalento.pas.backend.domain.SawInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SawInfoRepository extends MongoRepository<SawInfo, String> {

    public Optional<SawInfo> findById(String id);

    public SawInfo[] findByIdMacchinario(String machine);

}

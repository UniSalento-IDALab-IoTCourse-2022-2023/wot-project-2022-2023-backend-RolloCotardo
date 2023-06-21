package it.unisalento.pas.backend.repositories;

import it.unisalento.pas.backend.domain.LatheInfo;
import it.unisalento.pas.backend.domain.SawInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LatheInfoRepository extends MongoRepository<LatheInfo, String> {

    public Optional<LatheInfo> findById(String id);

}
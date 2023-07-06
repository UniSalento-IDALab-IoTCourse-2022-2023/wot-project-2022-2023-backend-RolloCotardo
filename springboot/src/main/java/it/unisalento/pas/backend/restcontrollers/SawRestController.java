package it.unisalento.pas.backend.restcontrollers;

import it.unisalento.pas.backend.domain.SawInfo;
import it.unisalento.pas.backend.dto.SawInfoDTO;
import it.unisalento.pas.backend.repositories.SawInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin
@RequestMapping("/api/info/saws")
public class SawRestController {


    @Autowired
    SawInfoRepository sawInfoRepository;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public List<SawInfoDTO> getAllSaws() {

        List<SawInfoDTO> saws = new ArrayList<>();

        for(SawInfo sawInfo : sawInfoRepository.findAll()) {
            SawInfoDTO sawInfoDTO = new SawInfoDTO();
            sawInfoDTO.setId(sawInfo.getId());
            sawInfoDTO.setIdMacchinario(sawInfo.getIdMacchinario());
            sawInfoDTO.setAllineamento(sawInfo.getAllineamento());
            sawInfoDTO.setAvanzamento(sawInfo.getAvanzamento());
            sawInfoDTO.setTensione(sawInfo.getTensione());
            sawInfoDTO.setRotazione(sawInfo.getRotazione());
            sawInfoDTO.setLubrificante(sawInfo.getLubrificante());
            sawInfoDTO.setPotenza(sawInfo.getPotenza());
            sawInfoDTO.setTimestamp(sawInfo.getTimestamp());

            saws.add(sawInfoDTO);
        }

        return saws;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/{machine}", method= RequestMethod.GET)
    public List<SawInfoDTO> getById(@PathVariable String machine) {

        List<SawInfoDTO> saws = new ArrayList<>();

        for(SawInfo sawsInfo : sawInfoRepository.findByIdMacchinario(machine)) {
            SawInfoDTO sawInfoDTO = new SawInfoDTO();
            sawInfoDTO.setId(sawsInfo.getId());
            sawInfoDTO.setIdMacchinario(sawsInfo.getIdMacchinario());
            sawInfoDTO.setAllineamento(sawsInfo.getAllineamento());
            sawInfoDTO.setAvanzamento(sawsInfo.getAvanzamento());
            sawInfoDTO.setTensione(sawsInfo.getTensione());
            sawInfoDTO.setRotazione(sawsInfo.getRotazione());
            sawInfoDTO.setLubrificante(sawsInfo.getLubrificante());
            sawInfoDTO.setPotenza(sawsInfo.getPotenza());
            sawInfoDTO.setTimestamp(sawsInfo.getTimestamp());

            saws.add(sawInfoDTO);
        }

        return saws;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/last", method=RequestMethod.GET)
    public SawInfoDTO getLastSaw() {
        Pageable pageable = PageRequest.of(0, 1, Sort.Direction.DESC, "id");
        Page<SawInfo> page = sawInfoRepository.findAll(pageable);
        SawInfoDTO sawInfoDTO = new SawInfoDTO();

        if (page.hasContent()) {
            SawInfo lastSaw = page.getContent().get(0);
            sawInfoDTO.setId(lastSaw.getId());
            sawInfoDTO.setIdMacchinario(lastSaw.getIdMacchinario());
            sawInfoDTO.setAllineamento(lastSaw.getAllineamento());
            sawInfoDTO.setAvanzamento(lastSaw.getAvanzamento());
            sawInfoDTO.setTensione(lastSaw.getTensione());
            sawInfoDTO.setRotazione(lastSaw.getRotazione());
            sawInfoDTO.setLubrificante(lastSaw.getLubrificante());
            sawInfoDTO.setPotenza(lastSaw.getPotenza());
            sawInfoDTO.setTimestamp(lastSaw.getTimestamp());
        }

        return sawInfoDTO;
    }



    @RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SawInfoDTO addSawInfo(@RequestBody SawInfoDTO sawInfoDTO) {

        SawInfo newSawInfo = new SawInfo();
        newSawInfo.setIdMacchinario(sawInfoDTO.getIdMacchinario());
        newSawInfo.setAllineamento(sawInfoDTO.getAllineamento());
        newSawInfo.setAvanzamento(sawInfoDTO.getAvanzamento());
        newSawInfo.setTensione(sawInfoDTO.getTensione());
        newSawInfo.setRotazione(sawInfoDTO.getRotazione());
        newSawInfo.setLubrificante(sawInfoDTO.getLubrificante());
        newSawInfo.setPotenza(sawInfoDTO.getPotenza());
        newSawInfo.setTimestamp(sawInfoDTO.getTimestamp());

        newSawInfo = sawInfoRepository.save(newSawInfo);

        sawInfoDTO.setId(newSawInfo.getId());

        return sawInfoDTO;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/last/{machine}")
    public SawInfoDTO getLastByMachine(@PathVariable String machine) {

        SawInfo[] sawsInfo = sawInfoRepository.findByIdMacchinario(machine);

        SawInfoDTO sawInfoDTO = new SawInfoDTO();

        int lastIndex = sawsInfo.length - 1;

        sawInfoDTO.setId(sawsInfo[lastIndex].getId());
        sawInfoDTO.setIdMacchinario(sawsInfo[lastIndex].getIdMacchinario());
        sawInfoDTO.setAllineamento(sawsInfo[lastIndex].getAllineamento());
        sawInfoDTO.setRotazione(sawsInfo[lastIndex].getRotazione());
        sawInfoDTO.setTensione(sawsInfo[lastIndex].getTensione());
        sawInfoDTO.setAvanzamento(sawsInfo[lastIndex].getAvanzamento());
        sawInfoDTO.setLubrificante(sawsInfo[lastIndex].getLubrificante());
        sawInfoDTO.setPotenza(sawsInfo[lastIndex].getPotenza());
        sawInfoDTO.setTimestamp(sawsInfo[lastIndex].getTimestamp());

        return sawInfoDTO;
    }


}

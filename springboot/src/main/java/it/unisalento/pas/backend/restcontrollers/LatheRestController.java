package it.unisalento.pas.backend.restcontrollers;

import it.unisalento.pas.backend.domain.LatheInfo;
import it.unisalento.pas.backend.dto.LatheInfoDTO;
import it.unisalento.pas.backend.repositories.LatheInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin
@RequestMapping("/api/info/lathes")
public class LatheRestController {


    @Autowired
    LatheInfoRepository latheInfoRepository;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public List<LatheInfoDTO> getAllLathes() {

        List<LatheInfoDTO> lathes = new ArrayList<>();

        for(LatheInfo latheInfo : latheInfoRepository.findAll()) {
            LatheInfoDTO latheInfoDTO = new LatheInfoDTO();
            latheInfoDTO.setId(latheInfo.getId());
            latheInfoDTO.setIdMacchinario(latheInfo.getIdMacchinario());
            latheInfoDTO.setAllineamento(latheInfo.getAllineamento());
            latheInfoDTO.setVibrazioni(latheInfo.getVibrazioni());
            latheInfoDTO.setRotazione(latheInfo.getRotazione());
            latheInfoDTO.setLubrificante(latheInfo.getLubrificante());
            latheInfoDTO.setPotenza(latheInfo.getPotenza());

            lathes.add(latheInfoDTO);
        }

        return lathes;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/last", method=RequestMethod.GET)
    public LatheInfoDTO getLastLathe() {
        Pageable pageable = PageRequest.of(0, 1, Sort.Direction.DESC, "id");
        Page<LatheInfo> page = latheInfoRepository.findAll(pageable);
        LatheInfoDTO latheInfoDTO = new LatheInfoDTO();

        if (page.hasContent()) {
            LatheInfo lastLathe = page.getContent().get(0);
            latheInfoDTO.setId(lastLathe.getId());
            latheInfoDTO.setIdMacchinario(lastLathe.getIdMacchinario());
            latheInfoDTO.setAllineamento(lastLathe.getAllineamento());
            latheInfoDTO.setVibrazioni(lastLathe.getVibrazioni());
            latheInfoDTO.setRotazione(lastLathe.getRotazione());
            latheInfoDTO.setLubrificante(lastLathe.getLubrificante());
            latheInfoDTO.setPotenza(lastLathe.getPotenza());
        }

        return latheInfoDTO;
    }


    @RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LatheInfoDTO addLatheInfo(@RequestBody LatheInfoDTO latheInfoDTO) {

        LatheInfo newLatheInfo = new LatheInfo();
        newLatheInfo.setIdMacchinario(latheInfoDTO.getIdMacchinario());
        newLatheInfo.setAllineamento(latheInfoDTO.getAllineamento());
        newLatheInfo.setVibrazioni(latheInfoDTO.getVibrazioni());
        newLatheInfo.setRotazione(latheInfoDTO.getRotazione());
        newLatheInfo.setLubrificante(latheInfoDTO.getLubrificante());
        newLatheInfo.setPotenza(latheInfoDTO.getPotenza());

        newLatheInfo = latheInfoRepository.save(newLatheInfo);

        latheInfoDTO.setId(newLatheInfo.getId());

        return latheInfoDTO;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/last/{machine}")
    public LatheInfoDTO getLastByMachine(@PathVariable String machine) {

        LatheInfo[] latheInfos = latheInfoRepository.findByIdMacchinario(machine);

        LatheInfoDTO latheInfoDTO = new LatheInfoDTO();

        int lastIndex = latheInfos.length - 1;

        latheInfoDTO.setId(latheInfos[lastIndex].getId());
        latheInfoDTO.setIdMacchinario(latheInfos[lastIndex].getIdMacchinario());
        latheInfoDTO.setAllineamento(latheInfos[lastIndex].getAllineamento());
        latheInfoDTO.setRotazione(latheInfos[lastIndex].getRotazione());
        latheInfoDTO.setVibrazioni(latheInfos[lastIndex].getVibrazioni());
        latheInfoDTO.setLubrificante(latheInfos[lastIndex].getLubrificante());
        latheInfoDTO.setPotenza(latheInfos[lastIndex].getPotenza());

        return latheInfoDTO;
    }

}

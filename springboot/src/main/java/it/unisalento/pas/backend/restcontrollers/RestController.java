package it.unisalento.pas.backend.restcontrollers;

import it.unisalento.pas.backend.domain.Alarm;
import it.unisalento.pas.backend.domain.LatheInfo;
import it.unisalento.pas.backend.domain.SawInfo;
import it.unisalento.pas.backend.dto.AlarmDTO;
import it.unisalento.pas.backend.dto.LatheInfoDTO;
import it.unisalento.pas.backend.dto.SawInfoDTO;
import it.unisalento.pas.backend.repositories.AlarmsRepository;
import it.unisalento.pas.backend.repositories.LatheInfoRepository;
import it.unisalento.pas.backend.repositories.SawInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin
@RequestMapping("/api")
public class RestController {


    @Autowired
    SawInfoRepository sawInfoRepository;

    @Autowired
    LatheInfoRepository latheInfoRepository;

    @Autowired
    AlarmsRepository alarmsRepository;


    @RequestMapping(value="/info/saws", method= RequestMethod.GET)
    public List<SawInfoDTO> getAllSaws() {

        List<SawInfoDTO> saws = new ArrayList<>();

        for(SawInfo sawInfo : sawInfoRepository.findAll()) {
            SawInfoDTO sawInfoDTO = new SawInfoDTO();
            sawInfoDTO.setId(sawInfo.getId());
            sawInfoDTO.setAllineamento(sawInfo.getAllineamento());
            sawInfoDTO.setAvanzamento(sawInfo.getAvanzamento());
            sawInfoDTO.setTensione(sawInfo.getTensione());
            sawInfoDTO.setRotazione(sawInfo.getRotazione());
            sawInfoDTO.setLubrificante(sawInfo.getLubrificante());
            sawInfoDTO.setPotenza(sawInfo.getPotenza());

            saws.add(sawInfoDTO);
        }

        return saws;
    }

    @RequestMapping(value="/info/lathes", method= RequestMethod.GET)
    public List<LatheInfoDTO> getAllLathes() {

        List<LatheInfoDTO> lathes = new ArrayList<>();

        for(LatheInfo latheInfo : latheInfoRepository.findAll()) {
            LatheInfoDTO latheInfoDTO = new LatheInfoDTO();
            latheInfoDTO.setId(latheInfo.getId());
            latheInfoDTO.setAllineamento(latheInfo.getAllineamento());
            latheInfoDTO.setVibrazioni(latheInfo.getVibrazioni());
            latheInfoDTO.setRotazione(latheInfo.getRotazione());
            latheInfoDTO.setLubrificante(latheInfo.getLubrificante());
            latheInfoDTO.setPotenza(latheInfo.getPotenza());

            lathes.add(latheInfoDTO);
        }

        return lathes;
    }


    @RequestMapping(value="/alarms", method= RequestMethod.GET)
    public List<AlarmDTO> getAllAlarms() {

        List<AlarmDTO> alarms = new ArrayList<>();

        for(Alarm alarm : alarmsRepository.findAll()) {
            AlarmDTO alarmDTO = new AlarmDTO();
            alarmDTO.setId(alarm.getId());
            alarmDTO.setIdMacchinario(alarm.getIdMacchinario());
            alarmDTO.setTipologia(alarm.getTipologia());
            alarmDTO.setValore(alarm.getValore());
            alarmDTO.setTimestamp(alarm.getTimestamp());

            alarms.add(alarmDTO);
        }

        return alarms;
    }


    @RequestMapping(value="/info/saws", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SawInfoDTO addSawInfo(@RequestBody SawInfoDTO sawInfoDTO) {

        SawInfo newSawInfo = new SawInfo();
        newSawInfo.setAllineamento(sawInfoDTO.getAllineamento());
        newSawInfo.setAvanzamento(sawInfoDTO.getAvanzamento());
        newSawInfo.setTensione(sawInfoDTO.getTensione());
        newSawInfo.setRotazione(sawInfoDTO.getRotazione());
        newSawInfo.setLubrificante(sawInfoDTO.getLubrificante());
        newSawInfo.setPotenza(sawInfoDTO.getPotenza());

        newSawInfo = sawInfoRepository.save(newSawInfo);

        sawInfoDTO.setId(newSawInfo.getId());

        return sawInfoDTO;
    }


    @RequestMapping(value="/info/lathes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LatheInfoDTO addLatheInfo(@RequestBody LatheInfoDTO latheInfoDTO) {

        LatheInfo newLatheInfo = new LatheInfo();
        newLatheInfo.setAllineamento(latheInfoDTO.getAllineamento());
        newLatheInfo.setVibrazioni(latheInfoDTO.getVibrazioni());
        newLatheInfo.setRotazione(latheInfoDTO.getRotazione());
        newLatheInfo.setLubrificante(latheInfoDTO.getLubrificante());
        newLatheInfo.setPotenza(latheInfoDTO.getPotenza());

        newLatheInfo = latheInfoRepository.save(newLatheInfo);

        latheInfoDTO.setId(newLatheInfo.getId());

        return latheInfoDTO;
    }


    @RequestMapping(value="/alarms", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AlarmDTO addLatheInfo(@RequestBody AlarmDTO alarmDTO) {

        Alarm newAlarm = new Alarm();
        newAlarm.setIdMacchinario(alarmDTO.getIdMacchinario());
        newAlarm.setTipologia(alarmDTO.getTipologia());
        newAlarm.setValore(alarmDTO.getValore());
        newAlarm.setTimestamp(alarmDTO.getTimestamp());

        newAlarm = alarmsRepository.save(newAlarm);

        alarmDTO.setId(newAlarm.getId());

        return alarmDTO;
    }
}

package it.unisalento.pas.backend.restcontrollers;

import it.unisalento.pas.backend.domain.Alarm;
import it.unisalento.pas.backend.domain.LatheInfo;
import it.unisalento.pas.backend.domain.Machine;
import it.unisalento.pas.backend.domain.SawInfo;
import it.unisalento.pas.backend.dto.AlarmDTO;
import it.unisalento.pas.backend.dto.LatheInfoDTO;
import it.unisalento.pas.backend.dto.MachineDTO;
import it.unisalento.pas.backend.dto.SawInfoDTO;
import it.unisalento.pas.backend.repositories.AlarmsRepository;
import it.unisalento.pas.backend.repositories.LatheInfoRepository;
import it.unisalento.pas.backend.repositories.MachinesRepository;
import it.unisalento.pas.backend.repositories.SawInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    MachinesRepository machinesRepository;


    @RequestMapping(value="/info/saws", method= RequestMethod.GET)
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

            saws.add(sawInfoDTO);
        }

        return saws;
    }

    @RequestMapping(value="/info/saws/last", method=RequestMethod.GET)
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
        }

        return sawInfoDTO;
    }

    @RequestMapping(value="/info/lathes", method= RequestMethod.GET)
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

    @RequestMapping(value="/info/lathes/last", method=RequestMethod.GET)
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


    @RequestMapping(value="/alarms", method= RequestMethod.GET)
    public List<AlarmDTO> getAllAlarms() {

        List<AlarmDTO> alarms = new ArrayList<>();

        for(Alarm alarm : alarmsRepository.findAll()) {
            AlarmDTO alarmDTO = new AlarmDTO();
            alarmDTO.setId(alarm.getId());
            alarmDTO.setIdMacchinario(alarm.getIdMacchinario());
            alarmDTO.setTipologia(alarm.getTipologia());
            alarmDTO.setValore(alarm.getValore());
            alarmDTO.setRange(alarm.getRange());
            alarmDTO.setTimestamp(alarm.getTimestamp());

            alarms.add(alarmDTO);
        }

        return alarms;
    }


    @RequestMapping(value="/info/saws", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SawInfoDTO addSawInfo(@RequestBody SawInfoDTO sawInfoDTO) {

        SawInfo newSawInfo = new SawInfo();
        newSawInfo.setIdMacchinario(sawInfoDTO.getIdMacchinario());
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


    @RequestMapping(value="/alarms", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AlarmDTO addLatheInfo(@RequestBody AlarmDTO alarmDTO) {

        Alarm newAlarm = new Alarm();
        newAlarm.setIdMacchinario(alarmDTO.getIdMacchinario());
        newAlarm.setTipologia(alarmDTO.getTipologia());
        newAlarm.setValore(alarmDTO.getValore());
        newAlarm.setRange(alarmDTO.getRange());
        newAlarm.setTimestamp(alarmDTO.getTimestamp());

        newAlarm = alarmsRepository.save(newAlarm);

        alarmDTO.setId(newAlarm.getId());

        return alarmDTO;
    }


    @RequestMapping(value="/machines", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MachineDTO addMachine(@RequestBody MachineDTO machineDTO) {

        Machine machine = new Machine();
        machine.setIdMacchinario(machineDTO.getIdMacchinario());
        machine.setMac_beacon(machineDTO.getMac_beacon());
        machine.setAllarmi(machineDTO.getAllarmi());

        machine = machinesRepository.save(machine);

        machineDTO.setId(machine.getId());

        return machineDTO;
    }
}

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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/alarms")
public class AlarmRestController {

    @Autowired
    AlarmsRepository alarmsRepository;


    @RequestMapping(value="/", method= RequestMethod.GET)
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


    @RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value="/find/{machine}")
    public List<AlarmDTO> getAlarmByMachine(@PathVariable String machine) {

        List<AlarmDTO> alarms = new ArrayList<>();

        for(Alarm alarm : alarmsRepository.findByIdMacchinario(machine)) {
            AlarmDTO alarmDTO = new AlarmDTO();
            alarmDTO.setId(alarm.getId());
            alarmDTO.setIdMacchinario(alarm.getIdMacchinario());
            alarmDTO.setRange(alarm.getRange());
            alarmDTO.setTipologia(alarm.getTipologia());
            alarmDTO.setValore(alarm.getValore());
            alarmDTO.setTimestamp(alarm.getTimestamp());
            alarms.add(alarmDTO);
        }
        return alarms;
    }

}

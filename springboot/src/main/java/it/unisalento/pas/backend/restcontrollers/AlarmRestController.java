package it.unisalento.pas.backend.restcontrollers;

import it.unisalento.pas.backend.domain.Alarm;
import it.unisalento.pas.backend.dto.AlarmDTO;
import it.unisalento.pas.backend.repositories.AlarmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/alarms")
public class AlarmRestController {

    @Autowired
    AlarmsRepository alarmsRepository;

    @PreAuthorize("hasRole('ADMIN')")
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
    public AlarmDTO addAlarm(@RequestBody AlarmDTO alarmDTO) {

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

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/find/{machine}")
    public List<AlarmDTO> getAlarmsByMachine(@PathVariable String machine) {

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

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
@RequestMapping("/api/machines")
public class MachineRestController {


    @Autowired
    MachinesRepository machinesRepository;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public List<MachineDTO> getAllMachines() {

        List<MachineDTO> machines = new ArrayList<>();

        for(Machine machine : machinesRepository.findAll()) {
            MachineDTO machineDTO = new MachineDTO();
            machineDTO.setId(machine.getId());
            machineDTO.setIdMacchinario(machine.getIdMacchinario());
            machineDTO.setMac_beacon(machine.getMac_beacon());
            machineDTO.setTopic(machine.getTopic());
            machineDTO.setAllarmi(machine.getAllarmi());

            machines.add(machineDTO);
        }

        return machines;
    }


    @RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MachineDTO addMachine(@RequestBody MachineDTO machineDTO) {

        Machine machine = new Machine();
        machine.setIdMacchinario(machineDTO.getIdMacchinario());
        machine.setMac_beacon(machineDTO.getMac_beacon());
        machine.setTopic(machineDTO.getTopic());
        machine.setAllarmi(machineDTO.getAllarmi());

        machine = machinesRepository.save(machine);

        machineDTO.setId(machine.getId());

        return machineDTO;
    }

    @RequestMapping(value="/find/{name}")
    public MachineDTO getMachineByName(@PathVariable String name) {

        Machine citizen = machinesRepository.findByIdMacchinario(name);

        MachineDTO machineDTO = new MachineDTO();

        machineDTO.setId(citizen.getId());
        machineDTO.setIdMacchinario(citizen.getIdMacchinario());
        machineDTO.setAllarmi(citizen.getAllarmi());
        machineDTO.setTopic(citizen.getTopic());
        machineDTO.setMac_beacon(citizen.getMac_beacon());

        return machineDTO;
    }
}

package it.unisalento.pas.backend.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class MachineDTO {

    @Id
    String id;

    String idMacchinario;

    String mac_beacon;

    String[] allarmi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMacchinario() {
        return idMacchinario;
    }

    public void setIdMacchinario(String idMacchinario) {
        this.idMacchinario = idMacchinario;
    }

    public String getMac_beacon() {
        return mac_beacon;
    }

    public void setMac_beacon(String mac_beacon) {
        this.mac_beacon = mac_beacon;
    }

    public String[] getAllarmi() {
        return allarmi;
    }

    public void setAllarmi(String[] allarmi) {
        this.allarmi = allarmi;
    }
}

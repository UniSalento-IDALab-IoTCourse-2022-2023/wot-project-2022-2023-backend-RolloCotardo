package it.unisalento.pas.backend.dto;

import org.springframework.data.annotation.Id;

public class SawInfoDTO {

    @Id
    String id;
    String idMacchinario;
    int tensione;
    float allineamento;
    int avanzamento;
    int rotazione;
    int lubrificante;
    int potenza;
    String timestamp;

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

    public int getTensione() {
        return tensione;
    }

    public void setTensione(int tensione) {
        this.tensione = tensione;
    }

    public float getAllineamento() {
        return allineamento;
    }

    public void setAllineamento(float allineamento) {
        this.allineamento = allineamento;
    }

    public int getAvanzamento() {
        return avanzamento;
    }

    public void setAvanzamento(int avanzamento) {
        this.avanzamento = avanzamento;
    }

    public int getRotazione() {
        return rotazione;
    }

    public void setRotazione(int rotazione) {
        this.rotazione = rotazione;
    }

    public int getLubrificante() {
        return lubrificante;
    }

    public void setLubrificante(int lubrificante) {
        this.lubrificante = lubrificante;
    }

    public int getPotenza() {
        return potenza;
    }

    public void setPotenza(int potenza) {
        this.potenza = potenza;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

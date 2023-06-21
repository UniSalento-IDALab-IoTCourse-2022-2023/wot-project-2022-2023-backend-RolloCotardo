package it.unisalento.pas.backend.dto;

import org.springframework.data.annotation.Id;

public class SawInfoDTO {

    @Id
    String id;
    int tensione;
    float allineamento;
    int avanzamento;
    int rotazione;
    int lubrificante;

    int potenza;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
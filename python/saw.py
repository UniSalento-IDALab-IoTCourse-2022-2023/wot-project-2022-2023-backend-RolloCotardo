import requests
import paho.mqtt.client as mqtt
from datetime import datetime

class Saw:

    chiavi = ['Tensione_sega', 'Allineamento_sega', 'Avanzamento_sega', 'Rotazione_sega', 'Lubrificante_sega', 'Potenza_sega']


    def __init__(self, info, idMacchinario, topic):
        self.idMacchinario = idMacchinario
        self.topic = topic
        self.sendInfo(info)


    def sendInfo(self, info):

        # Dati da inviare nella richiesta POST
        payload_saw = {
            "idMacchinario": self.idMacchinario,
            "tensione": info[self.chiavi[0]],
            "allineamento": info[self.chiavi[1]],
            "avanzamento": info[self.chiavi[2]],
            "rotazione": info[self.chiavi[3]],
            "lubrificante": info[self.chiavi[4]],
            "potenza": info[self.chiavi[5]],
        }

        # Effettua la richiesta POST

        if (int(info.get('Potenza_sega') != 0)):
            response = requests.post("http://localhost:8080/api/info/saws/", json=payload_saw)

        self.check(info)


    def check(self, info):

        if (int(info.get('Potenza_sega') != 0)):

            if (int(info.get('Tensione_sega') < 1400 or int(info.get('Tensione_sega') > 1500))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": self.idMacchinario,
                    "tipologia": "Tensione lama fuori range ottimale [1400-1500] psi",
                    "valore": int(info.get('Tensione_sega')),
                    "range": "1400-1500",
                    "timestamp": timestamp
                }

                self.sendAlarm("Tensione sega fuori range, allontanarsi", self.topic, payload_alarm)


            if (
            float(info.get('Allineamento_sega') < 0.025 or float(info.get('Allineamento_sega') > 0.05))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": self.idMacchinario,
                    "tipologia": "Allineamento lama fuori range ottimale [0.025-0.05] mm",
                    "valore": float(info.get('Allineamento_sega')),
                    "range": "0.025-0.05",
                    "timestamp": timestamp
                }

                self.sendAlarm("Allineamento sega fuori range, allontanarsi", self.topic, payload_alarm)

            if (int(info.get('Avanzamento_sega') < 50 or int(info.get('Avanzamento_sega') > 60))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": self.idMacchinario,
                    "tipologia": "Velocità della lama fuori range ottimale [50-60] m/minuto",
                    "valore": int(info.get('Avanzamento_sega')),
                    "range": "50-60",
                    "timestamp": timestamp
                }

                self.sendAlarm("Velocità avanzamento sega fuori range, allontanarsi", self.topic, payload_alarm)

            if (int(info.get('Rotazione_sega') < 300 or int(info.get('Rotazione_sega') > 400))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": self.idMacchinario,
                    "tipologia": "Velocità di rotazione della lama fuori range ottimale [300-400] m/minuto",
                    "valore": int(info.get('Rotazione_sega')),
                    "range": "300-400",
                    "timestamp": timestamp
                }

                self.sendAlarm("Velocità rotazione sega fuori range, allontanarsi", self.topic, payload_alarm)

            if (int(info.get('Lubrificante_sega') < 30)):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": self.idMacchinario,
                    "tipologia": "Livello del lubrificante sotto al 30%",
                    "valore": int(info.get('Lubrificante_sega')),
                    "range": "> 30",
                    "timestamp": timestamp
                }

                self.sendAlarm("Lubrificante sega fuori range, allontanarsi", self.topic, payload_alarm)


            if (int(info.get('Potenza_sega') < 750 or int(info.get('Potenza_sega') > 975))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": self.idMacchinario,
                    "tipologia": "Potenza della macchina furi range ottimale [750-975] W",
                    "valore": int(info.get('Potenza_sega')),
                    "range": "750-975",
                    "timestamp": timestamp
                }

                self.sendAlarm("Potenza sega fuori range, allontanarsi", self.topic, payload_alarm)



    def sendAlarm(self, message, topic, payload):

        '''
        # Configurazione del client MQTT
        client = mqtt.Client()
        # Connessione al broker MQTT
        client.connect("192.168.1.25", 1883, 60)
        # Invio di un messaggio MQTT
        client.publish(self.topic, message)
        '''


        requests.post("http://localhost:8080/api/alarms/", json=payload)
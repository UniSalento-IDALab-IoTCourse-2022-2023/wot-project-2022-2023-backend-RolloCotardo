import requests
import paho.mqtt.client as mqtt
from datetime import datetime

class Lathe:

    chiavi = ['Allineamento_tornio', 'Vibrazioni_tornio', 'Rotazione_tornio', 'Lubrificante_tornio',
              'Potenza_tornio']


    def __init__(self, info, idMacchinario, topic):
        self.idMacchinario = idMacchinario
        self.topic = topic
        self.sendInfo(info)


    def sendInfo(self, info):

        timestamp = str(datetime.now())

        # Dati da inviare nella richiesta POST
        payload_lathe = {
            "idMacchinario": self.idMacchinario,
            "allineamento": info[self.chiavi[0]],
            "vibrazioni": info[self.chiavi[1]],
            "rotazione": info[self.chiavi[2]],
            "lubrificante": info[self.chiavi[3]],
            "potenza": info[self.chiavi[4]],
            "timestamp": timestamp
        }

        # Effettua la richiesta POST

        if (int(info.get('Potenza_tornio') != 0)):
            requests.post("http://localhost:8080/api/info/lathes/", json=payload_lathe)

        self.check(info)


    def check(self, info):

        if (int(info.get('Potenza_tornio') != 0)):

            if (float(info.get('Allineamento_tornio') < 0.025 or float(info.get('Allineamento_tornio') > 0.05))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": self.idMacchinario,
                    "tipologia": "Allineamento fuori range ottimale [0.025-0,05] mm",
                    "valore": float(info.get('Allineamento_tornio')),
                    "range": "0.025-0,05",
                    "timestamp": timestamp
                }


                self.sendAlarm("Vibrazioni tornio fuori range, allontanarsi", self.topic, payload_alarm)

            if (float(info.get('Vibrazioni_tornio') < 0.1 or float(info.get('Vibrazioni_tornio') > 2))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": self.idMacchinario,
                    "tipologia": "Vibrazioni fuori range ottimale [0.1-2.0] mm/s",
                    "valore": float(info.get('Vibrazioni_tornio')),
                    "range": "0.1-2.0",
                    "timestamp": timestamp
                }

                self.sendAlarm("Vibrazioni tornio fuori range, allontanarsi", self.topic, payload_alarm)

            if (int(info.get('Rotazione_tornio') < 1500 or int(info.get('Rotazione_tornio') > 1600))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": self.idMacchinario,
                    "tipologia": "Velocità di rotazione fuori range ottimale [1500-1600] giri/minuto",
                    "valore": int(info.get('Rotazione_tornio')),
                    "range": "1500-1600",
                    "timestamp": timestamp
                }

                self.sendAlarm("Velocità tornio fuori range, allontanarsi", self.topic, payload_alarm)

            if (int(info.get('Potenza_tornio') < 7000 or int(info.get('Potenza_tornio') > 7500))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": self.idMacchinario,
                    "tipologia": "Potenza della macchina fuori range ottimale [7000-7500] W",
                    "valore": int(info.get('Potenza_tornio')),
                    "range": "7000-7500",
                    "timestamp": timestamp
                }

                self.sendAlarm("Potenza tornio fuori range, allontanarsi", self.topic, payload_alarm)

            if (int(info.get('Lubrificante_tornio') < 30)):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": self.idMacchinario,
                    "tipologia": "Livello del lubrificante sotto al 30%",
                    "valore": int(info.get('Lubrificante_tornio')),
                    "range": "> 30",
                    "timestamp": timestamp
                }

                self.sendAlarm("Lubrificante tornio fuori range, allontanarsi", self.topic, payload_alarm)



    def sendAlarm(self, message, topic, payload):


        # Configurazione del client MQTT
        client = mqtt.Client()
        # Connessione al broker MQTT
        client.connect("test.mosquitto.org", 1883, 60)
        # Invio di un messaggio MQTT
        client.publish(self.topic, message)
        


        requests.post("http://localhost:8080/api/alarms/", json=payload)
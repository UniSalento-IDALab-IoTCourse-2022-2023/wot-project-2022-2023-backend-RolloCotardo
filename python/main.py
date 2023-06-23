import json
from datetime import datetime

import requests
from pyModbusTCP.server import ModbusServer, DataBank
from time import sleep, time
import paho.mqtt.client as mqtt

server = ModbusServer("0.0.0.0", 12345, no_block=True)

#try:
print("Start server...")
server.start()
print("Server is online")
state = [0]
dizionario={}

try:

    while True:

        # gli holding register dall'1 al 6 sono per i valori della sega a nastro, dal 7 all'11 per il tornio
        state = server.data_bank.get_holding_registers(1, 12)
        chiavi = ['Tensione_sega', 'Allineamento_sega', 'Avanzamento_sega', 'Rotazione_sega', 'Lubrificante_sega', 'Potenza_sega', 'Allineamento_tornio', 'Vibrazioni_tornio', 'Rotazione_tornio', 'Lubrificante_tornio', 'Potenza_tornio']

        state[1] = state[1]/1000
        state[6] = state[6]/1000
        state[7] = state[7]/100

        for i in range(11):
            dizionario[chiavi[i]] = state[i]


        ### SEGA ###

        # Dati da inviare nella richiesta POST

        payload_saw = {
            "tensione": state[0],
            "allineamento": state[1],
            "avanzamento": state[2],
            "rotazione": state[3],
            "lubrificante": state[4],
            "potenza": state[5]
        }

        # Effettua la richiesta POST

        if(int(dizionario.get('Potenza_sega') != 0)):

            response = requests.post("http://localhost:8080/api/info/saws", json=payload_saw)


            if(int(dizionario.get('Tensione_sega') < 1400 or int(dizionario.get('Tensione_sega') > 1500))):

                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": "Sega1",
                    "tipologia": "Tensione fuori range ottimale [1400-1500] psi",
                    "valore": int(dizionario.get('Tensione_sega')),
                    "range": "1400-1500",
                    "timestamp": timestamp
                }

                '''

                # Configurazione del client MQTT
                client = mqtt.Client()
                # Connessione al broker MQTT
                client.connect("192.168.1.25", 1883, 60)
                # Invio di un messaggio MQTT
                topic = "topic/allarme/sega/1"
                message = "Tensione sega fuori range, allontanarsi"
                client.publish(topic, message)
                '''

                requests.post("http://localhost:8080/api/alarms", json=payload_alarm)



            if (float(dizionario.get('Allineamento_sega') < 0.025 or float(dizionario.get('Allineamento_sega') > 0.05))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": "Sega1",
                    "tipologia": "Allineamento lama fuori range ottimale [0.025-0.05] mm",
                    "valore": float(dizionario.get('Allineamento_sega')),
                    "range": "0.025-0.05",
                    "timestamp": timestamp
                }

                '''
                # Configurazione del client MQTT
                client = mqtt.Client()
                # Connessione al broker MQTT
                client.connect("192.168.1.25", 1883, 60)
                # Invio di un messaggio MQTT
                topic = "topic/allarme/sega/1"
                message = "Allineamento sega fuori range, allontanarsi"
                client.publish(topic, message)
                '''


                requests.post("http://localhost:8080/api/alarms", json=payload_alarm)



            if (int(dizionario.get('Avanzamento_sega') < 50 or int(dizionario.get('Avanzamento_sega') > 60))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": "Sega1",
                    "tipologia": "Velocità della lama fuori range ottimale [50-60] m/minuto",
                    "valore": int(dizionario.get('Avanzamento_sega')),
                    "range": "50-60",
                    "timestamp": timestamp
                }

                '''
                # Configurazione del client MQTT
                client = mqtt.Client()
                # Connessione al broker MQTT
                client.connect("192.168.1.25", 1883, 60)
                # Invio di un messaggio MQTT
                topic = "topic/allarme/sega/1"
                message = "Velocità avanzamento sega fuori range, allontanarsi"
                client.publish(topic, message)
                '''

                requests.post("http://localhost:8080/api/alarms", json=payload_alarm)




            if (int(dizionario.get('Rotazione_sega') < 300 or int(dizionario.get('Rotazione_sega') > 400))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": "Sega1",
                    "tipologia": "Velocità di rotazione della lama fuori range ottimale [300-400] m/minuto",
                    "valore": int(dizionario.get('Rotazione_sega')),
                    "range": "300-400",
                    "timestamp": timestamp
                }

                '''

                # Configurazione del client MQTT
                client = mqtt.Client()
                # Connessione al broker MQTT
                client.connect("192.168.1.25", 1883, 60)
                # Invio di un messaggio MQTT
                topic = "topic/allarme/sega/1"
                message = "Velocità rotazione sega fuori range, allontanarsi"
                client.publish(topic, message)
                
                '''

                requests.post("http://localhost:8080/api/alarms", json=payload_alarm)



            if (int(dizionario.get('Lubrificante_sega') < 30)):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": "Sega1",
                    "tipologia": "Livello del lubrificante sotto al 30%",
                    "valore": int(dizionario.get('Lubrificante_sega')),
                    "range": "> 30",
                    "timestamp": timestamp
                }

                '''

                # Configurazione del client MQTT
                client = mqtt.Client()
                # Connessione al broker MQTT
                client.connect("192.168.1.25", 1883, 60)
                # Invio di un messaggio MQTT
                topic = "topic/allarme/sega/1"
                message = "Lubrificante sega fuori range, allontanarsi"
                client.publish(topic, message)
                '''

                requests.post("http://localhost:8080/api/alarms", json=payload_alarm)



            if (int(dizionario.get('Potenza_sega') < 750 or int(dizionario.get('Potenza_sega') > 975))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": "Sega1",
                    "tipologia": "Potenza della macchina furi range ottimale [750-975] W",
                    "valore": int(dizionario.get('Potenza_sega')),
                    "range": "750-975",
                    "timestamp": timestamp
                }

                '''

                # Configurazione del client MQTT
                client = mqtt.Client()
                # Connessione al broker MQTT
                client.connect("192.168.1.25", 1883, 60)
                # Invio di un messaggio MQTT
                topic = "topic/allarme/sega/1"
                message = "Potenza sega fuori range, allontanarsi"
                client.publish(topic, message)
                
                '''

                requests.post("http://localhost:8080/api/alarms", json=payload_alarm)



        ### TORNIO ###

        # Dati da inviare nella richiesta POST

        payload_lathe = {
            "allineamento": state[6],
            "vibrazioni": state[7],
            "rotazione": state[8],
            "lubrificante": state[9],
            "potenza": state[10]
        }

        # Effettua la richiesta POST

        if (int(dizionario.get('Potenza_tornio') != 0)):

            requests.post("http://localhost:8080/api/info/lathes", json=payload_lathe)

            if (float(dizionario.get('Allineamento_tornio') < 0.025 or float(dizionario.get('Allineamento_tornio') > 0.05))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": "Tornio1",
                    "tipologia": "Allineamento fuori range ottimale [0.025-0,05] mm",
                    "valore": float(dizionario.get('Allineamento_tornio')),
                    "range": "0.025-0,05",
                    "timestamp": timestamp
                }

                '''
                # Configurazione del client MQTT
                client = mqtt.Client()
                # Connessione al broker MQTT
                client.connect("192.168.1.25", 1883, 60)
                # Invio di un messaggio MQTT
                topic = "topic/allarme/tornio/1"
                message = "Allineamento tornio fuori range, allontanarsi"
                client.publish(topic, message)
                
                '''

                requests.post("http://localhost:8080/api/alarms", json=payload_alarm)



            if (float(dizionario.get('Vibrazioni_tornio') < 0.1 or float(dizionario.get('Vibrazioni_tornio') > 2))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": "Tornio1",
                    "tipologia": "Vibrazioni fuori range ottimale [0.1-2.0] mm/s",
                    "valore": float(dizionario.get('Vibrazioni_tornio')),
                    "range": "0.1-2.0",
                    "timestamp": timestamp
                }

                '''

                # Configurazione del client MQTT
                client = mqtt.Client()
                # Connessione al broker MQTT
                client.connect("192.168.1.25", 1883, 60)
                # Invio di un messaggio MQTT
                topic = "topic/allarme/tornio/1"
                message = "Vibrazioni tornio fuori range, allontanarsi"
                client.publish(topic, message)
                
                '''

                requests.post("http://localhost:8080/api/alarms", json=payload_alarm)



            if (int(dizionario.get('Rotazione_tornio') < 1500 or int(dizionario.get('Rotazione_tornio') > 1600))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": "Tornio1",
                    "tipologia": "Velocità di rotazione fuori range ottimale [1500-1600] giri/minuto",
                    "valore": int(dizionario.get('Rotazione_tornio')),
                    "range": "1500-1600",
                    "timestamp": timestamp
                }

                '''

                # Configurazione del client MQTT
                client = mqtt.Client()
                # Connessione al broker MQTT
                client.connect("192.168.1.25", 1883, 60)
                # Invio di un messaggio MQTT
                topic = "topic/allarme/tornio/1"
                message = "Velocità tornio fuori range, allontanarsi"
                client.publish(topic, message)
                
                '''

                requests.post("http://localhost:8080/api/alarms", json=payload_alarm)



            if (int(dizionario.get('Potenza_tornio') < 7000 or int(dizionario.get('Potenza_tornio') > 7500))):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": "Tornio1",
                    "tipologia": "Potenza della macchina fuori range ottimale [7000-7500] W",
                    "valore": int(dizionario.get('Potenza_tornio')),
                    "range": "7000-7500",
                    "timestamp": timestamp
                }


                '''

                # Configurazione del client MQTT
                client = mqtt.Client()
                # Connessione al broker MQTT
                client.connect("192.168.1.25", 1883, 60)
                # Invio di un messaggio MQTT
                topic = "topic/allarme/tornio/1"
                message = "Potenza tornio fuori range, allontanarsi"
                client.publish(topic, message)
                
                '''

                requests.post("http://localhost:8080/api/alarms", json=payload_alarm)



            if (int(dizionario.get('Lubrificante_tornio') < 30)):
                timestamp = str(datetime.now())

                payload_alarm = {
                    "idMacchinario": "Tornio1",
                    "tipologia": "Livello del lubrificante sotto al 30%",
                    "valore": int(dizionario.get('Lubrificante_tornio')),
                    "range": "> 30",
                    "timestamp": timestamp
                }

                '''

                # Configurazione del client MQTT
                client = mqtt.Client()
                # Connessione al broker MQTT
                client.connect("192.168.1.25", 1883, 60)
                # Invio di un messaggio MQTT
                topic = "topic/allarme/tornio/1"
                message = "Lubrificante tornio fuori range, allontanarsi"
                client.publish(topic, message)
                
                '''

                requests.post("http://localhost:8080/api/alarms", json=payload_alarm)

        sleep(5)

except:
    print("Shutdown")
    server.stop()
    print("Server is offline")




# Smart Headphones for Safety

## Descrizione del progetto
Questa è un repository appartenente al progetto "Smart Headphones for Safety", ossia un progetto dell'Università del Salento del corso di Internet of Thing nato per tutelare la sicurezza di lavoratori in ambienti di lavoro rumorosi. Sono infatti numerosissimi i casi di operatori che subiscono danni permanenti all'udito a causa di una mancata protezione del loro apparato uditivo, in fabbriche e industrie rumorose. In questo progetto si è sviluppato un sistema di cuffie smart che non solo permettono all'operatore di isolarsi dai rumori esterni, ma grazie all'impiego di una applicazione Android permettono anche la ricezione di notifiche di allarme da parte di macchinari potenzialmente pericolosi, venendo quindi avvertiti in tempo e potendo quindi allontanarsi a una distanza di sicurezza dal pericolo. Il vantaggio di un progetto di questo tipo è che non solo permette un isolamento dai rumori esterni, ma evita anche quelle complicazioni dovute a un isolamento completo, come operatori che non si accorgono di essere troppo vicini ai macchinari, che possono quindi incorrere in danni fisici anche permanenti nei peggiori dei casi.

## Architettura del sistema
La soluzione proposta per affrontare e risolvere le problematiche precedentemente descritte prevede l’utilizzo e l’implementazione della seguente architettura:

### Macchinari
Il sistema prevede l’utilizzo di due Raspberry che simulano due macchinari presenti nel contesto di un’industria di lavorazione del legno: una sega a nastro e un tornio. La simulazione dei due macchinari avviene tramite script Python nel quale si generano in maniera pseudocasuale le misure relative ai parametri caratteristici del macchinario e si inoltrano al backend. L’inoltro dei dati avviene tramite protocollo TCP Modbus (libreria PyModbus) sfruttando 11 Holding Registers.

### Backend
Il backend è composto da tre componenti principali: un’applicazione Python, un’applicazione SpringBoot e un database Mongo.

* L’applicazione Python utilizza la libreria PyModbus per ricevere i dati dai macchinari, li processa e in caso di valori fuori range invia l’allarme al dispositivo mobile tramite protocollo MQTT. L’applicazione effettua anche delle richieste POST (con i dati dei macchinari e con gli allarmi) all’applicazione SpringBoot che si occuperà di salvarli nel database.
* L’applicazione SpringBoot ha quindi il compito di interfacciarsi con il database Mongo per la lettura e la scrittura dei dati e fornisce queste funzionalità anche agli altri componenti del sistema tramite la definizione di API Rest, alcune delle quali hanno la necessità di autenticazione tramite token JWT per poter essere richiamate.
* Il database Mongo contiene cinque collezioni, una per i dati dei macchinari, una per gli allarmi, una per le macchine e una per gli admin

### Mobile App
L’applicazione Android, predisposta già all’avvio per la ricezione di messaggi MQTT, svolge un compito molto importante: il subscribe dinamico ai topic relativi ai macchinari nelle vicinanze. Una volta avviata l’applicazione, viene costantemente eseguita la scansione dei dispositivi Bluetooth nella zona e viene letto l’indirizzo MAC del dispositivo rilevato e il valore di RSSI al fine di analizzare la distanza da quest’ultimo. L’applicazione, conoscendo l’indirizzo MAC dei beacon e i topic ad essi associati, può effettuare il subscribe o l’unsubscribe in maniera del tutto dinamica. Una volta ricevuto un messaggio di allarme, un sintetizzatore vocale riprodurrà il testo ricevuto. Le cuffie antirumore possono essere collegate tramite Bluetooth allo smartphone e in questo modo l’operaio verrà avvisato tempestivamente del malfunzionamento del macchiario.

### Frontend
L’amministratore del sistema può accedere tramite login alla dashboard, ovvero una web application in Angular che racchiude lo storico dei dati ricevuti dai macchinari e gli eventuali allarmi. La dashboard interrogherà il backend tramite delle richieste GET per ottenere i dati. È possibile visualizzare dei grafici, leggere le misure dei parametri dei macchinari in tempo reale e analizzare tutti gli allarmi ricevuti filtrandoli in base alle esigenze.

## Link alle altre componenti
* Applicazione Android: https://github.com/UniSalento-IDALab-IoTCourse-2022-2023/wot-project-2022-2023-AndroidApplication-RolloCotardo
* Python che simula sega a nastro: https://github.com/UniSalento-IDALab-IoTCourse-2022-2023/wot-project-2022-2023-raspberryS-RolloCotardo
* Python che simula tornio: https://github.com/UniSalento-IDALab-IoTCourse-2022-2023/wot-project-2022-2023-raspberryT-RolloCotardo
* Frontend: https://github.com/UniSalento-IDALab-IoTCourse-2022-2023/wot-project-2022-2023-Frontend-RolloCotardo
* Link alla presentazione del progetto: https://github.com/UniSalento-IDALab-IoTCourse-2022-2023/wot-project-presentation-RolloCotardo

## Cosa c'è nella presente repository
In questa repository è presente il Backend utilizzato dal sistema. 
Esso è composto da due parti principali: 
* **Applicazione Python** che si occuperà della ricezione dei dati Modbud dai macchinari. Questa applicazione effettuerà dei controlli ulteriori sui dati e vedrà quali sono i dati che escono fuori range, generando quindi gli allarmi corrispondenti. Questi dati verranno mandati tramite MQTT al Broker MQTT installato in locale (Mosquitto MQTT) che smisterà i messaggi ai vari subscriber in base ai topic. Infine questa applicazione richiama le API POST implementate in Springboot per aggiungere i dati e gli allarmi in un database. 
* **Applicazione Springboot** che implementa tutte le API, sia POST (utilizzate dall'applicazione Python per aggiungere i dati e gli allarmi al database) che le GET (utilizzate dal Frontend per ottenere i dati dal Backend ed elaborarli). Le API GET sono caratterizzate dall'annotazione @Preauthorize che permetterà loro di essere richiamate solo da un amministratore che abbia prima effettuato l'accesso al sistema (token JWT)

Per eseguire queste due componenti è sufficiente: 
1. clonare la presente repository
2. aprire il progetto nella cartella "springboot" con un IDE come IntellijIdea e buildare usando il modulo bootJar
3. spostarsi nella cartella "springboot" da terminale e lanciare il seguente comando:
```
docker compose up
```
aprendo docker noteremo che saranno up sia il container di Springboot che il container di Mongo
4. modificare l'indirizzo IP nel codice python con l'IP del proprio Broker MQTT
5. spostarsi nella cartella "python" da terminale e lanciare i seguenti comandi:
```
pip install pyModbusTCP
pip install requests
pip install paho-mqtt
python main.py
```

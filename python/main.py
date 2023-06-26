from pyModbusTCP.server import ModbusServer
from time import sleep

import saw
import lathe

server = ModbusServer("0.0.0.0", 12345, no_block=True)

info_saw={}
info_lathe={}

chiavi = ['Tensione_sega', 'Allineamento_sega', 'Avanzamento_sega', 'Rotazione_sega', 'Lubrificante_sega', 'Potenza_sega',
          'Allineamento_tornio', 'Vibrazioni_tornio', 'Rotazione_tornio', 'Lubrificante_tornio', 'Potenza_tornio']

try:
    print("Start server...")
    server.start()
    print("Server is online")
    state = [0]

    while True:

        # gli holding register dall'1 al 6 sono per i valori della sega a nastro, dal 7 all'11 per il tornio
        state = server.data_bank.get_holding_registers(1, 12)


        state[1] = state[1]/1000
        state[6] = state[6]/1000
        state[7] = state[7]/100


        for i in range(6):
            info_saw[chiavi[i]] = state[i]

        for i in range(6, 11):
            info_lathe[chiavi[i]] = state[i]


        print(info_saw)
        print(info_lathe)


        sega = saw.Saw(info_saw, "Sega1", "topic/saw/1")
        tornio = lathe.Lathe(info_lathe, "Tornio1","topic/lathe/1")

        sleep(5)

except:
    print("Shutdown")
    server.stop()
    print("Server is offline")



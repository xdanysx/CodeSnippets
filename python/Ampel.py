from gpiozero import LED, Button
from signal import pause
from time import sleep

# LED Flow:
# Anode (long lead)-> Cathode (short lead)
# Rot 1,8V; Gelb 2,0V; Grün 2,2V; Blau: 3,6V 

# Variables
# LED's
button = Button(2)
green_led_car = LED(18)
yellow_led_car = LED(17)
red_led_car = LED(27)
green_led_ped = LED(22)
red_led_ped = LED(23)

# Timings
greentime = 10
yellowtime = 3
redtime = 10
      
# Functions
def Ampel(state):
    if(state):
        # Pedestrians Side
        red_led_ped.on()
        green_led_ped.off()
        sleep(yellowtime+2)
        # Car Side
        yellow_led_car.on()
        sleep(yellowtime)
        yellow_led_car.off()
        red_led_car.off()
        green_led_car.on()
    else:
        # Car Side
        green_led_car.off()
        yellow_led_car.on()
        sleep(yellowtime)
        yellow_led_car.off()
        red_led_car.on()
        sleep(yellowtime+2)
        # Pedestrian Side
        green_led_ped.on()
        red_led_ped.off()
        sleep(redtime) # Bis Zeit vorbei ist

# Programm:
print("Programm gestartet")
# Loop, bleibt solange grün bis Fusgänger knopf betätigt
while True:
    Ampel(True) # Starte Start Sequenz
    button.wait_for_press() # Wenn Knopf gedrückt
    Ampel(False) # Starte Stop Sequenz
pause()
print("Programm gestoppt")
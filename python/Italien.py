from time import sleep

temperatur = 0 # in Grad
luftfeuchtigkeit = 0 # in Prozent
lueftungszeit = 300 # in Sekunden, gibt an wie lange gelüftet werden soll
abkuehlzeit = 300 # in Sekunden, gibt an wie lange pause nach lüften gemacht werden soll
kontrollzeit = 1800 # in Sekunden
kannStarten = True # Zustand des Lüfters, startet erst wenn abkuehlzeit abgewartet wurde

# Wenn Hauptschalter auf an:
#     Luftungssystem(True)
# Wenn Hauptschalter auf aus:
#     Luftungssystem(False)

def Lueftungssystem(zustand):
    while(zustand):
        CheckeUmgebung()
        sleep(kontrollzeit)

def CheckeUmgebung():
    # Ermittle Daten
    # Wenn Luftfeuchtigkeit zu hoch/niedrig => Lüfte
    if(luftfeuchtigkeit > 60%):
        AktiviereLuefter()
    # return Wahr oder Falsch je nach dem ob gelüftet werden soll

def AktiviereLuefter():
    if(kannStarten)
        kannStarten = False
        # Aktiviere GPIO pin für Relais vom Lüfter
        sleep(lueftungszeit) # Wartet Lüftungszeit ab
        # Deaktiviere GPIO Pin für Relais vom Lüfter
        sleep(abkuehlzeit) # Wartet Abkühlzeit ab
        kannStarten = True # Kann wieder genutzt werden
        
def Momentanwert():
    return "Derzeitige Werte: Temperatur " + temperatur + " Grad, Luftfeuchtigkeit " + luftfeuchtigkeit + "%"
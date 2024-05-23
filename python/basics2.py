import random

"""
Hier habe ich ein Wenig mit funktionen, arrays und random gespielt.
Dieses Skript soll lediglich Karten aus einem Deck an Spieler verteilen
"""

# Variablen
farben = ["Herz", "Karo", "Pik", "Kreuz"] # Liste mit Farben
zahlen = [1,2,3,4,5,6,7,8,9,10,11,12,13] # Liste mit Zahlen
zahlen2 = range(1,14) # geht auch und das gleiche wie "zahlen"

liste_kartendeck = [] # Kartendeck-Liste (Anfangs noch leer)
liste_spieler = [] # Spieler-Liste (Anfangs noch leer)

anzahl_spieler = 2
anzahl_karten_pro_spieler = 5

# Kartendeck auffüllen
def KartendeckAuffuellen():
    for farbe in farben:
        for zahl in zahlen:
            karte = (farbe, zahl)
            liste_kartendeck.append(karte)

# Karten aus deck ziehen
def ziehe_karte(deck):
    # Zufälligen Index auswählen
    index = random.randint(0, len(deck) - 1)
    # Karte am ausgewählten Index entfernen und zurückgeben
    return deck.pop(index)

# Teilnehmer initialisieren
def TeilnehmerInitialisieren():
    for index in range(anzahl_spieler):
        # Kreiere spieler mit Namen und Handkarten
        spieler = {"Name": f"Spieler {index + 1}", "Handkarten": []}
        # Spieler soll anzahl_karten_pro_spieler in seine Hand vom kartendeck ziehen
        for _ in range(anzahl_karten_pro_spieler):
            spieler["Handkarten"].append(ziehe_karte(liste_kartendeck))
        liste_spieler.append(spieler)

# Teilnehmer karten Verraten
def KartenVerraten():
    for spieler in liste_spieler:
        print(f"{spieler["Name"]} hat folgende Karten:")
        for karte in spieler["Handkarten"]:
            print(f"{karte[0]} {karte[1]}")
        print()  # Leerzeile zur Trennung der Spieler

# Kartendeck ausgeben
def DeckAusgeben():
    for karte in liste_kartendeck:
        print(karte)

# Funktionen ausführen
KartendeckAuffuellen()
TeilnehmerInitialisieren()
KartenVerraten()




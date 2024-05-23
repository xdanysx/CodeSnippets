# Import 
import numpy as np
import pandas as pd
import seaborn as sns
from matplotlib import pyplot as plt

# Variablen 
zahl_int = 2
zahl_float = 2.5
zahl_meinAlter = 25
wort_text = "Hallo wie gehts dir?"
bool_aussage = False
list_zeichen = ['a','b','c']
list_namen = ["Hallo", "das", "ist", "eine", "Liste"]
list_listeInListe = [["Namen",["Daniel","Emily","Hermann"]],["Alter",[25,24,55]]]

# Funktionen
def addiere(zahl1, zahl2):
    return zahl1 + zahl2

def subtrahiere(zahl1, zahl2):
    return zahl1 - zahl2

def dividiere(z1,z2):
    return z1/z2

def sageLaengeText(text):
    return len(text)

print("Dividiere: " + dividiere(zahl_int,zahl_float))
print("Länge des Textes: " + sageLaengeText(wort_text))
print("")

# If, else
if(not bool_aussage):
    print("ist wahr")
    print("")
else:
    print("nicht wahr")
    print("")

# If, else if, else
if(zahl_meinAlter < 20):
    print("Zu jung")
    print("")
elif(zahl_meinAlter > 60):
    print("Zu alt")
    print("")
else:
    print("Ihr Alter ist im grünen bereich")
    print("")


# Index'
print("Index: ")
print("1 " + wort_text[6:])
print("2 " + wort_text[:12])
print("3 " + wort_text[8:16])
print("4 " + wort_text[-10:])
print("")

# Listen
print("Listen: ")
print(list_zeichen)
print(list_zeichen + list_namen)
print(list_namen + list_zeichen)
print("")

# Verzweigte Listen
print("Verzweigte Listen: ")
print(list_listeInListe)
print(list_listeInListe[0][1])
print(list_listeInListe[1])
print("")


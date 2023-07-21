#include <stdio.h>

int main(){

  int zahlEingabe;
  float summe = 0;

// ======= Printf von der Zwischensumme

void zwischenSumme(){
  printf("Zwischensumme: %.2f Euro \n",summe );
  }

// ======= Printf von dem Menü

void menuAnzeige(){
  printf("1: Döner 4,00 Euro\n");
  printf("2: Lahmacun 5,00 Euro\n");
  printf("3: Pommes 2,50 Euro\n");
  printf("4: Pizza 6,50 Euro\n");
}

// Programmablauf in einer Do-While Schleife

do {
  printf("Was wurde bestellt? (0: Menü anzeigen, -1: Eingabe beenden)\n");
  scanf("%d",&zahlEingabe);
  switch (zahlEingabe) {
    case 0: menuAnzeige(); break; // Menü anzeigen
    case 1: summe +=   4; break; // der summe +4€ addieren
    case 2: summe +=   5; break; // der summe +5€ addieren
    case 3: summe += 2.5f; break; // der summe +2,50€ addieren
    case 4: summe += 6.5f; break; // der summe +6,50€ addieren
    case -1: break;
    default: printf("Ungültige Eingabe\n");
    }
    zwischenSumme(); //Zwischensumme anzeigen
  }while(zahlEingabe != -1);
      printf("Gesamt: %.2f Euro\n", summe); // Gesamtsumme anzeigen
      summe = 0; // Summe Zurücksetzen
}

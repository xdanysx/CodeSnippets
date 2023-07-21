// Das Programm erzeugt ein Palindrom bsp: von "Daniel" zu DanielleinaD

#include <stdio.h>

//Diese Funktion gibt die Laenge eines chars als int wieder
int laengeWortFunktion(char s[]) {
   int i=0;
   while (s[i] != '\0')i++;
   return i;
}

//Diese Funktion gibt ein Palindrome
void palindrome(char beispielSatz[]){
  int laengeWort = laengeWortFunktion(beispielSatz);
  for (int i = 0; i < laengeWort; i++) { // Vorwaerts Zaehlen
    printf("%c",beispielSatz[i]); 
  }
  for (int i = laengeWort; i >= 0; i--) { // Rueckwaerts Zaehlen
    printf("%c",beispielSatz[i]); 
  }
}

//======= Main Funktion =======
int main(){
  char satz[100];
  printf("Bitte Satz eingeben: ");
  scanf("%[^\n]%*c", &satz[0]);
  printf("Das Palindrome dazu: ");
  palindrome(satz);
}

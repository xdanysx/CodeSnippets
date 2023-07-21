// Thema: Wahrheitstabelle

#include <stdio.h>

int main(void) {

  // ======= Fälle =======
  int a;
  int b;
  int c;
  int d;

  // ======= Verknüfungen =======
  int e;
  int g;
  int h;
  int i;
  int j;
  int f;

  // ======= Programm =======

  // Hier wird nur der obere Teil der Tabelle ausgegeben
  printf("F ≡ (A → (B ∨ C)) ↔ ((¬D ∧ A ∧ C) ∨ (B ↔ D)): \n\n");
  printf(" | A | B | C | D || E | G | H | I | J | F | \n");
  printf("-+---+---+---+---++---+---+---+---+---+---+-\n");
  // Hier wird der rest der Tabelle ausgegeben:
  do {
    // Hier werden erstmal die Zahlen a b c d von 0000 bis 1111 gezählt
    for (a = 0; a <= 1; a++) {
      for (b = 0; b <= 1; b++) {
        for (c = 0; c <= 1; c++) {
          for (d = 0; d <= 1; d++) {

            // Hier werden die unterschiedlichen Fälle  berechnet
            e = b && c;
            g = !a || e;
            h = !d && a && c;
            i = b == d;
            j = h || i;
            f = g == j;
            // Hier werden anschließend alle Werte ausgeben
            printf(" | %d | %d | %d | %d || %d | %d | %d | %d | %d | %d | \n",a,b,c,d,e,g,h,i,j,f);
          }
        }
      }
    }
  } while(a==0 || b==0 || c==0 || d==0); // soll laufen solange einer der Werte a-d eine 0 hat, also bis 1111
}

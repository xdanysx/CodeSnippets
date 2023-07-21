/**************************************/
/* Daniel Spinella                    */
/* Gruppe: Di14, Leiter: M, Hallberg  */
/* Uebungsblatt: 09, Aufgabe: B-16    */
/* Thema: Bisektion                   */
/* Version: 1.0                       */
/* Datum: 18.06.2022                  */
/* Status: lauffaehig                 */
/**************************************/
#include <stdio.h> // Bei Printf/scanf
#include <math.h> // Bei sin(x)

// ======= Funktion =======
double function(double x){
  // Hier kann man die Funktion schreiben:
  return ((x*x*x)-(4*x*x)+(6*x)-(25));
  //return sin(x);
}

// ======= Bisektion =======
void bisect(double L, double R, double TOL){
  if(L<R){//Linke Grenze muss groeßer sein als Rechte
    if((function(L) * function(R)) <= 0){ // Wenn Bedingung f(L)*f(R)≤0 erfüllt ist:
      if(R-L<TOL){//Wenn Bedingung R-L<TOL erfüllt ist
        printf("Eine Nullstelle von f liegt in [ %.6e, %.6e ]\n",L,R);
        double mitte = ((L+R)/2);
        printf("f( (L + R)/2 ) = %.6e \n", function(mitte));
      }else{
        bisect(L,((L+R)/2),TOL);
        bisect(((L+R)/2),R,TOL);
      }
    }
  }else{
    printf("L muss kleiner als R sein!\n");
  }
}

// ======= Main =======
int main(void) {
  double eingabeL;
  double eingabeR;
  double eingabeTOL;

  printf("Geben Sie nun L ein: \n");
  scanf(" %lf", &eingabeL);
  printf("Geben Sie nun R ein: \n");
  scanf(" %lf", &eingabeR);
  printf("Geben Sie nun TOL ein: \n");
  scanf(" %lf", &eingabeTOL);
  bisect(eingabeL,eingabeR,eingabeTOL);
}

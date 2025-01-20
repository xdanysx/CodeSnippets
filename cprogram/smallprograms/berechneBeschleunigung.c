#include <stdio.h>

// Das Programm berechnet die Beschleunigung eines Fahrzeugs auf zweierlei weisen,
// einmal mithilfe der Geschwindigkeit und der Zeit und einmal mit der Zeit und der Strecke
// beide Werden verglichen und ein Fazit wird ausgegeben

void berechneWerte(float v, float t, float s){
    float v_ms = v/3.6f;
    
    float a1 = (v_ms/t); // Rechnung 1
    float a2 = (s*2)/(t*t); // Rechnung 2
     
    float a_d = (a1 + a2) / 2; // Durchschnitsswert berechnen
    float a_f = a1 - a2;
    if(a_f < 0) a_f = a_f * (-1.0f);
    
    float precision = 100.0f-(a_f/a_d)*100.0f;
    
    printf("Das Fahrzeug hat die Maximalgeschwindigkeit von %.2f km/h in %.2fs erreicht. Wenn das Fahrzeug bis dahin auch eine Strecke von %.2fm erreicht hat, dann hat es eine Beschleunigung von %.2f m/s^2. Die Genauigkeit der berechneten Beschleunigung liegt bei %.2f%% ", v,t,s,a_d,precision);
}

int main() {
    // Write C code here
    float geschwindigkeit = 20.0f;
    float strecke = 0.55f;
    float zeit = 0.2f;
    
    berechneWerte(geschwindigkeit, zeit, strecke);
    

    return 0;
}

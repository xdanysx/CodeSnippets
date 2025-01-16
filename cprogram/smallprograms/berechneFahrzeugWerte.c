// Online C compiler to run C program online
#include <stdio.h>

// Berechnet 10 Werte aus, die Sekuendlich ausgegeben werden
float berechneWert(float a){
    for(int t = 0; t < 10; t++){
        float v = a * t;
        float s = (1/2.0f) * a * (float)t * (float)t;
        printf("Nach %d Sekunden hat das Auto %f Meter hinterlegt und ist %f km/h schnell \n", t, s, v*3.6f );
    }
}

int main() {
    float gasPedal = 0.5f; // Beschreibt wie stark das GasPedal gedrückt worden ist
    float beschleunigung = 29.0f; // Gibt die beschleunigung in m/s*s an
    
    berechneWert(beschleunigung * gasPedal);
    return 0;
}


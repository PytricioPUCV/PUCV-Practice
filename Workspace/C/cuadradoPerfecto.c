#include <stdio.h>
#include <math.h>

int main() {
    int veces, numero, raiz, cuadrado, factor;

    printf("Ingresa la cantidad de números a verificar: ");
    scanf("%d", &veces);

    for (int i = 0; i < veces; i++) {
        printf("Ingresa un número: ");
        scanf("%d", &numero);

        raiz = sqrt(numero);
        cuadrado = raiz * raiz;

        if (cuadrado == numero) {
            printf("1\n");
        } else {
            factor = 1;
            while (1){
                int nuevoNumero = numero * factor;
                int nuevaRaiz = sqrt(nuevoNumero);
                if(nuevaRaiz * nuevaRaiz == nuevoNumero){
                    break;
                }
                factor++;
            }
            printf("%d\n", factor);
        }
    }

    return 0;
}
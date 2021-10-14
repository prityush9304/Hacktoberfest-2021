#include <stdio.h>

int main(void) 
{
    int n, fib = 0, aux = 0;    
    printf("Informe um numero para calcular a seq. de Fibonacci.\n ");

    scanf("%d", &n);
    while(fib <1000)//Só vai mostrar até 1000
    { 
        printf("%d-", fib);                   
        fib = fib + aux;                 
        aux = n;
        n = fib;        
    }
  return 0;
}
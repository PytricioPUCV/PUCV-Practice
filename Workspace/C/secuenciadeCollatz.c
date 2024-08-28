#include <stdio.h>

int main() {
    unsigned long long n;
    
    while(1) {
        scanf("%llu", &n);
        if (n >= 1 && n <= 100000000) {
            if (n == 1){
                printf("1");
            }
            while (n != 1) {
                if (n % 2 == 0) {
                    n = n / 2;
                    printf("%llu ", n);
                } else {
                    n = n * 3 + 1;
                    printf("%llu ", n);
                }
            }
            printf("\n");
            break;
        }
        else{
            scanf("%llu", &n);
        }
    }

    return 0;
}
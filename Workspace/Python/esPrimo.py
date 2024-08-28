n = int(input("Ingresa un número: "))
es_primo = True
if n <= 1:
    es_primo = False
for i in range(2, int(n**0.5) + 1):
    if n % i == 0:
        es_primo = False
        break
if es_primo:
    print(f"{n} es un número primo.")
else:
    print(f"{n} no es un número primo.")

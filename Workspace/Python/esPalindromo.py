s = input("Ingresa una cadena: ")
if s == s[::-1]:
    print(f'"{s}" es un palíndromo.')
else:
    print(f'"{s}" no es un palíndromo.')

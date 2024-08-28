// Personaje de TV
let nombre = "Homero Simpson";
let serie = "Los Simpson";
let edad = 40;

let personaje = {
    nombre: "Homero Simpson",
    serie: "Los Simpson",
    edad: 40
};
console.log(personaje);
console.log(personaje.edad);
console.log(personaje['nombre']);

personaje.edad = 45;

let llave = 'edad';
personaje[llave] = 50;

delete personaje.serie;
console.log(personaje);
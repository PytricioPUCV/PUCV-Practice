// Clase Departamento
class Departamento {

    private String direccion;
    private double precio;
    private int habitaciones;

    public Departamento(String direccion, double precio, int habitaciones) {
        this.direccion = direccion;
        this.precio = precio;
        this.habitaciones = habitaciones;
    }

    // Getters y Setters
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }

    // Método para mostrar información del departamento
    public void mostrarInfo() {
        System.out.println("Dirección: " + direccion);
        System.out.println("Precio: $" + precio);
        System.out.println("Habitaciones: " + habitaciones);
        System.out.println();
    }
}

// Clase Inmobiliaria
class Inmobiliaria {

    private Departamento[] departamentos;
    private int contador;

    public Inmobiliaria(int capacidad) {
        departamentos = new Departamento[capacidad];
        contador = 0;
    }

    // Método para agregar un departamento
    public void agregarDepartamento(Departamento departamento) {
        if (contador < departamentos.length) {
            departamentos[contador] = departamento;
            contador++;
        } else {
            System.out.println("No hay espacio para más departamentos.");
        }
    }

    // Método para mostrar todos los departamentos
    public void mostrarDepartamentos() {
        for (int i = 0; i < contador; i++) {
            System.out.println("Departamento #" + (i + 1));
            departamentos[i].mostrarInfo();
        }
    }

    // Método para buscar departamento por dirección
    public void buscarPorDireccion(String direccion) {
        boolean encontrado = false;
        for (int i = 0; i < contador; i++) {
            if (departamentos[i].getDireccion().equalsIgnoreCase(direccion)) {
                System.out.println("Departamento encontrado:");
                departamentos[i].mostrarInfo();
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró un departamento con esa dirección.");
        }
    }
}

// Clase principal
public class Main {

    public static void main(String[] args) {
        Inmobiliaria inmobiliaria = new Inmobiliaria(5);

        // Creando algunos departamentos
        Departamento d1 = new Departamento("Calle 1", 100000, 3);
        Departamento d2 = new Departamento("Calle 2", 120000, 2);
        Departamento d3 = new Departamento("Calle 3", 90000, 1);

        // Agregando departamentos a la inmobiliaria
        inmobiliaria.agregarDepartamento(d1);
        inmobiliaria.agregarDepartamento(d2);
        inmobiliaria.agregarDepartamento(d3);

        // Interfaz interactiva
        boolean salir = false;
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (!salir) {
            System.out.println("---- Inmobiliaria ----");
            System.out.println("1. Mostrar todos los departamentos");
            System.out.println("2. Buscar departamento por dirección");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    inmobiliaria.mostrarDepartamentos();
                    break;
                case 2:
                    System.out.print("Ingrese la dirección a buscar: ");
                    String direccion = scanner.nextLine();
                    inmobiliaria.buscarPorDireccion(direccion);
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }

        System.out.println("Saliendo...");
    }
}

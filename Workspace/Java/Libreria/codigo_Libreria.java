import java.io.*;

public class codigo_Libreria {
    public static void main(String[] args) throws IOException {
        /* Lector */
        BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
        
        /* Variables */
        String codigo_Libro;
        String titulo_Libro;
        String autor_Libro;
        int veces_prestado_Libro;
        int PLibre = 0;
        int opcion = 0;

        /* Arrays */
        String[] codigo = new String[100];
        String[] titulo = new String[100];
        String[] autor = new String[100];
        int[] veces_prestado = new int[100];

        do {
            /* Menú */
            System.out.println();
            System.out.println("Menú:");
            System.out.println("1. Ingresar un libro");
            System.out.println("2. Buscar un libro");
            System.out.println("3. Eliminar un libro");
            System.out.println("4. Mostrar todos los libros ingresados");
            System.out.println("5. Salir");
            System.out.println();
            System.out.print("Ingrese una opción: ");
            opcion = Integer.parseInt(user.readLine());

            switch (opcion) {
                case 1:
                    if (PLibre < codigo.length) {
                        /* Solicitar los datos */
                        System.out.println();
                        System.out.print("Ingrese el código del libro: ");
                        codigo_Libro = user.readLine();
                        System.out.print("Ingrese el título del libro: ");
                        titulo_Libro = user.readLine();
                        System.out.print("Ingrese el autor del libro: ");
                        autor_Libro = user.readLine();
                        System.out.print("Ingrese las veces prestado del libro: ");
                        veces_prestado_Libro = Integer.parseInt(user.readLine());

                        /* Almacenar los datos en los arreglos */
                        codigo[PLibre] = codigo_Libro;
                        titulo[PLibre] = titulo_Libro;
                        autor[PLibre] = autor_Libro;
                        veces_prestado[PLibre] = veces_prestado_Libro;

                        /* Incrementar el índice de la primera posición libre */
                        PLibre++;
                    } else {
                        System.out.println("No hay espacio disponible para ingresar más libros.");
                    }
                    break;
                case 2:
                    System.out.println();
                    System.out.println("2. Buscar un libro");
                    if (PLibre > 0) {
                        System.out.print("Ingrese el código del libro a buscar: ");
                        codigo_Libro = user.readLine();
                        boolean encontrado = false;
                        for (int i = 0; i < PLibre; i++) {
                            if (codigo_Libro.equals(codigo[i])) {
                                System.out.println();
                                System.out.println("Libro encontrado:");
                                System.out.println("Código: " + codigo[i] + ", Título: " + titulo[i] + ", Autor: " + autor[i] + ", Veces prestado: " + veces_prestado[i]);
                                System.out.println();
                                encontrado = true;
                                System.out.println("¿Te gustaría tomar prestado este libro?");
                                System.out.println("1. Sí");
                                System.out.println("2. No");
                                System.out.println();
                                System.out.print("Ingrese una opción: ");
                                int opcion_prestamo = Integer.parseInt(user.readLine());
                                if (opcion_prestamo == 1) {
                                    veces_prestado[i]++;
                                    System.out.println();
                                    System.out.println("Libro prestado.");
                                }
                                if(opcion_prestamo == 2){
                                    break;
                                }
                                break;
                            }
                        }
                        if (!encontrado) {
                            System.out.println();
                            System.out.println("No se encontró el libro.");
                        }
                    } else {
                        System.out.println("No hay libros ingresados.");
                    }
                    break;
                case 3:
                    System.out.println();
                    System.out.println("3. Eliminar un libro");
                    if (PLibre > 0) {
                        System.out.print("Ingrese el código del libro a eliminar: ");
                        codigo_Libro = user.readLine();
                        boolean encontrado = false;
                        for (int i = 0; i < PLibre; i++) {
                            if (codigo_Libro.equals(codigo[i])) {
                                for (int j = i; j < PLibre - 1; j++) {
                                    codigo[j] = codigo[j + 1];
                                    titulo[j] = titulo[j + 1];
                                    autor[j] = autor[j + 1];
                                    veces_prestado[j] = veces_prestado[j + 1];
                                }
                                PLibre--;
                                encontrado = true;
                                System.out.println();
                                System.out.println("Libro eliminado.");
                                break;
                            }
                        }
                        if (!encontrado) {
                            System.out.println();
                            System.out.println("No se encontró el libro.");
                        }
                    } else {
                        System.out.println("No hay libros ingresados.");
                    }
                    break;
                case 4: 
                    /* Mostrar los datos ingresados */
                    System.out.println();
                    System.out.println("Libros ingresados:");
                    if (PLibre == 0) {
                        System.out.println("No hay libros ingresados.");
                        break;
                    }
                    for (int i = 0; i < PLibre; i++) {
                        System.out.println("Código: " + codigo[i] + ", Título: " + titulo[i] + ", Autor: " + autor[i] + ", Veces prestado: " + veces_prestado[i]);
                    }
                    break;
                case 5:
                    System.out.println("Salir");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (opcion != 5);

    }
}
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
        String[] codigo = new String[2];
        String[] titulo = new String[2];
        String[] autor = new String[2];
        int[] veces_prestado = new int[2];

        do {
            /* Menú */
            System.out.println("Menú:");
            System.out.println("1. Ingresar un libro");
            System.out.println("2. Buscar un libro");
            System.out.println("3. Eliminar un libro");
            System.out.println("4. Salir");
            System.out.print("Ingrese una opción: ");
            opcion = Integer.parseInt(user.readLine());

            switch (opcion) {
                case 1:
                    if (PLibre < codigo.length) {
                        /*  Solicitar los datos */
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
                    System.out.println("2. Buscar un libro");
                    // Aquí puedes implementar la lógica de búsqueda
                    break;
                case 3:
                    System.out.println("3. Eliminar un libro");
                    // Aquí puedes implementar la lógica de eliminación
                    break;
                case 4:
                    System.out.println("4. Salir");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (opcion != 4);

        /* Mostrar los datos ingresados */
        System.out.println("Libros ingresados:");
        for (int i = 0; i < PLibre; i++) {
            System.out.println("Código: " + codigo[i] + ", Título: " + titulo[i] + ", Autor: " + autor[i] + ", Veces prestado: " + veces_prestado[i]);
        }
    }
}

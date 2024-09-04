import java.util.Scanner;

public class TicTacToe {

    public static final int TAMANO = 3;
    public static final char VACIO = '-';
    public static final char JUGADOR_X = 'X';
    public static final char JUGADOR_O = 'O';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean seguirJugando = true;
        int victoriasX = 0;
        int victoriasO = 0;

        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
        System.out.print("Ingresa el nombre del jugador X: ");
        String nombreX = scanner.nextLine();
        System.out.print("Ingresa el nombre del jugador O: ");
        String nombreO = scanner.nextLine();

        while (seguirJugando) {
            for (int i = 0; i < 20; i++) {
                System.out.println();
            }
            System.out.println("--------------");
            System.out.println("JUEGO DEL GATO");
            System.out.println("--------------");
            System.out.println("Instrucciones:");
            System.out.println("1. El juego es para dos jugadores: 'X' y 'O'.");
            System.out.println("2. Los jugadores se turnan para colocar su símbolo en una casilla del tablero.");
            System.out.println("3. El primer jugador en alinear tres de sus símbolos en fila, columna o diagonal gana.");
            System.out.println("4. Si todas las casillas se llenan y no hay ganador, el juego termina en empate.");
            System.out.println("\nPresiona Enter para comenzar el juego...");
            scanner.nextLine();  // Espera a que el usuario presione Enter

            char[][] tablero = new char[TAMANO][TAMANO];
            inicializarTablero(tablero);
            char jugadorActual = JUGADOR_X;
            boolean juegoTerminado = false;

            while (!juegoTerminado) {
                mostrarTablero(tablero);
                String nombreJugadorActual = (jugadorActual == JUGADOR_X) ? nombreX : nombreO;
                System.out.println("Turno de " + nombreJugadorActual + " (" + jugadorActual + ")");
                int fila, columna;

                while (true) {
                    System.out.print("Ingresa la fila (1-3): ");
                    fila = scanner.nextInt() - 1;
                    System.out.print("Ingresa la columna (1-3): ");
                    columna = scanner.nextInt() - 1;

                    if (fila >= 0 && fila < TAMANO && columna >= 0 && columna < TAMANO && tablero[fila][columna] == VACIO) {
                        tablero[fila][columna] = jugadorActual;
                        break;
                    } else {
                        System.out.println("Movimiento inválido. Intenta nuevamente.");
                    }
                }

                if (hayGanador(tablero, jugadorActual)) {
                    juegoTerminado = true;
                    mostrarTablero(tablero);
                    System.out.println("¡" + nombreJugadorActual + " (" + jugadorActual + ") ha ganado!");
                    if (jugadorActual == JUGADOR_X) {
                        victoriasX++;
                    } else {
                        victoriasO++;
                    }
                } else if (esEmpate(tablero)) {
                    juegoTerminado = true;
                    mostrarTablero(tablero);
                    System.out.println("¡Es un empate!");
                } else {
                    jugadorActual = (jugadorActual == JUGADOR_X) ? JUGADOR_O : JUGADOR_X;
                }
            }

            // Mostrar el número de victorias de cada jugador
            System.out.println("Puntuación: " + nombreX + " (" + JUGADOR_X + ") - " + victoriasX + " | " + nombreO + " (" + JUGADOR_O + ") - " + victoriasO);

            // Preguntar si quieren volver a jugar
            System.out.println("¿Deseas volver a jugar? (sí/no)");
            String respuesta = scanner.next().toLowerCase();

            seguirJugando = respuesta.equals("sí") || respuesta.equals("si") || respuesta.equals("s") || respuesta.equals("y") || respuesta.equals("yes") || respuesta.equals("1");

            if (!seguirJugando) {
                System.out.println("Gracias por jugar. ¡Hasta la próxima!");
            }
            scanner.nextLine();  // Consumir el salto de línea pendiente
        }

        scanner.close();
    }

    public static void inicializarTablero(char[][] tablero) {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                tablero[i][j] = VACIO;
            }
        }
    }

    public static void mostrarTablero(char[][] tablero) {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean hayGanador(char[][] tablero, char jugador) {
        for (int i = 0; i < TAMANO; i++) {
            if (tablero[i][0] == jugador && tablero[i][1] == jugador && tablero[i][2] == jugador) {
                return true;
            }
            if (tablero[0][i] == jugador && tablero[1][i] == jugador && tablero[2][i] == jugador) {
                return true;
            }
        }
        if (tablero[0][0] == jugador && tablero[1][1] == jugador && tablero[2][2] == jugador) {
            return true;
        }
        if (tablero[0][2] == jugador && tablero[1][1] == jugador && tablero[2][0] == jugador) {
            return true;
        }
        return false;
    }

    public static boolean esEmpate(char[][] tablero) {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                if (tablero[i][j] == VACIO) {
                    return false;
                }
            }
        }
        return true;
    }
}
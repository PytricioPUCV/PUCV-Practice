import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame implements ActionListener {

    private static final int TAMANO = 3;
    private static final char JUGADOR_X = 'X';
    private static final char JUGADOR_O = 'O';
    private static final char VACIO = '-';
    private char[][] tablero = new char[TAMANO][TAMANO];
    private JButton[][] botones = new JButton[TAMANO][TAMANO];
    private boolean turnoX = true;
    private JLabel labelEstado = new JLabel("Turno del jugador X");
    private String nombreJugadorX = "Jugador X";
    private String nombreJugadorO = "Jugador O";
    private JButton btnSalirJuego;

    public TicTacToeGUI() {
        setTitle("Juego del Gato (Tic Tac Toe)");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Personaliza el título
        JLabel titulo = new JLabel("¡Bienvenido al juego del Gato!", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.BLACK);
        add(titulo, BorderLayout.NORTH);

        // Mostrar menú inicial
        mostrarMenuInicial();
    }

    private void mostrarMenuInicial() {
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnJugar = new JButton("Jugar");
        JButton btnInstrucciones = new JButton("Cómo Jugar");
        JButton btnSalir = new JButton("Salir");

        btnJugar.setBackground(new Color(144, 238, 144)); // Verde claro
        btnInstrucciones.setBackground(new Color(173, 216, 230)); // Azul claro
        btnSalir.setBackground(new Color(240, 128, 128)); // Rojo claro

        btnJugar.addActionListener(e -> iniciarJuego());
        btnInstrucciones.addActionListener(e -> mostrarInstrucciones());
        btnSalir.addActionListener(e -> System.exit(0));

        panelMenu.add(btnJugar);
        panelMenu.add(btnInstrucciones);
        panelMenu.add(btnSalir);

        add(panelMenu, BorderLayout.CENTER);
    }

    private void iniciarJuego() {
        nombreJugadorX = JOptionPane.showInputDialog(this, "Ingrese el nombre del jugador X:", "Jugador X");
        if (nombreJugadorX == null || nombreJugadorX.trim().isEmpty()) {
            nombreJugadorX = "Jugador X";
        }
        nombreJugadorO = JOptionPane.showInputDialog(this, "Ingrese el nombre del jugador O:", "Jugador O");
        if (nombreJugadorO == null || nombreJugadorO.trim().isEmpty()) {
            nombreJugadorO = "Jugador O";
        }

        getContentPane().removeAll();
        JPanel panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(TAMANO, TAMANO));
        inicializarTablero(panelTablero);

        JPanel panelControles = new JPanel();
        panelControles.setLayout(new BorderLayout());
        btnSalirJuego = new JButton("Salir del Juego");
        btnSalirJuego.addActionListener(e -> salirJuego());

        panelControles.add(labelEstado, BorderLayout.CENTER);
        panelControles.add(btnSalirJuego, BorderLayout.EAST);

        add(panelTablero, BorderLayout.CENTER);
        add(panelControles, BorderLayout.SOUTH);

        turnoX = true;
        actualizarEstadoTurno();
        revalidate();
        repaint();
    }

    private void mostrarInstrucciones() {
        String instrucciones = """
            Instrucciones:
            1. El juego es para dos jugadores: 'X' y 'O'.
            2. Los jugadores se turnan para colocar su símbolo en una casilla del tablero.
            3. El primer jugador en alinear tres de sus símbolos en fila, columna o diagonal gana.
            4. Si todas las casillas se llenan y no hay ganador, el juego termina en empate.
            Presiona 'Jugar' para comenzar.
            """;
        JOptionPane.showMessageDialog(this, instrucciones, "Cómo Jugar", JOptionPane.INFORMATION_MESSAGE);
    }

    public void inicializarTablero(JPanel panelTablero) {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                tablero[i][j] = VACIO;
                botones[i][j] = new JButton("");
                botones[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                botones[i][j].setFocusable(false);

                // Efecto hover para las casillas vacías
                botones[i][j].setBackground(Color.WHITE);
                botones[i][j].setForeground(Color.BLACK);
                int finalI = i;
                int finalJ = j;
                botones[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        if (tablero[finalI][finalJ] == VACIO) {
                            botones[finalI][finalJ].setBackground(Color.LIGHT_GRAY);
                        }
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        if (tablero[finalI][finalJ] == VACIO) {
                            botones[finalI][finalJ].setBackground(Color.WHITE);
                        }
                    }
                });

                botones[i][j].addActionListener(this);
                panelTablero.add(botones[i][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botonPresionado = (JButton) e.getSource();
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                if (botones[i][j] == botonPresionado && tablero[i][j] == VACIO) {
                    tablero[i][j] = turnoX ? JUGADOR_X : JUGADOR_O;
                    botones[i][j].setText(String.valueOf(tablero[i][j]));

                    // Colores diferentes para cada jugador
                    botones[i][j].setForeground(turnoX ? Color.RED : Color.BLUE);

                    if (hayGanador()) {
                        resaltarPosicionesGanadoras(obtenerPosicionesGanadoras());
                        mostrarGanador(turnoX ? nombreJugadorX : nombreJugadorO);
                        return;
                    } else if (esEmpate()) {
                        mostrarEmpate();
                        return;
                    }
                    turnoX = !turnoX;
                    actualizarEstadoTurno();
                }
            }
        }
    }

    private void actualizarEstadoTurno() {
        if (turnoX) {
            labelEstado.setText("Turno de " + nombreJugadorX);
            labelEstado.setForeground(Color.RED);
        } else {
            labelEstado.setText("Turno de " + nombreJugadorO);
            labelEstado.setForeground(Color.BLUE);
        }
    }

    private boolean hayGanador() {
        for (int i = 0; i < TAMANO; i++) {
            if (tablero[i][0] != VACIO && tablero[i][0] == tablero[i][1] && tablero[i][1] == tablero[i][2]) {
                return true;
            }
            if (tablero[0][i] != VACIO && tablero[0][i] == tablero[1][i] && tablero[1][i] == tablero[2][i]) {
                return true;
            }
        }
        if (tablero[0][0] != VACIO && tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2]) {
            return true;
        }
        if (tablero[0][2] != VACIO && tablero[0][2] == tablero[1][1] && tablero[1][1] == tablero[2][0]) {
            return true;
        }
        return false;
    }

    private int[][] obtenerPosicionesGanadoras() {
        for (int i = 0; i < TAMANO; i++) {
            if (tablero[i][0] != VACIO && tablero[i][0] == tablero[i][1] && tablero[i][1] == tablero[i][2]) {
                return new int[][]{{i, 0}, {i, 1}, {i, 2}};
            }
            if (tablero[0][i] != VACIO && tablero[0][i] == tablero[1][i] && tablero[1][i] == tablero[2][i]) {
                return new int[][]{{0, i}, {1, i}, {2, i}};
            }
        }
        if (tablero[0][0] != VACIO && tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2]) {
            return new int[][]{{0, 0}, {1, 1}, {2, 2}};
        }
        if (tablero[0][2] != VACIO && tablero[0][2] == tablero[1][1] && tablero[1][1] == tablero[2][0]) {
            return new int[][]{{0, 2}, {1, 1}, {2, 0}};
        }
        return null;
    }

    private void resaltarPosicionesGanadoras(int[][] posiciones) {
        if (posiciones == null) return;
        for (int[] posicion : posiciones) {
            botones[posicion[0]][posicion[1]].setBackground(Color.GREEN);
        }
    }

    private boolean esEmpate() {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                if (tablero[i][j] == VACIO) {
                    return false;
                }
            }
        }
        return true;
    }

    private void mostrarGanador(String jugador) {
        JOptionPane.showMessageDialog(this, "¡" + jugador + " ha ganado!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        reiniciarJuego();
    }

    private void mostrarEmpate() {
        JOptionPane.showMessageDialog(this, "¡Es un empate!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        reiniciarJuego();
    }

    private void reiniciarJuego() {
        turnoX = true;
        labelEstado.setText("Turno de " + nombreJugadorX);
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                tablero[i][j] = VACIO;
                botones[i][j].setText("");
                botones[i][j].setBackground(null);
            }
        }
        actualizarEstadoTurno();
    }

    private void salirJuego() {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres salir al menú principal?", "Salir del Juego", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            getContentPane().removeAll();
            mostrarMenuInicial();
            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeGUI gui = new TicTacToeGUI();
            gui.setVisible(true);
        });
    }
}

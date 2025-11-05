import java.util.Random;

public class JuegoDeLaVida {
    private final int rows;
    private final int cols;
    private Cell[][] grid;
    private final Random rng;
    private final double spontaneousProb;

    //
    public JuegoDeLaVida(int rows, int cols, double spontaneousProb, long seed) {
        //INICIALIZAMOS LAS VARIABLES
        this.rows = rows;
        this.cols = cols;
        this.spontaneousProb = spontaneousProb;
        this.grid = new Cell[rows][cols];
        //ASIGNAMOS UN RANDOM SEGÚN LA SEMILLA SEA LONG.MIN_VALUE O NO
        this.rng = (seed == Long.MIN_VALUE) ? new Random() : new Random(seed);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c] = new Cell();
    }

    // Inicialización aleatoria con densidad de 0.3
    public void randomInit() {
        double density = 0.3;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                //INICIALIZAMOS LAS CELULAS SEGÚN COINCIDA CON LA VARIABLE DENSITY
                grid[r][c] = (rng.nextDouble() < density) ? new Cell(true, 0) : new Cell(false, 0);
    }

    // Cuenta vecinos (mundo esférico)
    private int countNeighbors(int r, int c) {
        int count = 0;
        for (int desplazamientoFila = -1; desplazamientoFila <= 1; desplazamientoFila++) {
            for (int desplazamientoColumna = -1; desplazamientoColumna <= 1; desplazamientoColumna++) {
                //no se tiene en cuenta a ella misma
                if (desplazamientoFila == 0 && desplazamientoColumna == 0) continue;
                int rr = (r + desplazamientoFila + rows) % rows;
                int cc = (c + desplazamientoColumna + cols) % cols;
                if (grid[rr][cc].alive) count++;
            }
        }
        return count;
    }

    // Aplica las reglas de evolución
    public void step() {
        Cell[][] next = new Cell[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int n = countNeighbors(r, c);
                boolean aliveNow = grid[r][c].alive;
                boolean aliveNext = false;

                if (!aliveNow && n == 3)
                    aliveNext = true; // Nace
                else if (aliveNow && (n == 2 || n == 3 || n == 4))
                    aliveNext = true; // Sobrevive

                // Evento espontáneo (10%)
                if (rng.nextDouble() < spontaneousProb)
                    aliveNext = !aliveNext;

                next[r][c] = new Cell(aliveNext, aliveNext ? grid[r][c].age + 1 : 0);
            }
        }

        grid = next;
    }

    // Muestra la matriz (0 = muerta, 1 = viva)
    public void printGrid() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                System.out.print(grid[r][c].alive ? "1 " : "0 ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

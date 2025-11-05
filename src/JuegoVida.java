public class JuegoVida {
    public static void main(String[] args) throws InterruptedException {
        //GENERAMOS LAS VARIABLES CON LAS QUE TRABAJAREMOS
        int filas = 6;
        int columnas = 6;
        int generaciones = 30;
        double probabilidadEspontanea = 0.10; // 10%

        //creamos un juegoDeLaVida para que empiece a jugar
        JuegoDeLaVida juego = new JuegoDeLaVida(filas, columnas, probabilidadEspontanea, System.currentTimeMillis());
        //lanzamos el metodo randomInit que coloca células al azar en el grid del juego
        juego.randomInit();
        //Imprimimos cada generación
        for (int i = 0; i < generaciones; i++) {
            System.out.println("Generación " + i + ":");
            juego.printGrid();
            juego.step();
            Thread.sleep(3000);
        }

        System.out.println("Simulación finalizada.");
    }
}

package lab11;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            Controlador controlador = new Controlador();
            new Vista(controlador, scanner).mostrar();
        }
    }
}

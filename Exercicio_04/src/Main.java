import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        // Exercício 04

        int[] numeros = new int[5];

        System.out.println("Digite 5 números");

        for (int i = 0; i < 5; i++) {
            System.out.print("Número " + (i + 1) + ": ");
            numeros[i] = in.nextInt();
        }

        System.out.print("Insira um número para verificar se está no array: ");
        int numeroVer = in.nextInt();


        boolean encontrado = false;
        for (int i = 0; i < 5; i++) {
            if (numeros[i] == numeroVer) {
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            System.out.println("O número " + numeroVer + " está no array.");
        } else {
            System.out.println("O número " + numeroVer + " não está no array.");
        }




    }
}
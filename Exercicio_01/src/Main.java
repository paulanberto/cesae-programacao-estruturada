import java.util.Scanner;

public class Main {

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        // Exercício 01



        int quantPares = 0;
        int quantImpares = 0;
        int somaTotal = 0;

        System.out.println("Digite 10 números");

        for (int i = 0; i < 10; i++) {
            System.out.println("Insira o " + (i + 1) + "º número: ");
            int num = in.nextInt();

            somaTotal += num;

            if (num % 2 == 0) {
                quantPares++;
            }
            else {
                quantImpares++;
            }

        }

        double media = somaTotal / 10;


        System.out.println("Quantidade de números pares é: " + quantPares);
        System.out.println("Quantidade de números ímpares é: " + quantPares);
        System.out.println("Soma de todos os números é: " + somaTotal);
        System.out.println("Média de todos os números é: " + media);
    }
}
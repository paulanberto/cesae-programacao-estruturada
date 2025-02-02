import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Exercício 01

        Scanner in = new Scanner(System.in);
        int pares = 0;
        int impares = 0;
        int soma = 0;

        System.out.println("Digite 10 números");

        for (int i = 0; i <= 10; i++) {
            System.out.println("Insira o " + (i + 1) + "º número: ");
            int num = in.nextInt();
        }

        if (num % 2 == 0) {
            System.out.println("O número " + num + " é par");
        }
        else {
            System.out.println("O número " + num + " é ímpar");
        }

        // Soma todos os números
        soma += num;


    }
}

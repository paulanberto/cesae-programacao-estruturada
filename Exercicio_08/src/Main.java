import java.util.Random;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        Random rnd = new Random();
        int cont = 0;

        int NumComp = rnd.nextInt(1, 51);

        while (true) {
            System.out.println("Selecione um número entre 1 e 50");
            int NumUtil = in.nextInt();

            if (NumUtil < 1 || NumUtil > 50) {
                System.out.println("Número invalido");

            } else if (NumUtil > NumComp) {
                System.out.println("Número errado. Tente um número mais baixo");
                cont++;

            } else if (NumUtil < NumComp) {
                System.out.println("Número errado. Tente um número mais alto");
                cont++;

            } else {
                System.out.println("Acertou!");
                System.out.println("Número correto: " + NumComp);
                System.out.println("Número de tentativas: " + (cont + 1));
                break;
            }
        }
    }
}




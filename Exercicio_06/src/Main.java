import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int valor;

        do {
            System.out.print("Digite um valor par e inferior a 100: ");
            valor = in.nextInt();

            if (valor >= 100 || valor % 2 != 0) {
                System.out.println("Valor inválido! O número deve ser par e inferior a 100.");
            }
        } while (valor >= 100 || valor % 2 != 0);


        System.out.println("Múltiplos de 5 entre 0 e " + valor + ":");
        for (int i = 0; i <= valor; i += 5) {
            System.out.println(i);
        }
    }
}
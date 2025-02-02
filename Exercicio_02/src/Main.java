import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        ArrayList<Integer> listaNum = new ArrayList<>();

        while (true) {
            System.out.print("Insira um número. Se quiser parar, insira um número negativo: ");
            int num = in.nextInt();

            if (num < 0) {
                break;
            } else {
                listaNum.add(num);
            }

        }


        System.out.println("\nNúmeros em ordem inversa:");
        for (int i = listaNum.size() - 1; i >= 0; i--) {
            System.out.println(listaNum.get(i));
        }
    }
}
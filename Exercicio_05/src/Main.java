import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);
    static ArrayList<String> listaCompras = new ArrayList<>();

    public static void main(String[] args) {


        int opcao;
        do {
            System.out.println("Lista de Compras");
            System.out.print("Escolha uma opção: ");
            System.out.println("1. Adicionar item");
            System.out.println("2. Editar item");
            System.out.println("3. Eliminar item");
            System.out.println("4. Mostrar lista");
            System.out.println("5. Sair");
            opcao = in.nextInt();


            switch (opcao) {
                case 1:
                    AdicionarItem();
                    break;

                case 2:
                    EditarItem();
                    break;

                case 3:
                    EliminarItem();
                    break;

                case 4:
                    MostrarLista();
                    break;

                case 5:
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 5);
    }

    private static void MostrarLista() {
        if (listaCompras.isEmpty()) {
            System.out.println("A lista de compras está vazia.");
        } else {
            System.out.println("Lista de Compras");
            for (int i = 0; i < listaCompras.size(); i++) {
                System.out.println((i + 1) + ". " + listaCompras.get(i));
            }
        }
    }

    private static void EliminarItem() {
        System.out.print("Digite o índice do item a eliminar (0 a " + (listaCompras.size() - 1) + "): ");
        int indiceEliminar = in.nextInt();
        in.nextLine();

        if (indiceEliminar >= 0 && indiceEliminar < listaCompras.size()) {
            listaCompras.remove(indiceEliminar);
            System.out.println("Item eliminado com sucesso!");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    private static void EditarItem() {
        System.out.print("Digite o índice do item a editar (0 a " + (listaCompras.size() - 1) + "): ");
        int indiceEditar = in.nextInt();
        in.nextLine();

        if (indiceEditar >= 0 && indiceEditar < listaCompras.size()) {
            System.out.print("Digite o novo valor para o item: ");
            String itemEditado = in.nextLine();
            listaCompras.set(indiceEditar, itemEditado);
            System.out.println("Item editado com sucesso!");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    private static void AdicionarItem() {
        System.out.print("Digite o item a adicionar: ");
        String novoItem = in.nextLine();
        listaCompras.add(novoItem);
        System.out.println("Item adicionado com sucesso!");
    }


}

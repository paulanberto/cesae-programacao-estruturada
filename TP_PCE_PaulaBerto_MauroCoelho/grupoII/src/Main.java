import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);
    static Random rnd = new Random();
    static ArrayList<String> nomes = new ArrayList<>();
    static ArrayList<Integer> numeros = new ArrayList<>();
    static ArrayList<Integer> acertos = new ArrayList<>();
    static ArrayList<Integer> jogos = new ArrayList<>();
    static ArrayList<Integer> ouvinte = new ArrayList<>();
    static final String ranking = "ranking.txt";


    public static void main(String[] args) {


        int op;
        do {

            System.out.println("\n" + "Bem vindo (a), vamos jogar?");
            System.out.println("Escolha uma das seguintes opções");
            System.out.println("1- Adicionar/Remover Ouvinte");
            System.out.println("2- Ranking Ouvintes!");
            System.out.println("3- ---Jogar---");
            System.out.println("0- Sair");
            op = in.nextInt();

            switch (op) {
                case 1:
                    AdicionarRemover();
                    break;
                case 2:
                    Ver();
                    break;
                case 3:
                    Jogar();
                    gravarRanking(); // Salva após cada jogo
                    break;
                case 4:
                    gravarRanking();
                    break;
                case 0:
                    gravarRanking(); // Salva antes de sair
                    System.out.println("Até a próxima!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void AdicionarRemover() {
        int op;
        do {
            System.out.println("\n" + "Adicionar ou Remover Ouvinte!");
            System.out.println("1- Adicionar Ouvinte");
            System.out.println("2- Remover Ouvinte");
            System.out.println("3- Ver Ouvintes");
            System.out.println("0- Voltar Atrás");
            op = in.nextInt();

            switch (op) {
                case 1:
                    Adicionar();
                    break;
                case 2:
                    Remover();
                    break;
                case 3:
                    Ver();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        }while (op != 0);
    }

    private static void Jogar() {
        if (nomes.isEmpty()) {
            System.out.println("Nenhum ouvinte encontrado! Tem de adicionar um ouvinte");
            return;
        }

        ArrayList<Integer> ouvinteSelecionado = new ArrayList<>();
        int numOuvinte = nomes.size(); // Todos os ouvintes participam

        // Seleciona ouvintes aleatoriamente
        while (ouvinteSelecionado.size() < numOuvinte) {
            int ind = rnd.nextInt(nomes.size());
            if (!ouvinteSelecionado.contains(ind)) {
                ouvinteSelecionado.add(ind);
            }
        }

        double pesoReal = 4.200 + (rnd.nextDouble() * 0.150);
        double margemInferior = pesoReal - 0.075;
        double margemSuperior = pesoReal + 0.075;

        System.out.printf("\nMargem para acerto: entre %.3f kg e %.3f kg%n",
                margemInferior, margemSuperior);

        double[] tentativas = new double[ouvinteSelecionado.size()];
        ArrayList<Double> mesmopeso = new ArrayList<>();
        // Registra tentativas
        System.out.println("\nOrdem de jogadas:");
        for (int i = 0; i < ouvinteSelecionado.size(); i++) {
            int indOuvinte = ouvinteSelecionado.get(i);
            System.out.printf("\nVez do(a) %s (Nº %d)%n",
                    nomes.get(indOuvinte),
                    numeros.get(indOuvinte));

            boolean tentativaValida = false;
            while (!tentativaValida) {
                System.out.print("Digite o peso: ");
                double tentativa = in.nextDouble();

                if (mesmopeso.contains(tentativa)) {
                    System.out.println("Erro: Este peso já foi jogado! Escolha outro valor.");
                    continue;
                }

                // Verifica se está dentro dos limites
                if (tentativa >= margemInferior && tentativa <= margemSuperior) {
                    tentativas[i] = tentativa;
                    mesmopeso.add(tentativa);
                    tentativaValida = true; // Sai do loop
                } else {
                    System.out.println("Erro: O peso está fora dos limites! Tente novamente.");
                }
            }

            // Atualiza o número de jogos
            jogos.set(indOuvinte, jogos.get(indOuvinte) + 1);
        }

        // Encontra o vencedor
        int indVencedor = 0;
        double menorDiferenca = Math.abs(tentativas[0] - pesoReal);

        for (int i = 1; i < tentativas.length; i++) {
            double diferenca = Math.abs(tentativas[i] - pesoReal);
            if (diferenca < menorDiferenca) {
                menorDiferenca = diferenca;
                indVencedor = i;
            }
        }

        // Registra acertos apenas para quem vence
        int ouvinteVencedor = ouvinteSelecionado.get(indVencedor);
        acertos.set(ouvinteVencedor, acertos.get(ouvinteVencedor) + 1);

        // Mostra resultados
        System.out.printf("\nVencedor: %s (Nº %d)%n",
                nomes.get(ouvinteVencedor),
                numeros.get(ouvinteVencedor));
        System.out.printf("Peso real: %.3f kg%n", pesoReal);
        System.out.printf("Peso apostado: %.3f kg%n", tentativas[indVencedor]);

        gravarRanking();
    }
    private static void gravarRanking() {
        try { FileWriter writer = new FileWriter(ranking);
            for (int i = 0; i < nomes.size(); i++) {
                writer.write(String.format("%s,%d,%d,%d\n",
                        nomes.get(i), numeros.get(i), jogos.get(i), acertos.get(i)));
            }
        } catch (IOException e) {
            System.out.println("Erro ao gravar o Ranking: " + e.getMessage());
        }
    }

    private static void Ver()  {
        if (nomes.isEmpty()) {
            System.out.println("Não há ouvintes registrados.");
            return;
        }

        System.out.println("\n=== RANKING DE OUVINTES ===");
        System.out.println("Posição | Nome | Número | Jogos | Acertos");
        System.out.println("------------------------------------------------");

        // Criar lista de índices para ordenação
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < nomes.size(); i++) {
            indices.add(i);
        }

        // Ordena número de acertos (ordem decrescente)
        indices.sort((i1, i2) -> Integer.compare(acertos.get(i2), acertos.get(i1)));

        // Mostra ranking ordenado
        for (int i = 0; i < indices.size(); i++) {
            int ind = indices.get(i);

            System.out.printf("%d. %s | %d | %d | %d%n",
                    i + 1,
                    nomes.get(ind),
                    numeros.get(ind),
                    jogos.get(ind),
                    acertos.get(ind));
        }
    }

    private static void Remover() {
        if (nomes.isEmpty()) {
            System.out.println("Não há Ouvintes para remover!");
            return;
        }

        System.out.print("Digite o número do Ouvinte que deseja remover: ");
        int numero = in.nextInt();

        int ind = numeros.indexOf(numero);
        if (ind != -1) {
            String nomeRemovido = nomes.get(ind);
            nomes.remove(ind);
            numeros.remove(ind);
            acertos.remove(ind);
            System.out.println("Ouvinte " + nomeRemovido + " (número " + numero + ") removido com sucesso!");
        } else {
            System.out.println("Ouvinte com número " + numero + " não encontrado.");
        }
    }

    private static void Adicionar() {
        System.out.print("Digite o nome do Ouvinte: ");
        String nome = in.next();

        System.out.print("Digite o número do Ouvinte: ");
        int numero = in.nextInt();

        if (numeros.contains(numero)) {
            System.out.println("Erro: Número " + numero + " já está em uso! Escolha outro!");
            return;
        }
        nomes.add(nome);
        numeros.add(numero);
        acertos.add(0);
        jogos.add(0);
        System.out.println("Ouvinte " + nome + " (número " + numero + ") adicionado com sucesso!");
        gravarRanking();
    }

}
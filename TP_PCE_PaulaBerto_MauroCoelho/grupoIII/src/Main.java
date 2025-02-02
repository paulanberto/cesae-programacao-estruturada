import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    static ArrayList<String> distritos = new ArrayList<>();
    static ArrayList<Integer> inscritos = new ArrayList<>();
    static ArrayList<Integer> votantes = new ArrayList<>();
    static ArrayList<Integer> nulos = new ArrayList<>();
    static ArrayList<Integer> brancos = new ArrayList<>();
    static ArrayList<Integer> ad = new ArrayList<>();
    static ArrayList<Integer> ps = new ArrayList<>();
    static ArrayList<Integer> ch = new ArrayList<>();
    static ArrayList<Integer> il = new ArrayList<>();
    static ArrayList<Integer> be = new ArrayList<>();
    static ArrayList<Integer> outros = new ArrayList<>();
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        carregarInformacao();

        while (true) {
            System.out.println("\n--- Estatísticas Eleitorais ---");
            System.out.println("1. Visualizar Informação");
            System.out.println("2. Total de Votantes por Distrito");
            System.out.println("3. Total de Votos em Outros Partidos");
            System.out.println("4. Partido com Mais Votos");
            System.out.println("5. Distrito/Partido com Mais Votos");
            System.out.println("6. Ordenar Distritos por Votos no Partido Vencedor");
            System.out.println("7. Atualizar Dados de um Distrito");
            System.out.println("8. Imprimir email");
            System.out.println("9. Guardar informação");
            System.out.println("0. Sair");

            int op = in.nextInt();

            if (op == 0) break;

            switch (op) {
                case 1 -> visualizarInformacao();
                case 2 -> totalVotantesPorDistrito();
                case 3 -> totalVotosOutros();
                case 4 -> partidoComMaisVotos();
                case 5 -> distritoPartidoComMaisVotos();
                case 6 -> ordenarDistritosPorPartidoVencedor();
                case 7 -> atualizarDistrito();
                case 8 -> imprimirEmail();
                case 9 -> guardarInformacao();

                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void carregarInformacao()
    {
        try (BufferedReader br = new BufferedReader(new FileReader("distritos.txt"))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");

                distritos.add(dados[0]);
                inscritos.add(Integer.parseInt(dados[1]));
                votantes.add(Integer.parseInt(dados[2]));
                nulos.add(Integer.parseInt(dados[3]));
                brancos.add(Integer.parseInt(dados[4]));
                ad.add(Integer.parseInt(dados[5]));
                ps.add(Integer.parseInt(dados[6]));
                ch.add(Integer.parseInt(dados[7]));
                il.add(Integer.parseInt(dados[8]));
                be.add(Integer.parseInt(dados[9]));
                outros.add(calculaVotosOutrosPartidos());
            }
            System.out.println("Informações carregadas com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    private static void visualizarInformacao() {
        System.out.printf("%-15s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s\n",
                "Distrito", "Inscritos", "Votantes", "Nulos", "Brancos",
                "AD", "PS", "CH", "IL", "BE", "Outros", "Total");

        System.out.println("-".repeat(155));

        for (int i = 0; i < distritos.size(); i++) {
            System.out.printf("%-15s | %-10d | %-10d | %-10d | %-10d | %-10d | %-10d | %-10d | %-10d | %-10d | %-10d | %-10d\n",
                    distritos.get(i), inscritos.get(i), votantes.get(i), nulos.get(i), brancos.get(i),
                    ad.get(i), ps.get(i), ch.get(i), il.get(i), be.get(i), outros.get(i), 0);

            System.out.println("-".repeat(155));
        }
    }

    private static void totalVotantesPorDistrito() {
        int maiorVotante = 0;
        ArrayList<String> distritosComMaisVotantes = new ArrayList<>();

        for (int i = 0; i < distritos.size(); i++) {
            int totalVotantes = votantes.get(i);

            if (totalVotantes > maiorVotante) {
                maiorVotante = totalVotantes;
                distritosComMaisVotantes.clear();
                distritosComMaisVotantes.add(distritos.get(i));
            } else if (totalVotantes == maiorVotante) {
                distritosComMaisVotantes.add(distritos.get(i));
            }
        }

        System.out.println("Total de Votantes por Distrito");
        for (int i = 0; i < distritos.size(); i++) {
            System.out.println(distritos.get(i) + ": " + votantes.get(i) + " votantes");
        }

        System.out.println("\nDistrito(s) com maior número de votantes: ");
        for (String distrito: distritosComMaisVotantes) {
            System.out.println(distrito + " com " + maiorVotante + " votantes.");
        }
    }

    private static int calculaVotosOutrosPartidos() {
        int votosOutrosPartidos = 0;

        for (int i = 0; i < distritos.size(); i++) {
            int totalPartidosConhecidos = ad.get(i) + ps.get(i) + ch.get(i) + il.get(i) + be.get(i) + nulos.get(i) + brancos.get(i);
            votosOutrosPartidos = votantes.get(i) - totalPartidosConhecidos;
        }
        return votosOutrosPartidos;
    }

    private static void totalVotosOutros() {
        System.out.println("Total de Votes em Outros Partidos por Distrito: ");

        for (int i = 0; i < distritos.size(); i++) {
            int totalPartidosConhecidos = ad.get(i) + ps.get(i) + ch.get(i) + il.get(i) + be.get(i) + nulos.get(i) + brancos.get(i);
            int votosOutrosPartidos = votantes.get(i) - totalPartidosConhecidos;

            System.out.println(distritos.get(i) + ": " + votosOutrosPartidos + " votos em outros partidos.");
        }
    }

    private static void partidoComMaisVotos() {
        int totalVotosPartidoAD = 0;
        int totalVotosPartidoPS = 0;
        int totalVotosPartidoCH = 0;
        int totalVotosPartidoIL = 0;
        int totalVotosPartidoBE = 0;

        for (int i = 0; i < distritos.size(); i++) {
            totalVotosPartidoAD += ad.get(i);
            totalVotosPartidoPS += ps.get(i);
            totalVotosPartidoCH += ch.get(i);
            totalVotosPartidoIL += il.get(i);
            totalVotosPartidoBE += be.get(i);
        }

        int totalVotantes = 0;
        for (Integer votante : votantes) {
            totalVotantes += votante;
        }

        int maiorVotos = Math.max(Math.max(totalVotosPartidoAD, totalVotosPartidoPS), Math.max(Math.max(totalVotosPartidoCH, totalVotosPartidoIL), totalVotosPartidoBE));
        System.out.println("Partido(s) com mais votos");

        if (totalVotosPartidoAD == maiorVotos) {
            double percentual = ((double) totalVotosPartidoAD / totalVotantes) * 100;
            percentual = Math.floor(percentual * 100) / 100;
            System.out.println("Partido AD com o total de " + totalVotosPartidoAD + " com " + percentual + "% dos votos.");
        }

        if (totalVotosPartidoPS == maiorVotos) {
            double percentual = ((double) totalVotosPartidoPS / totalVotantes) * 100;
            percentual = Math.floor(percentual * 100) / 100;
            System.out.println("Partido PS com o total de " + totalVotosPartidoPS + " com " + percentual + "% dos votos.");
        }

        if (totalVotosPartidoCH == maiorVotos) {
            double percentual = ((double) totalVotosPartidoCH / totalVotantes) * 100;
            percentual = Math.floor(percentual * 100) / 100;
            System.out.println("Partido CH com o total de " + totalVotosPartidoCH + " com " + percentual + "% dos votos.");
        }

        if (totalVotosPartidoIL == maiorVotos) {
            double percentual = ((double) totalVotosPartidoIL / totalVotantes) * 100;
            percentual = Math.floor(percentual * 100) / 100;
            System.out.println("Partido IL com o total de " + totalVotosPartidoIL + " com " + percentual + "% dos votos.");
        }

        if (totalVotosPartidoBE == maiorVotos) {
            double percentual = ((double) totalVotosPartidoBE / totalVotantes) * 100;
            percentual = Math.floor(percentual * 100) / 100;
            System.out.println("Partido BE com o total de " + totalVotosPartidoBE + " com " + percentual + "% dos votos.");
        }
    }

    private static void distritoPartidoComMaisVotos() {
        for (int i = 0; i < distritos.size(); i++) {
            int votosPartidoAD = ad.get(i);
            int votosPartidoPS = ps.get(i);
            int votosPartidoCH = ch.get(i);
            int votosPartidoIL = il.get(i);
            int votosPartidoBE = be.get(i);

            int totalVotantesDistrito = votantes.get(i);

            int maiorVotos = Math.max(Math.max(votosPartidoAD, votosPartidoPS), Math.max(votosPartidoCH, votosPartidoIL));

            String partidoVencedor = "";
            if (votosPartidoAD == maiorVotos) {
                partidoVencedor = "Partido AD";
            }
            if (votosPartidoPS == maiorVotos) {
                partidoVencedor = "Partido PS";
            }
            if (votosPartidoCH == maiorVotos) {
                partidoVencedor = "Partido CH";
            }
            if (votosPartidoIL == maiorVotos) {
                partidoVencedor = "Partido IL";
            }
            if (votosPartidoBE == maiorVotos) {
                partidoVencedor = "Partido BE";
            }

            double percentualVotosVencedor = ((double) maiorVotos / totalVotantesDistrito) * 100;
            percentualVotosVencedor = Math.floor(percentualVotosVencedor * 100) / 100;

            System.out.println("Distrito: " + distritos.get(i) + " | Partido vencedor: " + partidoVencedor + " com o total de " + maiorVotos + " e " + percentualVotosVencedor + "% dos votos.");
        }
    }

    private static void ordenarDistritosPorPartidoVencedor() {
        ArrayList<Integer> votosVencedor = new ArrayList<>();
        String partidoVencedor = "";

        for (int i = 0; i < distritos.size(); i++) {
            int votosPartidoAD = ad.get(i);
            int votosPartidoPS = ps.get(i);
            int votosPartidoCH = ch.get(i);
            int votosPartidoIL = il.get(i);
            int votosPartidoBE = be.get(i);

            int maiorVotos = Math.max(Math.max(votosPartidoAD, votosPartidoPS), Math.max(Math.max(votosPartidoCH, votosPartidoIL), votosPartidoBE));
            votosVencedor.add(maiorVotos);

            if (maiorVotos == votosPartidoAD) {
                partidoVencedor = "Partido AD";
            } else if (maiorVotos == votosPartidoPS) {
                partidoVencedor = "Partido PS";
            } else if (maiorVotos == votosPartidoCH) {
                partidoVencedor = "Partido CH";
            } else if (maiorVotos == votosPartidoIL) {
                partidoVencedor = "Partido IL";
            } else if (maiorVotos == votosPartidoBE) {
                partidoVencedor = "Partido BE";
            }
        }

        for (int i = 0; i < distritos.size(); i++) {
            for (int j = i + 1; j < distritos.size(); j++) {
                if (votosVencedor.get(i) < votosVencedor.get(j)) {
                    Collections.swap(distritos, i, j);
                    Collections.swap(votosVencedor, i, j);
                }
            }
        }

        System.out.println("Partidos ordenados por votos");
        for (int i = 0; i < distritos.size(); i++) {
            int votos = votosVencedor.get(i);
            System.out.println("Distrito: " + distritos.get(i) + " | Partido " + partidoVencedor + " recebeu um total de " + votos + " votos.");
        }
    }

    private static void atualizarDistrito() {
        while (true) {
            System.out.println("\nEscolha o distrito que deseja atualizar:");
            System.out.println("1. Aveiro");
            System.out.println("2. Beja");
            System.out.println("3. Braga");
            System.out.println("4. Viseu");
            System.out.println("0. Sair");

            int op = in.nextInt();

            if (op == 0) break;

            switch (op) {
                case 1 -> atualizarAveiro();
                case 2 -> atualizarBeja();
                case 3 -> atualizarBraga();
                case 4 -> atualizarViseu();

                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void atualizarAveiro() {
        int indiceDistrito = -1;

        for (int i = 0; i < distritos.size(); i++) {
            if (distritos.get(i).equals("Aveiro")) {
                indiceDistrito = i;
                break;
            }
        }

        int inscritosDistrito = inscritos.get(indiceDistrito);

        System.out.println("Digite os novos votos para o partido AD:");
        int votosAD = in.nextInt();

        System.out.println("Digite os novos votos para o partido PS:");
        int votosPS = in.nextInt();

        System.out.println("Digite os novos votos para o partido CH:");
        int votosCH = in.nextInt();

        System.out.println("Digite os novos votos para o partido IL:");
        int votosIL = in.nextInt();

        System.out.println("Digite os novos votos para o partido BE:");
        int votosBE = in.nextInt();

        System.out.println("Digite o novo número de votos nulos:");
        int votosNulos = in.nextInt();

        System.out.println("Digite o novo número de votos brancos:");
        int votosBrancos = in.nextInt();

        int totalVotosDistrito = votosAD + votosPS + votosCH + votosIL + votosBE + votosNulos + votosBrancos;

        if (totalVotosDistrito > inscritosDistrito) {
            System.out.println("Erro: O total de votos não pode ser superior ao número de inscritos no distrito.");
            return;
        }

        ad.set(indiceDistrito, ad.get(indiceDistrito) + votosAD);
        ps.set(indiceDistrito, ps.get(indiceDistrito) + votosPS);
        ch.set(indiceDistrito, ch.get(indiceDistrito) + votosCH);
        il.set(indiceDistrito, il.get(indiceDistrito) + votosIL);
        be.set(indiceDistrito, be.get(indiceDistrito) + votosBE);
        nulos.set(indiceDistrito, nulos.get(indiceDistrito) + votosNulos);
        brancos.set(indiceDistrito, brancos.get(indiceDistrito) + votosBrancos);

        int totalVotantes = votosAD + votosPS + votosCH + votosIL + votosBE + votosNulos + votosBrancos;
        votantes.set(indiceDistrito, votantes.get(indiceDistrito) + totalVotantes);

        if (totalVotantes != (votosAD + votosPS + votosCH + votosIL + votosBE + votosNulos + votosBrancos)) {
            System.out.println("Erro: O número total de votantes não corresponde à soma de todos os votos.");
            return;
        }

        System.out.println("Dados do distrito " + distritos.get(indiceDistrito) + " atualizados com sucesso!");
    }

    private static void atualizarBeja() {
        int indiceDistrito = -1;

        for (int i = 0; i < distritos.size(); i++) {
            if (distritos.get(i).equals("Beja")) {
                indiceDistrito = i;
                break;
            }
        }

        int inscritosDistrito = inscritos.get(indiceDistrito);

        // Solicita os novos dados para o distrito escolhido
        System.out.println("Digite os novos votos para o partido AD:");
        int votosAD = in.nextInt();

        System.out.println("Digite os novos votos para o partido PS:");
        int votosPS = in.nextInt();

        System.out.println("Digite os novos votos para o partido CH:");
        int votosCH = in.nextInt();

        System.out.println("Digite os novos votos para o partido IL:");
        int votosIL = in.nextInt();

        System.out.println("Digite os novos votos para o partido BE:");
        int votosBE = in.nextInt();

        System.out.println("Digite o novo número de votos nulos:");
        int votosNulos = in.nextInt();

        System.out.println("Digite o novo número de votos brancos:");
        int votosBrancos = in.nextInt();

        int totalVotosDistrito = votosAD + votosPS + votosCH + votosIL + votosBE + votosNulos + votosBrancos;

        if (totalVotosDistrito > inscritosDistrito) {
            System.out.println("Erro: O total de votos não pode ser superior ao número de inscritos no distrito.");
            return;
        }

        // Atualiza os votos na lista correspondente ao distrito
        ad.set(indiceDistrito, ad.get(indiceDistrito) + votosAD);
        ps.set(indiceDistrito, ps.get(indiceDistrito) + votosPS);
        ch.set(indiceDistrito, ch.get(indiceDistrito) + votosCH);
        il.set(indiceDistrito, il.get(indiceDistrito) + votosIL);
        be.set(indiceDistrito, be.get(indiceDistrito) + votosBE);
        nulos.set(indiceDistrito, nulos.get(indiceDistrito) + votosNulos);
        brancos.set(indiceDistrito, brancos.get(indiceDistrito) + votosBrancos);

        int totalVotantes = votosAD + votosPS + votosCH + votosIL + votosBE + votosNulos + votosBrancos;
        votantes.set(indiceDistrito, totalVotantes);

        if (totalVotantes != (votosAD + votosPS + votosCH + votosIL + votosBE + votosNulos + votosBrancos)) {
            System.out.println("Erro: O número total de votantes não corresponde à soma de todos os votos.");
            return;
        }

        System.out.println("Dados do distrito " + distritos.get(indiceDistrito) + " atualizados com sucesso!");
    }

    private static void atualizarViseu() {
        int indiceDistrito = -1;

        for (int i = 0; i < distritos.size(); i++) {
            if (distritos.get(i).equals("Viseu")) {
                indiceDistrito = i;
            }
        }

        int inscritosDistrito = inscritos.get(indiceDistrito);

        System.out.println("Digite os novos votos para o partido AD:");
        int votosAD = in.nextInt();

        System.out.println("Digite os novos votos para o partido PS:");
        int votosPS = in.nextInt();

        System.out.println("Digite os novos votos para o partido CH:");
        int votosCH = in.nextInt();

        System.out.println("Digite os novos votos para o partido IL:");
        int votosIL = in.nextInt();

        System.out.println("Digite os novos votos para o partido BE:");
        int votosBE = in.nextInt();

        System.out.println("Digite o novo número de votos nulos:");
        int votosNulos = in.nextInt();

        System.out.println("Digite o novo número de votos brancos:");
        int votosBrancos = in.nextInt();

        int totalVotosDistrito = votosAD + votosPS + votosCH + votosIL + votosBE + votosNulos + votosBrancos;

        if (totalVotosDistrito > inscritosDistrito) {
            System.out.println("Erro: O total de votos não pode ser superior ao número de inscritos no distrito.");
            return;
        }

        ad.set(indiceDistrito, ad.get(indiceDistrito) + votosAD);
        ps.set(indiceDistrito, ps.get(indiceDistrito) + votosPS);
        ch.set(indiceDistrito, ch.get(indiceDistrito) + votosCH);
        il.set(indiceDistrito, il.get(indiceDistrito) + votosIL);
        be.set(indiceDistrito, be.get(indiceDistrito) + votosBE);
        nulos.set(indiceDistrito, nulos.get(indiceDistrito) + votosNulos);
        brancos.set(indiceDistrito, brancos.get(indiceDistrito) + votosBrancos);

        int totalVotantes = votosAD + votosPS + votosCH + votosIL + votosBE + votosNulos + votosBrancos;
        votantes.set(indiceDistrito, totalVotantes);

        if (totalVotantes != (votosAD + votosPS + votosCH + votosIL + votosBE + votosNulos + votosBrancos)) {
            System.out.println("Erro: O número total de votantes não corresponde à soma de todos os votos.");
            return;
        }

        System.out.println("Dados do distrito " + distritos.get(indiceDistrito) + " atualizados com sucesso!");
    }

    private static void atualizarBraga() {
        int indiceDistrito = -1;

        for (int i = 0; i < distritos.size(); i++) {
            if (distritos.get(i).equals("Braga")) {
                indiceDistrito = i;
                break;
            }
        }

        int inscritosDistrito = inscritos.get(indiceDistrito);

        System.out.println("Digite os novos votos para o partido AD:");
        int votosAD = in.nextInt();

        System.out.println("Digite os novos votos para o partido PS:");
        int votosPS = in.nextInt();

        System.out.println("Digite os novos votos para o partido CH:");
        int votosCH = in.nextInt();

        System.out.println("Digite os novos votos para o partido IL:");
        int votosIL = in.nextInt();

        System.out.println("Digite os novos votos para o partido BE:");
        int votosBE = in.nextInt();

        System.out.println("Digite o novo número de votos nulos:");
        int votosNulos = in.nextInt();

        System.out.println("Digite o novo número de votos brancos:");
        int votosBrancos = in.nextInt();

        int totalVotosDistrito = votosAD + votosPS + votosCH + votosIL + votosBE + votosNulos + votosBrancos;

        if (totalVotosDistrito > inscritosDistrito) {
            System.out.println("Erro: O total de votos não pode ser superior ao número de inscritos no distrito.");
            return;
        }

        ad.set(indiceDistrito, ad.get(indiceDistrito) + votosAD);
        ps.set(indiceDistrito, ps.get(indiceDistrito) + votosPS);
        ch.set(indiceDistrito, ch.get(indiceDistrito) + votosCH);
        il.set(indiceDistrito, il.get(indiceDistrito) + votosIL);
        be.set(indiceDistrito, be.get(indiceDistrito) + votosBE);
        nulos.set(indiceDistrito, nulos.get(indiceDistrito) + votosNulos);
        brancos.set(indiceDistrito, brancos.get(indiceDistrito) + votosBrancos);

        int totalVotantes = votosAD + votosPS + votosCH + votosIL + votosBE + votosNulos + votosBrancos;
        votantes.set(indiceDistrito, totalVotantes);

        if (totalVotantes != (votosAD + votosPS + votosCH + votosIL + votosBE + votosNulos + votosBrancos)) {
            System.out.println("Erro: O número total de votantes não corresponde à soma de todos os votos.");
            return;
        }

        System.out.println("Dados do distrito " + distritos.get(indiceDistrito) + " atualizados com sucesso!");
    }

    private static void imprimirEmail() {
        int maiorQuantidadeVotosInvalidos = 0;
        ArrayList<String> distritosComMaisVotosInvalidos = new ArrayList<>();

        for (int i = 0; i < distritos.size(); i++) {
            int votosInvalidos = nulos.get(i) + brancos.get(i);

            if (votosInvalidos > maiorQuantidadeVotosInvalidos) {
                maiorQuantidadeVotosInvalidos = votosInvalidos;
                distritosComMaisVotosInvalidos.clear();
                distritosComMaisVotosInvalidos.add(distritos.get(i));
            } else if (votosInvalidos == maiorQuantidadeVotosInvalidos) {
                distritosComMaisVotosInvalidos.add(distritos.get(i));
            }
        }

        System.out.println("Email do distrito com mais votos inválidos");
        for (String distrito : distritosComMaisVotosInvalidos) {
            String email;
            if (distrito.length() < 2) {
                email = distrito + "@ine.pt";
            } else {
                char primeiraLetra = distrito.charAt(0);
                char segundaLetra = distrito.charAt(1);
                char penultimaLetra = distrito.charAt(distrito.length() - 2);
                char ultimaLetra = distrito.charAt(distrito.length() - 1);
                email = "" + primeiraLetra + segundaLetra + penultimaLetra + ultimaLetra + "@ine.pt";
            }

            System.out.println("Distrito: " + distrito + " | Email: " + email.toLowerCase());
        }
    }

    private static void guardarInformacao() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("distritos.txt"))) {
            for (int i = 0; i < distritos.size(); i++) {
                String distrito = distritos.get(i);
                int inscritosDistrito = inscritos.get(i);
                int votantesDistrito = votantes.get(i);
                int nulosDistrito = nulos.get(i);
                int brancosDistrito = brancos.get(i);
                int votosAD = ad.get(i);
                int votosPS = ps.get(i);
                int votosCH = ch.get(i);
                int votosIL = il.get(i);
                int votosBE = be.get(i);

                String linha = distrito + ";" + inscritosDistrito + ";" + votantesDistrito + ";" +
                        nulosDistrito + ";" + brancosDistrito + ";" + votosAD + ";" +
                        votosPS + ";" + votosCH + ";" + votosIL + ";" + votosBE;

                writer.write(linha);
                writer.newLine();
            }

            System.out.println("Arquivo atualizado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo. Erro: " + e.getMessage());
        }
    }
}

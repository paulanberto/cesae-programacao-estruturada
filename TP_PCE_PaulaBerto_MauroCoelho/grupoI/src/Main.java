import java.util.*;


public class Main {
    static Scanner in = new Scanner(System.in);
    static Random rnd = new Random();


    public static void main(String[] args) {


        int op;
        do {

            System.out.println("\n" + "Bem vindo (a), vamos jogar?");
            System.out.println("Escolha uma das seguintes opções");
            System.out.println("1-Sorteio");
            System.out.println("2-Apostar");
            System.out.println("3-Comprar jogo pronto");
            System.out.println("4-Simulação");
            System.out.println("0-Sair");
            op = in.nextInt();

            if (op == 0) {
                return;
            } else if (op == 2) {
                Apostar();
            } else if (op == 1) {
                Sorteio();
            } else if (op == 3) {
                ComprarJogo();
            } else if (op == 4) {
                Simulcao();
            } else {
                System.out.println("Opção inválida");
            }
        }
        while (op != 0);
    }

    private static void Simulcao() {
        ArrayList<Integer> chaveNumerosJogador = new ArrayList<>();
        ArrayList<Integer> chaveEstrelasJogador = new ArrayList<>();

        System.out.println("Por favor, insira sua chave com 5 números e 2 estrelas: ");
        System.out.println("Insira os 5 números principais entre 1 e 50:");

        while (chaveNumerosJogador.size() < 5) {
            try {
                System.out.print("Número " + (chaveNumerosJogador.size() + 1) + ": ");
                int numero = in.nextInt();

                if (numero < 1 || numero > 50) {
                    System.out.println("Número inválido! Deve estar entre 1 e 50.");
                } else if (chaveNumerosJogador.contains(numero)) {
                    System.out.println("Número repetido! Por favor, insira um número diferente.");
                } else {
                    chaveNumerosJogador.add(numero);
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida! Por favor, insira um número inteiro.");
                in.nextLine();
            }
        }

        System.out.println("Insira as 2 estrelas entre 1 e 12:");
        while (chaveEstrelasJogador.size() < 2) {
            try {
                System.out.print("Estrela " + (chaveEstrelasJogador.size() + 1) + ": ");
                int estrela = in.nextInt();

                if (estrela < 1 || estrela > 12) {
                    System.out.println("Estrela inválida! Deve estar entre 1 e 12.");
                } else if (chaveEstrelasJogador.contains(estrela)) {
                    System.out.println("Estrela repetida! Por favor, insira uma estrela diferente.");
                } else {
                    chaveEstrelasJogador.add(estrela);
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida! Por favor, insira um número inteiro.");
                in.nextLine();
            }
        }

        Collections.sort(chaveNumerosJogador);
        Collections.sort(chaveEstrelasJogador);

        System.out.println("\nSua chave é: ");
        System.out.println("Números: " + chaveNumerosJogador);
        System.out.println("Estrelas: " + chaveEstrelasJogador);


        long tentativas = 0;

        while (true) {
            tentativas++;

            ArrayList<Integer> numeroSorteados = new ArrayList<>();
            ArrayList<Integer> estrelasSorteados = new ArrayList<>();


            while (numeroSorteados.size() < 5) {
                int numero = rnd.nextInt(1, 51);

                if (!numeroSorteados.contains(numero)) {
                    numeroSorteados.add(numero);
                }
            }
            while (estrelasSorteados.size() < 2) {
                int estrela = rnd.nextInt(1, 13);

                if (!estrelasSorteados.contains(estrela)) {
                    estrelasSorteados.add(estrela);
                }
            }

            Collections.sort(numeroSorteados);
            Collections.sort(estrelasSorteados);


            if (numeroSorteados.containsAll(chaveNumerosJogador) &&
                    estrelasSorteados.containsAll(chaveEstrelasJogador)) {

                System.out.println("\nParabéns! Você acertou o 1º prêmio!");
                System.out.println("Números sorteados: " + numeroSorteados);
                System.out.println("Estrelas sorteadas: " + estrelasSorteados);
                System.out.println("Foram necessárias " + tentativas + " tentativas para ganhar.");
                break;
            }

            if (tentativas % 1_000_000 == 0) {
                System.out.println("Tentativas realizadas: " + tentativas + " (ainda tentando...)");
            }
        }

    }


    private static void ComprarJogo() {
        ArrayList<ArrayList<Integer>> numerosRandom = new ArrayList<>();
        ArrayList<ArrayList<Integer>> estrelasRandom = new ArrayList<>();
        ArrayList<Integer> numero;
        ArrayList<Integer> estrela;

        int numApostas = 0;
        boolean entradaValida = false;
        int i;


        while (!entradaValida) {
            try {
                System.out.println("Você pode fazer até 5 apostas. Quantas apostas você quer fazer?");
                numApostas = in.nextInt();


                if (numApostas < 1 || numApostas > 5) {
                    throw new IllegalArgumentException("O número de apostas deve ser entre 1 e 5.");
                }

                entradaValida = true; // Entrada válida, saímos do loop
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro: Entrada inválida. Por favor, insira um número inteiro.");
                in.nextLine(); // Limpar o buffer do Scanner para evitar looping infinito
            }
        }


        if (numApostas >= 1 && numApostas <= 5) {
            for (i = 0; i < numApostas; i++) {
                numero = new ArrayList<>();
                estrela = new ArrayList<>();


                while (numero.size() < 5) {
                    int num = rnd.nextInt(1, 51);

                    if (!numero.contains(num)) {
                        numero.add(num);
                    }
                }
                while (estrela.size() < 2) {
                    int star = rnd.nextInt(1, 13);

                    if (!estrela.contains(star)) {
                        estrela.add(star);
                    }
                }
                Collections.sort(numero);
                Collections.sort(estrela);
                numerosRandom.add(numero);
                estrelasRandom.add(estrela);

                System.out.println("A sua " + (i + 1) + "ª aposta é  " + "\t");
                System.out.println("Números: " + numerosRandom.get(i) + "  Estrelas: " + estrelasRandom.get(i));

            }

            List<List<Integer>> sorteio = Sorteio();
            ArrayList<Integer> numerosSorteados = new ArrayList<>(sorteio.get(0));
            ArrayList<Integer> estrelasSorteadas = new ArrayList<>(sorteio.get(1));


            System.out.println("\nConferência das apostas:");
            for (i = 0; i < numApostas; i++) {
                ArrayList<Integer> numerosAposta = numerosRandom.get(i);
                ArrayList<Integer> estrelasAposta = estrelasRandom.get(i);

                int acertosNumeros = (int) numerosAposta.stream().filter(numerosSorteados::contains).count();
                int acertosEstrelas = (int) estrelasAposta.stream().filter(estrelasSorteadas::contains).count();

                System.out.println("Aposta " + (i + 1) + ":");
                System.out.println("Números apostados: " + numerosAposta);
                System.out.println("Estrelas apostadas: " + estrelasAposta);
                System.out.println("Acertos: " + acertosNumeros + " números e " + acertosEstrelas + " estrelas.\n");
            }
        }

    }

    private static List<List<Integer>> Sorteio() {
        ArrayList<Integer> numeroSorteados = new ArrayList<>();
        ArrayList<Integer> estrelasSorteados = new ArrayList<>();


        while (numeroSorteados.size() < 5) {
            int numero = rnd.nextInt(1, 51);

            if (!numeroSorteados.contains(numero)) {
                numeroSorteados.add(numero);
            }
        }
        while (estrelasSorteados.size() < 2) {
            int estrela = rnd.nextInt(1, 13);

            if (!estrelasSorteados.contains(estrela)) {
                estrelasSorteados.add(estrela);
            }
        }

        Collections.sort(estrelasSorteados);
        Collections.sort(numeroSorteados);

        System.out.println("Os números sorteados são: ");
        System.out.println("Números: " + numeroSorteados + "  Estrelas: " + estrelasSorteados);

        List<List<Integer>> resultadoSorteio = new ArrayList<>();
        resultadoSorteio.add(numeroSorteados);
        resultadoSorteio.add(estrelasSorteados);
        return resultadoSorteio;

    }


    private static void Apostar() {


        ArrayList<ArrayList<Integer>> numeroSolicitados = new ArrayList<>();
        ArrayList<ArrayList<Integer>> estrelasSolicitadas = new ArrayList<>();
        ArrayList<Integer> numero;
        ArrayList<Integer> estrela;
        int i;
        int numApostas = 0;
        boolean entradaValida = false;


        while (!entradaValida) {
            try {
                System.out.println("Você pode fazer até 5 apostas. Quantas apostas você quer fazer?");
                numApostas = in.nextInt();


                if (numApostas < 1 || numApostas > 5) {
                    throw new IllegalArgumentException("O número de apostas deve ser entre 1 e 5.");
                }

                entradaValida = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro: Entrada inválida. Por favor, insira um número inteiro.");
                in.nextLine();
            }
        }

        System.out.println("Por favor, insira sua chave com 5 números e 2 estrelas: ");

        if (numApostas >= 1 && numApostas <= 5) {
            for (i = 0; i < numApostas; i++) {
                numero = new ArrayList<>();
                estrela = new ArrayList<>();

                while (numero.size() < 5) {
                    System.out.println("\n" + "Número " + (numero.size() + 1) + ": ");
                    int num = in.nextInt();

                    if (num < 1 || num > 50) {
                        System.out.println("Insira um número válido entre 1 e 50");
                        continue;
                    }

                    if (!numero.contains(num)) {
                        numero.add(num);
                    } else {
                        System.out.println("\n" + "Número repetido. Insira um novo número entre 1 e 50: ");
                    }
                }
                while (estrela.size() < 2) {
                    System.out.println("\n" + "Estrela " + (estrela.size() + 1) + ": ");
                    int star = in.nextInt();

                    if (star < 1 || star > 12) {
                        System.out.println("Insira um número válido entre 1 e 12");
                        continue;
                    }
                    if (!estrela.contains(star)) {
                        estrela.add(star);
                    } else {
                        System.out.println("\n" + "Número repetido. Insira um novo número entre 1 e 12:");
                    }
                }

                Collections.sort(numero);
                Collections.sort(estrela);
                numeroSolicitados.add(numero);
                estrelasSolicitadas.add(estrela);

                System.out.println("A sua " + (i + 1) + "ª aposta é  " + "\t");
                System.out.println("Números: " + numeroSolicitados.get(i) + "  Estrelas: " + estrelasSolicitadas.get(i));

            }


            List<List<Integer>> sorteio = Sorteio();
            ArrayList<Integer> numerosSorteados = new ArrayList<>(sorteio.get(0));
            ArrayList<Integer> estrelasSorteadas = new ArrayList<>(sorteio.get(1));


            System.out.println("\nConferência das apostas:");
            for (i = 0; i < numApostas; i++) {
                ArrayList<Integer> numerosAposta = numeroSolicitados.get(i);
                ArrayList<Integer> estrelasAposta = estrelasSolicitadas.get(i);

                int acertosNumeros = (int) numerosAposta.stream().filter(numerosSorteados::contains).count();
                int acertosEstrelas = (int) estrelasAposta.stream().filter(estrelasSorteadas::contains).count();

                System.out.println("Aposta " + (i + 1) + ":");
                System.out.println("Números apostados: " + numerosAposta);
                System.out.println("Estrelas apostadas: " + estrelasAposta);
                System.out.println("Acertos: " + acertosNumeros + " números e " + acertosEstrelas + " estrelas.\n");
            }

        }
    }
}
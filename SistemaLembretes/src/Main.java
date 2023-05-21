import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private static Map<String, List<Lembrete>> lembretesPorData = new TreeMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            exibirMenu();

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após o número

            switch (opcao) {
                case 1:
                    adicionarLembrete(scanner);
                    break;
                case 2:
                    deletarLembrete(scanner);
                    break;
                case 3:
                    exibirLembretes();
                    break;
                case 4:
                    System.out.println("Encerrando o programa...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Adicionar lembrete");
        System.out.println("2. Deletar lembrete");
        System.out.println("3. Exibir lembretes");
        System.out.println("4. Encerrar programa");
        System.out.print("Escolha uma opção: ");
    }

    private static void adicionarLembrete(Scanner scanner) {
        System.out.print("\nDigite o nome do lembrete: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a data do lembrete (dd/MM/yyyy): ");
        String dataStr = scanner.nextLine();

        try {
            Date data = DATE_FORMAT.parse(dataStr);

            if (data.before(new Date())) {
                System.out.println("A data do lembrete deve ser no futuro.");
                return;
            }

            Lembrete lembrete = new Lembrete(nome, data);
            String dataFormatada = DATE_FORMAT.format(data);

            if (lembretesPorData.containsKey(dataFormatada)) {
                lembretesPorData.get(dataFormatada).add(lembrete);
            } else {
                List<Lembrete> lembretes = new ArrayList<>();
                lembretes.add(lembrete);
                lembretesPorData.put(dataFormatada, lembretes);
            }

            System.out.println("Lembrete adicionado com sucesso!");
        } catch (ParseException e) {
            System.out.println("Data inválida. Certifique-se de usar o formato dd/MM/yyyy.");
        }
    }

    private static void deletarLembrete(Scanner scanner) {
        System.out.println("\n==== LEMBRETES ====");
        exibirLembretes();

        System.out.print("\nDigite a data do lembrete que deseja deletar (dd/MM/yyyy): ");
        String dataStr = scanner.nextLine();

        if (lembretesPorData.containsKey(dataStr)) {
            List<Lembrete> lembretes = lembretesPorData.get(dataStr);

            System.out.println("\n==== LEMBRETES NA DATA " + dataStr + " ====");
            exibirLembretesData(lembretes);

            System.out.print("\nDigite o número do lembrete que deseja deletar: ");
            int numeroLembrete = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após o número

            if (numeroLembrete > 0 && numeroLembrete <= lembretes.size()) {
                lembretes.remove(numeroLembrete - 1);
                System.out.println("Lembrete removido com sucesso!");
            } else {
                System.out.println("Número de lembrete inválido.");
            }
        } else {
            System.out.println("Não existem lembretes na data informada.");
        }
    }

    private static void exibirLembretes() {
        if (lembretesPorData.isEmpty()) {
            System.out.println("Não existem lembretes.");
            return;
        }

        for (Map.Entry<String, List<Lembrete>> entry : lembretesPorData.entrySet()) {
            String dataStr = entry.getKey();
            List<Lembrete> lembretes = entry.getValue();

            System.out.println("\n==== LEMBRETES NA DATA " + dataStr + " ====");
            exibirLembretesData(lembretes);
        }
    }

    private static void exibirLembretesData(List<Lembrete> lembretes) {
        for (int i = 0; i < lembretes.size(); i++) {
            Lembrete lembrete = lembretes.get(i);
            System.out.println((i + 1) + ". " + lembrete.getNome());
        }
    }
}

class Lembrete {
    private String nome;
    private Date data;

    public Lembrete(String nome, Date data) {
        this.nome = nome;
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public Date getData() {
        return data;
    }
}

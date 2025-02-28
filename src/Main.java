import model.Board;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static Board board = new Board();
    private static final String DEFAULT_GAME = "..9.6..5......98.....57496....3.569........7.39..87..4.4...6.35..31.8.......3....";
    private static final Scanner scanner = new Scanner(System.in);
    ;

    public static void main(String[] args) {
        startGame();
        int choice = 0;
        while (choice != 8) {
            String message = """
                    Selecione uma opção:
                        1 - Iniciar outro jogo
                        2 - Colocar novo número
                        3 - Remover número
                        4 - Verificar tabuleiro
                        5 - Verificar status do jogo
                        6 - Limpar tabuleiro
                        7 - Finalizar jogo
                        8 - Sair
                    """;
            choice = requestNumber(1, 8, message);
            switch (choice) {
                case 1 -> {
                    message = "Deseja mesmo iniciar outro jogo? Sim(1) ou Não(2)";
                    int start = requestNumber(1, 2, message);
                    if (start == 1) {
                        startGame();
                    }
                }
                case 2 -> changeNumber();
                case 3 -> removeNumber();
                case 4 -> System.out.println(board.getCurrentBoard());
                case 5 -> checkBoardStatus();
                case 6 -> {
                    message = "Deseja mesmo limpar o tabuleiro? Sim(1) ou Não(2)";
                    int start = requestNumber(1, 2, message);
                    if (start == 1) {
                        board.clearBoard();
                    }
                }
                case 7 -> finishGame();
            }
        } scanner.close();
        System.out.println("Até logo!");
        System.exit(0);
    }

    public static void startGame() {
        String message = "Gostaria de jogar o tabuleiro padrão(1) ou usar o seu próprio(2)?";
        int choice = requestNumber(1, 2, message);
        if (choice == 1) {
            board.startGame(DEFAULT_GAME);
        } else {
            message = """
                    Por favor insira o tabuleiro desejado no seguinte formato:
                    O caractere . para espaços vazios e o número correspondente aos números fixos. Ex:
                    ..9.6..5......98.....57496....3.569........7.39..87..4.4...6.35..31.8.......3....
                    
                    Se você não tiver um jogo em mente, você pode conseguir um jogo no seguinte link:
                    https://qqwing.com/generate.html
                    Selecione as opções que quiser e coloque Output Format em One Line e cole o jogo no terminal.
                    """;
            String game = requestGame(message);
            board.startGame(game);
        }
    }

    private static void finishGame() {
        if (board.isFinished()) {
            System.out.println("Parabéns, você terminou o jogo!");
        } else {
            checkBoardStatus();
        }
    }

    private static void checkBoardStatus() {
        System.out.println("O tabuleiro está " + board.getBoardStatus().getLabel() + " e " + (board.hasErrors() ? "" : "não ") + "contém erros.");
    }

    private static void changeNumber() {
        int row = requestNumber(0, 8, "Insira o número da linha desejada:");
        int col = requestNumber(0, 8, "Insira o número da coluna desejada:");
        int value = requestNumber(1, 9, "Insira o número para preencher:");
        if (!board.changeSpaceNumber(row, col, value)) {
            System.out.println("O número desta posição é fixo");
        }
    }

    private static void removeNumber() {
        int row = requestNumber(0, 8, "Insira o número da linha desejada:");
        int col = requestNumber(0, 8, "Insira o número da coluna desejada:");
        if (!board.removeSpaceNumber(row, col)) {
            System.out.println("O número desta posição é fixo.");
        }
    }

    public static int requestNumber(int min, int max, String message) {
        while (true) {
            System.out.println(message);
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
            } catch (NumberFormatException e) {
            }
            System.out.printf("Por favor, digite um número entre %d e %d\n", min, max);
        }
    }

    public static String requestGame(String message) {
        Pattern pattern = Pattern.compile("[.0-9]{81}");
        while (true) {
            System.out.println(message);
            String game = scanner.nextLine();
            Matcher matcher = pattern.matcher(game);
            if (matcher.find()) {
                return game;
            }
            System.out.println("Por favor, insira o jogo no formato especificado");
        }
    }


}
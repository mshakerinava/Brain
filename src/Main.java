import java.util.Scanner;

public class Main {
    private static final boolean AI_SELF_PLAY = false;
    private static final int N_GAMES = 300000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Brain brain = new Brain();
        if (AI_SELF_PLAY) {
            for (int i = 0; i < N_GAMES; i += 1) {
                Board board = new Board();
                while (true) {
                    brain.act(board);
                    String winner = board.winner();
                    if (winner != null) {
                        brain.reward(board);
                        break;
                    }
                }
                if (i % 100 == 0)
                    System.out.println(i * 100.0 / N_GAMES + "%");
            }
//            brain.writeHistory();
        } else {
            Board board = new Board();
            int player = Board.O;
            System.out.println("Will you be X or O? [X/O]");
            if (scanner.nextLine().equals("X")) player = Board.X;
            while (true) {
                if (board.getTurn() == player) {
                    /* player's turn */
                    System.out.println("Where do you want to play? [row column]");
                    String[] move = scanner.nextLine().split(" ");
                    int row = Integer.parseInt(move[0]) - 1;
                    int col = Integer.parseInt(move[1]) - 1;
                    board.play(row, col);
                } else {
                    /* AI's turn */
                    brain.act(board);
                }
                board.print();
                String winner = board.winner();
                if (winner != null) {
                    String blankTok = Board.TOKEN[Board.BLANK];
                    if (winner.equals(blankTok)) System.out.println("Draw!");
                    else System.out.println(winner + " Wins!");
                    brain.reward(board);
                    break;
                }
            }
        }
    }
}
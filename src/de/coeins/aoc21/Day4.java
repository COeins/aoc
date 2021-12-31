package de.coeins.aoc21;

public class Day4 implements Day {
    public long task1(String input) {
        String[] inputlines = input.split("\n");
        int numcards = (inputlines.length - 1) / 6;
        BingoCard[] cards = new BingoCard[numcards];
        for (int i = 0; i < numcards; i++) {
            cards[i] = new BingoCard(inputlines, i * 6 + 2);
        }
        String[] numbers = inputlines[0].split(",");

        try {
            for (int i = 0; i < numbers.length; i++) {
                int n = Integer.parseInt(numbers[i]);

                for (int j = 0; j < numcards; j++) {
                    cards[j].nextNumber(n);
                }
            }
        } catch (BingoException e) {
            return e.getCard().calculateScore();
        }
        throw new RuntimeException("No Bingo :(");
    }

    public long task2(String input) {
        String[] inputlines = input.split("\n");
        int numcards = (inputlines.length - 1) / 6;
        BingoCard[] cards = new BingoCard[numcards];
        for (int i = 0; i < numcards; i++) {
            cards[i] = new BingoCard(inputlines, i * 6 + 2);
        }
        String[] numbers = inputlines[0].split(",");

        int lastscore = 0;
        for (int i = 0; i < numbers.length; i++) {
            int n = Integer.parseInt(numbers[i]);
            for (int j = 0; j < numcards; j++) {
                try {
                    if (cards[j] != null)
                        cards[j].nextNumber(n);
                } catch (BingoException e) {
                    lastscore = cards[j].calculateScore();
                    System.out.println("Bord " + j + " won with score " + lastscore);
                    cards[j] = null;
                }
            }
        }
        return lastscore;
    }

    private static class BingoCard {

        private Integer[][] numbers;
        private Boolean[][] checked;
        private int lastnum = -1;

        public BingoCard(String[] lines, int firstline) {
            numbers = new Integer[5][5];
            checked = new Boolean[5][5];

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    numbers[i][j] = Integer.valueOf(lines[i + firstline].substring(3 * j, 3 * j + 2).trim());
                    checked[i][j] = false;
                }
            }
        }

        public void nextNumber(int number) throws BingoException {

            if (lastnum >= 0) throw new BingoException(this);
            if (checkNumber(number) && checkBoard()) {
                lastnum = number;
                throw new BingoException(this);
            }
        }

        public int calculateScore() {
            if (lastnum < 0) return 0;
            int score = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (!checked[i][j]) score += numbers[i][j];
                }
            }
            return score * lastnum;
        }

        private boolean checkNumber(int number) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (numbers[i][j] == number) {
                        checked[i][j] = true;
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean print() {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (checked[i][j])
                        System.out.print(" X ");
                    else
                        System.out.printf(" %02d", numbers[i][j]);

                }
                System.out.println();
            }
            return false;
        }

        private boolean checkBoard() {
            for (int i = 0; i < 5; i++) {
                if (checked[i][0] && checked[i][1] && checked[i][2] && checked[i][3] && checked[i][4]
                        || checked[0][i] && checked[1][i] && checked[2][i] && checked[3][i] && checked[4][i])
                    return true;
            }
            return false;
        }
    }

    private static class BingoException extends Exception {
        private final BingoCard card;

        public BingoException(BingoCard card) {
            this.card = card;
        }

        public BingoCard getCard() {
            return card;
        }
    }
}

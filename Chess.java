package Chess;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static Chess.Chess.*;
import static java.lang.System.out;

public class Chess {

    static int defeatedIndex = -1, chessboardSize = 8;
    static int innerListMovesCounter;
    static char[][] chessboard = new char[chessboardSize][chessboardSize];
    static List<Character> defeatedFigures = new ArrayList<>();
    static List<MovesRegister> movesRegister = new ArrayList<>();
    static List<Figure> initialFiguresLayout = new ArrayList<>();
    static Horse horseBlackLeft, horseBlackRight;
    static Horse.WhiteHorse horseWhiteLeft;
    static Horse.WhiteHorse horseWhiteRight;
    static Bishop bishopBlackLeft, bishopBlackRight;
    static Bishop.WhiteBishop bishopWhiteLeft, bishopWhiteRight;
    static Rook rookBlackLeft, rookBlackRight;
    static Rook.WhiteRook rookWhiteLeft, rookWhiteRight;
    static Pawn pawnBlack1, pawnBlack2, pawnBlack3, pawnBlack4, pawnBlack5, pawnBlack6, pawnBlack7, pawnBlack8;
    static Pawn.WhitePawn pawnWhite1, pawnWhite2, pawnWhite3, pawnWhite4, pawnWhite5, pawnWhite6, pawnWhite7, pawnWhite8;
    static Queen queenBlack;
    static Queen.WhiteQueen queenWhite;
    static King kingBlack;
    static King.WhiteKing kingWhite;
    static char dot = '\u00b7';

    public static void main(String[] args) {
        initialFill();
        horseWhiteRight.moveUpAndRight();
        horseBlackLeft.moveDownAndLeft();
        pawnBlack2.move(2);
        pawnWhite5.move();
        bishopWhiteRight.move(3, 1, bishopWhiteRight);
        pawnBlack3.move();
        pawnBlack3.move(3, 1, pawnBlack3);
        //kingWhite.move(7,5, kingWhite);
        //kingWhite.castle(kingWhite, true);
        //kingWhite.castle(kingWhite, false);
        bishopBlackLeft.move(1,1,bishopBlackLeft);
        queenBlack.move(1,2,queenBlack);
        kingBlack.castle(kingBlack, false);
    }

    static void printDelimiter() {
        out.println("--------------------------------------");
    }

    static void checkMoveOnSamePosition(int startX, int startY, int targetX, int targetY) {
        if (startX == targetX && startY == targetY) {
            printDelimiter();
            out.println("Trying to move on the same position");
            printDelimiter();
            throw new UnsupportedOperationException();
        }
    }

    static void viewBoard() {
        printBoard();
        //printFiguresPositions();
        printDelimiter();
        printMovesRegister();
        printDelimiter();
        out.println(!defeatedFigures.isEmpty() ? "Defeated: " + defeatedFigures : "");
    }

    private static void printMovesRegister() {
        if (!movesRegister.isEmpty()) {
            out.println("---- MovesRegister ----");
            AtomicInteger movesCounter = new AtomicInteger();
            movesRegister.forEach(e -> out.println(movesCounter.incrementAndGet() + ". "
                    + e.getFigure().getFigureChar() + " (" + e.getStartX() + " : " + e.getStartY()
                    + ") -> (" + e.getTargetX() + " : " + e.getTargetY() + ") " + e.getMoveSpecialty()));
        }
        if (movesRegister.size() >= 20) {
            printBoard();
            //printFiguresPositions();
        }
    }

    static boolean isFigureWhite(Figure figure) {
        return figure.getClass().getName().contains("White");
    }

    /*private static void printFiguresPositions() {
        if (horse != null) horse.printCurrentPosition();
        if (rook != null) rook.printCurrentPosition();
        if (bishop != null) bishop.printCurrentPosition();
        if (pawn != null) pawn.printCurrentPosition();
        if (queen != null) queen.printCurrentPosition();
        if (kingBlack != null) kingBlack.printCurrentPosition();
        if (kingWhite != null) kingWhite.printCurrentPositionKingWhite();
        if (!defeatedFigures.isEmpty()) {
            out.println("Defeated: " + defeatedFigures);
        }
    }*/

    private static void printBoard() {
        out.print("   ");
        for (int i = 0; i < chessboardSize; i++) {
            out.print(i + "  ");
        }
        out.println("\n   ________________________");
        for (int i = 0; i < chessboardSize; i++) {
            out.print(i + " | ");
            for (int j = 0; j < chessboardSize; j++) {
                out.print(chessboard[i][j] == dot ? dot + "  " : chessboard[i][j] + " ");
            }
            if (!curRowContainsFigures(i)) {
                out.println(i);
            } else {
                out.println("|" + i);
            }
        }
        out.println("   ------------------------");
    }

    private static boolean curRowContainsFigures(int x) {
        for (int i = 0; i < chessboardSize; i++) {
            if (chessboard[x][i] != dot) {
                return true;
            }
        }
        return false;
    }

    private static void initialFill() {
        fetchInitialChessboardLayout();
        for (char[] chars : chessboard) {
            Arrays.fill(chars, dot);
        }
        deployInitialChessboard();
        viewBoard();
    }

    private static void deployInitialChessboard() {
        for (Figure unit : initialFiguresLayout) {
            chessboard[unit.getX()][unit.getY()] = unit.getFigureChar();
        }
    }

    private static void fetchInitialChessboardLayout() {
        horseBlackLeft = new Horse(0, 1, horseBlackLeft);
        horseBlackRight = new Horse(0, 6, horseBlackRight);
        horseWhiteLeft = new Horse.WhiteHorse(7, 1, horseWhiteLeft);
        horseWhiteRight = new Horse.WhiteHorse(7, 6, horseWhiteRight);
        bishopBlackLeft = new Bishop(0, 2, bishopBlackLeft);
        bishopBlackRight = new Bishop(0, 5, bishopBlackRight);
        bishopWhiteLeft = new Bishop.WhiteBishop(7, 2, bishopWhiteLeft);
        bishopWhiteRight = new Bishop.WhiteBishop(7, 5, bishopWhiteRight);
        rookBlackLeft = new Rook(0, 0, rookBlackLeft);
        rookBlackRight = new Rook(0, 7, rookBlackRight);
        rookWhiteLeft = new Rook.WhiteRook(7, 0, rookWhiteLeft);
        rookWhiteRight = new Rook.WhiteRook(7, 7, rookWhiteRight);
        pawnBlack1 = new Pawn(1, 0, pawnBlack1);
        pawnBlack2 = new Pawn(1, 1, pawnBlack2);
        pawnBlack3 = new Pawn(1, 2, pawnBlack3);
        pawnBlack4 = new Pawn(1, 3, pawnBlack4);
        pawnBlack5 = new Pawn(1, 4, pawnBlack5);
        pawnBlack6 = new Pawn(1, 5, pawnBlack6);
        pawnBlack7 = new Pawn(1, 6, pawnBlack7);
        pawnBlack8 = new Pawn(1, 7, pawnBlack8);
        pawnWhite1 = new Pawn.WhitePawn(6, 0, pawnWhite1);
        pawnWhite2 = new Pawn.WhitePawn(6, 1, pawnWhite2);
        pawnWhite3 = new Pawn.WhitePawn(6, 2, pawnWhite3);
        pawnWhite4 = new Pawn.WhitePawn(6, 3, pawnWhite4);
        pawnWhite5 = new Pawn.WhitePawn(6, 4, pawnWhite5);
        pawnWhite6 = new Pawn.WhitePawn(6, 5, pawnWhite6);
        pawnWhite7 = new Pawn.WhitePawn(6, 6, pawnWhite7);
        pawnWhite8 = new Pawn.WhitePawn(6, 7, pawnWhite8);
        queenBlack = new Queen(0, 3, queenBlack);
        queenWhite = new Queen.WhiteQueen(7, 3, queenWhite);
        kingBlack = new King(0, 4, kingBlack);
        kingWhite = new King.WhiteKing(7, 4, kingWhite);
        initialFiguresLayout.addAll(List.of(
                horseBlackLeft, horseBlackRight, horseWhiteLeft, horseWhiteRight,
                bishopBlackLeft, bishopBlackRight, bishopWhiteLeft, bishopWhiteRight,
                rookBlackLeft, rookBlackRight, rookWhiteLeft, rookWhiteRight,
                pawnBlack1, pawnBlack2, pawnBlack3, pawnBlack4, pawnBlack5, pawnBlack6,
                pawnBlack7, pawnBlack7, pawnBlack8, pawnWhite1, pawnWhite2, pawnWhite3,
                pawnWhite4, pawnWhite5, pawnWhite6, pawnWhite7, pawnWhite8,
                queenBlack, queenWhite, kingBlack, kingWhite));
    }
}

class Horse extends Figure {
    public Horse(int x, int y, Figure figure) {
        super(x, y, figure);
    }
    static class WhiteHorse extends Horse {
        public WhiteHorse(int x, int y, Figure figure) {
            super(x, y, figure);
        }
        protected static char figureChar = '\u265E';
        {
            setFigureChar(figureChar);
            setWhite(true);
        }
    }
    protected static char figureChar = '\u2658';
    {
        setFigureChar(figureChar);
    }
    @Override
        // not applicable
    boolean checkTransitClear(int x, int y) {
        return true;
    }
    /**
     * Horse move, unlike other figures logic, is provided in user-friendly manner, like moveUpAndDown,
     * where first direction token corresponds to longer part of the movement and the latter - shorter one.
     * therefore all functions hardcoded for usability. Thus utility methods receive data by means of
     * addition/substraction from current coordinates.
     *
     * @param x      incremental/decremental value <b>x</b>
     * @param y      incremental/decremental value <b>y</b>
     * @param figure figure instance that moves
     */
    @Override
    void move(int x, int y, Figure figure) {
        checkMoveOnSamePosition(getX(), getY(), x, y);
        var figureCharr = this.isWhite() ? WhiteHorse.figureChar : figureChar;
        if (!checkBoundaries(x, y)) {
            out.println("move " + x + ":" + y + " leads outside the chessboard");
            throw new UnsupportedOperationException();
        } else if (targetSquareContainsFigure(getX() + x, getY() + y)) {
        } else {
            chessboard[getX()][getY()] = dot;
            registerMove(getX(), getY(), x + getX(), y + getY(), this);
            setX(getX() + x);
            setY(getY() + y);
            chessboard[getX()][getY()] = figureCharr;
            viewBoard();
        }
    }

    @Override
    void printCurrentPosition() {
        if (getX() != defeatedIndex) {
            out.println(figureChar + " cur pos: (" + getX() + " : " + getY() + ")");
        }
    }

    void moveDownAndRight() {
        move(2, 1, this);
    }

    void moveRightAndDown() {
        move(1, 2, this);
    }

    void moveUpAndLeft() {
        move(-2, -1, this);
    }

    void moveUpAndRight() {
        move(-2, 1, this);
    }

    void moveDownAndLeft() {
        move(2, -1, this);
    }

    void moveLeftAndUp() {
        move(-1, -2, this);
    }

    void moveLeftAndDown() {
        move(1, -2, this);
    }
}

class Bishop extends Figure {
    static class WhiteBishop extends Bishop {

        public WhiteBishop(int x, int y, Figure figure) {
            super(x, y, figure);
        }

        protected static char figureChar = '\u265D';

        {
            setFigureChar(figureChar);
            setWhite(true);
        }
    }

    protected static char figureChar = '\u2657';

    {
        setFigureChar(figureChar);
    }

    public Bishop(int x, int y, Figure figure) {
        super(x, y, figure);
    }

    @Override
    boolean checkTransitClear(int x, int y) {
        //go north east
        if (getX() > x && getY() < y) {
            for (int a = getX() - 1, b = getY() + 1; a > x && b < y; a--, b++) {
                if (chessboard[a][b] != dot) {
                    calloutIfOccupied(a, b);
                }
            }
        }
        //go north west
        else if (getX() > x && getY() > y) {
            for (int a = getX() - 1, b = getY() - 1; a > x && b > y; a--, b--) {
                if (chessboard[a][b] != dot) {
                    calloutIfOccupied(a, b);
                }
            }
        }

        //go south east
        else if (getX() < x && getY() < y) {
            for (int a = getX() + 1, b = getY() + 1; a < x && b < y; a++, b++) {
                if (chessboard[a][b] != dot) {
                    calloutIfOccupied(a, b);
                }
            }
        }

        //go south west
        else {
            for (int a = getX() + 1, b = getY() - 1; a < x && b < y; a++, b--) {
                if (chessboard[a][b] != dot) {
                    calloutIfOccupied(a, b);
                }
            }
        }
        return true;
    }

    private static void calloutIfOccupied(int a, int b) {
        out.println("Pos " + a + " : " + b + " is occupied by " + chessboard[a][b] + ", cannot jump");
        printDelimiter();
        throw new IllegalStateException();
    }

    @Override
    void move(int x, int y, Figure figure) {
        var figureCharr = figure.isWhite() ? Bishop.WhiteBishop.figureChar : figureChar;
        if (!isMoveDiagonal(x, y)) {
            out.println(figureCharr + " moves in diagonal");
            printDelimiter();
        } else if (!checkBoundaries(x, y)) {
            out.println(figureCharr + " move " + x + ":" + y + " leads outside the chessboard");
        } else if (!checkTransitClear(x, y)) {
        } else if (targetSquareContainsFigure(x, y)) {
        } else {
            chessboard[getX()][getY()] = dot;
            registerMove(getX(), getY(), x, y, this);
            setX(x);
            setY(y);
            chessboard[getX()][getY()] = figureCharr;
            viewBoard();
        }
    }

    @Override
    boolean checkBoundaries(int x, int y) {
        return x < chessboardSize && y < chessboardSize && x > -1 && y > -1;
    }

    @Override
    void printCurrentPosition() {
        if (getX() != defeatedIndex) {
            out.println(figureChar + " cur pos: (" + getX() + " : " + getY() + ")");
        }
    }

    /*void moveUpAndLRight(int x, int y) {
        move(x, y);
    }

    void moveDownAndLeft(int x, int y) {
        move(x, y);
    }

    void moveUpAndLeft(int z) {
        move(convertVectorCoordX(z),
                convertVectorCoordY(z));
    }

    void moveDownRight(int z) {
        move(convertVectorCoordX(z),
                convertVectorCoordY(z));
    }

    int convertVectorCoordY(int z) {
        return (chessboardSize - getY() + z - 1) % chessboardSize;
    }

    int convertVectorCoordX(int z) {
        return (chessboardSize - getX() + z - 1) % chessboardSize;
    }*/
}

class Rook extends Figure {
    static class WhiteRook extends Rook {
        public WhiteRook(int x, int y, Figure figure) {
            super(x, y, figure);
        }
        protected static char figureChar = '\u265C';
        {
            setFigureChar(figureChar);
            setWhite(true);
        }
    }

    protected static char figureChar = '\u2656';

    {
        setFigureChar(figureChar);
    }

    public Rook(int x, int y, Figure figure) {
        super(x, y, figure);
    }

    @Override
    boolean checkTransitClear(int x, int y) {
        if (y > getY()) { //go right
            for (int i = getY() + 1; i <= y; i++) {
                if (chessboard[x][i] != dot) {
                    return false;
                }
            }
            return true;
        } else if (y < getY()) {// go left
            for (int i = getY() - 1; i > y; i--) {
                if (chessboard[x][i] != dot) {
                    return false;
                }
            }
            return true;
        } else if (x > getX()) {// go down
            for (int i = getX() + 1; i < x; i++) {
                if (chessboard[i][y] != dot) {
                    return false;
                }
            }
            return true;
        } else { // go up
            for (int i = getX() - 1; i > x; i--) {
                if (chessboard[i][y] != dot) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    void move(int x, int y, Figure figure) {
        var figureCharr = figure.isWhite() ? Rook.WhiteRook.figureChar : figureChar;
        if (!isMoveHorizontalOrVertical(x, y)) {
            out.println(figureCharr + " moves only vertically or horisontally, not in diagonal");
            printDelimiter();
            throw new UnsupportedOperationException();
        } else if (!checkBoundaries(x, y)) {
            out.println(figureCharr + " move " + x + ":" + y + " leads outside the chessboard");
            printDelimiter();
            throw new UnsupportedOperationException();
        } else if (!checkTransitClear(x, y)) {
            out.println("There's a figure in the way of " + figureCharr + " move at " + x + ":" + y);
            printDelimiter();
            throw new UnsupportedOperationException();
        } else if (targetSquareContainsFigure(x, y)) {
        } else {
            chessboard[getX()][getY()] = dot;
            chessboard[x][y] = figureCharr;
            registerMove(getX(), getY(), x, y, this);
            setX(x);
            setY(y);
            viewBoard();
        }
    }

    @Override
    boolean checkBoundaries(int x, int y) {
        return x < chessboardSize && x > -1 && y > -1 && y < chessboardSize;
    }

    @Override
    void printCurrentPosition() {
        if (getX() != defeatedIndex) {
            out.println(figureChar + " cur pos: (" + getX() + " : " + getY() + ")");
        }
    }
}

class Pawn extends Figure {
    static class WhitePawn extends Pawn {

        @Override
        boolean checkTransitClear(int moveStep) {
            return chessboard[getX() - moveStep][getY()] == dot;
        }

        @Override
        boolean checkMaxMovementStepFromStartingSquareOnly(int movementStep) {
            return movementStep == 2 ? getX() == 6 : movementStep == 1;
        }

        @Override
        boolean checkBoundaries(int movementStep) {
            var newCoordinate = getX() - movementStep;
            if (newCoordinate < -1) {
                out.println(figureChar + " move " + newCoordinate + ":" + getY() + " leads outside the chessboard");
                printDelimiter();
                return false;
            } else if (movementStep > 2) {
                out.println(figureChar + " is not allowed to move more than 2 squares forward");
                return false;
            }
            return true;
        }

        public WhitePawn(int x, int y, Figure figure) {
            super(x, y, figure);
        }
        protected static char figureChar = '\u265F';
        {
            setFigureChar(figureChar);
            setWhite(true);
        }
    }

    protected static char figureChar = '\u2659';
    {
        setFigureChar(figureChar);
    }
    public Pawn(int x, int y, Figure figure) {
        super(x, y, figure);
    }

    static boolean checkPawnPosition(int y) {
        for (int i = 0; i < chessboardSize; i++) {
            if (chessboard[chessboardSize - 1][y] == figureChar) {
                return true;
            }
        }
        return false;
    }

    @Override
    boolean checkTransitClear(int x, int y) {
        return true;
    }

    boolean checkTransitClear(int moveStep) {
        return chessboard[getX() + moveStep][getY()] == dot;
    }

    boolean checkBoundaries(int movementStep) {
        var newCoordinate = getX() + movementStep;
        if (newCoordinate > chessboardSize) {
            out.println(figureChar + " move " + newCoordinate + ":" + getY() + " leads outside the chessboard");
            printDelimiter();
            return false;
        } else if (movementStep > 2) {
            out.println(figureChar + " is not allowed to move more than 2 squares forward");
            return false;
        } else if (movementStep < 0) {
            out.println(figureChar + " is allowed to move ONLY FORWARD");
            return false;
        } else {
            return true;
        }
    }

    @Override
    void move(int targetX, int targetY, Figure figure) {
        var figureCharr = this.isWhite() ? WhitePawn.figureChar : figureChar;
        checkMoveOnSamePosition(this.getX(), this.getY(), targetX, targetY);
        if (isMoveDiagonal(targetX, targetY) && Math.abs(getX() - targetX) == 1 && Math.abs(getY() - targetY) == 1 &&
                targetSquareContainsFigure(targetX, targetY)) {
        } else if (getX() < targetX && this.isWhite() || getX() > targetX && !this.isWhite()) {
            out.println("seems like you're trying to move " + figureCharr + " backwards");
        } else {
            out.println(figureCharr + " could move diagonal only in attack");
            throw new UnsupportedOperationException();
        }
    }

    void move(int movementStep) {
        var figureCharr = this.isWhite() ? WhitePawn.figureChar : figureChar;
        int newCoordinate = this.isWhite() ? getX() - movementStep : getX() + movementStep;
        if (newCoordinate == chessboardSize - 1 || newCoordinate == 0) {
            out.println(figureCharr + " has successfully reached top of the chessboard " +
                    "- you may choose any figure instead");
            printDelimiter();
            proceedMovementRegistration(chessboardSize - 1);
            elevatePawnToNewQueen(getY());
            viewBoard();
        } else if (!checkBoundaries(movementStep)) {
        } else if (!checkTransitClear(movementStep)) {
            out.println("There's " + chessboard[newCoordinate][getY()] + " in the way of " +
                    figureCharr + " move " + newCoordinate + ":" + getY());
            printDelimiter();
        } else if (!checkMaxMovementStepFromStartingSquareOnly(movementStep)) {
            out.println(figureCharr + " is allowed to make extended move only from starting position");
            printDelimiter();
        } else {
            proceedMovementRegistration(newCoordinate);
        }
    }

    void move() {
        move(1);
    }

    private void proceedMovementRegistration(int newCoordinate) {
        chessboard[newCoordinate][getY()] = this.isWhite() ? Pawn.WhitePawn.figureChar : figureChar;
        chessboard[getX()][getY()] = dot;
        registerMove(getX(), getY(), newCoordinate, getY(), this);
        setX(newCoordinate);
        viewBoard();
    }

    boolean checkMaxMovementStepFromStartingSquareOnly(int movementStep) {
        return movementStep == 2 ? getX() == 1 : movementStep == 1;
    }

    @Override
    void printCurrentPosition() {
        if (getX() != defeatedIndex) {
            out.println(figureChar + " cus pos: (" + getX() + " : " + getY() + ")");
        }
    }

    void elevatePawnToNewQueen(int y) { //todo implement logic for choosing any figure
        if (checkPawnPosition(y)) {
            chessboard[getX()][y] = Queen.figureChar;
            Queen queenNew;
            if (isFigureWhite(this)) {
                queenNew = new Queen.WhiteQueen(getX(), getY(), this);
            } else {
                queenNew = new Queen(getX(), getY(), this);
            }
            setX(-1);
            setY(-1);
            out.println(figureChar + " has been elevated to " + queenNew.getFigureChar());
            printDelimiter();
            defeatedFigures.add(figureChar);
        }
    }
}

class Queen extends Figure {
    static class WhiteQueen extends Queen {

        public WhiteQueen(int x, int y, Figure figure) {
            super(x, y, figure);
        }

        protected static char figureChar = '\u265B';

        {
            setFigureChar(figureChar);
            setWhite(true);
        }
    }

    protected static char figureChar = '\u2655';

    {
        setFigureChar(figureChar);
    }

    public Queen(int x, int y, Figure figure) {
        super(x, y, figure);
    }

    @Override
    boolean checkTransitClear(int x, int y) {
        if (isMoveHorizontalOrVertical(x, y)) { //horizontal
            return rookBlackLeft.checkTransitClear(x, y);
        } else if (isMoveDiagonal(x, y)) {
            return bishopBlackRight.checkTransitClear(x, y);
        }
        return true;
    }

    @Override
    boolean checkBoundaries(int x, int y) {
        return x < chessboardSize && y < chessboardSize && x > -1 && y > -1;
    }

    @Override
    void move(int x, int y, Figure figure) {
        var figureCharr = figure.isWhite() ? Queen.WhiteQueen.figureChar : figureChar;
        if (!isMoveHorizontalOrVertical(x, y) && !isMoveDiagonal(x, y)) {
            out.println(figureCharr + " moves in diagonal, vertical or horizontal");
            printDelimiter();
            throw new IllegalStateException();
        } else if (!checkBoundaries(x, y)) {
            out.println(figureCharr + " move " + x + ":" + y + " leads outside the chessboard");
            printDelimiter();
        } else if (!checkTransitClear(x, y)) {
            out.println("There's a figure in the way of " + figureCharr + " move at " + x + ":" + y);
            printDelimiter();
        } else if (targetSquareContainsFigure(x, y)) {
        } else {
            chessboard[getX()][getY()] = dot;
            chessboard[x][y] = figureCharr;
            registerMove(getX(), getY(), x, y, this);
            setX(x);
            setY(y);
            viewBoard();
        }
    }

    @Override
    void printCurrentPosition() {
        if (getX() != defeatedIndex) {
            out.println(figureChar + " cur pos: (" + getX() + " : " + getY() + ")");
        }
    }
}

class King extends Figure {
    public void castle(King king, boolean isCastlingShort) {
        if (!proveFigureNeverMoved(king)) {
            out.println(king.getFigureChar() + " has already moved, castling will not take place");
            throw new UnsupportedOperationException();
        } else if (!proveFigureNeverMoved(rookWhiteRight)) {
            out.println(rookWhiteRight.getFigureChar() + " has already moved, castling will not take place");
            throw new UnsupportedOperationException();
        } else if (!proveFreeSpaceToTheRook(king, isCastlingShort)) {
            out.println("Space between " + king.getFigureChar() + " " + rookWhiteRight.getFigureChar() + " is taken");
            throw new UnsupportedOperationException();
        }

        if (king.isWhite()) {
            chessboard[getX()][getY()] = dot;
            if (isCastlingShort) {
                registerMove(getX(), getY(), getX(), getY() + 2, king, "R");
                registerMove(rookWhiteRight.getX(), rookWhiteRight.getY(), rookWhiteRight.getX(), rookWhiteRight.getY() - 2, rookWhiteRight, "R");
                setY(getY() + 2);
                chessboard[rookWhiteRight.getX()][rookWhiteRight.getY()] = dot;
                chessboard[getX()][getY()] = getFigureChar();
                chessboard[rookWhiteRight.getX()][rookWhiteRight.getY() - 2] = rookWhiteRight.getFigureChar();
                rookWhiteRight.setY(rookWhiteRight.getY() - 2);
            } else {//castling Long, King is White
                setY(getY() - 3);
                registerMove(getX(), getY(), getX(), getY() - 3, king, "R");
                registerMove(rookWhiteLeft.getX(), rookWhiteLeft.getY(), rookWhiteLeft.getX(), rookWhiteLeft.getY() + 2, rookWhiteLeft);
                chessboard[getX()][getY()] = getFigureChar();
                chessboard[rookWhiteLeft.getX()][rookWhiteLeft.getY()] = dot;
                rookWhiteLeft.setY(rookWhiteLeft.getY() + 2);

            }
        } else { //king is Black
            chessboard[getX()][getY()] = dot;
            chessboard[rookWhiteRight.getX()][rookWhiteRight.getY() - 2] = dot;
            if (isCastlingShort) {
                registerMove(getX(), getY(), getX(), getY() + 2, king, "R");
                registerMove(rookBlackRight.getX(), rookBlackRight.getY(), rookBlackRight.getX(), rookBlackRight.getY() - 2, rookBlackRight, "R");
                chessboard[getX()][getY()] = dot;
                setY(getY() + 2);
                chessboard[rookBlackRight.getX()][rookBlackRight.getY()] = dot;
                chessboard[rookWhiteRight.getX()][rookWhiteRight.getY() - 2] = rookWhiteRight.getFigureChar();
                rookBlackRight.setY(rookBlackRight.getY() - 2);
                chessboard[getX()][getY()] = getFigureChar();
            } else { //castling Long, King is Black
                registerMove(getX(), getY(), getX(), getY() - 3, king, "R");
                registerMove(rookWhiteLeft.getX(), rookWhiteLeft.getY(), rookWhiteLeft.getX(), rookWhiteLeft.getY() + 2, rookWhiteLeft, "R");
                setY(getY() - 3);
                chessboard[getX()][getY()] = getFigureChar();
                chessboard[rookBlackLeft.getX()][rookBlackLeft.getY()] = dot;
                rookWhiteLeft.setY(rookWhiteLeft.getY() + 2);
                chessboard[rookBlackLeft.getX()][rookBlackLeft.getY() + 2] = rookBlackLeft.getFigureChar();

            }
        }
        printDelimiter();
        out.println("Castling of " + figureChar + " has been done");
        printDelimiter();
        viewBoard();
    }

    private boolean proveFreeSpaceToTheRook(Figure figure, boolean isCastlingShort) {
        if (figure.isWhite()) {
            return isCastlingShort ? chessboard[figure.getX()][figure.getY() + 1] == dot
                    && chessboard[figure.getX()][figure.getY() + 2] == dot
                    : chessboard[figure.getX()][figure.getY() - 1] == dot
                    && chessboard[figure.getX()][figure.getY() - 2] == dot
                    && chessboard[figure.getX()][figure.getY() - 3] == dot;
        } else return isCastlingShort ? chessboard[figure.getX()][figure.getY() + 1] == dot
                && chessboard[figure.getX()][figure.getY() + 2] == dot
                : chessboard[figure.getX()][figure.getY() - 1] == dot
                && chessboard[figure.getX()][figure.getY() - 2] == dot
                && chessboard[figure.getX()][figure.getY() - 3] == dot;
    }

    private boolean proveFigureNeverMoved(Figure figure) {
        return movesRegister.stream()
                .filter(e -> e.getFigure() == figure)
                .findFirst().isEmpty();
    }

    static class WhiteKing extends King {
        public WhiteKing(int x, int y, Figure figure) {
            super(x, y, figure);
        }
        protected static char figureChar = '\u265A';
        {
            setFigureChar(figureChar);
            setWhite(true);
        }
    }

    protected static char figureChar = '\u2654';
    {
        setFigureChar(figureChar);
    }

    protected King(int x, int y, Figure figure) {
        super(x, y, figure);
    }

    @Override
    boolean checkTransitClear(int x, int y) {
        return false;
    }

    @Override
    void move(int x, int y, Figure figure) {
        var figureCharr = figure.isWhite() ? King.WhiteKing.figureChar : figureChar;
        checkMoveOnSamePosition(getX(), getY(), x, y);
        var checkFigure = detectCheckByFigure(x, y);
        if (!checkBoundaries(x, y)) {
            out.println(figureCharr + " move " + x + " : " + y + " leads outside the chessboard");
            printDelimiter();
        } else if (Math.abs(x - getX()) > 1 || Math.abs(y - getY()) > 1) {
            out.println(figureCharr + " moves only one square");
            printDelimiter();
            throw new IllegalStateException();
        } else if (chessboard[x][y] != dot) {
            printDelimiter();
            targetSquareContainsFigure(x, y);
        } else if (checkFigure != dot) {
            out.println("The square " + x + " : " + y + " is under the check of " + checkFigure + ", unable to move");
            printDelimiter();
            throw new IllegalStateException();
        } else {
            chessboard[x][y] = figureCharr;
            chessboard[getX()][getY()] = dot;
            registerMove(getX(), getY(), x, y, this);
            setX(x);
            setY(y);
            viewBoard();
        }
    }

    private char detectCheckByFigure(int x, int y) {
        if (checkHorses(x, y)) return Horse.figureChar;
        else if (!checkRooks(x, y)) return Rook.figureChar;
        else if (!checkQueens(x, y)) return Queen.figureChar;
        else if (checkPawns(x, y)) return Pawn.figureChar;
        else if (!checkBishops(x, y)) return Bishop.figureChar;
        else if (checkKingBlacks(x, y)) return figureChar;
        else return dot;
    }

    private boolean checkHorses(int x, int y) {
        char charr = Horse.figureChar;
        if (x + 1 < chessboardSize && y - 2 > -1 && chessboard[x + 1][y - 2] == charr) { // Nord Nord East
            return true;
        } else if (x - 2 > -1 && y - 1 > -1 && chessboard[x - 2][y - 1] == charr) { //Nord Nord West
            return true;
        } else if (x - 1 > -1 && y - 2 > -1 && chessboard[x - 1][y - 2] == charr) { // Nord West West
            return true;
        } else if (x - 1 > -1 && y + 2 < chessboardSize && chessboard[x - 1][y + 2] == charr) { // Nord East East
            return true;
        } else if (x - 2 > -1 && y + 1 < chessboardSize && chessboard[x - 2][y + 1] == charr) { // South West West
            return true;
        } else if (x + 2 < chessboardSize && y - 1  > -1 && chessboard[x + 2][y - 1] == charr) { // South South West)
            return true;
        } else if (x + 1 < chessboardSize && y + 2 < chessboardSize && chessboard[x + 1][y + 2] == charr) { // South South East)
            return true;
        } else
            return x + 2 < chessboardSize && y + 1 < chessboardSize && chessboard[x + 2][y + 1] == charr; // South South East
    }

    private boolean checkKingBlacks(int x, int y) {
        return x - 1 < chessboardSize && y + 1 < chessboardSize && chessboard[x - 1][y + 1] == WhiteKing.figureChar &&
                x < chessboardSize && chessboard[x][y + 1] == WhiteKing.figureChar &&
                x + 1 < chessboardSize && chessboard[x + 1][y + 1] == WhiteKing.figureChar;
    }

    private boolean checkBishops(int x, int y) {
        //check North East
        for (int i = x, j = y; i > -1 && j < chessboardSize; i--, j++) {
            if (chessboard[i][j] == Bishop.figureChar) {
                return false;
            }
        }//check South East
        for (int i = x, j = y; i < chessboardSize && j < chessboardSize; i++, j++) {
            if (chessboard[i][j] == Bishop.figureChar) {
                return false;
            }
        }
        //check North West
        for (int i = x, j = y; i > -1 && j > -1; i--, j--) { //todo test it
            if (chessboard[i][j] == Bishop.figureChar) {
                return false;
            }
        }
        //check South West
        for (int i = x, j = y; i < chessboardSize && j > -1; i++, j--) { //todo test it
            if (chessboard[i][j] == Bishop.figureChar) {
                return false;
            }
        }
        return true;

    }

    private boolean checkPawns(int x, int y) {
        if (x + 1 < chessboardSize && y - 1 > -1 && chessboard[x + 1][y - 1] == Pawn.figureChar) { //check NE
            return true;
        } else if (x - 1 > -1 && y - 1 > -1 && chessboard[x - 1][y - 1] == Pawn.figureChar) { //check NW
            return true;
        } else if (x + 1 < chessboardSize && y + 1 < chessboardSize && chessboard[x + 1][y + 1] == Pawn.figureChar) {//check SE
            return true;
        } else return x - 1 > -1 && y + 1 < chessboardSize && chessboard[x - 1][y + 1] == Pawn.figureChar; //check SW
    }

    private boolean checkQueens(int x, int y) {
        return checkRooks(x, y) || checkBishops(x, y);
    }

    private boolean checkRooks(int x, int y) {

        //check horizontal to the right
        for (int i = getY(); i < chessboardSize; i++) {
            if (chessboard[x][i] == Rook.figureChar) {
                return false;
            }
        }
        //check horizontal to the left
        for (int i = getY(); i > -1; i--) {
            if (chessboard[x][i] == Rook.figureChar) {
                return false;
            }
        }
        //check vertical down
        for (int i = getX(); i < chessboardSize; i++) {
            if (chessboard[i][y] == Rook.figureChar) {
                return false;
            }
        }
        //check vertical up
        for (int i = getX(); i > -1; i--) {
            if (chessboard[i][y] == Rook.figureChar) {
                return false;
            }
        }
        return true;
    }

    @Override
    boolean checkBoundaries(int x, int y) {
        return x < chessboardSize && y < chessboardSize && x > -1 && y > -1;
    }

    @Override
    void printCurrentPosition() {
        if (getX() != defeatedIndex) {
            out.println(figureChar + " cur pos: (" + getX() + " : " + getY() + ")");
        }
    }

    void printCurrentPositionKingWhite() {
        if (kingBlack.getX() != defeatedIndex) {
            out.println(WhiteKing.figureChar + " cur pos: (" + kingBlack.getX() + " : " + kingBlack.getY() + ")");
        }
    }
}

abstract class Figure {
    private int x, y;
    private boolean isWhite;
    private char figureChar;
    private Figure figure;

    public Figure(int x, int y, Figure figure) {
        this.x = x;
        this.y = y;
        this.figure = figure;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    public void setFigureChar(char figureChar) {
        this.figureChar = figureChar;
    }

    public char getFigureChar() {
        return figureChar;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Checks move for being provisioned within the chessboard
     *
     * @param x coordinate (and 'y') for target cell to move in
     * @return true if move stays inside, false - trespasses board's measures
     */
    boolean checkBoundaries(int x, int y) {
        return getX() + x < chessboardSize &&
                getX() + x > -1 &&
                getY() + y < chessboardSize &&
                getY() + y > -1;
    }

    abstract boolean checkTransitClear(int x, int y);

    abstract void move(int x, int y, Figure figure);

    abstract void printCurrentPosition();


    static void registerMove(int startX, int startY, int targetX, int targetY, Figure figure) {
        movesRegister.add(new MovesRegister(startX, startY, targetX, targetY, figure, ""));
        out.println(++innerListMovesCounter + ". Moving " + figure.getFigureChar() + " ("
                + figure.getX() + " : " + figure.getY() + ") -> (" + targetX + " : " + targetY + ")");
    }

    static void registerMove(int startX, int startY, int targetX, int targetY, Figure figure, String moveSpecialty) {
        movesRegister.add(new MovesRegister(startX, startY, targetX, targetY, figure, moveSpecialty));
        out.println(++innerListMovesCounter + ". Moving " + figure.getFigureChar() + " ("
                + figure.getX() + " : " + figure.getY() + ") -> (" + targetX + " : " + targetY + ") " + moveSpecialty);
    }

    /**
     * Checks whether target Square contains a figure. If so, proceed with figure substitution and callout
     *
     * @param targetX - coordinate X of target square
     * @param targetY - coordinate Y of target square
     * @return <b>true<i></i></b> if no attackable figure available on target square
     * @throws NullPointerException if any figure has not been instantiated in prior.
     */
    boolean targetSquareContainsFigure(int targetX, int targetY) {
        var attackedSquare = chessboard[targetX][targetY];
        var attackedFigure = retrieveFigureAtAttackSquare(targetX, targetY);
        if (attackedSquare == dot && attackedFigure.isEmpty()) {
            return false;
        } else if (attackedSquare == King.figureChar || attackedSquare == King.WhiteKing.figureChar
            /*attackedFigure instanceof King*/) {
            out.println("You cannot kill King, only check (-mate) is allowed");
            printDelimiter();
            throw new UnsupportedOperationException("King Kill Attempt");
        } else if (!checkIfFiguresColorsIdentical(attackedFigure.orElseThrow(), this)) {
            printDelimiter();
            out.println(getFigureChar() + " kills " + attackedSquare + " at pos (" + targetX + " : " + targetY + ")");
            printDelimiter();
            defeatedFigures.add(attackedSquare);
            chessboard[targetX][targetY] = getFigureChar();
            chessboard[getX()][getY()] = dot;
            registerMove(getX(), getY(), targetX, targetY, this, "A");
            viewBoard();
        }
        return true;
    }

    private static boolean checkIfFiguresColorsIdentical(Figure attackedFigure, Figure attackingFigure) {
        if (attackedFigure == null) {
            out.println("attacked figure is null");
            return false;
        } else if (isFigureWhite(attackedFigure) && isFigureWhite(attackingFigure)
                || !isFigureWhite(attackedFigure) && !isFigureWhite(attackingFigure)) {
            out.println("Unable to attack figure of the same color");
            return true;
        }
        return false;
    }

    private static Optional<Figure> retrieveFigureAtAttackSquare(int x, int y) {
        return movesRegister.stream()
                .filter(e -> e.getTargetX() == x && e.getTargetY() == y)
                .max(Comparator.comparing(e -> e.getOrderNum()))
                .map(e -> e.getFigure());
    }

    boolean isMoveHorizontalOrVertical(int x, int y) {
        return getX() == x && getY() != y || getX() != x && getY() == y;
    }

    boolean isMoveDiagonal(int x, int y) {
        return Math.abs(x - getX()) == Math.abs(y - getY());
    }
}

class MovesRegister {
    private char figureChar;
    private int startX, startY, targetX, targetY, orderNum;
    public String getMoveSpecialty() {
        return moveSpecialty;
    }

    public void setMoveSpecialty(String moveSpecialty) {
        this.moveSpecialty = moveSpecialty;
    }

    private String moveSpecialty;

    public int getOrderNum() {
        return orderNum;
    }

    private Figure figure;

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public char getFigureChar() {
        return figureChar;
    }

    public void setFigureChar(char figureChar) {
        this.figureChar = figureChar;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public Figure getFigure() {
        return figure;
    }

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public MovesRegister(int startX, int startY, int targetX, int targetY, Figure figure, String moveSpecialty) {
        this.startX = startX;
        this.startY = startY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.figure = figure;
        orderNum = innerListMovesCounter;
        this.moveSpecialty = moveSpecialty;
    }
}
package Chess;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static Chess.Chess.*;
import static java.lang.System.out;

public class Chess {

    static int defeatedIndex = -1, chessboardSize = 8, innerListMovesCounter, lastCheckFigureCoordX = -1, lastCheckFigureCoordY = -1;
    static char[][] chessboard = new char[chessboardSize][chessboardSize];
    static char dot = '\u00b7', lastCheckFigureChar;
    static List<Character> defeatedWhiteFigures = new ArrayList<>(), defeatedBlackFigures = new ArrayList<>();
    static List<MovesRegister> movesRegister = new ArrayList<>();
    static List<Figure> initialFiguresLayout = new ArrayList<>();
    static Horse horseBlackLeft, horseBlackRight;
    static Horse.WhiteHorse horseWhiteLeft, horseWhiteRight;
    static Bishop bishopBlackLeft, bishopBlackRight;
    static Bishop.WhiteBishop bishopWhiteLeft, bishopWhiteRight;
    static Rook rookBlackLeft, rookBlackRight;
    static Rook.WhiteRook rookWhiteLeft, rookWhiteRight;
    static Pawn pawnBlack1, pawnBlack2, pawnBlack3, pawnBlack4, pawnBlack5, pawnBlack6, pawnBlack7, pawnBlack8;
    static Pawn.WhitePawn pawnWhite1, pawnWhite2, pawnWhite3, pawnWhite4, pawnWhite5, pawnWhite6, pawnWhite7, pawnWhite8;
    static Queen queenBlack;
    static Queen.WhiteQueen queenWhite;
    static Figure queenNew;
    static King kingBlack;
    static King.WhiteKing kingWhite;


    public static void main(String[] args) {
        initialFill();
        out.println("Is stalemate at cur pos : " + kingBlack.isStalemate(false));

    }

    static void printDelimiter() {
        out.println("--------------------------------------");
    }

    static void viewBoard() {
        printBoard();
        //printFiguresPositions();
        printDelimiter();
        printMovesRegister();
        out.println();
        printDelimiter();
        out.println(!defeatedWhiteFigures.isEmpty() ? "Whites: " + defeatedWhiteFigures : "");
        out.println(!defeatedBlackFigures.isEmpty() ? "Blacks: " + defeatedBlackFigures : "");
    }

    static void printMovesRegister() {
        if (!movesRegister.isEmpty()) {
            out.println("---- MovesRegister ----");
            AtomicInteger movesCounter = new AtomicInteger();
            movesRegister.forEach(e -> out.println(
                    movesCounter.incrementAndGet() + ". "
                            + e.getFigure().getFigureChar()
                            + " (" + e.getStartX() + " : "
                            + e.getStartY() + ") -> (" +
                            e.getTargetX() + " : " +
                            e.getTargetY() + ") "
                            + e.getMoveSpecialty()));
        }
        if (movesRegister.size() >= 20) {
            printBoard();
            //printFiguresPositions();
        }
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
        printDelimiter();
        out.print("    ");
        for (int i = 0; i < chessboardSize; i++) {
            out.print(i + "  ");
        }
        out.println("\n   ________________________");
        for (int i = 0; i < chessboardSize; i++) {
            out.print(i + " | ");
            for (int j = 0; j < chessboardSize; j++) {
                out.print(chessboard[i][j] == dot ? dot + "  " : chessboard[i][j] + " ");
            }
            out.println(!curRowContainsFigures(i) ? i : "|" + i);
        }
        out.println("   ------------------------");
        out.print("    ");
        for (int i = 0; i < chessboardSize; i++) {
            out.print(i + "  ");
        }
        out.println();
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
        //initStartChessboardLayout();
        initStalemateLayout();
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

    private static void initStartChessboardLayout() {
        horseBlackLeft = new Horse(0, 1);
        horseBlackRight = new Horse(0, 6);
        horseWhiteLeft = new Horse.WhiteHorse(7, 1);
        horseWhiteRight = new Horse.WhiteHorse(7, 6);
        bishopBlackLeft = new Bishop(0, 2);
        bishopBlackRight = new Bishop(0, 5);
        bishopWhiteLeft = new Bishop.WhiteBishop(7, 2);
        bishopWhiteRight = new Bishop.WhiteBishop(7, 5);
        rookBlackLeft = new Rook(0, 0);
        rookBlackRight = new Rook(0, 7);
        rookWhiteLeft = new Rook.WhiteRook(7, 0);
        rookWhiteRight = new Rook.WhiteRook(7, 7);
        pawnBlack1 = new Pawn(1, 0);
        pawnBlack2 = new Pawn(1, 1);
        pawnBlack3 = new Pawn(1, 2);
        pawnBlack4 = new Pawn(1, 3);
        pawnBlack5 = new Pawn(1, 4);
        pawnBlack6 = new Pawn(1, 5);
        pawnBlack7 = new Pawn(1, 6);
        pawnBlack8 = new Pawn(1, 7);
        pawnWhite1 = new Pawn.WhitePawn(6, 0);
        pawnWhite2 = new Pawn.WhitePawn(6, 1);
        pawnWhite3 = new Pawn.WhitePawn(6, 2);
        pawnWhite4 = new Pawn.WhitePawn(6, 3);
        pawnWhite5 = new Pawn.WhitePawn(6, 4);
        pawnWhite6 = new Pawn.WhitePawn(6, 5);
        pawnWhite7 = new Pawn.WhitePawn(6, 6);
        pawnWhite8 = new Pawn.WhitePawn(6, 7);
        queenBlack = new Queen(0, 3);
        queenWhite = new Queen.WhiteQueen(7, 3);
        kingBlack = new King(0, 4);
        kingWhite = new King.WhiteKing(7, 4);
        initialFiguresLayout.addAll(List.of(horseBlackLeft, horseBlackRight, horseWhiteLeft, horseWhiteRight, bishopBlackLeft, bishopBlackRight, bishopWhiteLeft, bishopWhiteRight, rookBlackLeft, rookBlackRight, rookWhiteLeft, rookWhiteRight, pawnBlack1, pawnBlack2, pawnBlack3, pawnBlack4, pawnBlack5, pawnBlack6, pawnBlack7, pawnBlack7, pawnBlack8, pawnWhite1, pawnWhite2, pawnWhite3, pawnWhite4, pawnWhite5, pawnWhite6, pawnWhite7, pawnWhite8, queenBlack, queenWhite, kingBlack, kingWhite));
    }

    private static void initStalemateLayout() {
        queenWhite = new Queen.WhiteQueen(0, 2);
        kingBlack = new King(1, 0);
        kingWhite = new King.WhiteKing(6, 6);
        rookWhiteLeft = new Rook.WhiteRook(5, 1);
        pawnBlack4 = new Pawn(1, 3);
        initialFiguresLayout.addAll(List.of(queenWhite, kingBlack, kingWhite, rookWhiteLeft, pawnBlack4));
    }
}

class Horse extends Figure {
    public Horse(int x, int y) {
        super(x, y);
    }

    static class WhiteHorse extends Horse {
        public WhiteHorse(int x, int y) {
            super(x, y);
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
    boolean checkTransitClear(int x, int y, Figure figure) {
        return true;
    }

    /**
     * Horse move, unlike other figures logic, is provided in user-friendly manner, like moveUpAndDown,
     * where first direction token corresponds to longer part of the movement and the latter - shorter one.
     * therefore all functions hardcoded for usability. Thus utility methods receive data by means of
     * addition/substraction from current coordinates.
     *
     * @param x incremental/decremental value <b>x</b>
     * @param y incremental/decremental value <b>y</b>     *
     */
    @Override
    void move(int x, int y) {
        assertFigureAlive(this, false);
        checkMoveOnSamePosition(x, y);
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
        move(2, 1);
    }

    void moveRightAndDown() {
        move(1, 2);
    }

    void moveUpAndLeft() {
        move(-2, -1);
    }

    void moveUpAndRight() {
        move(-2, 1);
    }

    void moveDownAndLeft() {
        move(2, -1);
    }

    void moveLeftAndUp() {
        move(-1, -2);
    }

    void moveLeftAndDown() {
        move(1, -2);
    }

    void moveRightAndUp() {
        move(-1, 2);
    }
}

class Bishop extends Figure {
    static class WhiteBishop extends Bishop {

        public WhiteBishop(int x, int y) {
            super(x, y);
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

    public Bishop(int x, int y) {
        super(x, y);
    }

    @Override
    boolean checkTransitClear(int x, int y, Figure figure) {
        //go north east
        if (figure.getX() > x && figure.getY() < y) {
            for (int a = getX() - 1, b = figure.getY() + 1; a > x && b < y; a--, b++) {
                if (chessboard[a][b] != dot) {
                    calloutIfOccupied(a, b);
                }
            }
        }
        //go north west
        else if (figure.getX() > x && figure.getY() > y) {
            for (int a = figure.getX() - 1, b = figure.getY() - 1; a > x && b > y; a--, b--) {
                if (chessboard[a][b] != dot) {
                    calloutIfOccupied(a, b);
                }
            }
        }

        //go south east
        else if (figure.getX() < x && figure.getY() < y) {
            for (int a = getX() + 1, b = figure.getY() + 1; a < x && b < y; a++, b++) {
                if (chessboard[a][b] != dot) {
                    calloutIfOccupied(a, b);
                }
            }
        }

        //go south west
        else {
            for (int a = figure.getX() + 1, b = figure.getY() - 1; a < x && b < y; a++, b--) {
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
    void move(int x, int y) {
        assertFigureAlive(this, false);
        tryMoveCallout(x, y);
        var figureCharr = isWhite() ? Bishop.WhiteBishop.figureChar : figureChar;
        if (!isMoveDiagonal(x, y)) {
            out.println(figureCharr + " moves in diagonal");
            printDelimiter();
        } else if (!checkBoundaries(x, y)) {
            out.println(figureCharr + " move " + x + ":" + y + " leads outside the chessboard");
        } else if (!checkTransitClear(x, y, this)) {
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
}

class Rook extends Figure {
    static class WhiteRook extends Rook {
        public WhiteRook(int x, int y) {
            super(x, y);
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

    public Rook(int x, int y) {
        super(x, y);
    }

    @Override
    boolean checkTransitClear(int x, int y, Figure figure) {
        if (y > figure.getY()) { //go right
            for (int i = figure.getY() + 1; i < y; i++) {
                if (chessboard[x][i] != dot) {
                    return false;
                }
            }
            return true;
        } else if (y < figure.getY()) {// go left
            for (int i = figure.getY() - 1; i > y; i--) {
                if (chessboard[x][i] != dot) {
                    return false;
                }
            }
            return true;
        } else if (x > figure.getX()) {// go down
            for (int i = figure.getX() + 1; i < x; i++) {
                if (chessboard[i][y] != dot) {
                    return false;
                }
            }
            return true;
        } else { // go up
            for (int i = figure.getX() - 1; i > x; i--) {
                if (chessboard[i][y] != dot) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    void move(int x, int y) {
        checkMoveOnSamePosition(x, y);
        printDelimiter();
        assertFigureAlive(this, false);
        printDelimiter();
        tryMoveCallout(x, y);
        printDelimiter();
        var figureCharr = isWhite() ? Rook.WhiteRook.figureChar : figureChar;
        if (!isMoveHorizontalOrVertical(x, y)) {
            out.println(figureCharr + " moves only vertically or horisontally, not in diagonal");
            printDelimiter();
            throw new UnsupportedOperationException();
        } else if (!checkBoundaries(x, y)) {
            out.println(figureCharr + " move " + x + ":" + y + " leads outside the chessboard");
            printDelimiter();
            throw new UnsupportedOperationException();
        } else if (!checkTransitClear(x, y, this)) {
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

        public WhitePawn(int x, int y) {
            super(x, y);
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

    public Pawn(int x, int y) {
        super(x, y);
    }

    @Override
    boolean checkTransitClear(int x, int y, Figure figure) {
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
    void move(int targetX, int targetY) {
        assertFigureAlive(this, false);
        tryMoveCallout(targetX, targetY);
        var figureCharr = isWhite() ? WhitePawn.figureChar : figureChar;
        checkMoveOnSamePosition(targetX, targetY);
        if (isMoveDiagonal(targetX, targetY) &&
                Math.abs(getX() - targetX) == 1 && Math.abs(getY() - targetY) == 1 &&
                targetSquareContainsFigure(targetX, targetY)) {
        } else if (getX() < targetX && this.isWhite() || getX() > targetX && !this.isWhite()) {
            out.println("seems like you're trying to move " + figureCharr + " backwards");
        } else {
            out.println(figureCharr + " could move diagonal only in attack");
            throw new UnsupportedOperationException();
        }
    }

    void move(int movementStep) {
        assertFigureAlive(this, false);
        tryMoveCallout(getX(), movementStep);
        var figureCharr = this.isWhite() ? WhitePawn.figureChar : figureChar;
        int newCoordinate = this.isWhite() ? getX() - movementStep : getX() + movementStep;
        if (!checkBoundaries(movementStep)) {
        } else if (!checkTransitClear(movementStep)) {
            out.println("There's " + chessboard[newCoordinate][getY()] + " in the way of " + figureCharr + " move " + newCoordinate + ":" + getY());
            printDelimiter();
        } else if (!checkMaxMovementStepFromStartingSquareOnly(movementStep)) {
            out.println(figureCharr + " is allowed to make extended move only from starting position");
            printDelimiter();
        } else if (newCoordinate == chessboardSize - 1 || newCoordinate == 0) {
            elevatePawnToNewQueen(newCoordinate);
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

    void elevatePawnToNewQueen(int newCoordinate) { //todo implement logic for choosing any figure
        if (isFigureWhite(this)) {
            queenNew = new Queen.WhiteQueen(0, 0);
            defeatedWhiteFigures.add(figureChar);
        } else {
            queenNew = new Queen(0, 0);
            defeatedBlackFigures.add(figureChar);
        }
        chessboard[getX()][getY()] = dot;
        registerMove(getX(), getY(), newCoordinate, getY(), this, this.getFigureChar() + " -> " + (isWhite() ? Queen.WhiteQueen.figureChar : Queen.figureChar));
        queenNew.setX(newCoordinate);
        queenNew.setY(getY());
        chessboard[queenNew.getX()][queenNew.getY()] = queenNew.getFigureChar();
        setX(-1);
        setY(-1);
        printDelimiter();
        out.println(figureChar + " has been promoted to " + queenNew.getFigureChar());
        printDelimiter();
        viewBoard();
    }
}

class Queen extends Figure {
    static class WhiteQueen extends Queen {

        public WhiteQueen(int x, int y) {
            super(x, y);
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

    public Queen(int x, int y) {
        super(x, y);
    }

    @Override
    boolean checkTransitClear(int x, int y, Figure figure) {
        if (isMoveHorizontalOrVertical(x, y)) { //horizontal
            return rookBlackLeft.checkTransitClear(x, y, this);
        } else if (isMoveDiagonal(x, y)) {
            return rookBlackLeft.checkTransitClear(x, y, this);
        }
        return true;
    }

    @Override
    boolean checkBoundaries(int x, int y) {
        return x < chessboardSize && y < chessboardSize && x > -1 && y > -1;
    }

    @Override
    void move(int x, int y) {
        assertFigureAlive(this, false);
        tryMoveCallout(x, y);
        var figureCharr = isWhite() ? Queen.WhiteQueen.figureChar : figureChar;
        if (!isMoveHorizontalOrVertical(x, y) && !isMoveDiagonal(x, y)) {
            out.println(figureCharr + " moves in diagonal, vertical or horizontal, " +
                    "move (" + getX() + " : " + getY() + ") -> (" + x + " : " + y + ") is invalid");
            printDelimiter();
            throw new IllegalStateException();
        } else if (!checkBoundaries(x, y)) {
            out.println(figureCharr + " move " + x + ":" + y + " leads outside the chessboard");
            printDelimiter();
        } else if (!checkTransitClear(x, y, this)) {
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
            if (isCastlingShort) {
                registerMove(getX(), getY(), getX(), getY() + 2, king, "R");
                registerMove(rookBlackRight.getX(), rookBlackRight.getY(), rookBlackRight.getX(), rookBlackRight.getY() - 2, rookBlackRight, "R");
                chessboard[getX()][getY()] = dot;
                setY(getY() + 2);
                chessboard[rookBlackRight.getX()][rookBlackRight.getY()] = dot;
                chessboard[rookBlackRight.getX()][rookBlackRight.getY() - 2] = rookBlackRight.getFigureChar();
                rookBlackRight.setY(rookBlackRight.getY() - 2);
                chessboard[getX()][getY()] = getFigureChar();
            } else { //castling Long, King is Black
                registerMove(getX(), getY(), getX(), getY() - 3, king, "R");
                registerMove(rookBlackLeft.getX(), rookBlackLeft.getY(), rookBlackLeft.getX(), rookBlackLeft.getY() + 2, rookBlackLeft, "R");
                chessboard[getX()][getY()] = dot;
                setY(getY() - 3);
                chessboard[getX()][getY()] = getFigureChar();
                chessboard[rookBlackLeft.getX()][rookBlackLeft.getY()] = dot;
                rookBlackLeft.setY(rookBlackLeft.getY() + 2);
                chessboard[rookBlackLeft.getX()][rookBlackLeft.getY()] = rookBlackLeft.getFigureChar();

            }
        }
        printDelimiter();
        out.println("Castling of " + figureChar + " has been done");
        printDelimiter();
        viewBoard();
    }

    private boolean proveFreeSpaceToTheRook(Figure figure, boolean isCastlingShort) {
        if (figure.isWhite()) {
            return isCastlingShort ? chessboard[figure.getX()][figure.getY() + 1] == dot && chessboard[figure.getX()][figure.getY() + 2] == dot : chessboard[figure.getX()][figure.getY() - 1] == dot && chessboard[figure.getX()][figure.getY() - 2] == dot && chessboard[figure.getX()][figure.getY() - 3] == dot;
        } else
            return isCastlingShort ? chessboard[figure.getX()][figure.getY() + 1] == dot && chessboard[figure.getX()][figure.getY() + 2] == dot : chessboard[figure.getX()][figure.getY() - 1] == dot && chessboard[figure.getX()][figure.getY() - 2] == dot && chessboard[figure.getX()][figure.getY() - 3] == dot;
    }

    private boolean proveFigureNeverMoved(Figure figure) {
        return movesRegister.stream().filter(e -> e.getFigure() == figure).findFirst().isEmpty();
    }

    static class WhiteKing extends King {
        public WhiteKing(int x, int y) {
            super(x, y);
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

    protected King(int x, int y) {
        super(x, y);
    }

    @Override
    boolean checkTransitClear(int x, int y, Figure figure) {
        return false;
    }

    @Override
    void move(int x, int y) {
        var figureCharr = isWhite() ? King.WhiteKing.figureChar : figureChar;
        checkMoveOnSamePosition(x, y);
        tryMoveCallout(x, y);
        //todo stalemate is also due when figures exist but unable to move
        /*if (isStalemate(x, y) && isSquareChecked(x, y)) {
            out.println("<<<<<<< Checkmate. Game over >>>>>>>>>" + (isWhite() ? "Blacks " : "Whites " + " have won"));
            movesRegister.add(new MovesRegister(getX(), getY(), x, y, this, "M"));
            printMovesRegisterLastEntry(movesRegister.get(movesRegister.size() - 1));
            System.exit(0);
        } else*/
        if (/*!isSquareChecked(x, y) && */isStalemate(false)) {
            out.println("<<<<<<< Stalemate. Draw >>>>>>>>>>>>>");
            movesRegister.add(new MovesRegister(getX(), getY(), x, y, this, "S"));
            printMovesRegisterLastEntry(movesRegister.get(movesRegister.size() - 1));
            System.exit(0);
        } else if (!checkBoundaries(x, y)) {
            out.println(figureCharr + " move " + x + " : " + y + " leads outside the chessboard");
            printDelimiter();
        } else if (Math.abs(x - getX()) > 1 || Math.abs(y - getY()) > 1) {
            out.println(figureCharr + " moves only one square");
            printDelimiter();
            throw new IllegalStateException();
        } else if (chessboard[x][y] != dot) {
            printDelimiter();
            targetSquareContainsFigure(x, y);
        } else if (isSquareChecked(x, y)) {
            var checkFigure = retrieveFigureAtSquare(lastCheckFigureCoordX, lastCheckFigureCoordY);
            if (checkFigure.isEmpty() && x < 3) { //limiting check for initial figures allocation in rows 1-2
                checkFigure = fetchInitialLayoutData(x, y);
            }
            Figure figureChecking;
            if (checkFigure.isPresent()) figureChecking = checkFigure.orElseThrow();
            else throw new IllegalArgumentException();
            if (attackingAndAttackedFiguresColorDiffer(figureChecking, this)) {
                if (isSquareChecked(this.getX(), this.getY())) {
                    out.println("<<< Checkmate. Game over >>>" + (isWhite() ? "\nBlacks " : " Whites " + "have won!!!"));
                    movesRegister.add(new MovesRegister(getX(), getY(), x, y, this, "M"));
                    printMovesRegisterLastEntry(movesRegister.get(movesRegister.size() - 1));
                    System.exit(0);
                } else {
                    out.println("The square " + x + " : " + y + " is under the check of "
                            + figureChecking.getFigureChar() + " ("
                            + figureChecking.getX() + " : "
                            + figureChecking.getY() + "), unable to move");
                    lastCheckFigureCoordX = figureChecking.getX();
                    lastCheckFigureCoordY = figureChecking.getY();
                    printDelimiter();
                    throw new UnsupportedOperationException();
                }
            }

        } else {
            chessboard[x][y] = figureCharr;
            chessboard[getX()][getY()] = dot;
            registerMove(getX(), getY(), x, y, this);
            setX(x);
            setY(y);
            viewBoard();
        }
    }

    private boolean noOtherFiguresLeft() {
        if (isWhite() && defeatedWhiteFigures.size() == 15) return true;
        else return defeatedBlackFigures.size() == 15;
    }

    private static void printMovesRegisterLastEntry(MovesRegister last) {
        out.println(
                last.getFigure().getFigureChar()
                        + " (" + last.getStartX() + " : "
                        + last.getStartY() + ") -> (" +
                        last.getTargetX() + " : " +
                        last.getTargetY() + ") "
                        + last.getMoveSpecialty());
    }

    public boolean isStalemate(boolean testMode) {
        int x = this.getX(), y = this.getY();
        if (!testMode && !noOtherFiguresLeft()) { //testMode does not include king's color figures left
                                                  // (currently the moving possibility of those figures are not checked
                                                  // other figures leftover is checking by counting figures in defeated stack
                                                  // which is empty in custom chessboard layout case
            return false;
        } else if (x - 1 < 0 && y - 1 < 0) {
            //King's on the upleft corner, checking: down, right, downright
            return isSquareChecked(x, y + 1) && //right
                    isSquareChecked(x + 1, y + 1) && //downright
                    isSquareChecked(x + 1, y); // down
        } else if (x - 1 < 0 && y + 1 >= chessboardSize) {
            //King's on the upright corner, checking: down, left, downleft
            return isSquareChecked(x + 1, y) && // down
                    isSquareChecked(x + 1, y - 1) && //downleft
                    isSquareChecked(x, y - 1); //left
        } else if (x + 1 >= chessboardSize && y - 1 < 0) {
            //King's on the downleft corner, checking: up, right, upright
            return isSquareChecked(x, y + 1) && //right
                    isSquareChecked(x - 1, y) && // up
                    isSquareChecked(x - 1, y + 1); //upright
        } else if (x + 1 >= chessboardSize && y + 1 >= chessboardSize) {
            //King's on the downright corner, checking: up, left, upleft
            return isSquareChecked(x, y - 1) && //left
                    isSquareChecked(x - 1, y) && // up
                    isSquareChecked(x - 1, y - 1);  //upleft
        } else if (x - 1 < 0) {
            //King's on the 1st row, not checking: up, upright, upleft
            return isSquareChecked(x, y + 1) && //right
                    isSquareChecked(x + 1, y + 1) && //downright
                    isSquareChecked(x + 1, y) && // down
                    isSquareChecked(x + 1, y - 1) && //downleft
                    isSquareChecked(x, y - 1); //left
        } else if (y - 1 < 0) {
            //King's on the 1st column, not checking: left, downleft, upleft
            return isSquareChecked(x - 1, y) && // up
                    isSquareChecked(x - 1, y + 1) && //upright
                    isSquareChecked(x, y + 1) && //right
                    isSquareChecked(x + 1, y + 1) && //downright
                    isSquareChecked(x + 1, y); // down
        } else if (y + 1 >= chessboardSize) {
            //King's on the last column, not checking: right, downright, upright
            return isSquareChecked(x - 1, y) && // up
                    isSquareChecked(x - 1, y - 1) &&  //upleft
                    isSquareChecked(x + 1, y) && // down
                    isSquareChecked(x, y - 1); //left
        } else if (x + 1 >= chessboardSize) {
            //King's on the last row, not checking: downleft, downright, down
            return isSquareChecked(x - 1, y) && // up
                    isSquareChecked(x - 1, y - 1) &&  //upleft
                    isSquareChecked(x, y - 1) && //left
                    isSquareChecked(x, y + 1); //right
        } else { //King's in the middle of chessboard. Check all squares around
            return isSquareChecked(x - 1, y) && // up
                    isSquareChecked(x - 1, y - 1) &&  //upleft
                    isSquareChecked(x - 1, y + 1) && //upright
                    isSquareChecked(x, y - 1) && //left
                    isSquareChecked(x, y + 1) && //right
                    isSquareChecked(x + 1, y) && // down
                    isSquareChecked(x + 1, y + 1) && //downright
                    isSquareChecked(x + 1, y - 1); //downleft
        }
    }

    boolean isSquareChecked(int x, int y) {
        if (checkHorses(x, y, this)) return true;
        else if (checkAxials(x, y, this, "R")) return true;
        else if (checkQueens(x, y)) return true;
        else if (checkPawns(x, y, this)) return true;
        else if (checkDiagonals(x, y, this, "B", false)) return true;
        else return checkKings(x, y, this);
    }

    private boolean checkHorses(int x, int y, King king) {
        char charr = king.isWhite() ? Horse.figureChar : Horse.WhiteHorse.figureChar;
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
        } else if (x + 2 < chessboardSize && y - 1 > -1 && chessboard[x + 2][y - 1] == charr) { // South South West)
            return true;
        } else if (x + 1 < chessboardSize && y + 2 < chessboardSize && chessboard[x + 1][y + 2] == charr) { // South South East)
            return true;
        } else
            return x + 2 < chessboardSize && y + 1 < chessboardSize && chessboard[x + 2][y + 1] == charr; // South South East
    }

    private boolean checkKings(int x, int y, King king) {
        char charr = king.isWhite() ? King.figureChar : King.WhiteKing.figureChar;
        if (y + 1 < chessboardSize && chessboard[x][y + 1] == charr) return true; //e
        else if (y - 1 > -1 && chessboard[x][y - 1] == charr) return true; //w
        else if (x + 1 < chessboardSize && chessboard[x + 1][y] == charr) return true; //s
        else if (x - 1 > -1 && chessboard[x - 1][y] == charr) return true; //n
        else if (x + 1 < chessboardSize && y + 1 < chessboardSize && chessboard[x + 1][y + 1] == charr)
            return true; //se
        else if (x - 1 > -1 && y + 1 < chessboardSize && chessboard[x - 1][y + 1] == charr) return true; //ne
        else if (x - 1 > -1 && y - 1 > -1 && chessboard[x - 1][y - 1] == charr) return true; //nw
        else if (x + 1 < chessboardSize && y - 1 > -1 && chessboard[x + 1][y - 1] == charr) return true; //sw
        else return false;
    }


    private boolean checkPawns(int x, int y, King king) {
        char charr = king.isWhite() ? Pawn.figureChar : Pawn.WhitePawn.figureChar;
        if (king.isWhite()) {
            if (x - 1 > -1 && y + 1 < chessboardSize &&
                    chessboard[x - 1][y + 1] == charr) {  //check NE
                return true;
            } else return x - 1 > -1 && y - 1 > -1 &&
                    chessboard[x - 1][y - 1] == charr; //check NW
        } else if (x + 1 < chessboardSize &&
                y + 1 < chessboardSize &&
                chessboard[x + 1][y + 1] == charr) {//check SE
            return true;
        } else return x + 1 < chessboardSize && y - 1 > -1 &&
                chessboard[x + 1][y - 1] == charr; //check SW
    }

    private boolean checkQueens(int x, int y) {
        if (this.isWhite() &&
                !assertFigureAlive(queenBlack, true) ||
                !assertFigureAlive(queenWhite, true)) return false;
        return checkAxials(x, y, this, "Q") || checkDiagonals(x, y, this, "Q", false);
    }

    private boolean checkDiagonals(int x, int y, King king, String figure, boolean checkingStalemate) {
        boolean isKingWhite = king.isWhite();
        char retrievedFigureChar;
        switch (figure) {
            case ("B") -> retrievedFigureChar = isKingWhite ? Bishop.figureChar : Bishop.WhiteBishop.figureChar;
            case ("Q") -> retrievedFigureChar = isKingWhite ? Queen.figureChar : Queen.WhiteQueen.figureChar;
            default -> throw new UnsupportedOperationException();
        }
        //check North East
        for (int i = x, j = y; i > -1 && j < chessboardSize; i--, j++) {
            if (chessboard[i][j] == retrievedFigureChar) {
                lastCheckFigureCoordX = i;
                lastCheckFigureCoordY = j;
                lastCheckFigureChar = chessboard[i][j];
                return true;
            } else if (chessboard[i][y] != this.getFigureChar() && chessboard[i][j] != dot) {
                break;
            }

        }//check South East
        for (int i = x, j = y; i < chessboardSize && j < chessboardSize; i++, j++) {
            if (chessboard[i][j] == retrievedFigureChar) {
                lastCheckFigureCoordX = i;
                lastCheckFigureCoordY = j;
                lastCheckFigureChar = chessboard[i][j];
                return true;
            } else if (chessboard[i][y] != this.getFigureChar() && chessboard[i][j] != dot) {
                break;
            }
        }
        //check North West
        for (int i = x, j = y; i > -1 && j > -1; i--, j--) {
            if (chessboard[i][j] == retrievedFigureChar) {
                lastCheckFigureCoordX = i;
                lastCheckFigureCoordY = j;
                lastCheckFigureChar = chessboard[i][j];
                return true;
            } else if (chessboard[i][y] != this.getFigureChar() && chessboard[i][j] != dot) {
                break;
            }
        }
        //check South West
        for (int i = x, j = y; i < chessboardSize && j > -1; i++, j--) {
            if (chessboard[i][j] == retrievedFigureChar) {
                lastCheckFigureCoordX = i;
                lastCheckFigureCoordY = j;
                lastCheckFigureChar = chessboard[i][j];
                return true;
            } else if (chessboard[i][y] != this.getFigureChar() && chessboard[i][j] != dot) {
                break;
            }
        }
        return false;
    }

    private boolean checkAxials(int x, int y, King king, String figure) {
        boolean isKingWhite = king.isWhite();
        char retrievedFigureChar;
        switch (figure) {
            case ("R") -> retrievedFigureChar = isKingWhite ? Rook.figureChar : Rook.WhiteRook.figureChar;
            case ("Q") -> retrievedFigureChar = isKingWhite ? Queen.figureChar : Queen.WhiteQueen.figureChar;
            default -> throw new UnsupportedOperationException();
        }
        //check horizontal to the right
        for (int i = y; i < chessboardSize; i++) {
            if (chessboard[x][i] == retrievedFigureChar) {
                lastCheckFigureCoordX = x; //todo in stalemate procedures there assignments are false
                lastCheckFigureCoordY = i;
                lastCheckFigureChar = chessboard[x][i];
                return true;
            } else if (chessboard[i][y] != this.getFigureChar() && chessboard[x][i] != dot) {
                break;
            }
        }
        //check horizontal to the left
        for (int i = y; i > -1; i--) {
            if (chessboard[x][i] == retrievedFigureChar) {
                lastCheckFigureCoordX = x;
                lastCheckFigureCoordY = i;
                lastCheckFigureChar = chessboard[x][i];
                return true;
            } else if (chessboard[i][y] != this.getFigureChar() && chessboard[x][i] != dot) {
                break;
            }
        }
        //check vertical down
        for (int i = x; i < chessboardSize; i++) {
            if (chessboard[i][y] == retrievedFigureChar) {
                lastCheckFigureCoordX = i;
                lastCheckFigureCoordY = y;
                lastCheckFigureChar = chessboard[i][y];
                return true;
            } else if (chessboard[i][y] != this.getFigureChar() && chessboard[i][y] != dot) { //skip encountering king himself as obstacle
                break;
            }
        }
        //check vertical up
        for (int i = x; i > -1; i--) {
            if (chessboard[i][y] == retrievedFigureChar) {
                lastCheckFigureCoordX = i;
                lastCheckFigureCoordY = y;
                lastCheckFigureChar = chessboard[i][y];
                return true;
            } else if (chessboard[i][y] != this.getFigureChar() && chessboard[i][y] != dot) {
                break;
            }
        }
        return false;
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
}

abstract class Figure {
    private int x, y;
    private boolean isWhite;
    private char figureChar;

    public Figure(int x, int y) {
        this.x = x;
        this.y = y;
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

    void tryMoveCallout(int x, int y) {
        printDelimiter();
        out.println("...Trying to move " + getFigureChar() + " (" + getX() + " : " + getY() + " -> " + x + " : " + y + ")");
        printDelimiter();
    }

    static boolean isFigureWhite(Figure figure) {
        return figure.getClass().getName().contains("White");
    }

    void checkMoveOnSamePosition(int targetX, int targetY) {
        if (getX() == targetX && getY() == targetY) {
            printDelimiter();
            out.println("Trying to move on the same position");
            printDelimiter();
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Checks move for being provisioned within the chessboard
     *
     * @param x coordinate (and 'y') for target cell to move in
     * @return true if move stays inside, false - trespasses board's measures
     */
    boolean checkBoundaries(int x, int y) {
        return getX() + x < chessboardSize && getX() + x > -1 && getY() + y < chessboardSize && getY() + y > -1;
    }

    abstract boolean checkTransitClear(int x, int y, Figure figure);

    abstract void move(int x, int y);

    abstract void printCurrentPosition();


    static void registerMove(int startX, int startY, int targetX, int targetY, Figure figure) {
        movesRegister.add(new MovesRegister(startX, startY, targetX, targetY, figure, ""));
        printDelimiter();
        out.println(++innerListMovesCounter + ". Moving " + figure.getFigureChar() + " (" + figure.getX() + " : " + figure.getY() + ") -> (" + targetX + " : " + targetY + ")");
    }

    static void registerMove(int startX, int startY, int targetX, int targetY, Figure figure, String moveSpecialty) {
        movesRegister.add(new MovesRegister(startX, startY, targetX, targetY, figure, moveSpecialty));
        printDelimiter();
        out.println(++innerListMovesCounter + ". Moving " + figure.getFigureChar() + " (" + figure.getX() + " : " + figure.getY() + ") -> (" + targetX + " : " + targetY + ") " + moveSpecialty);
    }

    boolean assertFigureAlive(Figure figure, boolean bypassOnQueenCheck) {
        if (bypassOnQueenCheck &&
                figure.getX() == -1 &&
                figure.getY() == -1) {
            return false;
        } else if (figure.getX() == -1 && figure.getY() == -1) {
            out.println("Figure " + this.getFigureChar() + " has been defeated");
            printDelimiter();
            throw new UnsupportedOperationException();
        }
        return true;
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
        var attackedFigure = retrieveFigureAtSquare(targetX, targetY);
        if (attackedFigure.isEmpty()) {
            attackedFigure = fetchInitialLayoutData(targetX, targetY);
        }
        if (this.isWhite && this instanceof King.WhiteKing &&
                kingWhite.isSquareChecked(targetX, targetY) ||
                !this.isWhite && this instanceof King &&
                        kingBlack.isSquareChecked(targetX, targetY)) {
            out.println("cannot kill " + attackedFigure.orElseThrow().getFigureChar() + " as it's protected by other figure");
            printDelimiter();
            throw new IllegalStateException();
        }
        if (attackedSquare == dot) {
            return false;
        } else if (attackedSquare == King.figureChar || attackedSquare == King.WhiteKing.figureChar
            /*attackedFigure instanceof King*/) {
            out.println("You cannot kill King, only check (-mate) is allowed");
            printDelimiter();
            throw new UnsupportedOperationException("King Kill Attempt");
        } else if (attackingAndAttackedFiguresColorDiffer(attackedFigure.orElseThrow(), this)) {
            printDelimiter();
            out.println(getFigureChar() + " kills " + attackedSquare + " at pos (" + targetX + " : " + targetY + ")");
            printDelimiter();
            if (attackedFigure.orElseThrow().isWhite()) {
                defeatedWhiteFigures.add(attackedSquare);
            } else {
                defeatedBlackFigures.add(attackedSquare);
            }
            attackedFigure.orElseThrow().setX(-1);
            attackedFigure.orElseThrow().setY(-1);
            chessboard[targetX][targetY] = getFigureChar();
            chessboard[getX()][getY()] = dot;
            registerMove(getX(), getY(), targetX, targetY, this, "A");
            setX(targetX);
            setY(targetY);
            viewBoard();
            return true;
        }
        return true;
    }

    Optional<Figure> fetchInitialLayoutData(int x, int y) {
        return initialFiguresLayout.stream().filter(e -> e.getX() == x && e.getY() == y).findFirst();
    }

    static boolean attackingAndAttackedFiguresColorDiffer(Figure attackedFigure, Figure attackingFigure) {
        if (attackedFigure == null) {
            out.println("attacked figure is null");
            return true;
        } else if (isFigureWhite(attackedFigure) && isFigureWhite(attackingFigure) || !isFigureWhite(attackedFigure) && !isFigureWhite(attackingFigure)) {
            out.println("Unable to attack figure of the same color");
            return false;
        }
        return true;
    }

    static Optional<Figure> retrieveFigureAtSquare(int x, int y) {
        return movesRegister.stream().filter(e -> e.getTargetX() == x && e.getTargetY() == y).max(Comparator.comparing(e -> e.getOrderNum())).map(e -> e.getFigure());
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
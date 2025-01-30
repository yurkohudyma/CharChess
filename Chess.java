package Chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static Chess.Chess.kingBlack;
import static Chess.Chess.*;
import static java.lang.System.out;

public class Chess {

    static int defeatedIndex = -1, chessboardSize = 8;
    static AtomicInteger movesCounter = new AtomicInteger();
    static char[][] chessboard = new char[chessboardSize][chessboardSize]; //todo resizing board to, say, 9x9 breaks the logic
    static int edge = chessboard.length;
    static List<Character> defeatedFigures = new ArrayList<>();
    static List<MovesRegister> movesRegister = new ArrayList<>();
    static Horse horse = new Horse(0, 7);
    static Bishop bishop = new Bishop(0, 0);
    static Rook rook = new Rook(4, 6);
    static Pawn pawn = new Pawn(1, 4);
    static Queen queen = new Queen(0, 3);
    static kingBlack kingBlack = new kingBlack(5, 0), kingWhite;
    static char dot = '\u00b7';

    public static void main(String[] args) {
        initialFill();
        rook.move(4, 0); //1
        kingBlack.move(5, 1);//2
        pawn.move(2);//3
        horse.moveDownAndLeft();//4
        bishop.move(4, 4);//5
        bishop.move(5, 3);//6
        rook.move(7, 0);//7
        horse.moveUpAndLeft();//8
        bishop.move(1, 7);//9
        bishop.move(0, 6);//10
        pawn.move(1);//11
        bishop.move(3, 3);//12
        rook.move(7, 6);//13
        bishop.move(6, 6);//14
        kingBlack.move(5, 2);//15
        pawn.move();//16
        horse.moveDownAndLeft();//17
        horse.moveDownAndLeft();//18
        kingBlack.move(5, 3);//19
        rook.move(7, 2);//20
        horse.moveLeftAndUp();//21
        kingBlack.move(5, 4);//22
        kingBlack.move(6, 3);//23
        rook.move(7, 7);//24
        kingBlack.move(6, 2);//25
        rook.move(5, 7);//26
        kingBlack.move(6, 1);//27
        bishop.move(4, 4);//28
        kingBlack.move(7, 0);//29
        rook.move(5, 0);//30
        kingBlack.move(6, 1);//31
        queen.move(7, 3);//32
        queen.move(7, 1);//33
        queen.move(4, 4);//34
    }

    static void printDelimiter() {
        out.println("--------------------------------------");
    }

    static void checkMoveOnSamePosition(int startX, int startY, int targetX, int targetY) {
        printDelimiter();
        if (startX == targetX && startY == targetY) {
            out.println("Trying to move on the same position");
            printDelimiter();
            throw new IllegalStateException();
        }
    }

    static void viewBoard() {
        printBoard();
        printFiguresPositions();
        printDelimiter();
        printMovesRegister();
    }

    private static void printMovesRegister() {
        if (!movesRegister.isEmpty()) {
            out.println("----MovesRegister----");
            AtomicInteger counter = new AtomicInteger();
            movesRegister.forEach(e -> out.println(counter.incrementAndGet() + ". " + e.getFigure() + " (" + e.getStartX() + " : " + e.getStartY()
                    + ") -> (" + e.getTargetX() + " : " + e.getTargetY() + ")"));
            printDelimiter();
        }
        if (movesRegister.size() >= 20) {
            printBoard();
            printFiguresPositions();
        }
    }

    private static void printFiguresPositions() {
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
    }

    private static void printBoard() {
        out.print("    ");
        for (int i = 0; i < chessboard.length; i++) {
            out.print(i + " ");
        }
        out.println("\n   __________________");
        for (int i = 0; i < chessboard.length; i++) {
            out.print(i + " | ");
            for (int j = 0; j < chessboard[i].length; j++) {
                out.print(chessboard[i][j] + " ");
            }
            if (!curRowContainsFigures(i)) {
                out.println(" |" + i);
            } else {
                out.println("|" + i);
            }
        }
        out.println("   ------------------");
    }

    private static boolean curRowContainsFigures(int x) {
        for (int i = 0; i < chessboard[0].length; i++) {
            if (chessboard[x][i] != dot) {
                return true;
            }
        }
        return false;
    }

    private static void initialFill() {
        for (char[] chars : chessboard) {
            Arrays.fill(chars, dot);
        }
        chessboard[horse.getX()][horse.getY()] = horse.figureChar;
        chessboard[bishop.getX()][bishop.getY()] = bishop.figureChar;
        chessboard[rook.getX()][rook.getY()] = rook.figureChar;
        chessboard[pawn.getX()][pawn.getY()] = pawn.figureChar;
        chessboard[kingBlack.getX()][kingBlack.getY()] = kingBlack.figureChar;
        chessboard[queen.getX()][queen.getY()] = queen.figureChar;
        viewBoard();
    }
}

class Horse extends Figure {

    protected char figureChar = '\u2658';

    public Horse(int x, int y) {
        super(x, y);
    }

    @Override
        // not applicable
    boolean checkTransitClear(int x, int y) {
        return true;
    }

    @Override
    void move(int x, int y) {
        if (!checkBoundaries(x, y)) {
            out.println("move " + x + ":" + y + " leads outside the chessboard");
        } else {
            checkFigureAttack(getX(), getY(), getX() + x, getY() + y, horse.figureChar);
            chessboard[getX()][getY()] = dot;
            registerMove(figureChar, getX(), getY(), getX() + x, getY() + y);
            setX(getX() + x);
            setY(getY() + y);
            chessboard[getX()][getY()] = horse.figureChar;
            viewBoard();
        }
    }

    @Override
    void printCurrentPosition() {
        if (getX() != defeatedIndex) {
            out.println(horse.figureChar + " cur pos: (" + getX() + " : " + getY() + ")");
        }
    }

    void moveRightAndDown() {
        move(1, 2);
    }

    void moveUpAndLeft() {
        move(-2, -1);
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
}

class Bishop extends Figure {

    protected char figureChar = '\u265D';

    public Bishop(int x, int y) {
        super(x, y);
    }

    @Override
    boolean checkTransitClear(int x, int y) {
        //go north east
        if (getX() > x && getY() < y) {
            for (int a = getX() - 1, b = getY() + 1; a > x && b < y; a--, b++) {
                if (chessboard[a][b] != dot) {
                    calloutIfOccupied(a, b);
                    return false;
                }
            }
        }
        //go north west
        else if (getX() > x && getY() > y) {
            for (int a = getX() - 1, b = getY() - 1; a >= x && b >= y; a--, b--) {
                if (chessboard[a][b] != dot) {
                    calloutIfOccupied(a, b);
                    return false;
                }
            }
        }

        //go south east
        else if (getX() < x && getY() < y) {
            for (int a = getX() + 1, b = getY() + 1; a <= x && b <= y; a++, b++) {
                if (chessboard[a][b] != dot) {
                    calloutIfOccupied(a, b);
                    return false;
                }
            }
        }

        //go south west
        //getX() < x && getY() > y
        else {
            for (int a = getX() + 1, b = getY() - 1; a <= x && b <= y; a++, b--) {
                if (chessboard[a][b] != dot) {
                    calloutIfOccupied(a, b);
                    return false;
                }
            }
        }
        return true;
    }

    private static void calloutIfOccupied(int a, int b) {
        out.println("Pos " + a + " : " + b + " is occupied by " + chessboard[a][b] + ", cannot jump");
        printDelimiter();
    }

    @Override
    void move(int x, int y) {
        if (!isMoveDiagonal(x, y)) {
            out.println(figureChar + " moves in diagonal");
            printDelimiter();
        } else if (!checkBoundaries(x, y)) {
            out.println(figureChar + " move " + x + ":" + y + " leads outside the chessboard");
        } else if (!checkTransitClear(x, y)) {
        } else {
            checkFigureAttack(getX(), getY(), x, y, figureChar);
            chessboard[getX()][getY()] = dot;
            registerMove(figureChar, getX(), getY(), x, y);
            setX(x);
            setY(y);
            chessboard[getX()][getY()] = figureChar;
            viewBoard();
        }
    }

    @Override
    boolean checkBoundaries(int x, int y) {
        return x < edge && y < edge && x > -1 && y > -1;
    }

    @Override
    void printCurrentPosition() {
        if (getX() != defeatedIndex) {
            out.println(bishop.figureChar + " cur pos: (" + getX() + " : " + getY() + ")");
        }
    }

    void moveUpAndLRight(int x, int y) {
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
        return (chessboard.length - getY() + z - 1) % chessboard.length;
    }

    int convertVectorCoordX(int z) {
        return (chessboard.length - getX() + z - 1) % chessboard.length;
    }
}

class Rook extends Figure {

    protected char figureChar = '\u2656';

    public Rook(int x, int y) {
        super(x, y);
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
    void move(int x, int y) {
        if (!isMoveHorizontalOrVertical(x, y)) {
            out.println(rook.figureChar + " moves only vertically or horisontally, not in diagonal");
            printDelimiter();
            throw new IllegalStateException();
        } else if (!checkBoundaries(x, y)) {
            out.println(figureChar + " move " + x + ":" + y + " leads outside the chessboard");
            printDelimiter();
        } else if (!checkTransitClear(x, y)) {
            out.println("There's a figure in the way of " + figureChar + " move at " + x + ":" + y);
            printDelimiter();
        } else {
            checkFigureAttack(getX(), getY(), x, y, figureChar);
            chessboard[getX()][getY()] = dot;
            chessboard[x][y] = figureChar;
            registerMove(figureChar, getX(), getY(), x, y);
            setX(x);
            setY(y);
            viewBoard();
        }
    }

    @Override
    boolean checkBoundaries(int x, int y) {
        return x < edge && x > -1 && y > -1 && y < edge;
    }

    @Override
    void printCurrentPosition() {
        if (getX() != defeatedIndex) {
            out.println(rook.figureChar + " cur pos: (" + getX() + " : " + getY() + ")");
        }
    }
}

class Pawn extends Figure {
    protected char figureChar = '\u265F';

    public Pawn(int x, int y) {
        super(x, y);
    }

    static boolean checkPawnPosition(int y) {
        for (int i = 0; i < chessboard.length; i++) {
            if (chessboard[chessboard.length - 1][y] == pawn.figureChar) {
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
        return chessboard[pawn.getX() + moveStep][pawn.getY()] == dot;
    }

    boolean checkBoundaries(int movementStep) {
        var newCoordinate = pawn.getX() + movementStep;
        if (newCoordinate > chessboard.length - 1) {
            out.println(pawn.figureChar + " move " + newCoordinate + ":" + pawn.getY() + " leads outside the chessboard");
            printDelimiter();
            return false;
        } else if (movementStep > 2) {
            out.println(pawn.figureChar + " is not allowed to move more than 2 squares forward");
            return false;
        } else if (movementStep < 0) {
            out.println(pawn.figureChar + " is allowed to move ONLY FORWARD");
            return false;
        } else {
            return true;
        }
    }

    @Override
    void move(int x, int y) {
        //todo implement attack diagonal move
    }

    void move(int movementStep) {
        int newCoordinate = pawn.getX() + movementStep;
        if (newCoordinate == chessboard.length - 1) {
            out.println(pawn.figureChar + " has successfully reached top of the chessboard - you may choose any figure instead");
            printDelimiter();
            proceedMovementRegistration(chessboard.length - 1);
            elevatePawnToNewFigure(pawn.getY(), queen.figureChar);
            viewBoard();
        } else if (!checkBoundaries(movementStep)) {
        } else if (!checkTransitClear(movementStep)) {
            out.println("There's " + chessboard[newCoordinate][getY()] + " in the way of " + pawn.figureChar + " move " + newCoordinate + ":" + pawn.getY());
            printDelimiter();
        } else if (!checkMaxMovementStepFromStartingSquareOnly(movementStep)) {
            out.println(pawn.figureChar + " is allowed to make extended move only from starting position");
            printDelimiter();
        } else {
            proceedMovementRegistration(getX() + movementStep);
        }
    }

    void move() {
        move(1);
    }

    private void proceedMovementRegistration(int newCoordinate) {
        chessboard[newCoordinate][pawn.getY()] = pawn.figureChar;
        chessboard[pawn.getX()][pawn.getY()] = dot;
        registerMove(figureChar, getX(), getY(), newCoordinate, getY());
        pawn.setX(newCoordinate);
        viewBoard();
    }

    boolean checkMaxMovementStepFromStartingSquareOnly(int movementStep) {
        return movementStep == 2 ? getX() == 1 : movementStep == 1;
    }

    @Override
    void printCurrentPosition() {
        if (getX() != defeatedIndex) {
            out.println(pawn.figureChar + " cus pos: (" + getX() + " : " + getY() + ")");
        }
    }

    static void elevatePawnToNewFigure(int y, char newFigure) {
        if (checkPawnPosition(y)) {
            chessboard[pawn.getX()][y] = newFigure;
            queen = new Queen(pawn.getX(), pawn.getY());
            pawn.setX(-1);
            pawn.setY(-1);
            out.println("Pawn has been elevated to " + newFigure);
            printDelimiter();
            defeatedFigures.add(pawn.figureChar);
        }
    }
}

class Queen extends Figure {
    protected char figureChar = '\u2655';

    protected Queen(int x, int y) {
        super(x, y);
    }

    @Override
    boolean checkTransitClear(int x, int y) {
        if (isMoveHorizontalOrVertical(x, y)) { //horizontal
            return rook.checkTransitClear(x, y);
        } else if (isMoveDiagonal(x, y)) {
            return bishop.checkTransitClear(x, y);
        }
        return true;
    }

    @Override
    boolean checkBoundaries(int x, int y) {
        return x < edge && y < edge && x > -1 && y > -1;
    }

    @Override
    void move(int x, int y) {
        if (!isMoveHorizontalOrVertical(x, y) && !isMoveDiagonal(x, y)) {
            out.println(figureChar + " moves in diagonal, vertical or horizontal");
            printDelimiter();
            throw new IllegalStateException();
        } else if (!checkBoundaries(x, y)) {
            out.println(figureChar + " move " + x + ":" + y + " leads outside the chessboard");
            printDelimiter();
        } else if (!checkTransitClear(x, y)) {
            out.println("There's a figure in the way of " + figureChar + " move at " + x + ":" + y);
            printDelimiter();
        } else if (checkFigureAttack(getX(), getY(), x, y, figureChar)) {
        } else {
            chessboard[getX()][getY()] = dot;
            chessboard[x][y] = figureChar;
            registerMove(figureChar, getX(), getY(), x, y);
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

class kingBlack extends Figure {
    protected char figureChar = '\u2654', kingWhiteChar = '\u265A';

    protected kingBlack(int x, int y) {
        super(x, y);
    }

    @Override
    boolean checkTransitClear(int x, int y) {
        return false;
    }

    @Override
    void move(int x, int y) {
        checkMoveOnSamePosition(getX(), getY(), x, y);
        var checkFigure = detectCheckByFigure(x, y);
        if (!checkBoundaries(x, y)) {
            out.println(figureChar + " move " + x + " : " + y + " leads outside the chessboard");
            printDelimiter();
        } else if (Math.abs(x - getX()) > 1 || Math.abs(y - getY()) > 1) {
            out.println("kingBlack moves only one square");
            printDelimiter();
            throw new IllegalStateException();
        } else if (chessboard[x][y] != dot) {
            //out.println("target square " + x + " : " + y + " is taken by " + chessboard[x][y]);
            printDelimiter();
            checkFigureAttack(getX(), getY(), x, y, figureChar);
        } else if (checkFigure != dot) {
            out.println("The square " + x + " : " + y + " is under the check of " + checkFigure + ", unable to move");
            printDelimiter();
            throw new IllegalStateException();
        } else {
            chessboard[x][y] = figureChar;
            chessboard[kingBlack.getX()][kingBlack.getY()] = dot;
            registerMove(figureChar, getX(), getY(), x, y);
            kingBlack.setX(x);
            kingBlack.setY(y);
            viewBoard();
        }
    }

    private char detectCheckByFigure(int x, int y) {
        if (checkHorses(x, y)) return horse.figureChar;
        else if (!checkRooks(x, y)) return rook.figureChar;
        else if (!checkQueens(x, y)) return queen.figureChar;
        else if (checkPawns(x, y)) return pawn.figureChar;
        else if (!checkBishops(x, y)) return bishop.figureChar;
        else if (checkKingBlacks(x, y)) return kingBlack.figureChar;
        else return dot;
    }

    private boolean checkHorses(int x, int y) {
        if (x + 1 < edge && y - 2 > -1 && chessboard[x + 1][y - 2] == horse.figureChar) { // Nord Nord East
            return true;
        } else if (x - 2 > -1 && y - 1 > -1 && chessboard[x - 2][y - 1] == horse.figureChar) { //Nord Nord West
            return true;
        } else if (x - 1 > -1 && y - 2 > -1 && chessboard[x - 1][y - 2] == horse.figureChar) { // Nord West West
            return true;
        } else if (x - 1 > -1 && y + 2 > -1 && chessboard[x - 1][y + 2] == horse.figureChar) { // Nord East East
            return true;
        } else if (x - 2 > -1 && y + 1 < edge && chessboard[x - 2][y + 1] == horse.figureChar) { // South West West
            return true;
        } else if (x - 1 > -1 && y + 2 < edge && chessboard[x - 1][y + 2] == horse.figureChar) { // South South West)
            return true;
        } else if (x + 1 < edge && y + 2 < edge && chessboard[x + 1][y + 2] == horse.figureChar) { // South South East)
            return true;
        } else return x + 2 < edge && y + 1 < edge && chessboard[x + 2][y + 1] == horse.figureChar; // South South East
    }

    private boolean checkKingBlacks(int x, int y) {
        return x - 1 < edge && y + 1 < edge && chessboard[x - 1][y + 1] == kingWhiteChar &&
                x < edge && chessboard[x][y + 1] == kingWhiteChar &&
                x + 1 < edge && chessboard[x + 1][y + 1] == kingWhiteChar;
    }

    private boolean checkBishops(int x, int y) {
        //check North East
        for (int i = x, j = y; i > -1 && j < edge; i--, j++) {
            if (chessboard[i][j] == bishop.figureChar) {
                return false;
            }
        }//check South East
        for (int i = x, j = y; i < edge && j < edge; i++, j++) {
            if (chessboard[i][j] == bishop.figureChar) {
                return false;
            }
        }
        //check North West
        for (int i = x, j = y; i > -1 && j > -1; i--, j--) { //todo test it
            if (chessboard[i][j] == bishop.figureChar) {
                return false;
            }
        }
        //check South West
        for (int i = x, j = y; i < edge && j > -1; i++, j--) { //todo test it
            if (chessboard[i][j] == bishop.figureChar) {
                return false;
            }
        }
        return true;

    }

    private boolean checkPawns(int x, int y) {
        if (x + 1 < edge && y - 1 > -1 && chessboard[x + 1][y - 1] == pawn.figureChar) { //check NE
            return true;
        } else if (x - 1 > -1 && y - 1 > -1 && chessboard[x - 1][y - 1] == pawn.figureChar) { //check NW
            return true;
        } else if (x + 1 < edge && y + 1 < edge && chessboard[x + 1][y + 1] == pawn.figureChar) {//check SE
            return true;
        } else return x - 1 > -1 && y + 1 < edge && chessboard[x - 1][y + 1] == pawn.figureChar; //check SW
    }

    private boolean checkQueens(int x, int y) {
        return checkRooks(x, y) || checkBishops(x, y);
    }

    private boolean checkRooks(int x, int y) {

        //check horizontal to the right
        for (int i = getY(); i < edge; i++) {
            if (chessboard[x][i] == rook.figureChar) {
                return false;
            }
        }
        //check horizontal to the left
        for (int i = getY(); i > -1; i--) {
            if (chessboard[x][i] == rook.figureChar) {
                return false;
            }
        }
        //check vertical down
        for (int i = getX(); i < edge; i++) {
            if (chessboard[i][y] == rook.figureChar) {
                return false;
            }
        }
        //check vertical up
        for (int i = getX(); i > -1; i--) {
            if (chessboard[i][y] == rook.figureChar) {
                return false;
            }
        }
        return true;
    }

    @Override
    boolean checkBoundaries(int x, int y) {
        return x <= edge - 1 &&
                y <= edge - 1 &&
                x > -1 &&
                y > -1;
    }

    @Override
    void printCurrentPosition() {
        if (getX() != defeatedIndex) {
            out.println(figureChar + " cur pos: (" + getX() + " : " + getY() + ")");
        }
    }

    void printCurrentPositionKingWhite() {
        if (kingBlack.getX() != defeatedIndex) {
            out.println(kingWhiteChar + " cur pos: (" + kingBlack.getX() + " : " + kingBlack.getY() + ")");
        }
    }
}

abstract class Figure {
    private int x, y;

    private char figureChar;

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

    protected Figure(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Checks move for being provisioned within the chessboard
     *
     * @param x coordinate (and 'y') for target cell to move in
     * @return true if move stays inside, false - trespasses board's measures
     */
    boolean checkBoundaries(int x, int y) {
        return getX() + x < edge &&
                getX() + x > -1 &&
                getY() + y < edge &&
                getY() + y > -1;
    }

    abstract boolean checkTransitClear(int x, int y);

    abstract void move(int x, int y);

    abstract void printCurrentPosition();


    static void registerMove(char figure, int startX, int startY, int targetX, int targetY) {
        movesRegister.add(new MovesRegister(figure, startX, startY, targetX, targetY));
        out.println(movesCounter.incrementAndGet() + ". Moving " + figure + " (" + startX + " : " + startY + ") -> (" + targetX + " : " + targetY + ")");
    }

    /**
     * Checks whether target cell contains a figure. If so, proceed with figure substitution and callout
     *
     * @param targetX - coordinate X of target cell
     * @param targetY - coordinate Y of target cell
     * @param startX  - coordinate X of start cell
     * @param targetY - coordinate Y of start cell
     * @param figure  - current figure in movement
     * @return true if no attack available on target square
     * @throws NullPointerException if any figure has not been instantiated in prior.
     */
    static boolean checkFigureAttack(int startX, int startY, int targetX, int targetY, char figure) {
        checkMoveOnSamePosition(startX, startY, targetX, targetY);
        var attackedCell = chessboard[targetX][targetY];
        if (attackedCell == kingBlack.figureChar) {
            out.println("You cannot kill kingBlack, only check (-mate) is allowed");
            printDelimiter();
            throw new IllegalStateException("kingBlack Kill Attempt");
        } else if (attackedCell != dot) {
            out.println(figure + " kills " + attackedCell + " at pos (" + targetX + " : " + targetY + ")");
            out.println("---------------------------------------");
            proceedWithDefeatedFigure(attackedCell);
            defeatedFigures.add(attackedCell);
            chessboard[targetX][targetY] = figure;
            chessboard[startX][startY] = dot;
            registerMove(figure, startX, startY, targetX, targetY);
            kingBlack.setX(targetX);
            kingBlack.setY(targetY);
            viewBoard();
            return true;
        } else return false;
    }

    private static void proceedWithDefeatedFigure(char attackedCell) {
        switch (attackedCell) {
            case '\u2656' -> //rook
            {
                Objects.requireNonNull(rook);
                rook.setX(defeatedIndex);
                rook.setY(defeatedIndex);
            }
            case '\u2658' -> //horse
            {
                Objects.requireNonNull(horse);
                horse.setX(defeatedIndex);
                horse.setY(defeatedIndex);
            }
            case '\u265D' -> //bishop
            {
                Objects.requireNonNull(bishop);
                bishop.setX(defeatedIndex);
                bishop.setY(defeatedIndex);
            }
            case '\u265F' -> //pawn
            {
                Objects.requireNonNull(pawn);
                pawn.setX(defeatedIndex);
                pawn.setY(defeatedIndex);
            }
            case '\u2655' -> //queen
            {
                Objects.requireNonNull(queen);
                queen.setX(defeatedIndex);
                queen.setY(defeatedIndex);
            }
            default -> {
            }
        }
    }

    boolean isMoveHorizontalOrVertical(int x, int y) {
        return getX() == x && getY() != y || getX() != x && getY() == y;
    }

    boolean isMoveDiagonal(int x, int y) {
        return Math.abs(x - getX()) == Math.abs(y - getY());
    }

}

class MovesRegister {
    private char figure;
    private int startX, startY, targetX, targetY;

    public char getFigure() {
        return figure;
    }

    public void setFigure(char figure) {
        this.figure = figure;
    }

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

    public MovesRegister(char figure, int startX, int startY, int targetX, int targetY) {
        this.figure = figure;
        this.startX = startX;
        this.startY = startY;
        this.targetX = targetX;
        this.targetY = targetY;
    }
}



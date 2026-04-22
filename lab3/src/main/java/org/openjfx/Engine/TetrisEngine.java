package org.openjfx.Engine;

import org.openjfx.Shapes.Shape;
import org.openjfx.Shapes.ShapesFactory;
import java.util.Random;

public class TetrisEngine {
    private int scores;
    private boolean isRunning;
    private int[][] board;
    private ShapesFactory factory;
    private final Random random;

    private Shape currentShape;

    public int getScores() {
        return scores;
    }

    public void start() {
        clearBoard();
        isRunning = true;
    }

    public void stop() {
        this.isRunning = false;
    }

    public boolean getStatus() {
        return isRunning;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public Shape getCurrentShape() {
        return this.currentShape;
    }

    public TetrisEngine() {
        this.isRunning = true;
        this.board = new int[20][10]; // Здесь было верно: [Y][X]
        this.factory = new ShapesFactory();
        this.random = new Random();

        spawnNewShape();

    }
    public void tryMoveLeft() {
        if (!isInvalidMove(currentShape.x - 1, currentShape.y, currentShape.matrix)) {
            currentShape.moveLeft();
        }
    }

    public void tryMoveRight() {
        if (!isInvalidMove(currentShape.x + 1, currentShape.y, currentShape.matrix)) {
            currentShape.moveRight();
        }
    }

    public void tryRotate() {
        Shape nextShape = currentShape;
        nextShape.rotate();
        if (!isInvalidMove(currentShape.x, currentShape.y, nextShape.matrix)) {
            currentShape.rotate();
        }
    }

    public void update() {
        if (isInvalidMove(currentShape.x, currentShape.y + 1, currentShape.matrix)) {
            freezeShape(currentShape);
            clearLines();
            spawnNewShape();
            if (isInvalidMove(currentShape.x, currentShape.y, currentShape.matrix)) {
                this.isRunning = false;
            }
        } else {
            currentShape.moveDown();
        }
    }

    private void freezeShape(Shape shape) {
        int[][] m = shape.matrix;
        for (int y = 0; y < m.length; y++) {
            for (int x = 0; x < m[y].length; x++) {
                if (m[y][x] != 0) {
                    int targetX = shape.x + x;
                    int targetY = shape.y + y;

                    if (targetX >= 0 && targetX < 10 && targetY >= 0 && targetY < 20) {
                        this.board[targetY][targetX] = m[y][x];
                    }
                }
            }
        }
    }

    private boolean isInvalidMove(int nextX, int nextY, int[][] nextMatrix) {
        for (int row = 0; row < nextMatrix.length; row++) {
            for (int col = 0; col < nextMatrix[row].length; col++) {

                if (nextMatrix[row][col] != 0) {
                    int targetX = nextX + col;
                    int targetY = nextY + row;

                    if (targetX < 0 || targetX >= 10 || targetY >= 20) {
                        return true;
                    }

                    if (targetY >= 0 && board[targetY][targetX] != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void spawnNewShape() {
        this.currentShape = factory.getShape(this.random.nextInt(7)); //удалить нахуй Shape.color
        int color = this.random.nextInt(6) + 1;
        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                this.currentShape.matrix[x][y] *= color;
            }
        }
    }

    private void clearLines() {
        for (int y = 19; y >= 0; y--) {
            boolean isFull = true;

            for (int x = 0; x < 10; x++) {
                if (this.board[y][x] == 0) {
                    isFull = false;
                    break;
                }
            }

            if (isFull) {
                shiftLinesDown(y);
                this.scores++;
                y++;
            }
        }
    }

    private void shiftLinesDown(int startY) {
        for (int y = startY; y > 0; y--) {
            for (int x = 0; x < 10; x++) {
                this.board[y][x] = this.board[y - 1][x];
            }
        }
        for (int x = 0; x < 10; x++) {
            this.board[0][x] = 0;
        }
    }
    private void handleGameOver() {
        System.out.println("Game Over!");
        this.board = new int[20][10];
    }

    private void clearBoard() {
        for(int i = 0; i < this.board.length; i++) {
            for(int j = 0; j < this.board[i].length; j++) {
                this.board[i][j] = 0;
            }
        }
    }
}
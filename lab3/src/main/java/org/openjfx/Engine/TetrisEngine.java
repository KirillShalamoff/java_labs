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
    private Shape nextShape;
    private String about;

    public TetrisEngine() {
        this.isRunning = true;
        this.board = new int[20][10]; // Здесь было верно: [Y][X]
        this.factory = new ShapesFactory();
        this.random = new Random();
        this.about = "сделано с душой и без фигни.\n ver:xx.xxx.xxxx";

        this.currentShape = spawnNewShape();
        this.nextShape = spawnNewShape();

    }

    public String getAbout() {
        return about;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
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

    public Shape getNextShape(){ return this.nextShape; }


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
        if (!isInvalidMove(currentShape.x, currentShape.y, currentShape.rotateMatrix())) {
            currentShape.matrix = currentShape.rotateMatrix();
        }
    }

    public void update() {
        if (isInvalidMove(currentShape.x, currentShape.y + 1, currentShape.matrix)) {
            freezeShape(currentShape);

            if (isInvalidMove(nextShape.x, nextShape.y, nextShape.matrix) && isFull()) {
                this.isRunning = false;
                return;
            }

            clearLines();
            currentShape = nextShape;
            nextShape = spawnNewShape();


        } else {
            currentShape.moveDown();
        }
    }

    private boolean isFull() {
        if (this.board[0][5] + this.board[0][6] != 0) {
            return true;
        }
        return false;
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

                    if ((targetY >= 0) && (board[targetY][targetX] != 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Shape spawnNewShape() {
        // 1. Получаем болванку из фабрики
        Shape shape = factory.getShape(this.random.nextInt(7));

        // 2. Генерируем случайный цвет (1-7)
        int colorId = this.random.nextInt(7) + 1;

        // 3. Красим матрицу
        int[][] m = shape.matrix;
        for (int y = 0; y < m.length; y++) {
            for (int x = 0; x < m[y].length; x++) {
                if (m[y][x] != 0) {
                    m[y][x] = colorId; // Присваиваем ID цвета вместо 1
                }
            }
        }

        shape.x = 3;   // Центр по горизонтали
        shape.y = -3;  // Прячем фигуру выше видимой зоны (за верхнюю границу)

        return shape;
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
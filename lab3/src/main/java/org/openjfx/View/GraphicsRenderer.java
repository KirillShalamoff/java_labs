package org.openjfx.View;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.openjfx.Engine.TetrisEngine;
import org.openjfx.Shapes.Shape;

public class GraphicsRenderer {
    private final Canvas canvas;
    private static final int BLOCK_SIZE = 30;

    public GraphicsRenderer(Canvas canvas) {
        this.canvas = canvas;
    }

    public void render(TetrisEngine engine, Shape current, Shape next, Canvas nextCanvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 1. Фон приложения и самого стакана
        gc.setFill(Color.web("#2c3e50")); // Общий фон
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.BLACK); // Фон стакана
        gc.fillRect(0, 0, 10 * BLOCK_SIZE, 20 * BLOCK_SIZE);

        // 2. Сетка и границы
        drawGrid(gc);
        drawBorder(gc);

        // 3. Отрисовка блоков
        drawBoard(engine, gc);
        if (current != null) {
            drawCurrentShape(current, gc);
        }

        // 4. Превью следующей фигуры
        renderNextPreview(next, nextCanvas);
    }

    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(Color.web("#34495e"));
        gc.setLineWidth(0.5);
        for (int i = 0; i <= 10 * BLOCK_SIZE; i += BLOCK_SIZE) gc.strokeLine(i, 0, i, 20 * BLOCK_SIZE);
        for (int i = 0; i <= 20 * BLOCK_SIZE; i += BLOCK_SIZE) gc.strokeLine(0, i, 10 * BLOCK_SIZE, i);
    }

    private void drawBorder(GraphicsContext gc) {
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2.0);
        gc.strokeRect(1, 1, 10 * BLOCK_SIZE - 2, 20 * BLOCK_SIZE - 2);
    }

    private void drawBoard(TetrisEngine engine, GraphicsContext gc) {
        int[][] board = engine.getBoard();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != 0) drawBlock(gc, col, row, getFxColor(board[row][col]));
            }
        }
    }

    private void drawCurrentShape(Shape shape, GraphicsContext gc) {
        int[][] matrix = shape.matrix;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0 && (shape.getY() + i) >= 0) {
                    drawBlock(gc, shape.getX() + j, shape.getY() + i, getFxColor(matrix[i][j]));
                }
            }
        }
    }

    private void renderNextPreview(Shape next, Canvas nextCanvas) {
        GraphicsContext nGc = nextCanvas.getGraphicsContext2D();
        double w = nextCanvas.getWidth();
        double h = nextCanvas.getHeight();

        nGc.setFill(Color.web("#1a1a1a"));
        nGc.fillRect(0, 0, w, h);

        if (next != null) {
            int[][] m = next.matrix;
            double offX = (w - m.length * BLOCK_SIZE) / 2.0;
            double offY = (h - m.length * BLOCK_SIZE) / 2.0;

            for (int i = 0; i < m.length; i++) {
                for (int j = 0; j < m[i].length; j++) {
                    if (m[i][j] != 0) {
                        nGc.setFill(getFxColor(m[i][j]));
                        nGc.fillRect(offX + j * BLOCK_SIZE, offY + i * BLOCK_SIZE, BLOCK_SIZE - 1, BLOCK_SIZE - 1);
                    }
                }
            }
        }
    }

    private void drawBlock(GraphicsContext gc, int x, int y, Color color) {
        gc.setFill(color);
        gc.fillRect(x * BLOCK_SIZE + 1, y * BLOCK_SIZE + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2);
        gc.setStroke(Color.web("#2c3e50"));
        gc.strokeRect(x * BLOCK_SIZE + 1, y * BLOCK_SIZE + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2);
    }

    private Color getFxColor(int id) {
        return switch (id) {
            case 1 -> Color.CYAN;   case 2 -> Color.PURPLE;
            case 3 -> Color.ORANGE; case 4 -> Color.BLUE;
            case 5 -> Color.YELLOW; case 6 -> Color.GREEN;
            case 7 -> Color.RED;    default -> Color.GRAY;
        };
    }
}
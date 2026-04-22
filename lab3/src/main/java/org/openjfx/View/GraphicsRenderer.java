package org.openjfx.View;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.openjfx.Engine.TetrisEngine;
import org.openjfx.Shapes.Shape;

public class GraphicsRenderer {
    private final Canvas canvas;
    private static final int BLOCK_SIZE = 30; // Размер одного квадратика в пикселях

    public GraphicsRenderer(Canvas canvas) {
        this.canvas = canvas;
    }

    public void render(TetrisEngine engine, Shape shape) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.web("#2c3e50")); // Темный стильный фон
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        drawGrid(gc, engine);

        drawBoard(engine, gc);

        if (shape != null) {
            drawCurrentShape(shape, gc);
        }
    }

    private void drawBoard(TetrisEngine engine, GraphicsContext gc) {
        int[][] board = engine.getBoard();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                // Если в ячейке не 0, значит там лежит ID цвета застывшей фигуры
                if (board[row][col] != 0) {
                    // Извлекаем ID цвета (например, 1, 2, 3...) и конвертируем в Color
                    Color blockColor = getFxColor(board[row][col]);
                    drawBlock(gc, col, row, blockColor);
                }
            }
        }
    }

    private void drawCurrentShape(Shape shape, GraphicsContext gc) {
        if (shape == null) return;

        int[][] matrix = shape.matrix;
        int startX = shape.getX();
        int startY = shape.getY();

        // Рисуем каждый блок текущей фигуры её цветом
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    int blockX = startX + j;
                    int blockY = startY + i;

                    // Используем ID цвета самой фигуры
                    drawBlock(gc, blockX, blockY, getFxColor(shape.color));
                }
            }
        }
    }

    // Вспомогательный метод для конвертации твоего int в нормальный цвет
    private Color getFxColor(int colorId) {
        return switch (colorId) {
            case 1 -> Color.CYAN;
            case 2 -> Color.PURPLE;
            case 3 -> Color.ORANGE;
            case 4 -> Color.BLUE;
            case 5 -> Color.YELLOW;
            case 6 -> Color.GREEN;
            case 7 -> Color.RED;
            default -> Color.GRAY; // На случай ошибки
        };
    }
    // Вспомогательный метод, чтобы не дублировать код отрисовки квадрата
    private void drawBlock(GraphicsContext gc, int x, int y, Color color) {
        gc.setFill(color);
        // fillRect(x_px, y_px, width, height)
        // -1 и -2 используются для создания небольшого зазора (рамки) между блоками
        gc.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE - 1, BLOCK_SIZE - 1);

        // Добавляем легкую бликовку сверху для объема
        gc.setStroke(color.brighter());
        gc.strokeRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE - 1, BLOCK_SIZE - 1);
    }

    private void drawGrid(GraphicsContext gc, TetrisEngine engine) {
        gc.setStroke(Color.web("#34495e"));
        gc.setLineWidth(0.5);
        for (int x = 0; x <= canvas.getWidth(); x += BLOCK_SIZE) {
            gc.strokeLine(x, 0, x, canvas.getHeight());
        }
        for (int y = 0; y <= canvas.getHeight(); y += BLOCK_SIZE) {
            gc.strokeLine(0, y, canvas.getWidth(), y);
        }
    }
}
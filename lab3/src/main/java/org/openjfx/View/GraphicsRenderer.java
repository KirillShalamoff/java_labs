package org.openjfx.View;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjfx.Engine.TetrisEngine;
import org.openjfx.Shapes.Shape;
import org.w3c.dom.css.Rect;

public class GraphicsRenderer {
    private final Canvas canvas;
    private static final int BLOCK_SIZE = 30;
    // Поменял на debug, чтобы консоль не "взорвалась" от спама
    private final Logger logger = LogManager.getLogger(GraphicsRenderer.class);

    public GraphicsRenderer(Canvas canvas) {
        this.canvas = canvas;
    }

    public void render(TetrisEngine engine, Shape currentShape, Shape nextShape, Canvas nextCanvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 1. Фон
        gc.setFill(Color.web("#2c3e50"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 10 * BLOCK_SIZE, 20 * BLOCK_SIZE);

        // 2. Сетка и границы
        drawGrid(gc);

        // 3. Отрисовка
        drawBoard(engine, gc);
        if (currentShape != null) {
            drawGhostShape(currentShape, engine, gc);
            drawCurrentShape(currentShape, gc);
            drawStrokes(currentShape, engine, gc);

        }

        // 4. Превью
        renderNextPreview(nextShape, nextCanvas);
        drawBorder(gc);

        logger.debug("Scene rendered");
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
                if (board[row][col] != 0) {
                    // Офсет 0, 0 для основного поля
                    drawBlock(gc, 0, 0, col, row, getFxColor(board[row][col]));
                }
            }
        }
    }

    private void drawCurrentShape(Shape shape, GraphicsContext gc) {
        int[][] matrix = shape.matrix;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0 && (shape.getY() + i) >= 0) {
                    // Рисуем текущую фигуру (офсет тоже 0, 0)
                    drawBlock(gc, 0, 0, shape.getX() + j, shape.getY() + i, getFxColor(matrix[i][j]));
                }
            }
        }
    }

    private void renderNextPreview(Shape next, Canvas nextCanvas) {
        GraphicsContext nGc = nextCanvas.getGraphicsContext2D();
        double w = nextCanvas.getWidth();
        double h = nextCanvas.getHeight();

        // 1. Фон как на основном поле
        nGc.setFill(Color.web("#2c3e50"));
        nGc.fillRect(0, 0, w, h);

        // 2. Внутренний черный квадрат (подложка под фигуру)
        int gridSize = 4;
        double startX = (w - gridSize * BLOCK_SIZE) / 2.0;
        double startY = (h - gridSize * BLOCK_SIZE) / 2.0;

        nGc.setFill(Color.BLACK);
        nGc.fillRect(startX, startY, gridSize * BLOCK_SIZE, gridSize * BLOCK_SIZE);

        // 3. Сетка внутри окна превью
        nGc.setStroke(Color.web("#34495e"));
        nGc.setLineWidth(0.5);
        for (int i = 0; i <= gridSize; i++) {
            // Вертикальные линии
            double x = startX + i * BLOCK_SIZE;
            nGc.strokeLine(x, startY, x, startY + gridSize * BLOCK_SIZE);
            // Горизонтальные линии
            double y = startY + i * BLOCK_SIZE;
            nGc.strokeLine(startX, y, startX + gridSize * BLOCK_SIZE, y);
        }

        // 4. Отрисовка самой фигуры
        if (next != null) {
            int[][] m = next.matrix;
            for (int i = 0; i < m.length; i++) {
                for (int j = 0; j < m[i].length; j++) {
                    if (m[i][j] != 0) {
                        // Используем универсальный метод drawBlock
                        drawBlock(nGc, startX, startY, j, i, getFxColor(m[i][j]));
                    }
                }
            }
        }

        // 5. Белая рамка вокруг окна превью (опционально, для стиля)
        nGc.setStroke(Color.WHITE);
        nGc.setLineWidth(1.0);
        nGc.strokeRect(startX, startY, gridSize * BLOCK_SIZE, gridSize * BLOCK_SIZE);

        logger.debug("Preview window rendered with main style");
    }

    // Универсальный солдат отрисовки
    private void drawBlock(GraphicsContext gc, double offX, double offY, int x, int y, Color color) {
        gc.setFill(color);
        gc.fillRect(offX + x * BLOCK_SIZE + 1, offY + y * BLOCK_SIZE + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2);
        gc.setStroke(Color.web("#2c3e50"));
        gc.strokeRect(offX + x * BLOCK_SIZE + 1, offY + y * BLOCK_SIZE + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2);
    }

    private void drawGhostShape(Shape currentShape, TetrisEngine engine, GraphicsContext gc) {
        if (currentShape == null) return;

        int[][] matrix = currentShape.matrix;
        int ghostY = currentShape.getY();

        // 1. Симулируем падение фигуры до упора вниз
        while(!engine.isInvalidMove(currentShape.x, ghostY + 1, currentShape.matrix)) {
            ghostY++;
        }

        // 2. Отрисовываем "призрак"
        // Используем тот же цвет, что у фигуры, но с очень низкой прозрачностью
        Color ghostColor = Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.2);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                // Рисуем блок призрака только если он в видимой зоне стакана
                if (matrix[i][j] != 0 && (ghostY + i) >= 0) {
                    drawGhostBlock(gc, currentShape.getX() + j, ghostY + i, ghostColor);
                }
            }
        }
    }

    // Специальный метод для отрисовки призрака (только контуры или прозрачный блок)
    private void drawGhostBlock(GraphicsContext gc, int x, int y, Color color) {
        // Вариант А: Полупрозрачный блок
        gc.setFill(color);
        gc.fillRect(x * BLOCK_SIZE + 2, y * BLOCK_SIZE + 2, BLOCK_SIZE - 4, BLOCK_SIZE - 4);
    }

    private void drawStrokes(Shape currentShape, TetrisEngine engine, GraphicsContext gc) {
        if (currentShape == null) return;

        int[][] matrix = currentShape.getMatrix();
        int minCol = matrix[0].length;
        int maxCol = -1;

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] != 0) {
                    if (col < minCol) minCol = col;
                    if (col > maxCol) maxCol = col;
                }
            }
        }

        gc.setStroke(Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.3));
        gc.setLineWidth(1.0);

        double leftX = (currentShape.getX() + minCol) * BLOCK_SIZE;

        double rightX = (currentShape.getX() + maxCol + 1) * BLOCK_SIZE;
        double boardHeight = engine.getBoard().length * BLOCK_SIZE;

        gc.strokeLine(leftX, 0, leftX, boardHeight);
        gc.strokeLine(rightX, 0, rightX, boardHeight);
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
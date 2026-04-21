package org.openjfx.View;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.openjfx.Engine.TetrisEngine;
import org.openjfx.Shapes.Shape;

public class GraphicsRenderer {
    Canvas canvas;

    public GraphicsRenderer(Canvas canvas) {
        this.canvas = canvas;
    }

    private void drawBoard() {

    }

    public void render(TetrisEngine engine, Shape shape) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

        drawBoard(engine, gc);
        drawCurrentShape(engine, gc);
    }
}

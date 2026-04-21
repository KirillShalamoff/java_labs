package org.openjfx.Shapes;

import javafx.scene.paint.Color;

public class ShapeJ extends Shape {
    public ShapeJ() {
        super(
            new int[][] {
                {0,0,1,0},
                {0,0,1,0},
                {0,1,1,0},
                {0,0,0,0}
            }, 2
        );

    }
}

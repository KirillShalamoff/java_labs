package org.openjfx.Shapes;

import javafx.scene.paint.Color;

public class ShapeL extends Shape {
    public ShapeL() {
        super(
                new int[][] {
                        {0,0,0,0},
                        {0,1,0,0},
                        {0,1,0,0},
                        {0,1,1,0}
                }, 3
        );

    }
}

package org.openjfx.Shapes;

import javafx.scene.paint.Color;

public class ShapeS extends Shape {
    public ShapeS() {
        super(
                new int[][] {
                        {0,0,0,0},
                        {0,1,1,0},
                        {1,1,0,0},
                        {0,0,0,0}
                }, 5
        );

    }
}

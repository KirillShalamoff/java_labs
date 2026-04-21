package org.openjfx.Shapes;
import javafx.scene.paint.Color;

abstract public class Shape {
    public int x;
    public int y;
    public int[][] matrix;
    public int color;

    public Shape(int[][] matrix, int color) {
        this.matrix = matrix;
        this.color = color;
        this.x = 4;
        this.y = 0;
    }
    public void moveDown() {
        this.y += 1;
    }
    public void moveLeft() {
        this.x -= 1;
    }
    public void moveRight() {
        this.x += 1;
    }
    public void rotate() {
        int[][] transposedMatrix = new int[4][4];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                transposedMatrix[j][i] = this.matrix[i][j];
            }
        }
        this.matrix = transposedMatrix;
    }
}

package org.openjfx.Shapes;

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

    public void setColor(int color) {
        this.color = color;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
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

    public int[][] rotateMatrix() {
        int n = matrix.length;
        int[][] rotatedMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotatedMatrix[j][n - 1 - i] = this.matrix[i][j];
            }
        }
        return rotatedMatrix;
    }
}

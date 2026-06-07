package org.openjfx.Shapes;

abstract public class Shape {
    public int x;
    public int y;
    public int[][] matrix;

    @Override
    public boolean equals(Object o) {
        Shape value = (Shape) o;
        if(value == null) return false;
        if(value.getMatrix().length != this.getMatrix().length) return false;
        for(int i = 0; i < this.getMatrix().length; i++) {
            if (value.getMatrix()[i].length != this.getMatrix()[i].length) return false;
        }

        for(int i = 0; i < this.getMatrix().length; i++) {
            for(int j = 0; j < this.getMatrix()[i].length; j++) {
                if (this.getMatrix()[i][j] * value.getMatrix()[i][j] == 0 && this.getMatrix()[i][j] + value.getMatrix()[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public Shape(int[][] matrix) {
        this.matrix = matrix;
        this.x = 4;
        this.y = 0;
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int[][] getMatrix() {
        return this.matrix;
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
    public void moveLeftDiag() {
        this.x -= 1;
        this.y += 1;
    }
    public void moveRightDiag() {
        this.x += 1;
        this.y += 1;
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

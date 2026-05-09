package org.openjfx.View;

import org.openjfx.Shapes.Shape;

public class ConsoleRenderer {
    public static void render(int[][] board, Shape currentShape) {
        System.out.flush();
        System.out.println("______________________");
        for (int y = 0; y < 20; y++) {
            System.out.print("|");
            for (int x = 0; x < 10; x++) {

                boolean isShapeBlock = false;


                int localX = x - currentShape.x;
                int localY = y - currentShape.y;

                if (localX >= 0 && localX < 4 && localY >= 0 && localY < 4) {
                    if (currentShape.matrix[localY][localX] != 0) {
                        isShapeBlock = true;
                    }
                }

                if (board[y][x] > 0 || isShapeBlock) {
                    System.out.print("[]");
                } else {
                    System.out.print(" .");
                }
            }
            System.out.println("|");
        }
        System.out.println("______________________");

    }
}


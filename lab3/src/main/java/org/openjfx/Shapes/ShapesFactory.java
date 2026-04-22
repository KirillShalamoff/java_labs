package org.openjfx.Shapes;

public class ShapesFactory {
    public ShapesFactory() {

    }

    public Shape getShape(int id) {

        return switch (id){
            case 0 -> new ShapeI();
            case 1 -> new ShapeJ();
            case 2 -> new ShapeL();
            case 3 -> new ShapeO();
            case 4 -> new ShapeS();
            case 5 -> new ShapeT();
            case 6 -> new ShapeZ();
            case 7 ->  new ShapeI();
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };
    }
}

package org.openjfx;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Board {
    ArrayList<Integer> field;

    public Board() {
        this.field = new ArrayList<>(200);
        for(Integer num : this.field){
            num = 0;
        }
    }
}


package com.code.dsa.dsa_automator;

import javafx.scene.Node;

public class Dragger {
    private double cursorX;
    private double cursorY;

    public void setDragger(Node node, boolean dec) {
        node.setOnMousePressed(mouseEvent -> {
            cursorX = mouseEvent.getX();
            cursorY = mouseEvent.getY();
        });
        node.setOnMouseDragged(mouseEvent -> {
            if (dec) {
                node.setLayoutX(mouseEvent.getSceneX() - cursorX);
                node.setLayoutY(mouseEvent.getSceneY() - cursorY);
            }
        });
    }
}

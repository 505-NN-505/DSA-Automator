package com.code.dsa.dsa_automator;

import javafx.scene.Node;

public class DrawNodes {
    private double cursorX;
    private double cursorY;

    public void setDraggerOn(Node node) {
        node.setOnMousePressed(mouseEvent -> {
            cursorX = mouseEvent.getX();
            cursorY = mouseEvent.getY();
        });
        node.setOnMousePressed(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - cursorX);
            node.setLayoutY(mouseEvent.getSceneY() - cursorY);
        });
    }
}

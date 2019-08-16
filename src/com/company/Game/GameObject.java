package com.company.Game;

import com.company.engine.GameLoop;
import com.company.engine.Renderer;

import java.io.Serializable;

public abstract class GameObject implements Serializable {
    protected float posX, posY, width, height;

    public GameObject(int posX, int posY, int width, int height){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public abstract void update(GameLoop gameLoop, GameManager gameManager, float dt);
    public abstract void render(GameLoop gameLoop, Renderer renderer);

    public float getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

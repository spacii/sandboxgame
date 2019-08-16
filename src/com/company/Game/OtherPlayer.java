package com.company.Game;

import com.company.engine.GameLoop;
import com.company.engine.Renderer;
import com.company.engine.gfx.ImageTile;

public class OtherPlayer extends Character {
    float temp = 0;
    private int ID;
    public OtherPlayer(int posX, int posY, int width, int height, int health, int id) {
        super(posX, posY, width, height, health, new ImageTile("/testMe.png",16,16));
        this.ID = id;
    }

    @Override
    public void update(GameLoop gameLoop, GameManager gameManager, float dt) {



        temp += dt;
        if(temp > 3){
            temp = 0;
        }
    }

    @Override
    public void render(GameLoop gameLoop, Renderer renderer) {
        renderer.drawImageTile(this.getImageTile(),(int)posX, (int)posY,(int)temp,6);
    }

    public int getID() {
        return ID;
    }
}

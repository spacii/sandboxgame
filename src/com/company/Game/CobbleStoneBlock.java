package com.company.Game;

import com.company.engine.GameLoop;
import com.company.engine.Renderer;
import com.company.engine.gfx.Image;

public class CobbleStoneBlock extends Block {

    static Image image = new Image("/CobbleStone16x16.jpg");

    public CobbleStoneBlock(int posX, int posY, int width, int height) {
        //super(posX, posY, width, height, new Image("/CobbleStone16x16.jpg"));
        super(posX, posY, width, height, image, 1);
    }

    @Override
    public void update(GameLoop gameLoop, GameManager gameManager, float dt) {

    }

    @Override
    public void render(GameLoop gameLoop, Renderer renderer) {
        renderer.drawImage(getTexture(),(int)posX,(int)posY);
    }
}

package com.company.Game;

import com.company.engine.GameLoop;
import com.company.engine.Renderer;
import com.company.engine.gfx.Image;

public class EmptyBlock extends Block {

    static Image image = new Image("/EmptyBlock16x16.jpg");

    public EmptyBlock(int posX, int posY, int width, int height) {
        super(posX, posY, width, height, image, 0);
    }

    @Override
    public void update(GameLoop gameLoop, GameManager gameManager, float dt) {

    }

    @Override
    public void render(GameLoop gameLoop, Renderer renderer) {
        renderer.drawImage(getTexture(),(int)posX,(int)posY);
    }
}

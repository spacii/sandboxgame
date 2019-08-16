package fwfwf.Game;

import fwfwf.engine.GameLoop;
import fwfwf.engine.Renderer;
import fwfwf.engine.gfx.Image;

public class BrickBlock extends Block {

    public BrickBlock(int posX, int posY, int width, int height) {
        super(posX, posY, width, height, new Image("/Brick16x16.jpg"), 3);
    }

    @Override
    public void update(GameLoop gameLoop, GameManager gameManager, float dt) {

    }

    @Override
    public void render(GameLoop gameLoop, Renderer renderer) {
        renderer.drawImage(getTexture(),(int)posX,(int)posY);
    }
}

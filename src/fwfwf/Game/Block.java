package fwfwf.Game;

import fwfwf.engine.gfx.Image;

public abstract class Block extends GameObject {

    private Image texture;
    private int ID;
    public Block(int posX, int posY, int width, int height, Image texture, int ID) {
        super(posX, posY, width, height);
        this.texture = texture;
        this.ID = ID;
    }

    public Image getTexture() {
        return texture;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}

package server;

import com.company.Game.Block;
import com.company.Game.BrickBlock;
import com.company.Game.CobbleStoneBlock;
import com.company.Game.EmptyBlock;
import com.company.engine.GameLoop;
import com.company.engine.Renderer;

import java.util.ArrayList;

public class GameWorldServer {

    private int BS = 16; // Количество пикселей на один блок
    //private int worldW = 30*BS, worldH = 15*BS; // Размер мира в пикселях
    private int worldW = 30*BS, worldH = 15*BS; // Размер мира в пикселях
    private ArrayList<Block> worldsBlocks; // Блоки
    private ArrayList<Boolean> worldCollision;

    public GameWorldServer(){
        worldsBlocks = new ArrayList<>();
        worldCollision = new ArrayList<>();
    }

    public void generateWorld(){
        for(int x = 0; x < worldW; x+=BS){ //0 16 32 48 64
            for(int y = 0; y < worldH/2; y+=BS){
                    worldsBlocks.add(new EmptyBlock(x, y, BS,BS));
                    worldCollision.add(true);
            }
        }

        for(int x = 0; x < worldW; x+=BS){
            for(int y =  worldH/2; y < worldH; y+=BS){
                worldsBlocks.add(new CobbleStoneBlock(x, y, 16,16));
                worldCollision.add(false);
            }
        }
    }

    public void update(GameLoop gameLoop, float dt) {

    }

    float temp = 0;


    public void render(GameLoop gameLoop, Renderer renderer) {
        for (int i = 0; i < worldsBlocks.size(); i++) {
            worldsBlocks.get(i).render(gameLoop, renderer);
        }
    }

    public ArrayList<Block> getWorldsBlocks() {
        return worldsBlocks;
    }

    public void setWorldsBlocks(ArrayList<Block> worldsBlocks) {
        this.worldsBlocks = worldsBlocks;
    }

    public ArrayList<Boolean> getWorldCollision() {
        return worldCollision;
    }
}

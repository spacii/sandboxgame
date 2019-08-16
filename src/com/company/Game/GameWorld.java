package com.company.Game;

import com.company.engine.GameLoop;
import com.company.engine.Renderer;

import java.util.ArrayList;

public class GameWorld {

    private int BS = 16; // Количество пикселей на один блок
    //private int worldW = 30*BS, worldH = 15*BS; // Размер мира в пикселях
    private int worldW = 30*BS, worldH = 15*BS; // Размер мира в пикселях
    private ArrayList<Block> worldsBlocks; // Блоки
    private ArrayList<Boolean> worldCollision;

    public GameWorld(){
        worldsBlocks = new ArrayList<>();
        worldCollision = new ArrayList<>();
    }

    public void loadWorld(ArrayList<Integer> id, ArrayList<Integer> x, ArrayList<Integer> y){
        for(int i = 0; i < x.size(); i++){
            for(int j = 0; j < y.size(); j++){
                int ID = id.get(i);
                switch (ID){
                    case 0 :
                        worldsBlocks.add(new EmptyBlock(x.get(i), y.get(i),16,16));
                        worldCollision.add(true);
                        break;
                    case 1 :
                        worldsBlocks.add(new CobbleStoneBlock(x.get(i), y.get(i),16,16));
                        worldCollision.add(false);
                        break;
                    case 3 :
                        worldsBlocks.add(new BrickBlock(x.get(i), y.get(i),16,16));
                        worldCollision.add(false);
                        break;
                    default:
                        break;
                }
            }
        }
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


    public boolean getCollision(float x, float y){
        int temp_index = 0;
        if(x < 0 || x >= worldW || y < 0 || y >= worldH){
            return false;
        }

        for(int i = 0; i < worldsBlocks.size(); i++){
            if(((worldsBlocks.get(i).getPosX() <= x) && (worldsBlocks.get(i).getPosX() + 15 >= x))
            && ((worldsBlocks.get(i).getPosY() <= y) && (worldsBlocks.get(i).getPosY() + 15 >= y))){
                temp_index = i;
            }
        }
        return worldCollision.get(temp_index);
    }

    public void buildBlock(int x, int y){
        for(int i = 0; i < worldsBlocks.size(); i++){
            if(((worldsBlocks.get(i).getPosX() <= x && worldsBlocks.get(i).getPosX()+16 >= x)
                    && (worldsBlocks.get(i).getPosY() <= y && worldsBlocks.get(i).getPosY()+16 >= y)) && worldsBlocks.get(i).getClass().getSimpleName().contains("Empty")){
                System.out.println(i + " " + worldsBlocks.get(i).getPosX() + " " + worldsBlocks.get(i).getPosY());
                int xx = (int)worldsBlocks.get(i).posX; int yy = (int)worldsBlocks.get(i).posY;
                worldsBlocks.set(i, new BrickBlock(xx,yy,16,16));
                worldCollision.set(i, false);
            }
        }
    }

    public void destroyBlock(int x, int y){
        for(int i = 0; i < worldsBlocks.size(); i++){
            if((worldsBlocks.get(i).getPosX() <= x && worldsBlocks.get(i).getPosX()+16 >= x)
            && (worldsBlocks.get(i).getPosY() <= y && worldsBlocks.get(i).getPosY()+16 >= y)){
                System.out.println(i + " " + worldsBlocks.get(i).getPosX() + " " + worldsBlocks.get(i).getPosY());
                int xx = (int)worldsBlocks.get(i).posX; int yy = (int)worldsBlocks.get(i).posY;
                worldsBlocks.set(i, new EmptyBlock(xx,yy,16,16));
                worldCollision.set(i, true);
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

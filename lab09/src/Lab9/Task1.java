package Lab9;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

/**
 * Draws a world initially full of trees.//绘制一个初始时充满树木的世界
 */

public class Task1 {
    private static final int WIDTH=60;
    private static final int HEIGHT=30;
    /**
     * Fills the entire 2D world with the Tileset.TREE tile.
     *///用Tileset.TREE这个tile填满整个二维世界
    private static void fillWithTrees(TETile[][] world) {
        int x,y;
        for(x=0;x<world.length;x++){
            for(y=0;y<world[x].length;y++){
                world[x][y]=Tileset.TREE;
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter=new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        TETile[][]world=new TETile[WIDTH][HEIGHT];
        fillWithTrees(world);
        ter.renderFrame(world);

    }
}
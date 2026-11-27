package demo;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
//实验九：随机森林世界；
/**
 *  Draws a world that is mostly empty except for a small region.
 *///绘制一个世界，除了一个小部分，其他都是空的；
public class BoringWorldDemo {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT//下一步要创建一个TERenderer对象并调用initialize(WIDTH,HEIGHT)
        //以便打开一个60*30的图块渲染窗口；
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles//初始化图块；
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // fills in a block 15 tiles wide by 5 tiles tall//填充一个宽十五格，高五格的方块区域；
        for (int x = 20; x < 35; x++) {
            for (int y = 5; y < 10; y++) {
                world[x][y] = Tileset.WALL;
            }
        }

        // draws the world to the screen//把世界花到频幕上
        ter.renderFrame(world);
    }


}

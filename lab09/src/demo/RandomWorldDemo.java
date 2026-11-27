package demo;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

/**
 * Draws a world that contains RANDOM tiles.
 *///绘制一个包含随机图块的世界
public class RandomWorldDemo {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Fills the given 2D array of tiles with RANDOM tiles.//用随机图块填充给定的二维图块数组
     * @param tiles///参数：二维图块数组
     */
    public static void fillWithRandomTiles(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = randomTile();
            }
        }
    }

    /** Picks a RANDOM tile with a 33% change of being
     *  a wall, 33% chance of being a flower, and 33%
     *  chance of being empty space.
     *///随机挑一个图块，33%的概率是墙，33%的概率是花，33%的概率，是空白；
    private static TETile randomTile() {
        // The following call to nextInt() uses a bound of 3 (this is not a seed!) so
        // the result is bounded between 0, inclusive, and 3, exclusive. (0, 1, or 2)//下面调用的nextInt()传了3作为边界，（这不是随机种子），所以结果的范围是>=0,<3,即0，1，2
        int tileNum = RANDOM.nextInt(3);
        return switch (tileNum) {
            case 0 -> Tileset.WALL;
            case 1 -> Tileset.FLOWER;
            default -> Tileset.NOTHING;
        };
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];
        fillWithRandomTiles(randomTiles);

        ter.renderFrame(randomTiles);
    }

}

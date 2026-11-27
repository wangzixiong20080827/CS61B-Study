package demo;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

/**
 * This demo shows how to build an interactive game.
 * In this demo, we create a world with 5 squares.
 * Pressing keys 1 2 3 4 5 causes the corresponding square to change between FLOOR and TILE.
 * Pressing key q causes the game to quit.
 *///这个演示展现了如何构建一个交互式游戏，在这个演示中，我们创建了一个包含5个方快的世界，按数字键12345，对应的方块会在floor和tile之间切换，按q
    //键让游退出；
public class GameLoopDemo {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    /**
     * Helper function.
     * If the tile at position (x,y) is FLOOR, change it to WALL.
     * If the tile at position (x,y) is WALL, change it to FLOOR.
     *///辅助函数，如果位置（x,y)处的tile是floor,就把他改成wall,如果位置(x,y）处的tile是wall,就把他改成floor;
    public static void toggle(TETile[][] world, int x, int y) {
        if (world[x][y].equals(Tileset.FLOOR)) {
            world[x][y] = Tileset.WALL;
        } else if (world[x][y].equals(Tileset.WALL)) {
            world[x][y] = Tileset.FLOOR;
        }
    }

    public static void main(String[] args) {
        // Initialize the tile rendering engine with a window of size WIDTH x HEIGHT.
        // This WIDTH and HEIGHT is very small!//用一个WIDTH*HEIGHT大小的窗口初始化tile渲染引擎，这个WIDTH和HEIGHT非常小；
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // Fill in the tile grid with FLOOR tiles.
        // If you don't fill in the grid, you'll get a NullPointerException!//用FLOOR图块填满整个网格；
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.FLOOR;
            }
        }

        char c; // Variable for saving the most recent character typed by the user.//用来保存用户最近一次输入的字符变量

        // This outer infinite-loop allows the game to continue indefinitely, until the user quits.//这个外层无限循环让游戏一直进行，直到用户退出为止。
        while (true) {

            // hasNextKeyTyped checks if the user has typed a key that we haven't processed.//检查用户是否按了未被处理的键
            // This loop runs until all unprocessed keys are processed.//这个循环会一直进行，直到未被处理的键被处理完
            // If there are no unprocessed keys, we go back to the outer infinite loop to wait for the next key.//如果没有未被处理的键，就回到外层无限循环，等待下一个键
            while (StdDraw.hasNextKeyTyped()) {

                // nextKeyTyped returns the next key to process.//返回下一个要处理的按键，
                // Always check hasNextKeyTyped before calling nextKeyTyped.//在调用nextKeyTyped时，一定要先检查hasNextKeyTyped;
                // If you call nextKeyTyped and there's no key to process, code will crash!//如果没有待处理的按键却调用hasNextKeyType,程序会崩溃
                c = StdDraw.nextKeyTyped();

                c = Character.toLowerCase(c);

                // Switch statements can be useful to replace long if-else statements!//switch语句很适合替代一长串if-else!
                switch (c) {
                    case '1':
                        toggle(world, 0, 0);
                        break;
                    case '2':
                        toggle(world, 1, 0);
                        break;
                    case '3':
                        toggle(world, 2, 0);
                        break;
                    case '4':
                        toggle(world, 3, 0);
                        break;
                    case '5':
                        toggle(world, 4, 0);
                        break;
                    case 'q':
                        System.exit(0); // Closes the game window and quits the game.//关闭游戏窗口并退出
                        break;
                    default:
                        break;
                }

            }

            // Draws the world to the screen.//把世界画到频幕上
            // This is in the while(true) loop, because we want to frequently re-render the world.//这行放在while(true)循环里，因为我们需要频繁的重绘世界
            ter.renderFrame(world);
        }
    }
}
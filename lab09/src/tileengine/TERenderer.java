package tileengine;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;

/**
 * Utility class for rendering tiles. You do not need to modify this file. You're welcome
 * to, but be careful. We strongly recommend getting everything else working before
 * messing with this renderer, unless you're trying to do something fancy like
 * allowing scrolling of the screen or tracking the avatar or something similar.
 *///这是用于渲染图块的实用工具类，你不需要修改这个文件，当然你想改也可以，但要小心，我们强烈建议先让其他部分
    //正常工作，在去动这个渲染器，--除非你想做一些高级的功能，比如让屏幕滚动，或者让镜头随角色之类的事情
public class TERenderer {
    private static final int TILE_SIZE = 16;
    private int width;
    private int height;
    private int xOffset;
    private int yOffset;

    /**
     * Same functionality as the other initialization method. The only difference is that the xOff
     * and yOff parameters will change where the renderFrame method starts drawing. For example,
     * if you select w = 60, h = 30, xOff = 3, yOff = 4 and then call renderFrame with a
     * TETile[50][25] array, the renderer will leave 3 tiles blank on the left, 7 tiles blank
     * on the right, 4 tiles blank on the bottom, and 1 tile blank on the top.
     * @param w width of the window in tiles//w:窗口宽度；
     * @param h height of the window in tiles.//hh:窗口高度，以图块为单位
     *///功能与另一个初始化方法相同，唯一的区别在于，xOff和yOff参数会改变renderFrame方法开始绘制的起点位置
    //举例来说，如果你设置w=60，h=30,xOff=3,yOff=4,然后用一个TETile[50][25]的数组调用renderFrame,渲染器会在左边空出三格，
    //右边空出7格，底部空出4格，顶部空出1格；
    public void initialize(int w, int h, int xOff, int yOff) {
        this.width = w;
        this.height = h;
        this.xOffset = xOff;
        this.yOffset = yOff;
        StdDraw.setCanvasSize(width * TILE_SIZE, height * TILE_SIZE);
        resetFont();
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);

        StdDraw.clear(new Color(0, 0, 0));

        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    /**
     * Initializes StdDraw parameters and launches the StdDraw window. w and h are the
     * width and height of the world in number of tiles. If the TETile[][] array that you
     * pass to renderFrame is smaller than this, then extra blank space will be left
     * on the right and top edges of the frame. For example, if you select w = 60 and
     * h = 30, this method will create a 60 tile wide by 30 tile tall window. If
     * you then subsequently call renderFrame with a TETile[50][25] array, it will
     * leave 10 tiles blank on the right side and 5 tiles blank on the top side. If
     * you want to leave extra space on the left or bottom instead, use the other
     * initializatiom method.
     * @param w width of the window in tiles//w:窗口宽度；
     * @param h height of the window in tiles.//h:窗口高度；
     *///初始化StdDraw的参数并启动StrDraw窗口，w和h分别是世界的宽度和高度，单位是图块(tile)的数量，,这是对initialize(int w,int h)的方法说明；
    //初始化StdDraw的参数并启动StdDraw窗口，w和h分别是世界的宽度和高度，单位是图块(tile)的数量；
    //如果你传给renderFrame的TETile[][]数组比窗口小，多余的空白会留在画面的右边和顶部；
    //举例，w=60,h=30,会创建60格宽，30格高的窗口，之后用TETile[50][25]数组调用renderFrame，右侧会空10格，顶部空5格；
    //如果想把多余空间留在左边或底部，改用另一个带偏移参数的initialize方法；
    //
    public void initialize(int w, int h) {
        initialize(w, h, 0, 0);
    }

    /**
     * Takes in a 2d array of TETile objects and renders the 2d array to the screen, starting from
     * xOffset and yOffset.//接受一个TETile对象的二维数组，从xOffset和yOffset指定的位置开始，将这个二维数组渲染到屏幕上；
     *
     * If the array is an NxM array, then the element displayed at positions would be as follows,
     * given in units of tiles.//如果数组是N*M的，那么各位置显示的元素如下表：//world[0][0]画在左下角，world[N-1][M-1]画在右上角，world[X][Y]:x是横向，y是纵向；
     *
     *              positions   xOffset |xOffset+1|xOffset+2| .... |xOffset+world.length//窗口y轴向上，x轴向有；
     *                     
     * startY+world[0].length   [0][M-1] | [1][M-1] | [2][M-1] | .... | [N-1][M-1]
     *                    ...    ......  |  ......  |  ......  | .... | ......
     *               startY+2    [0][2]  |  [1][2]  |  [2][2]  | .... | [N-1][2]
     *               startY+1    [0][1]  |  [1][1]  |  [2][1]  | .... | [N-1][1]
     *                 startY    [0][0]  |  [1][0]  |  [2][0]  | .... | [N-1][0]
     *
     * By varying xOffset, yOffset, and the size of the screen when initialized, you can leave
     * empty space in different places to leave room for other information, such as a GUI.
     * This method assumes that the xScale and yScale have been set such that the max x
     * value is the width of the screen in tiles, and the max y value is the height of
     * the screen in tiles.
     * @param world the 2D TETile[][] array to render//要渲染的二维TETile[][]数组；
     *///通过调整xOffset,yOffset以及初始化时屏幕的大小，你可以在不同位置留出空白，给其他信息（比如UI界面）留出空间；
    //这个方法假设xScale和yScale已经设置好，使得x的最大值是屏幕宽度，（单位：图块），y的最大值是屏幕高度（单位：图块）；
    public void renderFrame(TETile[][] world) {
        StdDraw.clear(new Color(0, 0, 0));
        drawTiles(world);
        StdDraw.show();
    }

    /**
     * Draws all world tiles without clearing the canvas or showing the tiles.
     * @param world the 2D TETile[][] array to render//TETile[][]要渲染的二维数组；
     *///绘制所有世界图块，但不清除画布，也不显示这些图块，
    public void drawTiles(TETile[][] world) {
        int numXTiles = world.length;
        int numYTiles = world[0].length;
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x + xOffset, y + yOffset);
            }
        }
    }

    /**
     * Resets the font to default settings. You should call this method before drawing any tiles
     * if you changed the pen settings.
     *///将字体重置为默认设置，如果你修改过画笔设置，那么应该在绘制任何图块之前调用这个方法；
    public void resetFont() {
        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);
    }
}

package tileengine;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.StdDraw;
import utils.RandomUtils;

/**
 * The TETile object is used to represent a single tile in your world. A 2D array of tiles make up a
 * board, and can be drawn to the screen using the TERenderer class.
 *
 * All TETile objects must have a character, textcolor, and background color to be used to represent
 * the tile when drawn to the screen. You can also optionally provide a path to an image file of an
 * appropriate size (16x16) to be drawn in place of the unicode representation. If the image path
 * provided cannot be found, draw will fallback to using the provided character and color
 * representation, so you are free to use image tiles on your own computer.
 *
 * The provided TETile is immutable, i.e. none of its instance variables can change. You are welcome
 * to make your TETile class mutable, if you prefer.
 *///TETile对象用来表示你世界中的一个格子，由tile组成的二维数组构成一个棋盘，可以用TeRenderer类绘制到频幕上
//所有TETile对象都必须有字符，文字颜色和背景色，才能在会知道屏幕是代表这个格子；
    //你可以选择性的提供一个合适尺寸，16*16的图片文件路径，用来代替Unicode字符来绘制；
    //提供的TETile是不可变的；也就是说他的任何实例变量都不能被修改；如果你愿意，可以把自己的TETile类改成可变的；
public class TETile {
    private final char character; // Do not rename character or the autograder will break.//不要重命名character,否则评分词系统会出错；
    private final Color textColor;
    private final Color backgroundColor;
    private final String description;
    private final String filepath;
    private final int id;

    /**
     * Full constructor for TETile objects.
     * @param character The character displayed on the screen.//TETile对象的完整构造函数；
     * @param textColor The color of the character itself.//屏幕上显示的字符
     * @param backgroundColor The color drawn behind the character.//字符本身的颜色
     * @param description The description of the tile, shown in the GUI on hovering over the tile.//字符背后绘制的背景色
     * @param filepath Full path to image to be used for this tile. Must be correct size (16x16)
     *///这个图块的描述文字，当鼠标悬停在该图块上时在GUI中显示；
    public TETile(char character, Color textColor, Color backgroundColor, String description,
                  String filepath, int id) {
        this.character = character;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.description = description;
        this.filepath = filepath;
        this.id = id;
    }

    /**
     * Constructor without filepath. In this case, filepath will be null, so when drawing, we
     * will not even try to draw an image, and will instead use the provided character and colors.//这是不带filepath的构造函数，此时filepath会被设为null,绘制时不会尝试画图片，而是直接使用提供的字符和颜色；
     * @param character The character displayed on the screen.//character:屏幕上显示的字符；
     * @param textColor The color of the character itself.//textcolor:字符的颜色
     * @param backgroundColor The color drawn behind the character.//backgroundcolor:字符背后的背景色；
     * @param description The description of the tile, shown in the GUI on hovering over the tile.//description:鼠标悬停时显示的描述；
     */
    public TETile(char character, Color textColor, Color backgroundColor, String description, int id) {
        this.character = character;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.description = description;
        this.filepath = null;
        this.id = id;
    }

    /**
     * Creates a copy of TETile t, except with given textColor.
     * @param t tile to copy//t：要复制的图块；
     * @param textColor foreground color for tile copy//副本的前景色：（文字颜色）；
     *///创建一个TETile对象t的副本，唯一的不同是使用指定的文字颜色；
    public TETile(TETile t, Color textColor) {
        this(t.character, textColor, t.backgroundColor, t.description, t.filepath, t.id);
    }

    /**
     * Creates a copy of TETile t, except with given character.//创建一个TETile对象t的副本，唯一的不同是使用指定的字符，
     * @param t tile to copy//t：要复制的图块，
     * @param c character for tile copy//c:要复制的字符；
     */
    public TETile(TETile t, char c) {
        this(c, t.textColor, t.backgroundColor, t.description, t.filepath, t.id);
    }


    /**
     * Draws the tile to the screen at location x, y. If a valid filepath is provided,
     * we draw the image located at that filepath to the screen. Otherwise, we fall
     * back to the character and color representation for the tile.//把这个图块绘制到频幕上（x,Y)的位置，如果提供了有效的filepath,我们就把该路径上的图片绘制到屏幕，否则，我们回退使用图块的字符和对应的颜色表示；
     *
     * Note that the image provided must be of the right size (16x16). It will not be
     * automatically resized or truncated.//注意：提供的图片必须是正确尺寸，（16*16），它不会被自动缩放或裁剪；
     * @param x x coordinate//x;x坐标；
     * @param y y coordinate//y:y坐标
     */
    public void draw(double x, double y) {
        if (filepath != null) {
            try {
                StdDraw.picture(x + 0.5, y + 0.5, filepath);
                return;
            } catch (IllegalArgumentException e) {
                // Exception happens because the file can't be found. In this case, fail silently
                // and just use the character and background color for the tile.//这个异常发生是因为文件找不到；这种情况下，静默失败；
            }//直接使用字符和背景色来代替这个图块；
        }

        StdDraw.setPenColor(backgroundColor);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5);
        StdDraw.setPenColor(textColor);
        StdDraw.text(x + 0.5, y + 0.5, Character.toString(character()));
    }

    /** Character representation of the tile. Used for drawing in text mode.
     * @return character representation
     *///图块的字符表式，用于文本模式下绘制，return 字符表示；
    public char character() {
        return character;
    }

    /**
     * Description of the tile. Useful for displaying mouseover text or
     * testing that two tiles represent the same type of thing.//图块的描述文字；它可用于显示鼠标悬停提示，或用于测试两个图块是否表示同一个东西；
     * @return description of the tile//return 图块的描述；
     */
    public String description() {
        return description;
    }

    /**
     * ID number of the tile. Used for equality comparisons.
     * @return id of the tile//返回图块的id.
     *///图块的id编号。用于相等性比较
    public int id() {
        return id;
    }

    /**
     * Creates a copy of the given tile with a slightly different text color. The new
     * color will have a red value that is within dr of the current red value,
     * and likewise with dg and db.//创建一个给定tile的副本。文字颜色略有不同。新颜色的红色分量与当前红色分量的差值在dr以内。绿色和蓝色同理。（差值在dg,db以内）
     * @param t the tile to copy//要复制的title
     * @param dr the maximum difference in red value//红色分量的最大变化量
     * @param dg the maximum difference in green value//绿色分量的最大变化量
     * @param db the maximum difference in blue value//蓝色分量的最大变化量
     * @param r the random number generator to use//要使用的随机数生成器
     */
    public static TETile colorVariant(TETile t, int dr, int dg, int db, Random r) {
        Color oldColor = t.textColor;
        int newRed = newColorValue(oldColor.getRed(), dr, r);
        int newGreen = newColorValue(oldColor.getGreen(), dg, r);
        int newBlue = newColorValue(oldColor.getBlue(), db, r);

        Color c = new Color(newRed, newGreen, newBlue);

        return new TETile(t, c);
    }

    private static int newColorValue(int v, int dv, Random r) {
        int rawNewValue = v + RandomUtils.uniform(r, -dv, dv + 1);

        // make sure value doesn't fall outside of the range 0 to 255.//确保数值不会超出0~255的范围
        int newValue = Math.min(255, Math.max(0, rawNewValue));
        return newValue;
    }

    /**
     * Converts the given 2D array to a String. Handy for debugging.
     * Note that since y = 0 is actually the bottom of your world when
     * drawn using the tile rendering engine, this print method has to
     * print in what might seem like backwards order (so that the 0th
     * row gets printed last).//把给定的二维数组转换成字符串。调试时很方便。注意，使用title渲染引擎绘制时，y=0实际上是世界的底部，所以这个打印方法必须一看起来反向的顺序打印（第零行最后打印）
     * @param world the 2D world to print//要打印的二维世界
     * @return string representation of the world//世界的字符串表示
     */
    public static String toString(TETile[][] world) {
        int width = world.length;
        int height = world[0].length;
        StringBuilder sb = new StringBuilder();

        for (int y = height - 1; y >= 0; y -= 1) {
            for (int x = 0; x < width; x += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                sb.append(world[x][y].character());
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Makes a copy of the given 2D tile array.
     * @param tiles the 2D array to copy//要复制的二维数组。
     **///创建给定二维tile数组的副本。
    public static TETile[][] copyOf(TETile[][] tiles) {
        if (tiles == null) {
            return null;
        }

        TETile[][] copy = new TETile[tiles.length][];

        int i = 0;
        for (TETile[] column : tiles) {
            copy[i] = Arrays.copyOf(column, column.length);
            i += 1;
        }

        return copy;
    }

    /**
     * Checks if two tiles are equal by comparing their IDs.//通过比较两个图块的ID来判断他们是否相等。
     * @param o object to compare with//要比较的对象
     * @return boolean representing equality//表示是否相等的布尔值
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return (o instanceof TETile otherTile && otherTile.id == this.id);
    }
}

package demo;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

import java.io.File;

public class ExampleSaveLoad {
    public static void main(String[] args) {
        String filename = "name.txt";
        File file = new File(filename);

        // Step 1: Check if the file exists and read the saved name//第一步，检查文件是否存在，并读取保存的名字；
        if (file.exists()) {
            In in = new In(file);
            if (in.hasNextLine()) {
                String savedName = in.readLine();
                System.out.println("Hello, the last time you ran this program, your name was " + savedName);
            } else {
                System.out.println("I don't know your name.");
            }
        } else {
            System.out.println("I don't know your name.");
        }

        // Step 2: Prompt the user for their name//第二部：提示用户输入名字；
        System.out.print("Please enter your name: ");
        In keyboard = new In();  // Reads from standard input//从标准输入读取；
        String currentName = keyboard.readLine();

        // Step 3: Save the new name to the file//第三步：把新的名字保存到文件；
        Out out = new Out(filename);
        out.println(currentName);
        out.close();
    }
}
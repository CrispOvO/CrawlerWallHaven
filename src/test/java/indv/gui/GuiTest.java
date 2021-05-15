package indv.gui;

import javafx.scene.control.Alert;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

/**
 * @author Crisp
 * @date 2021/4/15
 */
public class GuiTest {
    static JFrame jFrame;
    public void showFrame(int width, int height) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        jFrame = new JFrame("测试");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setBounds(400, 300, width, height);

    }

    public static void main(String[] args) {

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setCurrentDirectory(new File("D://picture"));

        chooser.addActionListener(e -> {
            System.out.println(chooser.getSelectedFile().getAbsolutePath());
        });
        int i = chooser.showSaveDialog(chooser);
    }
}

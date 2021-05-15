package indv.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Crisp
 * @date 2021/4/14
 */
public class AlertPanel {
    public static void alert(String message) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame jFrame = new JFrame("提示");
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setBounds(700, 500, 300, 100);
        JLabel jLabel = new JLabel(message);
        jLabel.setFont(new Font("dialog", Font.BOLD, 20));
        jFrame.add(jLabel);
        jFrame.setVisible(true);
    }

    public static File savePath() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showSaveDialog(null);
        chooser.setCurrentDirectory(new File("D://wallPicture"));

        return chooser.getSelectedFile();
    }
}

package indv.gui;

import indv.pojo.SubmitTask;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Crisp
 * @date 2021/3/27
 */
public class HomePage {
    public static TextArea message;
    public static JProgressBar bar;
    public static File savePath;

    static {
        savePath = new File("D://wallPaperPicture");
    }

    public void showGui() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("WallHaven");
        frame.setBounds(300, 200, 1280, 720);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.getLayeredPane().add(getBackground(), new Integer(Integer.MIN_VALUE));

        addElements((JPanel)frame.getContentPane());

        frame.setVisible(true);
    }

    public void addElements(JPanel fore) {
        fore.setLayout(null);
        fore.setOpaque(false);
        Font titleFont = new Font("dialog", Font.BOLD, 80);
        Font statement = new Font("dialog", Font.BOLD, 26);
        JLabel title = new JLabel("壁纸下载");
        title.setForeground(Color.white);
        title.setBounds(80, 30, 360, 80);
        title.setFont(titleFont);
        fore.add(title);

        //添加文本框描述
        JLabel start = new JLabel("起始页:");
        JLabel end = new JLabel("结束页:");
        start.setForeground(Color.white);
        start.setBounds(570, 540, 120, 40);
        end.setForeground(Color.white);
        end.setBounds(810, 540, 120, 40);
        start.setFont(statement);
        end.setFont(statement);
        fore.add(start);
        fore.add(end);

        //添加文本框
        JTextField startPage = new JTextField();
        JTextField endPage = new JTextField();
        startPage.setFont(statement);
        endPage.setFont(statement);
        startPage.setBounds(680, 540, 100, 40);
        endPage.setBounds(910, 540, 100, 40);

        fore.add(startPage);
        fore.add(endPage);

        //添加开始下载按钮
        JButton download = new JButton("开始下载");
        download.setFont(statement);
        download.setBounds(1040, 540, 170, 100);
        fore.add(download);

        download.addActionListener(e -> {

            Thread startDown = new Thread(new Runnable() {

                @Override
                public void run() {
                    AlertPanel.alert("开始下载");
                    String from = startPage.getText();
                    String to = endPage.getText();
                    SubmitTask submitTask = new SubmitTask();
                    try {
                        submitTask.submitTask(Integer.parseInt(from), Integer.parseInt(to));
                    } catch (Exception exception) {
                        message.append(exception.toString() + "\n");
                        AlertPanel.alert("网络连接错误，重新下载试试");
                    }
                }
            });
            startDown.start();
        });

        //添加文件存放位置框
        addPosition(fore);

        //添加信息打印框
        addMessage(fore);

        //进度条
        addProgressBar(fore);

    }

    public void addPosition(JPanel panel) {
        JTextField path = new JTextField();
        path.setBounds(160, 545, 350, 30);
        path.setFont(new Font("dialog", Font.PLAIN, 20));
        path.setText(savePath.getAbsolutePath());
        panel.add(path);


        JButton selectPath = new JButton("保存到:");
        selectPath.setFont(new Font("dialog", Font.BOLD, 23));
        selectPath.setBounds(20, 545, 120, 30);
        panel.add(selectPath);

        selectPath.addActionListener(e -> {
            savePath = AlertPanel.savePath();
            path.setText(savePath.getAbsolutePath());
        });
    }

    public void addProgressBar(JPanel panel) {
        JLabel label = new JLabel("下载进度:");
        label.setBounds(20, 600, 130, 30);
        label.setFont(new Font("dialog", Font.BOLD, 26));
        label.setForeground(Color.white);


        bar = new JProgressBar(0, 100);
        bar.setBorderPainted(true);
        bar.setBounds(160, 600, 850, 35);
        bar.setForeground(new Color(236, 70, 70));

        panel.add(bar);
        panel.add(label);
    }

    public void addMessage(JPanel panel) {
        Font statement = new Font("dialog", Font.PLAIN, 14);
        message = new TextArea();
        message.setFont(statement);
        message.setBounds(580, 90, 670, 330);
        panel.add(message);
    }

    public JLabel getBackground() {
        ImageIcon background = new ImageIcon("image/background.jpg");
        JLabel jLabel = new JLabel(background);
        jLabel.setBounds(0, 0, 1280, 720);
        return jLabel;
    }

}

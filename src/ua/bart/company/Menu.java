package ua.bart.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by B'Art on 16.01.2017.
 */
public class Menu extends JFrame{

    JPanel menu = new JPanel();
    JLabel background = new JLabel(new ImageIcon("res/menu/backgm.png"));
    JLabel icon = new JLabel(new ImageIcon("res/menu/icon.png"));
    JLabel name = new JLabel();
    JLabel coins = new JLabel(new ImageIcon("res/surprise/yellowBall.png"));
    JLabel iconPlayer;

    JButton B_new_game = new JButton(new ImageIcon("res/menu/newgame.png"));
    JButton B_continue = new JButton(new ImageIcon("res/menu/continue.png"));
    JButton B_options = new JButton(new ImageIcon("res/menu/options.png"));
    JButton B_exit = new JButton(new ImageIcon("res/menu/exit.png"));
    JButton B_changeIcon = new JButton(new ImageIcon("res/menu/changeicon.jpg"));

    BufferedReader bufferedReader;
    PrintWriter printWriter;

    File playerInfo = new File("C:/Users/B'Art/Desktop/MNG_resource/player.txt");

    JFileChooser jFileChooser = new JFileChooser(new File("C:/Users/B'Art/Desktop/MNG_resource"));

    String pathIconPlayer;
    String namePlayer;

    private File F_iconPlayer;

    private int distance;


    public Menu() {
        setSize(1366,768);
        setResizable(false);
        setUndecorated(true);
        setFocusable(false);
        readInFile();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        add(menu);
        menu.add(background);

        //Check if Icon Player exist
        if(pathIconPlayer!=null){
           iconPlayer = new JLabel(new ImageIcon(pathIconPlayer));
           B_changeIcon.setVisible(false);
        } else
            iconPlayer = new JLabel();

        name.setFont(new Font("Arial",Font.ITALIC,50));
        name.setForeground(Color.cyan);
        name.setBounds(150,90,300,70);

        coins.setFont(new Font("Arial",Font.ITALIC,50));
        coins.setForeground(Color.cyan);
        coins.setBounds(120,150,200,100);

        iconPlayer.setBounds(150,250,500,500);
        icon.setBounds(80,40,100,200);
        B_new_game.setBounds(960,360,380,100);
        B_continue.setBounds(960,480,380,100);
        B_options.setBounds(960,600,380,100);
        B_exit.setBounds(973,15,380,100);
        B_changeIcon.setBounds(180,360,128,128);




        //добавляем кнопки на панель
        setObject();
        //привязываем к кнопкам действия
        setButtonActions();

        setVisible(true);
    }


    private void setObject(){
        background.add(B_new_game);
        background.add(B_continue);
        background.add(B_options);
        background.add(B_exit);
        background.add(name);
        background.add(coins);
        background.add(B_changeIcon);
        background.add(iconPlayer);
    }

    ///////////////////////////
    //Setting Button Actions//
    /////////////////////////
    private void setButtonActions(){
        B_new_game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                repaint();
                new Level_x();

            }
        });

        B_options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OptionsFrame();
                repaint();
                readInFile();
            }
        });

        B_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null,"Вы уверены, что хотите выйти?");
                if(i==JOptionPane.OK_OPTION)
                    System.exit(12);
            }
        });

        B_changeIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = jFileChooser.showOpenDialog(null);
                if(i==JFileChooser.APPROVE_OPTION) {
                    F_iconPlayer = jFileChooser.getSelectedFile();
                    pathIconPlayer = String.valueOf(jFileChooser.getSelectedFile().getAbsoluteFile());
                    iconPlayer.setIcon(new ImageIcon(pathIconPlayer));
                    iconPlayer.updateUI();
                    B_changeIcon.setVisible(false);
                    writeInFile();
                }
            }
        });
    }


    void readInFile(){
        try {
            if(playerInfo.exists()){
                 bufferedReader = new BufferedReader(new FileReader(playerInfo));
                namePlayer = bufferedReader.readLine();
                distance = Integer.parseInt(bufferedReader.readLine());
                coins.setText(bufferedReader.readLine());
                if(coins.getText()==null){
                    coins.setText("0");
                }
                pathIconPlayer = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!playerInfo.exists()) {
            namePlayer = "Unnamed";
            distance = 0;
            coins.setText("0");
            pathIconPlayer = null;
        }
        name.setText("Hi, "+namePlayer);
    }

    void writeInFile(){
        try {
            printWriter = new PrintWriter(playerInfo);
                printWriter.println(namePlayer);
                printWriter.println(distance);
                printWriter.println(coins.getText());
                printWriter.print(pathIconPlayer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            printWriter.close();
        }
    }
}
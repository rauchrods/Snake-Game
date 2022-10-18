package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private int [] snakexlength = new int [750];
    private int [] snakeylength = new int [750];

    private  int [] xpos={25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825,850};

    private  int [] ypos={75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};

    private Random random = new Random();
    private int foodx,foody;
    private  int lengthofsnake = 3;
    private boolean left=false;
    private boolean right=true;
    private boolean up=false;
    private boolean down=false;

    private  boolean gameover=false;
    int moves=0;

    int score=3;
    private ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    private ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
    private ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));
    private ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));
    private ImageIcon snakeimage= new ImageIcon(getClass().getResource("snakeimage.png"));
    private ImageIcon enemy= new ImageIcon(getClass().getResource("enemy.png"));

    private Timer timer;
     private int delay=100;
    GamePanel(){
        addKeyListener(this);
        setFocusable(true);
//        setFocusTraversalKeys(true);
      timer = new Timer(delay,this);
      timer.start();
      newFood();
    }

    private void newFood() {
        foodx=xpos[random.nextInt(34)];
        foody=ypos[random.nextInt(23)];

        for(int i=lengthofsnake-1; i>=0; i--){
            if(snakexlength[i]==foodx && snakeylength[i]==foody){
                newFood();
            }
        }
    }

    private void setFocusTraversalKeys(boolean b) {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.white);
        g.drawRect(24,10,852,55);
        g.drawRect(24,74,852,576);
        snaketitle.paintIcon(this,g,25,11);
        g.setColor(Color.black);
        g.fillRect(25,75,851,575);
        if(moves==0){
           snakexlength[0]=100;
            snakexlength[1]=75;
            snakexlength[2]=50;

            snakeylength[0]=100;
            snakeylength[1]=100;
            snakeylength[2]=100;

        }
        if(left){
            leftmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }
        if(right){
            rightmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }
        if(up){
            upmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }
        if(down){
            downmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
        }

        for(int i=1;i<lengthofsnake;i++){
            snakeimage.paintIcon(this,g,snakexlength[i],snakeylength[i]);
        }
        enemy.paintIcon(this,g,foodx,foody);

        if(gameover){
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,40));
            g.drawString("Game Over",340,325);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
            g.drawString("Press Space to Restart the Game",210,500);
        }
        g.setColor(Color.white);
        g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
        g.drawString("Current Score: " + score,730,30);
        g.drawString("Length: " + lengthofsnake,730,55);
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=lengthofsnake-1;i>0;i--){
            snakexlength[i]=snakexlength[i-1];
            snakeylength[i]=snakeylength[i-1];
        }
         if(left){
             snakexlength[0]=snakexlength[0]-25;
         }
         if(right){
             snakexlength[0]=snakexlength[0]+25;
         }
        if(up){
            snakeylength[0]=snakeylength[0]-25;
        }
        if(down){
            snakeylength[0]=snakeylength[0]+25;
        }
        if(snakexlength[0]>850){
            snakexlength[0]=25;
        }
        if(snakexlength[0]<25){
            snakexlength[0]=850;
        }
        if(snakeylength[0]>625){
            snakeylength[0]=75;
        }
        if(snakeylength[0]<75){
            snakeylength[0]=625;
        }
        collidefood();
        collidewithitself();
        repaint();
    }

    private void collidewithitself() {
        for(int i=lengthofsnake-1;i>0;i--){
            if(snakexlength[i]==snakexlength[0] && snakeylength[i]==snakeylength[0]){
                timer.stop();
                gameover=true;
            }
        }
    }


    private void collidefood() {
        if(snakexlength[0]==foodx && snakeylength[0]==foody){
            newFood();
            lengthofsnake++;
            snakexlength[lengthofsnake-1]=snakexlength[lengthofsnake-2];
            snakeylength[lengthofsnake-1]=snakeylength[lengthofsnake-2];
            delay--;
            score++;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE && gameover){
            restart();
        }

      if((e.getKeyCode()==KeyEvent.VK_A  || e.getKeyCode()==KeyEvent.VK_LEFT) && (!right)){
            left=true;
            right=false;
            up=false;
            down=false;
          moves++;
      }
        if((e.getKeyCode()==KeyEvent.VK_D || e.getKeyCode()==KeyEvent.VK_RIGHT) && (!left)){
            left=false;
            right=true;
            up=false;
            down=false;
            moves++;
        }
        if((e.getKeyCode()==KeyEvent.VK_W || e.getKeyCode()==KeyEvent.VK_UP) && (!down)){
            left=false;
            right=false;
            up=true;
            down=false;
            moves++;
        }
        if((e.getKeyCode()==KeyEvent.VK_S || e.getKeyCode()==KeyEvent.VK_DOWN) && (!up)){
            left=false;
            right=false;
            up=false;
            down=true;
            moves++;
        }

    }

    private void restart() {
        gameover=false;
        moves=0;
        score=3;
        left=false;
        right=true;
        up=false;
        down=false;
        lengthofsnake=3;
        timer.start();
        repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

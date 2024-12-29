import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver){
            gameLoop.stop();
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
      if(e.getKeyCode()== KeyEvent.VK_UP && moveY !=1){
          moveX = 0;
          moveY = -1;
      }
      else if (e.getKeyCode()== KeyEvent.VK_DOWN && moveY != -1) {

          moveX = 0;
          moveY = 1;
      } else if (e.getKeyCode() == KeyEvent.VK_LEFT && moveX != 1) {
          moveX = -1;
          moveY = 0;
      } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && moveX != -1) {
          moveX = 1;
          moveY = 0;

      }
    }
//    We don't need these two methods, we just need to declare them
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    private class Tile{
        int x;
        int y;
        Tile(int x,int y){
            this.x = x;
            this.y = y;

        }
    }
    int width;
    int height;
    int tileSize = 25;
    boolean gameOver = false;

//    For snake
    Tile snakeHead;
//    For Snake Food
    Tile snakeFood;
//    Increasing the snake size by eating food/tiles
    ArrayList<Tile> snakeBody;
    //    Timer for game looping
    Timer gameLoop;
//    For Randomly picking food
    Random random;


//    For moving snake up and down to pick foodPlace
    int moveX;
    int moveY;
    SnakeGame(int width, int height){
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(this.width,this.height));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5,5);
        snakeFood = new Tile(7,7);
        random = new Random();
        gameLoop = new Timer(100,this);
        snakeBody = new ArrayList<Tile>();

        foodPlace();
        gameLoop.start();

        moveX= 0;
        moveY =1;

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);


    }
    public void draw(Graphics g){
        for (int i=0;i<width/tileSize;i++){
            g.drawLine(i*tileSize,0,i*tileSize,height);
            g.drawLine(0,i*tileSize,width,i*tileSize);


        }
//        For Food
        g.setColor(Color.red);
        g.fillRect(snakeFood.x*tileSize,snakeFood.y*tileSize,tileSize,tileSize);

//         For Snake
        g.setColor(Color.green);
        g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize,tileSize,tileSize);

//        For increasing snake size by eating food
        for(int i = 0; i< snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x*tileSize,snakePart.y*tileSize,tileSize,tileSize);
        }
        g.setFont(new Font("Arial",Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()),tileSize-16,tileSize);
        }else{
            g.drawString("Score: " + String.valueOf(snakeBody.size()),tileSize-16,tileSize);
        }

    }
    public void foodPlace(){
        snakeFood.x = random.nextInt(width/tileSize);
        snakeFood.y = random.nextInt(width/tileSize);
    }
    public void move(){



        if (snakeTileCollision(snakeHead,snakeFood)){
            snakeBody.add(new Tile(snakeFood.x,snakeFood.y));
            foodPlace();
        }

        for (int i =snakeBody.size()-1; i>=0;i--){
            Tile snakePart = snakeBody.get(i);
            if(i==0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        snakeHead.x += moveX;
        snakeHead.y += moveY;
        for(int i = 0; i< snakeBody.size();i++){
            Tile snakePart = snakeBody.get(i);
            if(snakeTileCollision(snakeHead,snakePart)){
                gameOver = true;
            }
        }
        if(snakeHead.x*tileSize<0 || snakeHead.x * tileSize > width || snakeHead.y * tileSize < 0
                || snakeHead.y * tileSize > height ){
            gameOver = true;
        }
    }
    public boolean snakeTileCollision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }


}

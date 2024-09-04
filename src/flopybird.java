import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class flopybird extends JPanel implements ActionListener,KeyListener{
    // all verable started

    //background images verable
    Image background;
    Image birdImage;
    Image pipImage1;
    Image pipImage2;

    //bird verable
    int birdX = 370/8 ;
    int birdY = 0 ;
    int birdwidth = 65;
    int birdhight = 44;

    class Bird{
        int x = birdX;
        int y = birdY;
        int width = birdwidth;
        int height = birdhight;
        Image img;
        Bird(Image img){
         this.img=img;
        }
    }

    //pip verable
    int pipX = 370;
    int pipY = 0;
    int pipwidth = 64;
    int pipheight = 512;

    class Pip{
        int x = pipX;
        int y = pipY;
        int width = pipwidth;
        int height = pipheight;
        Image img;
        boolean passed = false;

        Pip(Image img){
         this.img = img;
        }
    }

   
    //game logic
    Bird bird;
    int velocityX = -4; //move pipes to the left speed (simulatenious bird moving rright)
    int velocityY = 0;  //move bird up/down speed
    int gravity = 1;

    ArrayList<Pip> pipes;
    //place pip random
    Random rendom = new Random();
    //timer include
    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver =false;
    double score = 0;



    //constructer started


    flopybird(){

        setPreferredSize(new Dimension(370,620));
        //setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);
       

        //load img
        background = new ImageIcon(getClass().getResource("./background.jpg")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./bird2.png")).getImage();
        pipImage1 = new ImageIcon(getClass().getResource("./toppip.png")).getImage();
        pipImage2 = new ImageIcon(getClass().getResource("./bottompip.png")).getImage();

        //bird
        bird = new Bird(birdImage);
        pipes  = new ArrayList<Pip>();

        //place pip timer
        placePipesTimer = new Timer(1500,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });

       //timer start
        placePipesTimer.start();
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }


     // pip function 


    public void placePipes(){
        // randomly pip asssign
        int randompipY = (int)(pipY - pipheight/4 - Math.random()*(pipheight/2));
        //openingSpace bitwwen pip
        int openingSpace = 620/5;
        // start toppip
        Pip toppip = new Pip(pipImage1);
        toppip.y =randompipY;
        pipes.add(toppip);
        // bottom pip
        Pip bottompip = new Pip(pipImage2);
        bottompip.y = toppip.y + 620 + openingSpace;
        pipes.add(bottompip);
    }
    


    //print function make
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    


    // drow function of graphic 


    public void draw(Graphics g){
        System.out.println("draw");
        //background
        g.drawImage(background, 0, 0, 370,620,null);

        //bird
        g.drawImage(birdImage, bird.x, bird.y, bird.width, bird.height, null);

        //pip
        for(int i=0; i<pipes.size();i++){
            Pip pip = pipes.get(i);
            g.drawImage(pip.img, pip.x, pip.y, pip.width, pip.height,null);
        }

        //score
        g.setColor(Color .white);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if (gameOver) {
            g.drawString("Game Over:" +String.valueOf((int)score),10,35);
        }
        else{
            g.drawString(String.valueOf((int)score),10,35);
        }
    }


//move function


    public void move(){

    //bird velocity and gravity
      velocityY += gravity;  
      bird.y += velocityY;
      bird.y = Math.max(bird.y,0);
      
      //pip move to left side
      for(int i =0;i<pipes.size();i++){
        Pip pip = pipes.get(i);
        pip.x += velocityX;

        // score
        if (!pip.passed && bird.x > pip.x + pip.width) {
            pip.passed = true;
            score += 0.5; //0.5 because there are 2 pipes! so 0.5*2 = 1, 1 each set of pipes
        }

        //collision for game over ending pips
        if (collision(bird, pip)) {
            gameOver = true;
        }
      }
      // hame over bird down
      if (bird.y>620) {
        gameOver = true;
      }
    }




    // collision part that means  pip shift randamly set positin
    public boolean collision(Bird a,Pip b){
     return a.x < b.x + b.width &&  //a's top left corner dosen't reach b's top right corner
            a.x + a.width > b.x &&  //a's top right corner passes b's top left corner
            a.y < b.y + b.height && //a's top left corner dosen't reach b's bottom left corner
            a.y + a.height > b.y;   //a's bottom left corner passes b's top left corner
    }
    
    // action of game move bird and pip left
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }
   // action of key listner
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            velocityY = -9;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            velocityY = -9;
            if (gameOver) {
                //restart the game
                bird.y = birdY;
                velocityY = -9;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
   
}

package org.example;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

// In class GamePanel we set the screen resolution,
// key listener
// methods for starting the game
// graphics drawing
// counter and check collisions.
// and lots of variables (potato counter, delay, starting body types)

public class GamePanel extends JPanel implements ActionListener {

    //Setting the screen width and height.
    static final int SCREEN_WIDTH = 1300;
    static final int SCREEN_HEIGHT = 750;

    //Setting what size want for objects in game.
    static final int UNIT_SIZE = 50;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);

    //Setting delay for timer.
    static final int DELAY = 125;

    //These arrays holds bodyparts of the snake in game.
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    //Snake begins the game with 5 bodyparts
    int bodyParts = 5;

    //Integers for potato counting and spawning.
    int potatoEaten;
    int potatoX;
    int potatoY;

    //Starting move direction of snake.
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    //This method is setting screen width and height, color of background, adding key listener to it, and calling the method for starting the game.
    GamePanel() {
        random = new Random();

        //Setting the screen width and height.
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));

        //Setting the color of background
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        //Adding key listener.
        this.addKeyListener(new MyKeyAdapter());

        //Calling method for starting the game.
        startGame();
    }

    //Method for starting the game.
    public void startGame() {

    //Calling method for spawning potato
        newPotato();

    //Snake starts moving
        running=true;

    //Dictate how game is fast running and putting there action listener.
        timer = new Timer(DELAY, this);
        timer.start();

    }

    //This method coordinates where the new potato will spawn, setting the spawn dimensions in randomizer.
    public void newPotato(){
        potatoX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        potatoY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;

    }
    //Method that draws graphics components
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }
    //This method is drawing graphics components as potato to SnakeGame
    public void draw(Graphics g) {
        if (running) {

            //setting the color of "potato"
            g.setColor(Color.YELLOW);

            //setting the potato to be circle and how big potato will be.
            g.fillOval(potatoX, potatoY, UNIT_SIZE, UNIT_SIZE);

            //Forloop that the program know where is head (first block) and the other blocks is the body. This is needed for changing color or changing shape.
            for (int i = 0; i < bodyParts; i++) {

                //Changes for head
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }

                //Changes for body parts
                else {
                    g.setColor(new Color(45, 180, 0));
                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            //Score counter
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + potatoEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + potatoEaten)) / 2, g.getFont().getSize());

            //If not running, it calls game over method.
        } else {
            gameOver(g);
        }
    }


    //This method sets movement of snakes bodyparts
    public void move() {

        //This forloop shifts the bodyparts in array[x], [y]
        for(int i = bodyParts;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        //Setting switch for directions of snake movement. U = UP, D = DOWN, L = LEFT, R = RIGHT.
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    //Method that changing when snake eats potato, he grows by 1 body part, potato eaten score is growing by 1 and using new potato method to spawn new potato.
    public void checkPotato() {

        //Parameters if snake is in potato dimension
        if((x[0] == potatoX) && (y[0] == potatoY)) {
            bodyParts++;
            potatoEaten++;
            newPotato();
        }
    }

    //Method for checking if snake is not colliding with his head to body, or any border.
    public void checkCollisions() {

        //checks if head collides with body
        for(int i = bodyParts;i>0;i--) {
            if((x[0] == x[i])&& (y[0] == y[i])) {
                running = false;
            }
        }

        //check if head touches left border
        if(x[0] < 0) {
            running = false;
        }

        //check if head touches right border
        if(x[0] > 1250) {
            running = false;
        }

        //check if head touches top border
        if(y[0] < 0) {
            running = false;
        }

        //check if head touches bottom border
        if(y[0] > 700) {
            running = false;
        }
        //if not running, timer stops
        if(!running) {
            timer.stop();
        }
    }

    //Method that sets what happens when game over (snakes collides)
    public void gameOver(Graphics g) {

        //Score
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+potatoEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+potatoEaten))/2, g.getFont().getSize());

        //Game Over text
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }

    //Method that is using other methods if running.
    public void actionPerformed(ActionEvent e) {

        if(running) {
            move();
            checkPotato();
            checkCollisions();
        }
        repaint();
    }
    //Class MyKeyAdapter is for moving the snakes other directions with keylistener. (LEFT, RIGHT, UP, DOWN)
    public class MyKeyAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }

        }
    }
}

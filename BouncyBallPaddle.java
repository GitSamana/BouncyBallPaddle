/*
 * This class opens a window on the screen that has a an orange ball. The ball
 * moves around the screen and bounces off of the side and top edges of the 
 * screen. There is a gray paddle at the bottom of the screen that moves with 
 * the user's mouse pointer. The ball will bounce back if it hits the paddle.
 * It falls out of the screen if it hits the bottom edge.
 * 
 * The constructor of this class adds a MouseActionListener that listens for
 * mouse events. Everytime the mouse is moved, the paddle is redrawn according
 * to the coordinates of the pointer.
 * The drawFrame method draws the ball and the paddle. The ball is redrawn 
 * with  new xy coordinate that is changed by a vlur of xChange and yChange 
 * depending on the CLOCK_SPEED
 * 
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class BouncyBallPaddle extends JPanel implements ActionListener {
  
  // Window constants
  public static final int APP_WIDTH = 600;
  public static final int APP_HEIGHT = 450;
  public static final int WINDOW_X = 100;
  public static final int WINDOW_Y = 50;
  public static final String WINDOW_TITLE = "Samana's Bouncy Ball Game";
  public static final Color BACK_GRND_CLR = Color.WHITE;
  
  // Ball constants
  public static final int BALL_DIAM = 50;
  public static final Color BALL_COLOR = Color.ORANGE;
  
  // Paddle constants
  public static final int PADDLE_WIDTH = 100, PADDLE_HEIGHT = 16;
  public static final int PADDLE_Y = APP_HEIGHT - 50;
  public static final Color PADDLE_COLOR = Color.GRAY;
  
  // Timer constants
  public static final int CLOCK_SPEED = 6;
  
  
  
  /////////////////////////////////////////////
  // instance variables for the Ball
  int xpos = 100, ypos = 100;
  
  // Use these variables in your solution to parts 1 and 2 of lab11
  private int xChange = 2, yChange = 2;
  
  //////////////////////////////////////////////
  // instance variables for the Paddle
  private int paddleX = (APP_WIDTH / 2) - 50;
  
  


  /////////////////////////////////////////////
  // instance methods
  /**
   * This method is called from the paintComponent method. It draws a red circle
   * on a white background.
   */
  public void drawFrame(Graphics g, int width, int height) {
    
    //x, y coordinates of center of ball
    int ballXC = xpos + (BALL_DIAM / 2);
    int ballYC = ypos + (BALL_DIAM / 2);
    
    //x, y coordinates of center of paddle
    int paddleXC = paddleX + (PADDLE_WIDTH / 2);
    int paddleYC = PADDLE_Y + (PADDLE_HEIGHT / 2);
    
    //setting color for the ball
    g.setColor(BALL_COLOR); 
    
    //changing the x and y coordinates of the ball by xChange, yChange
    xpos += xChange;
    ypos += yChange;
    
    //drawing ball
    g.fillOval(xpos, ypos, BALL_DIAM, BALL_DIAM); 
    
    //if y coordinate of ball goes above the screen 
    if (ypos < 0){
      //change the y direction of the ball
      yChange = -yChange;
    } 

    //if x coordinate of ball goes beyond either of the side walls of screen
    if ((xpos > getWidth() - BALL_DIAM) || (xpos < 0)){
      //change x direction of ball
      xChange = -xChange;
    }
    
    //if the distance between the ball and paddle centers in the x direction 
    //is less than or equal to half the paddle width plus half the ball width,
    //the distance between the ball and paddle centers in the y direction is 
    //less than or equal to half the paddle height plus half the ball height, 
    //andthe ball is moving downward
    if (((Math.abs(ballXC - paddleXC)) <= (PADDLE_WIDTH/2 + BALL_DIAM/2)) && 
        ((Math.abs(ballYC - paddleYC)) <= (PADDLE_HEIGHT/2 + BALL_DIAM/2)) &&
        (yChange > 0)){
      //change y direction of ball
      yChange = -yChange;
    }
    
    //setting color for the paddle
    g.setColor(PADDLE_COLOR); 
    
    //drawing paddle
    g.fillRect(paddleX, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT); 
  }
  
  
  /**
   * This method is called every time an ActionEvent is fired, in this case
   * every time the clock ticks
   */
  public void actionPerformed(ActionEvent evt) {
    // The call to repaint calls paintComponent of the JPanel
    repaint();
  } // end actionPerformed
  
  
  /**
   * This method is part of every JPanel class. It is called when the 
   * program starts and every time repaint() is called in the program.
   */
  protected void paintComponent(Graphics g) {
    // call paintComponent in parent class, sending in the Graphics object
    super.paintComponent(g);
    // call drawing method, passing in the Graphics object g and 2 ints
    drawFrame(g, this.getWidth(), this.getHeight());
  }// end paintComponent
  
  
  /*
   * Execution starts here. The main method sets up the JFrame and adds
   * this JPanel to the JFrame. Also declares, instantiates and starts a 
   * Timer whose ticks will drive the animation.
   */
  public static void main(String[] args) {
    
    //creating a new JFrame called window
    JFrame window = new JFrame(WINDOW_TITLE);
    
    // drawingArea is a JPanel:
    BouncyBallPaddle drawingArea = new BouncyBallPaddle();
    drawingArea.setBackground(BACK_GRND_CLR);
    
    // These statements set up JFrame
    window.setContentPane(drawingArea);
    window.pack();
    window.setLocation(WINDOW_X,WINDOW_Y);
    window.setSize(APP_WIDTH,APP_HEIGHT);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setVisible(true);

    
    // A Timer object will fire an ActionEvent every CLOCK_SPEED
    // milliseconds. This ActionEvent is caught by the actionPerformed
    // method in this JPanel.
    Timer frameTimer = new Timer(CLOCK_SPEED, drawingArea);
    
    // Timer must be started, after which a new ActionEvent will be
    // generated each time clock is advanced.
    frameTimer.start();
  } // end main
  
  
  /**
   * Constructor is not initially used, but will be used when you add
   * a MouseMotionListener to the drawingArea.
   */
  // ball.getX();
  //ball.x;
  public BouncyBallPaddle() {
    //adding a new MouseMotionListener
    addMouseMotionListener(new MouseMotionListener(){
      //that listens for mouse movement event
       
      //methods of MouseMotionListener
      public void mouseMoved(MouseEvent m){
        //the mouse Moved method calls movePaddle method sending in 
        //the x coordinate of mouse pointer
        movePaddle(m.getX());
      }
      public void mouseDragged(MouseEvent m){
      };
    });
  }
  
  //outOfBounds method
  public boolean outOfBounds(int x) {
    //if x coordinate of paddle is at either edge of the wall
    if (x <= 0 || (x + PADDLE_WIDTH) > APP_WIDTH) {
      //return true
      return true;
    } else {
      //else false
      return false;
    }}
  
  //movePaddle method
  public void movePaddle(int x){
    // if outOdBounds is false
    if (!outOfBounds(x)){
      //change x coordinate of paddle to parameter x
      paddleX = x;
      //repaint the paddle
      repaint();
    }
  }
 
} // end class

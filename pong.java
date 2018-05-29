package pong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;


public class pong implements ActionListener , KeyListener {
	
public static pong Pong ;	
public int width= 700,height = 700 ;
public renderer Renderer;

public Paddle player1 ; 
public Paddle player2 ; 
public ball Ball ; 

public int gameStatus = 0, botmoves ,botDifficulty , botcooldown , scoreLimit = 10 , playerWon ;

public boolean bot = false , selectingDifficulty;

public boolean w, s , up , down ;

public Random random;

  public  pong () {
	  
	  Timer timer = new Timer(20 , this);
	  random = new Random();
	  JFrame jframe = new JFrame ("Pong");
	  Renderer = new renderer ();
	  
	  jframe.setSize(width + 7, height + 17);
	  jframe.setResizable(false);
	  jframe.setVisible(true);
	  jframe.add(Renderer);
	  jframe.addKeyListener(this);
	  jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  
	    
	  //start ();	
	 // timer.start();
}
  
  public void  start () {
	  gameStatus = 2 ;
	  player1 = new Paddle (this ,1);
	  player2 = new Paddle (this ,2);
	  
	  Ball = new ball (this);
  }
  
  public void update () {
	  if (player1.score >= scoreLimit)
	  {
		  gameStatus = 3 ;
		  playerWon = 1 ;
	  }
	  if (player2.score >= scoreLimit)
	  {
		  gameStatus = 3 ;
		  playerWon = 2 ;
	  }
	  
	  if (w)
	  {
		  player1.move(true);
	  }
	  if (s)
	  {
		  player1.move(false);
	  }
	  if (!bot)
	  {
		    if (up)
		    {
		    	player2.move(true);
		    }
		    if (down)
		    {
		    	player2.move(false);
		    }
	  }
	  else
	  {
	
		  if (player2.y + player2.height / 2 < Ball.y)
		  {
			  player2.move(false);;
		  }
		  
		  if (player2.y + player2.height / 2 > Ball.y)
		  {
			  player2.move(true);;
			  
		  }
		  if (random.nextInt(50) < 10)
		  {
			  player2.move (true);
		  }
		  if (random.nextInt(50) > 40)
		  {
			  player2.move (false);
		  }
		  
		  if (botDifficulty == 0)
		  {
			  botcooldown = 20 ;
		  }
		  if (botDifficulty == 1)
		  {
			  botcooldown = 15 ;
		  }
		  if (botDifficulty == 2)
		  { 
			  botcooldown = 10 ;
		  }
	  }
	 Ball.update(player1, player2);
	 
	  
  }

  public void render(Graphics2D g)
  {
	  g.setColor(Color.BLACK);
	  g.fillRect(0, 0, width, height);
	  g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	  
	  if (gameStatus == 0 )
	  {
		 g.setColor(Color.white);
		 g.setFont(new Font ("Arial" , 1 , 50));
		 
		 g.drawString("PONG", width / 2 - 70, 50); 
		 
		 if (!selectingDifficulty)
		 {
			 g.setFont(new Font ("Arial" , 1 , 30));
		     g.setStroke(new BasicStroke(3f));
		     g.drawString("<< ScoreLimit:" + scoreLimit+ " >> ", width / 2 - 150,height/2 + 70);
		     g.drawString("Press Space to Play", width / 2 - 150, height / 2 - 25);
		     g.drawString("Press Shift to play with Bot", width / 2 - 210,height/2 + 20);
		 }
	  
	  if (selectingDifficulty)
	  {
		  String string = botDifficulty == 0 ? "Easy" : (botDifficulty == 1 ? "Medium" : "Hard") ;
		  
		  g.setFont(new Font ("Arial" , 1 , 30));
		  
		  
		  g.drawString("<< Computer Difficulty >>: " + string, width / 2 - 250, height / 2 + 25);
			
		  g.drawString("Press Space to Play", width / 2 - 150, height / 2 + 60);
		  g.drawString("Press ESC for menu", width / 2 - 150, height / 2 - 15);
	  }
	  }
	  
	  

	  
	  if (gameStatus == 1)
	  {
		  g.setColor(Color.white);
		  g.setFont(new Font ("Arial" , 1 , 50));
		  g.drawString("PAUSED", width / 2 - 103,height /2 - 155);
	  }
	  
	  if (gameStatus == 1 || gameStatus == 2)
	  {
	  g.setColor(Color.WHITE);
	  g.setStroke(new BasicStroke(5f));
	  g.drawLine(width / 2,0,width / 2,height);
	  
	  g.setStroke(new BasicStroke(2f));
	  g.drawOval(width / 2 - 150,height / 2 - 150, 300, 300);
	  
	  g.setFont(new Font ("Arial" , 1 , 50));
	  g.drawString(String.valueOf(player1.score), width / 2 - 70, 50);
	  g.drawString(String.valueOf(player2.score), width / 2 + 37, 50);
	  
	  player1.render (g);
	  player2.render (g);
	  Ball.render(g);
	  }
	  if (gameStatus == 3 )
	  {
		 g.setColor(Color.white);
		 g.setFont(new Font ("Arial" , 1 , 50));
		 
		 g.drawString("PONG", width / 2 - 70, 50); 
		 
		 g.setFont(new Font ("Arial" , 1 , 30));
		 
		 if (bot)
		 {
			 g.drawString("!! Computer Wins !!", width / 2 - 150, 410);
			 
		 }
		 else
		 {
			 g.drawString("!! Player "+ playerWon + " Wins !!", width / 2 - 70, 410); 
		 }
		 
		 if (!selectingDifficulty)
		 {
			 g.setFont(new Font ("Arial" , 1 , 30));
		     g.setStroke(new BasicStroke(3f));
		     g.drawString("Press Space to Play again", width / 2 - 150, height / 2 - 25);
		     g.drawString("Press ESC for menu", width / 2 - 110,height/2 + 20);
		 }
	  
	  if (selectingDifficulty)
	  {
		  String string = botDifficulty == 0 ? "Easy" : (botDifficulty == 1 ? "Medium" : "Hard") ;
		  
		  g.setFont(new Font ("Arial" , 1 , 30));
		  
		  
		  g.drawString("<< Computer Difficulty >>: " + string, width / 2 - 200, height / 2 - 25);
			
		  g.drawString("Press Space to Play", width / 2 - 150, height / 2 + 25);
	  }

     }
  }

  public static void main (String []args) {
	
	Pong = new pong () ;
	
	
	
	
	
}

@Override
public void actionPerformed(ActionEvent e)
{
	if (gameStatus == 2) {
		
		update ();
	}
	
	Renderer.repaint ();
	
}

@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	
	int id = e.getKeyCode ();
	
	if (id == KeyEvent.VK_W)
	{
		w = true;
	}
	
	else if (id == KeyEvent.VK_S)
	{
		s = true;
	}
	else if (id == KeyEvent.VK_UP)
	{
		up = true;
	}
	else if (id == KeyEvent.VK_DOWN)
	{
	    down = true;
	}
	else if (id == KeyEvent.VK_RIGHT)
	{
		if (selectingDifficulty) 
		{	
			if (botDifficulty < 2 )
			{
	    	botDifficulty++ ;
	        }
			else 
			{
				botDifficulty = 0 ;
			}
	     }
		else if (gameStatus == 0)
		{
			scoreLimit++ ;
		}
	}
	else if (id == KeyEvent.VK_LEFT)
	{
		if(selectingDifficulty)
		{
			if (botDifficulty  > 0 )
			{
				botDifficulty-- ;
			}
			else
			{
				botDifficulty = 2 ;
			}
		}
		else if (gameStatus == 0 && scoreLimit > 0) 
		{
			scoreLimit-- ;
		}
	}
	
	else if (id == KeyEvent.VK_ESCAPE &&( gameStatus == 2 || gameStatus == 3))
	{
	    gameStatus = 0;
	}
	else if (id == KeyEvent.VK_SHIFT && gameStatus == 0)
	{
		bot = true;
		selectingDifficulty = true; 
	
		
	}
	
	
	else if (id == KeyEvent.VK_SPACE )
	{
		if (gameStatus == 0 || gameStatus == 3)
		{
			if (!selectingDifficulty)
			{
				bot = false ;
			}
			start () ;
			
		}
		else if (gameStatus == 1)
		{
			     gameStatus = 2 ;
		}
		else if (gameStatus == 2 )
		{
			     gameStatus = 1 ;
		}
	}
}
@Override
public void keyReleased(KeyEvent e)
{

     int id = e.getKeyCode ();
	
	if (id == KeyEvent.VK_W)
	{
		w = false;
	}
	
	else if (id == KeyEvent.VK_S) {
		s = false;
	}
	else if (id == KeyEvent.VK_UP)
	{
		up = false;
	}
	else if (id == KeyEvent.VK_DOWN)
	{
	    down = false;
	}
	
	
}

@Override
public void keyTyped(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}

}
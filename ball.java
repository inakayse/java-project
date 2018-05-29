package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class ball
{
	public int x, y, height = 25, width = 25;
	
	public int motionX, motionY ;
	
	public Random random ;
	
	public int amountofHits;
	
	private pong Pong ;

	
	public ball(pong Pong)
	{
		this.Pong = Pong ;
		
		this.random = new Random ();
		
		spawn();
		
	}
	
	public void update (Paddle paddle1, Paddle paddle2) 
	{
		int speed = 5 ;
		
		this.x += motionX * speed;
		this.y  += motionY * speed;
		
		if (this.y + height - motionY > Pong.height || this.y  + motionY < 0)
		{
			if (this.motionY < 0)
			{
				this.y = 0 ;
				this.motionY = random.nextInt (4);
				
				if (motionY == 0)
				{
					motionY = 1 ;
				}

			}
			else
			{
				this.motionY = -random.nextInt(4);
				this.y = Pong.height - height ;
				
				if (motionY == 0)
				{
					motionY = -1 ;
				}
 
			}
		}
		/*if (this.x + width > Pong.width || this.x < 0) 
		{
		 	
		}
		*/
		if (checkCollision(paddle1) == 1 )
		{
			this.motionX = 1 + (amountofHits / 5);
			this.motionY = -2 + random.nextInt(4);
			
			
			if (motionY == 0)
			{
  				motionY = 1 ;
			}
			 
			amountofHits++ ;
			
		}
		else if (checkCollision(paddle2) == 1 )
		{
			this.motionX = -1 - (amountofHits / 5);
			this.motionY = -2 + random.nextInt(4);
			
			if (motionY == 0)
			{
				motionY = 1 ;
			}
			
			amountofHits++ ;	
		}
		
		if (checkCollision(paddle1) == 2 ) 
		{
			paddle2.score++;
			spawn();
		}
		
		else if (checkCollision(paddle2) == 2 ) 
		{
			paddle1.score++;
			spawn();
		}
	}
	
	public void spawn () 
	{

		this.amountofHits = 0 ;
		this.x = Pong.width / 2 - this.width / 2 ;
		this.y = Pong.height / 2 - this.height / 2 ;
		this.motionY = -2 + random.nextInt(4); 
		//this.motionX = -1 + random.nextInt(1);
		
		if (motionY == 0)
		{
			motionY = 1 ;
		}
		if (random.nextBoolean())
		{
			motionX = -1 ; 
		}
		else
		{
			motionX = 1 ; 
		}
		
	}

	public int checkCollision(Paddle paddle)
	{
		
		if (this.x < paddle.x + paddle.width && this.x + width > paddle.x && this.y <paddle.y + paddle.height && this.y + height > paddle.y)
		{
			return 1 ;
		}
		else if ((paddle.x > x + width && paddle.paddleNumber == 1) || (paddle.x < x && paddle.paddleNumber == 2))
		{
	        return 2 ;
		}
		
		return 0 ;
	}
	
	public void render (Graphics g ) 
	{
		g.setColor(Color.white);
		g.fillOval(x, y, width, height);
		
		
		
	}
	

}

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

public class GameBreakOut extends JFrame {
	public static void main(String[] args) {
		new GameBreakOut();
	}
	// Start Variable
	int w = 700;
	int h = 500;
	Graphics g;
	Ball b;
	BufferedImage bufImg;
	// End Variable
	
	
	public GameBreakOut() {
		this.setTitle("Game Break Out");
		this.setSize(w,h);
		this.setDefaultCloseOperation(3);
		
		Random random = new Random();
		b = new Ball (350,450,13,random.nextDouble()*5-2,1.5,this);
		b.start();
		
		bufImg = new BufferedImage(w,h, BufferedImage.TYPE_3BYTE_BGR);
		g = bufImg.getGraphics();
		this.setVisible(true);
		// Add cac su kien
		
		//
	}
	
	public void paint(Graphics g1) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// Set graphics cho ball
		
		g.setColor(Color.blue);
		g.fillOval((int)(b.x-b.r), (int)(b.y-b.r), (int)(b.r*2), (int)(b.r*2));
		g.drawOval((int)(b.x-b.r), (int)(b.y-b.r), (int)(b.r*2), (int)(b.r*2));
		
		
		g1.drawImage(bufImg, 0, 0, this.getWidth(), this.getHeight(), null);
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO: lam gi do
		}
		
		repaint();
	}
}

class Ball extends Thread {
	double x, y, r, vx, vy;
	GameBreakOut gb;
	public Ball(double x, double y, double r, double vx, double vy, GameBreakOut gb) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.gb = gb;
		this.vx = vx;
		this.vy = vy;
	}
	
	public void run() {
		while(true) {
			x = x +vx;
			y = y + vy;
			if(x - r <= 0 || x + r >= gb.w) {
				vx = -vx;
			}
			if(y - r <=0 || y + r >= gb.h) {
				vy = -vy;
			}
			
			// Tang toc sau moi lan an bong;
			
			//
			
			// Delay mot time nho
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
			//
		}
	}
}

class Panel extends Thread {
	
}

// Class Vector
class MyVector {
	double x,y;
	MyVector(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public MyVector Add(MyVector v) {
		return new MyVector(this.x + v.x, this.y + v.y);
	}

	public MyVector Sub(MyVector v) {
		return new MyVector(this.x - v.x, this.y - v.y);
	}

	public double dotP(MyVector v) {
		return this.x*v.x + this.y*v.y;
	}

	public double length() {
		return Math.sqrt(x*x+y*y);
	}
	
	public MyVector Mult(double l) {
		return new MyVector(x*l, y*l);
	}
	
	public MyVector Norm() {
		return Mult(1.0/this.length());
	}
}


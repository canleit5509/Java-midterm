import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

public class GameBreakOut extends JFrame implements KeyListener {
	public static void main(String[] args) {
		new GameBreakOut();
	}

	// Start Variable
	int w = 700;
	int h = 500;
	int rec_w = 60;
	int rec_h = 30;
	int brickperrow = 9;
	int brickpercol = 5;
	int of = 10;
	Graphics g;
	Ball b;
	Brick brick[][] = new Brick[brickperrow][brickpercol];
	BufferedImage bufImg;
	// End Variable

	public GameBreakOut() {
		this.setTitle("Game Break Out");
		this.setSize(w, h);
		this.setDefaultCloseOperation(3);

		Random random = new Random();
		b = new Ball(350, 450, 13, random.nextDouble() * 5 - 2, 1.5, this);
		b.start();
		for (int i = 0; i < brickperrow; i++) {
			for (int j = 0; j < brickpercol; j++) {
				brick[i][j] = new Brick(30 + i * (rec_w + of), 50 + j * (rec_h + of), this);
			}
		}
		bufImg = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
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
		g.fillOval((int) (b.x - b.r), (int) (b.y - b.r), (int) (b.r * 2), (int) (b.r * 2));
		g.drawOval((int) (b.x - b.r), (int) (b.y - b.r), (int) (b.r * 2), (int) (b.r * 2));
		for (int i = 0; i < brickperrow; i++) {
			for (int j = 0; j < brickpercol; j++) {
				g.drawRect((int) (brick[i][j].x), (int) (brick[i][j].y), rec_w, rec_h);
			}
		}
		g1.drawImage(bufImg, 0, 0, this.getWidth(), this.getHeight(), null);

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO: lam gi do
		}

		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			// dich chuyen thanh sang trai
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			// dich chuyen thanh sang phai
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

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
		while (true) {
			x = x + vx;
			y = y + vy;
			if (x - r <= 0 || x + r >= gb.w) {
				vx = -vx;
			}
			if (y - r <= 0 || y + r >= gb.h) {
				vy = -vy;
			}

			// TODO: Tang toc sau moi lan an bong;

			// TODO: Neu cham vao bottom -> endgame --> v = 0

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

class Brick extends Thread {
	double x, y;
	GameBreakOut gb;

	public Brick(double x, double y, GameBreakOut gb) {
		this.x = x;
		this.y = y;
		this.gb = gb;
	}
}

// Class Vector
class MyVector {
	double x, y;

	MyVector(double x, double y) {
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
		return this.x * v.x + this.y * v.y;
	}

	public double length() {
		return Math.sqrt(x * x + y * y);
	}

	public MyVector Mult(double l) {
		return new MyVector(x * l, y * l);
	}

	public MyVector Norm() {
		return Mult(1.0 / this.length());
	}
}

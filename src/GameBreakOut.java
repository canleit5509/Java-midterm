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
	Panel panel;
	int panel_w = 100;
	int panel_h = 30;
	// End Variable

	public GameBreakOut() {
		this.setTitle("Game Break Out");
		this.setSize(w, h);
		this.setDefaultCloseOperation(3);
		// Create Panel
		panel = new Panel(350, 450, this);
		panel.start();
		// Create Ball
		Random random = new Random();
		b = new Ball(350, 450, 13, random.nextDouble() * 5 - 2, 1.5, this);
		b.start();

		// Create Brick
		for (int i = 0; i < brickperrow; i++) {
			for (int j = 0; j < brickpercol; j++) {
				brick[i][j] = new Brick(30 + i * (rec_w + of), 50 + j * (rec_h + of), this);
			}
		}

		bufImg = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		g = bufImg.getGraphics();
		this.setVisible(true);
		// Add cac su kien
		this.addKeyListener(this);
		//
	}

	public void paint(Graphics g1) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		// Set graphics for panel
		g.setColor(Color.black);
		g.drawRect((int) (panel.x), (int) (panel.y), panel_w, panel_h);
		g.fillRect((int) (panel.x), (int) (panel.y), panel_w, panel_h);

		// Set graphics for brick
		for (int i = 0; i < brickperrow; i++) {
			for (int j = 0; j < brickpercol; j++) {
				if (brick[i][j].exist) {
					g.setColor(Color.gray);
					g.drawRect((int) (brick[i][j].x), (int) (brick[i][j].y), rec_w, rec_h);
					g.fillRect((int) (brick[i][j].x), (int) (brick[i][j].y), rec_w, rec_h);
				}
			}
		}

		// Set graphics for ball
		g.setColor(Color.blue);
		g.fillOval((int) (b.x - b.r), (int) (b.y - b.r), (int) (b.r * 2), (int) (b.r * 2));
		g.drawOval((int) (b.x - b.r), (int) (b.y - b.r), (int) (b.r * 2), (int) (b.r * 2));

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
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			System.out.println("Left");
			panel.vx = -2;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			System.out.println("Right");
			panel.vx = 2;
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		panel.vx = 0;

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}

class Ball extends Thread {
	double x, y, r, vx, vy;
	GameBreakOut gb;
	int rec_w = 60;
	int rec_h = 30;

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

			// When Ball touch brick
			for (int i = 0; i < gb.brickperrow; i++) {
				for (int j = 0; j < gb.brickpercol; j++) {
					Brick br = gb.brick[i][j];
					// Xu ly hinh tron cham hinh chu nhat
					if (br != null) {
						if (br.exist) {
							double px = x;
							double py = y;
							if (px < br.x) {
								px = br.x;
							}
							if (px > br.x + rec_w) {
								px = br.x + rec_w;
							}
							if (py < br.y) {
								py = br.y;
							}
							if (py > br.y + rec_h) {
								py = br.y + rec_h;
							}
							double dx = x - px;
							double dy = y - py;
							if (dx * dx + dy * dy <= r * r) {
								gb.brick[i][j].exist = false;
								// TODO : sua lai cach cham cua bong
								vy = -vy;
							}
						}
					}
				}
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

// Class Panel
class Panel extends Thread {
	double x, y;
	GameBreakOut gb;
	double vx;

	public Panel(double x, double y, GameBreakOut gb) {
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.gb = gb;
	}

	public void run() {
		while (true) {
			this.x += vx;
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
		}
	}
}

class Brick extends Thread {
	double x, y;
	boolean exist;
	GameBreakOut gb;

	public Brick(double x, double y, GameBreakOut gb) {
		this.x = x;
		this.y = y;
		this.gb = gb;
		this.exist = true;
	}
}

// Class Point
class MyPoint {
	double x, y;

	MyPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double Distance(MyPoint p) {
		return Math.sqrt(Math.pow((this.x - p.x), 2) + Math.pow((this.y - p.y), 2));
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

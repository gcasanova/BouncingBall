package com.mochilo.bouncing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import sun.management.ManagementFactory;


public class Board extends JPanel implements Runnable, Commons {
	
	private ArrayList<Platform> platforms = new ArrayList<Platform>(PLATFORM_NUMBERS);
	private ArrayList<Item> items = new ArrayList<Item>(ITEM_NUMBERS);
	
	private Dimension d;
	private Thread thread;
	private Ball ball;
	private boolean inGame = true;
	private double score;
	private boolean gameOver = false;
	private boolean mouseIn = false;
	
	public Board() {
		addKeyListener(new TAdapter());
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if (gameOver || !inGame) {
										
					if (e.getX() > 158 && e.getX() < 250) {
						if (e.getY() > 165 && e.getY() < 185) {
							mouseIn = true;
							repaint();
						}
					}

					if (e.getX() < 158 || e.getX() > 250) {
						mouseIn = false;
						repaint();
					}

					if (e.getY() < 165 || e.getY() > 185) {
						mouseIn = false;
						repaint();
					}
				}
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (mouseIn) {
					Runnable runnable = new Runnable() {
						
						@Override
						public void run() {
							
						}
					};
					try {
						restartApplication(runnable);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		setFocusable(true);
		d = new Dimension(BOARD_WIDTH, BOARD_HEIGTH);
		
		SoundHelper sound = new SoundHelper();
		sound.backgroundMusic();
		
		gameInit();
		setDoubleBuffered(true);
	}

	public void gameInit() {
		
		ball = new Ball();
		score = 0;
		
		for (int i = 0; i < PLATFORM_NUMBERS; i++) {
			platforms.add(i, new Platform(i * 90, 170));
		}
		for (int i = 0; i < ITEM_NUMBERS; i++) {
			Random r = new Random();
			switch (r.nextInt(2)) {
			case 0:
				items.add(i, new ScoreUp(BOARD_WIDTH + 200 * i, this));
				break;
			case 1:
				items.add(i, new ScoreDown(BOARD_HEIGTH + 500 * i, this));
				break;
			}
		}
		
		if (thread == null || !inGame){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	private class TAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
        	
        	switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				ball.moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				ball.moveRight();
				break;
			}
        }
    }

	@Override
	public void run() {
		while (inGame && !gameOver) {

			// if (x < 0 - width){
			for (Platform platform: platforms) {
				int testX = platform.getX();
				if (testX < 0 - platform.getWidth()) {
					Random r = new Random();
					int fakei = platforms.indexOf(platform);
					if (fakei == 0) {
						fakei = PLATFORM_NUMBERS;
					}
					platform.setX(platforms.get(fakei - 1).getX() + platform.getWidth()
							+ r.nextInt(80));
				}
			}

			gameOver = ball.isGameOver();

			/*if (cityX > 709 * -1) {
				cityX -= cityDX;
			} else {
				cityX = 0;
			}
			*/

			if (inGame && !gameOver) {
				score += .1;
			}

			Random r = new Random();

			for (int i = 0; i < ITEM_NUMBERS; i++) {
				if (items.get(i).isCreateNew()) {
					items.remove(i);
					switch (r.nextInt(2)) {
					case 0:
						items.add(new ScoreUp(BOARD_WIDTH + 200 * i, this));
						break;
					case 1:
						items.add(new ScoreDown(BOARD_WIDTH + 500 * i, this));
						break;
					}
					items.get(i).setCreateNew(false);
				}
			}

			ball.update();
			
				for (Platform platform: platforms) {
					platform.update(this, ball);
				}
				for (Item item: items) {
					item.update(this, ball);
				}
			
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			repaint();
		}
	}
	
	public int getScore() {
		return (int) score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void drawBall(Graphics g)
    {
		g.setColor(Color.BLUE);
		g.fillOval(ball.getX()-BALL_RADIUS, ball.getY()-BALL_RADIUS, BALL_RADIUS*2, BALL_RADIUS*2);
    }
	
	public void drawPlatforms(Graphics g) 
    {
		for (int i = 0; i < platforms.size(); i++) {
			g.setColor(Color.BLACK);
			g.fillRect(platforms.get(i).getX(), platforms.get(i).getY(), PLATFORM_WIDTH, PLATFORM_HEIGHT);
			g.drawRect(platforms.get(i).getX(), platforms.get(i).getY(), PLATFORM_WIDTH, PLATFORM_HEIGHT);
		}
    }
	
	public void drawItems(Graphics g) 
    {
		for (Item item: items) {
			if(item.getClass() == ScoreDown.class){
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.GREEN);
			}
			g.fillOval(item.getX()-ITEM_RADIUS, item.getY()-ITEM_RADIUS, ITEM_RADIUS*2, ITEM_RADIUS*2);
		}
    }
	
	public void drawRetryScreen(Graphics g)
	{
		if (gameOver || !inGame) {

			g.setColor(Color.BLACK);
			Font font = new Font("Serif", Font.BOLD, 32);
			g.setFont(font);
			g.drawString("GAME OVER", 100, 140);

			Font font2 = new Font("Serif", Font.BOLD, 16);

			if (mouseIn) {
				g.setColor(Color.RED);
				g.setFont(font2);
				g.drawString("TRY AGAIN", 160, 180);
			} else {
				g.setColor(Color.BLACK);
				g.setFont(font2);
				g.drawString("TRY AGAIN", 160, 180);
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		
		super.paint(g);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, d.width, d.height);

		if (inGame && !gameOver) {
			
	        drawBall(g);
	        drawPlatforms(g);
	        drawItems(g);
	    }

		String s = Integer.toString((int) score);
		Font font = new Font("Serif", Font.BOLD, 32);
		g.setFont(font);
		g.setColor(Color.GRAY);
		g.drawString(s, getWidth() - 65 + 2, 30 + 2);
		g.setColor(Color.BLACK);
		g.drawString(s, getWidth() - 65, 30);
		
		drawRetryScreen(g);

	}
	
	/** 
	 * Sun property pointing the main class and its arguments. 
	 * Might not be defined on non Hotspot VM implementations.
	 */
	public static final String SUN_JAVA_COMMAND = "sun.java.command";

	/**
	 * Restart the current Java application
	 * @param runBeforeRestart some custom code to be run before restarting
	 * @throws IOException
	 */
	public static void restartApplication(Runnable runBeforeRestart) throws IOException {
		try {
			// java binary
			String java = System.getProperty("java.home") + "/bin/java";
			// vm arguments
			List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
			StringBuffer vmArgsOneLine = new StringBuffer();
			for (String arg : vmArguments) {
				// if it's the agent argument : we ignore it otherwise the
				// address of the old application and the new one will be in conflict
				if (!arg.contains("-agentlib")) {
					vmArgsOneLine.append(arg);
					vmArgsOneLine.append(" ");
				}
			}
			// init the command to execute, add the vm args
			final StringBuffer cmd = new StringBuffer("\"" + java + "\" " + vmArgsOneLine);

			// program main and program arguments
			String[] mainCommand = System.getProperty(SUN_JAVA_COMMAND).split(" ");
			// program main is a jar
			if (mainCommand[0].endsWith(".jar")) {
				// if it's a jar, add -jar mainJar
				cmd.append("-jar " + new File(mainCommand[0]).getPath());
			} else {
				// else it's a .class, add the classpath and mainClass
				cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + mainCommand[0]);
			}
			// finally add program arguments
			for (int i = 1; i < mainCommand.length; i++) {
				cmd.append(" ");
				cmd.append(mainCommand[i]);
			}
			// execute the command in a shutdown hook, to be sure that all the
			// resources have been disposed before restarting the application
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					try {
						Runtime.getRuntime().exec(cmd.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			// execute some custom code before restarting
			if (runBeforeRestart!= null) {
				runBeforeRestart.run();
			}
			// exit
			System.exit(0);
		} catch (Exception e) {
			// something went wrong
			throw new IOException("Error while trying to restart the application", e);
		}
	}

}

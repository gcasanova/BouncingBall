package com.mochilo.bouncing;

import java.util.Random;


public class Platform {
	
	private int width = Commons.PLATFORM_WIDTH;
	private int height = Commons.PLATFORM_HEIGHT;
	private int dx = Commons.PLATFORM_SPEED;
	
	private int x, y;
	
	public Platform() {
		this.x = 20;
		this.y = 200;
	}
	
	public Platform(int x, int y){
		this.x = x;
		this.y = y;
	}

	public void update(Board board, Ball ball){
		
		x += dx;
		
		checkForCollision(ball);
		if (x < 0 - width){
			Random r = new Random();
			y = Commons.BOARD_HEIGTH - r.nextInt(150);
			x = Commons.BOARD_WIDTH + r.nextInt(80);
		}
	}
	
	private void checkForCollision(Ball b) {
		int ballX = b.getX();
		int ballY = b.getY();
		int radius = b.getRadius();
		
		if (ballY + radius > y && ballY + radius < y + height){
			if (ballX > x - 2 && ballX < x + width + 2){
				double newDY = b.getGameDy();
				b.setY(y-radius);
				b.setDy(newDY);
				SoundHelper sound = new SoundHelper();
				sound.bounceMusic();
			}
		}
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getDx() {
		return dx;
	}
	
}

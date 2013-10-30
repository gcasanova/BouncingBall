package com.mochilo.bouncing;

import java.util.Random;


public class Item {
	
	private int radius = Commons.ITEM_RADIUS;
	private int dx = Commons.ITEM_SPEED;
	
	private Board board;
	private boolean createNew = false;
	private int x, y;
	
	public Item(int x) {
		this.x = x;
		Random r = new Random();
		this.y = r.nextInt(180) + radius;
	}
	

	public void update(Board board, Ball ball){
		x += dx;
		this.board = board;
		checkForCollision(ball);
		if (x < 0 - radius){
			createNew = true;
		}
	}
	
	private void checkForCollision(Ball ball) {
		int ballX = ball.getX();
		int ballY = ball.getY();
		int ballR = ball.getRadius();
		
		int A = x - ballX;
		int B = y - ballY;
		int collide = radius + ballR;
		//distance between object centers
		double C = Math.sqrt((double) (A*A) + (double) (B*B));
		
		if (C < collide){
			performAction(ball);
			createNew = true;
		}
		
	}

	public void performAction(Ball b) {
		// To be defined in the subclasses	
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


	public int getRadius() {
		return radius;
	}


	public int getDx() {
		return dx;
	}


	public boolean isCreateNew() {
		return createNew;
	}


	public void setCreateNew(boolean createNew) {
		this.createNew = createNew;
	}

}

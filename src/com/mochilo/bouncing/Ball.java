package com.mochilo.bouncing;


public class Ball {
	
	private double gameDy = Commons.BALL_BOUNCING_POWER;
	private int radius = Commons.BALL_RADIUS;
	private double gravity = Commons.BALL_GRAVITY;
	private double xFriction = Commons.BALL_XFRICTION;
	private double dt = Commons.BALL_TIME_DIFERENTIAL;
	private int agility = Commons.BALL_AGILITY;
	private int maxSpeed = Commons.BALL_MAX_SPEED;
	
	private int x = 180;
	private int y = 25;
	private double dx = 0;
	private double dy = 0;
	private boolean gameOver = false;

	public Ball() {
		// TODO Auto-generated constructor stub
	}
	
	public Ball(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void update(){
		
		//define lateral boundaries and acceleration
		if (x + dx > Commons.BOARD_WIDTH - radius - 1){
			x = Commons.BOARD_WIDTH - radius - 1;
			dx = -dx;
		} else if (x + dx < 0 + radius){
			x = 0 + radius;
			dx = -dx;
		} else {
			x += dx;
		}
		
		//define friction effect (x-axis)
		if (y == Commons.BOARD_HEIGTH - radius - 1){
			dx *= xFriction;
			if (Math.abs(dx) < .8){
				dx = 0;
			}
		}
		
		//define vertical bottom boundary and energy loss effect (y-axis)
		if (y - 200 > Commons.BOARD_HEIGTH - radius - 1){
			gameOver = true;
		} else {
			//define velocity (Kinematic Equations/Motion)
			dy += gravity * dt;
			//define position (current position "y" + displacement)
			y += dy * dt + .5 * gravity * dt * dt;
		}
		
	}
	
	public void moveRight(){
		if (dx + agility < maxSpeed){
			dx += agility;
		}
	}
	
	public void moveLeft(){
		if (dx - agility > -maxSpeed){
			dx -= agility;
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

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public double getGameDy() {
		return gameDy;
	}

	public int getRadius() {
		return radius;
	}

	public double getGravity() {
		return gravity;
	}

	public double getxFriction() {
		return xFriction;
	}

	public double getDt() {
		return dt;
	}

	public int getAgility() {
		return agility;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}
}

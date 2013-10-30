package com.mochilo.bouncing;


public class ScoreUp extends Item {
	
	private Board board;

	public ScoreUp(int x, Board board) {
		super(x);
		this.board = board;
	}
	
	@Override
	public void performAction(Ball b) {
		board.setScore(board.getScore() + 500);
		super.performAction(b);
	}

}

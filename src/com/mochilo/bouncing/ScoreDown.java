package com.mochilo.bouncing;


public class ScoreDown extends Item{
		
		private Board board;

		public ScoreDown(int x, Board board) {
			super(x);
			this.board = board;
		}
		
		@Override
		public void performAction(Ball b) {
			if(board.getScore() >= 1000){
				board.setScore(board.getScore() - 1000);
			} else {
				board.setScore(0);
			}
			super.performAction(b);
		}
}

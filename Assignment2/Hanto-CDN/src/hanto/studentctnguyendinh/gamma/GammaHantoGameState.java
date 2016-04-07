package hanto.studentctnguyendinh.gamma;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.*;

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

public class GammaHantoGameState implements HantoGameState {
	
	private Map<HantoCoordinateImpl, HantoPiece> board = new HashMap<>();
	
	private HantoPlayerColor movesFirst;
	private HantoPlayerColor movesSecond;
	
	private int currentPlayer = 0;	// 0 = first player, 1 = second player
	
	private int moveCount = 0; 
	private boolean gameOver = false;
	
	private HantoCoordinateImpl blueButterflyCoord;
	private HantoCoordinateImpl redButterflyCoord;
	
	protected GammaHantoGameState(HantoPlayerColor movesFirst) {
		this.movesFirst = movesFirst;
		this.movesSecond = movesFirst == BLUE ? RED : BLUE;
	}
	
	protected void advanceMove() {
		moveCount++;
		currentPlayer = 1 - currentPlayer;
	}
	
	protected void setGameOver() {
		gameOver = true;
	}
	
	protected void removePieceAt(HantoCoordinate coord) {
		board.remove(new HantoCoordinateImpl(coord));
	}
	
	protected void putPieceAt(HantoCoordinate coord, HantoPiece piece) {
		HantoCoordinateImpl innerCoord = new HantoCoordinateImpl(coord);
		board.put(innerCoord, piece);
		if (piece.getType() == BUTTERFLY) {
			if (piece.getColor() == BLUE) {
				blueButterflyCoord = innerCoord;
			}
			else {
				redButterflyCoord = innerCoord;
			}
		}
	}
	
	@Override
	public HantoPiece getPieceAt(HantoCoordinate coord) {
		return board.get(new HantoCoordinateImpl(coord));
	}
	
	@Override
	public int getNumberOfPlayedMoves() {
		return moveCount;
	}
	
	@Override
	public boolean isGameOver() {
		return gameOver;
	}

	@Override 
	public HantoCoordinate getBlueButterflyCoord() {
		return blueButterflyCoord;
	}
	
	@Override
	public HantoCoordinate getRedButterflyCoord() {
		return redButterflyCoord;
	}
	
	@Override
	public HantoPlayerColor getMoveFirstPlayer() {
		return movesFirst;
	}
	
	@Override
	public HantoPlayerColor getMoveSecondPlayer() {
		return movesSecond;
	}

	@Override
	public HantoPlayerColor getCurrentPlayer() {
		return currentPlayer == 0 ? movesFirst : movesSecond;
	}
}

package hanto.studentctnguyendinh.gamma;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.BLUE;

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

public class GammaHantoGameState implements HantoGameState {
	
	private Map<HantoCoordinate, HantoPiece> board = new HashMap<>();
	
	private int moveCount = 0; 
	private boolean gameOver = false;
	
	private HantoCoordinateImpl blueButterflyCoord;
	private HantoCoordinateImpl redButterflyCoord;
	
	protected void advanceMove() {
		moveCount++;
	}
	
	protected void setGameOver() {
		gameOver = true;
	}
	
	protected void removePieceAt(HantoCoordinate coord) {
		board.remove(coord);
	}
	
	protected void putPieceAt(HantoPiece piece, HantoCoordinate coord) {
		board.put(coord, piece);
		if (piece.getType() == BUTTERFLY) {
			if (piece.getColor() == BLUE) {
				blueButterflyCoord = new HantoCoordinateImpl(coord);
			}
			else {
				redButterflyCoord = new HantoCoordinateImpl(coord);
			}
		}
	}
	
	@Override
	public HantoPiece getPieceAt(HantoCoordinate coord) {
		return board.get(coord);
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

}

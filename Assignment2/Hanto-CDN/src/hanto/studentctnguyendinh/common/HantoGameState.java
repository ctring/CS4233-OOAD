package hanto.studentctnguyendinh.common;

import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;

public interface HantoGameState {
	
	/**
	 * Get a piece at the given coordinate.
	 * @param coord coordinate of the desired piece.
	 * @return the pieced at the given coordinate. Null if there is none.
	 */
	HantoPiece getPieceAt(HantoCoordinate coord);
	
	/**
	 * @return number of played moves.
	 */
	int getNumberOfPlayedMoves();
	
	/**
	 * @return a boolean indicating if game is over.
	 */
	boolean isGameOver();
	
	/**
	 * @return coordinate of the blue butterfly. Null if it is not placed yet.
	 */
	HantoCoordinate getBlueButterflyCoord();
	
	/**
	 * @return coordinate of the red butterfly. Null if it is not placed yet.
	 */
	HantoCoordinate getRedButterflyCoord();
}

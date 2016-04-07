package hanto.studentctnguyendinh.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;

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
	
	/**
	 * @return color of the player who moves first.
	 */
	HantoPlayerColor getMoveFirstPlayer();
	
	/**
	 * @return color of the player who moves second.
	 */
	HantoPlayerColor getMoveSecondPlayer();
	
	/**
	 * @return get color of current player.
	 */
	HantoPlayerColor getCurrentPlayer();
	
}

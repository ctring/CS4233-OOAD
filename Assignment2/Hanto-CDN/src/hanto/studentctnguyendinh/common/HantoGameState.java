package hanto.studentctnguyendinh.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
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
	
	/**
	 * @return object representing state of the blue player. Null if it is not placed yet.
	 */
	HantoPlayerState getBluePlayerState();
	
	/**
	 * @return object representing state of the red player. Null if it is not placed yet.
	 */
	HantoPlayerState getRedPlayerState();
	
	public interface HantoPlayerState {
		/**
		 * @return coordinate of the butterfly of this player. 
		 */
		public HantoCoordinate getButterflyCoordinate();
		/**
		 * @param pieceType type of piece in question. 
		 * @return number of remaining pieces of the given type.
		 */
		public int getNumberOfRemainingPieces(HantoPieceType pieceType);
	}
}

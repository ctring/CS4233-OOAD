/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * HantoGameState is an interface for providing state of a HantoGame during playing.
 * @author Cuong Nguyen
 * @version April 6, 2016
 */
public interface HantoGameState {
	
	/**
	 * Get a Hanto piece at a given coordinate.
	 * @param coord coordinate that needs retrieving a piece.
	 * @return a Hanto piece at the given coordinate. Null if there is none.
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
	 * @return get color of current player.
	 */
	HantoPlayerColor getCurrentPlayer();
	
	/**
	 * @param player of a player.
	 * @return object representing state of a player with given color. Null if it is not placed yet.
	 */
	HantoPlayerState getPlayerState(HantoPlayerColor player);

	
	/**
	 * @return Return a duplicate of the board of this game.
	 */
	HantoBoard cloneBoard();
	
	/**
	 * HantoPlayerState is an interface for providing state of a player. 
	 * @author Cuong Nguyen
	 * @version April 6, 2016
	 *
	 */
	interface HantoPlayerState {
		/**
		 * @return coordinate of the butterfly of this player. 
		 */
		HantoCoordinate getButterflyCoordinate();
		/**
		 * @param pieceType type of piece in question. 
		 * @return number of remaining pieces of the given type.
		 */
		int getNumberOfRemainingPieces(HantoPieceType pieceType);
	}
}

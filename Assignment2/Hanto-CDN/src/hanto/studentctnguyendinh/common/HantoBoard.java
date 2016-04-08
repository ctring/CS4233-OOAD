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

/**
 * HantoBoard represents a board in the HantoGame.
 * @author Cuong Nguyen 
 * @version April 7, 2016
 */
public interface HantoBoard {
	
	/**
	 * Get a Hanto piece at a given coordinate.
	 * @param coord coordinate that needs retrieving a piece.
	 * @return a Hanto piece at the given coordinate. Null if there is none.
	 */
	HantoPiece getPieceAt(HantoCoordinate coord);

	/**
	 * Put a Hanto piece at a given coordinate.
	 * @param coord coordinate of the new piece.
	 * @param piece piece to be placed.
	 */
	void putPieceAt(HantoCoordinate coord, HantoPiece piece);
	
	/**
	 * Move a piece between two coordinates.
	 * @param from coordinate of the piece to be moved.
	 * @param to destination coordinate.
	 */
	void movePiece(HantoCoordinate from, HantoCoordinate to);
	
	/**
	 * Check if the pieces are connected.
	 * @return true if every piece is connect, false otherwise.
	 */
	boolean validateConnectivity();
	
	/**
	 * @return a string representing the current state of the board.
	 */
	String getPrintableBoard();
}

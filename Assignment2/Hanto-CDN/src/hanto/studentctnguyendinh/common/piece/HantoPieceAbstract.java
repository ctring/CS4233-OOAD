/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.piece;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * This class extends the capability of a Hanto piece such that each piece can
 * perform movement validation by itself.
 * 
 * @author nguye_000
 *
 */
public abstract class HantoPieceAbstract implements HantoPiece {

	HantoMovementRule[] validators;

	/**
	 * Validate movement rules of this piece based on the current game state and
	 * movement path of this piece.
	 * 
	 * @param gameState
	 *            current game state.
	 * @param from
	 *            coordinate where this piece begins.
	 * @param to
	 *            coordinate where this piece is after making its move.
	 * @throws HantoException
	 *             if the move is illegal.
	 */
	public abstract void validateMove(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to)
			throws HantoException;

}

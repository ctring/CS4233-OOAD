/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * HantoRule are strategies for validating general rules of a Hanto game.
 * 
 * @author Cuong Nguyen
 * @version April 6, 2016
 *
 */
public interface HantoRule {

	/**
	 * Validate a rule based on current game state and input.
	 * 
	 * @param gameState
	 *            current game state.
	 * @param pieceType
	 *            type of the piece in the latest move.
	 * @param from
	 *            coordinate where the piece begins.
	 * @param to
	 *            coordinate where the piece is after this move.
	 * @return an error message if the move is illegal, null otherwise.
	 */
	String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to);
}

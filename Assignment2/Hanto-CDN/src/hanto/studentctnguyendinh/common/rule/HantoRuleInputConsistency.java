/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * This rule checks for consistency in the input against the state of the board:
 * <ul>
 * <li>Specified piece type must match piece type of the piece on the board.</li>
 * <li>Current player color must match color of the piece.</li>
 * <li>There must be a piece at the given coordinate.</li>
 * </ul>
 *
 * @author Cuong Nguyen
 *
 */
public class HantoRuleInputConsistency implements HantoRule {

	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		if (from != null) {
			HantoPiece piece = gameState.getPieceAt(from);
			HantoPlayerColor currentPlayer = gameState.getCurrentPlayer();
			if (piece == null || piece.getType() != pieceType 
					|| piece.getColor() != currentPlayer) {
				return "There is no piece with specified type or color at the given coordinate";
			}
		}
		return null;
	}

}

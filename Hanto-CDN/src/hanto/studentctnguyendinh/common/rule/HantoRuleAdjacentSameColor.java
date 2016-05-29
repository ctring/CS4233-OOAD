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
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * This rule checks if the added piece is adjacent to pieces with the same
 * color.
 * 
 * @author Cuong Nguyen
 * @version April 7, 2016
 */
public class HantoRuleAdjacentSameColor implements HantoRule {

	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		if (from == null && gameState.getNumberOfPlayedMoves() >= 2) {
			HantoPlayerColor currentPlayer = gameState.getCurrentPlayer();
			HantoCoordinateImpl[] adjCoords = new HantoCoordinateImpl(to).getAdjacentCoordsSet();
			for (HantoCoordinateImpl coord : adjCoords) {
				HantoPiece piece = gameState.getPieceAt(coord);
				if (piece != null) {
					if (piece.getColor() != currentPlayer) {
						return "Cannot place next to an opponent piece";
					}
				}
			}
		}
		return null;
	}

}

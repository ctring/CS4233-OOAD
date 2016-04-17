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
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * This rule checks if the move ends with a piece next to some other pieces. 
 * 
 * @author Cuong Nguyen
 * @version April 6, 2016
 */
public class HantoRuleNotAdjacent implements HantoRule {

	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		
		if (gameState.getNumberOfPlayedMoves() == 0) {
			return null;
		}
		
		boolean isAdjacentToAny = false;
		HantoCoordinateImpl coord = new HantoCoordinateImpl(to);
		HantoCoordinateImpl[] adjCoords = coord.getAdjacentCoordsSet();
		for (int i = 0; i < 6; i++) {
			if (gameState.getPieceAt(adjCoords[i]) != null) {
				isAdjacentToAny = true;
				break;
			}
		}
		
		return !isAdjacentToAny ? "Pieces must be ajacent to each other" : null;
	}
	
}

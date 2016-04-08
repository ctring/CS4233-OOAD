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
import hanto.studentctnguyendinh.common.HantoBoard;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * This rules check if pieces on the board remain connected as a whole.
 * @author Cuong Nguyen
 * @version April 7, 2016
 *
 */
public class HantoRuleContinuousMove implements HantoRule {

	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		if (from != null) {
			HantoBoard tryBoard = gameState.cloneBoard();
			tryBoard.movePiece(from, to);
			if (tryBoard.validateConnectivity()) {
				return null;
			}
			else {
				return "Cannot make a discontinuous move"; 
			}
		}
		return null;
	}

}

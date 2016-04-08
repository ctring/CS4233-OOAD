/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * This rule prevents movements that are made before the placer placing a butterfly.
 * @author Cuong Nguyen
 * @version April 7, 2016
 *
 */
public class HantoRuleMoveBeforeButterfly implements HantoRule {

	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		if (from != null) {
			HantoPlayerColor currentPlayer = gameState.getCurrentPlayer();
			if (gameState.getPlayerState(currentPlayer).getButterflyCoordinate() == null) {
				return "Cannot move before placing Butterfly";
			}
		}
		return null;
	}

}

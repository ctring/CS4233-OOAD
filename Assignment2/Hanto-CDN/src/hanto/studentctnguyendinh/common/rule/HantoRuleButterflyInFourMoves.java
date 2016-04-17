/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.rule;

import static hanto.common.HantoPieceType.BUTTERFLY;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * This rules check if a butterfly is placed in the first four moves.
 * 
 * @author Cuong Nguyen
 * @version April 6, 2016
 */
public class HantoRuleButterflyInFourMoves implements HantoRule {

	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		HantoGameState.HantoPlayerState currentPlayerState = gameState.getPlayerState(gameState.getCurrentPlayer());
		boolean placedButterfly = currentPlayerState.getButterflyCoordinate() != null;

		int currentPlayerMoves = gameState.getNumberOfPlayedMoves() / 2 + 1;
		if (currentPlayerMoves == 4 && !placedButterfly && pieceType != BUTTERFLY) {
			return "A butterfly must be placed in the first four turn";
		}
		return null;
	}
}

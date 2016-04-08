/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.rule;

import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.*;

import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * This rule checks if any of the butterfly is surrounded.
 * @author Cuong Nguyen
 *
 */
public class HantoEndRuleButterflyIsSurrounded implements HantoEndRule {

	@Override
	public MoveResult checkResult(HantoGameState state) {
		final boolean redButterflySurrounded = checkButterflySurrounded(state, RED);
		final boolean blueButterflySurrounded = checkButterflySurrounded(state, BLUE);
		if (redButterflySurrounded && blueButterflySurrounded) {
			return DRAW;
		}
		else if (redButterflySurrounded) {
			return BLUE_WINS;
		}
		else if (blueButterflySurrounded) {
			return RED_WINS;
		}
		return OK;
	}
	
	private boolean checkButterflySurrounded(HantoGameState gameState, HantoPlayerColor player) {
		if (gameState.getPlayerState(player).getButterflyCoordinate() == null) {
			return false;
		}
		final HantoCoordinateImpl butterflyCoord = new HantoCoordinateImpl(
				gameState.getPlayerState(player).getButterflyCoordinate());
		
		final HantoCoordinateImpl[] adjCoords = butterflyCoord.getAdjacentCoordsSet();
		for (int i = 0; i < 6; i++) {
			if (gameState.getPieceAt(adjCoords[i]) == null) {
				return false;
			}
		}
		return true;
	}

}

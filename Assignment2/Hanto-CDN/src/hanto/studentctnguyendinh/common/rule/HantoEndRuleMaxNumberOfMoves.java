/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.rule;

import static hanto.common.MoveResult.*;

import hanto.common.MoveResult;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * The game ends in DRAW if the maximum number of moves reaches.
 * @author nguye_000
 *
 */
public class HantoEndRuleMaxNumberOfMoves implements HantoEndRule {

	private final int MAX_NUMBER_OF_MOVE = 40;
	
	@Override
	public MoveResult checkResult(HantoGameState gameState) {
		if (gameState.getNumberOfPlayedMoves() >= MAX_NUMBER_OF_MOVE) {
			return DRAW;
		}
		return OK;
	}

}

/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.rule;

import hanto.common.MoveResult;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * HantoMovementRule are strategies for checking situations that leading the end
 * of a Hanto game. 
 * 
 * @author Cuong Nguyen
 * @version April 6, 2016
 *
 */
public interface HantoEndRule {
	/**
	 * Check result of a move based on current game state.
	 * @param state current game state
	 * @return 
	 * 		OK if the move does not lead to a game end.
	 * 		DRAW if both players are draw.
	 * 		REDS_WIN if red player wins.
	 * 		BLUES_WIN if blue player wins.
	 */
	MoveResult checkResult(HantoGameState state);
}

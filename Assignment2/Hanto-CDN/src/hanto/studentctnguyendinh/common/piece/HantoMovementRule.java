/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.piece;

import hanto.common.HantoCoordinate;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * HantoMovementRule are strategies for validating movement rules of a Hanto Piece. 
 * 
 * @author Cuong Nguyen
 * @version April 6, 2016
 *
 */
public interface HantoMovementRule {
	
	/**
	 * Validate a movement rule based on current game state an input.
	 * @param gameState current game state.
	 * @param from coordinate where the piece begins.
	 * @param to coordinate where the piece is after this move.
	 * @return an error message if the move is illegal, null otherwise.
	 */
	String validate(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to);
	
	/**
	 * Get an array of coordinates that can be reached using this movement rule.
	 * @param gameState current game state.
	 * @param from coordinate where the piece begins. 
	 * @return an array of coordinates that can be reached.
	 */
	//HantoCoordinate[] getReachableCoordinates(HantoGameState gameState, HantoCoordinate from);
}

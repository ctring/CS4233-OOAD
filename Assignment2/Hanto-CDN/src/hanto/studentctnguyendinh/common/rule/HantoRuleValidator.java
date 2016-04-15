/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * HantoRuleValidator is an interface for the rule validators that perform rules
 * checking using a set of strategies.
 *  
 * @author Cuong Nguyen
 * @version April 6, 2016
 *
 */
public interface HantoRuleValidator {

	/**
	 * Enforce a set of rule based on given game state and input.
	 * @param gameState current state of the game.
	 * @param pieceType type of the piece in the latest move.
	 * @param from coordinate where the piece begins in the latest move.
	 * @param to coordinate where the piece is after the latest move.
	 * @throws HantoException if there is a rule that is not satisfied.
	 */
	void validateRules(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,	
			HantoCoordinate to) throws HantoException;
}

/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.rule;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * This is an implementation of a Hanto rule validator.
 * @author Cuong Nguyen
 * @version April 6, 2016
 *
 */
public class HantoRuleValidatorImpl implements HantoRuleValidator {

	HantoRule[] ruleList;
	
	/**
	 * Create a new Hanto rule validator with given rules list and end rules list.
	 * @param ruleList list of general rules.
	 * @param endRuleList list of rules for a game end.
	 */
	public HantoRuleValidatorImpl(HantoRule[] ruleList) {
		this.ruleList = ruleList.clone();
	}
	
	@Override
	public void validateRules(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,	HantoCoordinate to) throws HantoException {
		for (HantoRule rule : ruleList) {
			String error = rule.validate(gameState, pieceType, from, to);
			if (error != null) {
				throw new HantoException(error);
			}
		} 
	}
	
}

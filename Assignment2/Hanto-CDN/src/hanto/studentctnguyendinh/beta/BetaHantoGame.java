/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package hanto.studentctnguyendinh.beta;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.HantoGameBase;
import hanto.studentctnguyendinh.common.HantoGameState;
import hanto.studentctnguyendinh.common.rule.HantoRule;
import hanto.studentctnguyendinh.common.rule.HantoRuleButterflyInFourMoves;
import hanto.studentctnguyendinh.common.rule.HantoRuleFirstMoveAtOrigin;
import hanto.studentctnguyendinh.common.rule.HantoRuleGameOver;
import hanto.studentctnguyendinh.common.rule.HantoRuleNotAdjacent;
import hanto.studentctnguyendinh.common.rule.HantoRuleOccupiedHex;
import hanto.studentctnguyendinh.common.rule.HantoRulePiecesQuota;
import hanto.studentctnguyendinh.common.rule.HantoRuleValidatorImpl;

/**
 * <<Fill this in>>
 * @version Mar 16, 2016
 */
public class BetaHantoGame extends HantoGameBase
{
	/**
	 * Construct a BetaHantoGame instance with the player who moves first being specified.
	 * @param movesFirst Color of the player who moves first.
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		
		maxNumberOfMove = 12;
		
		HantoRule[] rules = { new HantoRuleGameOver(), 
				new HantoRuleFirstMoveAtOrigin(),
				new HantoRuleOccupiedHex(),
				new HantoRuleNotAdjacent(), 
				new HantoRulePiecesQuota(), 
				new HantoRuleButterflyInFourMoves(),
				};

		ruleValidator = new HantoRuleValidatorImpl(new ArrayList<HantoRule>(Arrays.asList(rules)));

		Map<HantoPieceType, Integer> gammaPiecesQuota = new HashMap<>();
		gammaPiecesQuota.put(BUTTERFLY, 1);
		gammaPiecesQuota.put(SPARROW, 5);

		gameState = new HantoGameState(movesFirst, gammaPiecesQuota);
		
	}	
	
}

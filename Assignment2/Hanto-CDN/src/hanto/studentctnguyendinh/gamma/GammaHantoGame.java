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

package hanto.studentctnguyendinh.gamma;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.MoveResult.OK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import hanto.common.*;
import hanto.studentctnguyendinh.HantoPieceFactory;
import hanto.studentctnguyendinh.common.HantoGameBase;
import hanto.studentctnguyendinh.common.HantoGameState;
import hanto.studentctnguyendinh.common.piece.HantoPieceAbstract;
import hanto.studentctnguyendinh.common.rule.*;

/**
 * A concrete implementation of the Gamma version of the Hanto game.
 * 
 * @author Cuong Nguyen
 * @version April 7, 2016
 */
public class GammaHantoGame extends HantoGameBase {
	
	/**
	 * Construct a GammaHantoGame instance with the player who moves first being
	 * specified.
	 * 
	 * @param movesFirst
	 *            color of the player who moves first
	 * @param ruleValidator
	 *            a validators that validates set of rules for this game.
	 * @param piecesQuota
	 *            number of available pieces for each piece types.
	 */
	public GammaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		
		maxNumberOfMove = 40;
		
		HantoRule[] rules = { new HantoRuleGameOver(), new HantoRuleFirstMoveAtOrigin(),
				new HantoRuleInputConsistency(), new HantoRuleMoveBeforeButterfly(), new HantoRuleOccupiedHex(),
				new HantoRuleNotAdjacent(), new HantoRulePiecesQuota(), new HantoRuleButterflyInFourMoves(),
				new HantoRuleAdjacentSameColor(), new HantoRuleContinuousMove() };

		ruleValidator = new HantoRuleValidatorImpl(new ArrayList<HantoRule>(Arrays.asList(rules)));

		Map<HantoPieceType, Integer> gammaPiecesQuota = new HashMap<>();
		gammaPiecesQuota.put(BUTTERFLY, 1);
		gammaPiecesQuota.put(SPARROW, 5);

		gameState = new HantoGameState(movesFirst, gammaPiecesQuota);
	}
}

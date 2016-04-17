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

package hanto.studentctnguyendinh.delta;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.CRAB;
import static hanto.common.HantoPieceType.SPARROW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.HantoGameBase;
import hanto.studentctnguyendinh.common.HantoGameState;
import hanto.studentctnguyendinh.common.piece.HantoMovementRule;
import hanto.studentctnguyendinh.common.piece.HantoPieceImpl;
import hanto.studentctnguyendinh.common.piece.MVBlockedPiece;
import hanto.studentctnguyendinh.common.piece.MVFlying;
import hanto.studentctnguyendinh.common.piece.MVWalking;
import hanto.studentctnguyendinh.common.rule.HantoRule;
import hanto.studentctnguyendinh.common.rule.HantoRuleAdjacentSameColor;
import hanto.studentctnguyendinh.common.rule.HantoRuleButterflyInFourMoves;
import hanto.studentctnguyendinh.common.rule.HantoRuleMoveBeforeButterfly;
import hanto.studentctnguyendinh.common.rule.HantoRuleNotAdjacent;
import hanto.studentctnguyendinh.common.rule.HantoRuleOccupiedHex;
import hanto.studentctnguyendinh.common.rule.HantoRuleValidatorImpl;

/**
 * A concrete implementation of the Gamma version of the Hanto game.
 * 
 * @author Cuong Nguyen
 * @version April 7, 2016
 */
public class DeltaHantoGame extends HantoGameBase {
	
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
	public DeltaHantoGame(HantoPlayerColor movesFirst) {

		maxNumberOfMove = 40;
		
		HantoRule[] rules = { 
				new HantoRuleMoveBeforeButterfly(), 
				new HantoRuleOccupiedHex(),
				new HantoRuleNotAdjacent(),
				new HantoRuleButterflyInFourMoves(),
				new HantoRuleAdjacentSameColor() };

		ruleValidator = new HantoRuleValidatorImpl(rules);

		Map<HantoPieceType, Integer> deltaPiecesQuota = new HashMap<>();
		deltaPiecesQuota.put(BUTTERFLY, 1);
		deltaPiecesQuota.put(SPARROW, 4);
		deltaPiecesQuota.put(CRAB, 4);

		gameState = new HantoGameState(movesFirst, deltaPiecesQuota);
	}

	@Override
	public HantoPiece makeHantoPiece(HantoPlayerColor color, HantoPieceType pieceType) {
		HantoMovementRule[] validators;
		switch (pieceType) {
		case CRAB:
			validators = new HantoMovementRule[] {new MVBlockedPiece(), new MVWalking(3)};
			break;
		case BUTTERFLY:
			validators = new HantoMovementRule[] {new MVBlockedPiece(), new MVWalking(1)};
			break;
		case SPARROW:
			validators = new HantoMovementRule[] {new MVFlying()};
			break;
		default:
			validators = new HantoMovementRule[0];
		}

		return new HantoPieceImpl(color, pieceType, validators);
	}
}

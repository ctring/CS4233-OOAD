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

package hanto.studentctnguyendinh.epsilon;

import static hanto.common.HantoPieceType.*;

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.HantoGameBase;
import hanto.studentctnguyendinh.common.HantoGameState;
import hanto.studentctnguyendinh.common.piece.*;
import hanto.studentctnguyendinh.common.rule.*;


/**
 * A concrete implementation of the Delta version of the Hanto game.
 * 
 * @author Cuong Nguyen
 * @version April 18, 2016
 */
public class EpsilonHantoGame extends HantoGameBase {

	/**
	 * Construct a DeltaHantoGame instance with the player who moves first being
	 * specified.
	 * 
	 * @param movesFirst
	 *            color of the player who moves first
	 */
	public EpsilonHantoGame(HantoPlayerColor movesFirst) {

		maxNumberOfMove = 40;

		HantoRule[] rules = { new HantoRuleMoveBeforeButterfly(), new HantoRuleOccupiedHex(),
				new HantoRuleNotAdjacent(), new HantoRuleButterflyInFourMoves(), new HantoRuleAdjacentSameColor() };

		ruleValidator = new HantoRuleValidatorImpl(rules);

		Map<HantoPieceType, Integer> epsilonPiecesQuota = new HashMap<>();
		epsilonPiecesQuota.put(BUTTERFLY, 1);
		epsilonPiecesQuota.put(SPARROW, 2);
		epsilonPiecesQuota.put(CRAB, 6);
		epsilonPiecesQuota.put(HORSE, 4);

		gameState = new HantoGameState(movesFirst, epsilonPiecesQuota);
	}

	@Override
	public HantoPiece makeHantoPiece(HantoPlayerColor color, HantoPieceType pieceType) {
		HantoMovementRule[] validators;
		switch (pieceType) {
		case CRAB:
			validators = new HantoMovementRule[] { new MVWalking(1) };
			break;
		case BUTTERFLY:
			validators = new HantoMovementRule[] { new MVWalking(1) };
			break;
		case SPARROW:
			validators = new HantoMovementRule[] { new MVFlying(4) };
			break;
		case HORSE:
			validators = new HantoMovementRule[] { new MVJumping() };
			break;
		default:
			validators = new HantoMovementRule[0];
		}

		return new HantoPieceImpl(color, pieceType, validators);
	}
}

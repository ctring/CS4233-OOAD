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
 * A concerete implementation of the Gamma version of the Hanto game.
 * 
 * @author Cuong Nguyen
 * @version April 7, 2016
 */
public class GammaHantoGame extends HantoGameBase {
	
//	private HantoGameState gameState;
//	private HantoRuleValidator ruleValidator;
//	private HantoPieceFactory pieceFactory;

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
		HantoRule[] rules = { new HantoRuleGameOver(), new HantoRuleFirstMoveAtOrigin(),
				new HantoRuleInputConsistency(), new HantoRuleMoveBeforeButterfly(), new HantoRuleOccupiedHex(),
				new HantoRuleNotAdjacent(), new HantoRulePiecesQuota(), new HantoRuleButterflyInFourMoves(),
				new HantoRuleAdjacentSameColor(), new HantoRuleContinuousMove() };

		HantoEndRule[] endRules = { new HantoEndRuleButterflyIsSurrounded(), new HantoEndRuleMaxNumberOfMoves() };
		ruleValidator = new HantoRuleValidatorImpl(new ArrayList<HantoRule>(Arrays.asList(rules)),
				new ArrayList<HantoEndRule>(Arrays.asList(endRules)));

		Map<HantoPieceType, Integer> gammaPiecesQuota = new HashMap<>();
		gammaPiecesQuota.put(BUTTERFLY, 1);
		gammaPiecesQuota.put(SPARROW, 5);

		gameState = new HantoGameState(movesFirst, gammaPiecesQuota);
	}

//	/*
//	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType,
//	 * hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
//	 */
//	@Override
//	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
//			throws HantoException {
//		ruleValidator.validateRules(gameState, pieceType, from, to);
//
//		if (from == null) {
//			HantoPiece newPiece = pieceFactory.makeHantoPiece(gameState.getCurrentPlayer(), pieceType);
//			gameState.putPieceAt(to, newPiece);
//		} else {
//			HantoPieceAbstract piece = (HantoPieceAbstract) gameState.getPieceAt(from);
//			piece.validateMove(gameState, from, to);
//			gameState.movePiece(from, to);
//		}
//
//		gameState.advanceMove();
//
//		MoveResult moveResult = ruleValidator.validateEndRules(gameState);
//		if (moveResult != OK) {
//			gameState.flagGameOver();
//		}
//
//		return moveResult;
//	}
//
//	/*
//	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
//	 */
//	@Override
//	public HantoPiece getPieceAt(HantoCoordinate where) {
//		return gameState.getPieceAt(where);
//	}
//
//	/*
//	 * @see hanto.common.HantoGame#getPrintableBoard()
//	 */
//	@Override
//	public String getPrintableBoard() {
//		return gameState.getPrintableBoard();
//	}

}

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

import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.*;

import java.util.HashMap;
import java.util.Map;

import hanto.common.*;
import hanto.common.MoveResult;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.piece.HantoPieceImpl;
import hanto.studentctnguyendinh.common.rule.HantoRuleValidator;

/**
 * <<Fill this in>>
 * @version Mar 16, 2016
 */
public class GammaHantoGame implements HantoGame
{
	private GammaHantoGameState gameState;
	private HantoRuleValidator ruleValidator;
	
	private Map<HantoCoordinate, HantoPiece> board = new HashMap<>();
	
	public GammaHantoGame(HantoRuleValidator ruleValidator, Map<HantoPieceType, Integer> piecesQuota) {
		this(BLUE, ruleValidator, piecesQuota);
	}

	/**
	 * Construct a BetaHantoGame instance with the player who moves first being specified.
	 * @param movesFirst Color of the player who moves first.
	 */
	public GammaHantoGame(HantoPlayerColor movesFirst, HantoRuleValidator ruleValidator, Map<HantoPieceType, Integer> piecesQuota) {
		this.ruleValidator = ruleValidator;
		gameState = new GammaHantoGameState(movesFirst, piecesQuota);
	}	
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		ruleValidator.validateRules(gameState, pieceType, from, to);
						
		HantoPiece newPiece = new HantoPieceImpl(gameState.getCurrentPlayer(), pieceType);
		gameState.putPieceAt(to, newPiece);
		gameState.advanceMove();
		
		MoveResult moveResult = ruleValidator.validateEndRules(gameState);
		if (moveResult != OK) {
			gameState.setGameOver();
		}
		
		return moveResult;
	}
	
	
	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where)
	{
		return gameState.getPieceAt(where);
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		return gameState.getPrintableBoard();
	}

	
}

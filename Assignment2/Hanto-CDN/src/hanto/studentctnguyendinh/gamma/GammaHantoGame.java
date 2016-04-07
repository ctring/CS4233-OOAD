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
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;
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
	
	private HantoPlayerColor movesFirst;
	private HantoPlayerColor movesSecond;
	private Map<HantoCoordinate, HantoPiece> board = new HashMap<>();
	
	private int moveCount = 0; 
	private boolean bluePlacedButterfly = false;
	private boolean redPlacedButterfly = false;
	private boolean gameOver = false;
	
	
	private HantoCoordinateImpl blueButterflyCoord;
	private HantoCoordinateImpl redButterflyCoord;
	
	public GammaHantoGame(HantoRuleValidator ruleValidator) {
		this(BLUE, ruleValidator);
	}

	/**
	 * Construct a BetaHantoGame instance with the player who moves first being specified.
	 * @param movesFirst Color of the player who moves first.
	 */
	public GammaHantoGame(HantoPlayerColor movesFirst, HantoRuleValidator ruleValidator) {
		this.movesFirst = movesFirst;
		this.ruleValidator = ruleValidator;
		movesSecond = movesFirst == BLUE ? RED : BLUE;
		gameState = new GammaHantoGameState(movesFirst);
	}	
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		
		ruleValidator.validateRules(gameState, pieceType, from, to);
		
		moveCount++;
		
		// If moveCount is odd, it's the First Player (BLUE by default)'s turn
		HantoPlayerColor currentPlayer = moveCount % 2 == 1 ? movesFirst : movesSecond;
		
		if (!pieceTypeAvailable(pieceType)) {
			throw new HantoException("Only Butterflies and Sparrows are valid in Gamma Hanto");
		}
		
		if (moveCount > 1) {
			boolean placedButterfly = currentPlayer == BLUE ? 
					bluePlacedButterfly : redPlacedButterfly;
					
			if (placedButterfly && pieceType == BUTTERFLY) {
				throw new HantoException("Cannot place the second butterfly"); 
			}
		}
		
		HantoPiece newPiece = new HantoPieceImpl(currentPlayer, pieceType);
		gameState.putPieceAt(to, newPiece);
		gameState.advanceMove();
		
		if (pieceType == BUTTERFLY) {
			if (currentPlayer == BLUE) {
				bluePlacedButterfly = true;
				blueButterflyCoord = new HantoCoordinateImpl(to);
			}
			else {
				redPlacedButterfly = true;
				redButterflyCoord = new HantoCoordinateImpl(to);
			}
		}
		
		MoveResult moveResult = checkMoveResult();
		return moveResult;
	}
	
	
	/**
	 * Check the current board configuration after the latest move to
	 * decide the result of that move.
	 * @return Result of the latest move
	 */
	private MoveResult checkMoveResult() {
		int currentPlayerMoves = (moveCount + 1) / 2;
		int otherPlayerMoves = moveCount / 2;
		boolean blueWins = checkBlueWin();
		boolean redWins = checkRedWin();
		MoveResult moveResult;
		
		if (blueWins && redWins) {
			moveResult = DRAW;
		}
		else if (blueWins) {
			moveResult = BLUE_WINS;
		}
		else if (redWins) {
			moveResult = RED_WINS;
		}
		else if	(currentPlayerMoves == 6 && otherPlayerMoves == 6) {
			moveResult = DRAW;
		}
		else {
			moveResult = OK;
		}
		
		if (moveResult != OK) {
			gameOver = true;
		}
		
		return moveResult;
	}
	
	
	/**
	 * Check to see if blue wins.
	 * @return True if blue wins. False otherwise.
	 */
	private boolean checkBlueWin() {
		if (redButterflyCoord == null) {
			return false;
		}
		HantoCoordinateImpl[] adjCoords = redButterflyCoord.getAdjacentCoordsSet();
		for (int i = 0; i < 6; i++) {
			if (gameState.getPieceAt(adjCoords[i]) == null) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Check to see if red wins.
	 * @return True if red wins. False otherwise.
	 */	
	private boolean checkRedWin() {
		if (blueButterflyCoord == null) {
			return false;
		}
		HantoCoordinateImpl[] adjCoords = blueButterflyCoord.getAdjacentCoordsSet();
		for (int i = 0; i < 6; i++) {
			if (gameState.getPieceAt(adjCoords[i]) == null) {
				return false;
			}
		}
		return true;
	}
	
	
	private boolean pieceTypeAvailable(HantoPieceType pieceType) {
		if (pieceType != BUTTERFLY && pieceType != SPARROW) {
			return false;
		}
		return true;
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
		int maxR = Integer.MIN_VALUE, minR = Integer.MAX_VALUE;
		int maxC = Integer.MIN_VALUE, minC = Integer.MAX_VALUE;
		for (HantoCoordinate coord : board.keySet()) {
			maxR = Math.max(maxR, -(coord.getX() + 2 * coord.getY()));
			minR = Math.min(minR, -(coord.getX() + 2 * coord.getY()));
			maxC = Math.max(maxC, coord.getX());
			minC = Math.min(minC, coord.getX());
		}
		
		String hexes = "";
		
		for (int r = minR - 1; r <= maxR + 1; r++) {
			for (int c = minC - 1; c <= maxC + 1; c++) {
				if ((-r-c) % 2 == 0) {
					int coordX = c;
					int coordY = (-r - c) / 2;
					HantoPiece pc = board.get(new HantoCoordinateImpl(coordX, coordY));
					String pcString = "  ";
					if (pc != null) {
						pcString = getPieceString(pc);
						if (coordX == 0 && coordY == 0) {
							pcString = pcString.toUpperCase();
						}
					} 					
					hexes += " " + pcString + " ";
				}
				else {
					hexes += ">--<";
				}
			}
			hexes += "\n";
		}
		
		return hexes;
	}

	
	private String getPieceString(HantoPiece pc) {
		String pcstr = pc.getColor() == BLUE ? "b" : "r";
		switch (pc.getType()) {
			case BUTTERFLY: pcstr += "B";
			break;
			case SPARROW: pcstr += "S";
			break;
			/*case HORSE: pcstr += "H";
			break;
			case DOVE: pcstr += "D";
			break;
			case CRANE: pcstr += "R";
			break;
			case CRAB: pcstr += "C";
			break;*/
		}
		return pcstr;
	}
	
}

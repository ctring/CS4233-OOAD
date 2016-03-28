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
import hanto.studentctnguyendinh.common.HantoPieceImpl;

/**
 * <<Fill this in>>
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame
{
	private HantoPlayerColor movesFirst;
	private HantoPlayerColor movesSecond;
	private Map<HantoCoordinate, HantoPiece> board = new HashMap<>();
	
	private int moveCount = 0; 
	private boolean bluePlacedButterfly = false;
	private boolean redPlacedButterfly = false;
	private boolean gameOver = false;
	
	
	private HantoCoordinateImpl blueButterflyCoord;
	private HantoCoordinateImpl redButterflyCoord;
	
	public BetaHantoGame() {
		this(BLUE);
	}

	public BetaHantoGame(HantoPlayerColor movesFirst) {
		this.movesFirst = movesFirst;
		this.movesSecond = movesFirst == BLUE ? RED : BLUE;
	}	
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		if (gameOver) {
			throw new HantoException("Cannot make more moves after the game is finished");
		}
		
		moveCount++;
		
		// If moveCount is odd, it's the First Player (BLUE by default)'s turn
		HantoPlayerColor currentPlayer = moveCount % 2 == 1 ? movesFirst : movesSecond;
		HantoCoordinateImpl hexCoord = new HantoCoordinateImpl(to);
		int currentPlayerMoves = (moveCount + 1) / 2;
		int otherPlayerMoves = moveCount / 2;
		
		if (pieceType != BUTTERFLY && pieceType != SPARROW) {
			throw new HantoException("Only Butterflies and Sparrows are valid in Beta Hanto");
		}
		
		if (moveCount == 1) {
			if (to.getX() != 0 || to.getY() != 0) {
				throw new HantoException("First move must be the origin");
			}
		}
		else {
			boolean placedButterfly = currentPlayer == BLUE ? 
					bluePlacedButterfly : redPlacedButterfly;
					
			if (currentPlayerMoves == 4 && !placedButterfly && pieceType != BUTTERFLY) {
				throw new HantoException("A butterfly must be placed in the first four turn");
			}
			
			if (board.containsKey(hexCoord)) {
				throw new HantoException("Cannot place a piece on an occupied hex");
			}

			if (!isAdjacentToAny(hexCoord)) {
				throw new HantoException("The new piece must be ajacent to some pieces on the board");
			}
			
			if (placedButterfly && pieceType == BUTTERFLY) {
				throw new HantoException("Cannot place the second butterfly"); 
			}
		}
		
		board.put(hexCoord, new HantoPieceImpl(currentPlayer, pieceType));
		
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
		
		boolean blueWins = checkBlueWin();
		boolean redWins = checkRedWin();
		
		MoveResult moveResult;
		
		if (blueWins && redWins || 
				(currentPlayerMoves == 6 && otherPlayerMoves == 6)) {
			moveResult = DRAW;
		}
		else if (blueWins) {
			moveResult = BLUE_WINS;
		}
		else if (redWins) {
			moveResult = RED_WINS;
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
	 * Check if a hex coordinate is adjacent to some pieces on the board.
	 * @param coord Coordinate to be checked
	 * @return True if the given coordinate is adjacent to some pieces on the board. 
	 * False otherwise.
	 */
	private boolean isAdjacentToAny(HantoCoordinateImpl coord) {
		boolean isAdjacentToAny = false;
		for (int i = 0; i < 6; i++) {
			if (board.containsKey(coord.getAdjacentCoord(i))) {
				isAdjacentToAny = true;
				break;
			}
		}
		return isAdjacentToAny;
	}
	
	
	/**
	 * Check to see if blue wins.
	 * @return True if blue wins. False otherwise.
	 */
	private boolean checkBlueWin() {
		if (redButterflyCoord == null) {
			return false;
		}
		for (int i = 0; i < 6; i++) {
			if (!board.containsKey(redButterflyCoord.getAdjacentCoord(i))) {
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
		for (int i = 0; i < 6; i++) {
			if (!board.containsKey(blueButterflyCoord.getAdjacentCoord(i))) {
				return false;
			}
		}
		return true;
	}
	
	
	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where)
	{
		return board.get(new HantoCoordinateImpl(where));
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		// TODO Auto-generated method stub
		return null;
	}

}

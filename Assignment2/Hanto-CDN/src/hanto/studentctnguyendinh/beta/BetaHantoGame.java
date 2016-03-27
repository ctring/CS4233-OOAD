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

import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.HantoPieceType.*;

import java.util.HashMap;

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
	private HashMap<HantoCoordinate, HantoPiece> board = new HashMap<>();
	
	private int moveCount = 0; 
	private boolean bluePlacedButterfly = false;
	private boolean redPlacedButterfly = false;
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		moveCount++;
		
		// If moveCount is odd, it's the First Player (BLUE by default)'s turn
		HantoPlayerColor currentPlayer = moveCount % 2 == 1 ? BLUE : RED;
	
		if (moveCount == 1) {
			if (to.getX() != 0 || to.getY() != 0) {
				throw new HantoException("First move must be the origin");
			}
			board.put(new HantoCoordinateImpl(to), new HantoPieceImpl(currentPlayer, pieceType));
		}
		else {
			int moveOfCurrentPlayer = (moveCount + 1) / 2;
			if (moveOfCurrentPlayer == 4) {
				boolean placedButterfly = currentPlayer == BLUE ? 
						bluePlacedButterfly : redPlacedButterfly;
				
				if (!placedButterfly && pieceType != BUTTERFLY) {
					throw new HantoException(currentPlayer.toString() + 
							"must place butterfly on the fourth turn");
				}
			}
			
			HantoCoordinateImpl hexCoord = new HantoCoordinateImpl(to);
			
			if (board.containsKey(hexCoord)) {
				throw new HantoException("Cannot place a piece on an occupied hex");
			}

			if (!isAdjacentToAny(hexCoord)) {
				throw new HantoException("The new piece must be ajacent to some pieces on the board");
			}
			
			board.put(hexCoord, new HantoPieceImpl(currentPlayer, pieceType));
		}
		
		if (pieceType == BUTTERFLY) {
			if (currentPlayer == BLUE) {
				bluePlacedButterfly = true;
			}
			else {
				redPlacedButterfly = true;
			}
		}
		return OK;
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

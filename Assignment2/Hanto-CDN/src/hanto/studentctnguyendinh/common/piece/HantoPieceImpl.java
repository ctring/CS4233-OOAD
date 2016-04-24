/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentctnguyendinh.common.piece;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * Implementation of the HantoPiece.
 * 
 * @version Mar 2,2016
 */
public class HantoPieceImpl implements HantoPiece {

	HantoMovementRule validator;

	private final HantoPlayerColor color;
	private final HantoPieceType type;

	/**
	 * Default constructor
	 * 
	 * @param color
	 *            the piece color
	 * @param type
	 *            the piece type
	 */
	public HantoPieceImpl(HantoPlayerColor color, HantoPieceType type) {
		this(color, type, null);
	}

	/**
	 * Default constructor
	 * 
	 * @param color
	 *            the piece color
	 * @param type
	 *            the piece type
	 * @param validators
	 *            set of rule validators for this piece
	 */
	public HantoPieceImpl(HantoPlayerColor color, HantoPieceType type, HantoMovementRule validator) {
		this.color = color;
		this.type = type;
		this.validator = validator;
	}

	/*
	 * @see hanto.common.HantoPiece#getColor()
	 */
	@Override
	public HantoPlayerColor getColor() {
		return color;
	}

	/*
	 * @see hanto.common.HantoPiece#getType()
	 */
	@Override
	public HantoPieceType getType() {
		return type;
	}

	/**
	 * Validate movement rules of this piece based on the current game state and
	 * movement path of this piece.
	 * 
	 * @param gameState
	 *            current game state.
	 * @param from
	 *            coordinate where this piece begins.
	 * @param to
	 *            coordinate where this piece is after making its move.
	 * @throws HantoException
	 *             if the move is illegal.
	 */
	public void validateMove(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		if (validator != null) {
			String error = validator.validate(gameState, from, to);
			if (error != null) {
				throw new HantoException(error);
			}
		}
	}

	/**
	 * Generate a list of reachable coordinates according to the current state
	 * of the game and the rule assigned for this piece.
	 * 
	 * @param gameState
	 *            current state of the game.
	 * @param from
	 *            starting location of this piece.
	 */
	public List<HantoCoordinate> getReachableCoordinates(HantoGameState gameState, HantoCoordinate from) {
		if (validator != null) {
			return validator.getReachableCoordinates(gameState, from);
		}
		else {
			return new ArrayList<HantoCoordinate>();
		}
	}
}

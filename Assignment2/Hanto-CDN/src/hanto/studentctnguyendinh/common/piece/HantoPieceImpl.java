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

import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * Implementation of the HantoPiece.
 * @version Mar 2,2016
 */
public class HantoPieceImpl extends HantoPieceAbstract
{
	private final HantoPlayerColor color;
	private final HantoPieceType type;
	
	/**
	 * Default constructor
	 * @param color the piece color
	 * @param type the piece type
	 */
	public HantoPieceImpl(HantoPlayerColor color, HantoPieceType type)
	{
		this(color, type, null);
	}
	
	/**
	 * Default constructor
	 * @param color the piece color
	 * @param type the piece type
	 * @param validators set of rule validators for this piece
	 */
	public HantoPieceImpl(HantoPlayerColor color, HantoPieceType type, HantoMovementRule[] validators)
	{
		this.color = color;
		this.type = type;
		this.validators = validators;
	}
	/*
	 * @see hanto.common.HantoPiece#getColor()
	 */
	@Override
	public HantoPlayerColor getColor()
	{
		return color;
	}

	/*
	 * @see hanto.common.HantoPiece#getType()
	 */
	@Override
	public HantoPieceType getType()
	{
		return type;
	}
	
	@Override
	public void validateMove(HantoGameState gameState, 
			HantoCoordinate from, HantoCoordinate to) throws HantoException {
		if (validators != null) {
			for (HantoMovementRule validator : validators) {
				String error = validator.validate(gameState, from, to);
				if (error != null) {
					throw new HantoException(error);
				}
			}
		}
	}
}

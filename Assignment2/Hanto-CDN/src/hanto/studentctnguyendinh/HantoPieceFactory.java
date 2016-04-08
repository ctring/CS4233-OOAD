/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.piece.HantoMovementRule;
import hanto.studentctnguyendinh.common.piece.HantoPieceImpl;
import hanto.studentctnguyendinh.common.piece.MVBlockedPiece;
import hanto.studentctnguyendinh.common.piece.MVWalkOneHex;

/**
 * This is a singleton class that provides a factory to create a Hanto piece.
 * 
 * @author Cuong Nguyen
 * @version April 7, 2016
 *
 */
public class HantoPieceFactory {
	private static final HantoPieceFactory instance = new HantoPieceFactory();
	/**
	 * Default private descriptor.
	 */
	private HantoPieceFactory()
	{
		// Empty, but the private constructor is necessary for the singleton.
	}

	/**
	 * @return the instance
	 */
	public static HantoPieceFactory getInstance()
	{
		return instance;
	}
	
	/**
	 * Create a new HantoPiece.
	 * @param pieceType type piece.
	 * @param color color of the player making this piece.
	 * @return a new Hanto piece.
	 */
	public HantoPiece makeHantoPiece(HantoPlayerColor color, HantoPieceType pieceType)
	{
		List<HantoMovementRule> validators = new ArrayList<>();
		switch (pieceType) {
			case BUTTERFLY:
			case SPARROW:
				validators.add(new MVWalkOneHex());
				validators.add(new MVBlockedPiece());
				break;
		}

		return new HantoPieceImpl(color, pieceType, validators);
	}

}

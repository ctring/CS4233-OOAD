/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.piece;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * This rule checks if a piece attempts to walk when it is blocked.
 * @author Cuong Nguyen
 * @version April 6, 2016
 */
public class MVBlockedPiece implements HantoMovementRule {

	/**
	 * This assumes that it is valid to move to the destination.
	 */
	@Override
	public String validate(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to) {
		int normX = to.getX() - from.getX();
		int normY = to.getY() - from.getY();
		HantoCoordinateImpl coadjCoord1 = new HantoCoordinateImpl(
				from.getX() + normX + normY, from.getY() - normX);
		HantoCoordinateImpl coadjCoord2 = new HantoCoordinateImpl(
				from.getX() - normY, from.getY() + normX + normY);
		
		HantoPiece coadjPiece1 = gameState.getPieceAt(coadjCoord1);
		HantoPiece coadjPiece2 = gameState.getPieceAt(coadjCoord2);
		
		if (coadjPiece1 != null && coadjPiece2 != null) {
			return "Cannot move a blocked piece";
		}
		return null;
	}

	@Override
	public HantoCoordinate[] getReachableCoordinates(HantoGameState gameState, HantoCoordinate from) {
		// TODO Auto-generated method stub
		return null;
	}

}

/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.piece;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.studentctnguyendinh.common.HantoBoard;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * This rule checks for validity when jumping a piece.
 * 
 * @author Cuong Nguyen
 * @version April 28, 2016
 */
public class MVJumping implements HantoMovementRule {
	
	@Override
	public String validate(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to) {
		HantoCoordinateImpl fromCoord = new HantoCoordinateImpl(from);
		HantoCoordinateImpl toCoord = new HantoCoordinateImpl(to);
		
		if (fromCoord.alignedWith(toCoord) && fromCoord.getMinimumDistanceTo(toCoord) > 1 
				&& isConnectedLine(gameState, fromCoord, toCoord)) {
			return null;
		}
		return "Invalid jump";
	}
	
	private boolean isConnectedLine(HantoGameState gameState, HantoCoordinateImpl from, HantoCoordinateImpl to) {
		int distance = from.getMinimumDistanceTo(to);
		int incX = sign(to.getX() - from.getX());
		int incY = sign(to.getY() - from.getY());
		for (int i = 0; i < distance; i++) {
			int newX = from.getX() + incX * i;
			int newY = from.getY() + incY * i;
			HantoCoordinate newCoord = new HantoCoordinateImpl(newX, newY);
			if (gameState.getPieceAt(newCoord) == null) {
				return false;
			}
		}
		return true;
	}
	
	private int sign(int number) {
		return number < 0 ? -1 : (number > 0 ? 1 : 0);
	}

	@Override
	public List<HantoCoordinate> getReachableCoordinates(HantoGameState gameState, HantoCoordinate from) {
		HantoCoordinateImpl fromCoord = new HantoCoordinateImpl(from);
		HantoBoard board = gameState.cloneBoard();
		board.removePieceAt(fromCoord);
		List<HantoCoordinate> crude = board.getAdjacentHexes(null);
		List<HantoCoordinate> reachable = new ArrayList<>();
		for (HantoCoordinate coord : crude) {
			if (board.isContinuousAfter(coord) && validate(gameState, from, coord) == null) {
				reachable.add(coord);
			}
		}
		return reachable;
	}

}

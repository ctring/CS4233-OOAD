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
 * This rule checks for validity when flying a piece.
 * 
 * @author Cuong Nguyen
 * @version April 18, 2016
 */
public class MVFlying implements HantoMovementRule {

	private int maxSteps = Integer.MAX_VALUE;
	
	/**
	 * Default constructor.
	 */
	public MVFlying() {
	}

	/**
	 * Constructor that allows specifying maximum number of steps in a walk.
	 * 
	 * @param maxSteps
	 *            maximum number of steps for walking.
	 */
	public MVFlying(int maxSteps) {
		this.maxSteps = maxSteps;
	}
	
	@Override
	public String validate(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to) {
		HantoCoordinateImpl fromCoord = new HantoCoordinateImpl(from);
		HantoCoordinateImpl toCoord = new HantoCoordinateImpl(to);
		if (fromCoord.getMinimumDistanceTo(toCoord) > maxSteps) {
			return "Cannot fly further than " + maxSteps + " steps";
		}
		return null;
	}

	@Override
	public List<HantoCoordinate> getReachableCoordinates(HantoGameState gameState, HantoCoordinate from) {
		HantoCoordinateImpl fromCoord = new HantoCoordinateImpl(from);
		HantoBoard board = gameState.cloneBoard();
		board.removePieceAt(fromCoord);
		List<HantoCoordinate> crude = board.getAllAdjacentHexes();
		List<HantoCoordinate> reachable = new ArrayList<>();
		for (HantoCoordinate coord : crude) {
			if (board.isContinuousAfter(coord) && 
					fromCoord.getMinimumDistanceTo(coord) <= maxSteps && 
					fromCoord.getMinimumDistanceTo(coord) > 0) {
				reachable.add(coord);
			}
		}
		return reachable;
	}

}

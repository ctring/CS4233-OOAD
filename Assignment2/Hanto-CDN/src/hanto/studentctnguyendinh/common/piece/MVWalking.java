/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import hanto.common.HantoCoordinate;
import hanto.studentctnguyendinh.common.HantoBoard;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;
import hanto.studentctnguyendinh.common.HantoBoard.Cell;

/**
 * This rule ensures that the piece only walks one hex.
 * 
 * @author Cuong Nguyen
 * @version April 7, 2016
 */
public class MVWalking implements HantoMovementRule {

	private int maxSteps = Integer.MAX_VALUE;

	public MVWalking() {
	}

	public MVWalking(int maxSteps) {
		this.maxSteps = maxSteps;
	}

	@Override
	public String validate(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to) {
		if (new HantoCoordinateImpl(from).getMinimumDistanceTo(to) > maxSteps) {
			return "Cannot walk more than " + maxSteps + " hex(es)";
		}
		return null;
	}

	@Override
	public HantoCoordinate[] getReachableCoordinates(HantoGameState gameState, HantoCoordinate from) {
		HantoCoordinateImpl fromCoord = new HantoCoordinateImpl(from);

		HantoBoard board = gameState.cloneBoard();
		board.removePieceAt(from);

		int partitions = board.numberPartitions();

		ArrayList<HantoCoordinate> reachable = new ArrayList<>();
		LinkedList<HantoCoordinateImpl> queue = new LinkedList<>();

		board.setDataAt(fromCoord, new Integer(0));
		queue.push(fromCoord);

		while (!queue.isEmpty()) {
			HantoCoordinateImpl cur = queue.pop();
			Cell c = board.getCellAt(cur);
			int distance = c.data;
			if (distance > 0) {
				reachable.add(cur);
			}
			if (distance < maxSteps) {
				HantoCoordinateImpl[] adj = cur.getAdjacentCoordsSet();
				for (HantoCoordinateImpl coord : adj) {
					//if (convertDataToInt(board.getDataAt(coord)) == -1)
				}
			}
		}

		return null;
	}
	

}

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
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.studentctnguyendinh.common.HantoBoard;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

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
		return getReachableCoordinates(gameState.cloneBoard(), from);
	}	


	public HantoCoordinate[] getReachableCoordinates(HantoBoard board, HantoCoordinate from) {
		HantoCoordinateImpl fromCoord = new HantoCoordinateImpl(from);

		board.removePieceAt(from);

		int partitions = board.numberPartitions();
		ArrayList<HantoCoordinate> reachable = new ArrayList<>();
		LinkedList<HantoCoordinateImpl> queue = new LinkedList<>();
		Map<HantoCoordinateImpl, Boolean> checked = new HashMap<>();
		
		board.setDataAt(fromCoord, new Integer(0));
		checked.put(fromCoord, true);
		queue.push(fromCoord);

		while (!queue.isEmpty()) {
			HantoCoordinateImpl cur = queue.pop();
			int distance = board.getDataAt(cur);
			if (distance > 0) {
				reachable.add(cur);
			}
			if (distance < maxSteps) {
				HantoCoordinateImpl[] adj = cur.getAdjacentCoordsSet();
				for (HantoCoordinateImpl coord : adj) {
					if (checked.get(coord) == null &&
						pathIsNotBlocked(board, cur, coord) &&
						goodToPlace(board, partitions, coord)) {
						
						board.setDataAt(coord, distance + 1);
						checked.put(coord, true);
						queue.push(coord);
					}
				}
			}
		}
		HantoCoordinate[] ret = new HantoCoordinate[reachable.size()];
		reachable.toArray(ret);
		return ret;
	}
	
	private boolean goodToPlace(HantoBoard board, int partitions, HantoCoordinateImpl coord) {
		if (board.getPieceAt(coord) != null) {
			return false;
		}
		boolean isAdjacentToAny = false;
		HantoCoordinateImpl[] adj = coord.getAdjacentCoordsSet();
		for (HantoCoordinateImpl c : adj) {
			if (board.getPieceAt(c) != null) {
				isAdjacentToAny = true;
				break;
			}
		}
		if (!isAdjacentToAny) {
			return false;
		}
		boolean isContinuous = true;
		if (partitions > 1) {
			isContinuous = false;
			int parts = 0;
			for (HantoCoordinateImpl c : adj) {
				int p = board.getPartitionAt(c);
				if (p != 0) {
					if (parts == 0) {
						parts = p;
					} else {
						if (parts != p) {
							isContinuous = true;
							break;
						}
					}
				}
			}
		}
		if (!isContinuous) {
			return false;
		}
		
		return true;
	}

	private boolean pathIsNotBlocked(HantoBoard board, HantoCoordinate from, HantoCoordinate to) {
		int normX = to.getX() - from.getX();
		int normY = to.getY() - from.getY();
		HantoCoordinateImpl coadjCoord1 = new HantoCoordinateImpl(
				from.getX() + normX + normY, from.getY() - normX);
		HantoCoordinateImpl coadjCoord2 = new HantoCoordinateImpl(
				from.getX() - normY, from.getY() + normX + normY);
		
		HantoPiece coadjPiece1 = board.getPieceAt(coadjCoord1);
		HantoPiece coadjPiece2 = board.getPieceAt(coadjCoord2);
		
		return coadjPiece1 == null || coadjPiece2 == null;
	}
}

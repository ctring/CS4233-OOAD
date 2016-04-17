/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common;

import static hanto.common.HantoPlayerColor.BLUE;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;

/**
 * HantoBoard represents a board in the HantoGame.
 * @author Cuong Nguyen 
 * @version April 7, 2016
 */
public class HantoBoard {
	
	private Map<HantoCoordinateImpl, Cell> board = new HashMap<>();
	
	public HantoBoard() {

	}
	
	public HantoBoard(HantoBoard other) {
		board = new HashMap<HantoCoordinateImpl, Cell>(other.board);
	}
	
	/**
	 * Get a Hanto piece at a given coordinate.
	 * @param coord coordinate that needs retrieving a piece.
	 * @return a Hanto piece at the given coordinate. Null if there is none.
	 */
	public HantoPiece getPieceAt(HantoCoordinate coord) {
		Cell c = board.get(new HantoCoordinateImpl(coord));
		return c == null ? null : board.get(new HantoCoordinateImpl(coord)).piece;
	}
	
	/**
	 * Get partition number at a given coordinate.
	 * @param coord coordinate that needs retrieving partition number.
	 * @return partition number at the given coordinate. 0 if there is none.
	 */
	public int getParitionNumberAt(HantoCoordinate coord) {
		Cell c = board.get(new HantoCoordinateImpl(coord));
		return c == null ? 0 : board.get(new HantoCoordinateImpl(coord)).partition;
	}

	/**
	 * Put a Hanto piece at a given coordinate.
	 * @param coord coordinate of the new piece.
	 * @param piece piece to be placed.
	 */
	public void putPieceAt(HantoCoordinate coord, HantoPiece piece) {
		board.put(new HantoCoordinateImpl(coord), new Cell(piece));
	}
	
	/**
	 * Remove a piece at a given coordinate.
	 * @param coord coordinate of the piece being removed.
	 */
	public void removePieceAt(HantoCoordinate coord) {
		board.remove(new HantoCoordinateImpl(coord));
	}
	
	/**
	 * Move a piece between two coordinates.
	 * @param from coordinate of the piece to be moved.
	 * @param to destination coordinate.
	 */
	public void movePiece(HantoCoordinate from, HantoCoordinate to) {
		HantoPiece piece = getPieceAt(from);
		board.remove(new HantoCoordinateImpl(from));
		board.put(new HantoCoordinateImpl(to), new Cell(piece));
	}
	
	/**
	 * Check if the pieces are connected.
	 * @return true if every piece is connect, false otherwise.
	 */
	public boolean validateConnectivity() {
		int parts = numberPartitions();
		return parts <= 1;
	}
	
	/**
	 * Number the pieces inside a partition with the number of that partition. Normally,
	 * there is only one big partition. However, multiple partitions can exist in intermediate
	 * states of moving or when performing movement checking. 
	 * @return the number of partitions.
	 */
	public int numberPartitions() {
		LinkedList<HantoCoordinateImpl> queue = new LinkedList<>();
		int noPartitions = 0;
		
		for (Map.Entry<HantoCoordinateImpl, Cell> entry : board.entrySet()) {
			if (entry.getValue().partition == 0) {
				noPartitions++;
				entry.getValue().partition = noPartitions;
				queue.clear();
				queue.push(entry.getKey());
				
				while (!queue.isEmpty()) {
					HantoCoordinateImpl cur = queue.pop();

					HantoCoordinateImpl[] adj = cur.getAdjacentCoordsSet();
					for (HantoCoordinateImpl coord : adj) {
						Cell c = board.get(coord);
						if (c != null && c.partition == 0) {
							c.partition = noPartitions;
							queue.push(coord);
						}
					}
				}
			}
		}
		
		return noPartitions;
	}
	
	/**
	 * @return a string representing the current state of the board.
	 */
	public String getPrintableBoard() {
		int maxR = Integer.MIN_VALUE, minR = Integer.MAX_VALUE;
		int maxC = Integer.MIN_VALUE, minC = Integer.MAX_VALUE;
		for (HantoCoordinate coord : board.keySet()) {
			maxR = Math.max(maxR, -(coord.getX() + 2 * coord.getY()));
			minR = Math.min(minR, -(coord.getX() + 2 * coord.getY()));
			maxC = Math.max(maxC, coord.getX());
			minC = Math.min(minC, coord.getX());
		}
		
		String hexes = "";
		
		for (int r = minR - 1; r <= maxR + 1; r++) {
			for (int c = minC - 1; c <= maxC + 1; c++) {
				if ((-r-c) % 2 == 0) {
					int coordX = c;
					int coordY = (-r - c) / 2;
					HantoPiece pc = board.get(new HantoCoordinateImpl(coordX, coordY)).piece;
					String pcString = "  ";
					if (pc != null) {
						pcString = getPieceString(pc);
						if (coordX == 0 && coordY == 0) {
							pcString = pcString.toUpperCase();
						}
					} 					
					hexes += " " + pcString + " ";
				}
				else {
					hexes += ">--<";
				}
			}
			hexes += "\n";
		}
		
		return hexes;
	}
	
	private String getPieceString(HantoPiece pc) {
		String pcstr = pc.getColor() == BLUE ? "b" : "r";
		switch (pc.getType()) {
			case BUTTERFLY: pcstr += "B";
			break;
			case SPARROW: pcstr += "S";
			break;
			/*case HORSE: pcstr += "H";
			break;
			case DOVE: pcstr += "D";
			break;
			case CRANE: pcstr += "R";
			break;
			case CRAB: pcstr += "C";
			break;*/
		}
		return pcstr;
	}
	
	private static class Cell {
		HantoPiece piece;
		int partition = 0;
		
		Cell(HantoPiece piece) {
			this.piece = piece;
		}
	}
}

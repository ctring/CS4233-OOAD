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
 * 
 * @author Cuong Nguyen
 * @version April 7, 2016
 */
public class HantoBoard {

	private Map<HantoCoordinateImpl, Cell> board = new HashMap<>();
	private int partitions = 0;

	public HantoBoard() {

	}

	/**
	 * Copy constructor
	 * @param other a Hanto board to be copied.
	 */
	public HantoBoard(HantoBoard other) {
		board = new HashMap<HantoCoordinateImpl, Cell>(other.board);
	}

	/**
	 * Get a Hanto piece at a given coordinate.
	 * 
	 * @param coord
	 *            coordinate that needs retrieving a piece.
	 * @return a Hanto piece at the given coordinate. Null if there is none.
	 */
	public HantoPiece getPieceAt(HantoCoordinate coord) {
		Cell c = board.get(new HantoCoordinateImpl(coord));
		return c == null ? null : c.piece;
	}

	/**
	 * Get custom data associating with a cell.
	 * 
	 * @param coord
	 *            coordinate of the cell.
	 * @return custom data of the specified cell, 0 if data at the cell does not
	 *         exist.
	 */
	public int getDataAt(HantoCoordinate coord) {
		Cell inner = board.get(new HantoCoordinateImpl(coord));
		return inner == null ? 0 : inner.data;
	}

	/**
	 * Get partition number associating with a cell. This method should be
	 * called after calling the {@link #updatePartition() numberPartitions}
	 * method.
	 * 
	 * @param coord
	 *            coordinate of the cell.
	 * @return custom partition number of the specified cell, 0 if the cell does
	 *         not exist.
	 */
	public int getPartitionAt(HantoCoordinate coord) {
		Cell inner = board.get(new HantoCoordinateImpl(coord));
		return inner == null ? 0 : inner.partition;
	}

	/**
	 * Get a cell at a given coordinate. If the cell does not exist, create an
	 * empty one.
	 * 
	 * @param coord
	 *            coordinate of the cell.
	 * @return a cell at the given coordinate.
	 */
	private Cell getOrCreateCellAt(HantoCoordinate coord) {
		HantoCoordinateImpl innerCoord = new HantoCoordinateImpl(coord);
		Cell c = board.get(innerCoord);
		if (c == null) {
			c = new Cell();
			board.put(innerCoord, c);
		}
		return c;
	}

	/**
	 * Set custom data at a given coordinate.
	 * 
	 * @param coord
	 *            coordinate that needs setting custom data.
	 * @param data
	 *            custom data to be set.
	 */
	public void setDataAt(HantoCoordinate coord, int data) {
		Cell c = getOrCreateCellAt(coord);
		c.data = data;
	}

	/**
	 * Put a Hanto piece at a given coordinate.
	 * 
	 * @param coord
	 *            coordinate of the new piece.
	 * @param piece
	 *            piece to be placed.
	 */
	public void putPieceAt(HantoCoordinate coord, HantoPiece piece) {
		Cell c = getOrCreateCellAt(coord);
		c.piece = piece;
		updatePartition();
	}

	/**
	 * Remove a piece at a given coordinate.
	 * 
	 * @param coord
	 *            coordinate of the piece being removed.
	 */
	public void removePieceAt(HantoCoordinate coord) {
		board.remove(new HantoCoordinateImpl(coord));
		updatePartition();
	}

	/**
	 * Move a piece between two coordinates.
	 * 
	 * @param from
	 *            coordinate of the piece to be moved.
	 * @param to
	 *            destination coordinate.
	 */
	public void movePiece(HantoCoordinate from, HantoCoordinate to) {
		HantoPiece piece = getPieceAt(from);
		removePieceAt(new HantoCoordinateImpl(from));
		putPieceAt(new HantoCoordinateImpl(to), piece);
		updatePartition();
	}

	/**
	 * Check if the pieces are connected.
	 * 
	 * @return true if every piece is connect, false otherwise.
	 */
	public boolean validateConnectivity() {
		int parts = getNumberOfPartition();
		return parts <= 1;
	}

	/**
	 * Number the pieces inside a partition with the number of that partition.
	 * Normally, there is only one big partition. However, multiple partitions
	 * can exist in intermediate states of moving or when performing movement
	 * checking.
	 */
	public void updatePartition() {
		LinkedList<HantoCoordinateImpl> queue = new LinkedList<>();
		partitions = 0;
		Map<HantoCoordinateImpl, Boolean> checked = new HashMap<>();
		for (Map.Entry<HantoCoordinateImpl, Cell> entry : board.entrySet()) {
			if (checked.get(entry.getKey()) == null) {
				partitions++;
				entry.getValue().partition = partitions;
				checked.put(entry.getKey(), true);
				queue.clear();
				queue.push(entry.getKey());

				while (!queue.isEmpty()) {
					HantoCoordinateImpl cur = queue.pop();

					HantoCoordinateImpl[] adj = cur.getAdjacentCoordsSet();
					for (HantoCoordinateImpl coord : adj) {
						Cell c = board.get(coord);
						if (c != null && c.piece != null && checked.get(coord) == null) {
							c.partition = partitions;
							checked.put(coord, true);
							queue.push(coord);
						}
					}
				}
			}
		}
	}

	/**
	 * @return number of partitions.
	 */
	public int getNumberOfPartition() {
		return partitions;
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
				if ((-r - c) % 2 == 0) {
					int coordX = c;
					int coordY = (-r - c) / 2;
					HantoPiece pc = getPieceAt(new HantoCoordinateImpl(coordX, coordY));
					String pcString = "  ";
					if (pc != null) {
						pcString = getPieceString(pc);
						if (coordX == 0 && coordY == 0) {
							pcString = pcString.toUpperCase();
						}
					}
					hexes += " " + pcString + " ";
				} else {
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
		case BUTTERFLY:
			pcstr += "B";
			break;
		case SPARROW:
			pcstr += "S";
			break;
		case HORSE: 
			pcstr += "H";
			break;
		case CRAB:
			pcstr += "C";
			break;
		default:
			break;
		}
		return pcstr;
	}

	/**
	 * A cell contains information such as piece type, partition or custom data 
	 * associating with a hex on a Hanto board.
	 * @author Cuong
	 *
	 */
	static class Cell {
		private HantoPiece piece;
		private int partition;
		private int data;

		Cell() {
			this(null, 0, 0);
		}

		/**
		 * Create a new cell with given data.
		 * @param piece Hanto piece in this cell.
		 * @param partition partition number.
		 * @param data custom data.
		 */
		Cell(HantoPiece piece, int partition, int data) {
			this.piece = piece;
			this.partition = partition;
			this.data = data;
		}
		
	}
}

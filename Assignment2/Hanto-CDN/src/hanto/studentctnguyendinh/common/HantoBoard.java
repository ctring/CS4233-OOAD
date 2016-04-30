/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;

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
	 * 
	 * @param other
	 *            a Hanto board to be copied.
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
	private int getPartitionAt(HantoCoordinate coord) {
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
	}

	/**
	 * Check if the pieces are connected.
	 * 
	 * @return true if every piece is connect, false otherwise.
	 */
	public boolean isContinuous() {
		int parts = partitions;
		return parts <= 1;
	}

	/**
	 * Check if the pieces are connected after placing a piece.
	 * 
	 * @param coord
	 *            coordinate of the piece to be placed.
	 * 
	 * @return true if every piece is connected after placing the piece, false
	 *         otherwise.
	 */
	public boolean isContinuousAfter(HantoCoordinate coord) {
		HantoCoordinateImpl[] adj = new HantoCoordinateImpl(coord).getAdjacentCoordsSet();
		boolean[] mark = new boolean[] { false, false, false, false, false, false, false };
		boolean isContinuous = true;
		if (partitions > 1) {
			int parts = 0;
			for (HantoCoordinateImpl c : adj) {
				int p = getPartitionAt(c);
				if (p != 0 && mark[p] == false) {
					parts++;
				}
				mark[p] = true;
			}
			isContinuous = parts == partitions;
		}
		if (!isContinuous) {
			return false;
		}

		return true;
	}

	/**
	 * Number the pieces inside a partition with the number of that partition.
	 * Normally, there is only one big partition. However, multiple partitions
	 * can exist in intermediate states of moving or when performing movement
	 * checking.
	 */
	private void updatePartition() {
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
	 * Get a list of hexes that are adjacent to pieces with only the specified
	 * player's color, or to both player's color.
	 * 
	 * @param player
	 *            color of the player, enter null for both players.
	 * @return a list of hexes as described above.
	 */
	public List<HantoCoordinate> getAdjacentHexes(HantoPlayerColor player) {

		List<HantoCoordinate> pieces = getPiecesCoordinates(player);
		List<HantoCoordinate> adj = new ArrayList<>();

		for (HantoCoordinate c : pieces) {
			HantoCoordinateImpl coord = new HantoCoordinateImpl(c);
			for (HantoCoordinateImpl adjCoord : coord.getAdjacentCoordsSet()) {
				if (getPieceAt(adjCoord) == null && !adj.contains(adjCoord)
						&& coordIsAdjacentToColor(adjCoord, player)) {
					adj.add(adjCoord);
				}
			}
		}
		return adj;
	}

	/**
	 * Check if a coordinate is adjacent to a piece with the specified color.
	 * 
	 * @param coord
	 *            coordinate to be checked.
	 * @param color
	 *            color of the adjacent piece, enter null for both colors.
	 * @return true if the coordinate is next to a piece with given color, false
	 *         otherwise.
	 */
	public boolean coordIsAdjacentToColor(HantoCoordinate coord, HantoPlayerColor color) {
		HantoCoordinateImpl[] adjCoords = new HantoCoordinateImpl(coord).getAdjacentCoordsSet();

		boolean adjColor = false;
		boolean adjNonColor = false;

		for (HantoCoordinateImpl a : adjCoords) {
			HantoPiece piece = getPieceAt(a);
			if (piece != null) {
				if (color != null) {
					adjColor = adjColor || piece.getColor() == color;
					adjNonColor = adjNonColor || piece.getColor() != color;
				} else {
					return true;
				}
			}
		}
		return adjColor && !adjNonColor;
	}

	/**
	 * Get a list of coordinates of pieces on the board that belong to a player
	 * or to both players.
	 * 
	 * @param player
	 *            color of player that the pieces belongs to, use null for
	 *            getting pieces of both players.
	 * @return a list of coordinates of pieces on this board either belonging to
	 *         a player or to both players.
	 */
	public List<HantoCoordinate> getPiecesCoordinates(HantoPlayerColor player) {
		List<HantoCoordinate> coords = new ArrayList<>();
		for (HantoCoordinateImpl c : board.keySet()) {
			HantoPiece p = getPieceAt(c);
			if (p != null && (player == null || p.getColor() == player)) {
				coords.add(c);
			}
		}
		return coords;
	}

	private static final float MY_BUTTERFLY_SURROUNDING_WEIGHT = 2.0f;
	private static final float OTHER_BUTTERFLY_SURROUNDING_WEIGHT = 5.0f;
	private static final float MY_NEARNESS_WEIGHT = 1.0f;
	private static final float OTHER_NEARNESS_WEIGHT = 1.5f;
	private static final float SPARROW_WEIGHT = 3.0f;
	private static final float CRAB_WEIGHT = 0.25f;
	private static final float HORSE_WEIGHT = 0.5f;
	

	/**
	 * Evaluate how likely it is for a player to win.
	 * 
	 * @param player
	 * @return
	 */
	public float evaluateAIScore(HantoPlayerColor myColor) {
		HantoCoordinateImpl myButterfly = findButterfly(myColor);
		HantoCoordinateImpl otherButterfly = findButterfly(myColor == BLUE ? RED : BLUE);
		
		Random noise = new Random();
		return butterfliesSurroundingScore(myButterfly, otherButterfly)
				//+ butterfliesNearnessScore(myColor, myButterfly, otherButterfly)
				+ piecePresenceScore(myColor) 
				+ noise.nextFloat();
	}

	private HantoCoordinateImpl findButterfly(HantoPlayerColor playerColor) {
		for (HantoCoordinateImpl coord : board.keySet()) {
			HantoPiece p = getPieceAt(coord);
			if (p != null && p.getType() == BUTTERFLY && p.getColor() == playerColor) {
				return coord;
			}
		}
		return null;
	}

	private float butterfliesSurroundingScore(HantoCoordinateImpl myButterfly, HantoCoordinateImpl otherButterfly) {
		float score = 0;
		if (myButterfly != null) {
			for (HantoCoordinateImpl coord : myButterfly.getAdjacentCoordsSet()) {
				if (getPieceAt(coord) != null) {
					score -= MY_BUTTERFLY_SURROUNDING_WEIGHT;
				}
			}
		}
		if (otherButterfly != null) {
			for (HantoCoordinateImpl coord : otherButterfly.getAdjacentCoordsSet()) {
				if (getPieceAt(coord) != null) {
					score += OTHER_BUTTERFLY_SURROUNDING_WEIGHT;
				}
			}
		}
		return score;
	}

	private float piecePresenceScore(HantoPlayerColor myColor) {
		int countSparrow = 0;
		int countCrab = 0;
		int countHorse = 0;
		for (HantoCoordinateImpl coord : board.keySet()) {
			HantoPiece p = getPieceAt(coord);
			if (p != null) {
				switch (p.getType()) {
				case CRAB: countCrab++;
				break;
				case SPARROW: countSparrow++;
				break;
				case HORSE: countHorse++;
				break;
				default:
					break;
				}
			}
		}
		return SPARROW_WEIGHT * countSparrow + CRAB_WEIGHT * countCrab + HORSE_WEIGHT * countHorse;
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
	 * 
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
		 * 
		 * @param piece
		 *            Hanto piece in this cell.
		 * @param partition
		 *            partition number.
		 * @param data
		 *            custom data.
		 */
		Cell(HantoPiece piece, int partition, int data) {
			this.piece = piece;
			this.partition = partition;
			this.data = data;
		}

	}
}

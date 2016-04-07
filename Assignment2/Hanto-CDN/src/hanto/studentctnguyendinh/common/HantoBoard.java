package hanto.studentctnguyendinh.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;


public interface HantoBoard {
	/**
	 * Get a Hanto piece at a given coordinate.
	 * @param coord coordinate that needs retrieving a piece.
	 * @return a Hanto piece at the given coordinate. Null if there is none.
	 */
	public HantoPiece getPieceAt(HantoCoordinate coord);

	/**
	 * Put a Hanto piece at a given coordinate.
	 * @param coord coordinate of the new piece.
	 * @param piece piece to be placed.
	 */
	public void putPieceAt(HantoCoordinate coord, HantoPiece piece);
	
	/**
	 * Move a piece between two coordinates.
	 * @param from coordinate of the piece to be moved.
	 * @param to destination coordinate.
	 */
	public void movePiece(HantoCoordinate from, HantoCoordinate to);
	
	/**
	 * Check if the pieces are connected.
	 * @return true if every piece is connect, false otherwise.
	 */
	public boolean validateConnectivity();
	
	/**
	 * @return a string representing the current state of the board.
	 */
	public String getPrintableBoard();
}

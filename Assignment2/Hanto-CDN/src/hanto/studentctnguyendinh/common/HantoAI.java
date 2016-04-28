package hanto.studentctnguyendinh.common;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentctnguyendinh.common.piece.HantoPieceImpl;

public class HantoAI {
	
	private HantoGameState gameState;
	
	private HantoMoveRecord nextMove;
	
	public void setGameState(HantoGameState state) {
		gameState = state;
	}
	
	public void compute(MoveResult moveResult) {
		
		List<HantoMoveRecord> placingMove = getAllPlacingMoves();
		HantoBoard board = gameState.cloneBoard();
		List<HantoCoordinate> allCoord = 
				board.getPiecesCoordinates(gameState.getCurrentPlayer());
		
		for (HantoCoordinate c : allCoord) {
			HantoPieceImpl p = (HantoPieceImpl) board.getPieceAt(c);
			
		}
		
	}
	
	private List<HantoMoveRecord> getAllPlacingMoves() {
		List<HantoMoveRecord> moves = new ArrayList<>();
		
		HantoBoard board = gameState.cloneBoard();
		HantoPlayerColor currentPlayer = gameState.getCurrentPlayer();
		List<HantoCoordinate> placable;
		int playedMoves = gameState.getNumberOfPlayedMoves();
		if (playedMoves == 0) {
			placable = new ArrayList<>();
			placable.add(new HantoCoordinateImpl(0, 0));
		} 
//		else if (playedMoves == 1) {
//			placable = new ArrayList(new HantoCoordinateImpl(0, 0).getAdjacentCoordsSet());
//			break;
//		}
//		}
//		board.getAdjacentHexes(currentPlayer);
//		
//		
//		
//		return moves;
	}
	
	public HantoPieceType getPiece() {
		return nextMove.getPiece();
	}
	
	public HantoCoordinate getFrom() {
		return nextMove.getFrom();
	}
	
	public HantoCoordinate getTo() {
		return nextMove.getTo();
	}
	
	private static class HantoMoveRecord {
		private final HantoPieceType piece;
		private final HantoCoordinate from;
		private final HantoCoordinate to;
		
		/**
		 * Constructor. If the parameters are null, then this indicates a <em>resign</em>
		 * move.
		 * @param piece the Hanto piece type that moved
		 * @param from the source hex
		 * @param to the source hex
		 */
		public HantoMoveRecord(HantoPieceType piece, HantoCoordinate from, HantoCoordinate to)
		{
			this.piece = piece;
			this.from = from;
			this.to = to;
		}

		/**
		 * @return the piece
		 */
		public HantoPieceType getPiece()
		{
			return piece;
		}

		/**
		 * @return the from
		 */
		public HantoCoordinate getFrom()
		{
			return from;
		}

		/**
		 * @return the to
		 */
		public HantoCoordinate getTo()
		{
			return to;
		}
	}
}

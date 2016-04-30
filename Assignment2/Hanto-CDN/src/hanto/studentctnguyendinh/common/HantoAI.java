package hanto.studentctnguyendinh.common;

import static hanto.common.HantoPieceType.BUTTERFLY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentctnguyendinh.common.HantoGameState.HantoPlayerState;
import hanto.studentctnguyendinh.common.piece.HantoPieceImpl;

public class HantoAI {
	
	private HantoGameState gameState;
	
	private HantoMoveRecord nextMove;
	
	public void setGameState(HantoGameState state) {
		gameState = state;
	}
	
	public void compute(MoveResult moveResult) {
		
		List<HantoMoveRecord> allMoves = getAllMovingMoves();
		allMoves.addAll(getAllPlacingMoves());
		HantoMoveRecord selectedMove = null;
		
		selectedMove = getBestMove(allMoves);
		
		if (selectedMove != null) {
			nextMove = selectedMove;
		}
		else {
			// TODO: what to do here?
		}
	}
	
	private HantoMoveRecord getBestMove(List<HantoMoveRecord> moves) {
		HantoMoveRecord selectedMove = null;
		HantoPlayerColor currentPlayer = gameState.getCurrentPlayer();
		float maxScore = -Float.MAX_VALUE;
		
		for (HantoMoveRecord move : moves) {
			HantoBoard scratch = gameState.cloneBoard();
			if (move.getFrom() == null) {
				scratch.putPieceAt(move.getTo(), new HantoPieceImpl(currentPlayer, move.getPiece()));
			}
			else {
				scratch.movePiece(move.getFrom(), move.getTo());
			}
			float score = scratch.evaluateAIScore(currentPlayer);
			if (score > maxScore) {
				maxScore = score;
				selectedMove = move;
			}
		}
		
		// TODO: do some more checkings here
		
		return selectedMove;
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
		else if (playedMoves == 1) {
			placable = new ArrayList<>(Arrays.asList(
					new HantoCoordinateImpl(0, 0).getAdjacentCoordsSet()));
		}
		else {
			placable = board.getAdjacentHexes(currentPlayer);
		}
		
		HantoPlayerState player = gameState.getPlayerState(currentPlayer);
		
		if (playedMoves <= 1) {
			for (HantoCoordinate coord : placable) {
				moves.add(new HantoMoveRecord(BUTTERFLY, null, coord));
			}	
		}
		else {
			for (HantoPieceType piece: HantoPieceType.values()) {
				if (player.getNumberOfRemainingPieces(piece) > 0) {
					for (HantoCoordinate coord : placable) {
						moves.add(new HantoMoveRecord(piece, null, coord));
					}
				}
			}
		}
		
		return moves;
	}
	
	private List<HantoMoveRecord> getAllMovingMoves() {
		List<HantoMoveRecord> moves = new ArrayList<>();
		HantoBoard board = gameState.cloneBoard();
		List<HantoCoordinate> allCoord = 
				board.getPiecesCoordinates(gameState.getCurrentPlayer());
		
		for (HantoCoordinate from : allCoord) {
			HantoPieceImpl piece = (HantoPieceImpl) board.getPieceAt(from);
			
			List<HantoCoordinate> reachable = piece.getReachableCoordinates(gameState, from);
			for (HantoCoordinate to : reachable) {
				moves.add(new HantoMoveRecord(piece.getType(), from, to));
			}
		}

		return moves;
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

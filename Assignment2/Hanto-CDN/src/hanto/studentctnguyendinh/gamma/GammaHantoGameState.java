///*******************************************************************************
// * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
// * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
// * accompanying materials are made available under the terms of the Eclipse Public License
// * v1.0 which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// *******************************************************************************/
//package hanto.studentctnguyendinh.gamma;
//
//import static hanto.common.HantoPieceType.BUTTERFLY;
//import static hanto.common.HantoPlayerColor.BLUE;
//import static hanto.common.HantoPlayerColor.RED;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import hanto.common.HantoCoordinate;
//import hanto.common.HantoPiece;
//import hanto.common.HantoPieceType;
//import hanto.common.HantoPlayerColor;
//import hanto.studentctnguyendinh.common.HantoBoard;
//import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
//import hanto.studentctnguyendinh.common.HantoGameState;
//
///**
// * The GammaHantoGameState contains the state and methods used to changed the state of 
// * a Gamma Hanto Game.
// * 
// * @author Cuong Nguyen
// * @version April 7, 2016
// */
//public class GammaHantoGameState implements HantoGameState {
//	
//	private GammaHantoBoard board = new GammaHantoBoard();
//	
//	private HantoPlayerColor movesFirst;
//	private HantoPlayerColor movesSecond;
//	
//	private int currentPlayer = 0;	// 0 = first player, 1 = second player
//	
//	private int moveCount = 0; 
//	private boolean gameOver = false;
//	
//	private GammaHantoPlayerState bluePlayerState;
//	private GammaHantoPlayerState redPlayerState;
//	
//	/**
//	 * Create a game state with give first-move player and quota of the pieces.
//	 * @param movesFirst color of the player who moves first.
//	 * @param piecesQuota quota for the pieces.
//	 */
//	protected GammaHantoGameState(HantoPlayerColor movesFirst, Map<HantoPieceType, Integer> piecesQuota) {
//		this.movesFirst = movesFirst;
//		movesSecond = movesFirst == BLUE ? RED : BLUE;
//		bluePlayerState = new GammaHantoPlayerState(new HashMap<HantoPieceType, Integer>(piecesQuota));
//		redPlayerState = new GammaHantoPlayerState(new HashMap<HantoPieceType, Integer>(piecesQuota));
//	}
//	
//	/**
//	 * Increase the number of moves.
//	 */
//	protected void advanceMove() {
//		moveCount++;
//		currentPlayer = 1 - currentPlayer;
//	}
//	
//	/**
//	 * Turn on the game over flag.
//	 */
//	protected void flagGameOver() {
//		gameOver = true;
//	}
//	
//	/**
//	 * Put a piece at a specified location on the board.
//	 * @param coord coordinate of the location of the new piece.
//	 * @param piece a new piece to be put on the board.
//	 */
//	protected void putPieceAt(HantoCoordinate coord, HantoPiece piece) {
//		HantoCoordinateImpl innerCoord = new HantoCoordinateImpl(coord);
//		HantoPieceType pieceType = piece.getType();
//		
//		board.putPieceAt(innerCoord, piece);
//		
//		if (piece.getColor() == BLUE) {
//			int rem = bluePlayerState.getNumberOfRemainingPieces(pieceType);
//			bluePlayerState.setNumberOfRemainingPieces(pieceType, rem - 1);
//			if (piece.getType() == BUTTERFLY) {
//				bluePlayerState.setButterflyCoordinate(innerCoord); 
//			}
//		}
//		else {
//			int rem = redPlayerState.getNumberOfRemainingPieces(pieceType);
//			redPlayerState.setNumberOfRemainingPieces(pieceType, rem - 1);
//			if (piece.getType() == BUTTERFLY) {
//				redPlayerState.setButterflyCoordinate(innerCoord);
//			}	
//		}
//	}
//	
//	/**
//	 * Move a piece between two coordinates on the board.
//	 * @param from coordinate of the hex containing the piece.
//	 * @param to destination of the piece.
//	 */
//	protected void movePiece(HantoCoordinate from, HantoCoordinate to) {
//		board.movePiece(from, to);
//	}
//	
//	protected String getPrintableBoard() {
//		return board.getPrintableBoard();
//	}
//	
//	@Override
//	public HantoPiece getPieceAt(HantoCoordinate coord) {
//		return board.getPieceAt(coord);
//	}
//	
//	@Override
//	public int getNumberOfPlayedMoves() {
//		return moveCount;
//	}
//	
//	@Override
//	public boolean isGameOver() {
//		return gameOver;
//	}
//
//	@Override 
//	public HantoPlayerState getPlayerState(HantoPlayerColor player) {
//		return player == BLUE ? bluePlayerState : redPlayerState;
//	}
//	
//	@Override
//	public HantoPlayerColor getCurrentPlayer() {
//		return currentPlayer == 0 ? movesFirst : movesSecond;
//	}
//	
//	@Override 
//	public HantoBoard cloneBoard() {
//		return board.makeCopy();
//	}
//	
//	/**
//	 * The GammaHantoPlayerState contains the information of each player at a certain
//	 * moment in the game.
//	 * 
//	 * @author Cuong Nguyen
//	 * @version April 6, 2016
//	 *
//	 */
//	protected class GammaHantoPlayerState implements HantoGameState.HantoPlayerState {
//
//		HantoCoordinate butterflyCoord = null;
//		Map<HantoPieceType, Integer> remaining = new HashMap<>();
//		
//		/**
//		 * Create a new player state with a given piece quota. 
//		 * @param pieceQuota quota of the pieces for each player.
//		 */
//		GammaHantoPlayerState(Map<HantoPieceType, Integer> pieceQuota) {
//			remaining = pieceQuota;
//		}
//		
//		/**
//		 * @return coordinate of the butterfly of this player. 
//		 */
//		public HantoCoordinate getButterflyCoordinate() {
//			return butterflyCoord;
//		}
//		
//		protected void setButterflyCoordinate(HantoCoordinate coord) {
//			butterflyCoord = coord;
//		}
//		
//		/**
//		 * @param pieceType type of piece in question. 
//		 * @return number of remaining pieces of the given type.
//		 */
//		public int getNumberOfRemainingPieces(HantoPieceType pieceType) {
//			Integer rem = remaining.get(pieceType);	
//			return rem == null ? 0 : rem.intValue();
//		}
//		
//		/**
//		 * Set the number of remaining pieces by a new value.
//		 * @param pieceType type of the piece need to be set.
//		 * @param newVal new value for the number of the remaining piece.
//		 */
//		protected void setNumberOfRemainingPieces(HantoPieceType pieceType, int newVal) {
//			remaining.put(pieceType, newVal);
//		}
//	}
//	
//}

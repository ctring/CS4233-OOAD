package hanto.studentctnguyendinh.gamma;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

public class GammaHantoGameState implements HantoGameState {
	
	private Map<HantoCoordinateImpl, HantoPiece> board = new HashMap<>();
	
	private HantoPlayerColor movesFirst;
	private HantoPlayerColor movesSecond;
	
	private int currentPlayer = 0;	// 0 = first player, 1 = second player
	
	private int moveCount = 0; 
	private boolean gameOver = false;
	
	private GammaHantoPlayerState bluePlayerState;
	private GammaHantoPlayerState redPlayerState;
	
	protected GammaHantoGameState(HantoPlayerColor movesFirst, Map<HantoPieceType, Integer> piecesQuota) {
		this.movesFirst = movesFirst;
		this.movesSecond = movesFirst == BLUE ? RED : BLUE;
		bluePlayerState = new GammaHantoPlayerState(new HashMap<HantoPieceType, Integer>(piecesQuota));
		redPlayerState = new GammaHantoPlayerState(new HashMap<HantoPieceType, Integer>(piecesQuota));
	}
	
	protected void advanceMove() {
		moveCount++;
		currentPlayer = 1 - currentPlayer;
	}
	
	protected void setGameOver() {
		gameOver = true;
	}
	
	protected void movePiece(HantoCoordinate from, HantoCoordinate to) {
		HantoPiece piece = getPieceAt(from);
		board.remove(new HantoCoordinateImpl(from));
		board.put(new HantoCoordinateImpl(to), piece);
	}
	
	protected void putPieceAt(HantoCoordinate coord, HantoPiece piece) {
		HantoCoordinateImpl innerCoord = new HantoCoordinateImpl(coord);
		HantoPieceType pieceType = piece.getType();
		board.put(innerCoord, piece);
		if (piece.getColor() == BLUE) {
			int rem = bluePlayerState.getNumberOfRemainingPieces(pieceType);
			bluePlayerState.setNumberOfRemainingPieces(pieceType, rem - 1);
			if (piece.getType() == BUTTERFLY) {
				bluePlayerState.setButterflyCoordinate(innerCoord); 
			}
		}
		else {
			int rem = redPlayerState.getNumberOfRemainingPieces(pieceType);
			redPlayerState.setNumberOfRemainingPieces(pieceType, rem - 1);
			if (piece.getType() == BUTTERFLY) {
				redPlayerState.setButterflyCoordinate(innerCoord);
			}	
		}
	}
	
	@Override
	public HantoPiece getPieceAt(HantoCoordinate coord) {
		return board.get(new HantoCoordinateImpl(coord));
	}
	
	@Override
	public int getNumberOfPlayedMoves() {
		return moveCount;
	}
	
	@Override
	public boolean isGameOver() {
		return gameOver;
	}

	@Override 
	public HantoPlayerState getPlayerState(HantoPlayerColor player) {
		return player == BLUE ? bluePlayerState : redPlayerState;
	}
	
	@Override
	public HantoPlayerColor getMoveFirstPlayer() {
		return movesFirst;
	}
	
	@Override
	public HantoPlayerColor getMoveSecondPlayer() {
		return movesSecond;
	}

	@Override
	public HantoPlayerColor getCurrentPlayer() {
		return currentPlayer == 0 ? movesFirst : movesSecond;
	}
	
	protected class GammaHantoPlayerState implements HantoGameState.HantoPlayerState {

		HantoCoordinate butterflyCoord = null;
		Map<HantoPieceType, Integer> remaining = new HashMap<>();
		
		GammaHantoPlayerState(Map<HantoPieceType, Integer> initialVal) {
			remaining = initialVal;
		}
		
		/**
		 * @return coordinate of the butterfly of this player. 
		 */
		public HantoCoordinate getButterflyCoordinate() {
			return butterflyCoord;
		}
		
		protected void setButterflyCoordinate(HantoCoordinate coord) {
			butterflyCoord = coord;
		}
		
		/**
		 * @param pieceType type of piece in question. 
		 * @return number of remaining pieces of the given type.
		 */
		public int getNumberOfRemainingPieces(HantoPieceType pieceType) {
			Integer rem = remaining.get(pieceType);	
			return rem == null ? 0 : rem.intValue();
		}
		
		protected void setNumberOfRemainingPieces(HantoPieceType pieceType, int newVal) {
			remaining.put(pieceType, newVal);
		}
	}
	
	protected String getPrintableBoard()
	{
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
					HantoPiece pc = board.get(new HantoCoordinateImpl(coordX, coordY));
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
	
}

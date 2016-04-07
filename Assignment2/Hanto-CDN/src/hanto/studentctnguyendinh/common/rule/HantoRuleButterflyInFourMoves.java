package hanto.studentctnguyendinh.common.rule;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.BLUE;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.HantoGameState;

public class HantoRuleButterflyInFourMoves implements HantoRule {
	
	@Override
	public String validateRule(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) {
		HantoPlayerColor currentPlayer = gameState.getCurrentPlayer();
		boolean placedButterfly = (currentPlayer == BLUE ? gameState.getBlueButterflyCoord() : 
			gameState.getRedButterflyCoord()) != null;
				
		int currentPlayerMoves = gameState.getNumberOfPlayedMoves() / 2 + 1;
		if (currentPlayerMoves == 4 && !placedButterfly && pieceType != BUTTERFLY) {
			return "A butterfly must be placed in the first four turn";
		}
		return null;
	}
}

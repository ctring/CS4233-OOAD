package hanto.studentctnguyendinh.common.rule;

import static hanto.common.HantoPieceType.BUTTERFLY;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.studentctnguyendinh.common.HantoGameState;

public class HantoRuleButterflyInFourMoves implements HantoRule {
	
	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) {
		HantoGameState.HantoPlayerState currentPlayerState = gameState.getPlayerState(gameState.getCurrentPlayer());
		boolean placedButterfly = currentPlayerState.getButterflyCoordinate() != null;
				
		int currentPlayerMoves = gameState.getNumberOfPlayedMoves() / 2 + 1;
		if (currentPlayerMoves == 4 && !placedButterfly && pieceType != BUTTERFLY) {
			return "A butterfly must be placed in the first four turn";
		}
		return null;
	}
}

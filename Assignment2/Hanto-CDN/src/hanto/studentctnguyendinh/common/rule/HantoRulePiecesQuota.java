package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.studentctnguyendinh.common.HantoGameState;

public class HantoRulePiecesQuota implements HantoRule {
	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) {
		if (from == null) {
			HantoGameState.HantoPlayerState currentPlayerState = gameState.getPlayerState(gameState.getCurrentPlayer());
			if (currentPlayerState.getNumberOfRemainingPieces(pieceType) <= 0) {
				return "Run out of pieces type " + pieceType.getPrintableName();
			} else {
				return null;
			}
		}
		return null;
	}
}

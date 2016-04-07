package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.HantoGameState;

public class HantoRuleInputConsistency implements HantoRule {

	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		if (from != null) {
			HantoPiece piece = gameState.getPieceAt(from);
			HantoPlayerColor currentPlayer = gameState.getCurrentPlayer();
			if (piece == null || piece.getType() != pieceType 
					|| piece.getColor() != currentPlayer) {
				return "There is no piece with specified type or color at the given coordinate";
			}
		}
		return null;
	}

}

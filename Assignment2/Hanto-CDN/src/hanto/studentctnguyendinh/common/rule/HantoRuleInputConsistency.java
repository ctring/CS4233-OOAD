package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.studentctnguyendinh.common.HantoGameState;

public class HantoRuleInputConsistency implements HantoRule {

	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		if (from != null) {
			HantoPiece piece = gameState.getPieceAt(from);
			if (piece == null || piece.getType() != pieceType) {
				return "There is no piece with specified type at the given coordinate";
			}
		}
		return null;
	}

}

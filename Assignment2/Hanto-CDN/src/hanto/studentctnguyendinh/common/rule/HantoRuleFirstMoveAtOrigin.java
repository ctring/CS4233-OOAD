package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.studentctnguyendinh.common.HantoGameState;

public class HantoRuleFirstMoveAtOrigin implements HantoRule {
	
	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) {
		if (gameState.getNumberOfPlayedMoves() == 0 && (to.getX() != 0 || to.getY() != 0)) {
			return "First move must be the origin";
		}
		else {
			return null;
		}
	}
}


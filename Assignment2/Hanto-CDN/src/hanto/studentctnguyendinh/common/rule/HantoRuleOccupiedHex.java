package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.studentctnguyendinh.common.HantoGameState;

public class HantoRuleOccupiedHex implements HantoRule {

	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		return gameState.getBoard().getPieceAt(to) != null ? 
				"Cannot place a piece on an occupied hex" : null;
	}

}

package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.studentctnguyendinh.common.HantoGameState;

public interface HantoRule {
	String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from, 
			HantoCoordinate to);
}

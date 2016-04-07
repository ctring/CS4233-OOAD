package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.studentctnguyendinh.common.HantoGameState;

public interface HantoRule {
	String validateRule(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from, 
			HantoCoordinate to);
}

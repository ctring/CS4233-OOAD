package hanto.studentctnguyendinh.common.piece;

import hanto.common.HantoCoordinate;
import hanto.studentctnguyendinh.common.HantoGameState;

public interface HantoMoveValidator {
	String validate(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to);
}

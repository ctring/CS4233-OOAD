package hanto.studentctnguyendinh.common.piece;

import hanto.common.HantoCoordinate;

public interface HantoMoveValidator {
	boolean canMove(HantoCoordinate from, HantoCoordinate to);
}

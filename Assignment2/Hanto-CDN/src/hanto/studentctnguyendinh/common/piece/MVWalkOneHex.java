package hanto.studentctnguyendinh.common.piece;

import hanto.common.HantoCoordinate;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

public class MVWalkOneHex implements HantoMoveValidator {

	@Override
	public String validate(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to) {
		if (new HantoCoordinateImpl(from).getMinimumDistanceTo(to) > 1)
			return "Cannot walk more than one hex";
		return null;
	}

}

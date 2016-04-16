package hanto.studentctnguyendinh.common.piece;

import hanto.common.HantoCoordinate;
import hanto.studentctnguyendinh.common.HantoGameState;

public class MVFlyStraightLine implements HantoMovementRule {

	@Override
	public String validate(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to) {
		if ((from.getX() == to.getX()) ||
				(from.getY() == to.getY()) ||
				(from.getX() - to.getX() == to.getY() - from.getY())) {
			return null;
		}
		else {
			return "Flight path must be a straight line";
		}
	}

}

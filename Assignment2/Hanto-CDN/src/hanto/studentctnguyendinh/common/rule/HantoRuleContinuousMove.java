package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

public class HantoRuleContinuousMove implements HantoRule {

	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		if (from != null) {
			HantoCoordinateImpl oldc = new HantoCoordinateImpl(from);
			HantoCoordinateImpl newc = new HantoCoordinateImpl(to);
			HantoCoordinateImpl[] adjOld = oldc.getAdjacentCoordsSet();
			int numOfAdjacentHexes = HantoCoordinateImpl.NUMBER_OF_ADJACENT_HEXES;
			boolean[] occupied = new boolean[numOfAdjacentHexes];
			
			for (int i = 0; i < numOfAdjacentHexes; i++) {
				occupied[i] = gameState.getPieceAt(adjOld[i]) != null || newc.equals(adjOld[i]);
			}
			
			int start = -1;
			if (occupied[0] && !occupied[numOfAdjacentHexes - 1]) start = 0;
			for (int i = 1; i < numOfAdjacentHexes; i++) {
				if (occupied[i] && !occupied[i - 1]) {
					if (start != -1) {
						return "Cannot make a discontinuous move";
					}
					else {
						start = i;
					}
				}
			}
		}
		return null;
	}

}

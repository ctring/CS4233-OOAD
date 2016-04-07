package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

public class HantoRuleNotAdjacent implements HantoRule {

	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		
		if (gameState.getNumberOfPlayedMoves() == 0) {
			return null;
		}
		
		boolean isAdjacentToAny = false;
		HantoCoordinateImpl coord = new HantoCoordinateImpl(to);
		HantoCoordinateImpl[] adjCoords = coord.getAdjacentCoordsSet();
		for (int i = 0; i < 6; i++) {
			if (gameState.getPieceAt(adjCoords[i]) != null) {
				isAdjacentToAny = true;
				break;
			}
		}
		
		return !isAdjacentToAny ? "The new piece must be ajacent to some pieces on the board" : null;
	}
	
}

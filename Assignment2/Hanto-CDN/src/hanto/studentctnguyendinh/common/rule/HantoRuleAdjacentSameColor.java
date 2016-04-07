package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

public class HantoRuleAdjacentSameColor implements HantoRule {

	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		if (from == null && gameState.getNumberOfPlayedMoves() >= 2) {
			HantoPlayerColor currentPlayer = gameState.getCurrentPlayer();
			HantoCoordinateImpl[] adjCoords = new HantoCoordinateImpl(to).getAdjacentCoordsSet();
			for (HantoCoordinateImpl coord : adjCoords) {
				HantoPiece piece = gameState.getPieceAt(coord);
				if (piece != null) {
					if (piece.getColor() != currentPlayer) {
						return "Cannot place next to an opponent piece";
					}
				}
			}
		}
		return null;
	}

}

package hanto.studentctnguyendinh.common.piece;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

public class MVBlockedPiece implements HantoMoveValidator {

	/**
	 * This assumes that it is valid to move to the desination hex.
	 */
	@Override
	public String validate(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to) {
		int normX = to.getX() - from.getX();
		int normY = to.getY() - from.getY();
		HantoCoordinateImpl coadjCoord1 = new HantoCoordinateImpl(
				from.getX() + normX + normY, from.getY() - normX);
		HantoCoordinateImpl coadjCoord2 = new HantoCoordinateImpl(
				from.getX() - normY, from.getY() + normX + normY);
		
		HantoPiece coadjPiece1 = gameState.getPieceAt(coadjCoord1);
		HantoPiece coadjPiece2 = gameState.getPieceAt(coadjCoord2);
		
		if (coadjPiece1 != null && coadjPiece2 != null) {
			return "Cannot move a blocked piece";
		}
		return null;
	}

}

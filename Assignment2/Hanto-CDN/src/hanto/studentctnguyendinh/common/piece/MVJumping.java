package hanto.studentctnguyendinh.common.piece;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.studentctnguyendinh.common.HantoBoard;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

public class MVJumping implements HantoMovementRule {
	
	@Override
	public String validate(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to) {
		HantoCoordinateImpl fromCoord = new HantoCoordinateImpl(from);
		HantoCoordinateImpl toCoord = new HantoCoordinateImpl(to); 
		if (!fromCoord.alignedWith(toCoord)) {
			return "Jumping path must be a straight line";
		}
		return null;
	}

	@Override
	public List<HantoCoordinate> getReachableCoordinates(HantoGameState gameState, HantoCoordinate from) {
		HantoCoordinateImpl fromCoord = new HantoCoordinateImpl(from);
		HantoBoard board = gameState.cloneBoard();
		board.removePieceAt(fromCoord);
		List<HantoCoordinate> crude = board.getAllAdjacentHexes();
		List<HantoCoordinate> reachable = new ArrayList<>();
		for (HantoCoordinate coord : crude) {
			if (board.isContinuousAfter(coord) && fromCoord.alignedWith(coord)) {
				reachable.add(coord);
			}
		}
		return reachable;
	}

}

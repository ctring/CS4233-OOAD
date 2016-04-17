package hanto.studentctnguyendinh.common.piece;

import hanto.common.HantoCoordinate;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

public class MVFlying implements HantoMovementRule {

	private int maxSteps = Integer.MAX_VALUE;
	
	public MVFlying() {}
	
	public MVFlying(int maxSteps) {
		this.maxSteps = maxSteps;
	}
	
	@Override
	public String validate(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to) {
		HantoCoordinateImpl fromCoord = new HantoCoordinateImpl(from);
		HantoCoordinateImpl toCoord = new HantoCoordinateImpl(to); 
		if (fromCoord.getMinimumDistanceTo(toCoord) > maxSteps) {
			return "Cannot fly further than " + maxSteps + " steps";
		}
		return null;
	}

	@Override
	public HantoCoordinate[] getReachableCoordinates(HantoGameState gameState, HantoCoordinate from) {
		// TODO Auto-generated method stub
		return null;
	}

}

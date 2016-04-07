package hanto.studentctnguyendinh.common.rule;

import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.*;

import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

public class HantoEndRuleButterflyIsSurrounded implements HantoEndRule {

	@Override
	public MoveResult checkResult(HantoGameState state) {
		boolean redButterflySurrounded = checkButterflySurrounded(state, RED);
		boolean blueButterflySurrounded = checkButterflySurrounded(state, BLUE);
		if (redButterflySurrounded && blueButterflySurrounded) {
			return DRAW;
		}
		else if (redButterflySurrounded) {
			return BLUE_WINS;
		}
		else if (blueButterflySurrounded) {
			return RED_WINS;
		}
		return OK;
	}
	
	private boolean checkButterflySurrounded(HantoGameState gameState, HantoPlayerColor player) {
		if (gameState.getPlayerState(player).getButterflyCoordinate() == null) {
			return false;
		}
		HantoCoordinateImpl butterflyCoord = new HantoCoordinateImpl(
				gameState.getPlayerState(player).getButterflyCoordinate());
		
		HantoCoordinateImpl[] adjCoords = butterflyCoord.getAdjacentCoordsSet();
		for (int i = 0; i < 6; i++) {
			if (gameState.getPieceAt(adjCoords[i]) == null) {
				return false;
			}
		}
		return true;
	}

}

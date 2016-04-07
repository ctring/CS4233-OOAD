package hanto.studentctnguyendinh.common.rule;

import static hanto.common.MoveResult.*;

import hanto.common.MoveResult;
import hanto.studentctnguyendinh.common.HantoGameState;

public class HantoEndRuleMaxNumberOfMoves implements HantoEndRule {

	private final int MAX_NUMBER_OF_MOVE = 40;
	
	@Override
	public MoveResult checkResult(HantoGameState gameState) {
		if (gameState.getNumberOfPlayedMoves() >= MAX_NUMBER_OF_MOVE) {
			return DRAW;
		}
		return OK;
	}

}

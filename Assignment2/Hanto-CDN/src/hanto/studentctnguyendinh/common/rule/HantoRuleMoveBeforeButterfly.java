package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * This rule prevents movements that are made before the placer placing a butterfly.
 * @author Cuong Nguyen
 * @version April 7, 2016
 *
 */
public class HantoRuleMoveBeforeButterfly implements HantoRule {

	@Override
	public String validate(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		if (from != null) {
			HantoPlayerColor currentPlayer = gameState.getCurrentPlayer();
			if (gameState.getPlayerState(currentPlayer).getButterflyCoordinate() == null) {
				return "Cannot move before placing Butterfly";
			}
		}
		return null;
	}

}

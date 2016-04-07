package hanto.studentctnguyendinh.common.rule;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.MoveResult;
import hanto.studentctnguyendinh.common.HantoGameState;

public interface HantoRuleValidator {

	public void validateRules(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,	
			HantoCoordinate to) throws HantoException;
	
	public MoveResult validateEndRules(HantoGameState gameState); 
}

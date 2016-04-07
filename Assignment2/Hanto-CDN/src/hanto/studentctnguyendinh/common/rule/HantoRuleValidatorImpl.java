package hanto.studentctnguyendinh.common.rule;

import static hanto.common.MoveResult.*;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.MoveResult;
import hanto.studentctnguyendinh.common.HantoGameState;

public class HantoRuleValidatorImpl implements HantoRuleValidator {

	List<HantoRule> ruleList;
	List<HantoEndRule> endRuleList;
	
	public HantoRuleValidatorImpl(List<HantoRule> ruleList, List<HantoEndRule> endRuleList) {
		this.ruleList = new ArrayList<>(ruleList);
		this.endRuleList = new ArrayList<>(endRuleList);
	}
	
	@Override
	public void validateRules(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,	HantoCoordinate to) throws HantoException {
		for (HantoRule rule : ruleList) {
			String error = rule.validateRule(gameState, pieceType, from, to);
			if (error != null) {
				throw new HantoException(error);
			}
		} 
	}
	
	@Override
	public MoveResult validateEndRules(HantoGameState gameState) {
		for (HantoEndRule rule : endRuleList) {
			MoveResult result = rule.validateRule(gameState);
			if (result != OK) {
				return result; 
			}
		}
		return OK;
	}
}

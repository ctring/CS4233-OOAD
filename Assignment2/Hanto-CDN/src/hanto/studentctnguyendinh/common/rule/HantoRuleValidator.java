package hanto.studentctnguyendinh.common.rule;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.studentctnguyendinh.common.HantoGameState;

public class HantoRuleValidator {
	
	List<HantoRule> ruleList;
	List<HantoEndRule> endRuleList;
	
	public HantoRuleValidator(List<HantoRule> ruleList, List<HantoEndRule> endRuleList) {
		this.ruleList = new ArrayList<>(ruleList);
		this.endRuleList = new ArrayList<>(endRuleList);
	}
	
	public void validateRules(HantoGameState gameState, HantoPieceType pieceType, HantoCoordinate from,	HantoCoordinate to) {
		for (HantoRule rule : ruleList) {
			rule.validateRule(gameState, pieceType, from, to);
		}
	}
	
}

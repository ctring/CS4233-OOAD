package hanto.studentctnguyendinh.common.rule;

import hanto.common.MoveResult;
import hanto.studentctnguyendinh.common.HantoGameState;

public interface HantoEndRule {
	MoveResult validateRule(HantoGameState state);
}

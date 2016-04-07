package hanto.studentctnguyendinh.common.piece;

import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.studentctnguyendinh.common.HantoGameState;

public abstract class HantoPieceAbstract implements HantoPiece {

	List<HantoMoveValidator> validators;
	
	public abstract void validateMove(HantoGameState gameState, 
			HantoCoordinate from, HantoCoordinate to) throws HantoException;

}

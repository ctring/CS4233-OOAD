package hanto.studentctnguyendinh.common;

import static hanto.common.MoveResult.OK;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentctnguyendinh.HantoPieceFactory;
import hanto.studentctnguyendinh.common.piece.HantoPieceAbstract;
import hanto.studentctnguyendinh.common.rule.HantoRuleValidator;

public abstract class HantoGameBase implements HantoGame {
	
	protected HantoGameState gameState;
	protected HantoRuleValidator ruleValidator;
	protected HantoPieceFactory pieceFactory;
	
	/**
	 * Construct a HantoGameBase instance with the player who moves first being
	 * specified.
	 * 
	 * @param movesFirst
	 *            color of the player who moves first
	 * @param ruleValidator
	 *            a validators that validates set of rules for this game.
	 * @param piecesQuota
	 *            number of available pieces for each piece types.
	 */
	public HantoGameBase(HantoPlayerColor movesFirst) {
		pieceFactory = HantoPieceFactory.getInstance();
	}
	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		ruleValidator.validateRules(gameState, pieceType, from, to);

		if (from == null) {
			HantoPiece newPiece = pieceFactory.makeHantoPiece(gameState.getCurrentPlayer(), pieceType);
			gameState.putPieceAt(to, newPiece);
		} else {
			HantoPieceAbstract piece = (HantoPieceAbstract) gameState.getPieceAt(from);
			piece.validateMove(gameState, from, to);
			gameState.movePiece(from, to);
		}

		gameState.advanceMove();

		MoveResult moveResult = ruleValidator.validateEndRules(gameState);
		if (moveResult != OK) {
			gameState.flagGameOver();
		}

		return moveResult;
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return gameState.getPieceAt(where);
	}

	@Override
	public String getPrintableBoard() {
		return gameState.getPrintableBoard();
	}

}

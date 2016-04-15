package hanto.studentctnguyendinh.common;

import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;

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

	protected HantoPieceType playedPieceType;
	protected HantoCoordinate playedFrom;
	protected HantoCoordinate playedTo;
	
	protected int maxNumberOfMove = Integer.MAX_VALUE;

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

		recordMoveInput(pieceType, from, to);

		doPreMoveCheck();
		doMove();
		doPostMoveCheck();

		MoveResult moveResult = getMoveResult();
		if (moveResult != OK) {
			gameState.flagGameOver();
		}
		return moveResult;
	}

	/**
	 * Converts input into internal move data.
	 * @param pieceType type of the piece being played.
	 * @param from coordinate where the piece begins.
	 * @param to coordinate where the piece ends
	 */
	private void recordMoveInput(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) {
		playedPieceType = pieceType;
		playedFrom = from != null ? new HantoCoordinateImpl(from) : null;
		playedTo = to != null ? new HantoCoordinateImpl(to) : null;
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return gameState.getPieceAt(where);
	}

	@Override
	public String getPrintableBoard() {
		return gameState.getPrintableBoard();
	}

	/**
	 * Check for game rules compliance before making the move. 
	 * 
	 * @throws HantoException
	 */
	protected void doPreMoveCheck() throws HantoException {
		ruleValidator.validateRules(gameState, playedPieceType, playedFrom, playedTo);
	}

	/**
	 * Actually do the move. There can also be move validation in this method.
	 * @throws HantoException
	 */
	protected void doMove() throws HantoException {
		if (playedFrom == null) {
			HantoPiece newPiece = pieceFactory.makeHantoPiece(gameState.getCurrentPlayer(), playedPieceType);
			gameState.putPieceAt(playedTo, newPiece);
		} else {
			HantoPieceAbstract piece = (HantoPieceAbstract) gameState.getPieceAt(playedFrom);
			piece.validateMove(gameState, playedFrom, playedTo);
			gameState.movePiece(playedFrom, playedTo);
		}
		gameState.advanceMove();
	}

	/**
	 * Check for game rules compliance after making the move. 
	 * 
	 * @throws HantoException
	 */
	protected void doPostMoveCheck() throws HantoException {
		if (playedFrom != null) {
			if (!gameState.validateBoard()) {
				throw new HantoException("Cannot make a discontinuous move"); 
			}
		}
	}
	
	/**
	 * Get result of the move based on the game state after making the move.
	 * @return result of the move.
	 */
	protected MoveResult getMoveResult() {
		final boolean redButterflySurrounded = checkButterflySurrounded(RED);
		final boolean blueButterflySurrounded = checkButterflySurrounded(BLUE);
		if (redButterflySurrounded && blueButterflySurrounded) {
			return DRAW;
		}
		else if (redButterflySurrounded) {
			return BLUE_WINS;
		}
		else if (blueButterflySurrounded) {
			return RED_WINS;
		}
		else if (gameState.getNumberOfPlayedMoves() >= maxNumberOfMove) {
			return DRAW;
		}
		return OK;
	}
	
	/**
	 * Check whether the butterfly of the specified player is surrounded.
	 * @param player color of the player whose butterfly needs to be checked.
	 * @return true if the butterfly is surrounded, false otherwise.
	 */
	private boolean checkButterflySurrounded(HantoPlayerColor player) {
		if (gameState.getPlayerState(player).getButterflyCoordinate() == null) {
			return false;
		}
		final HantoCoordinateImpl butterflyCoord = new HantoCoordinateImpl(
				gameState.getPlayerState(player).getButterflyCoordinate());
		
		final HantoCoordinateImpl[] adjCoords = butterflyCoord.getAdjacentCoordsSet();
		for (int i = 0; i < 6; i++) {
			if (gameState.getPieceAt(adjCoords[i]) == null) {
				return false;
			}
		}
		return true;
	}

}

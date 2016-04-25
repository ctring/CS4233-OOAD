/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package hanto.studentctnguyendinh.epsilon;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.CRAB;
import static hanto.common.HantoPieceType.HORSE;
import static hanto.common.HantoPieceType.SPARROW;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.studentctnguyendinh.common.HantoBoard;
import hanto.studentctnguyendinh.common.HantoGameBase;
import hanto.studentctnguyendinh.common.HantoGameState;
import hanto.studentctnguyendinh.common.piece.HantoMovementRule;
import hanto.studentctnguyendinh.common.piece.HantoPieceImpl;
import hanto.studentctnguyendinh.common.piece.MVFlying;
import hanto.studentctnguyendinh.common.piece.MVJumping;
import hanto.studentctnguyendinh.common.piece.MVWalking;
import hanto.studentctnguyendinh.common.rule.HantoRule;
import hanto.studentctnguyendinh.common.rule.HantoRuleAdjacentSameColor;
import hanto.studentctnguyendinh.common.rule.HantoRuleButterflyInFourMoves;
import hanto.studentctnguyendinh.common.rule.HantoRuleMoveBeforeButterfly;
import hanto.studentctnguyendinh.common.rule.HantoRuleNotAdjacent;
import hanto.studentctnguyendinh.common.rule.HantoRuleOccupiedHex;
import hanto.studentctnguyendinh.common.rule.HantoRuleValidatorImpl;


/**
 * A concrete implementation of the Delta version of the Hanto game.
 * 
 * @author Cuong Nguyen
 * @version April 18, 2016
 */
public class EpsilonHantoGame extends HantoGameBase {

	/**
	 * Construct a DeltaHantoGame instance with the player who moves first being
	 * specified.
	 * 
	 * @param movesFirst
	 *            color of the player who moves first
	 */
	public EpsilonHantoGame(HantoPlayerColor movesFirst) {

		maxNumberOfMove = 40;

		HantoRule[] rules = { new HantoRuleMoveBeforeButterfly(), new HantoRuleOccupiedHex(),
				new HantoRuleNotAdjacent(), new HantoRuleButterflyInFourMoves(), new HantoRuleAdjacentSameColor() };

		ruleValidator = new HantoRuleValidatorImpl(rules);

		Map<HantoPieceType, Integer> epsilonPiecesQuota = new HashMap<>();
		epsilonPiecesQuota.put(BUTTERFLY, 1);
		epsilonPiecesQuota.put(SPARROW, 2);
		epsilonPiecesQuota.put(CRAB, 6);
		epsilonPiecesQuota.put(HORSE, 4);

		gameState = new HantoGameState(movesFirst, epsilonPiecesQuota);
	}
	
	@Override
	public MoveResult resign() throws HantoPrematureResignationException {
//		HantoBoard board = gameState.cloneBoard();
//		System.out.println(board.getPrintableBoard());
//		List<HantoCoordinate> allCoords = board.getPiecesCoordinates(gameState.getCurrentPlayer());
//		for (HantoCoordinate c : allCoords) {
//			HantoPiece p = board.getPieceAt(c);
//			if (p instanceof HantoPieceImpl) {
//				HantoPieceImpl piece = (HantoPieceImpl)p;
//				System.out.println(c.getX() + " " + c.getY());
//				for (HantoCoordinate co : piece.getReachableCoordinates(gameState, c)) {
//					System.out.println(" " + co.getX() + " " + co.getY());
//				}
//			}
//		}
		if (stillCanPlace() || stillCanMove()) {
			throw new HantoPrematureResignationException();
		}
		return super.resign();
	}
	
	private boolean stillCanPlace() {
		HantoPlayerColor currentPlayer = gameState.getCurrentPlayer();
		HantoGameState.HantoPlayerState currentPlayerState = gameState.getPlayerState(currentPlayer);
		List<HantoCoordinate> placable = currentPlayerState.getPlacableCoordinates();
//		for (HantoCoordinate c: placable) {
//			System.out.println(c.getX() + " " + c.getY());
//		}
		boolean nonzeroPieces = currentPlayerState.getTotalOfRemainingPieces() > 0;
				
		return placable.size() > 0 && nonzeroPieces;
	}
	
	private boolean stillCanMove() {
		HantoBoard board = gameState.cloneBoard();
		List<HantoCoordinate> allCoords = board.getPiecesCoordinates(gameState.getCurrentPlayer());
		for (HantoCoordinate c : allCoords) {
			HantoPiece p = board.getPieceAt(c);
			if (p instanceof HantoPieceImpl) {
				HantoPieceImpl piece = (HantoPieceImpl)p;
				if (piece.getReachableCoordinates(gameState, c).size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public HantoPiece makeHantoPiece(HantoPlayerColor color, HantoPieceType pieceType) {
		HantoMovementRule validators;
		switch (pieceType) {
		case CRAB:
			validators = new MVWalking(1);
			break;
		case BUTTERFLY:
			validators = new MVWalking(1);
			break;
		case SPARROW:
			validators = new MVFlying(4);
			break;
		case HORSE:
			validators = new MVJumping();
			break;
		default:
			validators = null;
		}

		return new HantoPieceImpl(color, pieceType, validators);
	}
}

/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentctnguyendinh.epsilon;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.CRAB;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.MoveResult;
import hanto.studentctnguyendinh.HantoGameFactory;

/**
 * Test cases for Beta Hanto.
 * 
 * @version Sep 14, 2014
 */
@RunWith(Enclosed.class)
public class EpsilonHantoMasterTest {
	/**
	 * Internal class for these test cases.
	 * 
	 * @version Sep 13, 2014
	 */
	static class TestHantoCoordinate implements HantoCoordinate {
		private final int x, y;

		public TestHantoCoordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX() {
			return x;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY() {
			return y;
		}

	}

	static class MoveData {
		final HantoPieceType type;
		final HantoCoordinate from;
		final HantoCoordinate to;

		public MoveData(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) {
			this.type = pieceType;
			this.from = from;
			this.to = to;
		}
	}

	private static HantoGameFactory factory = HantoGameFactory.getInstance();;
	private static HantoGame game;

	public static class PlacingPiecesTests {
		@Before
		public void setup() {
			// By default, blue moves first.
			game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO, BLUE);
		}

		@Test // 1
		public void bluePlacesInitialButterflyAtOrigin() throws HantoException {
			final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			assertEquals(OK, mr);
			final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
			assertEquals(BLUE, p.getColor());
			assertEquals(BUTTERFLY, p.getType());
		}

		@Test(expected = HantoException.class) // 2
		public void blueDoesNotPlaceInitialButterflyAtOrigin() throws HantoException {
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
		}

		@Test // 3
		public void redPlacesButterflyAtSecondMove() throws HantoException {
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
			assertEquals(OK, mr);
			final HantoPiece p = game.getPieceAt(makeCoordinate(0, 1));
			assertEquals(RED, p.getColor());
			assertEquals(BUTTERFLY, p.getType());
		}

		@Test(expected = HantoException.class) // 4
		public void redPlacesFirstPieceOnAnOccupiedHex() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 0));
		}

		@Test(expected = HantoException.class) // 5
		public void redPlacesOnAnOccupiedHex() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, -1, 0), md(SPARROW, -1, 0));
		}

		@Test(expected = HantoException.class) // 6
		public void bluePlacesPieceOnAnOccupiedHex() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0));
		}

		@Test(expected = HantoException.class) // 7
		public void redPlacesANonAdjacentPiece() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 2, 0));
		}

		@Test(expected = HantoException.class) // 8
		public void bluePlacesANonAdjacentPiece() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, -2, 0));
		}

		@Test(expected = HantoException.class) // 9
		public void blueDoesNotPlaceButterflyOnFourthMove() throws HantoException {
			makeMoves(md(SPARROW, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 0, 2), md(SPARROW, 0, -2),
					md(SPARROW, 0, 3), md(SPARROW, 0, -3));
		}

		@Test(expected = HantoException.class) // 10
		public void redDoesNotPlaceButterflyOnFourthMove() throws HantoException {
			makeMoves(md(SPARROW, 0, 0), md(SPARROW, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 0, 2), md(SPARROW, 0, -2),
					md(SPARROW, 0, 3), md(BUTTERFLY, 0, -3), md(SPARROW, 0, 4));
		}

		@Test // 11
		public void blueAndRedPlacesButterflyOnFourthMove() throws HantoException {
			makeMoves(md(SPARROW, 0, 0), md(SPARROW, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 0, 2), md(SPARROW, 0, -2),
					md(SPARROW, 0, 3), md(BUTTERFLY, -1, 0), md(BUTTERFLY, 1, 1));
		}

		@Test(expected = HantoException.class) // 12
		public void blueTriesToPlaceTheSecondButterfly() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 1, 0), md(BUTTERFLY, 0, -1));
		}

		@Test(expected = HantoException.class) // 13
		public void redTriesToPlaceTheSecondButterfly() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 0, 2),
					md(SPARROW, 0, -2), md(BUTTERFLY, 0, 3));
		}

		@Test // 14
		public void getPrintableBoardShouldReturnAString() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(SPARROW, 1, 0));
			assertNotNull(game.getPrintableBoard());
		}

		@Test // 15
		public void redPlacesInitialSparrowAtOrigin() throws HantoException {
			game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED); // RedFirst
			final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			assertEquals(OK, mr);
			final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
			assertEquals(RED, p.getColor());
			assertEquals(SPARROW, p.getType());
		}

		@Test(expected = HantoException.class) // 16
		public void blueAddsAPieceNextToARedPiece() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 1, 0));
		}

		@Test(expected = HantoException.class) // 17
		public void redAddsAPieceNextToABluePiece() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 1, -1), md(SPARROW, -1, 0));
		}

		@Test // 18
		public void blueAndRedMakeValidPlacings() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(SPARROW, 0, 1), md(SPARROW, -1, 0), md(BUTTERFLY, 1, 1),
					md(SPARROW, 0, -1), md(SPARROW, 2, 0));
		}

//		@Test(expected = HantoException.class) // 19
//		public void blueTriesToPlaceTheFifthSparrow() throws HantoException {
//			makeMoves(md(BUTTERFLY, 0, 0), md(SPARROW, 0, 1), md(SPARROW, 0, -1), md(BUTTERFLY, 0, 2),
//					md(SPARROW, 0, -2), md(SPARROW, 0, 3), md(SPARROW, 0, -3), md(SPARROW, 0, 4), md(SPARROW, 0, -4),
//					md(SPARROW, 0, 5), md(SPARROW, 0, -5));
//		}
//
//		@Test(expected = HantoException.class) // 20
//		public void redTriesToPlaceTheFifthSparrow() throws HantoException {
//			game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO, RED);
//			makeMoves(md(BUTTERFLY, 0, 0), md(SPARROW, 0, 1), md(SPARROW, 0, -1), md(BUTTERFLY, 0, 2),
//					md(SPARROW, 0, -2), md(SPARROW, 0, 3), md(SPARROW, 0, -3), md(SPARROW, 0, 4), md(SPARROW, 0, -4),
//					md(SPARROW, 0, 5), md(SPARROW, 0, -5));
//		}

	}

	public static class MovingPiecesTests {
		@Before
		public void setup() {
			// By default, blue moves first.
			game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO, BLUE);
		}

		@Test	// 21
		public void blueMakesAValidButterflyWalk() throws HantoException
		{
			MoveResult mr = makeMoves(md(SPARROW, 0, 0), md(BUTTERFLY, 0, -1), md(BUTTERFLY, -1, 1),
					md(SPARROW, -1, -1), md(BUTTERFLY, -1, 1, -1, 0));

			assertEquals(OK, mr);

			HantoPiece pc = game.getPieceAt(makeCoordinate(-1, 1));
			assertNull(pc);

			pc = game.getPieceAt(makeCoordinate(-1, 0));
			assertNotNull(pc);
			assertEquals(BLUE, pc.getColor());
			assertEquals(BUTTERFLY, pc.getType());
		}

		@Test	// 22
		public void redMakesAValidButterflyWalk() throws HantoException
		{
			MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(SPARROW, 0, -1), md(SPARROW, -1, 1),
					md(BUTTERFLY, -1, -1), md(SPARROW, 0, 1), md(BUTTERFLY, -1, -1, 0, -2));
			assertEquals(OK, mr);

			HantoPiece pc = game.getPieceAt(makeCoordinate(-1, -1));
			assertNull(pc);

			pc = game.getPieceAt(makeCoordinate(0, -2));
			assertNotNull(pc);
			assertEquals(RED, pc.getColor());
			assertEquals(BUTTERFLY, pc.getType());
		}

//		@Test	// 23
//		public void blueMakesAValidCrabWalk() throws HantoException {
//			MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 1, 1),
//					md(CRAB, 0, -1, -1, 1));
//			assertEquals(OK, mr);
//			HantoPiece pc = game.getPieceAt(makeCoordinate(0, -1));
//			assertNull(pc);
//
//			pc = game.getPieceAt(makeCoordinate(-1, 1));
//			assertNotNull(pc);
//			assertEquals(BLUE, pc.getColor());
//			assertEquals(CRAB, pc.getType());
//		}

//		@Test	// 24
//		public void blueMakesAValidSparrowFly() throws HantoException {
//			MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 1, 1),
//					md(SPARROW, 0, -1, -1, 1));
//			assertEquals(OK, mr);
//			HantoPiece pc = game.getPieceAt(makeCoordinate(0, -1));
//			assertNull(pc);
//
//			pc = game.getPieceAt(makeCoordinate(-1, 1));
//			assertNotNull(pc);
//			assertEquals(BLUE, pc.getColor());
//			assertEquals(SPARROW, pc.getType());
//		}

		@Test(expected = HantoException.class)	// 25
		public void redWalksButterflyTwoHexesAway() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 1, 0), md(SPARROW, 0, -1), md(CRAB, 1, 1),
					md(SPARROW, 0, -1, 0, 1), md(BUTTERFLY, 1, 0, 0, -1));
		}

//		@Test(expected = HantoException.class)	// 26
//		public void blueWalksCrabFourHexesAway() throws HantoException {
//			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 1, 1), md(CRAB, 0, -1, 0, 2));
//		}

		@Test(expected = HantoException.class)	// 27
		public void blueFliesSparrowToANonAdjacentHex() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, -1, 0), md(SPARROW, 1, 1),
					md(SPARROW, -1, 0, 3, 0));
		}

		@Test(expected = HantoException.class)	// 28
		public void blueAttemptsToWalkCrabBeforePlacingButterfly() throws HantoException {
			makeMoves(md(CRAB, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, -1, 0), md(SPARROW, 1, 1), md(CRAB, -1, 0, -1, 2));
		}

		@Test(expected = HantoException.class)	// 29
		public void redAttemptsToFlySparrowBeforePlacingButterfly() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(SPARROW, -1, 1), md(CRAB, 0, -1), md(SPARROW, -1, 2), md(SPARROW, 1, -1),
					md(SPARROW, -1, 2, 1, 0));
		}

		@Test(expected = HantoException.class)	// 30
		public void blueAttemptsToWalkABlockedCrab() throws HantoException
		{
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, -1, 0), md(CRAB, 1, 1),
					md(CRAB, 0, -1), md(CRAB, 2, 1), md(CRAB, -1, -1), md(CRAB, 3, 1), 
					md(CRAB, -2, 0), md(CRAB, 4, 1), md(SPARROW, -2, 1), md(SPARROW, 5, 1),
					md(CRAB, -1, 0, -1, 2));
		}
		
		@Test(expected = HantoException.class)	// 31 
		public void redAttemptsToWalkABlockedCrab() throws HantoException
		{
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, -1, 0), md(CRAB, 1, 1),
					md(CRAB, 0, -1), md(CRAB, 0, 2), md(CRAB, -1, -1), md(CRAB, 2, 1), 
					md(CRAB, -2, 0), md(CRAB, 1, 2), md(SPARROW, -2, 1), md(SPARROW, 3, 1),
					md(SPARROW, -2, 2), md(CRAB, 0, 2, -1, 1));
		}
		
		@Test(expected = HantoException.class)	// 32
		public void blueAttemptsToWalkAWronglySpecifiedPiece() throws HantoException
		{
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 1, -1), md(SPARROW, -1, 1), md(SPARROW,1, -2),
					md(CRAB, -1, 1, 0, 1));
		}
		
		@Test(expected = HantoException.class)	// 33
		public void redAttemptsToWalkANonExistentPiece() throws HantoException
		{
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 1, -1), md(CRAB, -1, 1), md(SPARROW,1, -2),
					md(CRAB, -1, 1, 0, 1), md(SPARROW, 2, -2, 1, 0));
		}
		
		@Test(expected = HantoException.class)	// 34
		public void blueAttemptsToWalkToAnOccupiedHex() throws HantoException
		{
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 1, -1), md(CRAB, -1, 1), md(SPARROW,1, -2),
					md(CRAB, -1, 1, 0, 0));			
		}
		
		@Test(expected = HantoException.class)	// 35
		public void redAttemptsToFlyToAnOccupiedHex() throws HantoException
		{
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 1, -1), md(CRAB, -1, 1), md(SPARROW,1, -2),
					md(CRAB, -1, 1, 0, 1), md(SPARROW, 1, -2, 0, 1));
		}
		
		@Test(expected = HantoException.class)	// 36
		public void blueAttemptsToMakeADiscontinuousWalk() throws HantoException
		{
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 1, -1), md(SPARROW, 1, 1),
					md(CRAB, 2, -1), md(SPARROW, -1, 2), md(CRAB, 1, -1, -1, 0));
		}
		
		@Test(expected = HantoException.class)	// 37
		public void redAttemptsToMakeADiscontinuousFly() throws HantoException
		{
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 1, -1), md(SPARROW, 1, 1),
					md(CRAB, 2, -1), md(SPARROW, 1, 2), md(CRAB, 2, -2), md(SPARROW, 1, 1, 1, 0));
		}
		
		@Test(expected = HantoException.class)	// 38
		public void blueAttemptsToMoveARedPiece() throws HantoException
		{
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 1, -1, 1));
		}
		
		@Test(expected = HantoException.class)	// 39
		public void redAttemptsToMoveARedPiece() throws HantoException
		{
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, -1, 0), md(BUTTERFLY, 0, 0, -1, 1));
		}
		
	}

//	public static class WinningAndDrawTests {
//		@Before
//		public void setup() {
//			// By default, blue moves first.
//			game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO, BLUE);
//		}
//
//	}

	public static class OtherTests {
		@Before
		public void setup() {
			// By default, blue moves first.
			game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO, BLUE);
		}

		@Test(expected = HantoException.class)	// 46
		public void makeMoveWithTheSameFromAndTo() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(SPARROW, 1, 0), md(BUTTERFLY, 0, 0, 0, 0));
		}


	}

	// Helper methods
	private static HantoCoordinate makeCoordinate(int x, int y) {
		return new TestHantoCoordinate(x, y);
	}

	private static MoveData md() {
		return new MoveData(null, null,null);
	}
	
	private static MoveData md(HantoPieceType type, int toX, int toY) {
		return new MoveData(type, null, makeCoordinate(toX, toY));
	}

	private static MoveData md(HantoPieceType type, int fromX, int fromY, int toX, int toY) {
		return new MoveData(type, makeCoordinate(fromX, fromY), makeCoordinate(toX, toY));
	}

	private static MoveResult makeMoves(MoveData... moves) throws HantoException {
		MoveResult mr = null;
		for (MoveData md : moves) {
			mr = game.makeMove(md.type, md.from, md.to);
		}
		return mr;
	}
}

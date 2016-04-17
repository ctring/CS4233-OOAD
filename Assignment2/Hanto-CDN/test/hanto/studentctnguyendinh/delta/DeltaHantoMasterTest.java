/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentctnguyendinh.delta;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.CRAB;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.BLUE_WINS;
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
import hanto.studentctnguyendinh.common.HantoBoard;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.piece.HantoPieceImpl;
import hanto.studentctnguyendinh.common.piece.MVWalking;

/**
 * Test cases for Beta Hanto.
 * 
 * @version Sep 14, 2014
 */
@RunWith(Enclosed.class)
public class DeltaHantoMasterTest {
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
			game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, BLUE);
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

		@Test(expected = HantoException.class) // 19
		public void blueTriesToPlaceTheFifthSparrow() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(SPARROW, 0, 1), md(SPARROW, 0, -1), md(BUTTERFLY, 0, 2),
					md(SPARROW, 0, -2), md(SPARROW, 0, 3), md(SPARROW, 0, -3), md(SPARROW, 0, 4), md(SPARROW, 0, -4),
					md(SPARROW, 0, 5), md(SPARROW, 0, -5));
		}

		@Test(expected = HantoException.class) // 20
		public void redTriesToPlaceTheFifthSparrow() throws HantoException {
			game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
			makeMoves(md(BUTTERFLY, 0, 0), md(SPARROW, 0, 1), md(SPARROW, 0, -1), md(BUTTERFLY, 0, 2),
					md(SPARROW, 0, -2), md(SPARROW, 0, 3), md(SPARROW, 0, -3), md(SPARROW, 0, 4), md(SPARROW, 0, -4),
					md(SPARROW, 0, 5), md(SPARROW, 0, -5));
		}

	}

	public static class MovingPiecesTests {
		@Before
		public void setup() {
			// By default, blue moves first.
			game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, BLUE);
		}

		@Test
		public void blueMakesAValidButterflyWalk() throws HantoException // 21
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

		@Test
		public void redMakesAValidButterflyWalk() throws HantoException // 22
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

		@Test
		public void blueMakesAValidCrabWalk() throws HantoException {
			MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 1, 1),
					md(CRAB, 0, -1, -1, 1));
			assertEquals(OK, mr);
			HantoPiece pc = game.getPieceAt(makeCoordinate(0, -1));
			assertNull(pc);

			pc = game.getPieceAt(makeCoordinate(-1, 1));
			assertNotNull(pc);
			assertEquals(BLUE, pc.getColor());
			assertEquals(CRAB, pc.getType());
		}
		
		@Test
		public void blueMakesAValidSparrowFly() throws HantoException {
			MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 1, 1),
					md(SPARROW, 0, -1, -1, 1));
			assertEquals(OK, mr);
			HantoPiece pc = game.getPieceAt(makeCoordinate(0, -1));
			assertNull(pc);

			pc = game.getPieceAt(makeCoordinate(-1, 1));
			assertNotNull(pc);
			assertEquals(BLUE, pc.getColor());
			assertEquals(SPARROW, pc.getType());			
		}

		@Test(expected = HantoException.class)
		public void redWalksButterflyTwoHexesAway() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 1, 0), md(SPARROW, 0, -1), md(CRAB, 1, 1),
					md(SPARROW, 0, -1, 0, 1), md(BUTTERFLY, 1, 0, 0, -1));
		}
		
		@Test(expected = HantoException.class)
		public void blueWalksCrabFourHexesAway() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 1, 1),
				md(CRAB, 0, -1, 0, 2));
		}
	}

	public static class WinningAndDrawTests {
		@Before
		public void setup() {
			// By default, blue moves first.
			game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, BLUE);
		}

		@Test
		public void blueWinsByWalking() throws HantoException // 35
		{
			MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 1, 0), md(SPARROW, -1, 1), md(SPARROW, 2, 0),
					md(SPARROW, -1, 2), md(SPARROW, 2, -1), md(SPARROW, 0, 2), md(SPARROW, 2, -2),
					md(SPARROW, 0, 2, 1, 1), md(SPARROW, 3, 0), md(SPARROW, -1, 2, 0, 2), md(SPARROW, 2, -2, 1, -1),
					md(SPARROW, 0, 2, 0, 1));
			assertEquals(BLUE_WINS, mr);
		}

		@Test
		public void redWinsByWalking() throws HantoException // 36
		{
			MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 1, 0), md(SPARROW, -1, 1), md(SPARROW, 1, 1),
					md(SPARROW, -1, 0), md(SPARROW, 2, -1), md(SPARROW, 0, -1), md(SPARROW, 1, 1, 0, 1),
					md(SPARROW, -2, 1), md(SPARROW, 2, -1, 1, -1));
			assertEquals(RED_WINS, mr);
		}

	}

	public static class OtherTests {
		@Before
		public void setup() {
			// By default, blue moves first.
			game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, BLUE);
		}

		@Test(expected = HantoException.class)
		public void makeMoveWithTheSameFromAndTo() throws HantoException {
			makeMoves(md(BUTTERFLY, 0, 0), md(SPARROW, 1, 0), md(BUTTERFLY, 0, 0, 0, 0));
		}
		
		@Test
		public void getReachableHex() {
			MVWalking mv = new MVWalking(2);
			HantoBoard board = new HantoBoard();
			board.putPieceAt(new HantoCoordinateImpl(0, 0), new HantoPieceImpl(BLUE, BUTTERFLY));
			board.putPieceAt(new HantoCoordinateImpl(0, 1), new HantoPieceImpl(RED, BUTTERFLY));
			board.putPieceAt(new HantoCoordinateImpl(1, 1), new HantoPieceImpl(BLUE, BUTTERFLY));
			board.putPieceAt(new HantoCoordinateImpl(2, 0), new HantoPieceImpl(RED, BUTTERFLY));
			board.putPieceAt(new HantoCoordinateImpl(2, -2), new HantoPieceImpl(BLUE, BUTTERFLY));
			board.putPieceAt(new HantoCoordinateImpl(1, -2), new HantoPieceImpl(BLUE, BUTTERFLY));
			board.putPieceAt(new HantoCoordinateImpl(0, -1), new HantoPieceImpl(BLUE, BUTTERFLY));
			
			HantoCoordinate[] coords = mv.getReachableCoordinates(board, new HantoCoordinateImpl(2, 0));
			for (HantoCoordinate c : coords) {
				System.out.println(c.getX() + " " + c.getY());
			}
		}
	}

	// Helper methods
	private static HantoCoordinate makeCoordinate(int x, int y) {
		return new TestHantoCoordinate(x, y);
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

/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentctnguyendinh.gamma;

import static hanto.common.HantoPieceType.*;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studentctnguyendinh.HantoGameFactory;

import org.junit.*;

/**
 * Test cases for Beta Hanto.
 * @version Sep 14, 2014
 */
public class GammaHantoMasterTest
{
	/**
	 * Internal class for these test cases.
	 * @version Sep 13, 2014
	 */
	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		
		public TestHantoCoordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		/*
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX()
		{
			return x;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY()
		{
			return y;
		}

	}
	
	private static HantoGameFactory factory;
	private HantoGame game;
	private HantoPlayerColor firstPlayer = BLUE;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO, BLUE);
	}
	
	@Test	// 1
	public void bluePlacesInitialButterflyAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	@Test(expected = HantoException.class)	// 22
	public void blueDoesNotPlaceInitialButterflyAtOrigin() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
	}
	
	@Test	// 2
	public void redPlacesButterflyAtSecondMove() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 1));
		assertEquals(RED, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	@Test(expected = HantoException.class)	// 4
	public void redPlacesFirstPieceOnAnOccupiedHex() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	}
	
	@Test(expected = HantoException.class)	// 6
	public void redPlacesOnAnOccupiedHex() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
	}
	
	@Test(expected = HantoException.class)	// 5
	public void bluePlacesPieceOnAnOccupiedHex() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	}
	
	@Test	// 7
	public void testAdjacencyDetection() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));			
		game.makeMove(SPARROW, null, makeCoordinate(1, -2));			
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));			
		game.makeMove(SPARROW, null, makeCoordinate(-1, -1));			
		MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		assertEquals(OK, mr);
	}
	
	@Test(expected = HantoException.class)	// 8
	public void redPlacesANonAdjacentPiece() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(2, 0));		
	}
	
	@Test(expected = HantoException.class)	// 9
	public void bluePlacesANonAdjacentPiece() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0));
	}
	
	@Test(expected = HantoException.class)	// 10
	public void blueDoesNotPlaceButterflyOnFourthMove() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));		// blue 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));	// red	1
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));		// blue	2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1));	// red	2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));	// blue	3
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));	// red	3
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0));	// blue	4
	}
	
	@Test(expected = HantoException.class)	// 11
	public void redDoesNotPlaceButterflyOnFourthMove() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0)); 	// blue	1
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));		// red	1
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));		// blue	2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1));	// red	2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));	// blue	3
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));	// red	3
		game.makeMove(BUTTERFLY, null, makeCoordinate(-2, 0));	// blue	4
		game.makeMove(SPARROW, null, makeCoordinate(-1, -1));	// red	4
	}
	
	@Test	// 12
	public void blueAndRedPlacesButterflyOnFourthMove() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0)); 	// blue	1
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));		// red	1
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));		// blue	2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1));	// red	2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));	// blue	3
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));	// red	3
		MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(-2, 0));	// blue	4
		assertEquals(OK, mr);
		HantoPiece pc = game.getPieceAt(makeCoordinate(-2, 0));
		assertNotNull(pc);
		assertEquals(BUTTERFLY, pc.getType());
		assertEquals(BLUE, pc.getColor());
		
		mr = game.makeMove(BUTTERFLY, null, makeCoordinate(-2, 1));	// red	4
		assertEquals(OK, mr);
		pc = game.getPieceAt(makeCoordinate(-2, 1));
		assertEquals(BUTTERFLY, pc.getType());
		assertEquals(RED, pc.getColor());
	}
	
	@Test(expected = HantoException.class)	// 13
	public void blueTriesToPlaceTheSecondButterfly() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
	}
	
	@Test(expected = HantoException.class)	// 14
	public void redTriesToPlaceTheSecondButterfly() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// blue 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));	// red 1
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));	// blue 2
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));	// red 2
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));		// blue 3
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));		// red 3
	}
	
	@Test(expected = HantoException.class)	// 15
	public void blueTriesToPlaceADove() throws HantoException 
	{	
		game.makeMove(DOVE, null, makeCoordinate(0, 0));
	}
	
	@Test(expected = HantoException.class)	// 16
	public void redTriesToPlaceADove() throws HantoException 
	{	
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(DOVE, null, makeCoordinate(1, 0));
	}

	@Test	// 17
	public void blueWinsBySurroundingRedButterfly() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0)); 	// blue	1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));	// red	1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));	// blue	2
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));		// red	2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1));	// blue	3
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));	// red	3
		MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 2));		// blue	3
		assertEquals(BLUE_WINS, mr);
	}
	
	@Test	// 18
	public void redWinsBySurroundingBlueButterfly() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); 	// blue	1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));	// red	1
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1));	// blue	2
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));		// red	2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));	// blue	3
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));	// red	3
		MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(1, -1));	// blue	4
		assertEquals(RED_WINS, mr);
	}
	
	@Test	// 19
	public void bothPlayersAreDraw() throws HantoException 
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); 	// blue	1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));	// red	1
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1));	// blue	2
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));		// red	2
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));		// blue	3
		game.makeMove(SPARROW, null, makeCoordinate(2, -1));	// red	3
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));	// blue	4
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));		// red	4
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));	// blue	5
		MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(1, -1));	// red	5
		assertEquals(DRAW, mr);
	}
	
	@Test	// 20
	public void allPlayersRunOutOfMove() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); 	// blue	1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));	// red	1
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));		// blue	2
		game.makeMove(SPARROW, null, makeCoordinate(3, 0));		// red	2
		game.makeMove(SPARROW, null, makeCoordinate(4, 0));		// blue	3
		game.makeMove(SPARROW, null, makeCoordinate(5, 0));		// red	3
		game.makeMove(SPARROW, null, makeCoordinate(6, 0));		// blue	4
		game.makeMove(SPARROW, null, makeCoordinate(7, 0));		// red	4
		game.makeMove(SPARROW, null, makeCoordinate(8, 0));		// blue	5
		game.makeMove(SPARROW, null, makeCoordinate(9, 0));		// red	5
		game.makeMove(SPARROW, null, makeCoordinate(10, 0));	// blue	6
		MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(11, 0));	// red	6
		assertEquals(DRAW, mr);
	}
	
	@Test	// 23
	public void getPrintableBoardShouldReturnAString() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		assertNotNull(game.getPrintableBoard());
	}
	
	@Test(expected = HantoException.class)	// 24
	public void blueTriesToMoveAPiece() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(1, 1));
	}
	
	@Test	// 25
	public void redSelfLosesBeforeLastTurn() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));		// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));		// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));	// Move 4
		assertEquals(BLUE_WINS, game.makeMove(SPARROW, null, makeCoordinate(-1,1)));
	}
	
	@Test	// 26
	public void redWinsOnLastTurn() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));		// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));		// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));		// Move 4
		game.makeMove(SPARROW, null, makeCoordinate(0, 4));
		game.makeMove(SPARROW, null, makeCoordinate(0, 5));		// Move 5
		game.makeMove(SPARROW, null, makeCoordinate(0, 6));
		game.makeMove(SPARROW, null, makeCoordinate(0, 7));		// Move 6
		assertEquals(RED_WINS, game.makeMove(SPARROW, null, makeCoordinate(-1,1)));
	}
	
	@Test	// 27
	public void redPlacesInitialSparrowAtOrigin() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);	// RedFirst
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
}

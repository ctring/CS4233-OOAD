/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentctnguyendinh.beta;

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
public class BetaHantoMasterTest
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
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, BLUE);
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
	
	@Test	// 2
	public void redMakesAValidMove() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 1));
		assertEquals(RED, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	@Test	// 3
	public void blueMakesAValidMove() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		
		final HantoPiece p = game.getPieceAt(makeCoordinate(1, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());		
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
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
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
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));			
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
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
}

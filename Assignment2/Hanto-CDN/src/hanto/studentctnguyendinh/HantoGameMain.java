package hanto.studentctnguyendinh;
/*package hanto.studentctnguyendinh;

import static hanto.common.HantoPieceType.*;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;

public class BetaHantoGameMain {
	
	static class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		
		public TestHantoCoordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		@Override
		public int getX()
		{
			return x;
		}

		@Override
		public int getY()
		{
			return y;
		}

	}
	
	public static void main(String[] args) throws HantoException {
		HantoGame game = HantoGameFactory.getInstance().makeHantoGame(HantoGameID.BETA_HANTO);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		System.out.println(game.getPrintableBoard());
		
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
		System.out.println(game.getPrintableBoard());
		
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		System.out.println(game.getPrintableBoard());
		
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		System.out.println(game.getPrintableBoard());
		
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		System.out.println(game.getPrintableBoard());
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
		System.out.println(game.getPrintableBoard());
		
		game.makeMove(SPARROW, null, makeCoordinate(-2, 2));
		System.out.println(game.getPrintableBoard());
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		System.out.println(game.getPrintableBoard());

	}

	private static HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
}
*/
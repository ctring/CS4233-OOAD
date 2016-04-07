package hanto.studentctnguyendinh;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentctnguyendinh.common.piece.HantoPieceImpl;

public class HantoPieceFactory {
	private static final HantoPieceFactory instance = new HantoPieceFactory();
	/**
	 * Default private descriptor.
	 */
	private HantoPieceFactory()
	{
		// Empty, but the private constructor is necessary for the singleton.
	}

	/**
	 * @return the instance
	 */
	public static HantoPieceFactory getInstance()
	{
		return instance;
	}
	
	/**
	 * Create a new HantoPiece.
	 * @param pieceType type piece.
	 * @param color color of the player making this piece.
	 * @return a new Hanto piece.
	 */
	public HantoPiece makeHantoPiece(HantoPlayerColor color, HantoPieceType pieceType)
	{
		return new HantoPieceImpl(color, pieceType);
	}

}

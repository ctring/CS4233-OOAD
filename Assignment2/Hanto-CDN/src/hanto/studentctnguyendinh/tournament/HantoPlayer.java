/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentctnguyendinh.tournament;

import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.OK;

import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentctnguyendinh.HantoGameFactory;
import hanto.studentctnguyendinh.common.HantoAI;
import hanto.studentctnguyendinh.common.HantoGameBase;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

/**
 * Description
 * @version Oct 13, 2014
 */
public class HantoPlayer implements HantoGamePlayer
{
	HantoGameBase game;
	HantoAI ai;
	/*
	 * @see hanto.tournament.HantoGamePlayer#startGame(hanto.common.HantoGameID, hanto.common.HantoPlayerColor, boolean)
	 */
	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor,
			boolean doIMoveFirst)
	{
		System.out.println("startGame");
		HantoPlayerColor otherColor = myColor == BLUE ? RED : BLUE;
		game = (HantoGameBase)HantoGameFactory.getInstance().makeHantoGame(version, 
				doIMoveFirst ? myColor : otherColor);
		
		ai = new HantoAI();
		game.registerAI(ai);
	}

	/*
	 * @see hanto.tournament.HantoGamePlayer#makeMove(hanto.tournament.HantoMoveRecord)
	 */
	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove)
	{
		try {
			if (opponentsMove != null) {
				MoveResult mr = game.makeMove(opponentsMove.getPiece(), opponentsMove.getFrom(), opponentsMove.getTo());
				if (mr != OK) {
					System.out.println(mr);
				}
			}
		} catch (HantoException e){
			// do nothing
		}
		
		
		HantoMoveRecord myMove = new HantoMoveRecord(ai.getPiece(), ai.getFrom(), ai.getTo());
		try {
			MoveResult mr = game.makeMove(ai.getPiece(), ai.getFrom(), ai.getTo());
			if (mr != OK) {
				System.out.println(mr);
			}
		}
		catch (HantoException e) {
			System.out.println("HantoAI: " + e.getMessage());
		}
		
		return myMove;
	}
	
}

/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentctnguyendinh.common.piece;

import hanto.common.HantoCoordinate;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * This rule checks for validity when flying a piece.
 * 
 * @author Cuong Nguyen
 * @version April 18, 2016
 */
public class MVFlying implements HantoMovementRule {

	@Override
	public String validate(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to) {
		// HantoCoordinateImpl fromCoord = new HantoCoordinateImpl(from);
		// HantoCoordinateImpl toCoord = new HantoCoordinateImpl(to);
		// if (fromCoord.getMinimumDistanceTo(toCoord) > maxSteps) {
		// return "Cannot fly further than " + maxSteps + " steps";
		// }
		return null;
	}

}

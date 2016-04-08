/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentctnguyendinh.common.piece;

import hanto.common.HantoCoordinate;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
import hanto.studentctnguyendinh.common.HantoGameState;

/**
 * This rule ensures that the piece only walks one hex.
 * @author Cuong Nguyen
 * @version April 7, 2016
 */
public class MVWalkOneHex implements HantoMovementRule {

	@Override
	public String validate(HantoGameState gameState, HantoCoordinate from, HantoCoordinate to) {
		if (new HantoCoordinateImpl(from).getMinimumDistanceTo(to) > 1) {
			return "Cannot walk more than one hex";
		}
		return null;
	}

}

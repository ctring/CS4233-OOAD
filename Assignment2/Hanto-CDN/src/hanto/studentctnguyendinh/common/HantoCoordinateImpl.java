/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2015 Gary F. Pollice
 *******************************************************************************/

package hanto.studentctnguyendinh.common;

import hanto.common.HantoCoordinate;

/**
 * The implementation for my version of Hanto.
 * 
 * @version Mar 2, 2016
 */
public class HantoCoordinateImpl implements HantoCoordinate {
	public static final int NUMBER_OF_ADJACENT_HEXES = 6;
	final private int x, y;

	/**
	 * The only constructor.
	 * 
	 * @param x
	 *            the x-coordinate
	 * @param y
	 *            the y-coordinate
	 */
	public HantoCoordinateImpl(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Copy constructor that creates an instance of HantoCoordinateImpl from an
	 * object that implements HantoCoordinate.
	 * 
	 * @param coordinate
	 *            an object that implements the HantoCoordinate interface.
	 */
	public HantoCoordinateImpl(HantoCoordinate coordinate) {
		this(coordinate.getX(), coordinate.getY());
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HantoCoordinateImpl)) {
			return false;
		}
		final HantoCoordinateImpl other = (HantoCoordinateImpl) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	/**
	 * @return a set of coordinates that are adjacent to this coordinate.
	 */
	public HantoCoordinateImpl[] getAdjacentCoordsSet() {
		final int[][] adjDiff = { { 0, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, 0 }, { -1, 1 } };
		HantoCoordinateImpl[] adj = new HantoCoordinateImpl[6];// = new
																// HantoCoordinateImpl[6];
		for (int i = 0; i < NUMBER_OF_ADJACENT_HEXES; i++) {
			adj[i] = new HantoCoordinateImpl(x + adjDiff[i][0], y + adjDiff[i][1]);
		}
		return adj;
	}

	/**
	 * Get the minimum distance to another coordinate.
	 * 
	 * @param other
	 *            another coordinate to calculate distance to.
	 * @return distance to the given coordinate.
	 */
	public int getMinimumDistanceTo(HantoCoordinate other) {
		int x2 = other.getX();
		int y2 = other.getY();
		HantoCoordinateImpl o = new HantoCoordinateImpl(other);
		HantoCoordinateImpl[] i = new HantoCoordinateImpl[] {
				new HantoCoordinateImpl(x, y2),
				new HantoCoordinateImpl(x2, y),
				new HantoCoordinateImpl(x, y2 - (x - x2)),
				new HantoCoordinateImpl(x2, y - (x2 - x)),
				new HantoCoordinateImpl(x - (y2 - y), y2),
				new HantoCoordinateImpl(x2 - (y - y2), y) 
		};
		
		int distance = Integer.MAX_VALUE;
		for (HantoCoordinateImpl c : i) {
			distance = Math.min(distance, 
					lineUpDistance(c) + o.lineUpDistance(c));
		}
		
		return distance;
	}
	
	private int lineUpDistance(HantoCoordinate other) {
		return Math.max(
				Math.abs(x - other.getX()), 
				Math.abs(y - other.getY()));
	}

	/**
	 * Check if this coordinate is on the same straight line with another coordinate.
	 * @param coord another coordinate
	 * @return true if this coordinate is aligned with the other coordinate, false otherwise.
	 */
	public boolean alignedWith(HantoCoordinate coord) {
		return (x == coord.getX()) || (y == coord.getY()) || (x - coord.getX() == coord.getY() - y);
	}

}

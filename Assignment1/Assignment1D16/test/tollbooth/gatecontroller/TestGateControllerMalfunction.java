/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package tollbooth.gatecontroller;

import tollbooth.TollboothException;

/**
 * Description
 * @version Feb 15, 2016
 */
public class TestGateControllerMalfunction implements GateController
{
	int numOfFailedOpens;
	int numOfFailedCloses;
	int numOfFailedResets;
	boolean isOpen;
	/**
	 * Constructor for the test gate controller.
	 */
	public TestGateControllerMalfunction(int failedOpens, int failedCloses, int failedResets)
	{
		isOpen = false;
		numOfFailedOpens = failedOpens;
		numOfFailedCloses = failedCloses;
		numOfFailedResets = failedResets;
	}
	
	/*
	 * @see tollbooth.gatecontroller.GateController#open()
	 */
	@Override
	public void open() throws TollboothException
	{
		if (numOfFailedOpens > 0) {
			numOfFailedOpens--;
			throw new TollboothException("Hardware error!");
		}
		else {
			isOpen = true;
		}
	}

	/*
	 * @see tollbooth.gatecontroller.GateController#close()
	 */
	@Override
	public void close() throws TollboothException
	{
		if (numOfFailedCloses > 0) {
			numOfFailedCloses--;
			throw new TollboothException("Hardware error!");
		}
		else {
			isOpen = false;
		}
	}

	/*
	 * @see tollbooth.gatecontroller.GateController#reset()
	 */
	@Override
	public void reset() throws TollboothException
	{
		if (numOfFailedResets > 0) {
			numOfFailedResets--;
			throw new TollboothException("Hardware error!");
		}
		else {
			isOpen = false;
		}
	}

	/*
	 * @see tollbooth.gatecontroller.GateController#isOpen()
	 */
	@Override
	public boolean isOpen() throws TollboothException
	{
		return isOpen;
	}

}

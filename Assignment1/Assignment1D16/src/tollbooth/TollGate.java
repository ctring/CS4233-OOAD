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

package tollbooth;

import tollbooth.gatecontroller.GateController;

/**
 * The TollGate contains everything about a tollgate in a tollbooth.
 * @version Feb 3, 2016
 */
public class TollGate
{
	private final int NUM_ATTEMPTS = 3;
	private final GateController controller;
	private final SimpleLogger logger;
	
	private int numOpens = 0;
	private int numCloses = 0;
	private boolean willNotRespond = false;
	
	/**
	 * Constructor that takes the actual gate controller and the logger.
	 * @param controller the GateController object.
	 * @param logger the SimpleLogger object.
	 */
	public TollGate(GateController controller, SimpleLogger logger) {
		this.controller = controller;
		this.logger = logger;
	}
	
	/**
	 * Open the gate.
	 * @throws TollboothException
	 */
	public void open() throws TollboothException
	{
		if (willNotRespond) {
			log(new LogMessage("open: will not respond"));
			return;
		}
		if (!controller.isOpen()) {
			for (int i = 0; i < NUM_ATTEMPTS; i++) {
				try {
					controller.open();
					numOpens++;
					if (i > 0) {
						log(new LogMessage("open: successful"));
					}
					break;
				}
				catch (TollboothException e) {
					log(new LogMessage("open: malfunction", e));
					if (i == NUM_ATTEMPTS - 1) {
						willNotRespond = true;
						log(new LogMessage("open: unrecoverable malfunction", e));
						throw new TollboothException("open: unrecoverable malfunction", e);
					}
				}
			}
		}
	}
	
	/**
	 * Close the gate
	 * @throws TollboothException
	 */
	public void close() throws TollboothException
	{
		if (willNotRespond) {
			log(new LogMessage("close: will not respond"));
			return;
		}
		if (controller.isOpen()) {
			for (int i = 0; i < NUM_ATTEMPTS; i++) {
				try {
					controller.close();
					numCloses++;
					if (i > 0) {
						log(new LogMessage("close: successful"));
					}
					break;
				}
				catch (TollboothException e) {
					log(new LogMessage("close: malfunction", e));
					if (i == NUM_ATTEMPTS - 1) {
						willNotRespond = true;
						log(new LogMessage("close: unrecoverable malfunction", e));
						throw new TollboothException("close: unrecoverable malfunction", e);
					}
				}
			}
		}
	}
	
	/**
	 * Reset the gate to the state it was in when created with the exception of the
	 * statistics.
	 * @throws TollboothException
	 */
	public void reset() throws TollboothException
	{
		if (!controller.isOpen()) {
			log(new LogMessage("reset: successful"));
		} else {
			for (int i = 0; i < NUM_ATTEMPTS; i++) {
				try {
					controller.reset();
					log(new LogMessage("reset: successful"));
					break;
				}
				catch (TollboothException e) {
					log(new LogMessage("reset: malfunction", e));
					if (i == NUM_ATTEMPTS - 1) {
						willNotRespond = true;
						log(new LogMessage("reset: unrecoverable malfunction", e));
						throw new TollboothException("reset: unrecoverable malfunction", e);
					}
				}
			}
		}
	}
	
	/**
	 * @return true if the gate is open
	 * @throws TollboothException 
	 */
	public boolean isOpen() throws TollboothException
	{
		if (willNotRespond) {
			throw new TollboothException("isOpen: unrecorable malfunction");
		}
		return controller.isOpen();
	}
	
	/**
	 * @return the number of times that the gate has been opened (that is, the
	 *  open method has successfully been executed) since the object was created.
	 */
	public int getNumberOfOpens()
	{
		// To be completed
		return numOpens;
	}
	
	/**
	 * @return the number of times that the gate has been closed (that is, the
	 *  close method has successfully been executed) since the object was created.
	 */
	public int getNumberOfCloses()
	{
		// To be completed
		return numCloses;
	}
	
	private void log(LogMessage message) {
		if (logger != null) {
			logger.accept(message);
		}
	}

}

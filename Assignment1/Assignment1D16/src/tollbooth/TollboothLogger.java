/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package tollbooth;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The TollboothLogger manages a queue of messages, which are retrieved
 * in a First In First Out manner. 
 * 
 * @version Mar 22, 2016
 */
public class TollboothLogger implements SimpleLogger {

	Queue<LogMessage> messageQueue = new LinkedList<>();
	
	@Override
	public void accept(LogMessage message) {
		if (message != null) {
			messageQueue.offer(message);
		}
	}

	@Override
	public LogMessage getNextMessage() {
		return messageQueue.poll();
	}

}

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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import tollbooth.gatecontroller.*;

/**
 * Test cases for the Tollbooth, TollGate class.
 * @version Feb 3, 2016
 */
@RunWith(Enclosed.class)
public class TollboothTest
{
	public static class General {
		private GateController controller;
		private TollGate gate;
		
		@Before
		public void initialize() {
			controller = new TestGateController();
			gate = new TollGate(controller, null);
		}
		
		@Test
		public void createNewTollGateWithNoController()
		{
			assertNotNull(new TollGate(null, null));
		}
		
		@Test
		public void createNewTollGateWithAController()
		{
			assertNotNull(new TollGate(new TestGateController(), null));
		}
		
		@Test
		public void newGateControllerIsClosed() throws TollboothException
		{
			assertFalse(gate.isOpen());
		}
	
	}
	
	public static class OpenTheGate {
	
		@Test
		public void gateOpensWithNoMalfunction() throws TollboothException
		{
			final GateController controller = new TestGateController();
			final TollGate gate = new TollGate(controller, null);
			
			gate.open();
			assertTrue(gate.isOpen());
		}
		
		@Test 
		public void numberOfOpensIncreasesByOne_OpenWithNoMalfuction() throws TollboothException
		{
			final GateController controller = new TestGateController();
			final TollGate gate = new TollGate(controller, null);
			
			int oldNumOpens = gate.getNumberOfOpens();
			gate.open();	
			assertEquals(oldNumOpens + 1, gate.getNumberOfOpens());
		}
		
		@Test
		public void gateStaysOpenWhenAlreadyOpen() throws TollboothException
		{
			final GateController controller = new TestGateController();
			final TollGate gate = new TollGate(controller, null);
			
			gate.open();
			gate.open();
			assertTrue(gate.isOpen());
		}
		
		@Test
		public void numberOfOpensDoesNotChange_OpenWhenGateAlreadyOpen() throws TollboothException 
		{
			final GateController controller = new TestGateController();
			final TollGate gate = new TollGate(controller, null);
			
			gate.open();
			int oldNumOpens = gate.getNumberOfOpens();
			gate.open();
			assertEquals(oldNumOpens, gate.getNumberOfOpens());
		}
		
		@Test
		public void gateOpensAfterOneMalfunction() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(1, 0, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			gate.open();
			LogMessage message = logger.getNextMessage();
			assertEquals("open: malfunction", message.getMessage());
			message = logger.getNextMessage();
			assertEquals("open: successful", message.getMessage());
			assertTrue(gate.isOpen());
		}
		
		@Test
		public void numberOfOpensIncreasesByOne_OpenAfterOneMalfunction() throws TollboothException

		{
			final GateController controller = new TestGateControllerMalfunction(1, 0, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			int oldNumOpens = gate.getNumberOfOpens();
			gate.open();
			assertEquals(oldNumOpens + 1, gate.getNumberOfOpens());
		}
	
		@Test
		public void gateOpensAfterTwoMalfunction() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(2, 0, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			gate.open();
			LogMessage message = logger.getNextMessage();
			assertEquals("open: malfunction", message.getMessage());
			message = logger.getNextMessage();
			assertEquals("open: malfunction", message.getMessage());
			message = logger.getNextMessage();
			assertEquals("open: successful", message.getMessage());
			assertTrue(gate.isOpen());
		}
		
		@Test
		public void numberOfOpensIncreasesByOne_OpenAfterTwoMalfunction() throws TollboothException

		{
			final GateController controller = new TestGateControllerMalfunction(2, 0, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			int oldNumOpens = gate.getNumberOfOpens();
			gate.open();
			assertEquals(oldNumOpens + 1, gate.getNumberOfOpens());
		}
	
		@Test
		public void gateSwitchesToWillNotRespondState() throws TollboothException 
		{
			final GateController controller = new TestGateControllerMalfunction(3, 0, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			try {
				gate.open();
			} catch (TollboothException e) {
				LogMessage message = logger.getNextMessage();
				assertEquals("open: malfunction", message.getMessage());
				message = logger.getNextMessage();
				assertEquals("open: malfunction", message.getMessage());
				message = logger.getNextMessage();
				assertEquals("open: malfunction", message.getMessage());
				message = logger.getNextMessage();
				assertEquals("open: unrecoverable malfunction", message.getMessage());
				assertTrue(message.hasCause());
			}
		}
		
		@Test(expected = TollboothException.class)
		public void gateThrowsExceptionAfterSwitchingToWillNotRespondState() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(3, 0, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			try {
				gate.open();
			} catch (TollboothException e) {
				assertEquals("open: unrecoverable malfunction", e.getMessage());
				assertNotNull(e.getCause());
				throw e;
			}
		}
	
		@Test
		public void gateNotRespondWhenInWillNotRespondState() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(3, 0, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			LogMessage message;
			
			try {
				gate.open();
			} catch (Exception e) {
				flushLogger(logger);
			}
			gate.open();
			message = logger.getNextMessage();
			assertEquals("open: will not respond", message.getMessage());
		}
		
		@Test
		public void numberOfOpensDoesNotChange_InWillNotRespondState() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(3, 0, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			int oldNumOpens = gate.getNumberOfOpens();
			try {
				gate.open();
			} catch (Exception e) {
				
			}
			assertEquals(oldNumOpens, gate.getNumberOfOpens());
		}
	}
	
	public static class CloseTheGate {
		@Test
		public void gateClosesWithNoMalfunction() throws TollboothException
		{
			final GateController controller = new TestGateController();
			final TollGate gate = new TollGate(controller, null);
			
			gate.open();
			gate.close();
			assertFalse(gate.isOpen());
		}
		
		@Test 
		public void numberOfClosesIncreasesByOne_CloseWithNoMalfuction() throws TollboothException
		{
			final GateController controller = new TestGateController();
			final TollGate gate = new TollGate(controller, null);
			
			gate.open();	
			int oldNumCloses = gate.getNumberOfCloses();
			gate.close();
			assertEquals(oldNumCloses + 1, gate.getNumberOfCloses());
		}
		
		@Test
		public void gateStaysCloseWhenAlreadyClose() throws TollboothException
		{
			final GateController controller = new TestGateController();
			final TollGate gate = new TollGate(controller, null);
			
			gate.open();
			gate.close();
			gate.close();
			assertFalse(gate.isOpen());
		}
		
		@Test
		public void numberOfClosesDoesNotChange_CloseWhenGateAlreadyClose() throws TollboothException 
		{
			final GateController controller = new TestGateController();
			final TollGate gate = new TollGate(controller, null);
			
			gate.open();
			gate.close();
			int oldNumCloses = gate.getNumberOfCloses();
			gate.close();
			assertEquals(oldNumCloses, gate.getNumberOfCloses());
		}
		
		@Test
		public void gateClosesAfterOneMalfunction() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(0, 1, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			gate.open();
			flushLogger(logger);
			
			gate.close();
			LogMessage message = logger.getNextMessage();
			assertEquals("close: malfunction", message.getMessage());
			message = logger.getNextMessage();
			assertEquals("close: successful", message.getMessage());
			assertFalse(gate.isOpen());
		}
		
		@Test
		public void numberOfClosesIncreasesByOne_CloseAfterOneMalfunction() throws TollboothException

		{
			final GateController controller = new TestGateControllerMalfunction(0, 1, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			gate.open();
			int oldNumCloses = gate.getNumberOfCloses();
			gate.close();
			assertEquals(oldNumCloses + 1, gate.getNumberOfCloses());
		}
	
		@Test
		public void gateClosesAfterTwoMalfunction() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(0, 2, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			gate.open();
			flushLogger(logger);
			
			gate.close();
			LogMessage message = logger.getNextMessage();
			assertEquals("close: malfunction", message.getMessage());
			message = logger.getNextMessage();
			assertEquals("close: malfunction", message.getMessage());
			message = logger.getNextMessage();
			assertEquals("close: successful", message.getMessage());
			assertFalse(gate.isOpen());
		}
		
		@Test
		public void numberOfClosesIncreasesByOne_CloseAfterTwoMalfunction() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(0, 2, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			gate.open();
			int oldNumCloses = gate.getNumberOfCloses();
			gate.close();
			assertEquals(oldNumCloses + 1, gate.getNumberOfCloses());
		}
	
		@Test
		public void gateSwitchesToWillNotRespondState() throws TollboothException 
		{
			final GateController controller = new TestGateControllerMalfunction(0, 3, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			gate.open();
			flushLogger(logger);
			try {
				gate.close();
			} catch (TollboothException e) {
				LogMessage message = logger.getNextMessage();
				assertEquals("close: malfunction", message.getMessage());
				message = logger.getNextMessage();
				assertEquals("close: malfunction", message.getMessage());
				message = logger.getNextMessage();
				assertEquals("close: malfunction", message.getMessage());
				message = logger.getNextMessage();
				assertEquals("close: unrecoverable malfunction", message.getMessage());
				assertTrue(message.hasCause());
			}
		}
		
		@Test(expected = TollboothException.class)
		public void gateThrowsExceptionAfterSwitchingToWillNotRespondState() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(0, 3, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			gate.open();
			try {
				gate.close();
			} catch (TollboothException e) {
				assertEquals("close: unrecoverable malfunction", e.getMessage());
				assertNotNull(e.getCause());
				throw e;
			}
		}
	
		@Test
		public void gateNotRespondWhenInWillNotRespondState() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(0, 3, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			LogMessage message;
			
			gate.open();
			flushLogger(logger);
			try {
				gate.close();
			} catch (Exception e) {
				flushLogger(logger);
			}
			gate.close();
			message = logger.getNextMessage();
			assertEquals("close: will not respond", message.getMessage());
		}
		
		@Test
		public void numberOfClosesDoesNotChange_InWillNotRespondState() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(0, 3, 0);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			gate.close();			
			int oldNumCloses = gate.getNumberOfCloses();
			try {
				gate.close();
			} catch (Exception e) {
				
			}
			assertEquals(oldNumCloses, gate.getNumberOfCloses());
		}	
	}
	
	public static class ResetTheGate {
		
		@Test
		public void gateResetsSuccessful() throws TollboothException
		{
			final GateController controller = new TestGateController();
			final SimpleLogger logger = new TollboothLogger();
			final TollGate gate = new TollGate(controller, logger);
			
			gate.reset();
			assertFalse(gate.isOpen());
			assertEquals("reset: successful", logger.getNextMessage().getMessage());
		}
		
		@Test
		public void gateResetsWithNoMalfunction() throws TollboothException
		{
			final GateController controller = new TestGateController();
			final SimpleLogger logger = new TollboothLogger();
			final TollGate gate = new TollGate(controller, logger);
			
			gate.open();
			gate.reset();
			assertFalse(gate.isOpen());
			assertEquals("reset: successful", logger.getNextMessage().getMessage());
		}
		
		@Test
		public void gateStaysCloseWhenAlreadyCloseAfterResetting() throws TollboothException
		{
			final GateController controller = new TestGateController();
			final TollGate gate = new TollGate(controller, null);
			
			gate.open();
			gate.close();
			gate.reset();
			assertFalse(gate.isOpen());
		}
				
		@Test
		public void gateResetsAfterOneMalfunction() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(0, 0, 1);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			gate.open();
			flushLogger(logger);
			gate.reset();
			LogMessage message = logger.getNextMessage();
			assertEquals("reset: malfunction", message.getMessage());
			message = logger.getNextMessage();
			assertEquals("reset: successful", message.getMessage());
			assertFalse(gate.isOpen());
		}
		
		@Test
		public void gateResetsAfterTwoMalfunction() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(0, 0, 2);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			gate.open();
			flushLogger(logger);
			
			gate.reset();
			LogMessage message = logger.getNextMessage();
			assertEquals("reset: malfunction", message.getMessage());
			message = logger.getNextMessage();
			assertEquals("reset: malfunction", message.getMessage());
			message = logger.getNextMessage();
			assertEquals("reset: successful", message.getMessage());
			assertFalse(message.hasCause());
			assertFalse(gate.isOpen());
		}
		
		@Test
		public void gateSwitchesToWillNotRespondState() throws TollboothException 
		{
			final GateController controller = new TestGateControllerMalfunction(0, 0, 3);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			gate.open();
			flushLogger(logger);
			try {
				gate.reset();
			} catch (TollboothException e) {
				LogMessage message = logger.getNextMessage();
				assertEquals("reset: malfunction", message.getMessage());
				message = logger.getNextMessage();
				assertEquals("reset: malfunction", message.getMessage());
				message = logger.getNextMessage();
				assertEquals("reset: malfunction", message.getMessage());
				message = logger.getNextMessage();
				assertEquals("reset: unrecoverable malfunction", message.getMessage());
				assertNotNull(message.getCause());
			}
		}
		
		@Test(expected = TollboothException.class)
		public void gateThrowsExceptionAfterSwitchingToWillNotRespondState() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(0, 0, 3);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			
			gate.open();
			try {
				gate.reset();
			} catch (TollboothException e) {
				assertEquals("reset: unrecoverable malfunction", e.getMessage());
				assertNotNull(e.getCause());
				throw e;
			}
		}
	/*
		@Test
		public void gateNotRespondWhenInWillNotRespondState() throws TollboothException
		{
			final GateController controller = new TestGateControllerMalfunction(0, 3);
			final SimpleLogger logger = new TollboothLogger();	
			final TollGate gate = new TollGate(controller, logger);
			LogMessage message;
			
			gate.open();
			flushLogger(logger);
			try {
				gate.close();
			} catch (Exception e) {
				flushLogger(logger);
			}
			gate.reset();
			message = logger.getNextMessage();
			assertEquals("reset: will not respond", message.getMessage());
		}
		*/
		
	}
	
	
	/**
	 * Dequeue multiple messages out of a logger
	 * @param logger the logger containing the messages
	 * @param numOfMessage number of message being dequeued
	 */
	private static void flushLogger(SimpleLogger logger) {
		while (logger.getNextMessage() != null);
	}
	
}

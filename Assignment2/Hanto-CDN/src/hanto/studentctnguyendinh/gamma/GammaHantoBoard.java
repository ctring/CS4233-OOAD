///*******************************************************************************
// * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
// * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
// * accompanying materials are made available under the terms of the Eclipse Public License
// * v1.0 which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// *******************************************************************************/
//package hanto.studentctnguyendinh.gamma;
//
//import static hanto.common.HantoPlayerColor.BLUE;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.Map;
//
//import hanto.common.HantoCoordinate;
//import hanto.common.HantoPiece;
//import hanto.studentctnguyendinh.common.HantoBoard;
//import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
//
///**
// * This is an implementation of a HantoBoard for the Gamma Hanto version.
// * @author Cuong Nguyen
// * @version April 7, 2016
// */
//public class GammaHantoBoard implements HantoBoard {
//	
//	private Map<HantoCoordinateImpl, HantoPiece> board = new HashMap<>();
//	
//	GammaHantoBoard() {
//		
//	}
//	
//	private GammaHantoBoard(Map<HantoCoordinateImpl, HantoPiece> board) {
//		this.board = new HashMap<HantoCoordinateImpl, HantoPiece>(board);
//	}
//	
//	/**
//	 * @return a copy of this board.
//	 */
//	protected HantoBoard makeCopy() {
//		return new GammaHantoBoard(board);
//	}
//	
//	@Override
//	public HantoPiece getPieceAt(HantoCoordinate coord) {
//		return board.get(new HantoCoordinateImpl(coord));
//	}
//
//	@Override
//	public void putPieceAt(HantoCoordinate coord, HantoPiece piece) {
//		board.put(new HantoCoordinateImpl(coord), piece);
//	}
//
//	@Override
//	public void movePiece(HantoCoordinate from, HantoCoordinate to) {
//		HantoPiece piece = getPieceAt(from);
//		board.remove(new HantoCoordinateImpl(from));
//		board.put(new HantoCoordinateImpl(to), piece);
//	}
//
//	@Override
//	public boolean validateConnectivity() {
//		if (board.isEmpty()) {
//			return true;
//		}
//		
//		LinkedList<HantoCoordinateImpl> queue = new LinkedList<>();
//		HashMap<HantoCoordinateImpl, Boolean> checked = new HashMap<>();
//		
//		HantoCoordinateImpl any = board.keySet().iterator().next();
//		queue.push(any);
//		checked.put(any, true);
//		
//		int count = 0;
//		
//		while (!queue.isEmpty()) {
//			HantoCoordinateImpl cur = queue.pop();
//			count++;
//			
//			HantoCoordinateImpl[] adj = cur.getAdjacentCoordsSet();
//			for (HantoCoordinateImpl coord : adj) {
//				if (board.containsKey(coord) && (checked.get(coord) == null)) {
//					queue.push(coord);
//					checked.put(coord, true);
//				}
//			}
//		}
//		
//		return count == board.size();
//	}
//
//	@Override
//	public String getPrintableBoard() {
//		int maxR = Integer.MIN_VALUE, minR = Integer.MAX_VALUE;
//		int maxC = Integer.MIN_VALUE, minC = Integer.MAX_VALUE;
//		for (HantoCoordinate coord : board.keySet()) {
//			maxR = Math.max(maxR, -(coord.getX() + 2 * coord.getY()));
//			minR = Math.min(minR, -(coord.getX() + 2 * coord.getY()));
//			maxC = Math.max(maxC, coord.getX());
//			minC = Math.min(minC, coord.getX());
//		}
//		
//		String hexes = "";
//		
//		for (int r = minR - 1; r <= maxR + 1; r++) {
//			for (int c = minC - 1; c <= maxC + 1; c++) {
//				if ((-r-c) % 2 == 0) {
//					int coordX = c;
//					int coordY = (-r - c) / 2;
//					HantoPiece pc = board.get(new HantoCoordinateImpl(coordX, coordY));
//					String pcString = "  ";
//					if (pc != null) {
//						pcString = getPieceString(pc);
//						if (coordX == 0 && coordY == 0) {
//							pcString = pcString.toUpperCase();
//						}
//					} 					
//					hexes += " " + pcString + " ";
//				}
//				else {
//					hexes += ">--<";
//				}
//			}
//			hexes += "\n";
//		}
//		
//		return hexes;
//	}
//	
//	private String getPieceString(HantoPiece pc) {
//		String pcstr = pc.getColor() == BLUE ? "b" : "r";
//		switch (pc.getType()) {
//			case BUTTERFLY: pcstr += "B";
//			break;
//			case SPARROW: pcstr += "S";
//			break;
//			/*case HORSE: pcstr += "H";
//			break;
//			case DOVE: pcstr += "D";
//			break;
//			case CRANE: pcstr += "R";
//			break;
//			case CRAB: pcstr += "C";
//			break;*/
//		}
//		return pcstr;
//	}
//	
//	
//}

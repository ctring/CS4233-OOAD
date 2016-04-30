//package hanto.studentctnguyendinh;
//
//import static hanto.common.HantoGameID.EPSILON_HANTO;
//import static hanto.common.HantoPieceType.BUTTERFLY;
//import static hanto.common.HantoPieceType.CRAB;
//import static hanto.common.HantoPieceType.HORSE;
//import static hanto.common.HantoPieceType.SPARROW;
//import static hanto.common.HantoPlayerColor.BLUE;
//import static hanto.common.HantoPlayerColor.RED;
//import static hanto.common.MoveResult.OK;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;
//
//import hanto.common.HantoCoordinate;
//import hanto.common.HantoException;
//import hanto.common.HantoPieceType;
//import hanto.common.HantoPlayerColor;
//import hanto.common.MoveResult;
//import hanto.studentctnguyendinh.common.HantoAI;
//import hanto.studentctnguyendinh.common.HantoCoordinateImpl;
//import hanto.studentctnguyendinh.common.HantoGameBase;
//
//public class HantoMain {
//
//	private static HantoGameBase game;
//	private static HantoAI ai;
//	
//	private static HantoPieceType piece;
//	private static HantoCoordinate from;
//	private static HantoCoordinate to;
//	private static Scanner scanner;
//	private static boolean doneReadingFromFile = true;
//	
//	public static void main(String[] args) {
//		initializeGame();
//		
//		boolean gameOver = false;
//		HantoPlayerColor currentPlayer = BLUE;
//		
//		if (args.length > 0) {
//			File inputFile = new File(args[0]);
//			try {
//				scanner = new Scanner(inputFile);
//			}
//			catch (FileNotFoundException e){
//				System.out.println("Error: Cannot find file " +  args[0]);
//				return;
//			}
//			doneReadingFromFile = false;
//		}
//		
//		do {
//			if (currentPlayer == BLUE) {
//				input();
//			}
//			else { 
//				inputAI();
//			}
//			try {
//			
//				System.out.print(currentPlayer + " makes move ");
//				printMove();
//				System.out.println();
//				
//				MoveResult mr = game.makeMove(piece, from, to);
//				
//				System.out.println(game.getPrintableBoard());
//				if (mr != OK) {
//					System.out.println("Game Over: " + mr);
//					gameOver = true;
//				}
//			} catch (HantoException e) {
//				System.out.println("Error: " + e.getMessage());
//				gameOver = true;
//			}
//			currentPlayer = currentPlayer == BLUE ? RED : BLUE;
//		} while (!gameOver);
//		scanner.close();
//	}
//	
//	private static void initializeGame() {
//		game = (HantoGameBase)HantoGameFactory.getInstance().makeHantoGame(EPSILON_HANTO, BLUE);
//		ai = new HantoAI();
//		game.registerAI(ai);
//	}
//	
//	private static void input() {
//		if (!doneReadingFromFile && !scanner.hasNextLine()) {
//			scanner.close();
//			scanner = new Scanner(System.in);
//			doneReadingFromFile = true;
//		}
//		if (doneReadingFromFile) {
//			System.out.println("Your turn: ");
//		}
//		int move = scanner.nextInt();
//		
//		if (move == 3) {
//			piece = null; from = null; to = null;
//		}
//		else {
//			piece = translatePieceType(scanner.next().charAt(0));
//			from = null;
//			if (move == 1) {
//				int fromX = scanner.nextInt();
//				int fromY = scanner.nextInt();
//				from = new HantoCoordinateImpl(fromX, fromY);
//			}
//			int toX = scanner.nextInt();
//			int toY = scanner.nextInt();
//			to = new HantoCoordinateImpl(toX, toY);
//		}
//	}
//	
//	private static void inputAI() {
//		System.out.println("AI's turn.");
//		piece = ai.getPiece();
//		from = ai.getFrom();
//		to = ai.getTo();
//	}
//	
//	private static void printMove() {
//		System.out.print("(" + piece + ", ");
//		if (from != null) {
//			System.out.print("[" + from.getX() + ", " + from.getY() + "], ");
//		}
//		else {
//			System.out.print("null, ");
//		}
//		System.out.print("[" + to.getX() + ", " + to.getY() + "])");		
//	}
//
//	
//	private static HantoPieceType translatePieceType(char p) {
//		HantoPieceType type;
//		switch (p) {
//			case 'b': type = BUTTERFLY;
//			break;
//			case 'c': type = CRAB;
//			break;
//			case 'h': type = HORSE;
//			break;
//			case 's': type = SPARROW;
//			break;
//			default: type = null;
//		}
//		return type;
//	}
//
//}

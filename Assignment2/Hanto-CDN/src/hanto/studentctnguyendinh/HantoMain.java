package hanto.studentctnguyendinh;

import static hanto.common.HantoGameID.EPSILON_HANTO;
import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.CRAB;
import static hanto.common.HantoPieceType.HORSE;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.*;

import java.util.Scanner;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentctnguyendinh.common.HantoCoordinateImpl;

public class HantoMain {

	private static HantoPieceType piece;
	private static HantoCoordinate from;
	private static HantoCoordinate to;
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		HantoGame game = HantoGameFactory.getInstance().makeHantoGame(EPSILON_HANTO, BLUE);
		boolean gameOver = false;
		HantoPlayerColor currentPlayer = BLUE;
		do {
			input(currentPlayer);
			currentPlayer = currentPlayer == BLUE ? RED : BLUE;
			try {
				MoveResult mr = game.makeMove(piece, from, to);
				System.out.println(game.getPrintableBoard());
				if (mr != OK) {
					System.out.println("Game Over: " + mr);
					gameOver = true;
				}
			} catch (HantoException e) {
				System.out.println("Error: " + e.getMessage());
				gameOver = true;
			}
		} while (!gameOver);
		scanner.close();
	}
	
	private static void input(HantoPlayerColor player) {
		System.out.println(player + "'s turn: ");
		int move = scanner.nextInt();
		
		if (move == 3) {
			piece = null; from = null; to = null;
		}
		else {
			piece = translatePieceType(scanner.next().charAt(0));
			from = null;
			if (move == 1) {
				int fromX = scanner.nextInt();
				int fromY = scanner.nextInt();
				from = new HantoCoordinateImpl(fromX, fromY);
			}
			int toX = scanner.nextInt();
			int toY = scanner.nextInt();
			to = new HantoCoordinateImpl(toX, toY);
		}
	}
	
	private static HantoPieceType translatePieceType(char p) {
		HantoPieceType type;
		switch (p) {
			case 'b': type = BUTTERFLY;
			break;
			case 'c': type = CRAB;
			break;
			case 'h': type = HORSE;
			break;
			case 's': type = SPARROW;
			break;
			default: type = null;
		}
		return type;
	}

}

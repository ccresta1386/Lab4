package pkgGame;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.junit.Test;

import pkgHelper.LatinSquare;

public class SudokuTest {

	private void PrintStars() {
		for (int i = 0; i < 50; i++)
			System.out.print("*");
		System.out.println();
	}

	@Test
	public void Sudoku() {
		// init vars
		boolean isPassed = false;
		int[][] puzzle = new int[9][9];
		int[] values = {1,2,3,4,5,6,7,8,9};
		
		try {
			//make puzzle
			Sudoku s = new Sudoku(9);
			
			//Do stuff
			s.PrintPuzzle();
			System.out.println("\n");
			int[] arr = s.getRegion(0);
			//
			
			//check if stuff is right
			if(s.isSudoku()) {//checks if its a valid sudoku ie. no duplicate values and contains all values in the range
				isPassed = true;
			}
			assertTrue(isPassed);
			s.PrintPuzzle();
			//
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(isPassed);
		}
	}

}

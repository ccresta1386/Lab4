//package pkgGame;
//
//import static org.junit.Assert.*;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//
//import org.junit.Test;
//
//public class SudokuPrivateMethodsTest {
//
//	private void PrintStars() {
//		for (int i = 0; i < 50; i++)
//			System.out.print("*");
//		System.out.println();
//	}
//
//	@Test
//	public void Sudoku_Test_SetRegion() {
//
//		Sudoku s1 = null;
//		int iPuzzleSize = 9;
//		int[] iExpectedRegion = new int[iPuzzleSize];
//		for (int i = 0; i < iPuzzleSize; i++) {
//			iExpectedRegion[i] = i + 1;
//		}
//		int[] iActualRegion;
//
//		try {
//			Class<?> c = Class.forName("pkgGame.Sudoku");
//			Constructor constructor = c.getConstructor(new Class[] { int.class });
//			constructor.setAccessible(true);
//			s1 = (Sudoku) constructor.newInstance(iPuzzleSize);
//
//			Method mSetRegion = c.getDeclaredMethod("SetRegion", new Class[] { int.class });
//
//			PrintStars();
//			System.out.println("Testing method: " + Thread.currentThread().getStackTrace()[1].getMethodName());
//			System.out.println("Original Puzzle:");
//			s1.PrintPuzzle();
//			System.out.println("Original Region 0:");
//			System.out.println(Arrays.toString(s1.getRegion(0)));
//
//			System.out.println("Set Puzzle:");
//			mSetRegion.setAccessible(true);
//			mSetRegion.invoke(s1, 0);
//			iActualRegion = s1.getRegion(0);
//
//			s1.PrintPuzzle();
//			System.out.println(Arrays.toString(s1.getRegion(0)));
//
//			assertTrue(Arrays.equals(iExpectedRegion, iActualRegion));
//
//			PrintStars();
//
//		} catch (ClassNotFoundException e1) {
//			fail("ClassNotFoundException");
//		} catch (NoSuchMethodException e) {
//			fail("NoSuchMethodException");
//		} catch (SecurityException e) {
//
//			fail("SecurityException");
//		} catch (InstantiationException e) {
//			fail("InstantiationException");
//		} catch (IllegalAccessException e) {
//			fail("IllegalAccessException");
//		} catch (IllegalArgumentException e) {
//			fail("IllegalArgumentException");
//		} catch (InvocationTargetException e) {
//			fail("InvocationTargetException, Invalid size");
//		}
//
//		PrintStars();
//
//	}
//
//	@Test
//	public void FillDiagonalRegions() {
//		
//		Sudoku s1 = null;
//		int iPuzzleSize = 9;
//		
//		try {
//			Class<?> c = Class.forName("pkgGame.Sudoku");
//			Constructor constructor = c.getConstructor(new Class[] { int.class });
//			constructor.setAccessible(true);
//			s1 = (Sudoku) constructor.newInstance(iPuzzleSize);
//
//			Method mFillDiagonalRegions = c.getDeclaredMethod("FillDiagonalRegions", null);
//
//			PrintStars();
//			System.out.println("Testing method: " + Thread.currentThread().getStackTrace()[1].getMethodName());
//			System.out.println("Original Puzzle:");
//			s1.PrintPuzzle();
//			System.out.println("Original Region 0:");
//			System.out.println(Arrays.toString(s1.getRegion(0)));
//
//			System.out.println("Set Puzzle:");
//			mFillDiagonalRegions.setAccessible(true);
//			mFillDiagonalRegions.invoke(s1, null);
//
//			s1.PrintPuzzle();
//			System.out.println(Arrays.toString(s1.getRegion(0)));
//
//			PrintStars();
//
//		} catch (ClassNotFoundException e1) {
//			fail("ClassNotFoundException");
//		} catch (NoSuchMethodException e) {
//			fail("NoSuchMethodException");
//		} catch (SecurityException e) {
//
//			fail("SecurityException");
//		} catch (InstantiationException e) {
//			fail("InstantiationException");
//		} catch (IllegalAccessException e) {
//			fail("IllegalAccessException");
//		} catch (IllegalArgumentException e) {
//			fail("IllegalArgumentException");
//		} catch (InvocationTargetException e) {
//			fail("InvocationTargetException, Invalid size");
//		}
//
//	}
//	@Test
//	public void setCells_Test1() {
//		int[][] puzzle = { {1,2,0,0}, {3,4,0,0}, {0,0,4,1}, {0,0,2,3}};
//		try {
//			Class<?> cs = Class.forName("pkgGame.Sudoku");
//			Constructor constructorS = cs.getConstructor(new Class[] { int.class });
//			constructorS.setAccessible(true);
//			Sudoku s1 = new Sudoku(puzzle);
//			s1.FillDiagonalRegions();
//			s1.PrintPuzzle();
//			s1.SetCells();
//			Method mgetCells = cs.getDeclaredMethod("getCells", null);
//			mgetCells.setAccessible(true);
//			System.out.println(mgetCells.invoke(s1));
//			HashMap cellset = (HashMap) mgetCells.invoke(s1);
//			System.out.println(cellset);
//			//Class<?> cc = Class.forName("pkgGame.Sudoku.Cell");
//			Constructor constructorCell = cc.getConstructor(new Class[] { int.class });
//			constructorCell.setAccessible(true);
//			Integer[] par1 = {1,1};
//			
//			//s1. cell3 = constructorCell.newInstance(par1);
//
//			
//			Method mgetLstValidValues = cc.getDeclaredMethod("getLstValidValues");
//			mgetLstValidValues.setAccessible(true);
//			System.out.println(cellset.get(3));
//			//System.out.println(mgetLstValidValues.invoke(cellset.get(3)));
//			//System.out.println(s1.cells.keySet());
//			//System.out.println(s1.cells.get(3).GetNextCell(s1.cells.get(3)).getiRow());
//			//System.out.println(s1.cells.get(3).GetNextCell(s1.cells.get(3)).getiCol());
//
//		} catch (ClassNotFoundException e1) {
//			fail("ClassNotFoundException");
//		} catch (NoSuchMethodException e) {
//			fail("NoSuchMethodException");
//		} catch (SecurityException e) {
//
//			fail("SecurityException");
//		} catch (IllegalAccessException e) {
//		fail("IllegalAccessException");
//	} catch (IllegalArgumentException e) {
//		fail("IllegalArgumentException");
//	} catch (InvocationTargetException e) {
//		fail("InvocationTargetException, Invalid size");
//
//		try {
//			Class<?> c = Class.forName("pkgGame.Sudoku");
//			Constructor constructor = c.getConstructor(new Class[] { int.class });
//			constructor.setAccessible(true);
//			s1 = (Sudoku) constructor.newInstance(iPuzzleSize);
//
//			Method mSetRegion = c.getDeclaredMethod("SetRegion", new Class[] { int.class });
//
//			PrintStars();
//			System.out.println("Testing method: " + Thread.currentThread().getStackTrace()[1].getMethodName());
//			System.out.println("Original Puzzle:");
//			s1.PrintPuzzle();
//			System.out.println("Original Region 0:");
//			System.out.println(Arrays.toString(s1.getRegion(0)));
//
//			System.out.println("Set Puzzle:");
//			mSetRegion.setAccessible(true);
//		mSetRegion.invoke(s1, 0);
//			iActualRegion = s1.getRegion(0);
//
//			s1.PrintPuzzle();
//		System.out.println(Arrays.toString(s1.getRegion(0)));
//
//			assertTrue(Arrays.equals(iExpectedRegion, iActualRegion));
//
//			PrintStars();
//
//		} catch (ClassNotFoundException e1) {
//			fail("ClassNotFoundException");
//		} catch (NoSuchMethodException e) {
//			fail("NoSuchMethodException");
//		} catch (SecurityException e) {
//
//			fail("SecurityException");
//		} catch (InstantiationException e) {
//			fail("InstantiationException");
//		} catch (IllegalAccessException e) {
//			fail("IllegalAccessException");
//		} catch (IllegalArgumentException e) {
//			fail("IllegalArgumentException");
//		} catch (InvocationTargetException e) {
//			fail("InvocationTargetException, Invalid size");
//		}
//		
//		}
//	}
//	@Test
//	public void fillRemaining_Test1() throws Exception {
//		int[][] puzzle = { {1,2,0,0}, {3,4,0,0}, {0,0,4,1}, {0,0,2,3}};
//		try {
//			Sudoku s1 = new Sudoku(9);
//			s1.PrintPuzzle();
//			System.out.println(s1.isSudoku());
//
//
//		} catch (ClassNotFoundException e1) {
//			fail("ClassNotFoundException");
//		} catch (NoSuchMethodException e) {
//			fail("NoSuchMethodException");
//		} catch (SecurityException e) {
//
//			fail("SecurityException");
//		} catch (IllegalAccessException e) {
//			fail("IllegalAccessException");
//		} catch (IllegalArgumentException e) {
//			fail("IllegalArgumentException");
//		} catch (InvocationTargetException e) {
//			fail("InvocationTargetException, Invalid size");
//		
//
//		
//		}
//	}
//}

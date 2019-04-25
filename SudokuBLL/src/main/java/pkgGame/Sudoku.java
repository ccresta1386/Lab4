package pkgGame;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import pkgHelper.LatinSquare;

/**
 * Sudoku - This class extends LatinSquare, adding methods, constructor to
 * handle Sudoku logic
 * 
 * @version 1.2
 * @since Lab #2
 * @author Bert.Gibbons
 *
 */
public class Sudoku extends LatinSquare {
	/**
	 * 
	 * iSize - the length of the width/height of the Sudoku puzzle.
	 * 
	 * @version 1.2
	 * @since Lab #2
	 */
	private int iSize;

	/**
	 * iSqrtSize - SquareRoot of the iSize. If the iSize is 9, iSqrtSize will be
	 * calculated as 3
	 * 
	 * @version 1.2
	 * @since Lab #2
	 */

	private int iSqrtSize;

	/**
	 * cells - cells of the sudoku
	 */

	private java.util.HashMap<java.lang.Integer, Cell> cells;

	/**
	 * Sudoku - for Lab #2... do the following:
	 * 
	 * set iSize If SquareRoot(iSize) is an integer, set iSqrtSize, otherwise throw
	 * exception
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @param iSize- length of the width/height of the puzzle
	 * @throws Exception if the iSize given doesn't have a whole number square root
	 */
	public Sudoku(int iSize) throws Exception {

		this.iSize = iSize;

		double SQRT = Math.sqrt(iSize);
		if ((SQRT == Math.floor(SQRT)) && !Double.isInfinite(SQRT)) {
			this.iSqrtSize = (int) SQRT;
		} else {
			throw new Exception("Invalid size");
		}
		int[][] puzzle = new int[iSize][iSize];
		super.setLatinSquare(puzzle);
		cells = new HashMap<Integer, Cell>();
		FillDiagonalRegions();
		SetCells();
		this.fillRemaining(this.cells.get(Objects.hash(0,iSqrtSize)));
	}

	/**
	 * Sudoku - pass in a given two-dimensional array puzzle, create an instance.
	 * Set iSize and iSqrtSize
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @param puzzle - given (working) Sudoku puzzle. Use for testing
	 * @throws Exception will be thrown if the length of the puzzle do not have a
	 *                   whole number square root
	 */
	public Sudoku(int[][] puzzle) throws Exception {
		super(puzzle);
		this.iSize = puzzle.length;
		double SQRT = Math.sqrt(iSize);
		if ((SQRT == Math.floor(SQRT)) && !Double.isInfinite(SQRT)) {
			this.iSqrtSize = (int) SQRT;
		} else {
			throw new Exception("Invalid size");
		}
		cells = new HashMap<Integer, Cell>();
		this.FillDiagonalRegions();
		Cell cell = new Cell(0,3);
		SetCells();
		this.fillRemaining(cell);
	}

	/**
	 * getPuzzle - return the Sudoku puzzle
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @return - returns the LatinSquare instance
	 */
	public int[][] getPuzzle() {
		return super.getLatinSquare();
	}

	/**
	 * getRegionNbr - Return region number based on given column and row
	 * 
	 * 
	 * Example, the following Puzzle:
	 * 
	 * 0 1 2 3 <br>
	 * 1 2 3 4 <br>
	 * 3 4 1 2 <br>
	 * 4 1 3 2 <br>
	 * 
	 * getRegionNbr(3,0) should return a value of 1
	 * 
	 * @param iCol - Given column number
	 * @param iRow - Given row number
	 * @version 1.3
	 * @since Lab #3
	 * 
	 * @return - return region number based on given column and row
	 */
	public int getRegionNbr(int iCol, int iRow) {

		int i = (iCol / iSqrtSize) + ((iRow / iSqrtSize) * iSqrtSize);

		return i;
	}

	/**
	 * getRegion - figure out what region you're in based on iCol and iRow and call
	 * getRegion(int)<br>
	 * 
	 * Example, the following Puzzle:
	 * 
	 * 0 1 2 3 <br>
	 * 1 2 3 4 <br>
	 * 3 4 1 2 <br>
	 * 4 1 3 2 <br>
	 * 
	 * getRegion(0,3) would call getRegion(1) and return [2],[3],[3],[4]
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @param iCol given column
	 * @param iRow given row
	 * @return - returns a one-dimensional array from a given region of the puzzle
	 */
	public int[] getRegion(int iCol, int iRow) {

		int i = (iCol / iSqrtSize) + ((iRow / iSqrtSize) * iSqrtSize);

		return getRegion(i);
	}

	/**
	 * getRegion - pass in a given region, get back a one-dimensional array of the
	 * region's content<br>
	 * 
	 * Example, the following Puzzle:
	 * 
	 * 0 1 2 3 <br>
	 * 1 2 3 4 <br>
	 * 3 4 1 2 <br>
	 * 4 1 3 2 <br>
	 * 
	 * getRegion(2) and return [3],[4],[4],[1]
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @param r given region
	 * @return - returns a one-dimensional array from a given region of the puzzle
	 */

	public int[] getRegion(int r) {

		int[] reg = new int[super.getLatinSquare().length];

		int i = (r % iSqrtSize) * iSqrtSize;
		int j = (r / iSqrtSize) * iSqrtSize;
		int iMax = i + iSqrtSize;
		int jMax = j + iSqrtSize;
		int iCnt = 0;

		for (; j < jMax; j++) {
			for (i = (r % iSqrtSize) * iSqrtSize; i < iMax; i++) {
				reg[iCnt++] = super.getLatinSquare()[j][i];
			}
		}

		return reg;
	}

	/**
	 * isPartialSudoku - return 'true' if...
	 * 
	 * It's a LatinSquare If each region doesn't have duplicates If each element in
	 * the first row of the puzzle is in each region of the puzzle At least one of
	 * the elemnts is a zero
	 * 
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @return true if the given puzzle is a partial sudoku
	 */
	public boolean isPartialSudoku() {

		if (!super.isLatinSquare()) {
			return false;
		}

		for (int k = 0; k < this.getPuzzle().length; k++) {

			if (super.hasDuplicates(getRegion(k))) {
				return false;
			}

			if (!hasAllValues(getRow(0), getRegion(k))) {
				return false;
			}
		}

		if (ContainsZero()) {
			return false;
		}

		return true;

	}

	/**
	 * isSudoku - return 'true' if...
	 * 
	 * Is a partialSudoku Each element doesn't a zero
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @return - returns 'true' if it's a partialSudoku, element match (row versus
	 *         column) and no zeros
	 */
	public boolean isSudoku() {

		if (!isPartialSudoku()) {
			return false;
		}

		if (ContainsZero()) {
			return false;
		}

		return true;
	}

	/**
	 * isValidValue - test to see if a given value would 'work' for a given column /
	 * row
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @param iCol   puzzle column
	 * @param iRow   puzzle row
	 * @param iValue given value
	 * @return - returns 'true' if the proposed value is valid for the row and
	 *         column
	 */
	public boolean isValidValue(int iRow, int iCol, int iValue) {

		if (doesElementExist(super.getRow(iRow), iValue)) {
			return false;
		}
		if (doesElementExist(super.getColumn(iCol), iValue)) {
			return false;
		}
		if (doesElementExist(this.getRegion(iCol, iRow), iValue)) {
			return false;
		}

		return true;
	}

	/**
	 * PrintPuzzle This method will print the puzzle to the console (space between
	 * columns, line break after row)
	 * 
	 * @version 1.3
	 * @since Lab #3
	 */
	public void PrintPuzzle() {
		for (int i = 0; i < this.getPuzzle().length; i++) {
			System.out.println("");
			for (int j = 0; j < this.getPuzzle().length; j++) {
				System.out.print(this.getPuzzle()[i][j]);
				if ((j + 1) % iSqrtSize == 0)
					System.out.print(" ");
			}
			if ((i + 1) % iSqrtSize == 0)
				System.out.println(" ");

		}
		System.out.println("");
	}

	/**
	 * FillDiagonalRegions - After the puzzle is created, set the diagonal regions
	 * with random values
	 * 
	 * @version 1.3
	 * @since Lab #3
	 */
	private void FillDiagonalRegions() {

		for (int i = 0; i < iSize; i = i + iSqrtSize + 1) {
			System.out.println("Filling region: " + getRegionNbr(i, i));
			SetRegion(getRegionNbr(i, i));
			ShuffleRegion(getRegionNbr(i, i));
		}
	}

	/**
	 * SetRegion - purpose of this method is to set the values of a given region
	 * (they will be shuffled later)
	 * 
	 * Example, the following Puzzle start state:
	 * 
	 * 0 0 0 0 <br>
	 * 0 0 0 0 <br>
	 * 0 0 0 0 <br>
	 * 0 0 0 0 <br>
	 * 
	 * SetRegion(2) would transform the Puzzle to:<br>
	 * 
	 * 0 0 0 0 <br>
	 * 0 0 0 0 <br>
	 * 1 2 0 0 <br>
	 * 3 4 0 0 <br>
	 * 
	 * @version 1.3
	 * @since Lab #3
	 * @param r - Given region number
	 */
	private void SetRegion(int r) {
		int iValue = 0;

		iValue = 1;
		for (int i = (r / iSqrtSize) * iSqrtSize; i < ((r / iSqrtSize) * iSqrtSize) + iSqrtSize; i++) {
			for (int j = (r % iSqrtSize) * iSqrtSize; j < ((r % iSqrtSize) * iSqrtSize) + iSqrtSize; j++) {
				this.getPuzzle()[i][j] = iValue++;
			}
		}
	}

	/**
	 * SetRegion - purpose of this method is to set the values of a given region
	 * (they will be shuffled later)
	 * 
	 * Example, the following Puzzle start state:
	 * 
	 * 1 2 0 0 <br>
	 * 3 4 0 0 <br>
	 * 0 0 0 0 <br>
	 * 0 0 0 0 <br>
	 * 
	 * ShuffleRegion(0) might transform the Puzzle to:<br>
	 * 
	 * 2 3 0 0 <br>
	 * 1 4 0 0 <br>
	 * 0 0 0 0 <br>
	 * 0 0 0 0 <br>
	 * 
	 * @version 1.3
	 * @since Lab #3
	 * @param r - Given region number
	 */
	private void ShuffleRegion(int r) {
		int[] region = getRegion(r);
		shuffleArray(region);
		int iCnt = 0;
		for (int i = (r / iSqrtSize) * iSqrtSize; i < ((r / iSqrtSize) * iSqrtSize) + iSqrtSize; i++) {
			for (int j = (r % iSqrtSize) * iSqrtSize; j < ((r % iSqrtSize) * iSqrtSize) + iSqrtSize; j++) {
				this.getPuzzle()[i][j] = region[iCnt++];
			}
		}
	}

	/**
	 * shuffleArray this method will shuffle a given one-dimension array
	 * 
	 * @version 1.3
	 * @since Lab #3
	 * @param ar given one-dimension array
	 */
	private void shuffleArray(int[] ar) {

		Random rand = new SecureRandom();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rand.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	/*
	 * fillRemaining - Recursive method to fill each cell
	 */
	private boolean fillRemaining(Sudoku.Cell c) {
		if(c == null)
			return true;
		for(int elem: c.getLstValidValues()) {
			if(isValidValue​(c, elem)) {
				this.getPuzzle()[c.getiRow()][c.getiCol()] = elem;
			if(fillRemaining(c.GetNextCell(c))) {
				return true;
			}
			this.getPuzzle()[c.getiRow()][c.getiCol()] = 0;
			}
		}
		return false;
	}

	/*
	 * getAllCellNumbers - This method will return all the valid values remaining
	 * for a given cell (by Col/Row).
	 */
	private java.util.HashSet<java.lang.Integer> getAllValidCellValues​(int iCol, int iRow) {
		
		if(this.getPuzzle()[iRow][iCol] > 0) {
			HashSet<Integer> validValues = new HashSet<Integer>();
			validValues.add(this.getPuzzle()[iRow][iCol]);
			return validValues;
		}
		HashSet<Integer> validValues = new HashSet<Integer>();
		for (int x = 1; x <= this.iSize; x++) {
			if (!super.doesElementExist(super.getColumn(iCol), x)) {
				if(!super.doesElementExist(super.getRow(iRow), x)) {
					Integer intobj = new Integer(x);
					validValues.add(intobj);
				}
			}
		}
		return validValues;
	}

	/*
	 * isValidValue - overload isValidValue, call by Cell
	 */
	public boolean isValidValue​(Sudoku.Cell c, int iValue) {
		if (c.getLstValidValues().contains(iValue)) {
			return true;
		} else
			return false;
	}

	/*
	 * SetCells - purpose of this method is to create a HashMap of all the cells in
	 * the puzzle.
	 */
	private void SetCells() {
		for(int x = 0; x < iSize; x++) {
			for(int y = 0; y < iSize; y++) {
				Cell c = new Cell(x,y);
				HashSet<java.lang.Integer> hsValidValues = this.getAllValidCellValues​(x, y);
				c.setlstValidValues​(hsValidValues);
				c.ShuffleValidValues();
				cells.put(c.hashCode(), c);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		return true;
	}

	private class Cell {

		private int iCol;
		private int iRow;
		private java.util.ArrayList<java.lang.Integer> lstValidValues;

		public Cell(int iCol, int iRow) {
			super();
			this.iCol = iCol;
			this.iRow = iRow;
			this.lstValidValues = new ArrayList<Integer>();
		}
		/*
		 * override to ensure object is equal by Row/Col
		 */

		@Override
		public boolean equals(Object o) {
			Cell cellA = (Cell) o;
			if (o instanceof Cell) {
				if (o == this) {
					return true;
				}
			}
			if (!(o instanceof Cell)) {
				return false;
			}
			return cellA.getiRow() == this.getiRow() && cellA.getiCol() == this.getiCol();
		}

		public int getiCol() {
			return iCol;
		}

		public int getiRow() {
			return iRow;
		}

		/*
		 * GetNextCell - get the next cell, return 'null' if there isn't a next cell to
		 * find This method will find the next valid value’s location in the puzzle, and
		 * return it in Cell form.
		 */
		public Sudoku.Cell GetNextCell(Sudoku.Cell c) {

			int row = c.getiRow();
			int col = c.getiCol() + 1;

			if (col >= iSize && row < iSize-1) {
				row++;
				col = 0;
			}
			if (col >= iSize && row >= iSize) {
				return null;
			}
			if(row < iSqrtSize) {
				if(col < iSqrtSize) {
					col = iSqrtSize;
				}
			}else if(row < iSize -iSqrtSize) {
				if(col == (int)(row/iSqrtSize) * iSqrtSize) {
					col += iSqrtSize;
				}
			}else {
				if(col == iSize - iSqrtSize) {
					row++;
					col = 0;
					if(row >= iSize) {
						return null;
					}
				}
				
			}

			return (Cell) cells.get(Objects.hash(col, row));
		}

		/*
		 * set hashcode to Objects.hash(iRow, iCol) (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
			return Objects.hash(iRow, iCol);
		}

		/*
		 * set the ArrayList of valid values using a HashSet of valid values. You can’t
		 * (easily) shuffle a HashSet
		 */
		public void setlstValidValues​(java.util.HashSet<java.lang.Integer> hsValidValues) {
			for (int x = 1; x <= iSize; x++) {
				if (hsValidValues.contains(x)) {
					lstValidValues.add(x);
				}
			}
		}

		/*
		 * return the ArrayList of valid values
		 */
		public java.util.ArrayList<java.lang.Integer> getLstValidValues() {
			return this.lstValidValues;
		}

		/*
		 * Shuffle the lstValidValues using Collections class
		 */
		public void ShuffleValidValues() {
			Collections.shuffle(lstValidValues);
		}

	}

}

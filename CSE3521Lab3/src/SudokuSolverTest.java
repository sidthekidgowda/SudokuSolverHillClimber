import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Vector;

import org.junit.Test;

/**
 * Test Cases for the Sudoku Solver using the hill Climber function
 * @author Sid Gowda
 * 11/17/2014
 *
 */

public class SudokuSolverTest {

	@Test
	public void testHillClimber() throws FileNotFoundException {
		Sudoku board = null;
		SudokuSolver agentHill;
		Vector<Integer> solStateHillClimber;
		
		//test 1 - Easy1.txt
		board = new Sudoku(new BufferedReader(new FileReader("easy1.txt")));
		agentHill = new SudokuSolver(board.getIndexesOfInitialBoard());
		solStateHillClimber = agentHill.hillClimber(board.getSudokuBoard());
		
		Vector<Integer> correctSol = new Vector<Integer>(Arrays.asList(2,1,4,3,4,3,2,1,3,2,1,4,1,4,3,2));
		assertEquals(solStateHillClimber,correctSol);
		
		//test2 - Easy2.txt
		board = new Sudoku(new BufferedReader(new FileReader("easy2.txt")));
		agentHill = new SudokuSolver(board.getIndexesOfInitialBoard());
		solStateHillClimber = agentHill.hillClimber(board.getSudokuBoard());
		
		correctSol = new Vector<Integer>(Arrays.asList(1,2,3,4,4,3,1,2,2,1,4,3,3,4,2,1));
		assertEquals(solStateHillClimber,correctSol);
		
		//test 3- Easy3.txt
		board = new Sudoku(new BufferedReader(new FileReader("easy3.txt")));
		agentHill = new SudokuSolver(board.getIndexesOfInitialBoard());
		solStateHillClimber = agentHill.hillClimber(board.getSudokuBoard());
		
		correctSol = new Vector<Integer>(Arrays.asList(3,4,1,2,2,1,4,3,1,2,3,4,4,3,2,1));
		assertEquals(solStateHillClimber,correctSol);
		
		//test 4 - Easy4.txt
		board = new Sudoku(new BufferedReader(new FileReader("easy4.txt")));
		agentHill = new SudokuSolver(board.getIndexesOfInitialBoard());
		solStateHillClimber = agentHill.hillClimber(board.getSudokuBoard());
		
		correctSol = new Vector<Integer>(Arrays.asList(1,2,3,4,4,3,1,2,3,4,2,1,2,1,4,3));
		assertEquals(solStateHillClimber,correctSol);
	
		//test 5 - Easy5.txt
		board = new Sudoku(new BufferedReader(new FileReader("easy5.txt")));
		agentHill = new SudokuSolver(board.getIndexesOfInitialBoard());
		solStateHillClimber = agentHill.hillClimber(board.getSudokuBoard());
		
		correctSol = new Vector<Integer>(Arrays.asList(4,2,3,1,1,3,4,2,3,1,2,4,2,4,1,3));
		assertEquals(solStateHillClimber,correctSol);
		
		
		//test 6 - Hard1.txt
		board = new Sudoku(new BufferedReader(new FileReader("hard1.txt")));
		agentHill = new SudokuSolver(board.getIndexesOfInitialBoard());
		solStateHillClimber = agentHill.hillClimber(board.getSudokuBoard());
		
		correctSol = new Vector<Integer>(Arrays.asList(3,2,4,1,1,4,3,2,2,3,1,4,4,1,2,3));
		assertEquals(solStateHillClimber,correctSol);
		
		
		
		//test 7 - Hard2.txt
		board = new Sudoku(new BufferedReader(new FileReader("hard2.txt")));
		agentHill = new SudokuSolver(board.getIndexesOfInitialBoard());
		solStateHillClimber = agentHill.hillClimber(board.getSudokuBoard());
		
		correctSol = new Vector<Integer>(Arrays.asList(4,1,3,2,2,3,4,1,1,4,2,3,3,2,1,4));
		assertEquals(solStateHillClimber,correctSol);
		
		
		//test 8 - Hard3.txt
		board = new Sudoku(new BufferedReader(new FileReader("hard3.txt")));
		agentHill = new SudokuSolver(board.getIndexesOfInitialBoard());
		solStateHillClimber = agentHill.hillClimber(board.getSudokuBoard());
		
		correctSol = new Vector<Integer>(Arrays.asList(3,2,1,4,1,4,3,2,2,1,4,3,4,3,2,1));
		assertEquals(solStateHillClimber,correctSol);
	
		
		//test 9 - Hard4.txt
		board = new Sudoku(new BufferedReader(new FileReader("hard4.txt")));
		agentHill = new SudokuSolver(board.getIndexesOfInitialBoard());
		solStateHillClimber = agentHill.hillClimber(board.getSudokuBoard());
		
		correctSol = new Vector<Integer>(Arrays.asList(1,4,2,3,2,3,4,1,4,1,3,2,3,2,1,4));
		assertEquals(solStateHillClimber,correctSol);
		
		
		//test 10 - Hard5.txt
		board = new Sudoku(new BufferedReader(new FileReader("hard5.txt")));
		agentHill = new SudokuSolver(board.getIndexesOfInitialBoard());
		solStateHillClimber = agentHill.hillClimber(board.getSudokuBoard());
		
		correctSol = new Vector<Integer>(Arrays.asList(2,4,1,3,3,1,4,2,4,3,2,1,1,2,3,4));
		assertEquals(solStateHillClimber,correctSol);
		
	
	}
	

}

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Vector;

import org.junit.Test;


/**
 * Test Cases for the SudokuSolverGA using the Genetic Algorithm
 * 
 * @author Sid Gowda
 * 11/17/2014
 *
 */
public class SudokuSolverGATest {

	@Test
	public void testGeneticAlgorithm() throws FileNotFoundException {
		
		Sudoku board = null;
		SudokuSolverGA agentGA;
		Vector<Integer> solStateGA;
		
		
		//test 1 - Easy1.txt
		board = new Sudoku(new BufferedReader(new FileReader("easy1.txt")));
		agentGA = new SudokuSolverGA(board.getSudokuBoard(),board.getIndexesOfInitialBoard());
		solStateGA = agentGA.geneticAlgorithm();
		
		Vector<Integer> correctSol = new Vector<Integer>(Arrays.asList(2,1,4,3,4,3,2,1,3,2,1,4,1,4,3,2));
		assertEquals(solStateGA,correctSol);
		
		
		//test2 - Easy2.txt
		board = new Sudoku(new BufferedReader(new FileReader("easy2.txt")));
		agentGA = new SudokuSolverGA(board.getSudokuBoard(),board.getIndexesOfInitialBoard());
		solStateGA = agentGA.geneticAlgorithm();
		
		correctSol = new Vector<Integer>(Arrays.asList(1,2,3,4,4,3,1,2,2,1,4,3,3,4,2,1));
		assertEquals(solStateGA,correctSol);
		
		
		//test 3- Easy3.txt
		board = new Sudoku(new BufferedReader(new FileReader("easy3.txt")));
		agentGA = new SudokuSolverGA(board.getSudokuBoard(),board.getIndexesOfInitialBoard());
		solStateGA = agentGA.geneticAlgorithm();
		
		correctSol = new Vector<Integer>(Arrays.asList(3,4,1,2,2,1,4,3,1,2,3,4,4,3,2,1));
		assertEquals(solStateGA,correctSol);
		
		
		//test 4 - Easy4.txt
		board = new Sudoku(new BufferedReader(new FileReader("easy4.txt")));
		agentGA = new SudokuSolverGA(board.getSudokuBoard(),board.getIndexesOfInitialBoard());
		solStateGA = agentGA.geneticAlgorithm();
		
		correctSol = new Vector<Integer>(Arrays.asList(1,2,3,4,4,3,1,2,3,4,2,1,2,1,4,3));
		assertEquals(solStateGA,correctSol);
	
		
		//test 5 - Easy5.txt
		board = new Sudoku(new BufferedReader(new FileReader("easy5.txt")));
		agentGA = new SudokuSolverGA(board.getSudokuBoard(),board.getIndexesOfInitialBoard());
		solStateGA = agentGA.geneticAlgorithm();
		
		correctSol = new Vector<Integer>(Arrays.asList(4,2,3,1,1,3,4,2,3,1,2,4,2,4,1,3));
		assertEquals(solStateGA,correctSol);
		
		
		
		//test 6 - Hard1.txt
		board = new Sudoku(new BufferedReader(new FileReader("hard1.txt")));
		agentGA = new SudokuSolverGA(board.getSudokuBoard(),board.getIndexesOfInitialBoard());
		solStateGA = agentGA.geneticAlgorithm();
		
		correctSol = new Vector<Integer>(Arrays.asList(3,2,4,1,1,4,3,2,2,3,1,4,4,1,2,3));
		assertEquals(solStateGA,correctSol);
		
		
		
		//test 7 - Hard2.txt
		board = new Sudoku(new BufferedReader(new FileReader("hard2.txt")));
		agentGA = new SudokuSolverGA(board.getSudokuBoard(),board.getIndexesOfInitialBoard());
		solStateGA = agentGA.geneticAlgorithm();
		
		correctSol = new Vector<Integer>(Arrays.asList(4,1,3,2,2,3,4,1,1,4,2,3,3,2,1,4));
		assertEquals(solStateGA,correctSol);
		
		
		//test 8 - Hard3.txt
		board = new Sudoku(new BufferedReader(new FileReader("hard3.txt")));
		agentGA = new SudokuSolverGA(board.getSudokuBoard(),board.getIndexesOfInitialBoard());
		solStateGA = agentGA.geneticAlgorithm();
		
		correctSol = new Vector<Integer>(Arrays.asList(3,2,1,4,1,4,3,2,2,1,4,3,4,3,2,1));
		assertEquals(solStateGA,correctSol);
	
		
		//test 9 - Hard4.txt
		board = new Sudoku(new BufferedReader(new FileReader("hard4.txt")));
		agentGA = new SudokuSolverGA(board.getSudokuBoard(),board.getIndexesOfInitialBoard());
		solStateGA = agentGA.geneticAlgorithm();
		
		correctSol = new Vector<Integer>(Arrays.asList(1,4,2,3,2,3,4,1,4,1,3,2,3,2,1,4));
		assertEquals(solStateGA,correctSol);
		
		
		//test 10 - Hard5.txt
		board = new Sudoku(new BufferedReader(new FileReader("hard5.txt")));
		agentGA = new SudokuSolverGA(board.getSudokuBoard(),board.getIndexesOfInitialBoard());
		solStateGA = agentGA.geneticAlgorithm();
		
		correctSol = new Vector<Integer>(Arrays.asList(2,4,1,3,3,1,4,2,4,3,2,1,1,2,3,4));
		assertEquals(solStateGA,correctSol);
		
		
	}

}

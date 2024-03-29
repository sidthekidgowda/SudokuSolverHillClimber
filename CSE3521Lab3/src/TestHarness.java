import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * Class TestHarness reads the sudoku problem from the file.
 * It then calls the Sudoku constructor to set up the initial state and 
 * then calls the hillClimber function, which passes back the solution state that it has found.
 * and that will be displayed to the screen.
 * 
 * @author Sid Gowda
 * 11/17/2014
 *
 */
public class TestHarness {

	//default constructor
	public TestHarness()
	{
		
	}
	
	/**
	 * Main method runs the hill climber program after reading in file input
	 * @param args
	 */
	public static void main(String[] args) {
		
		Sudoku board = null;
		SudokuSolver agentHill;
		Vector<Integer> solStateHillClimber;
		
		if(args.length != 1)
		{
			System.err.println("Too little arguments. Need an input file.");
			System.exit(1);//exit the program
		}
		try
		{
			/*
			 * parse the input file and create the initial Sudoku board
			 */
			board = new Sudoku(new BufferedReader(new FileReader(args[0])));
		}
		catch(IOException e)
		{
			System.err.println("Cannot read the input file" + args[0]);
			System.exit(1);
		}
		
		//create SudokuSolver object
		//call hill Climber function and geneticAlgorithm and they will return the solution state
		agentHill = new SudokuSolver(board.getIndexesOfInitialBoard());
		
		System.out.println("Sudoku problem solved using the Hill Climber Algorithm");
		solStateHillClimber = agentHill.hillClimber(board.getSudokuBoard());
		
		//print out the solution state of the hill climber
		System.out.println("Solution State of Hill Climber Algorithm:\n");
		for(int i = 0; i < solStateHillClimber.size();i++)
		{
			if(i % 4 == 0 && i!=0)
			{
				System.out.print("|\n");
				System.out.print("|"+solStateHillClimber.get(i));
			}
			else if(i == 15)
			{
				System.out.println("|"+solStateHillClimber.get(i)+"|");
			}
			else
			{
				System.out.print("|"+solStateHillClimber.get(i));
			}
		}

	}
}

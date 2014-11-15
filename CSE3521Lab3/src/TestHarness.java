import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class TestHarness reads the sudoku problem from the file.
 * It then calls the Sudoku constructor to set up the initial state and 
 * then calls the hillClimber function, which passes back the solution state that it has found.
 * and that will be displayed to the screen.
 * @author Sid
 *
 */
public class TestHarness {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Sudoku board;
		
		if(args.length != 2)
		{
			System.err.println("Too little arguments. Need an input file.");
			System.exit(1);//exit the program
		}
		
		try
		{
			/*
			 * parse the input file and create the initial sudoku board
			 */
			board = new Sudoku(new BufferedReader(new FileReader(args[0])));
		}
		catch(IOException e)
		{
			System.err.println("Cannot read the input file" + args[0]);
			System.exit(1);
		}
		
		
		/*
		 * Now create the hill Climber 
		 */
	}

}

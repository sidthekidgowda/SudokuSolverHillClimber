import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

/**
 * Class SudokuSolver solves the SudokuBoard using the hill Climber function combined with 
 * the random restart technique so a goal state will be found
 * @author Sid
 *
 */
public class SudokuSolver {
	private int numOfSteps;
	private Map<Integer,Integer> dontChangeIndexes;
	
	//random number generator
	private static Random ran;
	
	static
	{
		SudokuSolver.ran = new Random();
	}
		
	/**
	 * Constructor initializes the number of steps taken
	 * @param indexes
	 */
	public SudokuSolver(Map<Integer,Integer>indexes)
	{
		this.numOfSteps = 0;
		
		this.dontChangeIndexes = indexes;
	}
	
	/**
	 * hillClimber solves the SudokuPuzzle using the hillClimber algorithm 
	 * and random restart technique
	 * 
	 * 
	 * @param problem is the initial state of the board coming in
	 * @return the solution state
	 */
	public Vector<Integer>hillClimber(Vector<Integer> problem)
	{
		Vector<Integer> solution = problem;
		
		boolean randomRestart = true;
		boolean keepHillClimbing = true;
		int currentConflictValue;
		
		/**
		 * Continue looping until a solution is found
		 */
		while(randomRestart)
		{
			while(keepHillClimbing)
			{
				this.numOfSteps++;
				
				//print out state chosen
				System.out.println("Current State: "+solution.toString());
				
				//print out number of conflicts for the given state
				currentConflictValue = Sudoku.calculateNumOfConflicts(solution);
				System.out.println("Number of conflicts: " +currentConflictValue);
				
				//print out number of iterations taken so far
				System.out.println("Number of iterations taken: " +this.numOfSteps);
				System.out.println("");
				
				//create successor states in a list
				Map<Vector<Integer>,Integer> nStatesAndConflicts = this.generateNeighborStatesAndConflicts(solution);
				
				//find the neighbor with the lowest conflict value
				int lowest = 100;//arbitrary number to find lowest
				for(Integer value: nStatesAndConflicts.values())
				{
					int current = value;
					if(current < lowest)
						lowest = current;
				}

				
				//compare the current conflict value and neighbor conflict value
				//if the neighbor conflict value is less than the current conflict value
				//then set current equal to neighbor
				//else get out of while loop
				if(lowest < currentConflictValue)
				{
					for(Vector<Integer> board: nStatesAndConflicts.keySet())
					{
						if(nStatesAndConflicts.get(board).equals(lowest))
							{
								solution = board;
								break;
							}
					}
				}
				else
					keepHillClimbing = false;

			}

			currentConflictValue = Sudoku.calculateNumOfConflicts(solution);
			
			//check and see if hill climber hit a local minimum or global min
			if(currentConflictValue == 0)
			{
				randomRestart = false;
			}
			else
			{
				//create a new intial State
				solution = Sudoku.createNewInitialBoard(solution, this.dontChangeIndexes);
				int tempConflictValue = Sudoku.calculateNumOfConflicts(solution);
				
//				while(tempConflictValue > currentConflictValue)
//				{
//					solution = Sudoku.createNewInitialBoard(solution, this.dontChangeIndexes);
//					tempConflictValue = this.calculateNumOfConflicts(solution);
//				}
				currentConflictValue = tempConflictValue;
				keepHillClimbing = true;
			}
			
		}
		return solution;
	}
	
	/**
	 * 
	 * @param initState
	 * @return
	 */
	public Map<Vector<Integer>,Integer> generateNeighborStatesAndConflicts(Vector<Integer>initState)
	{
		Map<Vector<Integer>,Integer> neighbors = new HashMap<Vector<Integer>,Integer>();
		
		for(int i = 0; i < initState.size(); i++)
		{
			//dont replace initial numbers
			if(!this.dontChangeIndexes.containsKey(i))
			{
				//create a neighbor state
				Vector<Integer> neighborState = new Vector<Integer>(initState);

				int ranNum = ran.nextInt(4)+1;
				
				//replace number and add to neighbors
				neighborState.set(i, ranNum);
				
				int conflict = Sudoku.calculateNumOfConflicts(neighborState);
				neighbors.put(neighborState,conflict);
			}
		}

		return neighbors;
		
	}
		
	/**
	 * Used to test if the SudokuSolver solves the Sudoku Puzzle
	 * @param args
	 */
	public static void main(String[] args)
	{
		Sudoku testBoard;
		SudokuSolver agent;

		testBoard = new Sudoku(new BufferedReader(new StringReader("4\n24**\n*3**\n**4*\n**31")));
		
		agent = new SudokuSolver(testBoard.getIndexesOfInitialBoard());

		Vector<Integer>solState = agent.hillClimber(testBoard.getSudokuBoard());
		
		System.out.println("Solution State");
		//print out the solution state
		for(int i = 0; i < solState.size();i++)
		{
			if(i % 4 == 0 && i!=0)
			{
				System.out.print("|\n");
				System.out.print("|"+solState.get(i));
			}
			else if(i == 15)
			{
				System.out.println("|"+solState.get(i)+"|");
			}
			else
			{
				System.out.print("|"+solState.get(i));
			}
		}

	}
	
}

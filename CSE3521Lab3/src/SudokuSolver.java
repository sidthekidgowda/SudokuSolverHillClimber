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
	
	/**
	 * Constructor initializes the number of steps taken
	 */
	
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
				System.out.println(solution.toString());
				
				//print out number of conflicts for the given state
				currentConflictValue = this.calculateNumOfConflicts(solution);
				System.out.println(currentConflictValue);
				
				//print out number of iterations taken so far
				System.out.println(this.numOfSteps);
				
				//create successor states in a list
				Map<Integer,Vector<Integer>> nStatesAndConflicts = this.generateNeighborStatesAndConflicts(solution);
				
				//find the neighbor with the lowest conflict value
				int lowest = 100;//arbitary number to find lowest
				for(Integer key: nStatesAndConflicts.keySet())
				{
					int current = key;
					if(current < lowest)
						lowest = current;
				}

				
				//compare the current conflict value and neighbor conflict value
				//if the neighbor conflict value is less than the current conflict value
				//then set current equal to neighbor
				//else get out of while loop
				if(lowest < currentConflictValue)
					solution = nStatesAndConflicts.get(lowest);
				else
					keepHillClimbing = false;

			}

			currentConflictValue = this.calculateNumOfConflicts(solution);
			//check and see if hill climber hit a local minimum or not
			if(currentConflictValue == 0)//solution found
			{
				randomRestart = false;
			}
			else
			{
				//create a new intial State and restart hill climbing until solution is found
				solution = Sudoku.createNewInitialBoard(solution, this.dontChangeIndexes);
				currentConflictValue = this.calculateNumOfConflicts(solution);
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
	public Map<Integer,Vector<Integer>> generateNeighborStatesAndConflicts(Vector<Integer>initState)
	{
		Map<Integer,Vector<Integer>> neighbors = new HashMap<Integer,Vector<Integer>>();

		for(int i = 0; i < initState.size(); i++)
		{
			//dont replace initial numbers
			if(!this.dontChangeIndexes.containsKey(i))
			{
				//create a neighbor state
				Vector<Integer> neighborState = new Vector<Integer>(initState);
				
				//get random number
				Random ran = new Random();
				int ranNum = ran.nextInt(4)+1;
				
				//replace number and add to neighbors
				neighborState.set(i, ranNum);
				
				int conflict = this.calculateNumOfConflicts(neighborState);
				neighbors.put(conflict, neighborState);
			}
		}

		return neighbors;
		
	}
		
	/**
	 * This evaluation function scores each state vector based on how well it
	 * fits the sudoku board.
	 * 
	 * My way is to calculate num of conflicts by row, by col, and by square
	 * @return
	 */
	public int calculateNumOfConflicts(Vector<Integer> state)
	{
		int conflict = 0;
		
		Integer arr[] = {1,2,3,4};
		
		Set<Integer> checkSet = new HashSet<Integer>(Arrays.asList(arr));

		//calculate row conflicts
		for(int i = 0; i < state.size();i++)
		{			
			if(checkSet.contains(state.get(i)))
			{
				//remove element
				checkSet.remove(state.get(i));
			}
			else
			{
				//conflict
				conflict++;
			}
			
			if(i % 4 == 0)
			{
				//reinitialize set
				checkSet.clear();
				checkSet.addAll(Arrays.asList(arr));
			}
			
		}
		//reinitialize set
		checkSet.clear();
		checkSet.addAll(Arrays.asList(arr));
		
		//calculate col conflicts
		for(int i = 0; i < 4;i++)
		{
			for(int j = 0; j < state.size(); j+=4)
			{
				if(checkSet.contains(state.get(i)))
				{
					//remove element
					checkSet.remove(state.get(i));
				}
				else
				{
					//conflict
					conflict++;
					break;
				}
			}
			//reinitialize set
			checkSet.clear();
			checkSet.addAll(Arrays.asList(arr));
		}
		
		//calculate conflicts in 2x2 square
		//(0,1,4,5) (2,3,6,7) (8,9,12,13) (10,11,14,15)
		Set<Integer>square1 = new HashSet<Integer>(checkSet);
		Set<Integer>square2 = new HashSet<Integer>(checkSet);
		Set<Integer>square3 = new HashSet<Integer>(checkSet);
		Set<Integer>square4 = new HashSet<Integer>(checkSet);
		
		for(int i =0; i < state.size(); i++)
		{
			switch(i)
			{
			case 0://square1
			case 1:
			case 4:
			case 5:
				if(square1.contains(state.get(i)))
				{
					//remove element
					square1.remove(state.get(i));
				}
				else
				{
					//conflict
					conflict++;
				}
				
				break;
			case 2://square2
			case 3:
			case 6:
			case 7:
				if(square2.contains(state.get(i)))
				{
					//remove element
					square2.remove(state.get(i));
				}
				else
				{
					//conflict
					conflict++;
				}
				break;
			case 8://square3
			case 9:
			case 12:
			case 13:
				if(square3.contains(state.get(i)))
				{
					//remove element
					square3.remove(state.get(i));
				}
				else
				{
					//conflict
					conflict++;
				}
				break;
			case 10://square4
			case 11:
			case 14:
			case 15:
				if(square4.contains(state.get(i)))
				{
					//remove element
					square4.remove(state.get(i));
				}
				else
				{
					//conflict
					conflict++;
				}
				break;
			default:
				break;
			}
		}

		return conflict;
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
		testBoard.printBoard();
		
		agent = new SudokuSolver(testBoard.getIndexesOfInitialBoard());
		int conflicts = agent.calculateNumOfConflicts(testBoard.getSudokuBoard());
		
		System.out.println("\n"+conflicts);
		
		
		
		
		
	}
	
}

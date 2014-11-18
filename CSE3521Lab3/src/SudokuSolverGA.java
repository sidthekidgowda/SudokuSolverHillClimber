import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

/**
 * 
 * Class SudokuSolverGA solves the SudokuBoard using the Genetic Algorithm using the 1-Point
 * Crossover method and the Roulette Selection method
 * 
 * @author Sid
 *
 */
public class SudokuSolverGA {
	
	private Vector<Integer> sudokuBoard;
	private Map<Integer,Integer>dontChangeIndexes;
	private List<Integer> fitnessOfEachIndividual;
	

	
	private int bestFitIndex, worstFitIndex;
	
	private List<Vector<Integer>> population;
	
	private int generation;
	
	//initial population set to 150
	private static int popSize = 150;
	
	private static Random ran;
	
	static
	{
		SudokuSolverGA.ran = new Random();
	}

	/**
	 * Constructor of the Genetic Algorithm Sudoku Solver
	 * 
	 * @param board is the incoming Sudoku Board
	 * @param indexes are the initial Board state that should not be changed
	 */
	public SudokuSolverGA(Vector<Integer>board, Map<Integer,Integer> indexes)
	{
		this.sudokuBoard = board;
		this.dontChangeIndexes = indexes;
		
		this.generation = 0;
		
		this.population = new ArrayList<Vector<Integer>>();
		this.fitnessOfEachIndividual = new ArrayList<Integer>();
		
		
		this.bestFitIndex = this.worstFitIndex = 0;
		
		//create the initial population
		this.createInitialPopulation();
	}
	
	/**
	 * geneticAlgorithm solves the Sudoku Puzzle using the Genetic Algorithm
	 * 
	 * As generations are created, it optimizes towards individuals with higher fitnesses
	 * in the populations.
	 * 
	 * @return the solution
	 */
	public Vector<Integer> geneticAlgorithm()
	{
		Vector<Integer> solBoard = null;
		boolean solutionFound = false;
		
		//keep looping until the solution is found
		while(!solutionFound)
		{
			List<Vector<Integer>> newPopulation = new ArrayList<Vector<Integer>>();
			
			System.out.println("Generation: " + this.generation);
			System.out.println("Best Fit Individual in the Population: " + this.bestFitIndividual() + " and Score: " + this.fitnessOfEachIndividual.get(bestFitIndex));
			System.out.println("Worst Fit Individual in the Population: " + this.worstFitIndividual() + " and Score: "+ this.fitnessOfEachIndividual.get(worstFitIndex));
			System.out.println("");
			
			for(int i = 0; i < popSize; i++)
			{
				//randomly select two parents from the population in respect to fitness function
				Vector<Integer> parent1 = this.randomSelection();
				Vector<Integer> parent2 = this.randomSelection();
				
				//make sure the two parents are different
				while(parent1.equals(parent2))
				{
					parent2 = this.randomSelection();
				}

				//crossover
				Vector<Integer> child = this.reproduceChild(parent1,parent2);
				
				//if(small random probabily)then child = MUTATE(child)
				//probability to mutate is 1/16
				boolean mutate = new Random().nextInt(16)+1 == 1;
				if(mutate)
				{
					child = this.mutateChild(child);
				}
				
				//System.out.println("Child: " + child);
				newPopulation.add(child);
			}
			
			//clear the population
			this.population.clear();
			this.population.addAll(newPopulation);

			//calculate the fitness of each individual in the population 
			this.fitnessFunction();
			
			//the individual equals the MAX_FITNESS of an individual
			//means the algorithm solved the SudokuPuzzle and we can exit
			//the while loop
			if(this.fitnessOfEachIndividual.contains(120))
			{
				int index = this.fitnessOfEachIndividual.lastIndexOf(120);
				
				solBoard = this.population.get(index);
				solutionFound = true;
			}
			
			//increment generation
			this.generation++;

		}

		return solBoard;
	}
	
	/**
	 * This method creates the initial population and calculates the fitness of each individual
	 */
	private void createInitialPopulation()
	{
		for(int i = 0; i < popSize; i++)
		{
			Vector<Integer> board = new Vector<Integer>(Sudoku.createNewInitialBoard(this.sudokuBoard, this.dontChangeIndexes));
		
			this.population.add(board);
		}
		
		this.fitnessFunction();
		
	}
	
	/**
	 * This method calculates the fitness of each individual in the population.
	 * 
	 * MAX_FITNESS = SUM(CORRECT_ROWS)+SUM(CORRECT_COLS)+SUM(CORRECT_SQUARES)
	 * for 4x4 Sudoku Board, MAX_FITNESS = 40 + 40 + 40 = 120
	 * 
	 * a penalty of 1 is assessed if the row contains a duplicate with max penalty being 3
	 * and this penalty is applied to column and square respectively
	 */
	private void fitnessFunction()
	{
		//clear everything
		this.fitnessOfEachIndividual.clear();
		
		for(int i =0; i < popSize;i++)
		{
			int fitness;
			Vector<Integer> board = this.population.get(i);
			
			fitness = sumCorrectRows(board) + sumCorrectCols(board) + sumCorrectSquares(board);
			
			this.fitnessOfEachIndividual.add(fitness);
			
		}
	}
	/**
	 * Find the number of conflicts and subtract from the MAX_FITNESS(CORRECT_ROWS) = 40
	 * to find the sum of correct rows
	 * @param board
	 * @return the sum of the correct rows
	 */
	private int sumCorrectRows(Vector<Integer> board)
	{
		int conflict = 0;
		
		Integer arr[] = {1,2,3,4};
		
		Set<Integer> checkSet = new HashSet<Integer>(Arrays.asList(arr));

		//calculate row conflicts
		for(int i = 0; i < board.size();i++)
		{	
			if(i % 4 == 0 && i!=0)
			{
				//reinitialize set
				checkSet.clear();
				checkSet.addAll(Arrays.asList(arr));
			}
			if(checkSet.contains(board.get(i)))
			{
				//remove element
				checkSet.remove(board.get(i));
			}
			else
			{
				//conflict
				conflict++;
			}
			
		}
		
		return 40 - conflict;
		
	}
	
	/**
	 * Find the number of conflicts and subtract from the MAX_FITNESS(CORRECT_COLS) = 40
	 * to find the sum of correct columns
	 * @param board - Sudoku Board
	 * @return the sum of correct rows
	 */
	private int sumCorrectCols(Vector<Integer> board)
	{
		int conflict = 0;
		
		Integer arr[] = {1,2,3,4};
		
		Set<Integer> checkSet = new HashSet<Integer>(Arrays.asList(arr));
		//calculate col conflicts
		for(int i = 0; i < 4;i++)
		{
			int index = i;
			for(int j = 0; j < board.size(); j+=4)
			{
				int x = board.get(index);
				if(checkSet.contains(x))
				{
					//remove element
					checkSet.remove(x);
				}
				else
				{
					//conflict
					conflict++;
				}
				index+=4;
			}
			//reinitialize set
			checkSet.clear();
			checkSet.addAll(Arrays.asList(arr));
		}
				
		return 40 - conflict;
	}
	/**
	 * Find the number of conflicts and subtract from the MAX_FITNESS(CORRECT_SQUARES) = 40
	 * to find the sum of correct squares
	 * @param board
	 * @return
	 */
	private int sumCorrectSquares(Vector<Integer> board)
	{
		
		int conflict = 0;
		
		Integer arr[] = {1,2,3,4};
		
		Set<Integer> square1 = new HashSet<Integer>(Arrays.asList(arr));
		Set<Integer> square2 = new HashSet<Integer>(Arrays.asList(arr));
		Set<Integer> square3 = new HashSet<Integer>(Arrays.asList(arr));
		Set<Integer> square4 = new HashSet<Integer>(Arrays.asList(arr));
		
		//calculate conflicts in 2x2 square
		//(0,1,4,5) (2,3,6,7) (8,9,12,13) (10,11,14,15)
		
		for(int i =0; i < board.size(); i++)
		{
			switch(i)
			{
			case 0://square1
			case 1:
			case 4:
			case 5:
				if(square1.contains(board.get(i)))
				{
					//remove element
					square1.remove(board.get(i));
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
				if(square2.contains(board.get(i)))
				{
					//remove element
					square2.remove(board.get(i));
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
				if(square3.contains(board.get(i)))
				{
					//remove element
					square3.remove(board.get(i));
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
				if(square4.contains(board.get(i)))
				{
					//remove element
					square4.remove(board.get(i));
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
		
		return 40 - conflict;
		
	}

	/**
	 * This method calculates the total fitness of the current population
	 * 
	 * @return totalFitness
	 */
	private int totalFitness()
	{
		int sum = 0;
		
		for(int i =0; i < this.fitnessOfEachIndividual.size();i++)
		{
			sum += this.fitnessOfEachIndividual.get(i);
		}
		
		return sum;
	}
	
	/**
	 * This method selects a random individual using the Roulette Wheel Selection
	 * 
	 * @return parent selected according to its fitness
	 */
	private Vector<Integer>randomSelection()
	{		
		Vector<Integer> parent = null;
		
		int sum = 0;
		
		//calculate total fitness of the population
		int totalFitness = this.totalFitness();
		
		//get a random number between (0,TotalFitness)
		int r = ran.nextInt(totalFitness);

		//loop through the population and sum the fitness from 0 - sum s.
		//when sum s is greater than r. stop and return that number.
		for(int i =0; i < popSize; i++)
		{
			sum += this.fitnessOfEachIndividual.get(i);
			
			if(sum > r)
			{
				parent = this.population.get(i);
				break;
			}
		}

		return parent;
	}
	
	/**
	 * This method creates a child using the 1-Point Crossover 
	 * @param p1
	 * @param p2
	 * @return child of the two parents after the 1-Point Crossover
	 */
	private Vector<Integer> reproduceChild(Vector<Integer> p1,Vector<Integer> p2)
	{
		//get a number between 0 - 15
		int crossOver = ran.nextInt(16);
		
		Vector<Integer> child = new Vector<Integer>();
		
		//child = p2(0-(crossOver-1)+p1(crossOver-p1.Length)
		
		
		for(int i =0; i < p1.size(); i++)
		{
			if(i < crossOver)
			{
				child.add(p2.get(i));
			}
			else
			{
				child.add(p1.get(i));
			}
		}
		
		//child.addAll(p2.subList(0, crossOver));
		//child.addAll(p1.subList(crossOver, p1.size()-1));

		return child;
	}
	
	/**
	 * MutateChild changes a single number after it selects a random position in the board
	 * @param child
	 * @return
	 */
	private Vector<Integer>mutateChild(Vector<Integer>child)
	{
		Vector<Integer> mChild = new Vector<Integer>(child);
		
		int ranIndex = ran.nextInt(15);
		
		while(this.dontChangeIndexes.containsKey(ranIndex))
		{
			ranIndex = ran.nextInt(15);
		}
		
		int currentNum = mChild.get(ranIndex);
		int ranNum = ran.nextInt(4)+1;
		
		while(currentNum == ranNum)
		{
			ranNum = ran.nextInt(4)+1;
		}
		
		mChild.set(ranIndex, ranNum);
		
		return mChild;
	}
	
	
	/**
	 * Finds the best fit individual in the population and returns it
	 * @return
	 */
	private Vector<Integer> bestFitIndividual()
	{
		int set = this.fitnessOfEachIndividual.get(0);
		
		for(int i = 0; i < popSize; i++)
		{
			int better = this.fitnessOfEachIndividual.get(i);
			
			if(set < better)
			{
				set = better;
				this.bestFitIndex = i;
			}
		}

		return this.population.get(this.bestFitIndex);
		
	}
	
	/**
	 * This method finds the worst fit individual in the population and returns it
	 * @return
	 */
	private Vector<Integer> worstFitIndividual()
	{
		int set = this.fitnessOfEachIndividual.get(0);
		
		for(int i = 0; i < popSize; i++)
		{
			int lower = this.fitnessOfEachIndividual.get(i);
			if(set > lower)
			{
				set = lower;
				this.worstFitIndex = i;
			}
		}
		
		return this.population.get(this.worstFitIndex);
	}
	

	/**
	 * Main method to test
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		Sudoku testBoard;
		SudokuSolverGA agent;
		
		testBoard = new Sudoku(new BufferedReader(new StringReader("4\n24**\n*3**\n**4*\n**31")));
		agent = new SudokuSolverGA(testBoard.getSudokuBoard(),testBoard.getIndexesOfInitialBoard());
		
		Vector<Integer> solState = agent.geneticAlgorithm();
		
		System.out.println("Solution State using Genetic Algorithm");
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

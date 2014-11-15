import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;


/**
 * Class Sudoku represents the Sudoku board using the Vector class in a 1 dimensional format
 * @author Sid
 *
 */
public class Sudoku {

	//1D board represents the sudoku board
	private Vector<Integer> board;
	
	//this will hold the indexes of the original positions of the Sudoku board
	private Map<Integer,Integer> mapIndexToInitialBoard;
	
	/**
	 * @param bufferedReader 
	 * 
	 */
	public Sudoku(BufferedReader inputFile)
	{
		this.board = new Vector<Integer>();
		this.mapIndexToInitialBoard = new HashMap<Integer,Integer>();
		String readLine;
		
		try
		{
			int index = 0;
			while((readLine = inputFile.readLine())!=null)
			{
				/*
				 * Input comes in like this
				 * 4
				 * 24**
				 * *3**
				 * **4*
				 * **31	 
				 */
				
				char[] charArr = readLine.toCharArray();
				
				//check size of array
				//if the first line is 4, ignore
				if(charArr.length > 1)
				{
					for(int i = 0; i < charArr.length; i++)
					{
						//* are 0's in the sudoku board represented by the array
						if(charArr[i] == '*')
						{
							this.board.add(0);
						}
						//an integer is read
						else
						{
							this.board.add(Character.getNumericValue(charArr[i]));
							
							//add the index and value associated with it to the Map
							this.mapIndexToInitialBoard.put(index, Character.getNumericValue(charArr[i]));
						}
						index++;
					}
					
				}
			}

			//populate zeros into a random number from 1-4
			Sudoku.createNewInitialBoard(this.board, this.mapIndexToInitialBoard);
		}
		catch(IOException e)
		{
			System.err.println("Could not read the input: " + e.getMessage());
			System.exit(1);
		}
	}
	
	/**
	 * This method returns the SudokuBoard
	 * @return
	 */
	public Vector<Integer>getSudokuBoard()
	{
		return this.board;
	}
	
	/**
	 * This method returns the original indexes of the initial board
	 * @return
	 */
	public Map<Integer, Integer>getIndexesOfInitialBoard()
	{
		return this.mapIndexToInitialBoard;
	}
	
	
	/**
	 * This static method creates a new initial state of the SudokuBoard
	 * 
	 * @param board is the incoming Sudoku Board
	 * @param indexes are the initial indexes
	 * @return
	 */
	public static Vector<Integer> createNewInitialBoard(Vector<Integer> board, Map<Integer,Integer>indexes)
	{
		for(int i =0; i < board.size(); i++)
		{
			if(!indexes.containsKey(i))
			{
				Random ran = new Random();
				int ranNum = ran.nextInt(4)+1;
				
				//replace number
				board.set(i, ranNum);
			}
		}
		
		return board;
	}
	
	
	/**
	 * This function pretty prints the sudoku board. Used for testing only.
	 */
	public void printBoard()
	{
		for(int i = 0; i < this.board.size();i++)
		{
			if(i % 4 == 0 && i!=0)
			{
				System.out.print("|\n");
				System.out.print("|"+this.board.get(i));
			}
			else if(i == 15)
			{
				System.out.println("|"+this.board.get(i)+"|");
			}
			else
			{
				System.out.print("|"+this.board.get(i));
			}
		}
	}
	
	/**
	 * Used to test if the Sudoku board is initialized correctly
	 * @param args
	 */
	public static void main(String[] args)
	{
		Sudoku testBoard;

		testBoard = new Sudoku(new BufferedReader(new StringReader("4\n24**\n*3**\n**4*\n**31")));
		testBoard.printBoard();
	
	}
}

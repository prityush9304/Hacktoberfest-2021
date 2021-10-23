public class NQueenProblem {
	final int n = 4;

	void printSolution(int board[][])
	{
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				System.out.print(" " + board[i][j]
								+ " ");
			System.out.println();
		}
	}

	boolean isSafe(int board[][], int r, int c)
	{
		int i, j;

		for (i = 0; i < c; i++)
			if (board[r][i] == 1)
				return false;

		for (i = r, j = c; i >= 0 && j >= 0; i--, j--)
			if (board[i][j] == 1)
				return false;
		for (i = r, j = c; j >= 0 && i < n; i++, j--)
			if (board[i][j] == 1)
				return false;

		return true;
	}

	boolean solveNQUtil(int board[][], int col)
	{
		if (col >= n)
			return true;
		for (int i = 0; i < n; i++) {
			if (isSafe(board, i, col)) {
		
				board[i][col] = 1;

				if (solveNQUtil(board, col + 1) == true)
					return true;

				board[i][col] = 0; 
			}
		}

		return false;
	}

	boolean solveNQ()
	{
		int board[][] = { { 0, 0, 0, 0 },
						{ 0, 0, 0, 0 },
						{ 0, 0, 0, 0 },
						{ 0, 0, 0, 0 } };

		if (solveNQUtil(board, 0) == false) {
			System.out.print("Solution does not exist");
			return false;
		}

		printSolution(board);
		return true;
	}

	public static void main(String args[])
	{
		NQueenProblem Queen = new NQueenProblem();
		Queen.solveNQ();
	}
}

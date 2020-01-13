package kkx;

public class Solve {
	private char [] cache;
	private int [] data;
	private int[][] map;
	
	public final static int nodeNum = 9 * 9 * 4 + 9 * 9 * 9 * 4 + 1;
	public final static int colNum = 9 * 9 * 4;
	public final static int[] encode = { 1,2,4,8,16,32,64,128,256 };
	
	public Solve()
	{
		if(map==null)
		{
			map=new int[9][9];
		}
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				map[i][j]=0;
			}
		}
	}
	public char[] getSolution()
	{
		return this.cache;
	}
	public void FindSolution(String address) {
		// TODO Auto-generated method stub
		int i = 0;
		int goalNumber, nowNumber, result,index;
		String path;
		path = address;
		goalNumber = dealFile(path);
		index = 0;
		result = 0;
		for (nowNumber = 0; nowNumber < goalNumber; nowNumber++) {
			solveUnit(index);
			toCache(nowNumber, result);
		}
		cache[result++] = '\0';
		
	}
	

	private void toCache(int nowNumber, int result) {
		// TODO Auto-generated method stub
		int i, j;
		if (nowNumber != 0) {
			cache[result++] = '\n';
		}
		for (i = 0; i < 9; i++) {
			for (j = 0; j < 9; j++) {
				if (j != 0) {
					cache[result++] = ' ';
				}
				cache[result++] = (char) (map[i][j] + '0');
			}
			cache[result++] = '\n';
		}
		return;
	}

	private void solveUnit(int index) {
		// TODO Auto-generated method stub
		int[] criterion=new int[27];
		initialB(criterion, index);
		dealingB(criterion, 0, 0);
		return;
	}

	private boolean dealingB(int[] criterion, int i, int j) {
		// TODO Auto-generated method stub
		int k;
		boolean state = false;
		for (; i < 9; i++) {
			for (; j < 9; j++) {
				if (map[i][j] == 0) {
					state = true;
					break;
				}
			}
			if (state) {
				break;
			}
			j = 0;
		}
		if (!state) {
			return true;
		}
		int value;
		state = false;
		for (k = 0; k < 9; k++) {
			value = k + 1;
			if (!fill(i + 1, j + 1, value, criterion)) {
				continue;
			}
			map[i][j] = value;
			removeA(criterion, i + 1, j + 1, value);
			state = dealingB(criterion, i, j);
			if (state) {
				break;
			}
			recoverA(criterion, i + 1, j + 1, value);
			map[i][j] = 0;
		}
		return state;
	}

	private boolean fill(int row, int col, int value, int[] criterion) {
		// TODO Auto-generated method stub
		int temp = criterion[row - 1] & criterion[9 + col - 1] & criterion[18 + (getblock(row, col) - 1)] & encode[value - 1];
		if (temp != 0) {
			return true;
		}
		return false;
	}

	private void recoverA(int[] criterion, int row, int col, int value) {
		// TODO Auto-generated method stub
		criterion[0 + row - 1] = criterion[row - 1] | (encode[value - 1]);
		criterion[9 + col - 1] = criterion[9 + col - 1] | (encode[value - 1]);
		criterion[18 + (getblock(row, col) - 1)] = criterion[18 + (getblock(row, col) - 1)] | (encode[value - 1]);

		
	}

	private void initialB(int[] criterion, int index) {
		// TODO Auto-generated method stub
		int i, j;
		for (i = 0; i < 27; i++) {
			criterion[i] = 511;
		}
		for (i = 0; i < 9; i++) {
			for (j = 0; j < 9; j++) {
				map[i][j] = data[index++];
				if (map[i][j] != 0) {
					removeA(criterion, i + 1, j + 1, map[i][j]);
				}
			}
		}
	}

	private void removeA(int[] criterion, int row, int col, int value) {
		// TODO Auto-generated method stub
		criterion[0 + row - 1] = criterion[row - 1] & (~encode[value - 1]);
		criterion[9 + col - 1] = criterion[9 + col - 1] & (~encode[value - 1]);
		criterion[18 + (getblock(row, col) - 1)] = criterion[18 + (getblock(row, col) - 1)] & (~encode[value - 1]);
	}

	private int dealFile(String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	int getblock(int row, int col)
	{
		return (row - 1) / 3 * 3 + (col + 2) / 3;
	}

}

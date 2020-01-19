package kkx;

/**
 * @ClassName: Generate
 * @Description: 生成不同的终局
 * @author WangKeXin
 * @date 2020年1月15日 下午9:10:25
 *
 */
public class Generate {
	private int[] seed= {5,1,2,3,4,6,7,8,9};
	private int[] index={0,1,2,3,4,5,6,7,8,0,1,2,3,4,5,6,7,8};
	private int goalNum;
	private int nowNum;
	private int posi;
	private int[][] sudoku;
	private char[] output;

	/**
	 * @Title: getOutput
	 * @Description: 返回所有的终局
	 * @param @return
	 * @return char[]
	 * @throws
	 */
	public char[] getOutput()
	{
		return this.output;
	}
	public int[] getSeed(){return this.seed;}
	public void setGoal(int goal){this.goalNum=goal;}
	public void setPos(int pos){this.posi=pos;}
	public void setoutput(char[] a){this.output=a;}
	public int[][] getSudoku(){return sudoku;}
	public void setSudoku(int [][] a){this.sudoku=a;}
	public void setSeed(int[] a){this.seed=a;}
	public Generate()
	{
		posi=0;
		if(sudoku==null)
			sudoku=new int[9][9];
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				sudoku[i][j]=0;
	}

	/**
	 * @Title: generateSudoku
	 * @Description: 生成终局
	 * @param @param count
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean generateSudoku(int count)  {
		this.goalNum=count;
		int temp=(9*9*2+1)*count;
		output=new char[temp+100];
		createSeed(1);
		return true;
	}

	/**
	 * @Title: createSeed
	 * @Description: 将第一宫作为种子，创造种子
	 * @param @param cursor
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean createSeed(int cursor) {
		if(cursor==8)
		{
			createMap();
		}else {
			for (int i = cursor; i <= 8; i++) {
				swap(seed,cursor,i);
				createSeed(cursor + 1);
				if (nowNum == goalNum) break;
				swap(seed, cursor, i);
			}
		}
		return true;
	}

	/**
	 * @Title: createMap
	 * @Description: 通过平移生成数独
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean createMap() {
		int k=0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				sudoku[i][j] = seed[k++];  //在第一宫
				for (int l = 0; l < 3; l++) {
					for (int m = 0; m < 3; m++) {
						sudoku[(i + m) % 3 + 3 * l][(j + l) % 3 + m * 3] = sudoku[i][j];
					}
				}
			}
		}
		permutation();
		return true;
	}

	/**
	 * @Title: Permutation
	 * @Description: 对终局行列交换
	 * @param
	 * @return void
	 * @throws
	 */
	public void permutation() {
		changeIndex(3, 5);
	}

	/**
	 * @Title: changeIndex
	 * @Description: 对宫内的行列进行全排列
	 * @param @param a
	 * @param @param start
	 * @param @param end
	 * @return void
	 * @throws
	 */
	public void changeIndex( int start, int end) {
		int i;
		//分段进行全排列
		if (start == end) {
			if (end == 5) {
				changeIndex( 6, 8);
			}else if(end==8){
				changeIndex( 12, 14);
			}else if(end==14){
				changeIndex( 15, 17);
			}
			else {
				writeToOutput();
			}
		}
		else {
			for (i = start; i <= end; i++) {
				swap(index, start, i);
				changeIndex( start + 1, end);
				if (nowNum == goalNum) break;
				swap(index, start, i);

			}
		}
	}
	/**
	 * @Title: swap
	 * @Description: 宫内进行全排列
	 * @param  array
	 * @param  a
	 * @param  b
	 * @return void
	 * @throws
	 */
	public void swap(int[] array, int a, int b) {
		int temp;
		temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}

	/**
	 * @Title: writrToOutput
	 * @Description:
	 * @param
	 * @return void
	 * @throws
	 */
	public void writeToOutput() {
		if (nowNum > 0) {
			output[posi++] = '\n';
			output[posi++] = '\n';
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (j == 0) {
					output[posi++] = (char) (sudoku[index[i]][index[j+9]] +'0');
				}
				else {
					output[posi++] = ' ';
					output[posi++] = (char) (sudoku[index[i]][index[j+9]] +'0');
				}
			}
			if(i==8)
				break;
			output[posi++] = '\n';
		}
		nowNum++;
	}
}

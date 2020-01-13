package kkx;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Generate {
	private int goalNum;
	private int nowNum;
	private int[] seed= {7,1,2,3,4,5,6,8,9};
	private int[][] sudoku;
	private int[][] result;
	private int target;
	private int[] index={ 0,1,2,3,4,5,6,7,8,0,1,2,3,4,5,6,7,8 };
	private char[] output;
	
	public char[] getSudoku()
	{
		return this.output;
	}
	public Generate()
	{
		if(sudoku==null)
			sudoku=new int[9][9];
		if(result==null)
			result=new int [9][9];
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				result[i][j]=0;
				sudoku[i][j]=0;
			}
		}
	}
	public boolean GenerateSudoku(int count)  {
		// TODO Auto-generated method stub
		this.goalNum=count;
		int temp=163*count+100;
		output=new char[temp];
		CreateSeed(1);
		return true;
		
	}

	
	//对seed进行全排列
	private boolean CreateSeed(int cursor) {  
		// TODO Auto-generated method stub
		if(cursor==8)
		{
			CreateMap();
		}else {
			for (int i = cursor; i <= 8; i++) {
				swap(seed,cursor,i);
				CreateSeed(cursor + 1);
				if (nowNum == nowNum) break;
				swap(seed, cursor, i);
				
			}
		}
		return true;
	}
	//利用平移生成数独
	private boolean CreateMap() {
		// TODO Auto-generated method stub
		int i, j, k, l, m;
		for (i = 0, k = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				sudoku[i][j] = seed[k++];
				for (l = 0; l < 3; l++) {
					for (m = 0; m < 3; m++) {
						sudoku[(i + m) % 3 + 3 * l][(j + l) % 3 + m * 3] = sudoku[i][j];
					}
				}
			}	
		}
		changeMap();
		return true;
	}
	private void changeMap() {
		// TODO Auto-generated method stub
		changePartly(index, 3, 5);
		return;
	}
	
	private void changePartly(int[] a, int start, int end) {
		// TODO Auto-generated method stub
		int i;
		if (start == end) {
			if (end == 5) {
				changePartly(a, 6, 8);
			}
			else if (end == 8) {
				changePartly(a, 12, 14);
			}
			else if (end == 14) {
				changePartly(a, 15, 17);
			}
			else {
				outputResult();
			}
		}
		else {
			for (i = start; i <= end; i++) {
				swap(a, start, i);
				changePartly(a, start + 1, end);
				if (nowNum == goalNum) break;
				swap(a, start, i);
				
			}
		}
		return;
	}
	private void swap(int[] goalArray, int a, int b) {
		// TODO Auto-generated method stub
		int temp;
		temp = goalArray[a];
		goalArray[a] = goalArray[b];
		goalArray[b] = temp;
	}
	
	private void outputResult() {
		// TODO Auto-generated method stub
		int i, j;
		if (nowNum > 0) {
			output[target++] = '\n';
		}
		for (i = 0; i < 9; i++) {
			for (j = 0; j < 9; j++) {
				if (j == 0) {
					output[target++] = (char) (sudoku[index[i]][index[j + 9]] +'0');
				}
				else {
					output[target++] = ' ';
					output[target++] = (char) (sudoku[index[i]][index[j + 9]] +'0');
				}
			}
			output[target++] = '\n';	
		}
		nowNum++;
		return;
	}

}

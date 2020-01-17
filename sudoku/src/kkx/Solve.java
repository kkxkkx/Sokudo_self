package kkx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Solve {
	private char [] output;
	private int [] data;
	private int[][] map;
	//criterionǰ9λ��ʾ�У��м��λ��ʾ�У�����λ��ʾ��
	//ÿ��������ת����һ��9λ����������0��ʾ�Ѿ��ù���1��ʾû���ù�
	private int[] criterion;
	private int posi;
	private int index;
	private int goalNumber;
	public final static int[] encode = { 1,2,4,8,16,32,64,128,256 };

	public int getGoalNumber(){return goalNumber;}
	public int[] getCriterion(){return this.criterion;}
	public void setCriterion(int []a){this.criterion=a;}
	public void setData(int[] da){ this.data=da;System.out.println("data");}
	public Solve()
	{
		index = 0;
		posi = 0;
		if(map==null)
		{
			map=new int[9][9];
		}
		if(criterion==null)
		{
			criterion=new int[27];
		}
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				map[i][j]=0;
			}
		}
	}

	/**
	 * @Title: getSolution
	 * @Description: ���������Ľ������
	 * @return char[]
	 * @throws
	 */
	public char[] getSolution()
	{
		return this.output;
	}

	/**
	 * @Title: FindSolution
	 * @Description: �ҵ������Ĵ�
	 * @param path
	 * @return void
	 * @throws
	 */
	public void FindSolution(String path) {
		// TODO Auto-generated method stub
		int nowNumber;
		data=new int[1_000_000*81*2];
		goalNumber = DealFile(path);
		output=new char[goalNumber*81*3];
		for (nowNumber = 0; nowNumber < goalNumber; nowNumber++) {
			SolveSoduku();
			WriteToOutput(nowNumber);
		}
	}


	/**
	 * @Title: WriteToOutput
	 * @Description: �����ֱ��char������output��
	 * @param nowNumber
	 * @param  nowNumber
	 * @return void
	 * @throws
	 */
	public void  WriteToOutput(int nowNumber) {
		// TODO Auto-generated method stub
		int i, j;
		if (nowNumber != 0) {
			output[posi++] = '\n';
			output[posi++] = '\n';
		}
		for (i = 0; i < 9; i++) {
			for (j = 0; j < 9; j++) {
				if (j != 0) {
					output[posi++] = ' ';
				}
				output[posi++] = (char) (map[i][j] + '0');
			}
			if(i==8)
				break;
			output[posi++] = '\n';
		}
		return;
	}

	/**
	 * @Title: SolveSoduku
	 * @Description: �������
	 * @return void
	 * @throws
	 */
	public void SolveSoduku() {
		// TODO Auto-generated method stub
		InitCriterion();
		DealingCriterion(0, 0);
		return;
	}

	/**
	 * @Title: DealingCriterion
	 * @Description: �����������
	 * @param  row
	 * @param  col
	 * @return boolean
	 * @throws
	 */
	public boolean  DealingCriterion( int row, int col) {
		// TODO Auto-generated method stub
		//�Ƿ���0
		boolean state = false;
		for (; row < 9; row++) {
			for (; col < 9; col++) {
				if (map[row][col] == 0) {
					state = true;
					break;
				}
			}
			if (state)
				break;
			col = 0;
		}
		if (!state) {
			return true;
		}

		int value;
		state = false;
		for (int k = 0; k < 9; k++) {
			value = k + 1;
			if (!Fill(row , col, value)) {
				continue;
			}
			map[row][col] = value;
			UsedNum( row , col, value);
			state =  DealingCriterion(row, col);
			if (state) {
				break;
			}
			//����������
			ReleaseNum(row , col, value);
			map[row][col] = 0;
		}
		return state;
	}

	/**
	 * @Title: Fill
	 * @Description: �ж��ǲ���0
	 * @param row
	 * @param col
	 * @param value
	 * @return boolean
	 * @throws
	 */
	public boolean Fill(int row, int col, int value) {
		// TODO Auto-generated method stub
		int temp = criterion[row] & criterion[9 + col] & criterion[18 + (GetBlock(row, col) - 1)] & encode[value - 1];
		if (temp != 0) {
			return true;
		}
		return false;
	}

	/**
	 * @Title: ReleaseNum
	 * @Description: ����֮ǰѡ������֣���������λ��Ϊ0
	 * @param row
	 * @param col
	 * @param value
	 * @return void
	 * @throws
	 */
	public void ReleaseNum(int row, int col, int value) {
		// TODO Auto-generated method stub
		criterion[row] = criterion[row ] | (encode[value - 1]);
		criterion[9 + col ] = criterion[9 + col] | (encode[value - 1]);
		criterion[18 + (GetBlock(row, col) - 1)] = criterion[18 + (GetBlock(row, col) - 1)] | (encode[value - 1]);
	}

	/**
	 * @Title: InitCriterion
	 * @Description: �����������ö����Ʊ�ʾ
	 * @return void
	 * @throws
	 */
	public void InitCriterion() {
		// TODO Auto-generated method stub
		int i, j;
		for (i = 0; i < 27; i++) {
			//�����Ʊ�ʾ�պ�9λ��ÿһλ����1,1��ʾѡ����0��ʾû��ѡ��
			criterion[i] = encode[8]*2-1;
		}
		for (i = 0; i < 9; i++) {
			for (j = 0; j < 9; j++) {
				map[i][j] = data[index++];
				if (map[i][j] != 0) {
					UsedNum(i , j , map[i][j]);
				}
			}
		}
	}

	/**
	 * @Title: UsedNum
	 * @Description: ����λ��������ʹ�ù���������Ϊ0,
	 * @param row
	 * @param col
	 * @param value
	 * @return void
	 * @throws
	 */
	public void UsedNum( int row, int col, int value) {
		// TODO Auto-generated method stub
		//ÿһλ����������
		criterion[row] = criterion[row] & (~encode[value - 1]);
		criterion[9 + col ] = criterion[9 + col] & (~encode[value - 1]);

		criterion[18 + (GetBlock(row, col) - 1)] = criterion[18 + (GetBlock(row, col) - 1)] & (~encode[value - 1]);
	}

	/**
	 * @Title: DealFile
	 * @Description: ��ȡ�ļ��е�����
	 * @param @param path
	 * @return int
	 * @throws
	 */
	public int DealFile(String path)  {
		// TODO Auto-generated method stub
		int len = 0;
		int number = 0;
		int count=0;
		File file = new File(path);
		BufferedReader buffer;
		try {
			buffer = new BufferedReader(new FileReader(file));
			String s=buffer.readLine();
			while(s!=null)
			{
				boolean flag=true;
				for (int i = 0; i < s.length(); i++) {
					flag=false;
					if (s.charAt(i) == ' ')
						continue;
					if(s.charAt(i)=='0')
					{
						data[len++]=0;
					}
					else
						data[len++]=s.charAt(i)-'0';
				}
				if(flag)
					number++;
				s=buffer.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		number++;
		return number;
	}

	/**
	 * @Title: GetBlock
	 * @Description: �õ���ǰ�����ڵͼ���
	 * @param row
	 * @param col
	 * @return int
	 * @throws
	 */
	public int GetBlock(int row, int col)
	{
		return row  / 3 * 3 + (col + 3) / 3;
	}

}

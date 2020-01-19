package kkx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @ClassName: Solve
 * @Description: �ж�������Ƿ���ȷ
 * @author WangKeXin
 * @date 2020��01��13�� ����9:40:20
 *
 */
public class Solve {
	private int[][] map;
	//criterionǰ9λ��ʾ�У��м��λ��ʾ�У�����λ��ʾ��
	//ÿ��������ת����һ��9λ����������0��ʾ�Ѿ��ù���1��ʾû���ù�
	private int[] criterion;
	protected static final int[] encode = { 1,2,4,8,16,32,64,128,256 };

	public void setCriterion(int []a){this.criterion=a;}
	public Solve()
	{
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
	 * @Title: dealWithCriterion
	 * @Description: �����Ƿ��п�λҪ���
	 * @param  row
	 * @param  col
	 * @return boolean
	 * @throws
	 */
	public boolean  dealWithCriterion( int row, int col) {
		//û�п�λ
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
		//û�п�λ
		if (!state) {
			return true;
		}
		return chooseCriterion(row, col);
	}

	/**
	 * @Title: chooseCriterion
	 * @Description: ����ѡ����ʵ���
	 * @param row
	 * @param col
	 * @return boolean
	 * @throws
	 */
	private boolean chooseCriterion(int row, int col) {
		int value;
		boolean state = false;
		for (int k = 0; k < 9; k++) {
			if(!state) {
				value = k + 1;
				if (!fill(row, col, value)) {
					continue;
				}
				map[row][col] = value;
				usedNum(row, col, value);
				state = dealWithCriterion(row, col);
				if (!state) {
					releaseNum(row, col, value);
					map[row][col] = 0;
				}
			}
		}
		return state;
	}

	/**
	 * @Title: fill
	 * @Description: �ж��ǲ���0
	 * @param row
	 * @param col
	 * @param value
	 * @return boolean
	 * @throws
	 */
	public boolean fill(int row, int col, int value) {
		int temp = criterion[row] & criterion[9 + col] & criterion[18 + (getBlock(row, col) - 1)] & encode[value - 1];
		boolean flag=true;
		if(temp==0) flag=false;
		return flag;
	}

	/**
	 * @Title: releaseNum
	 * @Description: ����֮ǰѡ������֣���������λ��Ϊ0
	 * @param row
	 * @param col
	 * @param value
	 * @return void
	 * @throws
	 */
	public void releaseNum(int row, int col, int value) {
		criterion[row] = criterion[row ] | (encode[value - 1]);
		criterion[9 + col ] = criterion[9 + col] | (encode[value - 1]);
		criterion[18 + (getBlock(row, col) - 1)] = criterion[18 + (getBlock(row, col) - 1)] | (encode[value - 1]);
	}



	/**
	 * @Title: usedNum
	 * @Description: ����λ��������ʹ�ù���������Ϊ0,
	 * @param row
	 * @param col
	 * @param value
	 * @return void
	 * @throws
	 */
	public void usedNum( int row, int col, int value) {
		//ÿһλ����������
		criterion[row] = criterion[row] & (~encode[value - 1]);
		criterion[9 + col ] = criterion[9 + col] & (~encode[value - 1]);

		criterion[18 + (getBlock(row, col) - 1)] = criterion[18 + (getBlock(row, col) - 1)] & (~encode[value - 1]);
	}


	/**
	 * @Title: getBlock
	 * @Description: �õ���ǰ�����ڵڼ���
	 * @param row
	 * @param col
	 * @return int
	 * @throws
	 */
	public int getBlock(int row, int col)
	{
		return row  / 3 * 3 + (col + 3) / 3;
	}
	
	/**
	* @Title: checkSolution
	* @Description: �����
	* @param  data
	* @param  num
	* @return boolean   
	* @throws
	*/
	boolean checkSolution(int[] data, int num)
	{
		int index=0;
		int[] criterion=new int[27];
		Solve s=new Solve();
		for (int j = 0; j < 27; j++) {
			criterion[j] = 511;
		}
		for (int i = 0; i < num; i++) {
			s.setCriterion(criterion);
			if(!checkSudoku(data,index,s,i))
				return false;
		}
		return true;
	}

	/**
	* @Title: checkSudoku
	* @Description: ����������Ƿ���ȷ
	* @param  data
	* @param  index
	* @param  s
	* @param  num  
	* @return boolean   
	* @throws
	*/
	private boolean checkSudoku(int[] data,int index,Solve s,int num) {
		for (int j = 0; j < 9; j++) {
			for (int k = 0; k < 9; k++) {
				while (data[index]>9 || data[index]<1)
				{
					index++;
				}
				int temp = data[index++];
				if (!s.fill(j, k, temp)) {
					Logger logger=Logger.getLogger("SolveTest");
					logger.setLevel(Level.SEVERE);
					String msg="map:"+(num+1)+"row:"+(j+1)+"clo:"+(k+1)+"value:"+temp;
					logger.severe(msg);
					return false;
				}else{
					s.usedNum(j,k,temp);
				}
			}
		}
		return true;
	}
}

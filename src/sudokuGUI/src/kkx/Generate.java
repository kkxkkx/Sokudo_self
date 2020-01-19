package kkx;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: sudoku
 * @Description: 进行数独不同局的输出
 * @author WangKeXin
 * @date 2019年12月23日 下午2:00:15
 *
 */
public class Generate {
	public static final int side = 9;
	public static final int SideSub = 3;

	public int[][] layout = null;// 布局
	public byte[] ansFlag = null; // 每个布局位置解空间使用标识（指向下一次要处理的解）
	public byte[][] ans = null; // 记录每个位置的解空间
	public Random random = new Random();
	public int curr;// 当前处理的布局位置

	public int[][] getLayout() {
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				layout[i][j]++;
			}
		}
		return layout;
	}

	/**
	 * @Title: generateRandom
	 * @Description: 把需要输入局面的个数全部初始化
	 * @param @param count
	 * @return void
	 * @throws
	 */
	public void generateRandom() {
				init();
				generate();
	}

	/**
	 * @Title: init
	 * @Description: 初始化局面
	 * @param
	 * @return void
	 * @throws
	 */
	public void init() {

		if (ansFlag == null)
			ansFlag = new byte[side * side];
		if (ans == null)
			ans = new byte[side * side][side];
		if (layout == null)
			layout = new int[side][side];

		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++)
				layout[i][j] = -1;
			ansFlag[i] = 0;
		}
		for (int i = 0; i < side * side; i++)
			for (int j = 0; j < side; j++)
				ans[i][j] = -1;
	}

	/**
	 * @Title: generate
	 * @Description: 生成布局
	 * @param
	 * @return void
	 * @throws
	 */
	public void generate() {
		curr = 0;
		Boolean flag = true;
		while (flag) {
			if (ansFlag[curr] == 0)
				getPosiAnswer(curr);
			int ansCount = getAnswerCount(curr);
			if (ansCount == ansFlag[curr] && curr == 0)
				break;
			if (ansCount == 0 || ansFlag[curr] == ansCount) {
				ansFlag[curr] = 0;
				curr--;
				layout[curr / side][curr % side] = -1;
				continue;
			}else {
				layout[curr / side][curr % side] = getAnswer(curr, ansFlag[curr]);
				ansFlag[curr++]++;
			}
			if (side * side == curr) {
				flag = false;
				curr--;
			}
		}
	}

	/**
	 * @Title: RandomAnswer
	 * @Description:可用随机排序
	 * @param curr_temp
	 * @return void
	 * @throws
	 */
	private void RandomAnswer(int curr_temp) {
		List<Byte> list = new LinkedList<Byte>();
		for (int i = 0; i < side; i++)
			list.add(ans[curr_temp][i]);
		int posi = 0, index = 0;
		while (list.size() != 0) {
			posi = Math.abs(random.nextInt()) % list.size();
			ans[curr_temp][index] = list.get(posi);
			list.remove(posi);
			index++;
		}
		list = null;
	}

	/**
	 * @Title: getAnswerCount
	 * @Description: 获得解的数目
	 * @param  curr_temp
	 * @return int
	 * @throws
	 */
	private int getAnswerCount(int curr_temp) {
		int count = 0;
		for (int i = 0; i < side; i++)
			if (ans[curr_temp][i] != -1)
				count++;
		return count;
	}


	/**
	 * @Title: getPosiAnswer
	 * @Description: 返回值指定位置的可用解
	 * @param  curr_temp
	 * @return void
	 * @throws
	 */
	private void getPosiAnswer(int curr_temp) {
		for (byte i = 0; i < side; i++)
			ans[curr_temp][i] = i;
		int x = curr_temp / side, y = curr_temp % side;
		for (int i = 0; i < side; i++) {
			if (layout[i][y] != -1)
				ans[curr_temp][layout[i][y]] = -1;
			if (layout[x][i] != -1)
				ans[curr_temp][layout[x][i]] = -1;
		}
		int x2 = x / SideSub, y2 = y / SideSub;
		for (int i = x2 * SideSub; i < SideSub + x2 * SideSub; i++) {
			for (int j = y2 * SideSub; j < SideSub + y2 * SideSub; j++) {
				if (layout[i][j] != -1)
					ans[curr_temp][layout[i][j]] = -1;
			}
		}
		RandomAnswer(curr_temp);
	}

	/**
	 * @Title: getAnswer
	 * @Description: 得到当前位置可能解的个数
	 * @param  curr_temp
	 * @param  state
	 * @return byte
	 * @throws
	 */
	private byte getAnswer(int curr_temp, int state) {
		int cnt = 0;
		for (int i = 0; i < side; i++) {
			if (cnt == state && ans[curr_temp][i] != -1)
				return ans[curr_temp][i];
			if (ans[curr_temp][i] != -1)
				cnt++;
		}
		return -1;
	}
	/**
	 * @Title: dealAns
	 * @Description: 对答案进行挖空
	 * @param  answer
	 * @return int[][]
	 * @throws
	 */
	public int[][] dealAns(int[][] answer) {
		int[][] prob=new int[9][9];
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				prob[i][j]=answer[i][j];
			}
		}
		for(int i=0;i<9;i++)
		{
			int num=random.nextInt(3)+3;
			for(int j=0;j<num;j++)
			{
				int[] index=getRowCol(someRandom(num)[j],i);
				prob[index[0]][index[1]]=0;
			}

		}
		return prob;
	}

	/**
	 * @Title: getRowCol
	 * @Description: 得到第block中posi位置对应的横纵坐标
	 * @param posi
	 * @param block
	 * @return int[]
	 * @throws
	 */
	private int[] getRowCol(int posi,int block) {
		int[] temp=new int[2];
		if(block<3)
		{
			temp[0]=posi/3;
		}else if(block<6){
			temp[0]=3+posi/3;
		}else if(block<9){
			temp[0]=6+posi/3;
		}
		if(posi%3!=0)
		{
			temp[1]=posi%3-1+block%3*3;
		}else{
			temp[1]=2+block%3*3;
		}
		return temp;
	}

	/**
	 * @Title: SomeRandom
	 * @Description: 在1-9中随机获得不同的num个数
	 * @param  num
	 * @return int[]
	 * @throws
	 */
	private int[] someRandom(int num) {
		int[] temp=new int[num];
		List<Integer> list = new LinkedList<Integer>();
		for(int i=0;i<9;i++)
		{
			list.add(i);
		}
		int posi=0;
		int i=0;
		while (true) {
			int a=random.nextInt();
			posi = Math.abs(a) % list.size();
			temp[i++] = list.get(posi);
			list.remove(posi);
			if(i==num) break;
		}
		return temp;
	}
}
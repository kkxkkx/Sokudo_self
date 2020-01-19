package kkx;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: Generate
 * @Description: 进行数独不同局的输出
 * @author WangKeXin
 * @date 2019年12月23日 下午2:00:15
 *
 */
public class Generate {
	private int[][] layout = null;// 布局
	private int[] ansFlag = null; // 每个布局位置解空间使用标识（指向下一次要处理的解）
	private int[][] ans = null; // 记录每个位置的解空间
	private Random random = new Random();
	private int curr;// 当前处理的布局位置

	public void generateRandom() {
		init();
		generate();
	}

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
	 * @Title: init
	 * @Description: 初始化局面
	 * @param
	 * @return void
	 * @throws
	 */
	public void init() {

		if (ansFlag == null)
			ansFlag = new int[9 * 9];
		if (ans == null)
			ans = new int[9 * 9][9];
		if (layout == null)
			layout = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++)
				layout[i][j] = -1;
			ansFlag[i] = 0;
		}
		for (int i = 0; i < 9 * 9; i++)
			for (int j = 0; j < 9; j++)
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
		boolean flag = true;
		while (flag) {
			if (ansFlag[curr] == 0)
				getPosiAnswer(curr);
			int ansCount = getAnswerCount(curr);
			if (ansCount == ansFlag[curr] && curr == 0)
				break;
			if (ansCount == 0 || ansFlag[curr] == ansCount) {
				ansFlag[curr] = 0;
				curr--;
				layout[curr / 9][curr % 9] = -1;
				continue;
			}else {
				layout[curr / 9][curr % 9] = getAnswer(curr, ansFlag[curr]);
				ansFlag[curr++]++;
			}
			if (9 * 9 == curr) {
				flag = false;
				curr--;
				ansFlag[curr] = 1;
			}
		}
	}


	/**
	 * @Title: RandomAnswer
	 * @Description:随机排序
	 * @param currtemp
	 * @return void
	 * @throws
	 */
	private void randomAnswer(int currtemp) {
		List<Integer> list = new LinkedList<Integer>();
		for (int i = 0; i < 9; i++)
			list.add(ans[currtemp][i]);
		int posi = 0;
		int index = 0;
		while (list.isEmpty()) {
			int temp=random.nextInt();
			posi = Math.abs(temp) % list.size();
			ans[currtemp][index] = list.get(posi);
			list.remove(posi);
			index++;
		}
	}

	/**
	 * @Title: getAnswerCount
	 * @Description: 获得解的数目
	 * @param  currtemp
	 * @return int
	 * @throws
	 */
	private int getAnswerCount(int currtemp) {
		int count = 0;
		for (int i = 0; i < 9; i++)
			if (ans[currtemp][i] != -1)
				count++;
		return count;
	}

	/**
	 * @Title: getPosiAnswer
	 * @Description: 返回值指定位置的可用解
	 * @param  currtemp
	 * @return void
	 * @throws
	 */
	private void getPosiAnswer(int currtemp) {
		for (byte i = 0; i < 9; i++)
			ans[currtemp][i] = i;
		int x = currtemp / 9;
		int y = currtemp % 9;
		for (int i = 0; i < 9; i++) {
			if (layout[i][y] != -1)
				ans[currtemp][layout[i][y]] = -1;
			if (layout[x][i] != -1)
				ans[currtemp][layout[x][i]] = -1;
		}
		int x2 = x / 3;
		int y2 = y / 3;
		for (int i = x2 * 3; i < 3 + x2 * 3; i++) {
			for (int j = y2 * 3; j < 3 + y2 * 3; j++) {
				if (layout[i][j] != -1)
					ans[currtemp][layout[i][j]] = -1;
			}
		}
		randomAnswer(currtemp);
	}

	/**
	 * @Title: getAnswer
	 * @Description: 得到当前位置可能解的个数
	 * @param  currtemp
	 * @param  state
	 * @return byte
	 * @throws
	 */
	private int getAnswer(int currtemp, int state) {
		int cnt = 0;
		for (int i = 0; i < 9; i++) {
			if (cnt == state && ans[currtemp][i] != -1)
				return ans[currtemp][i];
			if (ans[currtemp][i] != -1)
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
			// posi是在list中的位置
			int a=random.nextInt();
			posi = Math.abs(a) % list.size();
			temp[i++] = list.get(posi);
			list.remove(posi);
			if(i==num) break;
		}
		return temp;
	}
}
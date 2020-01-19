package kkx;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: Generate
 * @Description: ����������ͬ�ֵ����
 * @author WangKeXin
 * @date 2019��12��23�� ����2:00:15
 *
 */
public class Generate {
	private int[][] layout = null;// ����
	private int[] ansFlag = null; // ÿ������λ�ý�ռ�ʹ�ñ�ʶ��ָ����һ��Ҫ����Ľ⣩
	private int[][] ans = null; // ��¼ÿ��λ�õĽ�ռ�
	private Random random = new Random();
	private int curr;// ��ǰ����Ĳ���λ��

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
	 * @Description: ��ʼ������
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
	 * @Description: ���ɲ���
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
	 * @Description:�������
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
	 * @Description: ��ý����Ŀ
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
	 * @Description: ����ֵָ��λ�õĿ��ý�
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
	 * @Description: �õ���ǰλ�ÿ��ܽ�ĸ���
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
	 * @Description: �Դ𰸽����ڿ�
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
	 * @Description: �õ���block��posiλ�ö�Ӧ�ĺ�������
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
	 * @Description: ��1-9�������ò�ͬ��num����
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
			// posi����list�е�λ��
			int a=random.nextInt();
			posi = Math.abs(a) % list.size();
			temp[i++] = list.get(posi);
			list.remove(posi);
			if(i==num) break;
		}
		return temp;
	}
}
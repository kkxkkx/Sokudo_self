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
	public static final int side = 9;
	public static final int SideSub = 3;
	public static final int init_num = 6;

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
			ansFlag = new int[side * side];
		if (ans == null)
			ans = new int[side * side][side];
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
	 * @Description: ���ɲ���
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
				ansFlag[curr] = 1;
			}
		}
	}


	/**
	 * @Title: RandomAnswer
	 * @Description:�������
	 * @param curr_temp
	 * @return void
	 * @throws
	 */
	private void RandomAnswer(int curr_temp) {
		List<Integer> list = new LinkedList<Integer>();
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
	 * @Description: ��ý����Ŀ
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
	 * @Description: ����ֵָ��λ�õĿ��ý�
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
	 * @Description: �õ���ǰλ�ÿ��ܽ�ĸ���
	 * @param  curr_temp
	 * @param  state
	 * @return byte
	 * @throws
	 */
	private int getAnswer(int curr_temp, int state) {
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
	* @Title: DealAns
	* @Description: �Դ𰸽����ڿ�
	* @param  answer
	* @return int[][]   
	* @throws
	*/
	public int[][] DealAns(int[][] answer) {
		int[][] prob=new int[9][9];
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				prob[i][j]=answer[i][j];
			}
		}
		Random random=new Random();
		for(int i=0;i<9;i++)
		{
			int num=random.nextInt(3)+3;
			int[] temp=new int[9];
			temp=SomeRandom(num);
			for(int j=0;j<num;j++)
			{
				int[] index=getRowCol(temp[j],i);
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
	private int[] SomeRandom(int num) {
		int[] temp=new int[num];
		List<Integer> list = new LinkedList<Integer>();
		for(int i=0;i<9;i++)
		{
			list.add(i);
		}
		int posi=0;
		int i=0;
		Random random=new Random();
		while (true) {
			// posi����list�е�λ��
			posi = Math.abs(random.nextInt()) % list.size();
			temp[i++] = list.get(posi);
			list.remove(posi);
			if(i==num) break;
		}
		return temp;
	}
}
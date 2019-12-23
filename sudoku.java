import java.io.FileOutputStream;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @ClassName: sudoku
* @Description: 进行数独不同局的输出
* @author WangKeXin
* @date 2019年12月23日 下午2:00:15
*
*/

public class sudoku {
	static int count; //需要输出数独的个数
	public int curPos;// 当前处理的布局位置
    public static final int fieldNum=9;
    public static int Sum=0;
    public byte[] layout = null;// 布局
    public byte[] outbytes = null;
    public Random random = new Random();
    public byte[] ansPosArr = null;// 每个布局位置解空间使用标识（指向下一次要处理的解）
    public byte[][] ansArr = null;// 记录每个位置的解空间
    public boolean randomLayout = false;
    public boolean init_state=true;  //是否在输出第一种状态
    
		public static void main(String[] args) {
			
			 if (args.length != 2) {  
		        System.out.println("您的输入不正确\n"); 
		        System.out.println("正确的输入格式为：java sudoku -c 阿拉伯数字");
		        return;  
		    }else {
		    	if(!args[0].equals("-c")){
		    		System.out.println("您的输入不正确\n"); 
		            System.out.println("正确的输入格式为：java sudoku -c 阿拉伯数字");
		            return; 
		    	}
		    	if(!isNumeric(args[1])){
		    		System.out.println("您的输入不正确\n"); 
		            System.out.println("正确的输入格式为：java sudoku -c 阿拉伯数字");
		            return; 
		    	}
		    }
		    System.out.println("您调用main方法时指定的参数包括：");  
		    for (int i = 0; i < args.length; i++) {  
		        System.out.println("参数" + (i + 1) + "的值为：" + args[i]);  
		    } 
		    
		    count=Integer.valueOf(args[1]);
			sudoku sdk = new sudoku();
			//count=2
			sdk.generateRandom(count);
		    
		}
		
		/**
		* @Title: isNumeric
		* @Description: 判断输入是否为数字
		* @param @param str
		* @param @return    
		* @return boolean    
		* @throws
		*/
		public static boolean isNumeric(String str) {
		    // 该正则表达式可以匹配所有的数字 包括负数
		    Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
		    String bigStr;
		    try {
		        bigStr = new BigDecimal(str).toString();
		    } catch (Exception e) {
		        return false;//异常 说明包含非数字。
		    }

		    Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
		    if (!isNum.matches()) {
		        return false;
		    }
		    return true;
		}
		
	    /**
	    * @Title: generateRandom
	    * @Description: 把需要输入局面的个数全部初始化
	    * @param @param count    
	    * @return void   
	    * @throws
	    */
	    public void generateRandom(int count) {
	        setRandomLayout(true);
	        for (int i = 0; i < count; i++) {
	            init();
	            generate(1);
	        }
	    }
	    
		/**
		* @Title: init
		* @Description: 初始化局面
		* @param    
		* @return void    
		* @throws
		*/
		public void init( )
		{
			curPos=0;
			if(layout==null)
				layout=new byte[fieldNum*fieldNum];
			if (outbytes == null)
	            outbytes = new byte[(fieldNum*fieldNum + 1) / 2];
	        if (ansPosArr == null)
	            ansPosArr = new byte[fieldNum*fieldNum];
	        // 用来记录布局中某个位置的可能解
	        if (ansArr == null)
	            ansArr = new byte[fieldNum*fieldNum ][fieldNum];
	        for (int i = 0; i < fieldNum*fieldNum; i++) {
	            layout[i] = -1;// 将布局全部设置为未填状态
	            ansPosArr[i] = 0;// 用来记录解的位置，回溯时从这个位置往后处理
	            for (int j = 0; j < fieldNum; j++)
	                ansArr[i][j] = -1;// 初始化为无解，程序运行中动态求取
	        }
			
		}
		
	    /**
	    * @Title: outData
	    * @Description: 将当前布局写入文件
	    * @param @param fw    
	    * @return void    
	    * @throws
	    */
	    public void outData(FileWriter fw) {
	        try {
	            for (int i = 0; i < fieldNum * fieldNum; i++) {
	                fw.write(String.valueOf(layout[i] + 1));
	                fw.write(" ");
	                if ((i + 1) % fieldNum == 0)
	                    fw.write("\n");
	            }
	            fw.write("\n");
	        } catch (Exception e) {
	        }
	    }

	    /**
	    * @Title: dealAnswer
	    * @Description: 可用随机排序
	    * @param @param pos    
	    * @return void    
	    * @throws
	    */
	    private void dealAnswer(int pos) {
	        // 随机调整一下顺序
	        List<Byte> list = new LinkedList<Byte>();
	        for (int i = 0; i < fieldNum; i++)
	            list.add(ansArr[pos][i]);
	        int rdm = 0, idx = 0;
	        while (list.size() != 0) {
	            rdm = Math.abs(random.nextInt()) % list.size();
	            ansArr[pos][idx] = list.get(rdm);
	            list.remove(rdm);
	            idx++;
	        }
	        list = null;
	    }
	    
	    /**
	    * @Title: getAnswerCount
	    * @Description: 获得解的数量
	    * @param @param pos
	    * @param @return    
	    * @return int    
	    * @throws
	    */
	    private int getAnswerCount(int pos) {
	        // 计算可用解的数量
	        int count = 0;
	        for (int i = 0; i < fieldNum; i++)
	            if (ansArr[pos][i] != -1)
	                count++;
	        return count;
	    }
	    
	    /**
	    * @Title: openFileWriter
	    * @Description: 获得一个写文件器，用来输出布局，构造函数的第二个参数true表示追加
	    * @param @param name
	    * @param @return    
	    * @return FileWriter    
	    * @throws
	    */
	    private FileWriter openFileWriter(String name) {
	        try {
	        	if(init_state) {
	        		init_state=false;
	        		return new FileWriter(name);
	        	}
	        	return new FileWriter(name,true);
	            
	        } catch (Exception e) {
	        }
	        return null;
	    }
	    
	    
	    /**
	    * @Title: openFileStream
	    * @Description: 打开流，输出布局
	    * @param @param name
	    * @param @return    
	    * @return FileOutputStream    
	    * @throws
	    */
	    private FileOutputStream openFileStream(String name) {
	        try {
	            return new FileOutputStream(name);
	        } catch (Exception e) {
	        }
	        return null;
	    }
	    
	    
	    /**
	    * @Title: getAnswer
	    * @Description: 返回值指定位置的可用解
	    * @param @param pos    
	    * @return void    
	    * @throws
	    */
	    private void getAnswer(int pos) {
	        for (byte i = 0; i < fieldNum; i++)
	            ansArr[pos][i] = i;// 假定包含所有解
	        // 去除已经包含的
	        int x = pos / fieldNum, y = pos % fieldNum;
	        for (int i = 0; i < fieldNum; i++) {
	            if (layout[i * fieldNum + y] != -1)
	                ansArr[pos][layout[i * fieldNum + y]] = -1;// 去除列中包含的元素
	            if (layout[x * fieldNum + i] != -1)
	                ansArr[pos][layout[x * fieldNum + i]] = -1;// 去除行中包含的元素
	        }
	        int subnum = (int) Math.sqrt(fieldNum);
	        int x2 = x / subnum, y2 = y / subnum;
	        // boolean bOver = false;//这个优化应该是没有问题的
	        for (int i = x2 * subnum; i < subnum + x2 * subnum; i++) {
	            // if (bOver)
	            // break;
	            for (int j = y2 * subnum; j < subnum + y2 * subnum; j++) {
	                if (layout[i * fieldNum + j] != -1)
	                    ansArr[pos][layout[i * fieldNum + j]] = -1;// 去小方格中包含的元素
	            }
	        }
	        if (randomLayout == true)
	            dealAnswer(pos);
	    }
	    
	    private byte getAnswerNum(int fieldPos, int ansPos) {
	        // 返回指定布局方格中指定位置的解
	        int cnt = 0;
	        for (int i = 0; i < fieldNum; i++) {
	            // 找到指定位置的解，返回
	            if (cnt == ansPos && ansArr[fieldPos][i] != -1)
	                return ansArr[fieldPos][i];
	            if (ansArr[fieldPos][i] != -1)
	                cnt++;// 是解，调整计数器
	        }
	        return -1;// 没有找到，逻辑没有问题的话，应该不会出现这个情况
	    }
	   
	    /**
	    * @Title: generate
	    * @Description: 按顺序生产布局
	    * @param @param layoutCount 生成的布局数
	    * @param @return    
	    * @return long  
	    * @throws
	    */
	    public long generate(long layoutCount) {
//	    	if(!init_state)
//	    	{
	    		FileWriter out = openFileWriter("layout.txt");
//	    		init_state=true;
//	    	}
//	    	else
//	    		FileOutputStream out = openFileStream("layout.txt");
	        //如果要保存布局，把这个注释打开
	        curPos = 0;   //当前处理的布局位置
	        long count = 0;    
	        Sum++;
	        while (count < layoutCount || layoutCount == -1) {
	            if (ansPosArr[curPos] == 0)  
	                getAnswer(curPos);// 如果这个位置没有被回溯过，就不用重新计算解空间
	            int ansCount = getAnswerCount(curPos);
	            if (ansCount == ansPosArr[curPos] && curPos == 0)
	                break;// 全部回溯完毕
	            if (ansCount == 0) {
	                ansPosArr[curPos] = 0;// 无可用解，应该就是0
	                // System.out.println("无可用解，回溯！");
	                curPos--;
	                layout[curPos] = -1;
	                continue;
	            }
	            // 可用解用完
	            else if (ansPosArr[curPos] == ansCount) {
	                // System.out.println("可用解用完，回溯！");
	                ansPosArr[curPos] = 0;
	                curPos--;
	                layout[curPos] = -1;
	                continue;
	            } else {
	                // 返回指定格格中，第几个解
	                layout[curPos] = getAnswerNum(curPos, ansPosArr[curPos]);
	                // System.out.println("位置："+curPos+"　填写："+layout[curPos]);
	                ansPosArr[curPos]++;
	                curPos++;
	            }
	            if (fieldNum * fieldNum == curPos) {
	                // System.out.print("/n========"+count+"========");
	                outData();
	                System.out.println();
	                if (out != null)
	                    outData(out);
	                count++;
	                curPos--;
	                layout[curPos] = -1;// 最后位置清空
	                ansPosArr[curPos] = 1;// 解位置标识请零//人为促使继续回溯
	            }
	        }
	        try {
	            out.close();
	        } catch (Exception e) {
	        }
	        System.out.println("处理完毕！共生成：" + count);
	        return count;
	    }
	    
	    public void setRandomLayout(boolean flag) {
	        randomLayout = flag;
	    }
	    
	    public void outData() {
	        for (int i = 0; i < fieldNum * fieldNum; i++) {
	            if (i % 9 == 0)
	                System.out.println();
	            System.out.print((layout[i] != -1 ? (layout[i] + 1) : " ") + " ");
	        }
	    }

}

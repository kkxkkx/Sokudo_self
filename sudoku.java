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
	public static int count; //需要输出数独的个数
	public int curr;// 当前处理的布局位置
    public static final int side=9;
    public static final int SideSub = 3;
    public static final int init_num=6;

    public boolean init_state=true;  //是否在输出第一种状态  
    public byte[][] layout = null;// 布局
    public byte[] ansFlag = null; //每个布局位置解空间使用标识（指向下一次要处理的解）
    							  //处理过以后变为1
    public byte[][] ans = null; //记录每个位置的解空间
    public Random random = new Random();
    
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
			//count=2;
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
	        for (int i = 0; i < count; i++) {
	            init();
	            generate();
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

			if(layout==null)
				layout=new byte[side][side];
	        if (ansFlag == null)
	            ansFlag = new byte[side*side];     
	        if (ans == null)
	            ans = new byte[side*side ][side];
	        
	        for (int i = 0; i < side; i++) {
	        	for(int j=0;j<side;j++)
	        		layout[i][j] = -1;// 将布局全部设置为未填状态	          
	            ansFlag[i] = 0;// 用来记录解的位置，回溯时从这个位置往后处理	               
	        }	        
	        for(int i=0;i<side*side;i++)
	        	for(int j=0;j<side;j++)
	        		ans[i][j] = -1;// 初始化为无解，程序运行中动态求取
		}
		
	    /**
	    * @Title: WriteToFile
	    * @Description: 将当前布局写入文件
	    * @param @param fw    
	    * @return void    
	    * @throws
	    */
	    public void WriteToFile(FileWriter fw) {
	        try {
	            for (int i = 0; i < side ; i++) {
	            	for(int j=0;j<side;j++){
	            		fw.write(String.valueOf(layout[i][j]+1));
	            		fw.write(" ");
	            	}
	                    fw.write("\n");
	            }
	            fw.write("\n");
	        } catch (Exception e) {
	        }
	    }

	    /**
	    * @Title: RandomAnswer
	    * @Description: 可用随机排序
	    * @param @param curr    
	    * @return void    
	    * @throws
	    */
	    private void RandomAnswer(int curr) {
	        // 随机调整一下顺序
	    	List<Byte> list = new LinkedList<Byte>();
	        for (int i = 0; i < side; i++)
	            list.add(ans[curr][i]);
	        int posi = 0, index = 0;
	        if(curr==0)
	        {
	        	ans[0][0]=init_num;
	        	list.remove(init_num);
	            index++;
	        }
	        while (list.size() != 0) {
	        	//posi是在list中的位置
	            posi = Math.abs(random.nextInt()) % list.size();
	            ans[curr][index] = list.get(posi);
	            list.remove(posi);
	            index++;
	        }
	        list = null;
	    }
	    
	    /**
	    * @Title: getAnswerCount
	    * @Description: 获得解的数量
	    * @param @param curr
	    * @param @return    
	    * @return int    
	    * @throws
	    */
	    private int getAnswerCount(int curr) {
	        // 计算可用解的数量
	        int count = 0;
	        for (int i = 0; i < side; i++)
	            if (ans[curr][i] != -1)
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
	    * @Title: getAnswer
	    * @Description: 返回值指定位置的可用解
	    * @param @param curr    
	    * @return void    
	    * @throws
	    */
	    private void getAnswer(int curr) {
	        for (byte i = 0; i < side; i++)
	            ans[curr][i] =i; //假定包含所有解
	        //在所有可能的解中出去该行或该列已经有的
	        int x = curr / side, y = curr % side; //x是当前行，y是当前列
	        for (int i = 0; i < side; i++) {
	            if (layout[i][y] != -1) //该列所有数字
	            {
	            	ans[curr][layout[i][y]] = -1; //删去这个数字
	            }	                
	            if (layout[x][i] != -1)  //该行所有数字
	            {
	            	ans[curr][layout[x][i]] = -1;
	            }         
	        }	        
	        //让在3X3的区域也互不相同
	        int x2 = x / SideSub, y2 = y / SideSub;  //该位置在3X3区域的位置
	        for (int i = x2 * SideSub; i < SideSub + x2 * SideSub; i++) {
	            for (int j = y2 * SideSub; j < SideSub + y2 * SideSub; j++) {	        
	                if (layout[i][j] != -1)
	                    ans[curr][layout[i][j]] = -1; //删去在3X3中出现过的数字
	            }
	        }
	        RandomAnswer(curr);
	    }
	    
	    /**
	    * @Title: getAnswerNum
	    * @Description: 得到当前位置可能解的个数
	    * @param @param curr
	    * @param @param state
	    * @param @return    
	    * @return byte    
	    * @throws
	    */
	    private byte getAnswerNum(int curr, int state) {
	        int cnt = 0;
	        for (int i = 0; i < side; i++) {
	            // 找到指定位置的解，返回
	            if (cnt == state && ans[curr][i] != -1)
	            	return ans[curr][i];
	            if (ans[curr][i] != -1)
	                cnt++;// 是解，调整计数器
	        }
	        return -1;// 没有找到，逻辑没有问题的话，应该不会出现这个情况
	    }
	   
	    /**
	    * @Title: generate
	    * @Description: 生成一种布局
	    * @param @param layoutCount 生成的布局数
	    * @param @return    
	    * @return long  
	    * @throws
	    */
	    public long generate() {
	    	FileWriter out = openFileWriter("layout.txt");
	        curr = 0;   //当前处理的布局位置	       
	        Boolean flag=true;
	        while (flag) {
	            if (ansFlag[curr] == 0)  
	                getAnswer(curr); //如果这个位置没有被回溯过，就不用重新计算解空间
	            int ansCount = getAnswerCount(curr);
	            if (ansCount == ansFlag[curr] && curr == 0) // 全部回溯完毕
	                break;
	            if (ansCount == 0) {
	                ansFlag[curr] = 0;// 无可用解，应该就是0
	                curr--;
	                layout[curr/side][curr%side] = -1;
	                continue;
	            }
	            // 可用解用完
	            else if (ansFlag[curr] == ansCount) {
	                ansFlag[curr] = 0;
	                curr--;
	                layout[curr/side][curr%side] = -1;
	                continue;
	            } else {
	                // 返回指定格格中，第几个解
	            	layout[curr/side][curr%side]= getAnswerNum(curr, ansFlag[curr]);
	                ansFlag[curr++]++;
	            }
	            if (side * side == curr) {
	                if (out != null)
	                	WriteToFile(out);
	                flag=false;
	                curr--;
	                layout[curr/side][curr%side] = -1; //最后位置清空
	                ansFlag[curr] = 1;// 解位置标识请零,人为促使继续回溯
	            }
	        }
	        try {
	            out.close();
	        } catch (Exception e) {
	        }
	        System.out.println("处理完毕！共生成：" + count);
	        return count;
	    }
}


package kkx;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
	 public static final int side = 9;
	    public static final int OUTPUT = 0;
	    public static final int CALCULATE = 1;

	    private static  int count=0; //需要输出数独的个数
	    public static void main(String[] args) {
	        long startTime=System.currentTimeMillis();

	        if(args[0].equals("-c"))
	        {
	        	 if (args.length != 2||!isNumeric(args[1]))
	        	 {
	        		 PrintError();
	 	            return;
	        	 }
	        	 
	            count=Integer.valueOf(args[1]);
	            Generate generate=new Generate();
	            generate.GenerateSudoku(count);	            
	            try {					
					StringToFile(generate.getSudoku());
				} catch (IOException e) {
					e.printStackTrace();
				}
	          //  sdk.generateRandom(count);
	        }
	        else if(args[0].equals("-s"))
	        {
	        	if(args.length != 3)
	        	{
	        		 PrintError();
		 	         return;
	        	}
	        	Solve solve=new Solve();
	        	solve.FindSolution(args[1]);
	        	 try {					
						StringToFile(solve.getSolution());
					} catch (IOException e) {
						e.printStackTrace();
					}
	            
	        }
	        else
	        {
	        	PrintError();
	        }
	        long endTime=System.currentTimeMillis();
	        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
	    }

	    /**
	     * @Title: isNumeric
	     * @Description: 判断输入是否为数字
	     * @param  str
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
	            return false;// 异常 说明包含非数字。
	        }
	        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
	        if (!isNum.matches()) {
	            return false;
	        }
	        return true;
	    }

	    /**
	     * @Title: PrintError
	     * @Description: 打印输入错误
	     * @param
	     * @return void
	     * @throws
	     */
	    private static void PrintError() {
	        // TODO Auto-generated method stub
	        System.out.println("您的输入不正确\n");
	        System.out.println("生成终局命令为：java sudoku -c 阿拉伯数字");
	        System.out.println("求解数独命令为：java sudoku -s 输出txt的绝对位置");
	        return;
	    }
	    
		private static void StringToFile(char[] output) throws IOException {
			// TODO Auto-generated method stub
			FileWriter fw=new FileWriter("layout.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fw);
			bufferedWriter.write(output);
	        bufferedWriter.close();
		}
}

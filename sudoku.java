import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sudoku {
	static int num;
	static final int bound=1000000;
		public static void main(String[] args) {
			 if (args.length != 2) {  
		        System.out.println("您的输入不正确"); 
		        System.out.println("正确的输入格式为：java sudoku -c 正数");
		        return;  
		    }else {
		    	if(!args[0].equals("-c")){
		    		System.out.println("您的输入不正确"); 
		            System.out.println("正确的输入格式为：java sudoku -c 正数");
		            return; 
		    	}
		    	if(!isNumeric(args[1])){
		    		System.out.println("您的输入不正确"); 
		            System.out.println("正确的输入格式为：java sudoku -c 正数");
		            return; 
		    	}
		    	num=Integer.valueOf(args[1]);
		    	if(num>bound)
		    	{
		    		System.out.println("您的输入不正确"); 
		    		System.out.println("最大可以输入的数字为"+bound);
		    	}
		    }
		}
		public static boolean isNumeric(String str) {
		    // 该正则表达式可以匹配所有的数字 包括负数
			return str.matches("^[+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
		}

}
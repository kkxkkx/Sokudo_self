import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sudoku {
	static int num;
	static final int bound=1000000;
		public static void main(String[] args) {
			 if (args.length != 2) {  
		        System.out.println("�������벻��ȷ"); 
		        System.out.println("��ȷ�������ʽΪ��java sudoku -c ����");
		        return;  
		    }else {
		    	if(!args[0].equals("-c")){
		    		System.out.println("�������벻��ȷ"); 
		            System.out.println("��ȷ�������ʽΪ��java sudoku -c ����");
		            return; 
		    	}
		    	if(!isNumeric(args[1])){
		    		System.out.println("�������벻��ȷ"); 
		            System.out.println("��ȷ�������ʽΪ��java sudoku -c ����");
		            return; 
		    	}
		    	num=Integer.valueOf(args[1]);
		    	if(num>bound)
		    	{
		    		System.out.println("�������벻��ȷ"); 
		    		System.out.println("���������������Ϊ"+bound);
		    	}
		    }
		}
		public static boolean isNumeric(String str) {
		    // ��������ʽ����ƥ�����е����� ��������
			return str.matches("^[+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
		}

}
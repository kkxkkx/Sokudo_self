package kkx;

import java.io.BufferedWriter;
import java.io.File;
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

	private static  int count=0; //��Ҫ��������ĸ���
	public static void main(String[] args) throws IOException {
		long startTime=System.currentTimeMillis();
		if(args[0].equals("-c"))
		{
			count=WhetherGenerate(args);
			if(count==-1)
				return;
			Generate generate=new Generate();
			generate.GenerateSudoku(count);
			StringToFile(generate.getOutput());
		}
		else if(args[0].equals("-s"))
		{
			if(!WhetherSolve(args))
				return;
			Solve solve=new Solve();
			solve.FindSolution(args[1]);
			StringToFile(solve.getSolution());
		}
		else
		{
			System.out.println("�������벻��ȷ\n");
			System.out.println("�����վ�����Ϊ��java sudoku -c ����������");
			System.out.println("�����������Ϊ��java sudoku -s puzzle.txt�ľ���·��");
			return;
		}
		long endTime=System.currentTimeMillis();
		System.out.println("��������ʱ�䣺 "+(float)(endTime-startTime)/1000+"s");
	}

	public static boolean WhetherSolve(String[] args) {
		if(args.length != 3)
		{
			System.out.println("�����������Ϊ��java sudoku -s puzzle.txt�ľ���·��");
			return false;
		}
		File file = new File(args[1]);
		if(file.isDirectory()) {
			System.out.println("·����ָ��һ��txt�ļ�");
		}
		return true;
	}

	public static int WhetherGenerate(String[] args) {
		if (args.length != 2||!isNumeric(args[1])) {
			System.out.println("�����վ�����Ϊ��java sudoku -c ����������");
			return -1;
		}
		count=Integer.valueOf(args[1]);
		if(count<0||count>1_000_000)
		{
			System.out.println("�����ɵ��վ���0~1,000,000֮��");
			return -1;
		}
		return count;
	}

	/**
	 * @Title: isNumeric
	 * @Description: �ж������Ƿ�Ϊ����
	 * @param  str
	 * @return boolean
	 * @throws
	 */
	public static boolean isNumeric(String str) {
		// ��������ʽ����ƥ�����е����� ��������
		Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
		String bigStr;
		try {
			bigStr = new BigDecimal(str).toString();
		} catch (Exception e) {
			return false;// �쳣 ˵�����������֡�
		}
		Matcher isNum = pattern.matcher(bigStr); // matcher��ȫƥ��
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}



	/**
	 * @Title: StringToFile
	 * @Description: �������վ�ͳһд���ļ�
	 * @param @param output
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	public static void StringToFile(char[] output) throws IOException {
		// TODO Auto-generated method stub
		FileWriter fw=new FileWriter("layout.txt");
		BufferedWriter bufferedWriter = new BufferedWriter(fw);
		bufferedWriter.write(output);
		bufferedWriter.close();
	}
}

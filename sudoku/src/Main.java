package kkx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static  int count=0; //需要输出数独的个数
    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();
        if(args[0].equals("-c"))
        {
            count=whetherGenerate(args);
            if(count==-1)
                return;
            Generate generate=new Generate();
            generate.generateSudoku(count);
            stringToFile(generate.getOutput());
        }
        else if(args[0].equals("-s"))
        {
            if(!whetherSolve(args))
                return;
            Solve solve=new Solve();
            solve.findSolution(args[1]);
            stringToFile(solve.getSolution());
        }
        else
        {
            System.out.println("您的输入不正确\n");
            System.out.println("生成终局命令为：java sudoku -c 阿拉伯数字");
            System.out.println("求解数独命令为：java sudoku -s puzzle.txt的绝对路径");
            return;
        }
        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(float)(endTime-startTime)/1000+"s");
    }

    public static boolean whetherSolve(String[] args) {
        if(args.length != 3)
        {
            System.out.println("求解数独命令为：java sudoku -s puzzle.txt的绝对路径");
            return false;
        }
        File file = new File(args[1]);
        if(file.isDirectory()) {
            System.out.println("路径需指向一个txt文件");
        }
        return true;
    }

    public static int whetherGenerate(String[] args) {
        if (args.length != 2||!isNumeric(args[1])) {
            System.out.println("生成终局命令为：java sudoku -c 阿拉伯数字");
            return -1;
        }
        count=Integer.valueOf(args[1]);
        if(count<0||count>1_000_000)
        {
            System.out.println("能生成的终局在0~1,000,000之间");
            return -1;
        }
        return count;
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
        boolean flag=true;
        if (!isNum.matches())
            flag=false;
        return flag;
    }



    /**
     * @Title: stringToFile
     * @Description: 将所有终局统一写入文件
     * @param @param output
     * @param @throws IOException
     * @return void
     * @throws
     */
    public static void stringToFile(char[] output)  {
        FileWriter fw= null;
        BufferedWriter bufferedWriter = null;
        Logger logger=Logger.getLogger("main");
        logger.setLevel(Level.SEVERE);
        try {
            fw = new FileWriter("layout.txt");
            bufferedWriter= new BufferedWriter(fw);
            bufferedWriter.write(output);
            bufferedWriter.close();
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }finally {
            try {
                if(bufferedWriter!=null)
                    bufferedWriter.close();
            } catch (IOException e1) {
                logger.severe(e1.getMessage());
            }
            try {
                if(fw!=null)
                    fw.close();
            } catch (IOException e) {
                logger.severe(e.getMessage());
            }
        }
    }
}

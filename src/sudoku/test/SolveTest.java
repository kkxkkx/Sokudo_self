package test.kkx;

import kkx.Solve;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

class SolveTest {
    public static final String PATH="D:\\Learn\\Third\\software Engineering\\commit_edition\\Sudoku_self\\1.txt";
    @Test
    void findSolution() {
        Solve s=new Solve();
        s.findSolution(PATH);
        char[] output=s.getSolution();
        checkSolution(output,s.getGoalNumber());
    }
    @Test
    void fill() {
        Solve s=new Solve();
        int[] a=new int[20000];
        s.setData(a);
        s.dealFile(PATH);
        s.initCriterion();
        Assert.assertEquals(true,s.fill(0,0,5));
    }

    @Test
    void releaseNum() {
        Solve s=new Solve();
        int[] a=new int[20000];
        s.setData(a);
        s.dealFile(PATH);
        s.initCriterion();
        s.releaseNum(0,0,4);
        Assert.assertEquals(24,s.getCriterion()[0]);
        Assert.assertEquals(511,s.getCriterion()[9]);
        Assert.assertEquals(92,s.getCriterion()[18]);
    }

    @Test
    void initCriterion() {
        Solve s=new Solve();
        int[] a=new int[20000];
        s.setData(a);
        s.dealFile(PATH);
        s.initCriterion();
        int[] temp={16,4,64,2,32,256,1,8,128,
                511,0,0,0,0,0,0,0,0,
                84,0,0,290,0,0,137,0,0};
        for(int i=0;i<27;i++)
        {
            Assert.assertEquals(temp[i],s.getCriterion()[i]);
        }

    }

    @Test
    void usedNum() {
        Solve s=new Solve();
        int[] temp=new int[27];
        for(int i=0;i<27;i++)
        {
            temp[i] = 511;
        }
        s.setCriterion(temp);
        s.usedNum(0,0,3);
        Assert.assertEquals(507,s.getCriterion()[0]);
        Assert.assertEquals(507,s.getCriterion()[9]);
        Assert.assertEquals(507,s.getCriterion()[18]);
    }

    @Test
    void dealFile() {
        Solve s=new Solve();
        int[] a=new int[20000];
        s.setData(a);
        Assert.assertEquals(1,s.dealFile(PATH));
    }

    @Test
    void getBlock() {
        Solve s=new Solve();
        Assert.assertEquals(1,s.getBlock(0,0));
        Assert.assertEquals(2,s.getBlock(0,4));
    }

    boolean checkSolution(char[] data, int num)
    {
        int index=0;
        int[] criterion=new int[27];
        Solve s=new Solve();
        for (int j = 0; j < 27; j++) {
            criterion[j] = 511;
        }
        for (int i = 0; i < num; i++) {
            s.setCriterion(criterion);
            if(!checkSudoku(data,index,s,i))
                return false;
        }
        return true;
    }

    private boolean checkSudoku(char[] data,int index,Solve s,int num) {
        for (int j = 0; j < 9; j++) {
            for (int k = 0; k < 9; k++) {
                while (data[index]>'9' || data[index]<'1')
                {
                    index++;
                }
                int temp = data[index++]-'0';
                if (!s.fill(j, k, temp)) {
                    Logger logger=Logger.getLogger("SolveTest");
                    logger.setLevel(Level.SEVERE);
                    String msg="map:"+(num+1)+"row:"+(j+1)+"clo:"+(k+1)+"value:"+temp;
                    logger.severe(msg);
                    return false;
                }else{
                    s.usedNum(j,k,temp);
                }
            }
        }
        return true;
    }

}
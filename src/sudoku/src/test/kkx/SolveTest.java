package test.kkx;

import kkx.Solve;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

class SolveTest {
    public static final String PATH="D:\\Learn\\Third\\test.txt";
    @Test
    void chooseCriterion()
    {
        Solve s=new Solve();
        int[][] temp={{0,0,2,7,8,9,3,4,6},
                {0, 4 ,6, 5 ,1 ,2, 7, 8, 9},
                {0 ,8 ,9, 3, 4 ,6, 5 ,1, 2},
                {0 ,5, 1 ,9 ,7 ,8 ,6 ,3, 4},
                {0 ,3 ,4, 2, 5, 1 ,9 ,7 ,8},
                {0 ,7 ,8, 6 ,3 ,4, 2 ,5 ,1},
                {1 ,2 ,5 ,8, 9, 7, 4 ,6 ,3},
                {4 ,6 ,3 ,1, 2, 5 ,8 ,9, 7},
                {8 ,9 ,7 ,4 ,6 ,3, 1, 2, 5}};
        int[] criterion=new int[27];
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                if (temp[i][j] != 0) {
                    s.usedNum(i , j , temp[i][j]);
                }
            }
        }
        s.setCriterion(criterion);
        s.setMap(temp);
        s.dealWithCriterion(0, 0);
    }
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
        Assert.assertEquals(25,s.getCriterion()[0]);
        Assert.assertEquals(511,s.getCriterion()[9]);
        Assert.assertEquals(93,s.getCriterion()[18]);
    }

    @Test
    void initCriterion() {
        Solve s=new Solve();
        int[] a=new int[20000];
        s.setData(a);
        s.dealFile(PATH);
        s.initCriterion();
        int[] temp={17,4,64,2,32,256,1,8,128,
                511,1,0,0,0,0,0,0,0,
                85,0,0,290,0,0,137,0,0};
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
        // Assert.assertEquals(2,s.dealFile(PATH));
        s.dealFile("D:/app/2.txt");
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
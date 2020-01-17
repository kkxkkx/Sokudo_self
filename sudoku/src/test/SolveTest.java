package test;

import kkx.Solve;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SolveTest {
    @Test
    void findSolution() {
        String path="D:\\Learn\\Third\\software Engineering\\commit_edition\\problem.txt";
        Solve s=new Solve();
        s.FindSolution(path);
        char[] output=s.getSolution();
        CheckSolution(output,s.getGoalNumber());
    }
    @Test
    void fill() {
        Solve s=new Solve();
        int[] a=new int[20000];
        s.setData(a);
        s.DealFile("D:\\Learn\\Third\\software Engineering\\commit_edition\\Sudoku_self\\1.txt");
        s.InitCriterion();
        Assert.assertEquals(true,s.Fill(0,0,5));
    }

    @Test
    void releaseNum() {
        Solve s=new Solve();
        int[] a=new int[20000];
        s.setData(a);
        s.DealFile("D:\\Learn\\Third\\software Engineering\\commit_edition\\Sudoku_self\\1.txt");
        s.InitCriterion();
        s.ReleaseNum(0,0,4);
        Assert.assertEquals(24,s.getCriterion()[0]);
        Assert.assertEquals(511,s.getCriterion()[9]);
        Assert.assertEquals(92,s.getCriterion()[18]);
    }

    @Test
    void initCriterion() {
        Solve s=new Solve();
        int[] a=new int[20000];
        s.setData(a);
        s.DealFile("D:\\Learn\\Third\\software Engineering\\commit_edition\\Sudoku_self\\1.txt");
        s.InitCriterion();
        int temp[]={16,4,64,2,32,256,1,8,128,
                511,0,0,0,0,0,0,0,0,
                84,0,0,290,0,0,137,0,0};
        for(int i=0;i<27;i++)
        Assert.assertEquals(temp[i],s.getCriterion()[i]);
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
        s.UsedNum(0,0,3);
        Assert.assertEquals(507,s.getCriterion()[0]);
        Assert.assertEquals(507,s.getCriterion()[9]);
        Assert.assertEquals(507,s.getCriterion()[18]);
    }

    @Test
    void dealFile() {
        Solve s=new Solve();
        int[] a=new int[20000];
        s.setData(a);
        Assert.assertEquals(1,s.DealFile("D:\\Learn\\Third\\software Engineering\\commit_edition\\Sudoku_self\\1.txt"));
    }

    @Test
    void getBlock() {
        Solve s=new Solve();
        Assert.assertEquals(1,s.GetBlock(0,0));
        Assert.assertEquals(2,s.GetBlock(0,4));
    }

    boolean CheckSolution(char[] data, int num)
    {
        int i, j, k,temp;
        int index=0;
        int[] criterion=new int[27];
        Solve s=new Solve();
        for (i = 0; i < num; i++) {
            for (j = 0; j < 27; j++) {
                criterion[j] = 511;
            }
            s.setCriterion(criterion);
            for (j = 0; j < 9; j++) {
                for (k = 0; k < 9; k++) {
                    while (data[index]>'9' || data[index]<'1')
                    {
                        index++;
                    }
                    temp = data[index++]-'0';
                    if (!s.Fill(j, k, temp)) {
                        System.out.println("map:"+(i+1)+"row:"+(j+1)+"clo:"+(k+1)+"value:"+temp);
                        return false;
                    }else{
                        s.UsedNum(j,k,temp);
                    }
                }
            }
        }
        return true;
    }

}
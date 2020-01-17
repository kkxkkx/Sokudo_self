package test;

import kkx.Generate;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenerateTest {

    @Test
    void generateSudoku() {
        Generate gen=new Generate();
        int num=1000;
        Assert.assertEquals(true, gen.GenerateSudoku(num));
        CheckGenerate(gen.getOutput(),num);
    }

    @Test
    void createSeed() {
        Generate gen=new Generate();
        int[] temp={1,2,3,4,5,6,7,8,9};
        gen.setSeed(temp);
        gen.setGoal(1);
        char[] out=new char[200];
        gen.setoutput(out);
        Assert.assertEquals(true, gen.CreateSeed(1));
        Assert.assertEquals(temp,gen.getSeed());
    }

    @Test
    void createMap() {
        Generate gen=new Generate();
        gen.setGoal(1);
        char[] out=new char[200];
        gen.setoutput(out);
        Assert.assertEquals(true,gen.CreateMap());
        int[][] temp={{5,1,2,7,8,9,3,4,6},
                {3, 4 ,6, 5 ,1 ,2, 7, 8, 9},
                {7 ,8 ,9, 3, 4 ,6, 5 ,1, 2},
                {2 ,5, 1 ,9 ,7 ,8 ,6 ,3, 4},
                {6 ,3 ,4, 2, 5, 1 ,9 ,7 ,8},
                {9 ,7 ,8, 6 ,3 ,4, 2 ,5 ,1},
                {1 ,2 ,5 ,8, 9, 7, 4 ,6 ,3},
                {4 ,6 ,3 ,1, 2, 5 ,8 ,9, 7},
                {8 ,9 ,7 ,4 ,6 ,3, 1, 2, 5}};
        Assert.assertEquals(temp,gen.getSudoku());
    }

    @Test
    void changeIndex() {

    }


    @Test
    void swap() {
        int[] a={1,2};
        Generate gen=new Generate();
        gen.Swap(a,0,1);
        assertEquals(2,a[0]);
        assertEquals(1,a[1]);
    }

    @Test
    void writeToOutput() {
        Generate gen=new Generate();
        gen.setPos(0);
        char[] out=new char[200];
        gen.setoutput(out);
        int  a[][]={{1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9},
                {1,2,3,4,5,6,7,8,9}};
        char[] output={'1',' ','2',' ','3',' ','4',' ','5',' ','6',' ','7',' ','8',' ','9','\n',
                '1',' ','2',' ','3',' ','4',' ','5',' ','6',' ','7',' ','8',' ','9','\n',
                '1',' ','2',' ','3',' ','4',' ','5',' ','6',' ','7',' ','8',' ','9','\n',
                '1',' ','2',' ','3',' ','4',' ','5',' ','6',' ','7',' ','8',' ','9','\n',
                '1',' ','2',' ','3',' ','4',' ','5',' ','6',' ','7',' ','8',' ','9','\n',
                '1',' ','2',' ','3',' ','4',' ','5',' ','6',' ','7',' ','8',' ','9','\n',
                '1',' ','2',' ','3',' ','4',' ','5',' ','6',' ','7',' ','8',' ','9','\n',
                '1',' ','2',' ','3',' ','4',' ','5',' ','6',' ','7',' ','8',' ','9','\n',
                '1',' ','2',' ','3',' ','4',' ','5',' ','6',' ','7',' ','8',' ','9'
        };
        gen.setSudoku(a);
        gen.WriteToOutput();
      //  Assert.assertEquals(output,gen.getOutput());
    }

    boolean compare(int[][] a, int[][] b)
    {
        int i, j;
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                if (a[i][j] != b[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean CheckGenerate(char[] result, int num)
    {
        int[][][] map = new int[num][9][9];
        int i, j, k;
        int index = 0;
        for (i = 0; i < num; i++) {
            for (j = 0; j < 9; j++) {
                map[i][j] = new int[9];
                for (k = 0; k < 9; k++) {
                    while (result[index]<'1' || result[index]>'9')
                        index++;
                    map[i][j][k] = result[index++];
                }
            }
        }
        boolean flag= true;
        for (i = 0; i < num-1; i++) {
            for (j = i + 1; j < num; j++) {
                if (!compare(map[i], map[j])) {
                    flag = false;
                    System.out.println("map:"+i+"&"+j);
                    break;
                }
                System.out.println("compare:"+i+"&"+j);
            }
            if (!flag)
                break;
        }
        return flag;
    }
}
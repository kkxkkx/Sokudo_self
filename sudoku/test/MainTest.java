package test.kkx;

import kkx.Main;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;


class MainTest {

    @Test
    void main() throws IOException {
        String[] a=new String[2];
        a[0]="11";
        Main.main(a);
        a[0]="-c";
        Main.main(a);
        a[0]="-s";
        Main.main(a);


    }

    @Test
    void whetherSolve() {
        String[] b=new String[3];
        b[0]="-s";
        b[1]="D:\\Learn\\Third\\software Engineering\\commit_edition\\Sudoku_self\\1.txt";
        Main.whetherSolve(b);
        b[1]="D:\\Learn\\Third\\software Engineering\\commit_edition\\Sudoku_self";
        Main.whetherSolve(b);
    }

    @Test
    void whetherGenerate() {
        String[] a=new String[2];
        a[0]="-c";
        a[1]="1";
        Main.whetherGenerate(a);
        a[1]="1000001";
        Main.whetherGenerate(a);
        a[1]="-1";
        Main.whetherGenerate(a);
    }
    @Test
    void isNumeric() {
        Assert.assertEquals(true, Main.isNumeric("1000"));
    }

    @Test
    void stringToFile() throws IOException {
        char[] s={'h','e','l','l','o'};
        Main.stringToFile(s);
    }
}
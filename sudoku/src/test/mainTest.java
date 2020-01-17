package test;

import kkx.main;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class mainTest {
    main m=new main();
    
    @Test
    void main() throws IOException {
    	String[] a=new String[2];
    	a[0]="11";
		m.main(a);
    	a[0]="-c";
    	m.main(a);
    	a[0]="-s";
    	m.main(a);
    	
    	
    }
    
    @Test
    void whetherSolve() {
        String b[]=new String[3];
        b[0]="-s";
        b[1]="D:\\Learn\\Third\\software Engineering\\commit_edition\\Sudoku_self\\1.txt";
        m.WhetherSolve(b);
        b[1]="D:\\Learn\\Third\\software Engineering\\commit_edition\\Sudoku_self";
        m.WhetherSolve(b);
    }

    @Test
    void whetherGenerate() {
        String a[]=new String[2];
        a[0]="-c";
        a[1]="1";
        m.WhetherGenerate(a);
        a[1]="1000001";
        m.WhetherGenerate(a);
        a[1]="-1";
        m.WhetherGenerate(a);
    }
    @Test
    void isNumeric() {
        Assert.assertEquals(true, main.isNumeric("1000"));
    }

    @Test
    void stringToFile() throws IOException {
        char[] s={'h','e','l','l','o'};
        main.StringToFile(s);
    }
}
package com.company.test;

import com.company.sudoku;
import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.*;

class sudokuTest {

    @org.junit.jupiter.api.Test
    void main() {

    }

    @org.junit.jupiter.api.Test
    void isNumeric() {
        Assert.assertEquals(true, sudoku.isNumeric("q"));
    }

    @org.junit.jupiter.api.Test
    void generateRandom() {

    }

    @org.junit.jupiter.api.Test
    void solvePuzzle() {
    }

    @org.junit.jupiter.api.Test
    void readPuzzle() throws FileNotFoundException {
        sudoku sdk=new sudoku();
        String path="D:\\Learn\\Third\\software Engineering\\Sokudo_self\\BIN\\puzzle.txt";
        sdk.InputType=sudoku.CALCULATE;
        sdk.init();
        Assert.assertEquals(true,sdk.readPuzzle(path));
        FileWriter out = sdk.openFileWriter("123.txt");
        sdk.WriteToFile(out);
        try {
            out.close();
        } catch (Exception e) {
        }
    }

    @org.junit.jupiter.api.Test
    void init() {

    }

    @org.junit.jupiter.api.Test
    void generate() {
        sudoku sdk=new sudoku();

    }

    @org.junit.jupiter.api.Test
    void writeToFile() {
        sudoku sdk=new sudoku();
        sdk.init();
        for(int i=0;i<sdk.side;i++)
        {
            for(int j=0;j<sdk.side;j++)
            {
                sdk.layout[i][j]= (byte)i;
            }
        }
        sdk.init_state=true;
        FileWriter out = sdk.openFileWriter("layout.txt");
       Assert.assertEquals(true,sdk.WriteToFile(out));
        try {
            out.close();
        } catch (Exception e) {
        }
    }
}
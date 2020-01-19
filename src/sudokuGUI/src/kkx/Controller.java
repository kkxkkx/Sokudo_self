package kkx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @ClassName: Controller
 * @Description: ��ӦUI�е��¼�
 * @author WangKeXin
 * @date 2020��01��13�� ����9:20:15
 *
 */
public class Controller implements Initializable {

    private int[][] prob;
    private int[][] answer;

    private boolean stop;
    private long startTime;
    private long curTime;

    @FXML private Text timer;
    @FXML private List<kkx.SudokuCell> cells;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTimer();
        Generate gen=new Generate();
        gen.generateRandom();
        answer=gen.getLayout();
        prob=gen.dealAns(answer);
        setCellNums(prob,answer,false);
    }



    /**
     * @Title: setCellNums
     * @Description: �����ַ���Cell��
     * @param puzzle
     * @param ans
     * @param solve
     * @return void
     * @throws
     */
    private void setCellNums(int[][] puzzle,int[][] ans,boolean solve) {
        SudokuCell cell;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cell=cells.get(i * 9 + j);
                cell.setInitStatus();
                if (puzzle[i][j] != 0) {
                    cell.setWrite(false);
                    cell.setText(Integer.toString(puzzle[i][j]));
                } else {
                    if(solve){
                        cell.setStyle(String.format("-fx-text-fill: black; -fx-font-weight: normal; -fx-font-size: %d", 20));
                        cell.wrapTextProperty().setValue(true);
                        cell.setText(Integer.toString(ans[i][j]));
                    }else
                        cell.setText("");
                }
            }
        }
    }

    /**
     * @Title: initTimer
     * @Description: ��ʼ����ʱ��
     * @return void
     * @throws
     */
    private void initTimer() {
        stop = false;
        startTime = System.currentTimeMillis();
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!stop) {
                    curTime = System.currentTimeMillis();
                    int interval = (int) ((curTime - startTime) / 1000);
                    int hour = interval / 3600;
                    int minute = (interval / 60) % 60;
                    int second = interval % 60;
                    timer.setText(String.format("%02d:%02d:%02d", hour, minute, second));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Logger logger=Logger.getLogger("controller");
                        logger.setLevel(Level.SEVERE);
                        logger.severe(e.getMessage());
                    }

                }

            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    @FXML private void keyListener(KeyEvent e) {
        String input = e.getText();
        SudokuCell cell = ((SudokuCell) e.getSource());
        if (!(e.getCode() != KeyCode.ENTER && e.getCode() == KeyCode.SPACE)) {
            cell.setNoteText(input);
        }
    }

    @FXML private void newGame() {

        Generate gen=new Generate();
        gen.generateRandom();
        answer=gen.getLayout();
        prob=gen.dealAns(answer);
        setCellNums(prob,answer,false);
        initTimer();
    }

    @FXML private void solve() {
        setCellNums(this.prob,this.answer,true);
        stop = true;
    }

    @FXML private void exit() {
        stop = true;
        Platform.exit();
        System.exit(0);
    }

    @FXML private void showHelp() {
        new AlertInfo(AlertType.INFORMATION, "����",
                "����: ������Լ����\n����: ����\n�ύ: �жϵ�ǰ�����Ƿ���ȷ\n��: ��ʾ��ǰ�����Ĵ�\n ").show();
    }

    @FXML private void showAbout() {
        new AlertInfo(AlertType.INFORMATION, "����",
                "Sudoku v1.0   2020-01-16\n���䣺kkxdlyj@163.com").show();
    }

    @FXML private void check()
    {
        if(!checkSuccess())
        {
            new AlertInfo(AlertType.INFORMATION, "����",
                    "����ϸ��飬�������пղſ����ύ").show();
            return;
        }
        int num = 0;
        int[][] curr=new int[9][9];
        for (SudokuCell cell: cells) {
            num = Integer.parseInt(cell.getText());
            curr[Character.getNumericValue(cell.getId().charAt(4))][Character.getNumericValue(cell.getId().charAt(5))]=num;
        }
        int index=0;
        int[] data=new int[81];
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                data[index++]=curr[i][j];
            }
        }
        Solve s=new Solve();
        if(s.checkSolution(data))
        {
            new AlertInfo(AlertType.INFORMATION, "��ϲ",
                    "���Ĵ���ȫ��ȷ:)").show();
        }else {
            new AlertInfo(AlertType.INFORMATION, "����",
                    "���Ĵ����д���:(").show();
        }
    }

    private boolean checkSuccess() {
        for (SudokuCell cell : cells)
            if (cell.getText().isEmpty() )
                return false;
        return true;
    }



}

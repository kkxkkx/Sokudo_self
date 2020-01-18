package kkx;

import javafx.scene.control.Button;
/**
 * @ClassName: Controller
 * @Description: ������ÿһ��С����
 * @author WangKeXin
 * @date 2020��01��13�� ����9:20:35
 *
 */
public class SudokuCell extends Button {

    private  boolean write;

    public void setWrite(boolean write) {
            this.write = write;
    }

    public boolean getWrite() {
        return write;
    }

    public SudokuCell() {
        write=true;
        setInitStatus();
    }

    public void setInitStatus() {
        write=true;
        setEffect(null);
        setStyle("-fx-text-fill: rgb(132, 117, 225)");
    }

    public void setNoteText(String input) {
        if (input.isEmpty() || !"123456789".contains(input)) {
            return;
        }
        if (this.getWrite()) {
            setStyle(String.format("-fx-text-fill: black; -fx-font-weight: normal; -fx-font-size: %d", 20));
            wrapTextProperty().setValue(false);
            setText(input);
        }
    }

}
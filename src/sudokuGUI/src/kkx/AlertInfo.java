package kkx;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
/**
 * @ClassName: AlertInfo
 * @Description: 进行弹窗
 * @author WangKeXin
 * @date 2020年01月13日 下午6:18:25
 *
 */
class AlertInfo {

    private AlertType alertType;
    private String title;
    private String headerText;
    private String contentText;

    AlertInfo(AlertType alertType, String title, String contentText) {
        this.alertType = alertType;
        this.title = title;
        this.contentText = contentText;
    }

    void show() {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

}
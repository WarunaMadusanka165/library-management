package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashBoardController {
    public JFXButton btnDashBoard;
    public JFXButton btnBook;
    public JFXButton btnMember;
    public JFXButton btnPublisher;
    public JFXButton btnBorrow;
    public AnchorPane pane;
    public ImageView imageView;

    public void btnDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("imageView"));
        pane.getChildren().clear();
        pane.getChildren().addAll(root);
    }

    public void btnBookOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Book.fxml"));
        pane.getChildren().clear();
        pane.getChildren().addAll(root);
    }

    public void btnMemberOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Member.fxml"));
        pane.getChildren().clear();
        pane.getChildren().addAll(root);
    }

    public void btnPublisherOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Publisher.fxml"));
        pane.getChildren().clear();
        pane.getChildren().addAll(root);
    }

    public void btnBorrowOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Borrow.fxml"));
        pane.getChildren().clear();
        pane.getChildren().addAll(root);
    }
}

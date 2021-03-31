package controller;

import bo.BOFactory;
import bo.SuperBO;
import bo.custom.BookBO;
import bo.custom.BorrowBO;
import bo.custom.MemberBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dao.DAOFactory;
import dao.custom.MemberDAO;
import dto.BookDTO;
import dto.BorrowDTO;
import dto.MemberDTO;
import dto.PublisherDTO;
import entity.Borrow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.BorrowTM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BorrowController {
    public JFXComboBox<String> cmbISBN;
    public JFXComboBox<String> cmbMemberID;
    public JFXTextField txtState;
    public JFXTextField txtFine;
    public TableView<BorrowTM> tblBorrow;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public JFXButton btnClear;
    public JFXButton btnDelete;
    public JFXTextField txtISBN;
    public JFXDatePicker txtReturnDatee;
    public JFXDatePicker txtIssuedDateee;
    public JFXDatePicker txtDueDatee;
    public JFXButton btnReturn;
    public JFXButton btnSearch;
    public AnchorPane pane;

    MemberBO memberBO = (MemberBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.MEMBER);
    BookBO bookBO = (BookBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.BOOK);
    private BorrowBO borrowBO;

    public void initialize(){
        borrowBO = (BorrowBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.BORROW);
        loadMemberComboBox();
        loadISBNComboBox();
        txtISBN.setVisible(false);
        loadTable();
        configurableTable();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        tblBorrow.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BorrowTM>() {
            @Override
            public void changed(ObservableValue<? extends BorrowTM> observable, BorrowTM oldValue, BorrowTM newValue) {
                int selectedIndex = tblBorrow.getSelectionModel().getSelectedIndex();

                if (selectedIndex == -1){
                    return;
                }

                BorrowTM selectedItem = tblBorrow.getSelectionModel().getSelectedItem();

                cmbISBN.getSelectionModel().select(selectedItem.getIsbn());
                cmbMemberID.getSelectionModel().select(selectedItem.getMemberID());
                txtState.setText(selectedItem.getStates());
                txtIssuedDateee.setValue(LocalDate.parse(selectedItem.getIssued_date()+""));
                txtDueDatee.setValue(LocalDate.parse(selectedItem.getDue_date()+""));


                if (selectedItem.getReturn_date()!=null){
                    txtReturnDatee.setValue(LocalDate.parse(selectedItem.getReturn_date()+""));
                }else {
                txtReturnDatee.setValue(null);
                }
                if (selectedItem.getFine()+""!=null){
                    txtFine.setText(selectedItem.getFine()+"");
                }else {
                    txtFine.setText("");
                }

                btnDelete.setDisable(false);
                btnUpdate.setDisable(false);
            }
        });
    }


    private void configurableTable(){
        tblBorrow.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tblBorrow.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("memberID"));
        tblBorrow.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("fine"));
        tblBorrow.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("due_date"));
        tblBorrow.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("states"));
        tblBorrow.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("issued_date"));
        tblBorrow.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("return_date"));
    }

    private void loadTable(){
        try {
            List<BorrowDTO> allBorrow= borrowBO.loadAllBorrow();
            ObservableList<BorrowTM> items = tblBorrow.getItems();
            items.clear();
            for (BorrowDTO borrowDTO : allBorrow) {
                items.add(new BorrowTM(
                        borrowDTO.getIsbn(),
                        borrowDTO.getMemberID(),
                        borrowDTO.getFine(),
                        borrowDTO.getDue_date(),
                        borrowDTO.getStates(),
                        borrowDTO.getIssued_date(),
                        borrowDTO.getReturn_date()
                ));
            }
            tblBorrow.refresh();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadISBNComboBox() {
        List<MemberDTO> allMembers = null;
        try {
            allMembers = memberBO.loadAllMembers();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<String> items = cmbMemberID.getItems();
        items.clear();
        for (MemberDTO memberDTO: allMembers) {
            items.addAll(memberDTO.getMemberID());
        }
    }

    private void loadMemberComboBox() {
        List<BookDTO> allBook = null;
        try {
            allBook = bookBO.loadAllBook();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<String> items = cmbISBN.getItems();
        items.clear();
        for (BookDTO bookDTO: allBook) {
            items.addAll(bookDTO.getISBN());
        }
    }


    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String isbn = this.cmbISBN.getSelectionModel().getSelectedItem();
        String memberID = this.cmbMemberID.getSelectionModel().getSelectedItem();
        String due_date = txtDueDatee.getValue()+"";
        String states = txtState.getText();
        String issued_date = txtIssuedDateee.getValue()+"";

        BorrowDTO newBorrow = new BorrowDTO(isbn,memberID,due_date,states,issued_date);

        borrowBO.memberBookCount(newBorrow);


        boolean addBorrow= false;
        try {
            addBorrow = borrowBO.addBorrow(newBorrow);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (addBorrow) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved...").showAndWait();

            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Not Saved...").showAndWait();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String isbn = this.cmbISBN.getSelectionModel().getSelectedItem();
        String memberID = this.cmbMemberID.getSelectionModel().getSelectedItem();
        double fine = Double.parseDouble(txtFine.getText());
        String due_date = txtDueDatee.getValue()+"";
        String states = txtState.getText();
        String issued_date = txtIssuedDateee.getValue()+"";
        String return_date = txtReturnDatee.getValue()+"";



        //System.out.println(bookISBN+""+pubID+""+title+""+author+""+qty+""+price);

        BorrowDTO borrow = new BorrowDTO(isbn, memberID, fine, due_date, states, issued_date,return_date);

        try {
            boolean isUpdate = borrowBO.updateBorrow(borrow);
            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated....").showAndWait();
                //clearAllText();
                loadTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Not Updated...").showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearAllText();
    }

    private void clearAllText() {
        txtFine.setText("");
        txtState.setText("");
        txtReturnDatee.setValue(null);
        txtIssuedDateee.setValue(null);
        txtDueDatee.setValue(null);
        cmbISBN.setValue("");
        cmbMemberID.setValue("");
        loadTable();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        deleteBorrow();
    }

    private void deleteBorrow() {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure ?", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get()==ButtonType.YES){
            String memberID = this.cmbMemberID.getSelectionModel().getSelectedItem();
            try {
                boolean deleteBorrow = borrowBO.deleteBorrow(memberID);
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").showAndWait();
                clearAllText();
                loadTable();
                String selectedItem = cmbMemberID.getSelectionModel().getSelectedItem();
                loadMemberComboBox();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public void txtISBNOnAction(ActionEvent actionEvent) {

    }

    public void cmbMemberIDOnAction(ActionEvent actionEvent) {

    }

    public void btnReturnOnAction(ActionEvent actionEvent) {
        String memberId = this.cmbMemberID.getSelectionModel().getSelectedItem();
        double fine = Double.parseDouble(txtFine.getText());
        String states = txtState.getText();
        String returnDate = txtReturnDatee.getValue() + "";
        try {
            BorrowDTO borrowDTO = new BorrowDTO(memberId, fine, states, returnDate);
             boolean returnBook = borrowBO.returnBook(borrowDTO);
            if (returnBook){
                new Alert(Alert.AlertType.CONFIRMATION, "Return Successed...").showAndWait();
                loadTable();
            }else {
                new Alert(Alert.AlertType.ERROR, "Try Again...").showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/BorrowSearch.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) this.pane.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Search Form");
        primaryStage.centerOnScreen();
        primaryStage.show();

        //djbkjafalkm;lla
    }
}

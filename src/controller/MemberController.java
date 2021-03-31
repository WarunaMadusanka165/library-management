package controller;

import bo.BOFactory;
import bo.custom.MemberBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import dto.MemberDTO;
import entity.Member;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.MemberTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberController {
    public JFXTextField txtMemberID;
    public JFXTextField txtMemberName;
    public JFXTextField txtMemberAddress;
    public JFXTextField txtMemberContact;
    public TableView<MemberTM> tblMember;
    public JFXButton btnMemberSave;
    public JFXButton btnMemberUpdate;
    public JFXButton btnMemberDelete;
    public JFXButton btnMemberClear;


    //MemberDAO memberDAO = new MemberDAOImpl();
    //MemberBO bo = new MemberBOImpl();
    MemberBO bo = (MemberBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.MEMBER);

    public void initialize() throws SQLException, ClassNotFoundException {
        loadTable();
        configurableTable();
        btnMemberDelete.setDisable(true);
        btnMemberUpdate.setDisable(true);

        tblMember.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MemberTM>() {
            @Override
            public void changed(ObservableValue<? extends MemberTM> observable, MemberTM oldValue, MemberTM newValue) {
                int selectedIndex = tblMember.getSelectionModel().getSelectedIndex();

                if (selectedIndex == -1){
                    return;
                }

                MemberTM selectedItem = tblMember.getSelectionModel().getSelectedItem();
                txtMemberID.setText(selectedItem.getMemberID());
                txtMemberName.setText(selectedItem.getName());
                txtMemberAddress.setText(selectedItem.getAddress());
                txtMemberContact.setText(selectedItem.getContact_number()+"");

                txtMemberID.setDisable(true);
                btnMemberDelete.setDisable(false);
                btnMemberUpdate.setDisable(false);
            }
        });
    }

    private void configurableTable() {
        tblMember.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("memberID"));
        tblMember.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblMember.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblMember.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact_number"));
    }

    private void loadTable() throws SQLException, ClassNotFoundException {
        try {

            List<MemberDTO> allMember = bo.loadAllMembers();
            ObservableList<MemberTM> items = tblMember.getItems();
            items.clear();
            for (MemberDTO memberDTO : allMember) {
                items.add(new MemberTM(
                        memberDTO.getMemberID(),
                        memberDTO.getName(),
                        memberDTO.getAddress(),
                        memberDTO.getContact_number()
                ));
            }
            tblMember.refresh();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void btnMemberSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String memberID = txtMemberID.getText();
        String memberName = txtMemberName.getText();
        String memberAddress = txtMemberAddress.getText();
        int memberContact = Integer.parseInt(txtMemberContact.getText());

        MemberDTO newMember = new MemberDTO(memberID, memberName, memberAddress, memberContact);

        boolean addMember = bo.addMember(newMember);

        if (addMember) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved...").showAndWait();
            clearAllText();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Not Saved...").showAndWait();
        }

    }

    public void btnMemberUpdateOnAction(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        String memberID = txtMemberID.getText();
        String memberName = txtMemberName.getText();
        String memberAddress = txtMemberAddress.getText();
        int memberContact = Integer.parseInt(txtMemberContact.getText());

        MemberDTO member = new MemberDTO(memberID, memberName, memberAddress, memberContact);

        boolean updateMember = bo.updateMember(member);


        if (updateMember) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated...").showAndWait();
            clearAllText();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Not Updated...").showAndWait();
        }

    }


    public void btnMemberDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

            deleteMember();

    }

    private void deleteMember() {

            Alert alert=new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure ?", ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get()==ButtonType.YES){
                String memberID = txtMemberID.getText();
                try {
                    boolean deleteMember = bo.deleteMember(memberID);
                    new Alert(Alert.AlertType.CONFIRMATION, "Deleted").showAndWait();
                    clearAllText();
                    loadTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }


    public void txtMemberIDOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String memberID = txtMemberID.getText();

        MemberDTO member = bo.searchMember(memberID);
        if (member!=null){
            txtMemberID.setText(member.getMemberID());
            txtMemberName.setText(member.getName());
            txtMemberAddress.setText(member.getAddress());
            txtMemberContact.setText(member.getContact_number()+"");
        }else {
            clearAllText();
        }

    }

    private void clearAllText() throws SQLException, ClassNotFoundException {
        txtMemberID.setText("");
        txtMemberName.setText("");
        txtMemberAddress.setText("");
        txtMemberContact.setText("");
        btnMemberDelete.setDisable(true);
        btnMemberUpdate.setDisable(true);
        txtMemberID.setDisable(false);
        txtMemberID.requestFocus();
        loadTable();
    }

    public void btnMemberClearOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        clearAllText();
    }
}

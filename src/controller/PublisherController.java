package controller;

import bo.BOFactory;
import bo.custom.PublisherBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.PublisherDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.PublisherTM;

import java.sql.SQLException;
import java.util.List;


public class PublisherController {
    public JFXTextField txtPublisherID;
    public JFXTextField txtPublisherAddress;
    public JFXTextField txtPublisherName;
    public TableView<PublisherTM> tblPublisher;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnClear;


    //PublisherDAO publisherDAO = new PublisherDAOImpl();
    //PublisherBO bo = new PublisherBOImpl();

    PublisherBO bo = (PublisherBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PUBLISHER);

    public void initialize(){
        loadTable();
        configurableTable();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        tblPublisher.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PublisherTM>() {
            @Override
            public void changed(ObservableValue<? extends PublisherTM> observable, PublisherTM oldValue, PublisherTM newValue) {
                int selectedIndex = tblPublisher.getSelectionModel().getSelectedIndex();

                if (selectedIndex == -1){
                    return;
                }

                PublisherTM selectedItem = tblPublisher.getSelectionModel().getSelectedItem();
                txtPublisherID.setText(selectedItem.getPublishID());
                txtPublisherName.setText(selectedItem.getName());
                txtPublisherAddress.setText(selectedItem.getAddress());
            }
        });
    }

    private void configurableTable() {
        tblPublisher.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("publishID"));
        tblPublisher.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblPublisher.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void loadTable(){
        try {

            List<PublisherDTO> allPublisher = bo.loadAllPublisher();
            ObservableList<PublisherTM> items = tblPublisher.getItems();
            items.clear();
            for (PublisherDTO publisherDTO : allPublisher) {
                items.add(new PublisherTM(
                        publisherDTO.getPublishID(),
                        publisherDTO.getName(),
                        publisherDTO.getAddress()
                ));
            }
            tblPublisher.refresh();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        String pubID = txtPublisherID.getText();


        boolean delete = this.bo.deletePublisher(pubID);

        if (delete){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted...").showAndWait();
            clearAllText();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR, "Not Deleted....!").showAndWait();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        String pubID = txtPublisherID.getText();
        String pubName = txtPublisherName.getText();
        String pubAddress = txtPublisherAddress.getText();

        PublisherDTO publisher = new PublisherDTO(pubID, pubName, pubAddress);
        boolean update = bo.updatePublisher(publisher);

        if (update){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated...").showAndWait();
            clearAllText();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR, "Not Updated....!").showAndWait();
        }

    }


    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        String pubID = txtPublisherID.getText();
        String pubName = txtPublisherName.getText();
        String pubAddress = txtPublisherAddress.getText();

        PublisherDTO publisher = new PublisherDTO(pubID, pubName, pubAddress);
        boolean add = bo.addPublisher(publisher);

        if (add){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved...").showAndWait();
            clearAllText();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR, "Unsaved....!").showAndWait();
        }

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearAllText();
    }

    public void txtPublisherIDOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String pubID = txtPublisherID.getText();
        PublisherDTO search = bo.searchPublisher(pubID);

        if (search!=null){
            txtPublisherID.setText(search.getPublishID());
            txtPublisherName.setText(search.getName());
            txtPublisherAddress.setText(search.getAddress());
        }else {
            clearAllText();
        }

    }

    private void clearAllText() {
        txtPublisherID.setText("");
        txtPublisherName.setText("");
        txtPublisherAddress.setText("");
        txtPublisherID.requestFocus();
    }
}

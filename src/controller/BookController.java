package controller;

import bo.BOFactory;
import bo.custom.BookBO;
import bo.custom.PublisherBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.BookDTO;
import dto.PublisherDTO;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import util.BookTM;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class BookController {
    public JFXTextField txtISBN;
    public JFXTextField txtTitle;
    public JFXTextField txtAuthor;
    public JFXTextField txtQty;
    public JFXTextField txtPrice;
    public TableView<BookTM> tblBook;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public AnchorPane pane;
    public JFXButton btnClear;
    public JFXTextField txtpubID;
    @FXML
    private JFXComboBox<String> cmbPubID;

    PublisherBO bo = (PublisherBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PUBLISHER);
    BookBO bookBO = (BookBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.BOOK);

    public void initialize() throws SQLException, ClassNotFoundException {

        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(2000));
        ft.setNode(pane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        txtpubID.setVisible(false);
        loadComboBox();
        loadTable();
        configurableTable();
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        tblBook.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BookTM>() {
            @Override
            public void changed(ObservableValue<? extends BookTM> observable, BookTM oldValue, BookTM newValue) {
                int selectedIndex = tblBook.getSelectionModel().getSelectedIndex();

                if (selectedIndex == -1){
                    return;
                }
                BookTM selectedItem = tblBook.getSelectionModel().getSelectedItem();

                txtISBN.setText(selectedItem.getIsbn());
                cmbPubID.getSelectionModel().select(selectedItem.getPublishID());
                txtAuthor.setText(selectedItem.getAuthor());
                txtQty.setText(selectedItem.getQty()+"");
                txtPrice.setText(selectedItem.getPrice()+"");
                txtTitle.setText(selectedItem.getTitle());

                txtISBN.setDisable(true);
                btnDelete.setDisable(false);
                btnUpdate.setDisable(false);
            }
        });
    }
    private void configurableTable() {
        tblBook.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tblBook.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("publishID"));
        tblBook.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("title"));
        tblBook.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("author"));
        tblBook.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblBook.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadTable() {
        try {
            List<BookDTO> allBook = bookBO.loadAllBook();
            ObservableList<BookTM> items = tblBook.getItems();
            items.clear();
            for (BookDTO bookDTO : allBook) {
                items.add(new BookTM(
                        bookDTO.getISBN(),
                        bookDTO.getPubID(),
                        bookDTO.getTitle(),
                        bookDTO.getAuthor(),
                        bookDTO.getQty(),
                        bookDTO.getPrice()
                ));
            }
            tblBook.refresh();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void loadComboBox() throws SQLException, ClassNotFoundException {

        List<PublisherDTO> allPublisher = bo.loadAllPublisher();
        ObservableList<String> items = cmbPubID.getItems();
        items.clear();
        for (PublisherDTO publisherDTO : allPublisher) {
            items.addAll(publisherDTO.getPublishID());
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String bookISBN = txtISBN.getText();
        String bookPubID = this.cmbPubID.getSelectionModel().getSelectedItem();
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double price = Double.parseDouble(txtPrice.getText());

        BookDTO newBook = new BookDTO(bookISBN,bookPubID,title,author,qty,price);


        boolean addBook = false;
        try {
            addBook = bookBO.addBook(newBook);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (addBook) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved...").showAndWait();
            loadTable();
            clearAllText();
        } else {
            new Alert(Alert.AlertType.ERROR, "Not Saved...").showAndWait();
        }
    }

    private void clearAllText() {
        txtISBN.setText("");
        txtpubID.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        txtQty.setText("");
        txtPrice.setText("");
        txtpubID.setVisible(false);
        cmbPubID.setVisible(true);
        txtISBN.requestFocus();
        txtISBN.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        loadTable();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String bookISBN = txtISBN.getText();
        String pubID = cmbPubID.getSelectionModel().getSelectedItem();
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double price = Double.parseDouble(txtPrice.getText());



        //System.out.println(bookISBN+""+pubID+""+title+""+author+""+qty+""+price);

        BookDTO book = new BookDTO(bookISBN, pubID, title, author, qty, price);

//        boolean updateBook = false;
        try {
            boolean isUpdate = bookBO.updateBook(book);
            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated...").showAndWait();
                loadTable();
                clearAllText();
            } else {
                new Alert(Alert.AlertType.ERROR, "Not Updated...").showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        deleteBook();
    }

    private void deleteBook() {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure ?", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get()==ButtonType.YES){
            String bookISBN = txtISBN.getText();
            if(bookISBN==null){
                btnDelete.setDisable(true);
            }
            try {
                boolean deleteBook = bookBO.deleteBook(bookISBN);
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").showAndWait();
                loadTable();
                clearAllText();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearAllText();
    }

    public void txtISBNOnAction(ActionEvent actionEvent) {
        String bookISBN = txtISBN.getText();

        BookDTO book = null;
        try {
            book = bookBO.searchBook(bookISBN);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (book!=null){
            txtpubID.setVisible(true);
            cmbPubID.setVisible(false);
            txtISBN.setText(book.getISBN());
            txtpubID.setText(book.getPubID());
            txtTitle.setText(book.getTitle());
            txtAuthor.setText(book.getAuthor());
            txtQty.setText(book.getQty()+ " ");
            txtPrice.setText(book.getPrice()+ " ");
        }else {
            clearAllText();
            txtpubID.setVisible(false);
            cmbPubID.setVisible(true);
        }
    }

}

package com.example.layeredarchitecture.controller;

import com.example.layeredarchitecture.dao.custom.CustomerDAO;
import com.example.layeredarchitecture.dao.custom.ItemDAO;
import com.example.layeredarchitecture.dao.custom.OrderDAO;
import com.example.layeredarchitecture.dao.custom.OrderDetailsDAO;
import com.example.layeredarchitecture.dao.custom.impl.CustomerDAOImpl;
import com.example.layeredarchitecture.dao.custom.impl.ItemDAOImpl;
import com.example.layeredarchitecture.dao.custom.impl.OrderDAOImpl;
import com.example.layeredarchitecture.dao.custom.impl.OrderDetailsDAOImpl;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;
import com.example.layeredarchitecture.view.tdm.OrderDetailTM;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



public class PlaceOrderFormController {

    public AnchorPane root;
    public JFXButton btnPlaceOrder;
    public TextField txtCustomerName;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public JFXButton btnSave;
    public TableView<OrderDetailTM> tblOrderDetails;
    public TextField txtUnitPrice;
    public JFXComboBox<String> cmbCustomerId;
    public JFXComboBox<String> cmbItemCode;
    public TextField txtQty;
    public Label lblId;
    public Label lblDate;
    public Label lblTotal;
    private String orderId;
    private ItemDAO itemDAO = new ItemDAOImpl();
    private CustomerDAO customerDAO = new CustomerDAOImpl();
    private OrderDAO ordDao = new OrderDAOImpl();
    private OrderDetailsDAO ordDetDao = new OrderDetailsDAOImpl();

    public void initialize() {

        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrderDetailTM, Button> lastCol = (TableColumn<OrderDetailTM, Button>) tblOrderDetails.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblOrderDetails.getItems().remove(param.getValue());
                tblOrderDetails.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        btnSave.setDisable(true);
        txtQty.setEditable(false);
        orderId = generateNewOrderId();
        btnPlaceOrder.setDisable(true);
        txtUnitPrice.setEditable(false);
        txtQtyOnHand.setEditable(false);
        txtDescription.setEditable(false);
        txtCustomerName.setEditable(false);
        lblId.setText("Order ID: " + orderId);
        txtUnitPrice.setFocusTraversable(false);
        txtQtyOnHand.setFocusTraversable(false);
        txtDescription.setFocusTraversable(false);
        txtCustomerName.setFocusTraversable(false);
        lblDate.setText(LocalDate.now().toString());
        txtQty.setOnAction(event -> btnSave.fire());

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();

            if (newValue != null) {
                try {
                    /*Search Customer*/
                    var dto = customerDAO.search(newValue);
                    txtCustomerName.setText(dto.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                txtCustomerName.clear();
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            btnSave.setDisable(newItemCode == null);

            if (newItemCode != null) {

                /*Find Item*/
                try {
                    if (!existItem(newItemCode + "")) {
//                        throw new NotFoundException("There is no such item associated with the id " + code);
                    }

                    ItemDTO dto = itemDAO.search(newItemCode);
                    ItemDTO item = new ItemDTO(newItemCode + "", dto.getDescription(), dto.getQtyOnHand(), dto.getUnitPrice());

                    txtDescription.setText(item.getDescription());
                    txtUnitPrice.setText(item.getUnitPrice().setScale(2).toString());

//                    txtQtyOnHand.setText(tblOrderDetails.getItems().stream().filter(detail-> detail.getCode().equals(item.getCode())).<Integer>map(detail-> item.getQtyOnHand() - detail.getQty()).findFirst().orElse(item.getQtyOnHand()) + "");
                    Optional<OrderDetailTM> optOrderDetail = tblOrderDetails.getItems().stream().filter(detail -> detail.getCode().equals(newItemCode)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getQty() : item.getQtyOnHand()) + "");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                txtDescription.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmbItemCode.setDisable(true);
                cmbItemCode.setValue(selectedOrderDetail.getCode());
                btnSave.setText("Update");
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getQty() + "");
                txtQty.setText(selectedOrderDetail.getQty() + "");
            } else {
                btnSave.setText("Add");
                cmbItemCode.setDisable(false);
                cmbItemCode.getSelectionModel().clearSelection();
                txtQty.clear();
            }

        });

        loadAllCustomerIds();
        loadAllItemCodes();
    }

    private boolean existItem(String code) throws Exception {
        return itemDAO.exist(code);
    }

    boolean existCustomer(String id) throws Exception {
        return customerDAO.exist(id);
    }

    public String generateNewOrderId() {
        try {

            return ordDao.genOrdId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }

    private void loadAllCustomerIds() {
        try {

            ArrayList<CustomerDTO> list = customerDAO.getAllId();

            for (CustomerDTO dto: list) {
                cmbCustomerId.getItems().add(dto.getId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllItemCodes() {
        try {
            /*Get all items*/
            ArrayList<ItemDTO> list = itemDAO.getAll();
            for (ItemDTO dto: list) {
                cmbItemCode.getItems().add(dto.getCode());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/com/example/layeredarchitecture/main-form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

        String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
        String description = txtDescription.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtQty.getText());
        BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2);

        boolean exists = tblOrderDetails.getItems().stream().anyMatch(detail -> detail.getCode().equals(itemCode));

        if (exists) {
            OrderDetailTM orderDetailTM = tblOrderDetails.getItems().stream().filter(detail -> detail.getCode().equals(itemCode)).findFirst().get();

            if (btnSave.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQty(qty);
                orderDetailTM.setTotal(total);
                tblOrderDetails.getSelectionModel().clearSelection();
            } else {
                orderDetailTM.setQty(orderDetailTM.getQty() + qty);
                total = new BigDecimal(orderDetailTM.getQty()).multiply(unitPrice).setScale(2);
                orderDetailTM.setTotal(total);
            }
            tblOrderDetails.refresh();
        } else {
            tblOrderDetails.getItems().add(new OrderDetailTM(itemCode, description, qty, unitPrice, total));
        }
        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();
    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);

        for (OrderDetailTM detail : tblOrderDetails.getItems()) {
            total = total.add(detail.getTotal());
        }
        lblTotal.setText("Total: " +total);
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbCustomerId.getSelectionModel().getSelectedItem() != null && !tblOrderDetails.getItems().isEmpty()));
    }

    public void txtQty_OnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrder_OnAction(ActionEvent actionEvent) {

        try {
            var isSaved = saveOrder(orderId, LocalDate.now(), cmbCustomerId.getValue(), tblOrderDetails.getItems().stream().map(tm -> new OrderDetailDTO(orderId, tm.getCode(), tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        orderId = generateNewOrderId();
        lblId.setText("Order Id: " + orderId);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblOrderDetails.getItems().clear();
        txtQty.clear();
        calculateTotal();
    }

    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {

        /*Transaction*/
        Connection connection = null;
        try {
            connection=DBConnection.getDbConnection().getConnection();

            //Check order id already exist or not

            boolean b1 = ordDao.ExistId(orderId);
            /*if order id already exist*/
            if (b1) {
                return false;
            }

            connection.setAutoCommit(false);

            //Save the Order to the order table
            boolean b2 = ordDao.saveOrd(new OrderDTO(orderId, orderDate, customerId));

            if (!b2) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            // add data to the Order Details table

            for (OrderDetailDTO detail : orderDetails) {
                boolean b3 = ordDetDao.saveOrdDet(detail);
                if (!b3) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

                //Search & Update Item
                ItemDTO item = itemDAO.search(detail.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

                //update item
                boolean b = itemDAO.update(new ItemDTO(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice()));

                if (!b) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}

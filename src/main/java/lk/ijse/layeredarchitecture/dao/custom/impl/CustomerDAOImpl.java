package lk.ijse.layeredarchitecture.dao.custom.impl;

import lk.ijse.layeredarchitecture.dao.SQLUtil;
import lk.ijse.layeredarchitecture.dao.custom.CustomerDAO;
import lk.ijse.layeredarchitecture.entity.Customer;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.setQue("SELECT * FROM Customer");

        ArrayList<Customer> cusList = new ArrayList<>();

        while (rst.next()) {
            cusList.add(
                    new Customer(
                            rst.getString("id"),
                            rst.getString("name"),
                            rst.getString("address")
                    )
            );
        }
        return cusList;
    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.setQue("INSERT INTO Customer (id,name, address) VALUES (?,?,?)", entity.getId(), entity.getName(), entity.getAddress());
    }

    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.setQue("UPDATE Customer SET name=?, address=? WHERE id=?", dto.getName(), dto.getAddress(), dto.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.setQue("DELETE FROM Customer WHERE id=?", id);
    }

    @Override
    public String genId() throws SQLException, ClassNotFoundException {

        ResultSet rst =  SQLUtil.setQue("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");

        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.setQue("SELECT id FROM Customer WHERE id=?", id);
        return rst.next();
    }

    @Override
    public ArrayList<Customer> getAllId() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.setQue("SELECT * FROM Customer");

        ArrayList<Customer> list = new ArrayList<>();
        while (rst.next()) {
            list.add(
                    new Customer(
                            rst.getString(1)
                    )
            );
        }
        return list;
    }

    @Override
    public Customer search(String newValue) throws SQLException, ClassNotFoundException {

        try {
            if (!exist(newValue + "")) {
//                            "There is no such customer associated with the id " + id
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
            }

            ResultSet rst = SQLUtil.setQue("SELECT * FROM Customer WHERE id=?", newValue);
            rst.next();
            Customer dto = new Customer(newValue + "", rst.getString("name"), rst.getString("address"));
            return dto;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
        }
        return null;
    }
}

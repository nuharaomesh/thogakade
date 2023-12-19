package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO{

    @Override
    public ArrayList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.setQue("SELECT * FROM Customer");

        ArrayList<CustomerDTO> cusList = new ArrayList<>();

        while (rst.next()) {
            cusList.add(
                    new CustomerDTO(
                            rst.getString("id"),
                            rst.getString("name"),
                            rst.getString("address")
                    )
            );
        }
        return cusList;
    }

    @Override
    public boolean save(CustomerDTO dto) throws Exception {
        return SQLUtil.setQue("INSERT INTO Customer VALUES (?,?,?)", dto.getId(), dto.getName(), dto.getAddress());
    }

    @Override
    public boolean update(CustomerDTO dto) throws SQLException, ClassNotFoundException {
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
        return SQLUtil.setQue("SELECT id FROM Customer WHERE id=?", id);
    }

    @Override
    public ArrayList<CustomerDTO> getAllId() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.setQue("SELECT * FROM Customer");

        ArrayList<CustomerDTO> list = new ArrayList<>();
        while (rst.next()) {
            list.add(
                    new CustomerDTO(
                            rst.getString(1)
                    )
            );
        }
        return list;
    }

    @Override
    public CustomerDTO search(String newValue) throws SQLException, ClassNotFoundException {

        try {
            if (!exist(newValue + "")) {
//                            "There is no such customer associated with the id " + id
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
            }

            ResultSet rst = SQLUtil.setQue("SELECT * FROM Customer WHERE id=?", newValue);
            rst.next();
            CustomerDTO dto = new CustomerDTO(newValue + "", rst.getString("name"), rst.getString("address"));
            return dto;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
        }
        return null;
    }
}

package com.example.layeredarchitecture.DAO;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerDAO {

    public ArrayList<CustomerDTO> getAllCus() throws Exception {

        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Customer");

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
}

package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public interface CustomerDAO {

    ArrayList<CustomerDTO> getAllCus() throws Exception;
    void saveCus(CustomerDTO dto) throws Exception;
    void updateCus(CustomerDTO dto) throws SQLException, ClassNotFoundException;
    void delCus(String id) throws SQLException, ClassNotFoundException;
    String getId() throws SQLException, ClassNotFoundException;
    boolean existCus(String id) throws SQLException, ClassNotFoundException;
    ArrayList<CustomerDTO> getAllCusId() throws SQLException, ClassNotFoundException;
}

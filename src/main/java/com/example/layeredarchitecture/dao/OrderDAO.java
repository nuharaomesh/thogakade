package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.OrderDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public interface OrderDAO {

    String genOrdId() throws SQLException, ClassNotFoundException;
    boolean saveOrd(OrderDTO dto) throws SQLException, ClassNotFoundException;
    boolean ExistId(String orderId) throws SQLException, ClassNotFoundException;
}

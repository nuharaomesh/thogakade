package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.OrderDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class OrderDAOImpl implements OrderDAO{

    private ItemDAO itemDao = new ItemDAOImpl();
    private OrderDetailsDAOImpl ordDetDao = new OrderDetailsDAOImpl();

    @Override
    public String genOrdId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.setQue("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");
        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";

    }

    @Override
    public boolean saveOrd(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.setQue("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)",
                dto.getOrderId(), Date.valueOf(dto.getOrderDate()), dto.getCustomerId());
    }

    @Override
    public boolean ExistId(String orderId) throws SQLException, ClassNotFoundException {
        return SQLUtil.setQue("SELECT oid FROM `Orders` WHERE oid=?", orderId);
    }
}

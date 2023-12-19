package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.zip.CheckedOutputStream;

public class OrderDetailsDAOImpl implements OrderDetailsDAO{

    private ItemDAO itemDAO = new ItemDAOImpl();

    @Override
    public boolean saveOrdDet(OrderDetailDTO dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.setQue("INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)", dto.getoId(), dto.getItemCode(), dto.getQty(), dto.getUnitPrice());
    }
}

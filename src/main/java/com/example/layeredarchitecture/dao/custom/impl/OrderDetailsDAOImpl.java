package com.example.layeredarchitecture.dao.custom.impl;

import com.example.layeredarchitecture.dao.SQLUtil;
import com.example.layeredarchitecture.dao.custom.ItemDAO;
import com.example.layeredarchitecture.dao.custom.OrderDetailsDAO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.SQLException;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    private ItemDAO itemDAO = new ItemDAOImpl();

    @Override
    public boolean saveOrdDet(OrderDetailDTO dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.setQue("INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)", dto.getoId(), dto.getItemCode(), dto.getQty(), dto.getUnitPrice());
    }
}

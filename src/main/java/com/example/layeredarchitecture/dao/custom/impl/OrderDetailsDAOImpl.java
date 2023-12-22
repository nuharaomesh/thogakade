package com.example.layeredarchitecture.dao.custom.impl;

import com.example.layeredarchitecture.dao.SQLUtil;
import com.example.layeredarchitecture.dao.custom.ItemDAO;
import com.example.layeredarchitecture.dao.custom.OrderDetailsDAO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    private ItemDAO itemDAO = new ItemDAOImpl();

    @Override
    public boolean saveOrdDet(OrderDetailDTO dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.setQue("INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)", dto.getoId(), dto.getItemCode(), dto.getQty(), dto.getUnitPrice());
    }

    @Override
    public ArrayList getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Object dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Object dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String genId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList getAllId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Object search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}

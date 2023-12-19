package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;
import com.example.layeredarchitecture.view.tdm.ItemTM;

import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO{

    @Override
    public ArrayList<ItemDTO> getAll() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.setQue("SELECT * FROM Item");

        ArrayList<ItemDTO> list = new ArrayList<>();
        while (rst.next()) {
            list.add(
                    new ItemDTO(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getInt(3),
                            rst.getBigDecimal(4)
                    )
            );
        }
        return list;
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.setQue("DELETE FROM Item WHERE code=?", code);
    }

    @Override
    public boolean save(ItemDTO dto) throws Exception {
        return SQLUtil.setQue("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)",
                dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand());
    }

    @Override
    public boolean update(ItemDTO dto) throws SQLException, ClassNotFoundException{
        return SQLUtil.setQue("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",
                dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand(), dto.getCode());
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.setQue("SELECT code FROM Item WHERE code=?",
                code);
    }

    @Override
    public String genId() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.setQue("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");

        if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }

    @Override
    public ArrayList<ItemDTO> getAllId() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.setQue("SELECT * FROM Item");

        ArrayList<ItemDTO> list = new ArrayList<>();
        while (rst.next()) {
            list.add(
                    new ItemDTO(
                            rst.getString(1)
                    )
            );
        }
        return list;
    }

    @Override
    public ItemDTO search(String itemCode) throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.setQue("SELECT * FROM Item WHERE code=?", itemCode);

        ItemDTO dto = new ItemDTO();

        if (rst.next()) {
            dto = new ItemDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getBigDecimal(4)
            );
        }
        return dto;
    }

    @Override
    public boolean setValues(OrderDetailDTO detail) throws SQLException, ClassNotFoundException {

        ItemDTO item = search(detail.getItemCode());
        item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());
        return update(item);
    }
}

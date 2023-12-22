package lk.ijse.layeredarchitecture.dao.custom.impl;

import lk.ijse.layeredarchitecture.dao.SQLUtil;
import lk.ijse.layeredarchitecture.dao.custom.ItemDAO;
import lk.ijse.layeredarchitecture.entity.Item;
import lk.ijse.layeredarchitecture.model.OrderDetailDTO;

import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.setQue("SELECT * FROM Item");

        ArrayList<Item> list = new ArrayList<>();
        while (rst.next()) {
            list.add(
                    new Item(
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
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.setQue("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)",
                entity.getCode(), entity.getDescription(), entity.getUnitPrice(), entity.getQtyOnHand());
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException{
        return SQLUtil.setQue("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",
                entity.getDescription(), entity.getUnitPrice(), entity.getQtyOnHand(), entity.getCode());
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.setQue("SELECT code FROM Item WHERE code=?",
                code);
        return rst.next();
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
    public ArrayList<Item> getAllId() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.setQue("SELECT * FROM Item");

        ArrayList<Item> list = new ArrayList<>();
        while (rst.next()) {
            list.add(
                    new Item(
                            rst.getString(1)
                    )
            );
        }
        return list;
    }

    @Override
    public Item search(String itemCode) throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.setQue("SELECT * FROM Item WHERE code=?", itemCode);

        Item dto = new Item();

        if (rst.next()) {
            dto = new Item (
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

        Item item = search(detail.getItemCode());
        item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());
        return update(item);
    }
}

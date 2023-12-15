package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.view.tdm.ItemTM;

import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl {


    public ArrayList<ItemTM> getAllIt() throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Item");

        ArrayList<ItemTM> dto = new ArrayList<>();
        while (rst.next()) {
            dto.add(
                    new ItemTM(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getBigDecimal(3),
                            rst.getInt(4)
                    )
            );
        }
        return dto;
    }

    public void delItem(String code) throws Exception{

        Connection connection = DBConnection.getDbConnection().getConnection();

        PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE code=?");
        pstm.setString(1, code);
        pstm.executeUpdate();
    }

    public void saveItem(ItemDTO dto) throws Exception {

        Connection connection = DBConnection.getDbConnection().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)");
        pstm.setString(1, dto.getCode());
        pstm.setString(2, dto.getDescription());
        pstm.setBigDecimal(3, dto.getUnitPrice());
        pstm.setInt(4, dto.getQtyOnHand());
        pstm.executeUpdate();
    }

    public void updateItem(ItemDTO dto) throws Exception{

        Connection connection = DBConnection.getDbConnection().getConnection();

        PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
        pstm.setString(1, dto.getDescription());
        pstm.setBigDecimal(2, dto.getUnitPrice());
        pstm.setInt(3, dto.getQtyOnHand());
        pstm.setString(4, dto.getCode());
        pstm.executeUpdate();
    }

    public boolean existIt(String code) throws Exception {

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM Item WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();
    }

    public String genId() throws Exception {

        Connection connection = DBConnection.getDbConnection().getConnection();

        ResultSet rst = connection.createStatement().executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }

    public ArrayList<ItemDTO> getItCode() throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getDbConnection().getConnection();

        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Item");

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
}

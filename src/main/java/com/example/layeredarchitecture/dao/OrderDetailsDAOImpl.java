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
    public boolean saveOrdDet(String orderId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.getDbConnection().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)");

            for (OrderDetailDTO detail : orderDetails) {

                pstm.setString(1, orderId);
                pstm.setString(2, detail.getItemCode());
                pstm.setInt(3, detail.getQty());
                pstm.setBigDecimal(4, detail.getUnitPrice());
                pstm.executeUpdate();
                //Search & Update Item
                itemDAO.setValues(detail);
            }
            return true;
    }
}

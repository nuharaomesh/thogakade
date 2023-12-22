package lk.ijse.layeredarchitecture.dao.custom;

import lk.ijse.layeredarchitecture.dao.CrudDAO;
import lk.ijse.layeredarchitecture.entity.Item;
import lk.ijse.layeredarchitecture.model.OrderDetailDTO;

import java.sql.*;

public interface ItemDAO extends CrudDAO<Item> {

    boolean setValues(OrderDetailDTO detail) throws SQLException, ClassNotFoundException;
}

package lk.ijse.layeredarchitecture.dao.custom;

import lk.ijse.layeredarchitecture.dao.CrudDAO;
import lk.ijse.layeredarchitecture.entity.OrderDetail;

import java.sql.SQLException;

public interface OrderDetailsDAO extends CrudDAO {

    boolean saveOrdDet(OrderDetail dto) throws SQLException, ClassNotFoundException;
}

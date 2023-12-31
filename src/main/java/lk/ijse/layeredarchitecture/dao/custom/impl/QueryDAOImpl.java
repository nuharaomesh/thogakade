package lk.ijse.layeredarchitecture.dao.custom.impl;

import lk.ijse.layeredarchitecture.dao.SQLUtil;
import lk.ijse.layeredarchitecture.dao.custom.QueryDAO;
import lk.ijse.layeredarchitecture.model.CustomDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<CustomDTO> getCusAndOrd() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.setQue("SELECT c.name, od.qty, od.unitPrice FROM Customer c JOIN Orders o ON c.id = o.customerID JOIN OrderDetails od ON o.oid =  od.oid");

        ArrayList<CustomDTO> list = new ArrayList<>();

        while (rst.next()) {
            list.add(
                    new CustomDTO(
                            rst.getString(1),
                            rst.getInt(2),
                            rst.getBigDecimal(3)
                    )
            );
        }
        return list;
    }
}

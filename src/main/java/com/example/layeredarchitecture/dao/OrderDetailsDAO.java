package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDAO {

    boolean saveOrdDet(OrderDetailDTO dto) throws SQLException, ClassNotFoundException;
}

package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;
import com.example.layeredarchitecture.view.tdm.ItemTM;

import java.sql.*;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<ItemDTO> {

    boolean setValues(OrderDetailDTO detail) throws SQLException, ClassNotFoundException;
}

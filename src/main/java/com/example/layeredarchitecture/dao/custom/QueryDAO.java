package com.example.layeredarchitecture.dao.custom;

import com.example.layeredarchitecture.dao.SuperDAO;
import com.example.layeredarchitecture.model.CustomDTO;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {

    ArrayList<CustomDTO> getCusAndOrd() throws SQLException, ClassNotFoundException;
}

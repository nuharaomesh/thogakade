package com.example.layeredarchitecture.dao.custom;

import com.example.layeredarchitecture.model.QueryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO {

    ArrayList<QueryDTO> getCusAndOrd() throws SQLException, ClassNotFoundException;
}

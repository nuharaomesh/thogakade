package com.example.layeredarchitecture.dao.custom;

import com.example.layeredarchitecture.model.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomDAO {

    ArrayList<CustomDTO> getCusAndOrd() throws SQLException, ClassNotFoundException;
}

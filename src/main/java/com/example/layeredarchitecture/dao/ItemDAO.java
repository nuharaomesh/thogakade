package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;
import com.example.layeredarchitecture.view.tdm.ItemTM;

import java.sql.*;
import java.util.ArrayList;

public interface ItemDAO {

    ArrayList<ItemTM> getAllIt() throws SQLException, ClassNotFoundException;
    void delItem(String code) throws Exception;
    void saveItem(ItemDTO dto) throws Exception;
    boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException;
    boolean existIt(String code) throws Exception;
    String genId() throws Exception;
    ArrayList<ItemDTO> getItCode() throws SQLException, ClassNotFoundException;
    ItemDTO searchItem(String newItemCode) throws SQLException, ClassNotFoundException;
    boolean setValues(OrderDetailDTO detail) throws SQLException, ClassNotFoundException;
}

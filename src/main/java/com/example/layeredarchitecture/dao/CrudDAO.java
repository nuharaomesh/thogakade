package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> {

    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    boolean save(T dto) throws Exception;
    boolean update(T dto) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    String genId() throws SQLException, ClassNotFoundException;
    boolean exist(String id) throws SQLException, ClassNotFoundException;
    ArrayList<T> getAllId() throws SQLException, ClassNotFoundException;
    T search(String id) throws SQLException, ClassNotFoundException;
}

package dao;

import entity.Member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CrudDAO<T,ID> extends SuperDAO{
    boolean add(T entity) throws SQLException, ClassNotFoundException;
    boolean update(T entity) throws SQLException, ClassNotFoundException;
    boolean delete(ID id) throws SQLException, ClassNotFoundException;
    T search(ID id) throws SQLException, ClassNotFoundException;
    List<T> getAll() throws SQLException, ClassNotFoundException;
}

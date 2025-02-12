package karbbone.todayversus.dao;

import java.util.List;

public interface DaoEntity<T> {
    boolean insert(T entity);
    boolean update(T entity);
    boolean delete(int id);
    T findById(int id);
    List<T> findAll();
}

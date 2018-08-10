package lk.ijse.mountCalvary.dao;

import java.util.ArrayList;

public interface CrudDAO<E, T> extends SuperDAO {
    boolean save(E entity) throws Exception;

    boolean update(E entity) throws Exception;

    boolean delete(T id) throws Exception;

    E search(T id) throws Exception;

    ArrayList<E> getAll() throws Exception;

    T lastIndex() throws Exception;

    //This will need sometime when you want to get last incremented index
    T getIncrementIndex() throws Exception;
}

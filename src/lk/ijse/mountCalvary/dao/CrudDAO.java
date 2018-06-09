package lk.ijse.mountCalvary.dao;

import java.util.ArrayList;

public interface CrudDAO<E, T> extends SuperDAO {
    public boolean save(E entity) throws Exception;

    public boolean update(E entity) throws Exception;

    public boolean delete(T id) throws Exception;

    public E search(T id) throws Exception;

    public ArrayList<E> getAll() throws Exception;

    public T lastIndex() throws Exception;

    //This will need sometime when you want to get last incremented index
    public T getIncrementIndex() throws Exception;
}

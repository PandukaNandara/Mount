package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.EventListDAO;
import lk.ijse.mountCalvary.entity.EventList;

import java.sql.ResultSet;
import java.util.ArrayList;

public class EventListDAOImpl implements EventListDAO {

    @Override
    public boolean save(EventList evtlist) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO event_list VALUES (?, ?, ?, ?)",
                evtlist.getELID(),
                evtlist.getCID(),
                evtlist.getEID(),
                evtlist.getGID()
        ) > 0;
    }
    @Override
    public boolean saveWithoutPKey(EventList evtlist) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO event_list(CID, EID, GID) VALUES (?, ?, ?)",
                evtlist.getCID(),
                evtlist.getEID(),
                evtlist.getGID()
        ) > 0;
    }

    @Override
    public boolean update(EventList evtlist) throws Exception {
        return CrudUtil.executeUpdate("UPDATE event_list set ELID = ?, CID = ?, EID = ?, GID = ? where ELID = ?",
                evtlist.getELID(),
                evtlist.getCID(),
                evtlist.getEID(),
                evtlist.getGID(),
                evtlist.getELID()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("DELETE from event_list where ELID = ?", id) > 0;
    }

    @Override
    public EventList search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From event_list where ELID = ?", id);
        if(rst.next()) {
            return new EventList(
                    rst.getInt("ELID"),
                    rst.getInt("CID"),
                    rst.getInt("EID"),
                    rst.getInt("GID")
            );
        }else{
            return null;
        }
    }

    @Override
    public ArrayList<EventList> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From event_list");
        ArrayList<EventList> allEvent_list = new ArrayList<>();
        if(rst.next()) {
            allEvent_list.add(new EventList(
                    rst.getInt("ELID"),
                    rst.getInt("CID"),
                    rst.getInt("EID"),
                    rst.getInt("GID")
            ));
        }
        return allEvent_list;
    }

    @Override
    public Integer lastIndex() throws Exception {
        return CrudUtil.executeQuery("SELECT max(ELID) From event_list").getInt(1);
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'event_list'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }
}

package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.EventDAO;
import lk.ijse.mountCalvary.entity.Event;

import java.sql.ResultSet;
import java.util.ArrayList;

public class EventDAOImpl implements EventDAO {
    @Override
    public boolean save(Event evt) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO EVENT VALUES (?, ?, ?, ?)",
                evt.getEID(),
                evt.geteName(),
                evt.isGender(),
                evt.getAID()
        ) > 0;
    }

    @Override
    public boolean saveWithoutPKey(Event evt) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO EVENT(eName, gender, AID) VALUES (?, ?, ?)",
                evt.geteName(),
                evt.isGender(),
                evt.getAID()
        ) > 0;
    }

    @Override
    public ArrayList<Event> getEventsForThisActivity(int AID) throws Exception {
        ArrayList<Event> events = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Event where AID = ?", AID);
        while (rst.next()) {
            events.add(new Event(
                            rst.getInt("EID"),
                            rst.getString("eName"),
                            rst.getBoolean("gender"),
                            rst.getInt("AID")
                    )
            );
        }
        return events;
    }

    @Override
    public boolean update(Event evt) throws Exception {
        return CrudUtil.executeUpdate("UPDATE Event set EID = ?, eName = ?, gender = ?, AID = ? where EID = ?",
                evt.getEID(),
                evt.geteName(),
                evt.isGender(),
                evt.getAID(),
                evt.getEID()
        ) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.executeUpdate("DELETE from Event where EID = ?", id) > 0;
    }

    @Override
    public Event search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Event where EID = ?", id);
        if (rst.next()) {
            return new Event(
                    rst.getInt("EID"),
                    rst.getString("eName"),
                    rst.getBoolean("gender"),
                    rst.getInt("AID")
            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Event> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * From Event");
        ArrayList<Event> evtList = new ArrayList<>();
        while (rst.next()) {
            evtList.add(new Event(
                    rst.getInt("EID"),
                    rst.getString("eName"),
                    rst.getBoolean("gender"),
                    rst.getInt("AID")
            ));
        }
        return evtList;
    }

    @Override
    public Integer lastIndex() throws Exception {
        return CrudUtil.executeQuery("SELECT max(EID) From Event").getInt(1);
    }

    @Override
    public Integer getIncrementIndex() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_name = 'Event'\n" +
                "AND table_schema = DATABASE( ) ;");
        rst.next();
        return rst.getInt(1) - 1;
    }
}

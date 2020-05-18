package lk.ijse.mountCalvary.dao.custom;

import lk.ijse.mountCalvary.dao.CrudDAO;
import lk.ijse.mountCalvary.entity.Competition;

import java.util.ArrayList;

public interface CompetitionDAO extends CrudDAO<Competition, Integer> {

    boolean saveWithoutPKey(Competition com) throws Exception;

    Competition search(String name) throws Exception;

    ArrayList<Competition> getCompetitionsNameAndNumber() throws Exception;
}

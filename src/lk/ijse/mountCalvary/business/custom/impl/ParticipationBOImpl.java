package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.ParticipationBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.ParticipationDAO;
import lk.ijse.mountCalvary.dao.custom.QueryDAO;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.entity.CustomEntity;
import lk.ijse.mountCalvary.entity.Participation;
import lk.ijse.mountCalvary.model.ParticipationDTO;

import java.sql.Connection;
import java.util.ArrayList;

public final class ParticipationBOImpl implements ParticipationBO {
    private ParticipationDAO participationDAOImpl;
    private QueryDAO queryDAOImpl;
    private Connection conn;

    public ParticipationBOImpl() {
        participationDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PARTICIPATION);
        queryDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOM);
    }

    @Override
    public boolean addAllParticipation(ObservableList<ParticipationDTO> allParticipationDTOS) throws Exception {
        conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            for (ParticipationDTO participationDTO : allParticipationDTOS) {
                Participation participation = new Participation(participationDTO.getELID(),
                        participationDTO.getRID(),
                        participationDTO.getResult(),
                        participationDTO.getPerformance()
                );
                if (!participationDAOImpl.saveWithoutPKey(participation)) {
                    return false;
                }
            }
            conn.commit();
            return true;
        } finally {
            conn.rollback();
            conn.setAutoCommit(true);
        }
    }

    @Override
    public ObservableList<ParticipationDTO> getCompetitionAndAchievementOfThisStudent(int SID) throws Exception {
        ArrayList<CustomEntity> achievementDetails = queryDAOImpl.getCompetitionAndAchievementOfThisStudent(SID);
        ArrayList<ParticipationDTO> allParticipation = new ArrayList<>();
        for (CustomEntity oneAch : achievementDetails) {
            allParticipation.add(new ParticipationDTO(oneAch.getCID(),
                            oneAch.getcName(),
                            oneAch.getDate(),
                            oneAch.getAID(),
                            oneAch.getaName(),
                            oneAch.geteName(),
                            oneAch.getResult(),
                            oneAch.getPerformance()
                    )
            );
        }
        return FXCollections.observableArrayList(allParticipation);
    }

    @Override
    public ObservableList<ParticipationDTO> getParticipationForThisEventList(int ELID) throws Exception {
        ArrayList<Participation> participations = queryDAOImpl.getParticipationForThisEventList(ELID);

        ArrayList<ParticipationDTO> participationDTOS = new ArrayList<>();

        for (Participation oneParticipation : participations) {
            participationDTOS.add(new ParticipationDTO(oneParticipation));
        }

        return FXCollections.observableArrayList(participationDTOS);
    }

    @Override
    public ObservableList<ParticipationDTO> getParticipationForThisCompetition(int CID) throws Exception {
        ArrayList<Participation> participations = queryDAOImpl.getParticipationForThisCompetition(CID);

        ArrayList<ParticipationDTO> participationDTOS = new ArrayList<>();

        for (Participation oneParticipation : participations) {
            participationDTOS.add(new ParticipationDTO(oneParticipation));
        }

        return FXCollections.observableArrayList(participationDTOS);
    }

    @Override
    public ObservableList<ParticipationDTO> getParticipationForThisEventAndCompetition(int EID, int CID) throws Exception {
        ArrayList<CustomEntity> participationForThisEventAndCompetition = queryDAOImpl.getParticipationForThisEventAndCompetition(EID, CID);
        ArrayList<ParticipationDTO> participationDTOS = new ArrayList<>();
        for (CustomEntity oneParti : participationForThisEventAndCompetition) {
            participationDTOS.add(new ParticipationDTO(
                    oneParti.getPID(),
                    oneParti.getSID(),
                    oneParti.getRID(),
                    oneParti.getsName(),
                    oneParti.getResult(),
                    oneParti.getPerformance(),
                    oneParti.getGID(),
                    oneParti.getGroup_name(),
                    oneParti.getELID()
                    )
            );
        }
        return FXCollections.observableArrayList(participationDTOS);
    }
}

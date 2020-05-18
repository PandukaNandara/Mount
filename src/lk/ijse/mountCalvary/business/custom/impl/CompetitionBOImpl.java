package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.CompetitionBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.*;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.entity.Competition;
import lk.ijse.mountCalvary.entity.CustomEntity;
import lk.ijse.mountCalvary.entity.EventList;
import lk.ijse.mountCalvary.entity.TeacherInChargeList;
import lk.ijse.mountCalvary.model.*;

import java.sql.Connection;
import java.util.ArrayList;

public final class CompetitionBOImpl implements CompetitionBO {

    private CompetitionDAO competitionDAOImpl;
    private TeacherInChargeListDAO teacherInChargeListDAOImpl;
    private Connection conn;
    private ActivityBOImpl activityBOImpl;
    private EventBOImpl eventBOImpl;
    private EventListDAO eventListDAOImpl;
    private AgeGroupDAO ageGroupDAOImpl;
    private QueryDAO queryDAOImpl;

    public CompetitionBOImpl() {
        competitionDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.COMPETITION);
        teacherInChargeListDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TEACHER_IN_CHARGE_LIST);
        eventListDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EVENT_LIST);
        ageGroupDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.AGE_GROUP);
        queryDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOM);
        activityBOImpl = new ActivityBOImpl();
        eventBOImpl = new EventBOImpl();
    }

    @Override
    public boolean addCompetitionWithTeacherInCharge(CompetitionDTO comp) throws Exception {
        conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            if (!competitionDAOImpl.saveWithoutPKey(new Competition(
                    comp.getComName(),
                    comp.getLocation(),
                    comp.getDate(),
                    comp.getDesc())
            ))
                return false;
            int competitionID = competitionDAOImpl.getIncrementIndex();

            ObservableList<TeacherInChargeListDTO> teacherInChargeListDTOS = comp.getTeacherInChargeList();

            for (TeacherInChargeListDTO oneTeacherList : teacherInChargeListDTOS) {

                TeacherInChargeList teacherList = new TeacherInChargeList(competitionID, oneTeacherList.getTID());
                if (!teacherInChargeListDAOImpl.saveWithoutPKey(teacherList))
                    return false;

            }
            conn.commit();
            return true;
        } finally {
            conn.rollback();
            conn.setAutoCommit(true);
        }
    }

    //This method is used for getting all competition with event list. This is very old one.
    @Override
    public ObservableList<CompetitionDTO> getCompetitionWithEventList() throws Exception {
        ArrayList<CustomEntity> allCompetition = queryDAOImpl.getAllCompetitionWithEventList();

        ArrayList<CompetitionDTO> allCompetitionDTO = new ArrayList<>();
        for (CustomEntity oneCompetition : allCompetition) {
            Competition competition = oneCompetition.getCompetition();
            ArrayList<EventList> eventLists = oneCompetition.getEventLists();

            CompetitionDTO competitionDTO = new CompetitionDTO(competition.getCID(), competition.getComName(), competition.getLocation(), competition.getDate(), competition.getDesc());

            ArrayList<EventListDTO> eventListDTOS = new ArrayList<>();
            for (EventList oneEventList : eventLists) {
                EventListDTO eventListDTO = new EventListDTO(oneEventList);
                EventDTO eventDTO = new EventDTO(oneEventList.getEvent());
                eventDTO.setActivityName(oneEventList.getEvent().getaName());
                eventDTO.setAID(oneEventList.getEvent().getAID());
                eventListDTO.setEventDTO(eventDTO);
                eventListDTO.setAgeGroupDTO(new AgeGroupDTO(oneEventList.getAgeGroup()));
                eventListDTO.setCompetitionDTO(competitionDTO);
                eventListDTOS.add(eventListDTO);
            }
            competitionDTO.setEventListDTOS(FXCollections.observableArrayList(eventListDTOS));
            allCompetitionDTO.add(competitionDTO);

        }
        return FXCollections.observableArrayList(allCompetitionDTO);
    }

    @Override
    public ObservableList<CompetitionDTO> getAllCompetitionWithParticipation() throws Exception {
        ObservableList<CompetitionDTO> allCompetition = getCompetitionWithEventList();
        ArrayList<CustomEntity> allCompetitionWithParticipation = queryDAOImpl.getAllCompetitionWithParticipation();
        for (CompetitionDTO oneCompetitionDTO : allCompetition) {
            ArrayList<ParticipationDTO> allParticipation = new ArrayList<>();
            for (CustomEntity oneCompetitionWithParticipation : allCompetitionWithParticipation) {
                if (oneCompetitionDTO.getCID() == oneCompetitionWithParticipation.getCompetition().getCID()) {
                    for (EventListDTO oneEventListDTO : oneCompetitionDTO.getEventListDTOS()) {
                        for (EventList eventList : oneCompetitionWithParticipation.getEventLists()) {
                            if (oneEventListDTO.getELID() == eventList.getELID()) {
                                oneEventListDTO.setParticipationDTOS(eventList.getParticipations());
                                allParticipation.addAll(oneEventListDTO.getParticipationDTOS());
                            }
                        }
                    }
                }
            }
            oneCompetitionDTO.setParticipationDTOS(FXCollections.observableArrayList(allParticipation));
        }
        return allCompetition;
    }

    @Override
    public boolean deleteCompetition(int CID) throws Exception {
        conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            if (!competitionDAOImpl.delete(CID)) {
                return false;
            }
            conn.commit();
            return true;
        } finally {
            conn.rollback();
            conn.setAutoCommit(true);
        }
    }

    @Override
    public ObservableList<CompetitionDTO> getAllCompetition() throws Exception {
        ArrayList<Competition> competitions = competitionDAOImpl.getCompetitionsNameAndNumber();
        ArrayList<CompetitionDTO> competitionDTOS = new ArrayList<>();
        for (Competition competition : competitions) {
            competitionDTOS.add(new CompetitionDTO(competition.getCID(), competition.getComName()));
        }
        return FXCollections.observableArrayList(competitionDTOS);
    }

    @Override
    public CompetitionDTO getCompetitionDetail(int CID) throws Exception {
        Competition competition = competitionDAOImpl.search(CID);
        return new CompetitionDTO(competition.getCID(), competition.getComName(), competition.getLocation(), competition.getDate(), competition.getDesc());
    }
//    @Override
//    public ObservableList<CompetitionDTO> getAllCompetitionWithParticipation() throws Exception {
//        ArrayList<CustomEntity> allCompetition = queryDAOImpl.getAllCompetitionWithParticipation();
//        ArrayList<CompetitionDTO> allCompetitionDTO = new ArrayList<>();
//
//        for (CustomEntity oneCompetition : allCompetition) {
//
//            Competition competition = oneCompetition.getAllCompetition();
//
//            ArrayList<EventList> eventLists = oneCompetition.getEventLists();
//
//            ArrayList<ParticipationDTO> allPartcipation = new ArrayList<>();
//
//            CompetitionDTO competitionDTO = new CompetitionDTO(competition.getCID(), competition.getComName(), competition.getLocation(), competition.getDate(), competition.getDesc());
//
//            ArrayList<EventListDTO> eventListDTOS = new ArrayList<>();
//
//            for (EventList oneEventList : eventLists) {
//                System.out.println("AA   " + oneEventList);
//                EventListDTO eventListDTO = new EventListDTO(oneEventList);
//
//                EventDTO eventDTO = new EventDTO(oneEventList.getEvent());
//                eventDTO.setActivityName(oneEventList.getEvent().getaName());
//                eventListDTO.setEventDTO(eventDTO);
//
//                eventListDTO.setAgeGroupDTO(new AgeGroupDTO(oneEventList.getAgeGroup()));
//
//                eventListDTO.setCompetitionDTO(competitionDTO);
//
//                eventListDTOS.add(eventListDTO);
//                //Add Participation to competition
//                System.out.println(eventListDTO);
//
//                System.out.println("YY " + eventListDTO.getParticipationDTOS());
//                allPartcipation.addAll(eventListDTO.getParticipationDTOS());
//            }
//            competitionDTO.setEventListDTOS(FXCollections.observableArrayList(eventListDTOS));
//            System.out.println("XX " + allPartcipation);
//            competitionDTO.setParticipationDTOS(FXCollections.observableArrayList(allPartcipation));
//            System.out.println(competitionDTO.getParticipationDTOS());
//            allCompetitionDTO.add(competitionDTO);
//
//        }
//        return FXCollections.observableArrayList(allCompetitionDTO);
//    }
//    @Override
//    public ObservableList<CompetitionDTO> getCompetitionWithEventList() throws Exception {
//        ArrayList<Competition> allCompetition = competitionDAOImpl.getAll();
//        ArrayList<EventList> allEventList = eventListDAOImpl.getAll();
//        ArrayList<CompetitionDTO> allCompetitionDTO = new ArrayList<>();
//        ObservableList<EventDTO> allEventDTO = eventBOImpl.getAllEventWithActivity();
//        ArrayList<AgeGroup> allAgeGroups = ageGroupDAOImpl.getAll();
//        for(Competition oneCompetition : allCompetition){
//       //     System.out.println(oneCompetition);
//            CompetitionDTO oneCompetitionDTO = new CompetitionDTO(oneCompetition.getCID(), oneCompetition.getComName(), oneCompetition.getLocation(), oneCompetition.getDate(), oneCompetition.getDesc());
//           // System.out.println(oneCompetitionDTO);
//            ArrayList<EventListDTO> eventListDTOS = new ArrayList<>();
//            for(EventList oneEventList : allEventList){
//             //   System.out.println(oneEventList);
//                if(oneCompetitionDTO.getCID() == oneEventList.getCID()){
//
//                    for(EventDTO oneEventDTO : allEventDTO){
//                    //    System.out.println(oneEventDTO);
//                        if(oneEventDTO.getEID() == oneEventList.getEID()){
//
//                            for(AgeGroup oneAgeGroup : allAgeGroups){
//                              //  System.out.println(oneAgeGroup);
//
//                                if(oneAgeGroup.getGID() == oneEventList.getGID()){
//
//                                    AgeGroupDTO ageGroupDTO = new AgeGroupDTO(oneAgeGroup.getGID(), oneAgeGroup.getGroupName(), oneAgeGroup.getMax(), oneAgeGroup.getMin());
//                                    eventListDTOS.add(new EventListDTO(oneEventList.getELID(), oneEventDTO, oneCompetitionDTO, ageGroupDTO));
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        //    System.out.println(eventListDTOS);
//            oneCompetitionDTO.setEventListDTOS(FXCollections.observableArrayList(eventListDTOS));
//            allCompetitionDTO.add(oneCompetitionDTO);
//        }
//        return FXCollections.observableArrayList(allCompetitionDTO);
//    }
}


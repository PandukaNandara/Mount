package lk.ijse.mountCalvary.dao.custom.impl;

import lk.ijse.mountCalvary.dao.CrudUtil;
import lk.ijse.mountCalvary.dao.custom.QueryDAO;
import lk.ijse.mountCalvary.entity.*;

import java.sql.ResultSet;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    private CompetitionDAOImpl competitionDAO;

    public QueryDAOImpl() {
        competitionDAO = new CompetitionDAOImpl();
    }

    @Override
    public ArrayList<CustomEntity> getAllStudentNotDoThisActivity(int AID) throws Exception {
        ArrayList<CustomEntity> allStudent = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select SID, sName\n" +
                "from student s\n" +
                "where SID not in(select distinct s.SID\n" +
                "from student s, registration r\n" +
                "where s.SID = r.SID and AID = ?)" +
                "and s.quit = false", AID);
        while (rst.next()) {
            System.out.println(rst.getInt("SID") + "  " +
                    rst.getString("sName"));
            allStudent.add(new CustomEntity(new Student(
                            rst.getInt("SID"),
                            rst.getString("sName")
                    )
                    )
            );
        }
        return allStudent;
    }

    @Override
    public ArrayList<CustomEntity> getRegistrationOfThisActivity(int AID) throws Exception {
        ArrayList<CustomEntity> customEntities = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select RID, s.SID, sName, s.gender, DOB, joined_date, class\n" +
                "from registration r, student s\n" +
                "where AID = ? and r.SID = s.SID order by class", AID);
        while (rst.next()) {
            CustomEntity customEntity = new CustomEntity(
                    rst.getInt("RID"),
                    rst.getInt("SID"),
                    rst.getString("sName"),
                    rst.getBoolean("gender"),
                    rst.getDate("DOB")
            );
            customEntity.setJoinedDate(rst.getDate("joined_date"));
            customEntity.setStudentClass(rst.getString("class"));
            customEntities.add(customEntity);
        }
        return customEntities;
    }

    @Override
    public ArrayList<CustomEntity> getAllCompetitionWithEventList() throws Exception {

        ArrayList<CustomEntity> customEntities = new ArrayList<>();
        //get all competition
        ArrayList<Competition> allCompetition = competitionDAO.getAll();
        for (Competition oneCompetition : allCompetition) {
            CustomEntity customEntity = new CustomEntity();
            customEntity.setCompetition(oneCompetition);
            ArrayList<EventList> eventLists = new ArrayList<>();

            //get participation according to ELID

            ResultSet rst = CrudUtil.executeQuery("select distinct ELID," +
                    " CID, el.EID," +
                    " eName, e.gender," +
                    " e.AID, aName," +
                    " el.GID, " +
                    "group_name, " +
                    "min, " +
                    "max\n" +
                    "from event e,age_group ag, event_list el, activity a\n" +
                    "where (e.EID = el.EID and ag.GID = el.GID) and CID = ? group by ELID;", oneCompetition.getCID());
            while (rst.next()) {
                EventList eventList = new EventList(
                        rst.getInt("ELID"),
                        rst.getInt("CID"),
                        rst.getInt("EID"),
                        rst.getInt("GID")
                );
                eventList.setEvent(new Event(
                                rst.getInt("EID"),
                                rst.getString("eName"),
                        rst.getInt("gender"),
                                rst.getInt("AID"),
                                rst.getString("aName")
                        )
                );
                eventList.setAgeGroup(new AgeGroup(
                                rst.getInt("GID"),
                                rst.getString("group_name"),
                                rst.getInt("min"),
                                rst.getInt("max")
                        )
                );
                eventLists.add(eventList);
            }

            customEntity.setEventLists(eventLists);
            customEntities.add(customEntity);
        }

        return customEntities;
    }

    @Override
    public ArrayList<EventList> getEventListForThisCompetition(int CID) throws Exception {
        //get all competition
        ArrayList<EventList> eventLists = new ArrayList<>();
        //get participation according to ELID
        ResultSet rst = CrudUtil.executeQuery("select " +
                "distinct ELID," +
                " CID, el.EID," +
                " eName, e.gender, " +
                "e.AID," +
                " aName," +
                " el.GID," +
                " group_name," +
                " min," +
                " max," +
                " CID\n" +
                "from event e, age_group ag, event_list el, activity a\n" +
                "where (e.EID = el.EID and ag.GID = el.GID and e.AID = a.AID)  and CID = ?  group by ELID;", CID);
        while (rst.next()) {
            EventList eventList = new EventList(
                    rst.getInt("ELID"),
                    rst.getInt("CID"),
                    rst.getInt("EID"),
                    rst.getInt("GID")
            );
            eventList.setEvent(new Event(
                            rst.getInt("EID"),
                            rst.getString("eName"),
                    rst.getInt("gender"),
                            rst.getInt("AID"),
                            rst.getString("aName")
                    )
            );

            eventList.setAgeGroup(new AgeGroup(
                            rst.getInt("GID"),
                            rst.getString("group_name"),
                            rst.getInt("min"),
                            rst.getInt("max")
                    )
            );
            eventLists.add(eventList);
        }
        return eventLists;
    }

    @Override
    public ArrayList<CustomEntity> getAllCompetitionWithParticipation() throws Exception {
        ArrayList<CustomEntity> allCompetitionWithEvent = getAllCompetitionWithEventList();
        for (CustomEntity oneCompetition : allCompetitionWithEvent) {
            ArrayList<EventList> eventLists = oneCompetition.getEventLists();
            for (EventList oneEventList : eventLists) {
                int ELID = oneEventList.getELID();

                ResultSet rst = CrudUtil.executeQuery("select PID, r.RID, el.ELID, result, performance, s.SID, sName, DOB, e.AID, e.eName, a.aName, e.gender, el.EID\n" +
                        "from participation p, event_list el ,student s, registration r, Event e, activity a\n" +
                        "where (p.RID = r.RID and\n" +
                        "       s.SID = r.SID and\n" +
                        "       r.AID = e.AID and\n" +
                        "       e.AID = a.AID and\n" +
                        "       e.EID = el.EID and\n" +
                        "      p.ELID = el.ELID\n" +
                        ") and p.ELID = ?", ELID);

                ArrayList<Participation> participations = new ArrayList<>();
                while (rst.next()) {
                    participations.add(new Participation(
                            rst.getInt("PID"),
                            rst.getInt("ELID"),
                            rst.getInt("RID"),
                            rst.getString("result"),
                            rst.getString("performance") == null || rst.getString("performance").length() < 2 ? " - " : rst.getString("performance"),
                            rst.getInt("SID"),
                            rst.getString("sName"),
                            rst.getDate("DOB"),
                            rst.getInt("AID"),
                            String.format("%s - %s - %s",
                                    rst.getString("eName"),
                                    rst.getString("aName"),
                                    rst.getInt("gender") == EventInterface.MALE ?
                                            "Male" : rst.getInt("gender") == EventInterface.FEMALE ?
                                            "Female" : "Mixed")
                    ));

                }
                oneEventList.setParticipations(participations);
            }
        }
        return allCompetitionWithEvent;
    }

    @Override
    public ArrayList<Participation> getParticipationForThisEventList(int ELID) throws Exception {

        ArrayList<Participation> participations = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select PID, " +
                "r.RID, " +
                "el.ELID," +
                "result, " +
                "performance, " +
                "s.SID," +
                " sName," +
                " DOB, " +
                "e.AID," +
                " e.eName," +
                " a.aName," +
                " e.gender," +
                " el.EID\n" +
                "from participation p, event_list el ,student s, registration r, Event e, activity a\n" +
                "where (p.RID = r.RID and\n" +
                "       s.SID = r.SID and\n" +
                "       r.AID = e.AID and\n" +
                "       e.AID = a.AID and\n" +
                "       e.EID = el.EID and\n" +
                "      p.ELID = el.ELID\n" +
                ") and p.ELID = ?", ELID);

        while (rst.next()) {
            participations.add(new Participation(
                    rst.getInt("PID"),
                    rst.getInt("ELID"),
                    rst.getInt("RID"),
                    rst.getString("result"),
                    rst.getString("performance") == null || rst.getString("performance").length() < 2 ? " - " : rst.getString("performance"),
                    rst.getInt("SID"),
                    rst.getString("sName"),
                    rst.getDate("DOB"),
                    rst.getInt("AID"),
                    String.format("%s - %s - %s",
                            rst.getString("eName"),
                            rst.getString("aName"),
                            rst.getInt("gender") == EventInterface.MALE ?
                                    "Male" : rst.getInt("gender") == EventInterface.FEMALE ?
                                    "Female" : "Mixed")
            ));
        }
        return participations;
    }

    @Override
    public ArrayList<Participation> getParticipationForThisCompetition(int CID) throws Exception {

        ArrayList<Participation> participations = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select\n" +
                "  PID,\n" +
                "  r.RID,\n" +
                "  el.ELID,\n" +
                "  result,\n" +
                "  performance,\n" +
                "  s.SID,\n" +
                "  sName,\n" +
                "  DOB,\n" +
                "  e.AID,\n" +
                "  e.eName,\n" +
                "  a.aName,\n" +
                "  e.gender,\n" +
                "  el.EID,\n" +
                "  c.CID,\n" +
                "  group_name\n" +
                "from participation p, event_list el, student s, registration r, Event e, activity a, competition c, age_group ag\n" +
                "where (p.RID = r.RID and\n" +
                "       s.SID = r.SID and\n" +
                "       r.AID = e.AID and\n" +
                "       e.AID = a.AID and\n" +
                "       e.EID = el.EID and\n" +
                "       p.ELID = el.ELID and\n" +
                "       el.CID = c.CID and\n" +
                "       el.GID = ag.GID\n" +
                "      ) and c.CID = ?", CID);

        while (rst.next()) {
            participations.add(new Participation(
                    rst.getInt("PID"),
                    rst.getInt("ELID"),
                    rst.getInt("RID"),
                    rst.getString("result"),
                    rst.getString("performance") == null || rst.getString("performance").length() < 2 ? " - " : rst.getString("performance"),
                    rst.getInt("SID"),
                    rst.getString("sName"),
                    rst.getDate("DOB"),
                    rst.getInt("AID"),
                    String.format("%s - %s - %s",
                            rst.getString("eName"),
                            rst.getString("aName"),
                            rst.getInt("gender") == EventInterface.MALE ?
                                    "Male" : rst.getInt("gender") == EventInterface.FEMALE ?
                                    "Female" : "Mixed")
            ));
        }
        return participations;
    }

    @Override
    public ArrayList<CustomEntity> getRegistrationForThisStudent(int SID) throws Exception {
        ArrayList<CustomEntity> registration = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select" +
                " RID," +
                " SID, " +
                "a.AID," +
                "joined_date," +
                "aName\n" +
                "from registration r, activity a\n" +
                "where (r.AID = a.AID) and SID = ?;", SID);
        while (rst.next()) {
            registration.add(new CustomEntity(
                    rst.getInt("RID"),
                    rst.getInt("SID"),
                    rst.getInt("AID"),
                    rst.getDate("joined_date"),
                    rst.getString("aName")
            ));
        }
        return registration;
    }

    @Override
    public ArrayList<CustomEntity> getAttendanceSheetForThisStudent(int SID) throws Exception {
        ArrayList<CustomEntity> attendance = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select " +
                "at.ATID, " +
                "r.RID, " +
                "date, " +
                "a.AID, " +
                "aName," +
                "t.TID, " +
                "t.tName\n" +
                "from attendant_sheet at, registration r, teacher t, student s, activity a\n" +
                "where (r.RID = at.RID and\n" +
                "  at.TID = t.TID and\n" +
                "  s.SID = r.SID and\n" +
                "  a.AID = r.AID) and s.SID = ?", SID);
        while (rst.next()) {
            attendance.add(new CustomEntity(
                    rst.getInt("ATID"),
                    rst.getInt("RID"),
                    rst.getDate("date"),
                    rst.getInt("AID"),
                    rst.getString("aName"),
                    rst.getInt("TID"),
                    rst.getString("tName")
            ));
        }
        return attendance;
    }

    @Override
    public ArrayList<CustomEntity> getActivityListForThisStudent(int SID) throws Exception {
        ArrayList<CustomEntity> activityList = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select s.SID, " +
                "r.RID," +
                "a.AID, " +
                "aName\n" +
                "from registration r, student s, activity a\n" +
                "where (s.SID = r.SID and\n" +
                "       a.AID = r.AID) and s.SID = ?", SID);
        while (rst.next()) {
            activityList.add(new CustomEntity(
                    rst.getInt("SID"),
                    rst.getInt("RID"),
                    rst.getInt("AID"),
                    rst.getString("aName"))
            );
        }
        return activityList;
    }

    @Override
    public ArrayList<CustomEntity> getCompetitionAndAchievementOfThisStudent(int SID) throws Exception {
        ArrayList<CustomEntity> participationList = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select c.CID, cName, date, a.AID, aName, eName, result, performance\n" +
                "from competition c, event_list el, event e, participation p, registration r, student s, activity a\n" +
                "where s.SID = ? and (a.AID = e.AID and\n" +
                "  s.SID = r.SID and\n" +
                "  e.EID = el.EID and\n" +
                "  c.CID = el.CID and\n" +
                "  p.RID = r.RID and\n" +
                "  p.ELID = el.ELID and\n" +
                "  a.AID = r.AID);", SID);
        while (rst.next()) {
            participationList.add(new CustomEntity(
                            rst.getInt("CID"),
                            rst.getString("cName"),
                            rst.getDate("date"),
                            rst.getInt("AID"),
                            rst.getString("aName"),
                            rst.getString("eName"),
                            rst.getString("result"),
                            rst.getString("performance") == null || rst.getString("performance").length() < 2 ? " - " : rst.getString("performance")
                    )
            );
        }
        return participationList;
    }

    @Override
    public ArrayList<CustomEntity> getAllAttendanceForThisActivity(int AID) throws Exception {
        ArrayList<CustomEntity> attendaceList = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select\n" +
                "  at.ATID,\n" +
                "  r.RID,\n" +
                "  s.SID,\n" +
                "  s.sName,\n" +
                "  date,\n" +
                "  t.TID,\n" +
                "  t.tName\n" +
                "from attendant_sheet at, registration r, teacher t, student s, activity a\n" +
                "where a.AID = ? and (r.RID = at.RID and\n" +
                "       at.TID = t.TID and\n" +
                "       s.SID = r.SID and\n" +
                "       a.AID = r.AID)" +
                "order by date DESC ", AID);
        while (rst.next()) {
            attendaceList.add(new CustomEntity(
                            rst.getInt("ATID"),
                            rst.getInt("RID"),
                            rst.getInt("SID"),
                            rst.getString("sName"),
                            rst.getDate("date"),
                            rst.getInt("TID"),
                            rst.getString("tName")
                    )
            );
        }
        return attendaceList;
    }

    @Override
    public ArrayList<CustomEntity> getPaymentOfThisStudent(int sid) throws Exception {
        ArrayList<CustomEntity> paymentList = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select\n" +
                "  PAYID,\n" +
                "  r.RID,\n" +
                "  a.AID,\n" +
                "  aName,\n" +
                "  fee,\n" +
                "  month,\n" +
                "  year\n" +
                "from activity a, registration r, payment py\n" +
                "where SID = ? and\n" +
                "      (r.AID = a.AID and\n" +
                "       r.RID = py.RID)\n", sid);
        while (rst.next()) {
            paymentList.add(new CustomEntity(
                            rst.getInt("PAYID"),
                            rst.getInt("RID"),
                            rst.getBigDecimal("fee"),
                            rst.getInt("month"),
                            rst.getInt("year"),
                            rst.getInt("AID"),
                            rst.getString("aName")
                    )
            );
        }
        return paymentList;
    }

    @Override
    public ArrayList<CustomEntity> getPaymentOfThisActivity(int AID) throws Exception {
        ArrayList<CustomEntity> paymentList = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select\n" +
                "  PAYID,\n" +
                "  r.RID,\n" +
                "  s.SID,\n" +
                "  sName,\n" +
                "  fee,\n" +
                "  month,\n" +
                "  year\n" +
                "from student s, registration r, payment py\n" +
                "where\n" +
                "  (r.SID = s.SID and\n" +
                "   r.RID = py.RID) and AID = ?\n" +
                "order by PAYID DESC", AID);
        while (rst.next()) {
            paymentList.add(new CustomEntity(
                            rst.getInt("PAYID"),
                            rst.getInt("RID"),
                            rst.getInt("SID"),
                            rst.getString("sName"),
                            rst.getBigDecimal("fee"),
                            rst.getInt("month"),
                            rst.getInt("year")
                    )
            );
        }
        return paymentList;
    }

    @Override
    public ArrayList<CustomEntity> getCompetitionForThisEvent(int EID) throws Exception {
        ArrayList<CustomEntity> competitionList = new ArrayList<>();

        ResultSet rst = CrudUtil.executeQuery("select\n" +
                "  c.CID,\n" +
                "  cName,\n" +
                "  c.date\n" +
                "from event e, event_list el, competition c\n" +
                "where (e.EID = el.EID and\n" +
                "       c.CID = el.CID) and\n" +
                "      e.EID = ?\n" +
                "group by c.CID\n" +
                "order by c.CID", EID);

        while (rst.next()) {
            competitionList.add(new CustomEntity(
                            rst.getInt("CID"),
                            rst.getString("cName"),
                            rst.getDate("date")
                    )
            );
        }
        return competitionList;
    }

    @Override
    public ArrayList<CustomEntity> getParticipationForThisEventAndCompetition(int EID, int CID) throws Exception {
        ArrayList<CustomEntity> participationList = new ArrayList<>();


        ResultSet rst = CrudUtil.executeQuery("select\n" +
                "  PID,\n" +
                "  s.SID,\n" +
                "  r.RID,\n" +
                "  sName,\n" +
                "  result,\n" +
                "  performance,\n" +
                "  ag.GID,\n" +
                "  ag.group_name,\n" +
                "  el.ELID\n" +
                "from age_group ag, participation p, registration r, student s, event_list el\n" +
                "where (el.ELID = p.ELID and\n" +
                "      p.RID = r.RID and\n" +
                "      ag.GID = el.GID and\n" +
                "      s.SID = r.SID) and EID = ? and CID = ?", EID, CID);

        while (rst.next()) {
            participationList.add(new CustomEntity(
                            rst.getInt("PID"),
                            rst.getInt("SID"),
                            rst.getInt("RID"),
                            rst.getString("sName"),
                            rst.getString("result"),
                            rst.getString("performance") == null || rst.getString("performance").length() < 2 ? " - " : rst.getString("performance"),
                            rst.getInt("GID"),
                            rst.getString("group_name"),
                            rst.getInt("ELID")
                    )
            );
        }
        return participationList;
    }

    @Override
    public ArrayList<CustomEntity> getTeacherInChargeListForThisCompetition(int cid) throws Exception {
        ArrayList<CustomEntity> allTeacherInCharge = new ArrayList<>();

        ResultSet rst = CrudUtil.executeQuery("select\n" +
                "  TINCID,\n" +
                "  t.TID,\n" +
                "  tName\n" +
                "from teacher_in_charge_list ticl, teacher t\n" +
                "where (ticl.TID = t.TID) and CID = ?", cid);

        while (rst.next()) {
            CustomEntity teacher = new CustomEntity();
            teacher.setTINCID(rst.getInt("TINCID"));
            teacher.setTID(rst.getInt("TID"));
            teacher.settName(rst.getString("tName"));
            allTeacherInCharge.add(teacher);
        }
        return allTeacherInCharge;
    }

    @Override
    public ArrayList<CustomEntity> getPaymentDetailForThisMonthAndYearAndActivity(int aid, int year, int month) throws Exception {
        ArrayList<CustomEntity> paymentDetail = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select\n" +
                "  PAYID,\n" +
                "  r.RID,\n" +
                "  s.SID,\n" +
                "  sName,\n" +
                "  fee,\n" +
                "  month,\n" +
                "  year\n" +
                "from student s, registration r, payment py\n" +
                "where \n" +
                "      (r.SID = s.SID and\n" +
                "       r.RID = py.RID) and \n" +
                "AID = ? and " +
                "month = ? and " +
                "year = ?", aid, month, year);
        while (rst.next()) {
            paymentDetail.add(new CustomEntity(
                            rst.getInt("PAYID"),
                            rst.getInt("RID"),
                            rst.getInt("SID"),
                            rst.getString("sName"),
                            rst.getBigDecimal("fee"),
                            rst.getInt("month"),
                            rst.getInt("year")
                    )
            );
        }
        return paymentDetail;
    }

    @Override
    public ArrayList<CustomEntity> getMaximumDistinctPaymentDataForThisActivity(int aid, Integer year, int month) throws Exception {
        ArrayList<CustomEntity> distinctPayment = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select\n" +
                "  PAYID,\n" +
                "  r.RID,\n" +
                "  s.SID,\n" +
                "  sName,\n" +
                "  fee,\n" +
                "  max(month),\n" +
                "  year\n" +
                "from student s, registration r, payment py\n" +
                "where\n" +
                "  (r.SID = s.SID and\n" +
                "   r.RID = py.RID) and \n" +
                "  AID = ? and \n" +
                "  Year = ? and\n" +
                "  month < ?\n" +
                "  group by RID\n" +
                "order by PAYID DESC", aid, month, year);
        while (rst.next()) {
            distinctPayment.add(new CustomEntity(
                            rst.getInt("PAYID"),
                            rst.getInt("RID"),
                            rst.getInt("SID"),
                            rst.getString("sName"),
                            rst.getBigDecimal("fee"),
                            rst.getInt("month"),
                            rst.getInt("year")
                    )
            );
        }
        return distinctPayment;
    }

    @Override
    public ArrayList<CustomEntity> getPhysicalTestForThisClassAndTermWithClassStudent(int term_id, String class_) throws Exception {
        ArrayList<CustomEntity> allStudentWithMarks = new ArrayList<>();
        //Get student whom does't enter into the PhysicalTest table.
        ResultSet firstRst = CrudUtil.executeQuery(
                "select\n" +
                        "  s.SID,\n" +
                        "  s.sName,\n" +
                        "  s.class\n" +
                        "from student s\n" +
                        "where s.class = ? and SID not in (select distinct SID\n" +
                        "                                      from physical_test\n" +
                        "                                      where TERM_ID = ?)",
                class_, term_id
        );
        while (firstRst.next()) {
            allStudentWithMarks.add(new CustomEntity(
                    firstRst.getInt("SID"),
                    firstRst.getString("sName"),
                    firstRst.getString("class"),
                    new PhysicalTest(true, term_id))); // Because this is not in the Physical Test
        }
        ResultSet secondRst = CrudUtil.executeQuery(
                "select\n" +
                        "  pt.PTID,\n" +
                        "  s.SID,\n" +
                        "  s.sName,\n" +
                        "  TERM_ID,\n" +
                        "  s.class,\n" +
                        "  attendance,\n" +
                        "  skill,\n" +
                        "  progress_effort,\n" +
                        "  attitudes,\n" +
                        "  performance,\n" +
                        "  total\n" +
                        "\n" +
                        "from student s, physical_test pt\n" +
                        "where (s.SID = pt.SID)\n" +
                        "      and s.class = ?\n" +
                        "      and TERM_ID = ?;",
                class_, term_id
        );
        while (secondRst.next()) {
            allStudentWithMarks.add(new CustomEntity(
                    secondRst.getInt("SID"),
                    secondRst.getString("sName"),
                    secondRst.getString("class"),
                    new PhysicalTest(
                            secondRst.getInt("PTID"),
                            secondRst.getInt("SID"),
                            secondRst.getInt("TERM_ID"),
                            secondRst.getString("class"),
                            secondRst.getInt("attendance"),
                            secondRst.getInt("skill"),
                            secondRst.getInt("progress_effort"),
                            secondRst.getInt("attitudes"),
                            secondRst.getInt("performance"),
                            secondRst.getInt("total")
                    ))); // Because this in the Physical Test
        }
        return allStudentWithMarks;
    }

    @Override
    public ArrayList<CustomEntity> getPhysicalTestForThisTerm(int term_id) throws Exception {
        ArrayList<CustomEntity> allStudentWithMarks = new ArrayList<>();
        //Get student whom does't enter into the PhysicalTest table.
        ResultSet rst = CrudUtil.executeQuery(
                "select\n" +
                        "  pt.PTID,\n" +
                        "  s.SID,\n" +
                        "  s.sName,\n" +
                        "  TERM_ID,\n" +
                        "  pt.class,\n" +
                        "  attendance,\n" +
                        "  skill,\n" +
                        "  progress_effort,\n" +
                        "  attitudes,\n" +
                        "  performance,\n" +
                        "  total\n" +
                        "from student s, physical_test pt\n" +
                        "where (s.SID = pt.SID)\n" +
                        "      and TERM_ID = ? \n" +
                        "order by s.class asc",
                term_id
        );
        while (rst.next()) {
            allStudentWithMarks.add(new CustomEntity(
                    rst.getInt("SID"),
                    rst.getString("sName"),
                    rst.getString("class"),
                    new PhysicalTest(
                            rst.getInt("PTID"),
                            rst.getInt("SID"),
                            rst.getInt("TERM_ID"),
                            rst.getString("class"),
                            rst.getInt("attendance"),
                            rst.getInt("skill"),
                            rst.getInt("progress_effort"),
                            rst.getInt("attitudes"),
                            rst.getInt("performance"),
                            rst.getInt("total")
                    )));
        }
        return allStudentWithMarks;
    }

    @Override
    public ArrayList<CustomEntity> getPhysicalTestForThisStudent(int sid) throws Exception {
        ArrayList<CustomEntity> studentMarks = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery(
                "select\n" +
                        "  pt.PTID,\n" +
                        "  SID,\n" +
                        "  t.TERM_ID,\n" +
                        "  t.term_name,\n" +
                        "  t.term_no,\n" +
                        "  t.year,\n" +
                        "  class,\n" +
                        "  attendance,\n" +
                        "  skill,\n" +
                        "  progress_effort,\n" +
                        "  attitudes,\n" +
                        "  performance,\n" +
                        "  total\n" +
                        "from term t, physical_test pt\n" +
                        "where (t.TERM_ID = pt.TERM_ID)\n" +
                        "      and SID = ? \n" +
                        "order by class asc",
                sid
        );
        while (rst.next()) {
            studentMarks.add(new CustomEntity(
                    new Term(rst.getInt("TERM_ID"),
                            rst.getString("term_name"),
                            rst.getInt("year"),
                            rst.getInt("term_no")),

                    rst.getString("class"),

                    new PhysicalTest(
                            rst.getInt("PTID"),
                            rst.getInt("SID"),
                            rst.getInt("TERM_ID"),
                            rst.getString("class"),
                            rst.getInt("attendance"),
                            rst.getInt("skill"),
                            rst.getInt("progress_effort"),
                            rst.getInt("attitudes"),
                            rst.getInt("performance"),
                            rst.getInt("total")
                    )));
        }
        return studentMarks;
    }

    @Override
    public ArrayList<Student> getStudentWhoIsNotInContributionListOfThisCompetition(int CID) throws Exception {
        ArrayList<Student> studentList = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery(
                "select\n" +
                        "  SID,\n" +
                        "  sName\n" +
                        "from student s\n" +
                        "where SID not in (select distinct (SID)\n" +
                        "                  from comp_contribution\n" +
                        "                  where CID = ?)",
                CID
        );
        while (rst.next()) {
            studentList.add(new Student(
                    rst.getInt("SID"),
                    rst.getString("sName")
            ));
        }
        return studentList;
    }

    @Override
    public ArrayList<CustomEntity> getContributionForThisCompetition(int cid) throws Exception {
        ArrayList<CustomEntity> studentList = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery(
                "select\n" +
                        "  CCID,\n" +
                        "  s.SID,\n" +
                        "  CID,\n" +
                        "  s.sName,\n" +
                        "  cc.contribution\n" +
                        "from student s, comp_contribution cc\n" +
                        "where s.SID = cc.SID and CID = ?",
                cid
        );
        while (rst.next()) {
            studentList.add(new CustomEntity(
                    rst.getInt("CCID"),
                    rst.getInt("SID"),
                    rst.getString("sName"),
                    rst.getInt("CCID"),
                    rst.getString("contribution")
            ));
        }
        return studentList;
    }

    @Override
    public ArrayList<CustomEntity> getContributionForThisStudent(int sid) throws Exception {
        ArrayList<CustomEntity> studentList = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery(
                "select\n" +
                        "  CCID,\n" +
                        "  SID,\n" +
                        "  c.CID,\n" +
                        "  c.cName,\n" +
                        "  cc.contribution\n" +
                        "from comp_contribution cc, competition c\n" +
                        "where c.CID = cc.CID and SID = ?",
                sid
        );
        while (rst.next()) {
            studentList.add(new CustomEntity(
                    rst.getInt("CCID"),
                    rst.getInt("SID"),
                    rst.getInt("CID"),
                    rst.getString("cName"),
                    rst.getString("contribution")
            ));
        }
        return studentList;

    }
}

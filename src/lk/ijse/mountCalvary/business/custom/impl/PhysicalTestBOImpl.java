package lk.ijse.mountCalvary.business.custom.impl;

import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.PhysicalTestBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.PhysicalTestDAO;
import lk.ijse.mountCalvary.dao.custom.QueryDAO;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.entity.CustomEntity;
import lk.ijse.mountCalvary.entity.PhysicalTest;
import lk.ijse.mountCalvary.entity.Term;
import lk.ijse.mountCalvary.model.PhysicalTestDTO;
import lk.ijse.mountCalvary.model.TermDTO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 7/21/2018
 * Time: 5:49 AM
 */

public final class PhysicalTestBOImpl implements PhysicalTestBO {

    private QueryDAO queryDAOImpl;
    private PhysicalTestDAO physicalTestDAOImpl;
    private Connection conn;

    public PhysicalTestBOImpl() {
        queryDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOM);
        physicalTestDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PHYSICAL_TEST);
    }

    @Override
    public boolean addNewPhysicalTest(PhysicalTestDTO dto) throws Exception {
        conn = DBConnection.getInstance().getConnection();
        conn.setAutoCommit(false);
        try {
            boolean add = physicalTestDAOImpl.saveWithoutPKey(new PhysicalTest(dto.getSID(), dto.getTERM_ID(), dto.getClass_(),
                    dto.getAttendance(), dto.getSkill(), dto.getProgressEffort(), dto.getAttitudes(), dto.getPerformance(),
                    dto.getTotal()
            ));
            if (add) {
                dto.setPTID(physicalTestDAOImpl.getIncrementIndex());
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public boolean updatePhysicalTest(PhysicalTestDTO dto) throws Exception {
        conn = DBConnection.getInstance().getConnection();
        conn.setAutoCommit(false);
        try {
            boolean update = physicalTestDAOImpl.update(new PhysicalTest(dto.getPTID(), dto.getSID(), dto.getTERM_ID(), dto.getClass_(),
                    dto.getAttendance(), dto.getSkill(), dto.getProgressEffort(), dto.getAttitudes(), dto.getPerformance(),
                    dto.getTotal()
            ));
            if (update) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public ObservableList<PhysicalTestDTO> getPhysicalTestForThisClassAndTerm(int TERM_ID, String class_) throws Exception {
        ArrayList<CustomEntity> allStudentWithMarks = queryDAOImpl
                .getPhysicalTestForThisClassAndTermWithClassStudent(TERM_ID, class_);
        ArrayList<PhysicalTestDTO> physicalTestDTOS = new ArrayList<>();

        for (CustomEntity one : allStudentWithMarks) {

            PhysicalTest physicalTest = one.getPhysicalTest();
            PhysicalTestDTO testDTO = new PhysicalTestDTO(
                    physicalTest.getPTID(),
                    one.getSID(),
                    one.getsName(),
                    physicalTest.getTERM_ID(),
                    one.getClass_(),
                    physicalTest.getAttendance(),
                    physicalTest.getSkill(),
                    physicalTest.getProgressEffort(),
                    physicalTest.getAttitudes(),
                    physicalTest.getPerformance(),
                    physicalTest.getTotal(),
                    physicalTest.isNewOne()
            );

            testDTO.totalProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    updateOrSave(testDTO);
                } catch (Exception e) {
                    Logger.getLogger(PhysicalTestBOImpl.class.getName()).log(Level.SEVERE, null, e);
                }
            });

            IntegerBinding totally = new IntegerBinding() {
                {
                    super.bind(
                            testDTO.attendanceProperty(),
                            testDTO.skillProperty(),
                            testDTO.progressEffortProperty(),
                            testDTO.attitudesProperty(),
                            testDTO.performanceProperty()
                    );
                }

                @Override
                protected int computeValue() {
                    return testDTO.attendanceProperty().get() +
                            testDTO.skillProperty().get() +
                            testDTO.progressEffortProperty().get() +
                            testDTO.attitudesProperty().get() +
                            testDTO.performanceProperty().get();
                }
            };

            testDTO.totalProperty().bind(totally);


            physicalTestDTOS.add(testDTO);
        }

        return FXCollections.observableArrayList(physicalTestDTOS);
    }

    private boolean updateOrSave(PhysicalTestDTO physicalTestDTO) throws Exception {
        if (physicalTestDTO.isNewOne()) {
            physicalTestDTO.setNewOne(false);
            return addNewPhysicalTest(physicalTestDTO);
        } else {
            return updatePhysicalTest(physicalTestDTO);
        }
    }

    @Override
    public ObservableList<PhysicalTestDTO> getPhysicalTestForThisTerm(int TERM_ID) throws Exception {
        ArrayList<CustomEntity> physicalTestForThisTerm = queryDAOImpl.getPhysicalTestForThisTerm(TERM_ID);
        ArrayList<PhysicalTestDTO> physicalTestDTOS = new ArrayList<>();

        for (CustomEntity one : physicalTestForThisTerm) {

            PhysicalTest physicalTest = one.getPhysicalTest();

            physicalTestDTOS.add(new PhysicalTestDTO(
                            physicalTest.getPTID(),
                            one.getSID(),
                            one.getsName(),
                            physicalTest.getTERM_ID(),
                            one.getClass_(),
                            physicalTest.getAttendance(),
                            physicalTest.getSkill(),
                            physicalTest.getProgressEffort(),
                            physicalTest.getAttitudes(),
                            physicalTest.getPerformance(),
                            physicalTest.getTotal(),
                            physicalTest.isNewOne()
                    )
            );
        }
        return FXCollections.observableArrayList(physicalTestDTOS);
    }

    @Override
    public ObservableList<PhysicalTestDTO> getPhysicalTestForThisStudent(int sid) throws Exception {
        ArrayList<CustomEntity> physicalTestForThisTerm = queryDAOImpl.getPhysicalTestForThisStudent(sid);

        ArrayList<PhysicalTestDTO> physicalTestDTOS = new ArrayList<>();

        for (CustomEntity one : physicalTestForThisTerm) {

            PhysicalTest physicalTest = one.getPhysicalTest();
            Term term = one.getTerm();

            physicalTestDTOS.add(new PhysicalTestDTO(
                            physicalTest.getPTID(),
                            physicalTest.getSID(),
                            new TermDTO(term.getTERM_ID(), term.getTermName(), term.getYear(), term.getTermNo()),
                            one.getClass_(),
                            physicalTest.getAttendance(),
                            physicalTest.getSkill(),
                            physicalTest.getProgressEffort(),
                            physicalTest.getAttitudes(),
                            physicalTest.getPerformance(),
                            physicalTest.getTotal()
                    )
            );
        }
        return FXCollections.observableArrayList(physicalTestDTOS);
    }
}


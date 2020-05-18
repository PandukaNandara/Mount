package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.*;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.entity.CustomEntity;
import lk.ijse.mountCalvary.entity.Registration;
import lk.ijse.mountCalvary.entity.Student;
import lk.ijse.mountCalvary.entity.TelNo;
import lk.ijse.mountCalvary.model.RegistrationDTO;
import lk.ijse.mountCalvary.model.StudentDTO;
import lk.ijse.mountCalvary.model.TelNoDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public final class StudentBOImpl implements StudentBO {

    private StudentDAO studentDAOImpl;
    private TelNoDAO telNoDAOImpl;
    private RegistrationDAO registrationDAOImpl;
    private QueryDAO queryDAOImpl;
    private MSStudentDAO msStudentDAOImpl;

    public StudentBOImpl() {
        this.studentDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);
        this.telNoDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TELEPHONE_NO);
        this.registrationDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.REGISTRATION);
        this.queryDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOM);
        this.msStudentDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MS_STUDENT);
    }

    @Override
    public boolean addStudentWithActivity(StudentDTO st) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            Student stEn = dtoToEntity(st);
            boolean result = studentDAOImpl.save(stEn);
            if (result) {
                for (TelNoDTO telNo : st.getTelNoList()) {
                    result = telNoDAOImpl.saveWithoutPKey(new TelNo(telNo.getTelNo(), telNo.getSID()));
                    if (!result) {
                        conn.rollback();
                        return false;
                    }
                }
                for (RegistrationDTO reg : st.getAllInitialActivity()) {
                    result = registrationDAOImpl.saveWithoutPKey(new Registration(reg.getSID(),
                            reg.getActivity().getAID(), reg.getJoinedDate()));
                    if (!result) {
                        conn.rollback();
                        return false;
                    }
                }
            } else {
                conn.rollback();
                return false;
            }
            conn.commit();
            return true;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public ArrayList<StudentDTO> getAll() throws Exception {
        ArrayList<Student> all = studentDAOImpl.getAll();
        ArrayList<TelNo> telNoList = telNoDAOImpl.getAll();

        ArrayList<StudentDTO> allDTO = new ArrayList<>();
        for (Student one : all) {
            ArrayList<TelNoDTO> telNo = new ArrayList<>();
            for (TelNo oneNumber : telNoList) {
                if (oneNumber.getSID() == one.getSID())
                    telNo.add(new TelNoDTO(oneNumber.getTelID(), oneNumber.getTelNo(), oneNumber.getSID()));
            }
            allDTO.add(entityToDTO(one, FXCollections.observableArrayList(telNo)));
        }
        return allDTO;
    }

    @Override
    public int getNewIndex() throws Exception {
        return studentDAOImpl.getIncrementIndex();
    }

    @Override
    public ObservableList<StudentDTO> getAllStudentNameAndNumber() throws Exception {
        ArrayList<Student> all = studentDAOImpl.getAllStudentNameAndNumber();
        ArrayList<StudentDTO> allDTO = new ArrayList<>();

        for (Student one : all) {
            allDTO.add(new StudentDTO(one.getSID(), one.getsName()));
        }
        return FXCollections.observableArrayList(allDTO);
    }

    @Override
    public ObservableList<StudentDTO> getAllStudentNameAndNumberButLeft() throws Exception {
        ArrayList<Student> all = studentDAOImpl.getAllStudentNameAndNumberButLeft();
        ArrayList<StudentDTO> allDTO = new ArrayList<>();

        for (Student one : all) {
            allDTO.add(new StudentDTO(one.getSID(), one.getsName()));
        }
        return FXCollections.observableArrayList(allDTO);
    }

    @Override
    public boolean updateStudent(StudentDTO st) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            Student stEn = dtoToEntity(st);
            boolean result = studentDAOImpl.update(stEn);
            if (result) {
                for (TelNoDTO telNo : st.getTelNoList()) {
                    result = telNoDAOImpl.update(new TelNo(telNo.getTelID(), telNo.getTelNo(), telNo.getSID()));
                    if (!result) {
                        conn.rollback();
                        return false;
                    }
                }
            } else {
                conn.rollback();
                return false;
            }
            conn.commit();
            return true;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public StudentDTO getStudent(int SID) throws Exception {
        Student student = studentDAOImpl.get(SID);

        ArrayList<TelNo> telNoList = telNoDAOImpl.getTelNosForThisStudent(SID);

        ArrayList<TelNoDTO> telNo = new ArrayList<>();

        for (TelNo oneNumber : telNoList) {
            telNo.add(new TelNoDTO(oneNumber.getTelID(), oneNumber.getTelNo(), oneNumber.getSID()));
        }

        return entityToDTO(student);
    }

    @Override
    public ObservableList<StudentDTO> getStudentNotDoThisActivity(int AID) throws Exception {
        ArrayList<CustomEntity> nonRegistrations = queryDAOImpl.getAllStudentNotDoThisActivity(AID);
        ArrayList<StudentDTO> registrationDTOS = new ArrayList<>();
        for (CustomEntity oneStudent : nonRegistrations) {
            registrationDTOS.add(new StudentDTO(oneStudent.getStudent().getSID(), oneStudent.getStudent().getsName()));
        }
        return FXCollections.observableArrayList(registrationDTOS);
    }

    @Override
    public boolean isUniqueBCID(int BCID) throws Exception {
        return studentDAOImpl.isUniqueBCID(BCID);
    }

    @Override
    public boolean isUniqueStudentID(int SID) throws Exception {
        return studentDAOImpl.isUniqueStudentID(SID);
    }

    @Override
    public ObservableList<String> getAllDistinctClasses() throws Exception {
        return FXCollections.observableArrayList(studentDAOImpl.getAllDistinctClasses());
    }

    @Override
    public ObservableList<StudentDTO> getStudentWhoIsNotInContributionListOfThisCompetition(int CID) throws Exception {
        ArrayList<Student> studentList = queryDAOImpl.getStudentWhoIsNotInContributionListOfThisCompetition(CID);
        ArrayList<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student one : studentList) {
            studentDTOS.add(new StudentDTO(one.getSID(), one.getsName()));
        }
        return FXCollections.observableArrayList(studentDTOS);
    }

    @Override
    public ObservableList<StudentDTO> getAllStudentFromExternalDB(final String dbPath,
                                                                  String tableName, String SID,
                                                                  String sName, String gender,
                                                                  String DOB, String class_,
                                                                  String fatherName, String motherName,
                                                                  String note,
                                                                  String sAddress, String quit, String BCID)
            throws Exception {
        String[] studentNameParms = sName.split("\\+");
        String[] addressParms = sAddress.split("\\+");
        ArrayList<Student> allStudentFromExternalDB = msStudentDAOImpl.getAllStudentFromExternalDB(dbPath,
                tableName,
                SID,
                studentNameParms,
                gender,
                DOB,
                class_,
                fatherName,
                motherName,
                note,
                addressParms,
                quit,
                BCID);
        ObservableList<StudentDTO> allStudentDTO = FXCollections.observableArrayList();
        for (Student student : allStudentFromExternalDB)
            allStudentDTO.add(entityToDTO(student));
        return allStudentDTO;
    }

    @Override
    public boolean addUpdateStudentList(ObservableList<StudentDTO> items) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);

            for (StudentDTO studentDTO : items) {
                if (studentDAOImpl.isUniqueStudentID(studentDTO.getSID())) {
                    if (!studentDAOImpl.save(dtoToEntity(studentDTO)))
                        return false;
                } else if (!studentDAOImpl.update(dtoToEntity(studentDTO)))
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
    public boolean isLeftStudent(int SID) throws Exception {
        return studentDAOImpl.isLeftStudent(SID);
    }

    @Override
    public boolean isLeftStudent(String name) throws Exception {
        return studentDAOImpl.isLeftStudent(name);
    }

    @Override
    public ArrayList<String> showTablesAndDescTables(String filePath, String tableName) throws Exception {
        return msStudentDAOImpl.descTables(filePath, tableName);
    }

    @Override
    public boolean checkTableName(String filePath, String tableName) throws Exception {
        return msStudentDAOImpl.checkTableName(filePath, tableName);
    }

    @Override
    public boolean checkColumnName(String filePath, String tableName, String columnName) throws Exception{
        return msStudentDAOImpl.checkColumnName(filePath, tableName, columnName);
    }

    private StudentDTO entityToDTO(Student student) {
        return new StudentDTO(
                student.getSID(),
                student.getsName(),
                student.isGender(),
                student.getDOB(),
                student.getsClass(),
                student.getFatherName(),
                student.getMotherName(),
                student.getNote(),
                student.getHouse(),
                student.getAddress(),
                student.isQuit(),
                student.getBCID()
        );
    }
    private Student dtoToEntity(StudentDTO student) {
        return new Student(
                student.getSID(),
                student.getSName(),
                student.isGender(),
                student.getDOB(),
                student.getSClass(),
                student.getFatherName(),
                student.getMotherName(),
                student.getNote(),
                student.getHouse(),
                student.getAddress(),
                student.isQuit(),
                student.getBCID()
        );
    }
    private StudentDTO entityToDTO(Student student, ObservableList<TelNoDTO> telNoDTOS) {
        StudentDTO studentDTO = entityToDTO(student);
        studentDTO.setTelNoList(telNoDTOS);
        return studentDTO;
    }
}

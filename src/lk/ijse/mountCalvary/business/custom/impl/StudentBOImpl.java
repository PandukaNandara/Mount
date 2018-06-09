package lk.ijse.mountCalvary.business.custom.impl;

import javafx.collections.FXCollections;
import lk.ijse.mountCalvary.business.custom.StudentBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.RegistrationDAO;
import lk.ijse.mountCalvary.dao.custom.StudentDAO;
import lk.ijse.mountCalvary.dao.custom.TelNoDAO;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.entity.Registration;
import lk.ijse.mountCalvary.entity.Student;
import lk.ijse.mountCalvary.entity.TelNo;
import lk.ijse.mountCalvary.model.RegistrationDTO;
import lk.ijse.mountCalvary.model.StudentDTO;
import lk.ijse.mountCalvary.model.TelNoDTO;

import java.sql.Connection;
import java.util.ArrayList;

public class StudentBOImpl implements StudentBO {

    private StudentDAO studentDAOImpl;
    private TelNoDAO telNoDAOImpl;
    private RegistrationDAO registrationDAOImpl;

    public StudentBOImpl() {
        this.studentDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);

        this.telNoDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TELEPHONE_NO);

        this.registrationDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.REGISTRATION);
    }

    @Override
    public boolean addStudent(StudentDTO st) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            Student stEn = new Student(st.getSID(), st.getsName(), st.isGender(), st.getDOB(), st.getsClass(), st.getFatherName(), st.getMotherName(), st.getNote(), st.getHouse(), st.getAddress());
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
                    System.out.println(reg.toString());
                    result = registrationDAOImpl.saveWithoutPKey(new Registration(reg.getSID(), reg.getActivity().getAID(), reg.getJoinedDate()));
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
            allDTO.add(new StudentDTO(one.getSID(), one.getsName(), one.isGender(), one.getDOB(), one.getsClass(), one.getFatherName(), one.getMotherName(), one.getNote(), one.getHouse(), one.getAddress(), FXCollections.observableArrayList(telNo)));
        }
        return allDTO;
    }

    @Override
    public ArrayList<StudentDTO> getAllStudentNameAndNumber() throws Exception {
        ArrayList<Student> all = studentDAOImpl.getAllStudentNameAndNumber();
        ArrayList<StudentDTO> allDTO = new ArrayList<>();

        for (Student one : all) {
            allDTO.add(new StudentDTO(one.getSID(), one.getsName()));
        }
        return allDTO;
    }


    @Override
    public boolean updateStudent(StudentDTO st) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            Student stEn = new Student(st.getSID(), st.getsName(), st.isGender(), st.getDOB(), st.getsClass(), st.getFatherName(), st.getMotherName(), st.getNote(), st.getHouse(), st.getAddress());
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
                FXCollections.observableArrayList(telNo)
        );
    }
}
//System.out.println("TEMP" + st.getSID() +" "+ st.getStudentName() +" "+ st.isGender() +" "+ st.getDOB() +" "+ st.getsClass() +" "+ st.getFatherName() +" "+ st.getMotherName() +" "+ st.getNote() +" "+ st.getHouse() +" "+ st.getAddress());
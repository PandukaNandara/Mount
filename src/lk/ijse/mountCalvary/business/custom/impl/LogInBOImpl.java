package lk.ijse.mountCalvary.business.custom.impl;

import lk.ijse.mountCalvary.business.custom.LogInBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.LogInDAO;
import lk.ijse.mountCalvary.entity.LogIn;
import lk.ijse.mountCalvary.model.LogInDTO;

import java.util.ArrayList;
import java.util.Arrays;

public class LogInBOImpl implements LogInBO {
    private LogInDAO logInDAOImpl;

    public LogInBOImpl(){
        logInDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.LOG_IN);
    }

    @Override
    public boolean isValid(LogInDTO log) throws Exception {
        ArrayList<LogIn> allLogIn = logInDAOImpl.getAll();
        System.out.println(Arrays.toString(allLogIn.toArray()));
        for(LogIn logEnt : allLogIn){
            if(log.getUserName().equals(logEnt.getUserName()) && log.getPassword().equals(logEnt.getPassword()))
                return true;
        }
        return false;
    }
}

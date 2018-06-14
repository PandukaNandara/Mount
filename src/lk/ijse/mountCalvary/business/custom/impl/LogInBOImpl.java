package lk.ijse.mountCalvary.business.custom.impl;

import lk.ijse.mountCalvary.business.PasswordUtils;
import lk.ijse.mountCalvary.business.custom.LogInBO;
import lk.ijse.mountCalvary.dao.DAOFactory;
import lk.ijse.mountCalvary.dao.custom.LogInDAO;
import lk.ijse.mountCalvary.entity.LogIn;
import lk.ijse.mountCalvary.model.LogInDTO;

import java.util.Random;

public class LogInBOImpl implements LogInBO {
    private LogInDAO logInDAOImpl;

    public LogInBOImpl() {
        logInDAOImpl = DAOFactory.getInstance().getDAO(DAOFactory.DAOType.LOG_IN);
    }

    @Override
    public boolean isExistedUserName(String userName) throws Exception {
        return logInDAOImpl.search(userName) != null;
    }

    @Override
    public boolean isValidPassword(LogInDTO log) throws Exception {
        LogIn search = logInDAOImpl.search(log.getUserName());
        if (search == null)
            return false;
        return PasswordUtils.verifyUserPassword(log.getPassword(), search.getPassword(), search.getSalt());
    }

    @Override
    public boolean isNewOne(String userName) throws Exception {
        LogIn search = logInDAOImpl.search(userName);
        return search == null;
    }

    @Override
    public boolean add(LogInDTO logInDTO) throws Exception {
        Random random = new Random();
        int randNumber = random.nextInt(50) + 1;
        while (randNumber < 25)
            randNumber = random.nextInt(50) + 1;

        // Generate Salt. The generated value can be stored in DB.

        String salt = PasswordUtils.getSalt(randNumber);

        // Protect user's password. The generated value can be stored in DB.

        String securePassword = PasswordUtils.generateSecurePassword(logInDTO.getPassword(), salt);

        return logInDAOImpl.saveWithoutPKey(new LogIn(logInDTO.getUserName(), securePassword, salt));
    }
}

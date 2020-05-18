package lk.ijse.mountCalvary.business.custom;

import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.LogInDTO;

public interface LogInBO extends SuperBO {

    boolean isExistedUserName(String userName) throws Exception;

    boolean isValidPassword(LogInDTO log) throws Exception;

    boolean isNewOne(String userName) throws Exception;

    boolean add(LogInDTO logInDTO) throws Exception;
}

package lk.ijse.mountCalvary.business.custom;

import lk.ijse.mountCalvary.business.SuperBO;
import lk.ijse.mountCalvary.model.LogInDTO;

public interface LogInBO extends SuperBO {

    boolean isValid(LogInDTO log) throws Exception;

}

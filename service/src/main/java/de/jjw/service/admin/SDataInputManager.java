package de.jjw.service.admin;

import java.util.List;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.SDataInputModel;
import de.jjw.service.exception.JJWManagerException;

public interface SDataInputManager {
	
	public List<SDataInputModel> getAllInput();

    public void saveInputItem( SDataInputModel input )  throws OptimisticLockingException, JJWManagerException;
    
    public void removeSDataInput( )  throws JJWManagerException;

}

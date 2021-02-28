package de.jjw.dao.admin;

import java.util.List;

import de.jjw.dao.Dao;
import de.jjw.model.admin.SDataInputModel;

public interface SDataInputDao extends Dao {

	public List<SDataInputModel> getAllInput();

    public void saveInputItem( SDataInputModel input );
    
    public void removeSDataInput( );
}

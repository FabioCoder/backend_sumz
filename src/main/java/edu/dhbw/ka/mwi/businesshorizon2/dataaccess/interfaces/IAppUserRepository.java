package edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces;

import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.AppUserDao;

/**
 * Handles Crud-Operation for the AppUserDao-DB-Tables
 */
public interface IAppUserRepository extends CrudRepository<AppUserDao, Long> {
	AppUserDao findByEmail(String s);
	
	//void activateUser(UserActivationToken)
}

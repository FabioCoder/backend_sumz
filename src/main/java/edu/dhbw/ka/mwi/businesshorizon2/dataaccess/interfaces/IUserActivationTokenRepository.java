package edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces;

import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.dhbw.ka.mwi.businesshorizon2.models.daos.UserActivationTokenDao;

/**
 * This repository can execute crud-Repositories on the UserActivationTokenDao-DB-Table
 */
public interface IUserActivationTokenRepository extends CrudRepository<UserActivationTokenDao, Long>{

}

package edu.dhbw.ka.mwi.businesshorizon2.models.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IAppUserRepository;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.AppUserDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.UserActivationTokenDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.UserPasswordResetTokenDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.UserActivationTokenDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.AppUserDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.UserPasswordResetTokenDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.UserPutRequestDto;


/**
 * This is a mapper class. It maps Dtos to Daos or the other way around.
 */
public class UserMapper {
	
	@Autowired
	static
	IAppUserRepository userRepository;

	/**
	 * Maps UserDTO to UserDAO
	 * @param userDto
	 * @return AppUserDao
	 */
	public static AppUserDao mapToDao(AppUserDto userDto) {
		if(userDto == null) {
			return null;
		}
		
		return new AppUserDao(userDto.getId(), userDto.getEmail(), userDto.getPassword(), userDto.getRoles(), false);
	}

	/**
	 * Maps a List of UserDaos to UserDtos
	 * @param userDao
	 * @return
	 */
	public static List<AppUserDto> mapToDto(List<AppUserDao> userDao){
		if(userDao == null) {
			return null;
		}
		
		List<AppUserDto> result = new ArrayList<AppUserDto>();
		for (AppUserDao uDao: userDao) {
			result.add(mapToDto(uDao));
		}
		return result;
	}

	/**
	 * Maps UserDao to UserDTo
	 * @param uDao
	 * @return
	 */
	public static AppUserDto mapToDto(AppUserDao uDao) {
		if(uDao == null) {
			return null;
		}
		
		return new AppUserDto(uDao.getAppUserId(), uDao.getEmail(), uDao.getAppUserPassword(), uDao.getAppRoles(), uDao.getIsActive());
	}

	/**
	 * Maps UserPutRequestDto to UserDao. Only sets appUserPassword to uDto.oldPassword().
	 * @param uDto
	 * @return
	 */
	public static AppUserDao mapPutRequestOldToDao(UserPutRequestDto uDto) {
		
		if(uDto == null) {
			return null;
		}
		
		AppUserDao dao = new AppUserDao(uDto.getOldPassword());
		
		return dao;
	}
	/**
	 * Maps UserPutRequestDto to UserDao. Only sets appUserPassword to uDto.newPassword().
	 * @param uDto
	 * @return
	 */
	public static AppUserDao mapPutRequestNewToDao(UserPutRequestDto uDto) {
		
		if(uDto == null) {
			return null;
		}
		
		AppUserDao dao = new AppUserDao(uDto.getNewPassword());
		
		return dao;
	}

	/**
	 * Maps UserActivationTokenDto to UserActivationTokenDao
	 * @param tDto
	 * @return
	 */
	public static UserActivationTokenDao mapToDao(UserActivationTokenDto tDto) {
		
		if(tDto == null) {
			return null;
		}
		
		return new UserActivationTokenDao(tDto.getUserActivationTokenId(), null, tDto.getExpirationDate(), tDto.getTokenKey());
	}

	/**
	 * Maps UserActivationTokenDao to UserActionTokenDto
	 * @param tDao
	 * @return
	 */
	public static UserActivationTokenDto mapToDto(UserActivationTokenDao tDao) {
		UserActivationTokenDto userActivationTokenDto = new UserActivationTokenDto(tDao.getUserActivationTokenId(), 
				tDao.getAppUser().getAppUserId(), tDao.getExpirationDate(), tDao.getTokenKey());
		
		return userActivationTokenDto;
	}

	/**
	 * Maps UserPassowrdRestTokenDto to UserPasswordResetTokenDao
	 * @param tDto
	 * @return
	 */
	public static UserPasswordResetTokenDao mapToDao(UserPasswordResetTokenDto tDto) { 
		
		if(tDto == null) {
			return null;
		}
		
		return new UserPasswordResetTokenDao(tDto.getUserPasswordResetTokenId(), null, tDto.getExpirationDate(), tDto.getTokenKey());
	}

	/**
	 * Maps UserPasswordResetTokenDao to UserPassowrdResetTokenDto
	 * @param tDao
	 * @return
	 */
	public static UserPasswordResetTokenDto mapToDto(UserPasswordResetTokenDao tDao) {
		UserPasswordResetTokenDto userPasswordResetTokenDto = new UserPasswordResetTokenDto(tDao.getUserPasswordResetTokenId(),  
				tDao.getAppUser().getAppUserId(), tDao.getExpirationDate(), tDao.getTokenKey());
		
		return userPasswordResetTokenDto;
	}
}

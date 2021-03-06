package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import edu.dhbw.ka.mwi.businesshorizon2.models.daos.AppRoleDao;

public class UserPutRequestDto {
    
    @Email
    private String email;
    
    //at least 6 characters, maximum 20 
    //must contain one digit 0..9 
    //must contain 1 uppercase & 1 lowercase character 
    //must contain one special symbol from @#$%
    //This represents the current password of the user
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", message = "Das Anforderungen an das Passwort sind nicht erfüllt")
    private String oldPassword;
    
    //at least 6 characters, maximum 20 
    //must contain one digit 0..9 
    //must contain 1 uppercase & 1 lowercase character 
    //must contain one special symbol from @#$%
    //This is the new password
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", message = "Das Anforderungen an das Passwort sind nicht erfüllt")
    private String newPassword;
    
    private List<AppRoleDao> roles;
    
    private Boolean isActive;
    
    public UserPutRequestDto(String email, String passwordOld, String passwordNew, List<AppRoleDao> roles, Boolean isActive) {

    	this.email = email;
    	this.oldPassword = passwordOld;
    	this.newPassword = passwordNew;
    	this.roles = roles;
    	this.isActive = isActive;
    }

	public UserPutRequestDto() {
    	
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String password) {
        this.oldPassword = password;
    }
    
    public String getNewPassword() {
    	return newPassword;
    }
    
    public String setNewPassword(String passwordNew) {
    	return this.newPassword = passwordNew;
    }

    public List<AppRoleDao> getRoles() {
        return roles;
    }

    public void setRoles(List<AppRoleDao> roles) {
        this.roles = roles;
    }

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}

package dhbw.ka.mwi.businesshorizon2.businesshorizon2.user;

import edu.dhbw.ka.mwi.businesshorizon2.App;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.IUserService;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services.UserService;
import edu.dhbw.ka.mwi.businesshorizon2.config.SecurityConfig;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IAppRoleRepository;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IAppUserRepository;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.AppRoleDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.AppUserDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.AppUserDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.UserPutRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.mappers.UserMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UserTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private IAppRoleRepository roleRepository;

    @Autowired
    private IAppUserRepository userRepository;

    private Validator validator;

    @Before
    public void before() {

        validator = Validation.buildDefaultValidatorFactory().getValidator();
        //ApplicationContext context = springApplicationContext.getApplicationContext();
        //userService = new UserService();

    }

    @Test()
    public void testAddWithWrongEmailPattern() throws Exception{
        AppUserDto usr = new AppUserDto();
        usr.setEmail("testmail");
        usr.setPassword("123Abc@");
        Set<ConstraintViolation<AppUserDto>> violationSet = validator.validate(usr);
        Assert.assertTrue(violationSet.size() > 0);
    }

    @Test
    public void testSetUserIdIgnored() throws Exception{
        AppUserDto usr = new AppUserDto();
        usr.setEmail("testmail@test.de");
        usr.setId(-1L);
        usr.setPassword("123Abc@");
        if(userRepository.findByEmail(usr.getEmail()) != null) userRepository.delete(userRepository.findByEmail(usr.getEmail()));
        userService.addUser(usr, "localhost:8080");
    }

    @Test()
    public void testAddWithWrongPasswordPatternNoAt() throws Exception {
        AppUserDto usr = new AppUserDto();
        usr.setEmail("testmail@test.de");
        usr.setPassword("Abc1234");
        Set<ConstraintViolation<AppUserDto>> violationSet = validator.validate(usr);
        Assert.assertTrue(violationSet.size() > 0);
    }

    @Test()
    public void testAddWithWrongPasswordPatternNoNumber() throws Exception {
        AppUserDto usr = new AppUserDto();
        usr.setEmail("testmail@test.de");
        usr.setPassword("Abc@abc");
        Set<ConstraintViolation<AppUserDto>> violationSet = validator.validate(usr);
        Assert.assertTrue(violationSet.size() > 0);
    }

    @Test()
    public void testAddWithWrongPasswordPatternTooLong() throws Exception {
        AppUserDto usr = new AppUserDto();
        usr.setEmail("testmail@test.de");
        usr.setPassword("Ab1@dddddddddddddddddddddddddddddddddddddddddddddddddd");
        Set<ConstraintViolation<AppUserDto>> violationSet = validator.validate(usr);
        Assert.assertTrue(violationSet.size() > 0);
    }

    @Test()
    public void testAddWithWrongPasswordPatternTooShort() throws Exception {
        AppUserDto usr = new AppUserDto();
        usr.setEmail("testmail@test.de");
        usr.setPassword("Ab1@");
        Set<ConstraintViolation<AppUserDto>> violationSet = validator.validate(usr);
        Assert.assertTrue(violationSet.size() > 0);
    }

    @Test()
    public void testAddWithWrongPasswordPatternNoUpperCase() throws Exception {
        AppUserDto usr = new AppUserDto();
        usr.setEmail("testmail@test.de");
        usr.setPassword("abc@123");
        Set<ConstraintViolation<AppUserDto>> violationSet = validator.validate(usr);
        Assert.assertTrue(violationSet.size() > 0);
    }

    @Test(expected = Exception.class)
    public void addUserDoubleTest() throws Exception{
        AppUserDto user = new AppUserDto();
        user.setEmail("testmail@noavailable");
        user.setPassword("Abc123@");
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.delete(userRepository.findByEmail(user.getEmail()));
        userService.addUser(user, "localhost:8080");
        userService.addUser(user, "localhost:8080");
    }

    @Test
    public void addUserTestTestActive() throws Exception {
        AppUserDto user = new AppUserDto();
        user.setEmail("testmail@noavailable.de");
        user.setPassword("Abc123@");
        user.setIsActive(true);
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.delete(userRepository.findByEmail(user.getEmail()));
        userService.addUser(user, "localhost:8080");
        Assert.assertFalse(userRepository.findByEmail("Abc123@").getIsActive());
    }

    @Test
    public void addUserTestAddAdminRole() throws Exception {
        AppUserDto user = new AppUserDto();
        user.setEmail("testmail@noavailable.de");
        user.setPassword("Abc123@");

        List<AppRoleDao> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);

        user.setRoles(roles);
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.delete(userRepository.findByEmail(user.getEmail()));
        userService.addUser(user, "localhost:8080");
        Assert.assertTrue(userRepository.findByEmail("testmail@noavailable.de").getAppRoles().size() == 1);
    }

    @Test
    public void addUserTest() throws Exception {
        AppUserDto user = new AppUserDto();
        user.setEmail("testmail@noavailable.de");
        user.setPassword("Abc123@");
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.delete(userRepository.findByEmail(user.getEmail()));
        userService.addUser(user, "localhost:8080");
    }

    @Test(expected = Exception.class)
    public void TestUpdatePasswordIncorrectOldPw() throws Exception {
        AppUserDto user = new AppUserDto();
        user.setEmail("testmail@noavailable.de");
        user.setPassword(userService.encodePassword("Abc123@"));
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.delete(userRepository.findByEmail(user.getEmail()));
        AppUserDao oldUser = userRepository.save(UserMapper.mapToDao(user));

        UserPutRequestDto put = new UserPutRequestDto();
        put.setOldPassword("wrongPassowrd");
        put.setNewPassword("Abcd123@");

        userService.updateUserPassword(put, oldUser.getAppUserId());
    }

    @Test(expected = Exception.class)
    public void TestUpdatePasswordInvalidId() throws Exception {
        AppUserDto user = new AppUserDto();
        user.setEmail("testmail@noavailable.de");
        user.setPassword(userService.encodePassword("Abc123@"));
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.delete(userRepository.findByEmail(user.getEmail()));
        AppUserDao oldUser = userRepository.save(UserMapper.mapToDao(user));

        UserPutRequestDto put = new UserPutRequestDto();
        put.setOldPassword("Abc123@");
        put.setNewPassword("Abcd123@");

        userService.updateUserPassword(put, -123L);
    }

    @Test
    public void TestUpdatePassword() throws Exception {
        AppUserDto user = new AppUserDto();
        user.setEmail("testmail@noavailable.de");
        user.setPassword(userService.encodePassword("Abc123@"));
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.delete(userRepository.findByEmail(user.getEmail()));
        AppUserDao oldUser = userRepository.save(UserMapper.mapToDao(user));

        UserPutRequestDto put = new UserPutRequestDto();
        put.setOldPassword("Abc123@");
        put.setNewPassword("Abcd123@");

        userService.updateUserPassword(put, oldUser.getAppUserId());
        AppUserDao newUser = userRepository.findById(oldUser.getAppUserId()).get();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(securityConfig.getEncodingStrength());
        Boolean passwordMatch = passwordEncoder.matches("Abcd123@", newUser.getAppUserPassword());


        Assert.assertTrue(passwordMatch);
    }

    @Test
    public void TestDeleteUser() throws Exception{
        AppUserDto user = new AppUserDto();
        user.setEmail("testmail@noavailable.de");
        user.setPassword(userService.encodePassword("Abc123@"));
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.delete(userRepository.findByEmail(user.getEmail()));
        AppUserDao dbusr = userRepository.save(UserMapper.mapToDao(user));
        user.setPassword("Abc123@");
        userService.deleteUser(user, dbusr.getAppUserId());

        Assert.assertFalse(userRepository.existsById(dbusr.getAppUserId()));

    }

    @Test(expected = Exception.class)
    public void TestDeleteUserIncorrectPassword() throws Exception{
        AppUserDto user = new AppUserDto();
        user.setEmail("testmail@noavailable.de");
        user.setPassword(userService.encodePassword("Abc123@"));
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.delete(userRepository.findByEmail(user.getEmail()));
        AppUserDao dbusr = userRepository.save(UserMapper.mapToDao(user));
        user.setPassword("wrongPassword");
        userService.deleteUser(user, dbusr.getAppUserId());

    }

    @Test(expected = Exception.class)
    public void TestDeleteUserIncorrectId() throws Exception{
        AppUserDto user = new AppUserDto();
        user.setEmail("testmail@noavailable.de");
        user.setPassword(userService.encodePassword("Abc123@"));
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.delete(userRepository.findByEmail(user.getEmail()));
        AppUserDao dbusr = userRepository.save(UserMapper.mapToDao(user));
        user.setPassword("Abc123@");
        userService.deleteUser(user, -123L);

    }

    @Test()
    public void testUpdateWithWrongEmailPattern() throws Exception{
        UserPutRequestDto usr = new UserPutRequestDto();
        usr.setEmail("testmail");
        usr.setOldPassword("123Abc@");
        usr.setNewPassword("123Abc@");
        Set<ConstraintViolation<UserPutRequestDto>> violationSet = validator.validate(usr);
        Assert.assertTrue(violationSet.size() > 0);
    }


    @Test()
    public void testUpdateWithWrongPasswordPatternNoAt() throws Exception {
        UserPutRequestDto usr = new UserPutRequestDto();
        usr.setEmail("testmail@test.de");
        usr.setOldPassword("123Abc");
        usr.setNewPassword("123Abc");
        Set<ConstraintViolation<UserPutRequestDto>> violationSet = validator.validate(usr);
        Assert.assertTrue(violationSet.size() > 1);
    }

    @Test()
    public void testUpdateWithWrongPasswordPatternNoNumber() throws Exception {
        UserPutRequestDto usr = new UserPutRequestDto();
        usr.setEmail("testmail@test.de");
        usr.setOldPassword("Abcccc@");
        usr.setNewPassword("Abcccc@");
        Set<ConstraintViolation<UserPutRequestDto>> violationSet = validator.validate(usr);
        Assert.assertTrue(violationSet.size() > 1);
    }

    @Test()
    public void testUpdateWithWrongPasswordPatternTooLong() throws Exception {
        UserPutRequestDto usr = new UserPutRequestDto();
        usr.setEmail("testmail@test.de");
        usr.setOldPassword("123Abc@hhhhhhhhhhhhhhhhhhhhhhhhhhh");
        usr.setNewPassword("123Abc@hhhhhhhhhhhhhhhhhhhhhhhhhhh");
        Set<ConstraintViolation<UserPutRequestDto>> violationSet = validator.validate(usr);
        Assert.assertTrue(violationSet.size() > 1);
    }

    @Test()
    public void testUpdateWithWrongPasswordPatternTooShort() throws Exception {
        UserPutRequestDto usr = new UserPutRequestDto();
        usr.setEmail("testmail@test.de");
        usr.setOldPassword("Ab1@");
        usr.setNewPassword("Ab1@");
        Set<ConstraintViolation<UserPutRequestDto>> violationSet = validator.validate(usr);
        Assert.assertTrue(violationSet.size() > 1);
    }

    @Test()
    public void testUpdateWithWrongPasswordPatternNoUpperCase() throws Exception {
        UserPutRequestDto usr = new UserPutRequestDto();
        usr.setEmail("testmail@test.de");
        usr.setOldPassword("123bc@");
        usr.setNewPassword("123bc@");
        Set<ConstraintViolation<UserPutRequestDto>> violationSet = validator.validate(usr);
        Assert.assertTrue(violationSet.size() > 1);
    }

    @Test
    public void testUpdateUserTestTestActive() throws Exception {
        AppUserDto user = new AppUserDto();
        user.setEmail("testmail@noavailable.de");
        user.setPassword("Abc123@");
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.delete(userRepository.findByEmail(user.getEmail()));
        AppUserDao dbusr = userRepository.save(UserMapper.mapToDao(user));

        UserPutRequestDto usr = new UserPutRequestDto();
        usr.setEmail("testmail@test.de");
        usr.setOldPassword("Abc123@");
        usr.setNewPassword("Abc1234@");
        usr.setIsActive(true);
        userService.updateUserPassword(usr, dbusr.getAppUserId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(securityConfig.getEncodingStrength());
        Boolean passwordMatch = passwordEncoder.matches("Abcd1234@", dbusr.getAppUserPassword());


        Assert.assertTrue(passwordMatch);
        Assert.assertFalse(userRepository.findByEmail("Abc123@").getIsActive());
    }

    @Test
    public void testUpdateUserTestAddAdminRole() throws Exception {
        AppUserDto user = new AppUserDto();
        user.setEmail("testmail@noavailable.de");
        user.setPassword("Abc123@");

        UserPutRequestDto usr = new UserPutRequestDto();
        usr.setEmail("testmail@test.de");
        usr.setOldPassword("Abc123@");
        usr.setNewPassword("Abc1234@");

        List<AppRoleDao> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);

        user.setRoles(roles);
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.delete(userRepository.findByEmail(user.getEmail()));
        AppUserDao dbusr = userRepository.save(UserMapper.mapToDao(user));

        userService.updateUserPassword(usr, dbusr.getAppUserId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(securityConfig.getEncodingStrength());
        Boolean passwordMatch = passwordEncoder.matches("Abc1234@", dbusr.getAppUserPassword());


        Assert.assertTrue(passwordMatch);
        Assert.assertTrue(userRepository.findByEmail("testmail@noavailable.de").getAppRoles().size() == 1);
    }

}

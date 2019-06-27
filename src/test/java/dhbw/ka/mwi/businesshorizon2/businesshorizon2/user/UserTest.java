package dhbw.ka.mwi.businesshorizon2.businesshorizon2.user;

import edu.dhbw.ka.mwi.businesshorizon2.App;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.IUserService;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services.UserService;
import edu.dhbw.ka.mwi.businesshorizon2.config.SecurityConfig;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IAppUserRepository;
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
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UserTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private SecurityConfig securityConfig;

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

}

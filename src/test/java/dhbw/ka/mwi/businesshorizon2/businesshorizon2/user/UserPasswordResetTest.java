package dhbw.ka.mwi.businesshorizon2.businesshorizon2.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dhbw.ka.mwi.businesshorizon2.App;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services.UserPasswordResetService;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services.UserService;
import edu.dhbw.ka.mwi.businesshorizon2.config.SecurityConfig;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IAppUserRepository;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IUserPasswordResetTokenRepository;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.AppRoleDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.AppUserDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.UserPasswordResetTokenDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.AppUserDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.UserPasswordResetTokenDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.mappers.UserMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UserPasswordResetTest {

    @Autowired
    private UserPasswordResetService userPasswordResetService;

    @Autowired
    private IAppUserRepository userRepository;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private IUserPasswordResetTokenRepository userPasswordResetTokenRepository;

    @Test(expected = Exception.class)
    public void testRequestResetTokenNotRegisteredEmail() throws Exception {

        userService.requestUserPasswordReset("aeggegpsdghsadpgu@gegwegd.gwegwegsds", "localhost");
    }

    @Test(expected = Exception.class)
    public void testPasswordResetTokenInvalid() throws Exception {
        userService.resetUserPassword(new AppUserDto(1123L, "54235@te.de", "erwr23523@3R", new ArrayList<AppRoleDao>(), true), "gasg23tq34g34g3q4gt3q4g3q4g§$&$%/%&(");

    }

    @Test
    public void testPasswordResetProcess() throws Exception{

        //Create User
        AppUserDao user = new AppUserDao();
        user.setEmail("test@test.de");
        user.setIsActive(true);
        user.setPassword("Abc123@");
        //Check if user is still present in DB
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.deleteById(userRepository.findByEmail(user.getEmail()).getAppUserId());
        //Save User
        userRepository.save(user);

        //Get Reset Token
        String resetToken = userService.requestUserPasswordReset(user.getEmail(), "localhost");

        //Create UserDto
        AppUserDto dto = new AppUserDto();
        dto.setId(user.getAppUserId());
        dto.setPassword("Abcd123@");

        //Reset the Password
        userService.resetUserPassword(dto, resetToken);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(securityConfig.getEncodingStrength());


        AppUserDao newUser = new AppUserDao();
        newUser = userRepository.findById(user.getAppUserId()).get();
        Boolean oldPasswordIsCorrect = passwordEncoder.matches(dto.getPassword(), newUser.getAppUserPassword());


        Assert.assertTrue(oldPasswordIsCorrect);
        //Assert.assertEquals(userService.encodePassword(dto.getPassword()), newUser.getAppUserPassword());
    }

}

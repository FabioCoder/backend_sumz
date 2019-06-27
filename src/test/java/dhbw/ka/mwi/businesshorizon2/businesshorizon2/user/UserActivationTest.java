package dhbw.ka.mwi.businesshorizon2.businesshorizon2.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dhbw.ka.mwi.businesshorizon2.App;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services.UserActivationService;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services.UserService;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IAppUserRepository;
import edu.dhbw.ka.mwi.businesshorizon2.dataaccess.interfaces.IUserActivationTokenRepository;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.AppUserDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.daos.UserActivationTokenDao;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.UserActivationTokenDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.mappers.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UserActivationTest {


    @Autowired
    private UserActivationService activationService;

    @Autowired
    private IAppUserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private IUserActivationTokenRepository activationTokenRepository;

    @Test(expected = Exception.class)
    public void testActivateUserWithExpiredToken() throws Exception {
        //Create new USer
        AppUserDao user = new AppUserDao();
        user.setPassword("Abc123@");
        user.setIsActive(false);
        user.setEmail("test123@test.de");
        //Check if user still exists from last tests
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.deleteById(userRepository.findByEmail(user.getEmail()).getAppUserId());
        //Save new user
        userRepository.save(user);
        //Check that user is inactive
        Assert.assertFalse(user.getIsActive());
        //get Activationtoken for given User
        UserActivationTokenDao token = activationService.createUserActivationToken(user);
        //Set Token to be expired
        token.setExpirationDate(LocalDateTime.MIN);
        activationTokenRepository.save(token);

        //Translate token to the required Format
        UserActivationTokenDto userTokenDto = UserMapper.mapToDto(token);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String link = objectMapper.writeValueAsString(userTokenDto);
        link = Base64.getEncoder().encodeToString(link.getBytes(StandardCharsets.UTF_8));

        //Activate User
        userService.activateUser(link);
    }

    @Test
    public void testActivateUser() throws Exception {
        //Create new USer
        AppUserDao user = new AppUserDao();
        user.setPassword("Abc123@");
        user.setIsActive(false);
        user.setEmail("test123@test.de");
        //Check if user still exists from last tests
        if(userRepository.findByEmail(user.getEmail()) != null) userRepository.deleteById(userRepository.findByEmail(user.getEmail()).getAppUserId());
        //Save new user
        userRepository.save(user);
        //Check that user is inactive
        Assert.assertFalse(user.getIsActive());
        //get Activationtoken for given User
        UserActivationTokenDao token = activationService.createUserActivationToken(user);

        //Translate token to the required Format
        UserActivationTokenDto userTokenDto = UserMapper.mapToDto(token);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String link = objectMapper.writeValueAsString(userTokenDto);
        link = Base64.getEncoder().encodeToString(link.getBytes(StandardCharsets.UTF_8));

        //Activate User
        userService.activateUser(link);

        AppUserDao newUser = userRepository.findById(user.getAppUserId()).get();
        //Assert that User is active
        Assert.assertTrue(newUser.getIsActive());
    }
}

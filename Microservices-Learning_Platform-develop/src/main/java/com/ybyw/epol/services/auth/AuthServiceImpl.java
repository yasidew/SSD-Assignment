package com.ybyw.epol.services.auth;

import com.ybyw.epol.dto.SignupRequest;
import com.ybyw.epol.dto.UserDto;
import com.ybyw.epol.entity.User;
import com.ybyw.epol.enums.UserRole;
//import com.ybyw.epol.messaging.UserRegistrationSource;
import com.ybyw.epol.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;

    //    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //new changes

//    @Autowired
//    private UserRegistrationSource userRegistrationSource;

    public UserDto createUser(SignupRequest signupRequest){
        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.LEARNER);
        User createUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(createUser.getId());
        userDto.setEmail(createUser.getEmail()); // set email
        userDto.setName(createUser.getName()); // set name


        //new changes
        // Create and send the message
//        userRegistrationSource.userRegistration().apply(userDto);

        return userDto;
    }

    public Boolean hasUserWithEmail(String email){
        return userRepository.findFirstByEmail(email).isPresent();
    }

    //get automatically called
    @PostConstruct
    public void createInstructorAccount() {
        User adminAccount = userRepository.findByRole(UserRole.INSTRUCTOR);

        if(null == adminAccount) {
            User user = new User();

            user.setEmail("instructor@test.com");
            user.setName("instructor");
            user.setRole(UserRole.INSTRUCTOR);
            user.setPassword(new BCryptPasswordEncoder().encode("instructor"));
            userRepository.save(user);

        }
    }

    //get automatically called
    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);

        if(null == adminAccount) {
            User user = new User();

            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);

        }
    }
}

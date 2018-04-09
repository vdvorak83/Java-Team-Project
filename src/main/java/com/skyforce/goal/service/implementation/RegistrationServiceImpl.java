package com.skyforce.goal.service.implementation;

import com.skyforce.goal.dto.UserDto;
import com.skyforce.goal.model.Image;
import com.skyforce.goal.model.User;
import com.skyforce.goal.repository.UserRepository;
import com.skyforce.goal.security.role.UserRole;
import com.skyforce.goal.security.state.UserState;
import com.skyforce.goal.service.RegistrationService;
import com.skyforce.goal.util.SmtpMailSender;
import com.skyforce.goal.exception.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SmtpMailSender mailSender;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User register(UserDto userDto) throws EmailExistsException {
        if (emailExists(userDto.getEmail())) {
            throw new EmailExistsException("There is an account with that e-mail address: " +
                    userDto.getEmail());
        }

        Image defaultImage = Image.builder().id(8L).build();

        User newUser = User.builder()
                .login(userDto.getLogin())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .regDate(new Date())
                .role(UserRole.USER)
                .state(UserState.NOT_ACTIVE)
                .uuid(UUID.randomUUID().toString())
                .image(defaultImage)
                .build();

        userRepository.save(newUser);

        System.out.println(newUser.getEmail());

        executorService.submit(() -> {
            try {
                mailSender.send(newUser.getEmail(), "Please confirm your registration",
                        "http://localhost:8080/confirm/" + newUser.getUuid());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        return newUser;
    }

    @Override
    public void confirm(String uuid) {
        Optional<User> optionalUser = userRepository.findUserByUuid(uuid);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.setState(UserState.ACTIVE);
            user.setUuid(null);

            userRepository.save(user);
        }
    }

    @Override
    public boolean emailExists(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);

        return optionalUser.isPresent();
    }
}

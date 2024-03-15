package com.kaisikk.java.springboot.javaspringboot.service;

import com.kaisikk.java.springboot.javaspringboot.domain.Role;
import com.kaisikk.java.springboot.javaspringboot.domain.User;
import com.kaisikk.java.springboot.javaspringboot.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Kaisikk
 *
 * Сервис для работы с юзером
 */
@Service
public class UserService implements UserDetailsService {

    // репозиторий для сохранения юзера
    @Autowired
    private UserRepository userRepo;

    // сервис по отправке сообщения
    @Autowired
    private MailSender mailSender;

    // шифровальщик паролей
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Поиск юзера по имени
     *
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);

        if(user == null){
        throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    /**
     * Добавление юзера
     *
     * @param user
     * @return
     */
    public boolean addUser(User user) {

        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        // устанавливаем признак, что юзер активный
        user.setActive(true);
        // устанваливаю юзеру базовую роль
        user.setRoles(Collections.singleton(Role.USER));
        // устанавливаем юзеру код для активации
        user.setActivationCode(UUID.randomUUID().toString());
        // шифруем и устанавливаем пароль
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        // если указана почта, то составляем письмо для того чтобы юзер активировал аккаунт
        if (!StringUtils.isEmpty(user.getEmail())) {

            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            // отправка письма активации
            mailSender.send(user.getEmail(), "ActivationCode", message);
        }

        // возвращаем признак, что юзер добавлен
        return true;
    }

    /**
     * Активация юзера
     *
     * @param code
     * @return boolean
     */
    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }

    /**
     * Поиск всех юзеров в базе
     *
     * @return List
     */
    public List<User> findAll() {
        return userRepo.findAll();
    }

    /**
     * Сохранение пользователя
     *
     * @param user
     * @param userName
     * @param form
     */
    public void saveUser(User user, String userName, Map<String, String> form) {

        user.setUsername(userName);
        // получем роль пользователя и перебиваем их полученными
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

    /**
     * Обновление юзера
     *
     * @param user
     * @param password
     * @param email
     */
    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail) ||
                (userEmail != null && !userEmail.equals(email)));
        if (isEmailChanged) {
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }
        userRepo.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }

    }

    /**
     * Отправка сообщения активации на почту
     *
     * @param user
     */
    private void sendMessage(User user) {

        if (!StringUtils.isEmpty(user.getEmail())) {

            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );


            mailSender.send(user.getEmail(), "ActivationCode", message);
        }

    }
}

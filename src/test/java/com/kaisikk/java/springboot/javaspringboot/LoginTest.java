package com.kaisikk.java.springboot.javaspringboot;

import com.kaisikk.java.springboot.javaspringboot.controller.MainController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController mainController;

    @Test
    public void contextLoad() throws Exception {
        this.mockMvc.perform(
                        // выполняем get запрос
                        get("/"))
                // выводим результат в консоль
                .andDo(print())
                // проверяем статус ответа
                .andExpect(status().isOk())
                // проверяем содержит ли ответ переданную подстроку
                .andExpect(content().string(containsString("Hello, guest")))
                .andExpect(content().string(containsString("Please, login")));
    }


    @Test
    public void accessDeniedTest() throws Exception {
        this.mockMvc.perform(
                        get("/main"))
                .andDo(print())
                // проверяем что будет 300 статус
                .andExpect(status().is3xxRedirection())
                // проверяем куда будет редирект
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void correctLoginTest() throws Exception{

    this.mockMvc.perform(formLogin().user("admin").password("123"))
            .andDo(print())
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));
    }

    @Test
    public void badCredentials() throws Exception{
        this.mockMvc.perform(post("/login").param("user", "Alfred"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}

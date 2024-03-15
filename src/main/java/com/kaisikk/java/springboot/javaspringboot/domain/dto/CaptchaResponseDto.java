package com.kaisikk.java.springboot.javaspringboot.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

/**
 * @author Kaisikk
 *
 * Сервис для получения ответа от гугла, прошел ли пользователь капчу или нет
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponseDto {
    private boolean success;

    @JsonAlias("error-codes")
    private Set<String> errorCodes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Set<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(Set<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}

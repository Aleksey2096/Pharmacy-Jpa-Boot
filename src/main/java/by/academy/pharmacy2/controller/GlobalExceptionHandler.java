package by.academy.pharmacy2.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.SQLIntegrityConstraintViolationException;

import static by.academy.pharmacy2.entity.Constant.ERR;
import static by.academy.pharmacy2.entity.Constant.ERROR1;
import static by.academy.pharmacy2.entity.Constant.ERROR_CONSTRAINT_VIOLATION;
import static by.academy.pharmacy2.entity.Constant.REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public String handleException(final Model model) {
        model.addAttribute(ERR, ERROR_CONSTRAINT_VIOLATION);
        return ERROR1;
    }

    @ModelAttribute
    public void addRequestAttribute(final Model model, final HttpServletRequest request) {
        model.addAttribute(REQUEST, request);
    }
}

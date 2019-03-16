package com.repository.core.handler;

import com.repository.core.config.ProjectConfig;
import com.repository.core.constants.URLConstant;
import com.repository.core.exception.AuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 权限校验异常捕获处理
 * @author schuyler
 */
@ControllerAdvice
public class AuthExceptionHandler {
    @Autowired
    private ProjectConfig projectConfig;

    @ExceptionHandler(value = AuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:"
                .concat(URLConstant.USER_LOGIN_PAGE_URL));
    }

}

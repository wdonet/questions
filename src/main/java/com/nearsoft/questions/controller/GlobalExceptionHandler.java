package com.nearsoft.questions.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";

    private final Logger _log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView handleDefaultException(HttpServletRequest req, Exception exception) {
        _log.error("Error : " + exception.getMessage());
        //todo wdonet : If the exception is annotated with @ResponseStatus rethrow it and
        // let the framework handle it - like:
//        @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Order")  // 404
//        public class OrderNotFoundException extends RuntimeException { ...
//        }
//        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
//            throw e;
        ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
        mav.addObject("status", "GNL");
        mav.addObject("message", exception.getMessage());
        if (_log.isDebugEnabled()) {
            mav.addObject("stackTrace", Arrays.toString(exception.getStackTrace()));
        }
        return mav;
    }
}

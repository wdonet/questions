package com.nearsoft.questions.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView handleIllegalStateException(HttpServletRequest req, Exception exception) {
        log.error("Error : " + exception.getMessage());
        return buildModelAndView(DEFAULT_ERROR_VIEW, "Session", exception);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleDefaultException(HttpServletRequest req, Exception exception) {
        log.error("Error : " + exception.getMessage());
        //todo wdonet : If the exception is annotated with @ResponseStatus rethrow it and
        // let the framework handle it - like:
//        @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Order")  // 404
//        public class OrderNotFoundException extends RuntimeException { ...
//        }
//        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
//            throw e;
        return buildModelAndView(DEFAULT_ERROR_VIEW, "General", exception);
    }

    private ModelAndView buildModelAndView(String viewName, String status, Exception e) {
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("status", status);
        String message = e.getMessage();
        if (message == null && e.getCause() != null) {
            message = e.getCause().getCause().getMessage();
        }
        if (message == null) {
            message = e.toString();
        }
        mav.addObject("message", message);
        if (log.isDebugEnabled()) {
            mav.addObject("stackTrace", Arrays.toString(e.getStackTrace()));
        }
        return mav;
    }
}

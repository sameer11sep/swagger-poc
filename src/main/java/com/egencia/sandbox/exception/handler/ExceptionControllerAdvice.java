package com.egencia.sandbox.exception.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by samarora on 12/16/14.
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({Exception.class,RuntimeException.class})
    public ModelAndView exception(Exception e) {
        ModelAndView mav = new ModelAndView("exception");
        mav.addObject("name", e.getClass().getSimpleName());
        mav.addObject("message", e.getMessage());

        return mav;
    }
}

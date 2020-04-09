package ru.itis.onlineShop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {


    @GetMapping("/signIn")
    public String getPage(){
        return "sign_in";
    }

//    @PostMapping("/signIn")
//    public ModelAndView signIn(SignInDto signInDto, ServletRequest request, ServletResponse response) {
//        try {
//            TokenDto tokenDto = signInService.signIn(signInDto);
//            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//            httpServletRequest.setAttribute("Authorization", tokenDto.getToken());
//            httpServletResponse.setHeader("Authorization", tokenDto.getToken());
//            return new ModelAndView("redirect:/main");
//        } catch (AccessDeniedException e){
//            return new ModelAndView("sign_in", "exception", e);
//        }
//    }
}

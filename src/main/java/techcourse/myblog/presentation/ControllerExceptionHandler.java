package techcourse.myblog.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import techcourse.myblog.service.exception.AuthenticationFailException;
import techcourse.myblog.service.exception.ArticleNotFoundException;

import javax.servlet.http.HttpServletResponse;

import static techcourse.myblog.presentation.UserController.LOGIN_ERROR_MSG;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ArticleNotFoundException.class)
    public String handleArticleNotFoundException(ArticleNotFoundException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return "error404";
    }

    @ExceptionHandler(AuthenticationFailException.class)
    public String handleUserNotFoundException(AuthenticationFailException e, HttpServletResponse response, Model model) {
        model.addAttribute("error", LOGIN_ERROR_MSG);
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return "login";
    }
}

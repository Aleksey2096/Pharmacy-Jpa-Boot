package by.academy.pharmacy2.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static by.academy.pharmacy2.entity.Constant.CLIENT_MEDICINES;
import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.REDIRECT;
import static by.academy.pharmacy2.entity.Constant.SLASH;

@Controller
@RequestMapping
public class WelcomeController {

    @GetMapping({EMPTY, SLASH})
    public String index() {
        return REDIRECT + CLIENT_MEDICINES;
    }
}

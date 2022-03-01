package ee.demo.sectorSelector.api;

import ee.demo.sectorSelector.models.UserCreateDto;
import ee.demo.sectorSelector.security.AuthService;
import ee.demo.sectorSelector.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static java.util.Objects.nonNull;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (authService.isAuthenticated()) {
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute("error", "Username/password is invalid.");
        }
        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if (authService.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("userForm", new UserCreateDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userForm") UserCreateDto userForm, BindingResult bindingResult) {
        if (nonNull(userService.findByUsername(userForm.getUsername()))) {
            bindingResult.rejectValue( "username", "Username already taken");
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        authService.autoLogin(userForm.getUsername(), userForm.getPassword());
        return "redirect:/";
    }

}

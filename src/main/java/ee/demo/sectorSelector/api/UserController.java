package ee.demo.sectorSelector.api;

import ee.demo.sectorSelector.models.SectorDto;
import ee.demo.sectorSelector.models.UserDto;
import ee.demo.sectorSelector.models.UserViewDto;
import ee.demo.sectorSelector.security.UserValidator;
import ee.demo.sectorSelector.services.SectorsService;
import ee.demo.sectorSelector.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SectorsService sectorsService;
    private final UserValidator validator;

    @GetMapping("/")
    public String loadUserSectors(Model model) {
        UserDto user = userService.getLoggedInUser();
        if (user == null) {
            return "logout";
        }
        List<String> userSectors = sectorsService.findUserSectors(user.getId());

        List<SectorDto> sectorOptions = sectorsService.findAll();
        sectorOptions.forEach(sectorDto -> {
            sectorDto.setName(String.format("%s%s", "--".repeat(sectorDto.getSubTypeLevel()), sectorDto.getName()));
            sectorDto.setSelected(userSectors.contains(sectorDto.getCode()));
        });

        UserViewDto userDetails = UserViewDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .termsAgreed(user.getTermsAgreed())
                .sectors(userSectors)
                .build();

        model.addAttribute("userDetails", userDetails);
        model.addAttribute("sectorOptions", sectorOptions);
        return "index";
    }

    @PostMapping("/user/update")
    public String saveUserDetails(
            @Valid @ModelAttribute("userDetails") UserViewDto userDetails,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        validator.validateAllowed(userDetails);
        try {
            userService.update(userDetails);
            sectorsService.upsertUserSectors(userDetails);
        } catch (Exception e) {
            bindingResult.addError(new ObjectError("globalError", e.getMessage()));
        }

        if (bindingResult.hasErrors()) {
            List<SectorDto> sectorOptions = sectorsService.findAll();
            model.addAttribute("sectorOptions", sectorOptions);
            return "index";
        }
        redirectAttributes.addFlashAttribute("message", "User updated successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/";
    }

}

package ee.demo.sectorSelector.security;

import ee.demo.sectorSelector.models.UserDto;
import ee.demo.sectorSelector.models.UserViewDto;
import ee.demo.sectorSelector.persistence.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserDao userDao;

    public void validateAllowed(UserViewDto userView) {
        UserDto loggedInUser = getLoggedInUser();
        if(!Objects.equals(loggedInUser.getId(), userView.getUserId())) {
            throw new AccessDeniedException("Access to user denied");
        }
    }

    private UserDto getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userDao.findByUsername(username);
    }
}

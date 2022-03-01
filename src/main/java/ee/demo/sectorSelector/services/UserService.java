package ee.demo.sectorSelector.services;

import ee.demo.sectorSelector.models.UserCreateDto;
import ee.demo.sectorSelector.models.UserDto;
import ee.demo.sectorSelector.models.UserViewDto;
import ee.demo.sectorSelector.persistence.UserDao;
import ee.demo.sectorSelector.security.UserLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final UserLoader userLoader;

    @Transactional
    public void save(UserCreateDto createDto) {
        UserDto user = UserDto.builder()
                .username(createDto.getUsername())
                .password(new BCryptPasswordEncoder().encode(createDto.getPassword()))
                .role(UserDto.UserRole.ROLE_USER)
                .termsAgreed(false)
                .build();
        userDao.insert(user);
    }

    @Transactional
    public void update(UserViewDto userViewDto) {
        UserDto user = findById(userViewDto.getUserId());
        if (!Objects.equals(user.getUsername(), userViewDto.getUsername())) {
            validateUsernameChange(userViewDto.getUsername());
            user.setUsername(userViewDto.getUsername());
        }
        user.setTermsAgreed(userViewDto.isTermsAgreed());
        userDao.update(user);
        userLoader.updateContextUser(user);
    }

    private void validateUsernameChange(String newUsername) {
        if (nonNull(findByUsername(newUsername))) {
            throw new DuplicateKeyException("Username already exists");
        }
    }

    public UserDto findById(Long id) {
        return userDao.findById(id);
    }

    public UserDto findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public UserDto getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByUsername(username);
    }
}

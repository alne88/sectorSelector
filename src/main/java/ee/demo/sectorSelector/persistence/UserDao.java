package ee.demo.sectorSelector.persistence;

import ee.demo.sectorSelector.models.UserDto;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    void insert(UserDto user);

    void update(UserDto user);

    UserDto findByUsername(String username);

    UserDto findById(Long id);
}

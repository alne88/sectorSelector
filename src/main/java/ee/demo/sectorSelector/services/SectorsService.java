package ee.demo.sectorSelector.services;

import ee.demo.sectorSelector.models.SectorDto;
import ee.demo.sectorSelector.models.UserViewDto;
import ee.demo.sectorSelector.persistence.SectorDao;
import ee.demo.sectorSelector.security.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectorsService {

    private final SectorDao sectorDao;
    private final UserValidator validator;

    public List<SectorDto> findAll() {
        return sectorDao.findAll();
    }

    public List<String> findUserSectors(Long userId ) {
        return sectorDao.findByUserId(userId);
    }

    @Transactional
    public void insertUserSectors(Long userId, String sectorCode) {
        sectorDao.insertUserSectors(userId, sectorCode);
    }

    @Transactional
    public void deleteUserSectors(Long userId) {
        sectorDao.deleteByUserId(userId);
    }

    @Transactional
    public void upsertUserSectors(UserViewDto user) {
        Long userId = user.getUserId();
        List<String> sectors = user.getSectors();

        List<String> userCurrentSectors = sectorDao.findByUserId(userId);
        if (!userCurrentSectors.isEmpty()) {
            deleteUserSectors(userId);
        }
        sectors.forEach(sector -> insertUserSectors(userId, sector));
    }

}

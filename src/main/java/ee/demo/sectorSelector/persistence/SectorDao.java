package ee.demo.sectorSelector.persistence;

import ee.demo.sectorSelector.models.SectorDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorDao {

    List<SectorDto> findAll();

    List<SectorDto> findByCode(String code);

    List<String> findByUserId(Long userId);

    void insertUserSectors(Long userId, String sectorCode);

    void deleteByUserId(Long userId);
}

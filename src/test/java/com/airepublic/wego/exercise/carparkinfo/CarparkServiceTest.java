package com.airepublic.wego.exercise.carparkinfo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;

import com.airepublic.wego.exercise.carparkinfo.service.CarparkAvailabilityDTO;
import com.airepublic.wego.exercise.carparkinfo.service.CarparkAvailabilityEntity;
import com.airepublic.wego.exercise.carparkinfo.service.CarparkAvailabilityRepository;
import com.airepublic.wego.exercise.carparkinfo.service.CarparkInfoEntity;
import com.airepublic.wego.exercise.carparkinfo.service.CarparkService;

@SpringBootTest
public class CarparkServiceTest {

    @Autowired
    private CarparkService service;

    @MockBean
    private CarparkAvailabilityRepository repository;

    @Test
    public void testCorrectEntityMappingToDTO() {
        // prepare
        final List<CarparkAvailabilityEntity> entities = new ArrayList<>();
        entities.add(new CarparkAvailabilityEntity("xxx", 321, 123, new CarparkInfoEntity("xxx", "BLK 364 / 365 UPP SERANGOON RD", 1.37011, 103.897)));

        when(repository.findTableCarparkAvailabilityByLocation(anyDouble(), anyDouble(), anyInt(), any(Pageable.class))).thenReturn(entities);

        // perform
        final List<CarparkAvailabilityDTO> dtos = service.getAvailableCarparks(123, 123, 123, 123);
        assertThat(dtos).isNotEmpty();
        assertThat(dtos.size()).isEqualTo(1);
        assertThat(dtos.get(0).getAddress()).isEqualTo("BLK 364 / 365 UPP SERANGOON RD");
        assertThat(dtos.get(0).getLatitude()).isEqualTo(1.37011);
        assertThat(dtos.get(0).getLongitude()).isEqualTo(103.897);
        assertThat(dtos.get(0).getTotalLots()).isEqualTo(321);
        assertThat(dtos.get(0).getAvailableLots()).isEqualTo(123);
    }
}

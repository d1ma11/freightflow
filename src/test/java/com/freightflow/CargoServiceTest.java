package com.freightflow;

import com.freightflow.model.Cargo;
import com.freightflow.model.Cargo.CargoType;
import com.freightflow.repository.CargoRepository;
import com.freightflow.repository.ShipmentRepository;
import com.freightflow.service.CargoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CargoService.
 */
@RunWith(MockitoJUnitRunner.class)
public class CargoServiceTest {

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private CargoService cargoService;

    private Cargo testCargo;

    @Before
    public void setUp() {
        testCargo = new Cargo("Test Cargo", 100.0, 1.5, CargoType.GENERAL);
        testCargo.setId(1L);
    }

    @Test
    public void testGetAllCargos() {
        List<Cargo> cargos = Collections.singletonList(testCargo);
        when(cargoRepository.findAll()).thenReturn(cargos);

        List<Cargo> result = cargoService.getAllCargos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Cargo", result.get(0).getName());
        verify(cargoRepository, times(1)).findAll();
    }

    @Test
    public void testGetCargoById() {
        when(cargoRepository.findById(1L)).thenReturn(Optional.of(testCargo));

        Cargo result = cargoService.getCargoById(1L);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals("Test Cargo", result.getName());
        verify(cargoRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateCargo() {
        when(cargoRepository.save(any(Cargo.class))).thenReturn(testCargo);

        Cargo result = cargoService.createCargo(testCargo);

        assertNotNull(result);
        assertEquals("Test Cargo", result.getName());
        verify(cargoRepository, times(1)).save(any(Cargo.class));
    }

    @Test
    public void testGetCargosByType() {
        List<Cargo> cargos = Collections.singletonList(testCargo);
        when(cargoRepository.findByType(CargoType.GENERAL)).thenReturn(cargos);

        List<Cargo> result = cargoService.getCargosByType(CargoType.GENERAL);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(CargoType.GENERAL, result.get(0).getType());
        verify(cargoRepository, times(1)).findByType(CargoType.GENERAL);
    }
}

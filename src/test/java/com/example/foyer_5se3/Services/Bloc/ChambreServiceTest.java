package com.example.foyer_5se3.Services.Bloc;

import com.example.foyer_5se3.DAO.Entities.Bloc;
import com.example.foyer_5se3.DAO.Entities.Chambre;
import com.example.foyer_5se3.DAO.Entities.Foyer;
import com.example.foyer_5se3.DAO.Entities.TypeChambre;
import com.example.foyer_5se3.DAO.Repositories.BlocRepository;
import com.example.foyer_5se3.DAO.Repositories.ChambreRepository;
import com.example.foyer_5se3.Services.Chambre.ChambreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
 class ChambreServiceTest {
    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private ChambreService chambreService;

    private Chambre chambre;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(101);
        chambre.setTypeC(TypeChambre.SIMPLE);
        chambre.setBloc(new Bloc());
        chambre.getBloc().setNomBloc("Bloc A");
        chambre.getBloc().setFoyer(Foyer.builder().nomFoyer("Foyer A").build());
        chambre.setReservations(new ArrayList<>());
    }

    @Test
    void testAddOrUpdate() {
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Chambre result = chambreService.addOrUpdate(chambre);
        assertNotNull(result);
        assertEquals(chambre.getIdChambre(), result.getIdChambre());
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testFindAll() {
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(chambre);
        when(chambreRepository.findAll()).thenReturn(chambres);
        List<Chambre> result = chambreService.findAll();
        assertEquals(1, result.size());
        assertEquals(chambre.getNumeroChambre(), result.get(0).getNumeroChambre());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById() {
        doNothing().when(chambreRepository).deleteById(anyLong());
        chambreService.deleteById(1L);
        verify(chambreRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete() {
        doNothing().when(chambreRepository).delete(any(Chambre.class));
        chambreService.delete(chambre);
        verify(chambreRepository, times(1)).delete(chambre);
    }

    @Test
    void testGetChambresParNomBloc() {
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(chambre);
        when(chambreRepository.findByBlocNomBloc("Bloc A")).thenReturn(chambres);
        List<Chambre> result = chambreService.getChambresParNomBloc("Bloc A");
        assertEquals(1, result.size());
        verify(chambreRepository, times(1)).findByBlocNomBloc("Bloc A");
    }

    @Test
    void testNbChambreParTypeEtBloc() {
        when(chambreRepository.countByTypeCAndBlocIdBloc(any(), anyLong())).thenReturn(5);
        long result = chambreService.nbChambreParTypeEtBloc(TypeChambre.SIMPLE, 1L);
        assertEquals(5L, result);
        verify(chambreRepository, times(1)).countByTypeCAndBlocIdBloc(TypeChambre.SIMPLE, 1L);
    }

    @Test
    void testGetChambresNonReserveParNomFoyerEtTypeChambre() {
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(chambre);
        when(chambreRepository.findAll()).thenReturn(chambres);
        List<Chambre> result = chambreService.getChambresNonReserveParNomFoyerEtTypeChambre("Foyer A", TypeChambre.SIMPLE);
        assertEquals(1, result.size());
        verify(chambreRepository, times(1)).findAll();
    }

}
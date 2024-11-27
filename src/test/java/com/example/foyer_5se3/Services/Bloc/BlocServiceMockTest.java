package com.example.foyer_5se3.Services.Bloc;

import com.example.foyer_5se3.DAO.Entities.Bloc;
import com.example.foyer_5se3.DAO.Entities.Chambre;
import com.example.foyer_5se3.DAO.Entities.Foyer;
import com.example.foyer_5se3.DAO.Repositories.BlocRepository;
import com.example.foyer_5se3.DAO.Repositories.ChambreRepository;
import com.example.foyer_5se3.DAO.Repositories.FoyerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class BlocServiceMockTest {

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private BlocService blocService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testAddOrUpdate() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc B");
        bloc.setCapaciteBloc(10);

        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        Bloc savedBloc = blocService.addOrUpdate(bloc);

        assertNotNull(savedBloc);
        assertEquals("Bloc B", savedBloc.getNomBloc());
        assertEquals(10, savedBloc.getCapaciteBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
     void testFindById() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        Bloc foundBloc = blocService.findById(1L);

        assertNotNull(foundBloc);
        assertEquals("Bloc A", foundBloc.getNomBloc());
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
     void testDeleteById() {
        doNothing().when(blocRepository).deleteById(1L);

        blocService.deleteById(1L);

        verify(blocRepository, times(1)).deleteById(1L);
    }

    @Test
     void testAffecterChambresABloc() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        when(blocRepository.findByNomBloc("Bloc A")).thenReturn(bloc);

        Chambre chambre1 = new Chambre();
        chambre1.setNumeroChambre(101L);
        Chambre chambre2 = new Chambre();
        chambre2.setNumeroChambre(102L);
        when(chambreRepository.findByNumeroChambre(101L)).thenReturn(chambre1);
        when(chambreRepository.findByNumeroChambre(102L)).thenReturn(chambre2);

        Bloc updatedBloc = blocService.affecterChambresABloc(Arrays.asList(101L, 102L), "Bloc A");

        assertNotNull(updatedBloc);
        assertEquals("Bloc A", updatedBloc.getNomBloc());
        verify(chambreRepository, times(1)).save(chambre1);
        verify(chambreRepository, times(1)).save(chambre2);
    }

    @Test
     void testAffecterBlocAFoyer() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Foyer 1");

        when(blocRepository.findByNomBloc("Bloc A")).thenReturn(bloc);
        when(foyerRepository.findByNomFoyer("Foyer 1")).thenReturn(foyer);
        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc updatedBloc = blocService.affecterBlocAFoyer("Bloc A", "Foyer 1");

        assertNotNull(updatedBloc);
        assertEquals(foyer, updatedBloc.getFoyer());
        verify(blocRepository, times(1)).save(bloc);
    }
}


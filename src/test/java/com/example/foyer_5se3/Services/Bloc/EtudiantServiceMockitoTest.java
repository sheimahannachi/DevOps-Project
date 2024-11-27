package com.example.foyer_5se3.Services.Bloc;

import com.example.foyer_5se3.DAO.Entities.Etudiant;
import com.example.foyer_5se3.DAO.Repositories.EtudiantRepository;
import com.example.foyer_5se3.Services.Etudiant.EtudiantService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class EtudiantServiceMockitoTest {

    @InjectMocks

    private EtudiantService etudiantService;


    @Mock
    private EtudiantRepository etudiantRepository;


    private Etudiant etudiant;

    @BeforeEach
    public void setUp() {
        // Set up an Etudiant entity for tests
        etudiant = Etudiant.builder()
                .nomEt("Ghmougui")
                .prenomEt("Ons")
                .cin(12345678)
                .ecole("Esprit")
                .dateNaissance(LocalDate.of(2000, 1, 1))
                .build();
    }

    @Test
    void testAddOrUpdate() {
        Mockito.when(etudiantRepository.save(Mockito.any(Etudiant.class))).thenReturn(etudiant);

        Etudiant savedEtudiant = etudiantService.addOrUpdate(etudiant);

        assertNotNull(savedEtudiant);
        assertEquals("Ghmougui", savedEtudiant.getNomEt());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testFindAll() {
        List<Etudiant> mockEtudiants = List.of(
                Etudiant.builder().nomEt("Ghmougui0").cin(12345678).build(),
                Etudiant.builder().nomEt("Ghmougui1").cin(12345679).build()
        );

        Mockito.when(etudiantRepository.findAll()).thenReturn(mockEtudiants);

        List<Etudiant> etudiants = etudiantService.findAll();

        assertEquals(2, etudiants.size());
        assertEquals("Ghmougui0", etudiants.get(0).getNomEt());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Mockito.when(etudiantRepository.findById(etudiant.getIdEtudiant())).thenReturn(Optional.of(etudiant));

        Etudiant foundEtudiant = etudiantService.findById(etudiant.getIdEtudiant());

        assertNotNull(foundEtudiant);
        assertEquals("Ghmougui", foundEtudiant.getNomEt());
        verify(etudiantRepository, times(1)).findById(etudiant.getIdEtudiant());
    }

    @Test
    void testFindById_NotFound() {
        long nonExistentId = 999L;
        Mockito.when(etudiantRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            etudiantService.findById(nonExistentId);
        });

        assertEquals("Etudiant with id " + nonExistentId + " not found", exception.getMessage());
        verify(etudiantRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void testDeleteById() {
        // Stub only the deletion behavior since findById is unnecessary for this test
        doNothing().when(etudiantRepository).deleteById(etudiant.getIdEtudiant());

        // Call the deleteById method in the service
        etudiantService.deleteById(etudiant.getIdEtudiant());

        // Verify that deleteById was called on the repository
        verify(etudiantRepository, times(1)).deleteById(etudiant.getIdEtudiant());
    }


    @Test
    void testDelete() {
        doNothing().when(etudiantRepository).delete(etudiant);

        etudiantService.delete(etudiant);

        verify(etudiantRepository, times(1)).delete(etudiant);
    }

}
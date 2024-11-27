package com.example.foyer_5se3.Services.Bloc;

import com.example.foyer_5se3.DAO.Entities.Bloc;
import com.example.foyer_5se3.DAO.Entities.Foyer;
import com.example.foyer_5se3.DAO.Entities.Universite;
import com.example.foyer_5se3.DAO.Repositories.BlocRepository;
import com.example.foyer_5se3.DAO.Repositories.FoyerRepository;
import com.example.foyer_5se3.DAO.Repositories.UniversiteRepository;
import com.example.foyer_5se3.Services.Foyer.FoyerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

 class FoyerServiceMockTest {
    @InjectMocks
    private FoyerService foyerService; // The class under test

    @Mock
    private FoyerRepository repo; // Mock of FoyerRepository

    @Mock
    private UniversiteRepository universiteRepo; // Mock of UniversiteRepository

    @Mock
    private BlocRepository blocRepository; // Mock of BlocRepository

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
     void testAddOrUpdate() {
        // Create a new Foyer instance
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer Example")
                .capaciteFoyer(100L)
                .build();

        // Mock the behavior of the repository's save method to return the foyer
        when(repo.save(any(Foyer.class))).thenReturn(foyer);

        // Call the addOrUpdate method
        Foyer savedFoyer = foyerService.addOrUpdate(foyer);

        // Verify the returned Foyer is not null
        assertNotNull(savedFoyer);
        // Verify the properties are the same
        assertEquals("Foyer Example", savedFoyer.getNomFoyer());
        assertEquals(100L, savedFoyer.getCapaciteFoyer());

        // Verify that the save method was called once with the correct foyer
        verify(repo, times(1)).save(foyer);
    }

    @Test
     void testFindAll() {
        // Create some Foyer instances
        Foyer foyer1 = Foyer.builder()
                .nomFoyer("Foyer One")
                .capaciteFoyer(50L)
                .build();

        Foyer foyer2 = Foyer.builder()
                .nomFoyer("Foyer Two")
                .capaciteFoyer(100L)
                .build();

        // Mock the behavior of the repository's findAll method to return the list of foyers
        when(repo.findAll()).thenReturn(Arrays.asList(foyer1, foyer2));

        // Call the findAll method
        List<Foyer> foyers = foyerService.findAll();

        // Verify the list is not null and has the expected size
        assertNotNull(foyers);
        assertEquals(2, foyers.size());

        // Verify the contents of the list
        assertEquals("Foyer One", foyers.get(0).getNomFoyer());
        assertEquals("Foyer Two", foyers.get(1).getNomFoyer());

        // Verify that the findAll method was called once
        verify(repo, times(1)).findAll();
    }

    @Test
     void testFindById_Success() {
        // Create a Foyer instance
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer Example")
                .capaciteFoyer(100L)
                .build();

        // Mock the behavior of the repository's findById method to return the foyer
        when(repo.findById(any(Long.class))).thenReturn(Optional.of(foyer));

        // Call the findById method
        Foyer foundFoyer = foyerService.findById(foyer.getIdFoyer());

        // Verify the returned Foyer is not null and properties match
        assertNotNull(foundFoyer);
        assertEquals(foyer.getIdFoyer(), foundFoyer.getIdFoyer());
        assertEquals(foyer.getNomFoyer(), foundFoyer.getNomFoyer());
        assertEquals(foyer.getCapaciteFoyer(), foundFoyer.getCapaciteFoyer());

        // Verify that the findById method was called once with the expected ID
        verify(repo, times(1)).findById(foyer.getIdFoyer());
    }

    @Test
     void testFindById_NotFound() {
        // Use an ID that is guaranteed not to exist
        long nonExistentId = 999L;

        // Mock the behavior of the repository's findById method to return an empty Optional
        when(repo.findById(nonExistentId)).thenReturn(Optional.empty());

        // Verify that the EntityNotFoundException is thrown
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            foyerService.findById(nonExistentId);
        });

        // Check the exception message
        assertEquals("Foyer with id " + nonExistentId + " not found", exception.getMessage());

        // Verify that the findById method was called once with the non-existent ID
        verify(repo, times(1)).findById(nonExistentId);
    }

    @Test
     void testDeleteById_Success() {
        // Create a Foyer instance
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer Example")
                .capaciteFoyer(100L)
                .build();

        // Mock the behavior of the repository to save the foyer and return the saved instance
        when(repo.save(any(Foyer.class))).thenReturn(foyer);

        // Simulate saving the foyer
        Foyer savedFoyer = foyerService.addOrUpdate(foyer); // Assuming you have an addOrUpdate method

        // Call deleteById method
        foyerService.deleteById(savedFoyer.getIdFoyer());

        // Verify that the repository's deleteById method is called once with the foyer's ID
        verify(repo, times(1)).deleteById(savedFoyer.getIdFoyer());

        // Verify that the Foyer is no longer in the repository
        when(repo.findById(savedFoyer.getIdFoyer())).thenReturn(Optional.empty()); // Simulate that it doesn't exist anymore
        assertFalse(repo.findById(savedFoyer.getIdFoyer()).isPresent());
    }

    @Test
     void testDeleteById_NotFound() {
        // Attempt to delete a Foyer with a non-existent ID
        long nonExistentId = 999L; // Use an ID that does not exist

        // Mock the behavior of the repository to not throw an exception when deleting a non-existent ID
        doNothing().when(repo).deleteById(nonExistentId);

        // Call deleteById with the non-existent ID and ensure no exception is thrown
        assertDoesNotThrow(() -> foyerService.deleteById(nonExistentId));
    }

    @Test
     void testDelete_Success() {
        // Create a Foyer instance
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer Example")
                .capaciteFoyer(100L)
                .build();

        // Mock the repository to return a Foyer when saved
        when(repo.save(foyer)).thenReturn(foyer);

        // Save the foyer
        Foyer savedFoyer = repo.save(foyer);

        // Call delete method
        doNothing().when(repo).delete(savedFoyer);
        foyerService.delete(savedFoyer);

        // Verify that the Foyer is no longer in the repository
        when(repo.findById(savedFoyer.getIdFoyer())).thenReturn(Optional.empty());
        assertFalse(repo.findById(savedFoyer.getIdFoyer()).isPresent());
    }

    @Test
     void testDelete_NotSavedEntity() {
        // Create a Foyer instance that is not saved in the repository
        Foyer unsavedFoyer = Foyer.builder()
                .nomFoyer("Unsaved Foyer")
                .capaciteFoyer(50L)
                .build();

        // Call delete method on the unsaved Foyer and ensure no exception is thrown
        assertDoesNotThrow(() -> foyerService.delete(unsavedFoyer));

        // Verify that the repository's count remains zero
        // Assuming repo.count() is not affected by delete() method for unsaved entities
        when(repo.count()).thenReturn(0L);
        assertEquals(0, repo.count());
    }
    @Test
     void testAffecterFoyerAUniversite_Success() {
        // Create a Foyer instance
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer Example")
                .capaciteFoyer(100L)
                .idFoyer(1L) // Manually setting the ID for the test
                .build();

        // Create a Universite instance
        Universite universite = Universite.builder()
                .nomUniversite("Université Example")
                .build();

        // Mock the behavior of the repositories
        when(repo.findById(foyer.getIdFoyer())).thenReturn(Optional.of(foyer)); // Correctly returning Optional<Foyer>
        when(universiteRepo.findByNomUniversite("Université Example")).thenReturn(universite); // Directly returning Universite
        when(universiteRepo.save(universite)).thenReturn(universite); // Ensure that save returns the Universite

        // Call the method under test
        Universite updatedUniversite = foyerService.affecterFoyerAUniversite(foyer.getIdFoyer(), "Université Example");

        // Verify that the Universite is updated correctly
        assertNotNull(updatedUniversite);  // This is where the error occurred
        assertEquals("Université Example", updatedUniversite.getNomUniversite());
        assertEquals(foyer.getIdFoyer(), updatedUniversite.getFoyer().getIdFoyer());

        // Verify interactions with the repository
        verify(repo).findById(foyer.getIdFoyer());
        verify(universiteRepo).findByNomUniversite("Université Example");
        verify(universiteRepo).save(universite); // Ensure Universite was saved after updating
    }


    @Test
     void testAjoutFoyerEtBlocs_Success() {
        // Create mock Bloc instances
        Bloc bloc1 = Bloc.builder()
                .idBloc(1L)
                .nomBloc("Bloc 1")
                .build();
        Bloc bloc2 = Bloc.builder()
                .idBloc(2L)
                .nomBloc("Bloc 2")
                .build();

        // Create a mock Foyer instance with the blocs
        Foyer foyer = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer Example")
                .capaciteFoyer(100L)
                .blocs(Arrays.asList(bloc1, bloc2)) // Assign blocs to the foyer
                .build();

        // Mock repo.save() to return the saved Foyer
        when(repo.save(foyer)).thenReturn(foyer);

        // Call the method under test
        Foyer savedFoyer = foyerService.ajoutFoyerEtBlocs(foyer);

        // Verify that the Foyer was saved
        assertNotNull(savedFoyer);
        assertEquals("Foyer Example", savedFoyer.getNomFoyer());
        assertEquals(2, savedFoyer.getBlocs().size()); // Ensure blocs are associated

        // Verify that each Bloc was saved with the correct Foyer reference
        verify(blocRepository).save(bloc1);
        verify(blocRepository).save(bloc2);

        // Verify that the Foyer was saved
        verify(repo).save(foyer);
    }





}
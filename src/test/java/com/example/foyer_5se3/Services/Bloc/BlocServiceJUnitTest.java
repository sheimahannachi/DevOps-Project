package com.example.foyer_5se3.Services.Bloc;

import com.example.foyer_5se3.DAO.Entities.Bloc;
import com.example.foyer_5se3.DAO.Entities.Chambre;
import com.example.foyer_5se3.DAO.Entities.Foyer;
import com.example.foyer_5se3.DAO.Repositories.BlocRepository;
import com.example.foyer_5se3.DAO.Repositories.ChambreRepository;
import com.example.foyer_5se3.DAO.Repositories.FoyerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test") // Loads application-test.properties
class BlocServiceJUnitTest {

    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private FoyerRepository foyerRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    private BlocService blocService;

    @BeforeEach
    void setUp() {
        blocService = new BlocService(blocRepository, chambreRepository, blocRepository, foyerRepository);
        assertThat(blocService).isNotNull();
        assertThat(blocRepository).isNotNull();
        assertThat(chambreRepository).isNotNull();
        assertThat(foyerRepository).isNotNull();
    }

    @Test
    void addOrUpdate2_shouldSaveBlocWithChambres() {
        // Prepare a Foyer
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer Test")
                .build();
        foyerRepository.save(foyer);

        // Prepare a Bloc with a list of Chambres
        Bloc bloc = Bloc.builder()
                .nomBloc("Bloc Test 2")
                .capaciteBloc(150)
                .foyer(foyer) // Associate Foyer with Bloc
                .build();

        Chambre chambre1 = Chambre.builder().numeroChambre(101).build();
        Chambre chambre2 = Chambre.builder().numeroChambre(102).build();
        bloc.setChambres(List.of(chambre1, chambre2));

        // Save the Bloc using addOrUpdate2 method (cascade save)
        Bloc savedBloc = blocService.addOrUpdate2(bloc);

        // Assertions
        assertThat(savedBloc).isNotNull();
        assertThat(savedBloc.getIdBloc()).isPositive();
        assertThat(savedBloc.getNomBloc()).isEqualTo("Bloc Test 2");
        assertThat(savedBloc.getChambres()).isNotEmpty(); // Check that chambres are saved
    }

    @Test
    void findById_shouldReturnBlocWhenExists() {
        // Prepare and save a Bloc
        Bloc bloc = Bloc.builder()
                .nomBloc("Bloc Test")
                .capaciteBloc(100)
                .build();
        blocRepository.save(bloc);

        // Find the Bloc using the service
        Bloc foundBloc = blocService.findById(bloc.getIdBloc());

        // Assertions
        assertThat(foundBloc).isNotNull();
        assertThat(foundBloc.getIdBloc()).isEqualTo(bloc.getIdBloc());
    }

    @Test
    void deleteById_shouldDeleteBloc() {
        // Prepare and save a Bloc
        Bloc bloc = Bloc.builder()
                .nomBloc("Bloc to Delete")
                .capaciteBloc(50)
                .build();
        blocRepository.save(bloc);

        // Ensure the Bloc is saved and has an ID
        assertThat(bloc.getIdBloc()).isPositive();

        // Delete the Bloc using the service
        blocService.deleteById(bloc.getIdBloc());

        // Verify deletion by trying to fetch it
        Bloc deletedBloc = blocRepository.findById(bloc.getIdBloc()).orElse(null);
        assertThat(deletedBloc).isNull(); // Assert that the Bloc is deleted and no longer exists
    }

    @Test
    void affecterBlocAFoyer_shouldAssignBlocToFoyer() {
        // Prepare and save a Foyer
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer Test Affectation")
                .build();
        foyerRepository.save(foyer);

        // Prepare and save a Bloc
        Bloc bloc = Bloc.builder()
                .nomBloc("Bloc Test Affectation")
                .capaciteBloc(100)
                .build();
        blocRepository.save(bloc);

        // Assign Bloc to the Foyer
        Bloc updatedBloc = blocService.affecterBlocAFoyer("Bloc Test Affectation", "Foyer Test Affectation");

        // Assertions
        assertThat(updatedBloc).isNotNull();
        assertThat(updatedBloc.getFoyer()).isNotNull();
        assertThat(updatedBloc.getFoyer().getNomFoyer()).isEqualTo("Foyer Test Affectation");
    }

    @Test
    void affecterChambresABloc_shouldAssignChambresToBloc() {
        // Prepare and save a Foyer
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer Test Affectation Chambres")
                .build();
        foyerRepository.save(foyer);

        // Prepare and save a Bloc
        Bloc bloc = Bloc.builder()
                .nomBloc("Bloc Test Chambres")
                .capaciteBloc(150)
                .foyer(foyer) // Associate Foyer with Bloc
                .build();
        blocRepository.save(bloc);

        // Prepare some Chambres
        Chambre chambre1 = Chambre.builder().numeroChambre(101).build();
        Chambre chambre2 = Chambre.builder().numeroChambre(102).build();
        Chambre chambre3 = Chambre.builder().numeroChambre(103).build();

        // Save Chambres
        chambreRepository.save(chambre1);
        chambreRepository.save(chambre2);
        chambreRepository.save(chambre3);

        // Assign Chambres to the Bloc using affecterChambresABloc
        List<Long> chambreNumbers = List.of(101L, 102L, 103L);
        blocService.affecterChambresABloc(chambreNumbers, "Bloc Test Chambres");

        // Fetch the updated Chambres
        Chambre updatedChambre1 = chambreRepository.findByNumeroChambre(101L);
        Chambre updatedChambre2 = chambreRepository.findByNumeroChambre(102L);
        Chambre updatedChambre3 = chambreRepository.findByNumeroChambre(103L);

        // Assertions
        assertThat(updatedChambre1).isNotNull();
        assertThat(updatedChambre1.getBloc()).isNotNull();
        assertThat(updatedChambre1.getBloc().getNomBloc()).isEqualTo("Bloc Test Chambres");

        assertThat(updatedChambre2).isNotNull();
        assertThat(updatedChambre2.getBloc()).isNotNull();
        assertThat(updatedChambre2.getBloc().getNomBloc()).isEqualTo("Bloc Test Chambres");

        assertThat(updatedChambre3).isNotNull();
        assertThat(updatedChambre3.getBloc()).isNotNull();
        assertThat(updatedChambre3.getBloc().getNomBloc()).isEqualTo("Bloc Test Chambres");
    }
}

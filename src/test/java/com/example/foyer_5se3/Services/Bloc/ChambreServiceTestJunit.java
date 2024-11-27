package com.example.foyer_5se3.Services.Bloc;

import com.example.foyer_5se3.DAO.Entities.Bloc;
import com.example.foyer_5se3.DAO.Entities.Chambre;
import com.example.foyer_5se3.DAO.Entities.Foyer;
import com.example.foyer_5se3.DAO.Entities.TypeChambre;
import com.example.foyer_5se3.DAO.Repositories.BlocRepository;
import com.example.foyer_5se3.DAO.Repositories.ChambreRepository;
import com.example.foyer_5se3.DAO.Repositories.FoyerRepository;
import com.example.foyer_5se3.Services.Chambre.ChambreService;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
 class ChambreServiceTestJunit {




    ChambreService chambreService;

    @Autowired
    BlocRepository blocRepository;

    @Autowired
    ChambreRepository chambreRepository;

    @Autowired
    FoyerRepository foyerRepository;

    @BeforeEach
    void setUp() {
        chambreService=new ChambreService(chambreRepository,blocRepository);
        assertThat(chambreService).isNotNull();
        assertThat(blocRepository).isNotNull();
        assertThat(chambreRepository).isNotNull();
    }

    @Test
    void addOrUpdate_shouldSaveChambre() {
        Bloc bloc = Bloc.builder().nomBloc("aa").build();
        blocRepository.save(bloc);

        Chambre chambre = Chambre.builder().numeroChambre(103)
                .typeC(TypeChambre.TRIPLE)
                .bloc(bloc).build();


        Chambre savedChambre = chambreService.addOrUpdate(chambre);
        assertThat(savedChambre).isNotNull();
    }




    @Test
    void getChambresNonReserveParNomFoyerEtTypeChambre_shouldReturnOnlyAvailableRooms() {

        Foyer foyer= foyerRepository.save(Foyer.builder().nomFoyer("Foyer A").build());

        Bloc bloc = Bloc.builder().nomBloc("Bloc A").foyer(foyer).build();
        blocRepository.save(bloc);

        Chambre chambre1 = Chambre.builder().idChambre(1L).numeroChambre(101).typeC(TypeChambre.SIMPLE).bloc(bloc).reservations(new ArrayList<>()).build();
        Chambre chambre2 = Chambre.builder().idChambre(2L).numeroChambre(102).typeC(TypeChambre.DOUBLE).bloc(bloc).reservations(new ArrayList<>()).build();
        Chambre chambre3 = Chambre.builder().idChambre(3L).numeroChambre(103).typeC(TypeChambre.TRIPLE).bloc(bloc).reservations(new ArrayList<>()).build();
        chambreRepository.saveAll(List.of(chambre1, chambre2, chambre3));


        List<Chambre> result = chambreService.getChambresNonReserveParNomFoyerEtTypeChambre("Foyer A", TypeChambre.SIMPLE);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getIdChambre()).isEqualTo(chambre1.getIdChambre());




    }


}
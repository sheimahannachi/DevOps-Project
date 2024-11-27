package com.example.foyer_5se3.Services.Bloc;

import com.example.foyer_5se3.DAO.Entities.*;
import com.example.foyer_5se3.DAO.Repositories.ChambreRepository;
import com.example.foyer_5se3.DAO.Repositories.EtudiantRepository;
import com.example.foyer_5se3.DAO.Repositories.ReservationRepository;
import com.example.foyer_5se3.Services.Reservation.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceMockTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Chambre chambre;
    private Etudiant etudiant;
    private Bloc bloc;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");

        chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(101L);
        chambre.setTypeC(TypeChambre.SIMPLE);
        chambre.setBloc(bloc);

        etudiant = new Etudiant();
        etudiant.setCin(12345678L);
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");

        reservation = new Reservation();
        reservation.setIdReservation("2024-101-12345678");
        reservation.setAnneeUniversitaire(LocalDate.now());
        reservation.setEstValide(true);
        reservation.getEtudiants().add(etudiant);
    }

    @Test
    void testAddOrUpdate() {
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation savedReservation = reservationService.addOrUpdate(reservation);

        assertNotNull(savedReservation);
        assertEquals(reservation.getIdReservation(), savedReservation.getIdReservation());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testFindAll() {
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));

        List<Reservation> reservations = reservationService.findAll();

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals("2024-101-12345678", reservations.get(0).getIdReservation());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(reservationRepository.findById(reservation.getIdReservation())).thenReturn(Optional.of(reservation));

        Reservation foundReservation = reservationService.findById(reservation.getIdReservation());

        assertNotNull(foundReservation);
        assertEquals(reservation.getIdReservation(), foundReservation.getIdReservation());
        verify(reservationRepository, times(1)).findById(reservation.getIdReservation());
    }



    @Test
    void testDeleteById() {
        reservationService.deleteById(reservation.getIdReservation());

        verify(reservationRepository, times(1)).deleteById(reservation.getIdReservation());
    }

    @Test
    void testAjouterReservationEtAssignerAChambreEtAEtudiant() {
        when(chambreRepository.findByNumeroChambre(101L)).thenReturn(chambre);
        when(etudiantRepository.findByCin(12345678L)).thenReturn(etudiant);
        when(chambreRepository.countReservationsByIdChambreAndReservationsAnneeUniversitaireBetween(anyLong(), any(), any())).thenReturn(0);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation addedReservation = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(101L, 12345678L);

        assertNotNull(addedReservation);
        assertEquals("2024-101-12345678", addedReservation.getIdReservation());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(chambreRepository, times(1)).findByNumeroChambre(101L);
        verify(etudiantRepository, times(1)).findByCin(12345678L);
    }

    @Test
    void testGetReservationParAnneeUniversitaire() {
        LocalDate startAcademicYear = LocalDate.of(LocalDate.now().getYear(), 9, 15);
        LocalDate endAcademicYear = LocalDate.of(LocalDate.now().plusYears(1).getYear(), 6, 30);

        when(reservationRepository.countByAnneeUniversitaireBetween(startAcademicYear, endAcademicYear)).thenReturn(1);

        long count = reservationService.getReservationParAnneeUniversitaire(startAcademicYear, endAcademicYear);

        assertEquals(1, count);
        verify(reservationRepository, times(1)).countByAnneeUniversitaireBetween(startAcademicYear, endAcademicYear);
    }
}
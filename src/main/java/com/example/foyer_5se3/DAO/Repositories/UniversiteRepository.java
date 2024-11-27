package com.example.foyer_5se3.DAO.Repositories;


import com.example.foyer_5se3.DAO.Entities.Universite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UniversiteRepository extends JpaRepository<Universite, Long> {
    Universite findByNomUniversite(String nomUniversite);
    // Afficher la liste des universités qui ont des étudiants dont leurs noms contiennet
    // la chaine de caractère en paramètre et leurs dates de naissance entre deux dates
    // passées en paramètre
    List<Universite> findByFoyerBlocsChambresReservationsEtudiantsNomEtLikeAndFoyerBlocsChambresReservationsEtudiantsDateNaissanceBetween(String nom, LocalDate date1, LocalDate date2);
}

package com.example.foyer_5se3.Services.Foyer;

import com.example.foyer_5se3.DAO.Entities.Etudiant;
import com.example.foyer_5se3.DAO.Entities.Foyer;
import com.example.foyer_5se3.DAO.Entities.Reservation;
import com.example.foyer_5se3.DAO.Entities.Universite;

import java.util.List;

public interface IFoyerService {
    Foyer addOrUpdate(Foyer f);

    List<Foyer> findAll();

    Foyer findById(long id);

    void deleteById(long id);

    void delete(Foyer f);

    Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite);

    Universite desaffecterFoyerAUniversite(long idUniversite);

    Foyer ajouterFoyerEtAffecterAUniversite (Foyer foyer, long idUniversite); // Universite: Parent / Foyer:Child
    Foyer ajoutFoyerEtBlocs(Foyer foyer);


}

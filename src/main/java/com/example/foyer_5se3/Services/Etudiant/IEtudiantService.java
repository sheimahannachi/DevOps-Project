package com.example.foyer_5se3.Services.Etudiant;

import com.example.foyer_5se3.DAO.Entities.Etudiant;

import java.util.List;

public interface IEtudiantService  {
    Etudiant addOrUpdate(Etudiant e);
    List<Etudiant> findAll();
    Etudiant findById(long id);
    void deleteById(long id);
    void delete(Etudiant e);
}

package com.example.foyer_5se3.RestController;



import com.example.foyer_5se3.DAO.Entities.Etudiant;
import com.example.foyer_5se3.Services.Etudiant.IEtudiantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("etudiant")
@AllArgsConstructor
public class EtudiantRestController {
    IEtudiantService service;

    @PostMapping("addOrUpdate")
    Etudiant addOrUpdate(@RequestBody Etudiant e) {
        return service.addOrUpdate(e);
    }

    @GetMapping("findAll")
    List<Etudiant> findAll() {
        return service.findAll();
    }

    @GetMapping("findById")
    Etudiant findById(@RequestParam long id) {
        return service.findById(id);
    }

    @DeleteMapping("delete")
    void delete(@RequestBody Etudiant e) {
        service.delete(e);
    }

    @DeleteMapping("deleteById")
    void deleteById(@RequestParam long id) {
        service.deleteById(id);
    }
}
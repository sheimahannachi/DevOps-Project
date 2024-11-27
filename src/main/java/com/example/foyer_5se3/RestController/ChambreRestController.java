package com.example.foyer_5se3.RestController;


import com.example.foyer_5se3.DAO.Entities.Chambre;
import com.example.foyer_5se3.DAO.Entities.TypeChambre;
import com.example.foyer_5se3.Services.Chambre.IChambreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chambre")
@AllArgsConstructor
public class ChambreRestController {
    IChambreService service;

    @PostMapping("addOrUpdate")
    Chambre addOrUpdate(@RequestBody Chambre c) {
        return service.addOrUpdate(c);
    }

    @GetMapping("findAll")
    List<Chambre> findAll() {
        return service.findAll();
    }

    @GetMapping("findById")
    Chambre findById(@RequestParam long id) {
        return service.findById(id);
    }

    @DeleteMapping("delete")
    void delete(@RequestBody Chambre c) {
        service.delete(c);
    }

    @DeleteMapping("deleteById")
    void deleteById(@RequestParam long id) {
        service.deleteById(id);
    }

    @GetMapping("getChambresParNomBloc")
    public List<Chambre> getChambresParNomBloc(@RequestParam String nomBloc) {
        return service.getChambresParNomBloc(nomBloc);
    }

    @GetMapping("nbChambreParTypeEtBloc")
    long nbChambreParTypeEtBloc(@RequestParam TypeChambre type, @RequestParam long idBloc) {
        return service.nbChambreParTypeEtBloc(type, idBloc);
    }

    @GetMapping("getChambresNonReserveParNomFoyerEtTypeChambre")
    List<Chambre> getChambresNonReserveParNomFoyerEtTypeChambre(@RequestParam String nomFoyer,@RequestParam TypeChambre type){
        return service.getChambresNonReserveParNomFoyerEtTypeChambre(nomFoyer,type);
    }
}

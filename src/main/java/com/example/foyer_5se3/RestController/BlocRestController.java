package com.example.foyer_5se3.RestController;

import com.example.foyer_5se3.DAO.Entities.Bloc;
import com.example.foyer_5se3.Services.Bloc.IBlocService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bloc")
@AllArgsConstructor
public class BlocRestController {

    IBlocService service;

    @PostMapping("addOrUpdate")
    Bloc addOrUpdate(@RequestBody Bloc b) {
        return service.addOrUpdate(b);
    }

    @GetMapping("findAll")
    List<Bloc> findAll() {
        return service.findAll();
    }

    @GetMapping("findById")
    Bloc findById(@RequestParam long id) {
        return service.findById(id);
    }

    @DeleteMapping("delete")
    void delete(@RequestBody Bloc b) {
        service.delete(b);
    }

    @DeleteMapping("deleteById")
    void deleteById(@RequestParam long id) {
        service.deleteById(id);
    }

    @PutMapping("affecterChambresABloc")
    Bloc affecterChambresABloc(@RequestBody List<Long> numChambre, @RequestParam String nomBloc) {
        return service.affecterChambresABloc(numChambre, nomBloc);
    }

    @PutMapping("affecterBlocAFoyer")
    Bloc affecterBlocAFoyer(@RequestParam String nomBloc, @RequestParam String nomFoyer){
        return service.affecterBlocAFoyer(nomBloc,nomFoyer);
    }
}

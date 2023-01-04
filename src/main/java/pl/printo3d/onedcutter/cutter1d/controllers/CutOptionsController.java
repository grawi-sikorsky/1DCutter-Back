package pl.printo3d.onedcutter.cutter1d.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.printo3d.onedcutter.cutter1d.models.project.CutOptions;
import pl.printo3d.onedcutter.cutter1d.services.CutOptionsService;
import pl.printo3d.onedcutter.cutter1d.services.CutOptionsServiceImpl;

import javax.crypto.interfaces.PBEKey;

@RestController
@RequestMapping("/options")
public class CutOptionsController {
    private final CutOptionsService cutOptionsService;

    public CutOptionsController(CutOptionsService cutOptionsService) {
        this.cutOptionsService = cutOptionsService;
    }

    @GetMapping
    public ResponseEntity<CutOptions> getCutOptions(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(cutOptionsService.getCutOptions(Long.valueOf(id)));
    }

    @PostMapping
    public ResponseEntity<CutOptions> addCutOptions(@RequestBody CutOptions cutOptions){
        return ResponseEntity.status(HttpStatus.OK).body(cutOptionsService.addCutOptions(cutOptions));
    }

    @PatchMapping
    public ResponseEntity<CutOptions> editCutOptions(@RequestParam String id, @RequestBody CutOptions cutOptions){
        return ResponseEntity.status(HttpStatus.OK).body(cutOptionsService.editCutOptions(Long.valueOf(id),cutOptions));
    }
}

package pl.printo3d.onedcutter.cutter1d.cutter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultBar;
import pl.printo3d.onedcutter.cutter1d.cutter.models.ResultBarPieceModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.WorkPiece;
import pl.printo3d.onedcutter.cutter1d.cutter.services.OneDCutService;
import pl.printo3d.onedcutter.cutter1d.cutter.services.ResultService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class OneDCutterController {

  @Autowired
  private OneDCutService cutService;

  @Autowired 
  private ResultService resultService;

  @GetMapping("/1dcut")
  public List<WorkPiece> showCuts()
  {
    // W tej metodzie trzeba ustawic cala strone 1DCUT?
    return cutService.firstFit();
  }

  // @PostMapping("/1dcut")
  // public List<List<ResultBarPieceModel>> odertiy()
  // {
  //   return resultService.getResultsBars(cutService.workPiecesList);
  // }

  @GetMapping("/cut")
  public List<ResultBar> cut()
  {
    return resultService.getResultsBars(cutService.workPiecesList);
  }
}

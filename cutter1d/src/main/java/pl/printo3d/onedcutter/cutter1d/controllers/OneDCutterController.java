package pl.printo3d.onedcutter.cutter1d.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.models.StockPiece;
import pl.printo3d.onedcutter.cutter1d.services.OneDCutService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class OneDCutterController {

  @Autowired
  private OneDCutService cutService;

  @GetMapping("/1dcut")
  public List<StockPiece> showCuts()
  {
    return cutService.firstFit();
  }
  
}

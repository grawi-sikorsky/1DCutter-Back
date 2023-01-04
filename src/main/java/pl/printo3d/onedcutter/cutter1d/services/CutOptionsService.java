package pl.printo3d.onedcutter.cutter1d.services;

import pl.printo3d.onedcutter.cutter1d.models.project.CutOptions;

public interface CutOptionsService {

    CutOptions getCutOptions(Long id);

    CutOptions addCutOptions(CutOptions cutOptions);

    CutOptions editCutOptions(Long id, CutOptions cutOptions);
}

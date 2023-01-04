package pl.printo3d.onedcutter.cutter1d.services;

import org.springframework.stereotype.Service;
import pl.printo3d.onedcutter.cutter1d.models.project.CutOptions;
import pl.printo3d.onedcutter.cutter1d.repo.CutOptionsRepository;

@Service
public class CutOptionsServiceImpl implements CutOptionsService {

    private final CutOptionsRepository cutOptionsRepository;

    public CutOptionsServiceImpl(CutOptionsRepository cutOptionsRepository) {
        this.cutOptionsRepository = cutOptionsRepository;
    }

    @Override
    public CutOptions getCutOptions(Long id) {
        return cutOptionsRepository.findById(id).orElseThrow(() -> new RuntimeException("No CutOptions with such id."));
    }

    @Override
    public CutOptions addCutOptions(CutOptions cutOptions) {
        return cutOptionsRepository.save(cutOptions);
    }

    @Override
    public CutOptions editCutOptions(Long id, CutOptions cutOptions) {
        CutOptions optionsToSave = cutOptionsRepository.findById(id).orElseThrow(()-> new RuntimeException("No CutOptions with such id."));
        // mapper to CutOptions..
        optionsToSave.setOptionSzrank(cutOptions.getOptionSzrank() );
        optionsToSave.setOptionPrice(cutOptions.isOptionPrice());
        optionsToSave.setOptionStackResult(cutOptions.isOptionStackResult());
        optionsToSave.setOptionAlgo(cutOptions.isOptionAlgo());
        optionsToSave.setOptionIterations(cutOptions.getOptionIterations());

        return cutOptionsRepository.save(optionsToSave);
    }
}

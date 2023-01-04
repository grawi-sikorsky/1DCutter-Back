package pl.printo3d.onedcutter.cutter1d.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.printo3d.onedcutter.cutter1d.models.project.CutOptions;

public interface CutOptionsRepository extends JpaRepository<CutOptions,Long> {
}

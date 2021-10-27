package pl.printo3d.onedcutter.cutter1d.user.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.printo3d.onedcutter.cutter1d.user.models.UserModel;

@Repository
public interface UserRepo extends CrudRepository<UserModel, Long> {
    
    UserModel findByUsername(String username);
    boolean existsByUsername(String username);
}
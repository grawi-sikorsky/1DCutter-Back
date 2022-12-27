package pl.printo3d.onedcutter.cutter1d.repo;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import pl.printo3d.onedcutter.cutter1d.models.user.UserModel;

public interface UserRepo extends CrudRepository<UserModel, Long> {
    
    UserModel findByUsername(String username);
    boolean existsByUsername(String username);
    UserModel findByUuid(String uuid);

    @Transactional
    void deleteByUuid(String uuid);
}
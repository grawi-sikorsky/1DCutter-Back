package pl.printo3d.onedcutter.cutter1d.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.printo3d.onedcutter.cutter1d.models.project.OrderModel;

@Repository
public interface OrderRepository extends CrudRepository<OrderModel, Long>{

    OrderModel findOrderModelById(Long id);
    OrderModel getById(Long id);
    OrderModel getByIdAndUserId(Long id, Long userId);
    OrderModel findByIdAndUserId(Long id, Long userId);
}
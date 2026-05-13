package com.freightflow.repository;

import com.freightflow.model.Cargo;
import com.freightflow.model.Cargo.CargoType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Cargo entity.
 */
@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    List<Cargo> findByType(CargoType type);
    
    int countByType(CargoType type);
}

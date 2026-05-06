package com.freightflow.repository;

import com.freightflow.model.Carrier;
import com.freightflow.model.Carrier.TransportType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Carrier entity.
 */
@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Long> {

    List<Carrier> findByTransportType(TransportType transportType);
}

package com.castellarin.autorepuestos.repository;

import com.castellarin.autorepuestos.domain.entity.PayerDetails;
import com.castellarin.autorepuestos.domain.entity.PayerDetailsId;import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayerDetailsRepository extends JpaRepository<PayerDetails, PayerDetailsId> {
}

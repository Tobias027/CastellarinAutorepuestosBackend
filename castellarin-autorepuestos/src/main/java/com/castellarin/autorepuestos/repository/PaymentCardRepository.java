package com.castellarin.autorepuestos.repository;

import com.castellarin.autorepuestos.domain.entity.PaymentCard;
import com.castellarin.autorepuestos.domain.entity.PaymentCardId;import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentCardRepository extends JpaRepository<PaymentCard, PaymentCardId> {
}

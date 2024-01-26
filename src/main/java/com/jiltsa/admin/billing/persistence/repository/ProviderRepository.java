package com.jiltsa.admin.billing.persistence.repository;

import com.jiltsa.admin.billing.persistence.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {
}

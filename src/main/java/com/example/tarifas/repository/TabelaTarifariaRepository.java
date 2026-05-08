package com.example.tarifas.repository;
import com.example.tarifas.model.TabelaTarifaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TabelaTarifariaRepository extends JpaRepository<TabelaTarifaria, Long> {

    List<TabelaTarifaria> findByAtivoTrue();

}
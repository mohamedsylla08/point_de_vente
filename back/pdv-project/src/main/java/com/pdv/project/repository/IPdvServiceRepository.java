package com.pdv.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdv.project.model.PdvService;

/**
 * @author MOHAMED LAMINE SYLLA
 *
 */
public interface IPdvServiceRepository extends JpaRepository<PdvService, Integer> {

}

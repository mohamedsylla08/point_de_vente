package com.pdv.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdv.project.model.Carburant;

/**
 * @author MOHAMED LAMINE SYLLA
 *
 */
public interface ICarburantRepository extends JpaRepository<Carburant, Integer> {

}

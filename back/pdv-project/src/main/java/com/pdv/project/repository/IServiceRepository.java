package com.pdv.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pdv.project.model.Service;

/**
 * @author MOHAMED LAMINE SYLLA
 *
 */
public interface IServiceRepository extends JpaRepository<Service, Integer> {

}

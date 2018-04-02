package com.pdv.project.model;
// Generated 29 nov. 2017 15:40:03 by Hibernate Tools 5.2.6.Final

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Service generated by hbm2java
 */
@Entity
@Table(name = "service", schema = "pdv")
@Data
@Builder
@AllArgsConstructor
public class Service {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "code", length = 10)
	private String code;

	@Column(name = "libelle", length = 100)
	private String libelle;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "service")
	private Set<PdvService> pdvServices;

}
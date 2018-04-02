package com.pdv.project.model;
// Generated 29 nov. 2017 15:40:03 by Hibernate Tools 5.2.6.Final

import java.util.Date;
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
 * Ouverture generated by hbm2java
 */
@Entity
@Table(name = "ouverture", schema = "pdv")
@Data
@Builder
@AllArgsConstructor
public class Ouverture {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "debut", nullable = false, length = 50)
	private Date debut;

	//	@Temporal(TemporalType.DATE)
	@Column(name = "fin", nullable = false, length = 13)
	private Date fin;

	@Column(name = "saufjour", length = 255)
	private String saufjour;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ouverture")
	private Set<PointDeVente> pointDeVentes;

}
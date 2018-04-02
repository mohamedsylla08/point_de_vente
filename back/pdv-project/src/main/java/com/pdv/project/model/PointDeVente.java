package com.pdv.project.model;
// Generated 29 nov. 2017 15:40:03 by Hibernate Tools 5.2.6.Final

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * PointDeVente generated by hbm2java
 */
@Entity
@Table(name = "point_de_vente", schema = "pdv", uniqueConstraints = { @UniqueConstraint(columnNames = "adressefk_pdv") })
@Data
@AllArgsConstructor
@Builder
public class PointDeVente {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "identifiant", nullable = false)
	private int identifiant;

	@Column(name = "latitude", precision = 17, scale = 17)
	private Double latitude;

	@Column(name = "longitude", precision = 17, scale = 17)
	private Double longitude;

	@Column(name = "codepostal", length = 10)
	private String codepostal;

	@Column(name = "pop", length = 1)
	private String pop;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "villefk_pdv", nullable = false)
	private Ville ville;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "adressefk_pdv", unique = true, nullable = false)
	private Adresse adresse;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fermeturefk_pdv", nullable = true)
	private Fermeture fermeture;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ouverturefk_pdv", unique = true, nullable = false)
	private Ouverture ouverture;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pointDeVente")
	private Set<PdvService> pdvServices;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pointDeVente")
	private Set<Prix> prixes;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pointDeVente")
	private Set<Rupture> ruptures;

}

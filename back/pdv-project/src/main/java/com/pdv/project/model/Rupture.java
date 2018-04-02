package com.pdv.project.model;
// Generated 29 nov. 2017 15:40:03 by Hibernate Tools 5.2.6.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rupture generated by hbm2java
 */
@Entity
@Table(name = "rupture", schema = "pdv")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rupture implements java.io.Serializable {

	private static final long serialVersionUID = 8026852835771276827L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "identifiant", nullable = false)
	private int identifiant;

	@Column(name = "fuel", length = 20)
	private String fuel;

	@Temporal(TemporalType.DATE)
	@Column(name = "debut", nullable = false, length = 13)
	private Date debut;

	@Temporal(TemporalType.DATE)
	@Column(name = "fin", length = 13)
	private Date fin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pointdeventefk_rupture", nullable = false)
	private PointDeVente pointDeVente;

}

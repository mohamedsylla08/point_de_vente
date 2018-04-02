package com.pdv.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.project.model.Ville;

/**
 * @author MOHAMED LAMINE SYLLA
 *
 */
@Repository
public interface IVillesFranceRepository extends JpaRepository<Ville, Integer> {

	Ville findByVilleCodePostal(String codePostal);

	Ville findFirstByVilleNomIgnoreCase(String villeNomSimple);
	
//	Ville findByVilleNomSimpleContainingIgnoreCase(String villeNomSimple);

}

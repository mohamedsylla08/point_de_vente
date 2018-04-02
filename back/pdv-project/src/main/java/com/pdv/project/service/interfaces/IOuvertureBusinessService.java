package com.pdv.project.service.interfaces;

import org.springframework.stereotype.Service;

import com.pdv.project.model.Ouverture;

/**
 * @author MOHAMED LAMINE SYLLA
 *
 */
@Service
public interface IOuvertureBusinessService {
	
	Ouverture generateOuverture() throws Exception;

}

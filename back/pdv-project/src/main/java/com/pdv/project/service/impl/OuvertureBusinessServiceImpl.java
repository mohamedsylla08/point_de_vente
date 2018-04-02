package com.pdv.project.service.impl;

import com.pdv.project.model.Ouverture;
import com.pdv.project.service.interfaces.IOuvertureBusinessService;

/**
 * @author MOHAMED LAMINE SYLLA
 *
 */
public class OuvertureBusinessServiceImpl implements IOuvertureBusinessService {

	@Override
	public Ouverture generateOuverture() throws Exception {
		Ouverture ouverture = Ouverture.builder().build();
		return ouverture;
	}

}

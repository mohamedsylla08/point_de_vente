package com.pdv.project.model;

import java.util.Date;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MOHAMED LAMINE SYLLA
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Price {

	private double[] loc;

	private String ville;
	private String type;
	private Date date;
	private double prix;

	public Price(String longitude, String latitude, String ville, String type, Date date, String priceValue) {
		super();

		if(Strings.isNullOrEmpty(longitude) || Strings.isNullOrEmpty(latitude)) {
			loc = new double[] {};
		} else {
			double x = Double.parseDouble(longitude) / 100_000;
			double y = Double.parseDouble(latitude) / 100_000;
			loc = new double[] { x, y };
		}
		this.ville = ville;
		this.type = type;
		this.date = date;
		this.prix = Double.parseDouble(priceValue) / 1_000;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("loc", loc).add("ville", ville).add("type", type).add("date", date)
				.add("prix", prix).toString();
	}

}

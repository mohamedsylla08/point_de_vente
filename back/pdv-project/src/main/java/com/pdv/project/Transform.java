package com.pdv.project;

import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.camel.language.XPath;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import com.google.common.base.Strings;
import com.pdv.project.filter.CityFilter;
import com.pdv.project.model.Adresse;
import com.pdv.project.model.Fermeture;
import com.pdv.project.model.Ouverture;
import com.pdv.project.model.PdvService;
import com.pdv.project.model.PointDeVente;
import com.pdv.project.model.Prix;
import com.pdv.project.model.Rupture;
import com.pdv.project.model.Service;
import com.pdv.project.model.Ville;
import com.pdv.project.repository.IAdresseRepository;
import com.pdv.project.repository.IFermetureRepository;
import com.pdv.project.repository.IOuvertureRepository;
import com.pdv.project.repository.IPointDeVenteRepository;
import com.pdv.project.repository.IPrixRepository;
import com.pdv.project.repository.IRuptureRepository;
import com.pdv.project.repository.IVillesFranceRepository;

/**
 * @author MOHAMED LAMINE SYLLA
 *
 */
@Component
public class Transform {

	private static final Logger LOG = LoggerFactory.getLogger(Transform.class);

	/** Formatage des dates **/
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	/** Formatage des dates **/
	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private IVillesFranceRepository villeRepository;

	@Autowired
	private IAdresseRepository adresserepository;

	@Autowired
	private IOuvertureRepository ouvertureRepository;

	@Autowired
	private IFermetureRepository fermetureRepository;

	@Autowired
	private IPointDeVenteRepository pointDeVenteRepository;

	@Autowired
	private IPrixRepository prixRepository;

	@Autowired
	IRuptureRepository ruptureRepository;

	@Autowired
	private CityFilter cityFilter;

	public Optional<PointDeVente> toPointDeVente(@XPath("/pdv/@id") String id, @XPath("/pdv/@longitude") String longitude,
			@XPath("/pdv/@latitude") String latitude, @XPath("/pdv/@cp") String codepostal, @XPath("/pdv/@pop") String pop,
			@XPath("/pdv/adresse/text()") String adressepdv, @XPath("/pdv/ville/text()") String nomville, @XPath("/pdv/prix") List<Element> prices,
			@XPath("/pdv/fermeture") Element ferm, @XPath("/pdv/rupture") List<Element> ruptures, @XPath("/pdv/ouverture") Element ouver,
			@XPath("/pdv/services/service") List<Element> pdvServices) {

		if (Strings.isNullOrEmpty(longitude) || Strings.isNullOrEmpty(latitude) || Strings.isNullOrEmpty(nomville)
				|| CollectionUtils.isEmpty(prices)) {
			Collections.emptyList();
		}

		String nfdCity = Normalizer.normalize(nomville, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

		if (!cityFilter.keep(nfdCity)) {
			return Optional.empty();
		} else {

			try {

				int identifiant = Integer.valueOf(id);

				Double lgtude = Double.parseDouble(!Strings.isNullOrEmpty(longitude) ? longitude : "0") / 100_000;
				Double lttude = Double.parseDouble(!Strings.isNullOrEmpty(latitude) ? latitude : "0") / 100_000;

				// Recherche de la ville par code postal et par nom
				Ville ville = villeRepository.findFirstByVilleNomIgnoreCase(nfdCity);

				// Initialisation de l'adresse
				Adresse adresse = Adresse.builder().adresselibre(adressepdv).codepostal(codepostal).ville(ville).build();
				adresse = adresserepository.save(adresse);
				LOG.debug("Sauvegarde de l'adresse: " + adresse.toString());

				// Création de l'ouverture
				Optional<Ouverture> ouvertFromDb = createOuverture(ouver);
				Ouverture ouverture = ouvertFromDb.isPresent() ? ouvertFromDb.get() : null;

				// Création de la fermeture
				Optional<Fermeture> fermFromDb = createFermeture(ferm);
				Fermeture fermeture = fermFromDb.isPresent() ? fermFromDb.get() : null;

				// Création du point de ente
				PointDeVente pointDeVente = createPointDeVente(codepostal, pop, identifiant, lgtude, lttude, ville, adresse, ouverture, fermeture);

				// Création des prix
				prices.stream().map(elem -> createPrice(elem, pointDeVente)).filter(Optional::isPresent).map(Optional::get)
						.collect(Collectors.toSet());

				// Création des ruptures
				ruptures.stream().map(elem -> createRupture(elem, pointDeVente)).collect(Collectors.toSet());

				// Création du service
				pdvServices.stream().map(elem -> createPdvService(elem, pointDeVente)).collect(Collectors.toSet());

				return Optional.of(pointDeVente);

			} catch (DataIntegrityViolationException exception) {
				exception.getStackTrace();
				throw new DataIntegrityViolationException(exception.getMessage(), exception.getCause());
			}

		}

	}

	/**
	 * @param codepostal
	 * @param pop
	 * @param identifiant
	 * @param lgtude
	 * @param lttude
	 * @param ville
	 * @param adresse
	 * @param ouverture
	 * @param fermeture
	 * @return
	 */
	private PointDeVente createPointDeVente(String codepostal, String pop, int identifiant, Double lgtude, Double lttude, Ville ville,
			Adresse adresse, Ouverture ouverture, Fermeture fermeture) {

		PointDeVente pointDeVente = PointDeVente.builder().identifiant(identifiant).longitude(lgtude).latitude(lttude).codepostal(codepostal).pop(pop)
				.ville(ville).adresse(adresse).fermeture(fermeture).ouverture(ouverture).build();

		pointDeVenteRepository.save(pointDeVente);
		LOG.debug("Sauvegarde du point de vente: " + pointDeVente.toString());

		return pointDeVente;
	}

	/**
	 * Création de l'ouverture.
	 * @param ouver
	 * @return
	 */
	private Optional<Ouverture> createOuverture(Element ouver) {
		Ouverture ouverture = null;
		try {
			String debut = ouver.getAttribute("debut");
			String fin = ouver.getAttribute("fin");

			Date debutOuverture = null;
			if (!Strings.isNullOrEmpty(debut)) {
				debutOuverture = format.parse(debut);
			}
			Date finOuverture = null;
			if (!Strings.isNullOrEmpty(fin)) {
				finOuverture = format.parse(fin);
			}
			String saufjour = ouver.getAttribute("saufjour");

			ouverture = Ouverture.builder().debut(debutOuverture).fin(finOuverture).saufjour(saufjour).build();
			ouvertureRepository.save(ouverture);

			return Optional.of(ouverture);

		} catch (Exception e) {
			LOG.error("Erreur lors de la sauvegarde de l'ouverture :" + e.getStackTrace());

			return Optional.empty();
		}

	}

	/**
	 * Création de la fermeture
	 * 
	 * @param fermeture
	 * @return
	 */
	private Optional<Fermeture> createFermeture(Element ferm) {

		// Si la fermeture n'est pas renseignée, on sort
		if (ferm == null) {
			return Optional.empty();
		}

		String debut = ferm.getAttribute("debut");
		String fin = ferm.getAttribute("fin");
		String type = ferm.getAttribute("type");

		Date dateDebut = null;
		Date dateFin = null;
		try {
			dateDebut = df.parse(debut);
			if (!Strings.isNullOrEmpty(fin)) {
				dateFin = df.parse(fin);
			}
		} catch (ParseException e) {
			LOG.error("Erreur lors du parsing des dates lors de la création de la fermeture " + e.getMessage());
		}

		try {
			Fermeture fermeture = Fermeture.builder().debut(dateDebut).fin(dateFin).type(type).build();

			fermetureRepository.save(fermeture);
			LOG.debug("Sauvegarde de la fermeture: " + fermeture.toString());

			return Optional.of(fermeture);

		} catch (Exception e) {
			LOG.debug("Erreur lors de la créatio de la fermeture " + e.getStackTrace());

			return Optional.empty();
		}
	}

	/**
	 * Permet de créer un élément price
	 * 
	 * @param element
	 * @return
	 */
	private Optional<Prix> createPrice(Element element, PointDeVente pointDeVente) {
		String identifiant = element.getAttribute("id");
		String nom = element.getAttribute("nom");
		String maj = element.getAttribute("maj");
		String priceValue = element.getAttribute("valeur");

		if (Strings.isNullOrEmpty(nom) || Strings.isNullOrEmpty(maj) || Strings.isNullOrEmpty(priceValue) || Strings.isNullOrEmpty(identifiant)) {
			return Optional.empty();
		}

		try {
			Date date = df.parse(maj);
			Prix prix = Prix.builder().identifiant(Integer.valueOf(identifiant)).nom(nom).maj(date).valeur(Double.valueOf(priceValue))
					.pointDeVente(pointDeVente).build();

			prixRepository.save(prix);
			LOG.debug("Sauvegarde du prix");

			return Optional.of(prix);
		} catch (Exception e) {
			LOG.error("Erreur lors de la sauvegarde du prix " + e.getStackTrace());
			return Optional.empty();
		}
	}

	/**
	 * Création de la rupture.
	 * 
	 * @param elem
	 * @param pointDeVente
	 * @return
	 */
	private Rupture createRupture(Element elem, PointDeVente pointDeVente) {

		String identifiant = elem.getAttribute("id");
		String fuel = elem.getAttribute("nom");
		String debut = elem.getAttribute("debut");
		String fin = elem.getAttribute("fin");

		Date dateDebut = null;
		Date dateFin = null;
		try {
			dateDebut = df.parse(debut);
			if (!Strings.isNullOrEmpty(fin)) {
				dateFin = df.parse(fin);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Rupture rupture = Rupture.builder().identifiant(Integer.valueOf(identifiant)).fuel(fuel).debut(dateDebut).fin(dateFin)
				.pointDeVente(pointDeVente).build();
		try {
			ruptureRepository.save(rupture);

		} catch (Exception e) {
			LOG.error("Erreur lors de la sauvegarde de la rupture ");
			e.printStackTrace();
		}

		return rupture;
	}

	/**
	 * Création des services des points de vente.
	 * 
	 * @param elem
	 * @param pointDeVente
	 *            TODO
	 * @return
	 */
	private PdvService createPdvService(Element elem, PointDeVente pointDeVente) {

		String code = "TEST";
		String libelle = elem.getTextContent();
		Service service = Service.builder().libelle(libelle).build();

		PdvService pdvService = new PdvService(pointDeVente, service);

		return pdvService;
	}
}

package applets.etsmtl.ca;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import applets.etsmtl.ca.amc.model.*;
import applets.etsmtl.ca.orders.Address;
import applets.etsmtl.ca.orders.Customer;
import applets.etsmtl.ca.orders.CustomerResource;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

@Provider
@SuppressWarnings("rawtypes")
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

	private JAXBContext context;
	// defining these explicitly is only required to state to use the configuration for natural json handling
	// https://jersey.java.net/nonav/apidocs/1.5/jersey/com/sun/jersey/api/json/JSONConfiguration.Notation.html#NATURAL
	private Class[] types = { Address.class, Customer.class, CustomerResource.class, TirageInscrit.class,
			Equipe.class, Evenement.class, Intervenant.class, Musique.class, Partenaire.class,
			Participant.class, Streaming.class, TirageInscrit.class, TiragePrix.class, TirageSort.class};

	public JAXBContextResolver() throws Exception {
		this.context = new JSONJAXBContext(JSONConfiguration.natural().build(), types);
	}

	@Override
	public JAXBContext getContext(Class<?> objectType) {
		for (Class type : types) {
			if (type == objectType) {
				return context;
			}
		}
		return null;
	}
}
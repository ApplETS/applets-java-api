package applets.etsmtl.ca.orders;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("customer")
public class CustomerResource {

	@GET
	@Path("id/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Customer getCustomer(@PathParam("id") String id) {
		CustomerDao customerDao = new CustomerDao();
		return customerDao.fetchCustomer(id);
	}

}

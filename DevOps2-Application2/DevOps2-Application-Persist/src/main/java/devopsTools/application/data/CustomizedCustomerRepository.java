package devopsTools.application.data;

import devopsTools.application.domain.Customer;
import devopsTools.application.domain.db.NameDB;

public interface CustomizedCustomerRepository {

	/*
	 * This allows updating just the name field of a customer
	 * It is used by the Rest PATCH service specifically
	 */
	public Customer save(long customerId, NameDB name);
}

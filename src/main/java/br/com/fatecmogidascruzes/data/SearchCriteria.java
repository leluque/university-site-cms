package br.com.fatecmogidascruzes.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class implements query filter criteria.
 *
 * Development History:
 *
 * 26/04/2016 - First version developed by Leandro Luque
 * (leandro.luque@gmail.com).
 */
public class SearchCriteria {

	/**
	 * The value that the attributes specified in whatToFilter must like.
	 */
	protected String filter = "";

	/**
	 * The attributes that must be filtered.
	 */
	protected List<String> whatToFilter = new ArrayList<String>();

	/**
	 * The attributes that will be used for ordering.
	 */
	protected List<String> orderBy = new ArrayList<String>();

	/**
	 * The initial register to return.
	 */
	protected Integer initialRegister;

	/**
	 * The number of registers to return.
	 */
	protected Integer numberOfRegisters;

	/**
	 * An enumeration of ordering types.
	 */
	public enum Order {
		ASCENDING, DESCENDING
	};

	/**
	 * The order.
	 */
	protected Order order;

	/**
	 * Was any filter criteria specified?
	 *
	 * @return true, if yes. false, otherwise.
	 */
	public boolean hasFilter() {
		return filter != null && filter.trim().length() > 0 && !whatToFilter.isEmpty();
	}

	/**
	 * Was an order specified?
	 *
	 * @return true, if yes. false, otherwise.
	 */
	public boolean isOrdered() {
		return !orderBy.isEmpty();
	}

	/**
	 * Was any pagination criteria specified?
	 *
	 * @return true, if yes. false, otherwise.
	 */
	public boolean hasPagination() {
		return initialRegister >= 0 && numberOfRegisters > 0;
	}

	/**
	 * Gets the value that the attributes specified in filterBy must have.
	 *
	 * @return The value that the attributes specified in filterBy must have.
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * Sets the value that the attributes specified in filterBy must have.
	 *
	 * @param filter The value that the attributes specified in filterBy must have.
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * Gets the attributes that must be filtered.
	 *
	 * @return The attributes that must be filtered.
	 */
	public List<String> getCamposFiltragem() {
		return whatToFilter;
	}

	/**
	 * Sets the attributes that must be filtered.
	 *
	 * @param filterBy The attributes that must be filtered.
	 */
	public void setWhatToFilter(List<String> filterBy) {
		this.whatToFilter = filterBy;
	}

	/**
	 * Gets the attributes that will be used for ordering.
	 *
	 * @return The attributes that will be used for ordering.
	 */
	public List<String> getOrderBy() {
		return orderBy;
	}

	/**
	 * Sets the attributes that will be used for ordering.
	 *
	 * @param orderBy The attributes that will be used for ordering.
	 */
	public void setOrderBy(List<String> orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * Add the attributes that will be used for ordering.
	 *
	 * @param ordersBy The attributes that will be used for ordering.
	 */
	public void addSortBy(String... ordersBy) {
		this.orderBy.addAll(Arrays.asList(ordersBy));
	}

	/**
	 * Gets the order type.
	 *
	 * @return The order type.
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Sets the order type.
	 *
	 * @param order The order type.
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * Gets the initial row.
	 *
	 * @return The initial row.
	 */
	public Integer getInitialRegister() {
		return initialRegister;
	}

	/**
	 * Sets the initial row.
	 *
	 * @param initialRegister The initial row.
	 */
	public void setInitialRegister(Integer initialRegister) {
		this.initialRegister = initialRegister;
	}

	/**
	 * Gets the number of rows.
	 *
	 * @return The number of rows.
	 */
	public Integer getNumberOfRegisters() {
		return numberOfRegisters;
	}

	/**
	 * Sets the number of rows.
	 *
	 * @param numberOfRegisters The number of rows.
	 */
	public void setNumberOfRegisters(Integer numberOfRegisters) {
		this.numberOfRegisters = numberOfRegisters;
	}

}
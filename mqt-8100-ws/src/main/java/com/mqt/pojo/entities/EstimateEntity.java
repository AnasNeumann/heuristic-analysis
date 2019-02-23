package com.mqt.pojo.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.mqt.pojo.AbstractResource;

/**
 * ESTIMATE table mapping
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 23/02/2019
 * @version 1.0
 */
@Entity
@Table(name = "ESTIMATE")
@SequenceGenerator(name = "estimate_id_seq_generator", sequenceName = "estimate_id_seq")
public class EstimateEntity extends AbstractResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimate_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;

	@Column(name = "VALUE")
	private Integer value;

	@Column(name = "TIMESTAMPS")
	private Calendar timestamps;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public EstimateEntity setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public EstimateEntity setValue(Integer value) {
		this.value = value;
		return this;
	}

	/**
	 * @return the timestamps
	 */
	public Calendar getTimestamps() {
		return timestamps;
	}

	/**
	 * @param timestamps
	 *            the timestamps to set
	 */
	public EstimateEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}

}

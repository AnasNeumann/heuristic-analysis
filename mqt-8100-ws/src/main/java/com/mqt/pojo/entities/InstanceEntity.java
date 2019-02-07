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
 * ISTANCE table mapping
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @version 1.0
 */
@Entity
@Table(name = "ISTANCE")
@SequenceGenerator(name = "istance_id_seq_generator", sequenceName = "istance_id_seq")
public class InstanceEntity extends AbstractResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "istance_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;

	@Column(name = "OPTIMAL")
	private Integer optimal;

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
	public InstanceEntity setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the optimal
	 */
	public Integer getOptimal() {
		return optimal;
	}

	/**
	 * @param optimal
	 *            the optimal to set
	 */
	public InstanceEntity setOptimal(Integer optimal) {
		this.optimal = optimal;
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
	public InstanceEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}

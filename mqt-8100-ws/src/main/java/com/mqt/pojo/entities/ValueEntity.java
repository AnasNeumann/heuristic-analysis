package com.mqt.pojo.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.mqt.pojo.AbstractResource;

/**
 * VALUES table mapping
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 06/02/2019
 * @version 1.0
 */
@Entity
@Table(name = "VALUES")
@SequenceGenerator(name = "message_id_seq_generator", sequenceName = "message_id_seq")
public class ValueEntity extends AbstractResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id_seq_generator")
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;

	@Column(name = "VALUE")
	private Integer value;

	@Column(name = "HEURISTIC_ID")
	private Long heuristicId;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "ISTANCE_ID", referencedColumnName = "ID")
	private InstanceEntity instance;

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
	public ValueEntity setId(Long id) {
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
	public ValueEntity setValue(Integer value) {
		this.value = value;
		return this;
	}

	/**
	 * @return the heuristicId
	 */
	public Long getHeuristicId() {
		return heuristicId;
	}

	/**
	 * @param heuristicId
	 *            the heuristicId to set
	 */
	public ValueEntity setHeuristicId(Long heuristicId) {
		this.heuristicId = heuristicId;
		return this;
	}

	/**
	 * @return the instance
	 */
	public InstanceEntity getInstance() {
		return instance;
	}

	/**
	 * @param instance
	 *            the instance to set
	 */
	public ValueEntity setInstance(InstanceEntity instance) {
		this.instance = instance;
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
	public ValueEntity setTimestamps(Calendar timestamps) {
		this.timestamps = timestamps;
		return this;
	}
}

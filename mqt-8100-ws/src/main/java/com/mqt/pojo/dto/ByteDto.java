package com.mqt.pojo.dto;

import com.mqt.pojo.AbstractResource;

/**
 * DTO pour un contenu multimédia
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 14/10/2017
 * @version 1.0
 */
public class ByteDto extends AbstractResource {
	private static final long serialVersionUID = 1L;
	private byte[] content;
	private Long id;

	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public ByteDto setContent(byte[] content) {
		this.content = content;
		return this;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public ByteDto setId(Long id) {
		this.id = id;
		return this;
	}
}
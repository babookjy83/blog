package com.blog.core.jpa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = false, of = { "keyword" })
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = { "keyword" })
@Entity
@Table(name = "TB_SEARCH_HISTORY")
public class SearchHistory implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7084787786709688336L;

	@Id
	@Column(name = "ID", nullable = true)
	@GeneratedValue
	private Long id;
	
	@Column(name = "KEYWORD", nullable = false)
	private String keyword;
	
	@Column(name = "SEARCH_COUNT", nullable = true)
	private int searchCount = 0;
	
	public SearchHistory(String keyword) {
		this.keyword = keyword;
	}
	
}

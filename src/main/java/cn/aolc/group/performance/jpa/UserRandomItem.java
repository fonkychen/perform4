package cn.aolc.group.performance.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.aolc.group.performance.jpa.inter.EntityWithId;
import cn.aolc.group.performance.jpa.tenant.RandomCommentCategory;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="User_Random_Item")
public class UserRandomItem implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = -546402767287671684L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="comment_category_id")
	private RandomCommentCategory commentCategory;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="random_comment_id")
	private UserRandomComment randomComment;
	
	@Column(name="star_num")
	private Integer starNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RandomCommentCategory getCommentCategory() {
		return commentCategory;
	}

	public void setCommentCategory(RandomCommentCategory commentCategory) {
		this.commentCategory = commentCategory;
	}

	public UserRandomComment getRandomComment() {
		return randomComment;
	}

	public void setRandomComment(UserRandomComment randomComment) {
		this.randomComment = randomComment;
	}

	public Integer getStarNum() {
		return starNum;
	}

	public void setStarNum(Integer starNum) {
		this.starNum = starNum;
	}

}

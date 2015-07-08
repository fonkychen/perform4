package cn.aolc.group.performance.jpa;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import cn.aolc.group.performance.jpa.inter.EntityWithId;

@Entity
@Table(name="Company")
public class Company implements EntityWithId{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2131082286086977049L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",length=2,nullable=false)
	private Long id;
	
	@Column(name="name",length=50,nullable=false)
	private String name;
	
	@Column(name="code",length=8,nullable=false)
	private String code;
	
	@Column(name="address",length=300)
	private String address;
	
	@Column(name="logo")
	private Byte[] logo;
	
	@Column(name="daily_self_mark_score")
	private Integer dailySelfMarkScore;
	
	@Column(name="daily_board_score")
	private Integer dailyBoardScore;
	
	@Column(name="random_comment_core")
	private Integer randomCommentScore;
	
	@Column(name="comment_score")
	private Integer commentScore;
	
	@OneToMany(mappedBy="company",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@OrderBy(" id asc ")
	@Where(clause="user_status_id!='2'")
	private List<User> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Byte[] getLogo() {
		return logo;
	}

	public void setLogo(Byte[] logo) {
		this.logo = logo;
	}

	public Integer getDailySelfMarkScore() {
		return dailySelfMarkScore;
	}

	public void setDailySelfMarkScore(Integer dailySelfMarkScore) {
		this.dailySelfMarkScore = dailySelfMarkScore;
	}

	public Integer getDailyBoardScore() {
		return dailyBoardScore;
	}

	public void setDailyBoardScore(Integer dailyBoardScore) {
		this.dailyBoardScore = dailyBoardScore;
	}

	public Integer getRandomCommentScore() {
		return randomCommentScore;
	}

	public void setRandomCommentScore(Integer randomCommentScore) {
		this.randomCommentScore = randomCommentScore;
	}

	public Integer getCommentScore() {
		return commentScore;
	}

	public void setCommentScore(Integer commentScore) {
		this.commentScore = commentScore;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}

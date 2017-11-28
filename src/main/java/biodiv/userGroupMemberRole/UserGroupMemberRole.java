package biodiv.userGroupMemberRole;
// Generated 31 Jul, 2017 7:18:53 AM by Hibernate Tools 3.5.0.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import biodiv.user.Role;
import biodiv.user.User;
import biodiv.userGroup.UserGroup;

/**
 * UserGroupMemberRole generated by hbm2java
 */
@Entity
@Table(name = "user_group_member_role", schema = "public")
public class UserGroupMemberRole implements java.io.Serializable {

	private UserGroupMemberRoleId id;
	private User user;
	private UserGroup userGroup;
	private Role role;

	public UserGroupMemberRole() {
	}

	public UserGroupMemberRole(UserGroupMemberRoleId id, User user, UserGroup userGroup, Role role) {
		this.id = id;
		this.user = user;
		this.userGroup = userGroup;
		this.role = role;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "userGroupId", column = @Column(name = "user_group_id", nullable = false)),
			@AttributeOverride(name = "roleId", column = @Column(name = "role_id", nullable = false)),
			@AttributeOverride(name = "userId", column = @Column(name = "s_user_id", nullable = false)) })
	public UserGroupMemberRoleId getId() {
		return this.id;
	}

	public void setId(UserGroupMemberRoleId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "s_user_id", nullable = false, insertable = false, updatable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_group_id", nullable = false, insertable = false, updatable = false)
	public UserGroup getUserGroup() {
		return this.userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false, insertable = false, updatable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}

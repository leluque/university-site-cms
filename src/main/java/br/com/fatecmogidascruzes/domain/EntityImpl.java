package br.com.fatecmogidascruzes.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.devskiller.friendly_id.FriendlyId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Access(AccessType.FIELD)
@AllArgsConstructor
@Getter
@MappedSuperclass
@Setter
public abstract class EntityImpl implements Entity, Serializable {

	private static final long serialVersionUID = 1L;

	protected Long id;

	@Column(name = "creation_date", nullable = false, updatable = false)
	protected LocalDateTime creationDate;

	@Column(name = "last_update", nullable = false)
	protected LocalDateTime lastUpdate;

	@Basic
	@Column(name = "enabled", nullable = false)
	protected boolean enabled = true;

	@Basic
	@Column(name = "hash", nullable = false, updatable = false)
	protected UUID hash;

	@Access(AccessType.PROPERTY)
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@NotNull
	public Long getId() {
		return this.id;
	}

	public EntityImpl() {
		this.creationDate = LocalDateTime.now();
		this.lastUpdate = LocalDateTime.now();
		this.hash = UUID.randomUUID();
		this.enabled = true;
	}

	public boolean getEnabled() {
		return this.isEnabled();
	}

	public boolean isEnabled() {
		return this.enabled;
	}
	
	public String getHashString() {
		return FriendlyId.toFriendlyId(hash);
	}

	@Override
	public int hashCode() {
		int hash = id != null ? id.hashCode() : 0;
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (null == other) {
			return false;
		}
		if (!getClass().getName().equals(other.getClass().getName())) {
			return false;
		}
		Entity otherEntity = (Entity) other;
		if (null == getId() || null == otherEntity.getId()) {
			return this == otherEntity;
		}
		if (0 == getId() && 0 == otherEntity.getId()) {
			return false;
		}
		return Objects.equals(getId(), otherEntity.getId());
	}

}

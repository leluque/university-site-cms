package br.com.fatecmogidascruzes.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Entity {

	public Long getId();

	public void setId(Long id);

	public UUID getHash();

	public void setHash(UUID hash);

	public LocalDateTime getCreationDate();

	public void setCreationDate(LocalDateTime creationDate);

	public LocalDateTime getLastUpdate();

	public void setLastUpdate(LocalDateTime lastUpdate);

	public boolean getEnabled();

	public boolean isEnabled();

	public void setEnabled(boolean enabled);

}

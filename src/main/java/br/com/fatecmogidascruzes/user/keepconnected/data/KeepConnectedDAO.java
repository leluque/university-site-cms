package br.com.fatecmogidascruzes.user.keepconnected.data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatecmogidascruzes.user.User;
import br.com.fatecmogidascruzes.user.keepconnected.KeepConnected;

public interface KeepConnectedDAO extends JpaRepository<KeepConnected, String> {

	List<KeepConnected> findByUser(User user);

	List<KeepConnected> findByLastUseBefore(LocalDateTime date);

	Optional<KeepConnected> findBySeries(String series);

	void removeByUser(User user);

}
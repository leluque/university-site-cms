package br.com.fatecmogidascruzes.dashboard.service.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATIVE,ROLE_ACADEMIC,ROLE_MULTIMIDIA,ROLE_REQUEST')")
@RequestMapping("/admin/dashboard")
public class DashboardController {

	@GetMapping
	public String dashboard() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("fran2018@SiteFatecMC"));
		System.out.println(encoder.encode("pri2018@SiteFatecMC"));
		System.out.println(encoder.encode("brunao2018@SiteFatecMC"));
		System.out.println(encoder.encode("monica2018@SiteFatecMC"));
		return "/index";
	}

}

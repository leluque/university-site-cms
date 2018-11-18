package br.com.fatecmogidascruzes.site;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatecmogidascruzes.course.Course;
import br.com.fatecmogidascruzes.employee.service.web.EmployeeWebService;
import br.com.fatecmogidascruzes.event.Event;
import br.com.fatecmogidascruzes.event.service.web.EventWebService;
import br.com.fatecmogidascruzes.news.News;
import br.com.fatecmogidascruzes.news.service.web.NewsWebService;
import br.com.fatecmogidascruzes.testimonial.service.web.TestimonialWebService;

@Controller
public class SiteController {

	private final NewsWebService newsWebService;
	private final EmployeeWebService employeeWebService;
	private final EventWebService eventWebService;
	private final TestimonialWebService testimonialWebService;

	public SiteController(NewsWebService newsWebService, EmployeeWebService employeeWebService,
			EventWebService eventWebService, TestimonialWebService testimonialWebService) {
		super();
		this.newsWebService = newsWebService;
		this.employeeWebService = employeeWebService;
		this.eventWebService = eventWebService;
		this.testimonialWebService = testimonialWebService;
	}

	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("site/index");
		modelAndView.addObject("highlightList", newsWebService.getHighlights());
		modelAndView.addObject("newsList", newsWebService.getHomeNews());
		modelAndView.addObject("eventList", eventWebService.getHomeEvents());
		modelAndView.addObject("testimonialList", testimonialWebService.getHomeTestimonials());

		return modelAndView;
	}

	@RequestMapping("/ads")
	public ModelAndView ads() {
		ModelAndView modelAndView = new ModelAndView("site/ads");
		modelAndView.addObject("professors", employeeWebService.getEnabledByCourse(Course.ADS));
		return modelAndView;

	}

	@RequestMapping("/aFatecMogi")
	public ModelAndView aFatecMogi() {
		return new ModelAndView("site/aFatecMogi");
	}

	@RequestMapping("/agro")
	public ModelAndView agro() {
		ModelAndView modelAndView = new ModelAndView("site/agro");
		modelAndView.addObject("professors", employeeWebService.getEnabledByCourse(Course.AGRO));
		return modelAndView;

	}

	@RequestMapping("/biblioteca")
	public ModelAndView biblioteca() {
		return new ModelAndView("site/biblioteca");
	}

	@RequestMapping("/calendarioAcademico")
	public ModelAndView calendarioAcademico() {
		return new ModelAndView("site/calendarioAcademico");
	}

	@RequestMapping("/comoChegar")
	public ModelAndView comoChegar() {
		return new ModelAndView("site/comoChegar");
	}

	@RequestMapping("/concursos")
	public ModelAndView concursos() {
		return new ModelAndView("site/concursos");
	}

	@RequestMapping("/corpoAdministrativo")
	public ModelAndView corpoAdministrativo() {
		return new ModelAndView("site/corpoAdministrativo");
	}

	@RequestMapping("/corpoDocente")
	public ModelAndView corpoDocente(@RequestParam(name = "course", required = false) String courseString) {
		ModelAndView modelAndView = new ModelAndView("site/corpoDocente");

		Course course = null;
		try {
			course = Course.valueOf(courseString);
		} catch (Exception error) {
		}

		if (null != course) {
			modelAndView.addObject("professors", employeeWebService.getEnabledByCourse(course));
		} else {
			modelAndView.addObject("professors", employeeWebService.getEnabledProfessors());
		}

		return modelAndView;
	}

	@RequestMapping("/detalheNoticia")
	public ModelAndView detalheNoticia(@RequestParam("hash") UUID hash,
			@RequestParam(name = "abrirLink", required = false) boolean abrirLink) {
		News news = null;
		if (abrirLink && null != (news = newsWebService.abrirLink(hash))) {
			ModelAndView modelAndView = new ModelAndView("redirect:" + news.getLink());
			return modelAndView;
		} else {
			ModelAndView modelAndView = new ModelAndView("site/detalheNoticia");
			modelAndView.addObject("news", newsWebService.getNewsDetail(hash));

			return modelAndView;
		}
	}

	@RequestMapping("/detalheEvento")
	public ModelAndView detalheEvento(@RequestParam("hash") UUID hash,
			@RequestParam(name = "abrirLink", required = false) boolean abrirLink) {
		Event event = null;
		if (abrirLink && null != (event = eventWebService.abrirLink(hash))) {
			ModelAndView modelAndView = new ModelAndView("redirect:" + event.getLink());
			return modelAndView;
		} else {
			ModelAndView modelAndView = new ModelAndView("site/detalheEvento");
			modelAndView.addObject("event", eventWebService.getEventDetail(hash));

			return modelAndView;
		}
	}

	@RequestMapping("/direcao")
	public ModelAndView direcao() {
		return new ModelAndView("site/direcao");
	}

	@RequestMapping("/estagio")
	public ModelAndView estagio() {
		return new ModelAndView("site/estagio");
	}

	@RequestMapping("/intercambio")
	public ModelAndView intercambio() {
		return new ModelAndView("site/intercambio");
	}

	@RequestMapping("/logistica")
	public ModelAndView logistica() {
		ModelAndView modelAndView = new ModelAndView("site/logistica");
		modelAndView.addObject("professors", employeeWebService.getEnabledByCourse(Course.LOG));
		return modelAndView;

	}

	@RequestMapping("/rh")
	public ModelAndView rh() {
		ModelAndView modelAndView = new ModelAndView("site/rh");
		modelAndView.addObject("professors", employeeWebService.getEnabledByCourse(Course.RH));
		return modelAndView;
	}

	@RequestMapping("/selecoes")
	public ModelAndView selecoes() {
		return new ModelAndView("site/selecoes");
	}

	@RequestMapping("/todasNoticias")
	public ModelAndView todasNoticias() {
		ModelAndView modelAndView = new ModelAndView("site/todasNoticias");
		modelAndView.addObject("newsList", newsWebService.getEnabledNews());

		return modelAndView;
	}

	@RequestMapping("/todosEventos")
	public ModelAndView todosEventos() {
		ModelAndView modelAndView = new ModelAndView("site/todosEventos");
		modelAndView.addObject("eventList", eventWebService.getEnabledEvents());

		return modelAndView;
	}

	@RequestMapping("/vagasRemanescentes")
	public ModelAndView vagasRemanescentes() {
		return new ModelAndView("site/vagasRemanescentes");
	}

	@RequestMapping("/passeEscolar")
	public ModelAndView passeEscolar() {
		return new ModelAndView("site/passeEscolar");
	}

	@RequestMapping("/secretariaAcademica")
	public ModelAndView secretariaAcademica() {
		return new ModelAndView("site/secretariaAcademica");
	}

	@RequestMapping("/congregacao")
	public ModelAndView congregacao() {
		return new ModelAndView("site/congregacao");
	}

	@RequestMapping("/gruposPesquisa")
	public ModelAndView gruposPesquisa() {
		return new ModelAndView("site/gruposPesquisa");
	}

	@RequestMapping("/animaTerra")
	public ModelAndView animaTerra() {
		return new ModelAndView("site/animaTerra");
	}

}

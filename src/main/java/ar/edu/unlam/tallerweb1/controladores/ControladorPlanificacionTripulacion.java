package ar.edu.unlam.tallerweb1.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Planificacion;
import ar.edu.unlam.tallerweb1.modelo.PlanificacionTripulante;
import ar.edu.unlam.tallerweb1.modelo.Tripulacion;
import ar.edu.unlam.tallerweb1.servicios.ServicioPlanificacion;
import ar.edu.unlam.tallerweb1.servicios.ServicioPlanificacionTripulante;
import ar.edu.unlam.tallerweb1.servicios.ServicioTripulacion;

@Controller
public class ControladorPlanificacionTripulacion {

	@Inject
	private ServicioPlanificacionTripulante servicioPlanificacionTripulante;
	@Inject
	private ServicioTripulacion servicioTripulacion;
	@Inject
	private ServicioPlanificacion servicioPlanificacion;
	
	@RequestMapping("/formularioCarga")
	public ModelAndView cargarTripulante(@RequestParam ("Plan") long idPlan,@RequestParam ("Posicion") Integer posicion){
		ModelMap modelo= new ModelMap();
		List<Tripulacion> lista = servicioTripulacion.consultarTripulaciones();
		modelo.put("idPlan", idPlan);
		modelo.put("listaTripulante", lista);
		modelo.put("posicion", posicion);
		return new ModelAndView("formularioCarga",modelo);
	}

	@RequestMapping(path = "/tripulanteCargado", method = RequestMethod.POST)
	public ModelAndView tripulanteCargado(@RequestParam ("numeroPlanificacion") long numeroPlanificacion,@RequestParam ("nombreTripulante") long nombreTripulante,
			HttpServletRequest request,@RequestParam ("posicion") Integer posicion) {
		ModelMap modelo = new ModelMap();
		PlanificacionTripulante PT = new PlanificacionTripulante();
		PT.setIdPlanificacion(servicioPlanificacionTripulante.agregarPlan(numeroPlanificacion));
		PT.setIdTripulacion(servicioPlanificacionTripulante.agregarTripulante(nombreTripulante));
		List<Planificacion>listaDeVuelos =servicioTripulacion.traerVuelosDeUnTripulante(servicioPlanificacionTripulante.agregarTripulante(nombreTripulante));
		Long TV1=servicioTripulacion.calcularTV(listaDeVuelos,servicioPlanificacionTripulante.agregarPlan(numeroPlanificacion));
		System.out.println(TV1);
		String validacion = servicioTripulacion.verificarTVUnDia(TV1);
		Long TSV=servicioTripulacion.calcularTSV(listaDeVuelos, servicioPlanificacionTripulante.agregarPlan(numeroPlanificacion));
		System.out.println(TSV);
		validacion += servicioTripulacion.verificarTSVUnDia(TSV);
		if(!(validacion.isEmpty())){
			modelo.put("Error", validacion);
			List<Planificacion> lista = servicioPlanificacion.consultarPlanificaciones();
			modelo.put("lista", lista);
			return new ModelAndView("consultarPlanificaciones",modelo);
		}
		servicioPlanificacionTripulante.guardar(PT);
		servicioPlanificacion.guardarTripulante(numeroPlanificacion,posicion,servicioPlanificacionTripulante.agregarTripulante(nombreTripulante));
		return new ModelAndView("redirect:/consultarPlanificaciones");
	}
}

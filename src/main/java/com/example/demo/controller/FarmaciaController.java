package com.example.demo.controller;

import java.sql.SQLException;
import java.util.LinkedList;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.persistence.*;
import com.example.demo.transferobject.*;
import org.springframework.stereotype.Controller;


@Controller
public class FarmaciaController {
	@RequestMapping(value = "/")
	public String index() {
		return "login.jsp";
	}

	@RequestMapping(value= "/loginAdmin", method= RequestMethod.POST)
	public String greeting(@RequestParam(value="user", required=false, defaultValue="World") String correo,@RequestParam(value="pass",
	required=false, defaultValue="World") String pass, Model model) throws SQLException {
		AdministradorTO admin= new AdministradorTO();
		admin.setCorreo(correo);
		admin.setClave(pass);
		AdministradorDAO adminDao = new AdministradorDAO();
		AdministradorTO result =adminDao.login(admin);
		if(result!=null) {
		return "index.jsp";
		}
		return "login.jsp";
	}
	@RequestMapping(value = "/registroUsuario")
	public String registro() {
		return "agregar-usuarios.jsp";
	}
	@RequestMapping(value = "/agregarUsuarios")
	public String agregarUser() {
		return "agregar-usuarios.jsp";
	}
	@RequestMapping(value = "/agregarMedicamentos")
	public String agregarMedicamentos() {
		return "agregar-medicamentos.jsp";
	}
	@RequestMapping(value= "/addUser", method= RequestMethod.POST)
	public String addUser(@RequestParam(value="nombres-usuarios", required=false, defaultValue="World") String nombre,
			@RequestParam(value="apellido-paterno-usuarios",	required=false, defaultValue="World") String app,
			@RequestParam(value="apellido-materno-usuarios",	required=false, defaultValue="World") String apm,
			@RequestParam(value="rut-usuarios",	required=false, defaultValue="World") String rut,
			@RequestParam(value="email-usuarios",	required=false, defaultValue="World") String email,
			@RequestParam(value="direccion-usuarios",	required=false, defaultValue="World") String dir,
			@RequestParam(value="telefono-usuarios",	required=false, defaultValue="World") int tel,
			@RequestParam(value="contrasena-usuarios",	required=false, defaultValue="World") String pass,Model model) throws SQLException {
		UsuarioTO user= new UsuarioTO();
		user.setCorreo(email);
		user.setClave(pass);
		user.setRut(rut);
		user.setNombre(nombre);
		user.setApellidoPaterno(app);
		user.setApellidoMaterno(apm);
		user.setTelefono(tel);
		user.setDireccion(dir);
		
		UsuarioDAO userDao = new UsuarioDAO();
		int result =userDao.create(user);
		if(result == 1) {
		return verUsuarios(model);
		}
		return "agregar-usuarios.jsp";
	}
	@RequestMapping(value= "/modUser", method= RequestMethod.POST)
	public String moduser(@RequestParam(value="nombres-usuarios", required=false, defaultValue="World") String nombre,
			@RequestParam(value="apellido-paterno-usuarios",	required=false, defaultValue="World") String app,
			@RequestParam(value="apellido-materno-usuarios",	required=false, defaultValue="World") String apm,
			@RequestParam(value="rut-usuarios",	required=false, defaultValue="World") String rut,
			@RequestParam(value="email-usuarios",	required=false, defaultValue="World") String email,
			@RequestParam(value="direccion-usuarios",	required=false, defaultValue="World") String dir,
			@RequestParam(value="telefono-usuarios",	required=false, defaultValue="World") int tel,
			@RequestParam(value="id",	required=false, defaultValue="World") int id,
			@RequestParam(value="contrasena-usuarios",	required=false, defaultValue="World") String pass,Model model) throws SQLException {
		UsuarioTO user= new UsuarioTO();
		user.setCorreo(email);
		user.setClave(pass);
		user.setRut(rut);
		user.setNombre(nombre);
		user.setApellidoPaterno(app);
		user.setApellidoMaterno(apm);
		user.setTelefono(tel);
		user.setDireccion(dir);
		user.setIdUsuario(id);
		
		UsuarioDAO userDao = new UsuarioDAO();
		int result =userDao.update(user);
		if(result ==1) {
		return this.verUsuarios(model);
		}
		return moduser(nombre,app,apm,rut,email,dir,tel,id,pass,model);
	}
	@RequestMapping(value= "/addMedic", method= RequestMethod.POST)
	public String addMedicamento(@RequestParam(value="nombres-medicamentos", required=false, defaultValue="World") String nombre,Model model) throws SQLException {
		MedicamentoTO med= new MedicamentoTO();
		med.setNombre(nombre);
		
		MedicamentoDAO medDao = new MedicamentoDAO();
		int result =medDao.create(med);
		if(result ==1) {
		return verMedicamento(model);
		}
		return "agregar-medicamentos.jsp";
	}
	@RequestMapping(value= "/modMedic", method= RequestMethod.POST)
	public String modMedicamento(@RequestParam(value="nombres-medicamentos", required=false, defaultValue="World") String nombre,@RequestParam(value="id", required=false, defaultValue="World") int id,Model model) throws SQLException {
		MedicamentoTO med= new MedicamentoTO();
		med.setNombre(nombre);
		med.setIdMedicamento(id);
		MedicamentoDAO medDao = new MedicamentoDAO();
		int result =medDao.update(med);
		if(result ==1) {
		return verMedicamento(model);
		}
		model.addAttribute("med",med);
		return editarMedicamento(id,model);
	}
	@RequestMapping(value= "/verMedicamentos")
	public String verMedicamento(Model model) throws SQLException {
		LinkedList<MedicamentoTO> med= new LinkedList<>();

		MedicamentoDAO medDao = new MedicamentoDAO();
		med = medDao.readAll();
		if(med.size() >0) {
		model.addAttribute("list",med);
		
		}
		return "ver-medicamentos.jsp";
	}
	@RequestMapping(value= "/verUsuarios")
	public String verUsuarios(Model model) throws SQLException {
		LinkedList<UsuarioTO> user= new LinkedList<>();

		UsuarioDAO userDao = new UsuarioDAO();
		user = userDao.readAll();
		if(user.size() >0) {
		model.addAttribute("list",user);
		
		}
		return "ver-usuarios.jsp";
	}
	@RequestMapping(value= "/editmed", method= RequestMethod.GET)
	public String editarMedicamento(@RequestParam(value="id", required=false, defaultValue="World") int id,Model model) throws SQLException {
		MedicamentoTO med= new MedicamentoTO();
		med.setIdMedicamento(id);
		MedicamentoDAO medDao = new MedicamentoDAO();
		med = medDao.read(med);
		if(med!=null) {
		model.addAttribute("med",med);
		return "modificar-medicamentos.jsp";
		}
		return "modificar-medicamentos.jsp";
	}
	@RequestMapping(value= "/deletemed", method= RequestMethod.GET)
	public String eliminarMedicamento(@RequestParam(value="id", required=false, defaultValue="World") int id,Model model) throws SQLException {
		MedicamentoTO med= new MedicamentoTO();
		med.setIdMedicamento(id);
		MedicamentoDAO medDao = new MedicamentoDAO();
		
		if( medDao.delete(med)) {
		return this.verMedicamento(model);
		}
		return this.verMedicamento(model);
	}
	@RequestMapping(value= "/edituser", method= RequestMethod.GET)
	public String editarUser(@RequestParam(value="id", required=false, defaultValue="World") int id,Model model) throws SQLException {
		UsuarioTO user= new UsuarioTO();
		user.setIdUsuario(id);
		UsuarioDAO userDao = new UsuarioDAO();
		user = userDao.read(user);
		if(user!=null) {
		model.addAttribute("user",user);
		return "modificar-usuarios.jsp";
		}
		return "modificar-usuarios.jsp";
	}
	@RequestMapping(value= "/deleteuser", method= RequestMethod.GET)
	public String eliminarUser(@RequestParam(value="id", required=false, defaultValue="World") int id,Model model) throws SQLException {
		UsuarioTO user= new UsuarioTO();
		user.setIdUsuario(id);
		UsuarioDAO userDao = new UsuarioDAO();
		
		
		if( userDao.delete(user)) {
		return this.verUsuarios(model);
		}
		return this.verUsuarios(model);
	}
}

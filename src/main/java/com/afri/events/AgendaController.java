package com.afri.events;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.afri.eventmanager.EventCalendar;
import com.afri.eventmanager.ICalendar;


/*
 @Controller
public class LoginController {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage() {
		return "login";
	}
}
 */
@Controller
public class AgendaController {

	@RequestMapping(value="/events")
	@ResponseBody
	public String getText()
	{
		return "hello from agenda Controller in value=events";
	}
	
	@RequestMapping(value="/Agenda", method=RequestMethod.GET)
	public String showAgenda(Model model)
	{
		
		ICalendar calendar = new EventCalendar();
		model.addAttribute("calendarItems", calendar.getCalendar());
		return "Agenda";
	}

}

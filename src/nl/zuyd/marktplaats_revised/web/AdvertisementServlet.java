package nl.zuyd.marktplaats_revised.web;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.zuyd.marktplaats_revised.Advertisement;
import nl.zuyd.marktplaats_revised.AdvertisementRepository;

import org.eclipse.persistence.annotations.Convert;

/**
 * Servlet implementation class AdvertisementServlet
 */
@WebServlet("/advertisements")
public class AdvertisementServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@EJB
	AdvertisementRepository advertRepo;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdvertisementServlet()
	{	
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{		
		List<Advertisement> l = advertRepo.getAll();
		
		// check for query params
		String s;
		if ((s = request.getParameter("id")) != null)
		{
			Advertisement advert = this.advertRepo.getById(Integer.parseInt(s));
			request.setAttribute("Advertisement", advert);
			
			// TODO: do  not check for == 1,  but check for == currentUser.getUserId() !!
			// or just check if the owner == the current user
			if (advert.getAdvertiser().getId() == 1)
			{
				this.getServletContext()
						.getRequestDispatcher("/EditAdvertisement.jsp")
						.forward(request, response);
			}
			else
			{
				this.getServletContext()
						.getRequestDispatcher("/SingleAdvertisement.jsp")
						.forward(request, response);
			}
		}
		else
		{
			
			// else list all advertisements and forward to the
			// ListAdvertisements.jsp to display all advertisements
			request.setAttribute("Advertisements", l);
			this.getServletContext()
					.getRequestDispatcher("/ListAdvertisements.jsp")
					.forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{		
		String p;
		if ((p = request.getParameter("delete_id")) != null)
		{
			// del
			Advertisement advertToDelete = this.advertRepo.getById(Integer.parseInt(p));
			
			response.getWriter().write("Advert with title " + advertToDelete.getTitle() + " is going to be deleted");
		}
		else if ((p = request.getParameter("sold_id")) != null)
		{
			response.getWriter().write(p);
		}
		else if ((p = request.getParameter("save_id")) != null)
		{
			Advertisement a = null;
			//GET BY ID
			for (Advertisement advertisement : l) {
				if (advertisement.getId()==Integer.parseInt(p))
				{
					a=advertisement;
				}
			}
			
			if (a != null){
				//TODO
			}
		}
		else
		{
			super.doPost(request, response);
		}
	}
}

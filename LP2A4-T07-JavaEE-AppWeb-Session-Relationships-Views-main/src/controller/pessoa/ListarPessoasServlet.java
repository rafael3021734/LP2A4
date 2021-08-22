package controller.pessoa;

import java.io.IOException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Pessoa;
import model.repository.PessoaRepository;

/**
 * Servlet implementation class PessoaServlet
 */
@WebServlet({ "/pessoa", "/pessoa/listar" })
public class ListarPessoasServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListarPessoasServlet()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if ("OK".equals(session.getAttribute("usuarioAutenticado")))
		{
			int view = 1;
			String viewStr = (String) request.getParameter("view");
			
			if ("2".equals(viewStr))
				view = Integer.parseInt(viewStr); // view = 2;
			
			Set<Pessoa> pessoas = PessoaRepository.recuperarPessoas();
			
			request.setAttribute("pessoasCadastradas", pessoas);
			request.setAttribute("tituloPagina", "Cadastro de Pessoas");
			
			if (view == 1)
				request.setAttribute("pathPagina", "/pessoa/listar.jsp");
			else
				request.setAttribute("pathPagina", "/pessoa/listar2.jsp");
		}
		else
		{
			request.setAttribute("tituloPagina", "Acesso Negado!");
			request.setAttribute("pathPagina", "/unauthorized.jsp");
		}
		
		request.setAttribute("doServidor", true);
		
		RequestDispatcher rd = request.getRequestDispatcher("/template.jsp");
		
		rd.forward(request, response);
	}
}

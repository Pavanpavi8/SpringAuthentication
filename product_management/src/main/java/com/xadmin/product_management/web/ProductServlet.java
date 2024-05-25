package com.xadmin.product_management.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xadmin.product_management.bean.Product;
import com.xadmin.product_management.dao.productDoa;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private productDoa productDoa;
	public void init() throws ServletException {
		productDoa = new productDoa();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		String action = request.getServletPath();
		switch (action) {
		case "/new":
			showNewForm(request,response);
			break;
			
		case "/insert":
			insertProduct(request,response);
			break;
			
		case "/delete":
			deleteProduct(request,response);
			break;
			
		case "/edit":
			editProduct(request,response);
			break;
			
		case "/update":
			try {
				update_Product(request,response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		default:
			try {
				listProduct(request,response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
	
	//default
	
			private void listProduct(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException, SQLException {
				try {
					List<Product> listProduct = productDoa.selectAllProducts();
					request.setAttribute("listProduct", listProduct);
					RequestDispatcher dispatcher = request.getRequestDispatcher("Product-list.jsp");
					dispatcher.forward(request, response);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
	
		//new product
		private void showNewForm(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException {
			RequestDispatcher dispatcher = request.getRequestDispatcher("Product-form.jsp");
			dispatcher.forward(request, response);
		}
		
		// add
		private void insertProduct(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException {
			String name = request.getParameter("name");
			String strPrice = request.getParameter("price");
			String strStock = request.getParameter("stock");
			double price = 0;
			double stock = 0;
			if(strPrice != null && !strPrice.isEmpty()) {
				 price = Double.parseDouble(strPrice);
			}
			if(strStock != null && !strStock.isEmpty()) {
				 stock = Double.parseDouble(strStock);
			}
			Product newProduct = new Product (name,price,stock);
			productDoa.addProduct(newProduct);
			response.sendRedirect("list");
		}
		
		//delete
		private void deleteProduct(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException {
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				productDoa.deleteProuct(id);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect("list");
		}
		
		
		//edit
		
		private void editProduct(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException {
			int id = Integer.parseInt(request.getParameter("id"));
			
			Product existingProduct ;
			try {
				existingProduct = productDoa.selectProduct(id);
				RequestDispatcher dispatcher = request.getRequestDispatcher("Product-form.jsp");
				request.setAttribute("Product", existingProduct);
				dispatcher.forward(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		//update
		
		private void update_Product(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException, SQLException {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("pname");
			String strPrice = request.getParameter("price");
			String strStock = request.getParameter("pstocks");
			double price = 0;
			double stock = 0;
			if(strPrice != null && !strPrice.isEmpty()) {
				 price = Double.parseDouble(strPrice);
			}
			if(strStock != null && !strStock.isEmpty()) {
				 stock = Double.parseDouble(strStock);
			}
			Product newProduct = new Product (id,name,price,stock);
			productDoa.updateProduct(newProduct);
			response.sendRedirect("list");
		}
		
		
		
	}

	
	



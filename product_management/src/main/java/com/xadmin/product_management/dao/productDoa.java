package com.xadmin.product_management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xadmin.product_management.bean.Product;

public class productDoa {
		
	private String url = "jdbc:mysql://localhost:3306/authenticate?useSSl=false";
	private String user = "root";
	private String password= "root";
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	
	private static final String Insert_product_sql = "insert into product" + "(pname,price,pstocks)VALUES" + "(?,?,?);";
	private static final String Select_product_by_id = "select id,pname,price,pstocks from product where id =?";
	private static final String Select_all_products = "select * from product";
	private static final String Delete_product_sql = "delete from product where id=?";
	private static final String update_product_sql ="update product set pname=?,price =?,pstocks=? ,where id=?";
	
	
	public productDoa() {
		
	}
	
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url,user,password);
			
			}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	
	//add product
	public void addProduct(Product product) {
		
		System.out.println();
		try {
			Connection connection = getConnection();
			PreparedStatement  preparedstatement = connection.prepareStatement(Insert_product_sql);
			preparedstatement.setString(1,product.getName());
			preparedstatement.setDouble(2,product.getPrice());
			preparedstatement.setDouble(3,product.getStocks());
			
			System.out.println(preparedstatement);
			preparedstatement.executeUpdate();
		}
		catch(SQLException e) {
			printSQLException(e);
		}
	}
		
	// select by id
	
	public Product selectProduct(int id) {
		Product product = null;
		try(Connection connection = getConnection();
				PreparedStatement peparedstatement = connection.prepareStatement(Select_product_by_id);){
			peparedstatement.setInt(1,id);
			System.out.println(peparedstatement);
			ResultSet rs = peparedstatement.executeQuery();
			
			while(rs.next()) {	
				String name = rs.getString("pname");
				double price = rs.getDouble("price");
				double pstocks = rs.getDouble("pstocks");
				product = new Product (id , name , price ,pstocks);
			}
		}catch(SQLException e) {
			printSQLException(e);
		}
		return product;
	}
	
	
	
	
	//select all products
	
	public List<Product> selectAllProducts() throws SQLException{
		List <Product> product = new ArrayList<Product>();
		
		try(Connection connection = getConnection();
				PreparedStatement preparedstatement = connection.prepareStatement(Select_all_products);){
			System.out.println(preparedstatement);
			ResultSet rs = preparedstatement.executeQuery();
			
			while(rs.next()) {
				int id =rs.getInt("id");
				String name= rs.getString("pname");
				double price = rs.getDouble("price");
				double stocks = rs.getDouble("pstocks");
				product.add(new Product (id,name,price,stocks));
			}
			
		}
		catch(SQLException e) {
			printSQLException(e);
		}
		return product;
	}
	
	
	
	//update products
	
	public boolean updateProduct(Product product) throws SQLException {
		boolean rowUpdated;
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(update_product_sql);){
			System.out.println("Updated Poduct" + statement);
			statement.setInt(1, product.getId());
			statement.setString(2,product.getName());
			statement.setDouble(3,product.getPrice());
			statement.setDouble(4,product.getStocks());
			
			rowUpdated = statement.executeUpdate()>0;
		}
		return rowUpdated;
	}
	
	
	// delete users;
	
	public boolean deleteProuct(int id) throws SQLException{
		boolean rowDeleted;
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(Delete_product_sql);){
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate()>0;
		}
		return rowDeleted;
	}
	
	
	
	
	
	
		private void printSQLException(SQLException ex) {
			for(Throwable e : ex) {
				if(e instanceof SQLException) {
					e.printStackTrace(System.err);
					System.err.println("SQL State" + ((SQLException) e ).getSQLState());
					System.err.println("Error Code" + ((SQLException) e ).getErrorCode());
					System.err.println("Message" + e.getMessage());
					Throwable t = ex.getCause();
					while(t!=null) {
						System.out.println("cause" + t);
						t =t.getCause();
					}
				}
			}
		}
	
	

	

	

	
}



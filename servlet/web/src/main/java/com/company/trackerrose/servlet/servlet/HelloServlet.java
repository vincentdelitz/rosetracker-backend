package com.company.trackerrose.servlet.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import com.company.trackerrose.servlet.servlet.HelloUtil;

//Philipps Importe
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private String AccessToken = "eyJhbGciOiJSUzI1NiIsImprdSI6Imh0dHBzOi8vcDIwMDEzNDgzNzl0cmlhbC5hdXRoZW50aWNhdGlvbi5ldTEwLmhhbmEub25kZW1hbmQuY29tL3Rva2VuX2tleXMiLCJraWQiOiJrZXktaWQtMSIsInR5cCI6IkpXVCJ9.eyJqdGkiOiIwZjYwODFkMTlhMzY0OWM2OGRhOTYyZmIxMDI0MjE5MSIsImV4dF9hdHRyIjp7ImVuaGFuY2VyIjoiWFNVQUEiLCJ6ZG4iOiJwMjAwMTM0ODM3OXRyaWFsIiwic2VydmljZWluc3RhbmNlaWQiOiJhODY3OThiZC00YmVjLTRiZmYtODYxNS0xOGExNzIwOGRjMzkifSwieHMuc3lzdGVtLmF0dHJpYnV0ZXMiOnsieHMucm9sZWNvbGxlY3Rpb25zIjpbXX0sImdpdmVuX25hbWUiOiJQaGlsaXBwIiwieHMudXNlci5hdHRyaWJ1dGVzIjp7fSwiZmFtaWx5X25hbWUiOiJMYW5kbGVyIiwic3ViIjoiOGJhMWY2NjQtZWU3My00YzIyLWIzZDYtNjg0ZDA4MDc3MzE3Iiwic2NvcGUiOlsib3BlbmlkIl0sImNsaWVudF9pZCI6InNiLWE4Njc5OGJkLTRiZWMtNGJmZi04NjE1LTE4YTE3MjA4ZGMzOSFiMTUxMDZ8bmEtNDIwYWRmYzktZjk2ZS00MDkwLWE2NTAtMDM4Njk4OGI2N2UwIWIxODM2IiwiY2lkIjoic2ItYTg2Nzk4YmQtNGJlYy00YmZmLTg2MTUtMThhMTcyMDhkYzM5IWIxNTEwNnxuYS00MjBhZGZjOS1mOTZlLTQwOTAtYTY1MC0wMzg2OTg4YjY3ZTAhYjE4MzYiLCJhenAiOiJzYi1hODY3OThiZC00YmVjLTRiZmYtODYxNS0xOGExNzIwOGRjMzkhYjE1MTA2fG5hLTQyMGFkZmM5LWY5NmUtNDA5MC1hNjUwLTAzODY5ODhiNjdlMCFiMTgzNiIsImdyYW50X3R5cGUiOiJwYXNzd29yZCIsInVzZXJfaWQiOiI4YmExZjY2NC1lZTczLTRjMjItYjNkNi02ODRkMDgwNzczMTciLCJvcmlnaW4iOiJsZGFwIiwidXNlcl9uYW1lIjoicGhpbGlwcC5sYW5kbGVyQGdtYWlsLmNvbSIsImVtYWlsIjoicGhpbGlwcC5sYW5kbGVyQGdtYWlsLmNvbSIsImF1dGhfdGltZSI6MTU2MTQ0Njk0OSwicmV2X3NpZyI6IjQ2Y2M1N2I5IiwiaWF0IjoxNTYxNDQ2OTQ5LCJleHAiOjE1NjE0OTAxNDksImlzcyI6Imh0dHA6Ly9wMjAwMTM0ODM3OXRyaWFsLmxvY2FsaG9zdDo4MDgwL3VhYS9vYXV0aC90b2tlbiIsInppZCI6IjViODBiZWE0LTQyZDEtNDI3OC1iZjZhLWUwMDYxNzE1OTYzMiIsImF1ZCI6WyJzYi1hODY3OThiZC00YmVjLTRiZmYtODYxNS0xOGExNzIwOGRjMzkhYjE1MTA2fG5hLTQyMGFkZmM5LWY5NmUtNDA5MC1hNjUwLTAzODY5ODhiNjdlMCFiMTgzNiIsIm9wZW5pZCJdfQ.YcNqO9_sSdUArN5pcRsz05x_SUynoQKt1a9cllvZbR7YvenZrQVT6KRN9gkEe822TbZefMv1R468rF1g1isrX146gHy7AfcFPFyMPPzUi3c9uoEJHDXccASzW3JqGBv6mmiB9l5OgAt05whL91LJ1vnls1F4YbZD1snxXZdLRWQtRdfNH0asQn5X9Xt1g2Bp02Ld62uTahB8CEFnuDB9dZKEPPwUfPYb0g5YKaVS2qkg6nkKvf-Bs0JZiR_VNfwK-WceaQ96nVJc5SbIwlKg-U2UePIowzCNQD5AXPSlOTFSMh9HIx0TN0trR2Zdgg1TgENCjRDq27uMtDlic1R0nvX0IW1mInpVM5GXfQk3kTZqMOQng3dLvnUK8-F-N7wW4RlTXCE-PqAi93-A7SBrlqIOsYrumiSxJRjiChaGAhWqI6eBLh7Iy2PAZ9H_n0BwSNJzeCgEmvRvAOA1hpoTW41y279v2jMaNNa4AndmAyY4nyksLYzDOeJyNZdM7FO9e3G_DJtO34ErO65jXn_4B5JhU4ixo3KhUh4_QV6M5O0zeuw1sRSIh6cl6A3AhtnACp_r05SOC1pIY3IfSQqa7C97QdxwdXB9UYdS2DMpUGnQme7t7yuV2cvQBGZ9VIe1ZrjLWCnb0ghE-hD5x4G4GlSu58aBYfexvljKsNYeuJc";
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	DataOutputStream dataOut = null;
             BufferedReader in =null;
             
             int responseCode = 0;

             try {

               String url = "https://blockchain-service.cfapps.eu10.hana.ondemand.com/blockchain/proofOfHistory/api/v1/histories/{id}";


               //Available API Endpoints
               //https://blockchain-service.cfapps.sap.hana.ondemand.com/blockchain/proofOfHistory/api/v1
               //https://blockchain-service.cfapps.eu10.hana.ondemand.com/blockchain/proofOfHistory/api/v1
               //https://blockchain-service.cfapps.us10.hana.ondemand.com/blockchain/proofOfHistory/api/v1

               URL urlObj = new URL(url);
               HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
               //setting request method
               connection.setRequestMethod("GET");

               //adding headers
               connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
               connection.setRequestProperty("Accept","application/json");


               //Available Security Schemes for productive API Endpoints
               //OAuth 2.0
               
               String userCredentials = "eyJhbGciOiJSUzI1NiIsImprdSI6Imh0dHBzOi8vcDIwMDEzNDgzNzl0cmlhbC5hdXRoZW50aWNhdGlvbi5ldTEwLmhhbmEub25kZW1hbmQuY29tL3Rva2VuX2tleXMiLCJraWQiOiJrZXktaWQtMSIsInR5cCI6IkpXVCJ9.eyJqdGkiOiIwZjYwODFkMTlhMzY0OWM2OGRhOTYyZmIxMDI0MjE5MSIsImV4dF9hdHRyIjp7ImVuaGFuY2VyIjoiWFNVQUEiLCJ6ZG4iOiJwMjAwMTM0ODM3OXRyaWFsIiwic2VydmljZWluc3RhbmNlaWQiOiJhODY3OThiZC00YmVjLTRiZmYtODYxNS0xOGExNzIwOGRjMzkifSwieHMuc3lzdGVtLmF0dHJpYnV0ZXMiOnsieHMucm9sZWNvbGxlY3Rpb25zIjpbXX0sImdpdmVuX25hbWUiOiJQaGlsaXBwIiwieHMudXNlci5hdHRyaWJ1dGVzIjp7fSwiZmFtaWx5X25hbWUiOiJMYW5kbGVyIiwic3ViIjoiOGJhMWY2NjQtZWU3My00YzIyLWIzZDYtNjg0ZDA4MDc3MzE3Iiwic2NvcGUiOlsib3BlbmlkIl0sImNsaWVudF9pZCI6InNiLWE4Njc5OGJkLTRiZWMtNGJmZi04NjE1LTE4YTE3MjA4ZGMzOSFiMTUxMDZ8bmEtNDIwYWRmYzktZjk2ZS00MDkwLWE2NTAtMDM4Njk4OGI2N2UwIWIxODM2IiwiY2lkIjoic2ItYTg2Nzk4YmQtNGJlYy00YmZmLTg2MTUtMThhMTcyMDhkYzM5IWIxNTEwNnxuYS00MjBhZGZjOS1mOTZlLTQwOTAtYTY1MC0wMzg2OTg4YjY3ZTAhYjE4MzYiLCJhenAiOiJzYi1hODY3OThiZC00YmVjLTRiZmYtODYxNS0xOGExNzIwOGRjMzkhYjE1MTA2fG5hLTQyMGFkZmM5LWY5NmUtNDA5MC1hNjUwLTAzODY5ODhiNjdlMCFiMTgzNiIsImdyYW50X3R5cGUiOiJwYXNzd29yZCIsInVzZXJfaWQiOiI4YmExZjY2NC1lZTczLTRjMjItYjNkNi02ODRkMDgwNzczMTciLCJvcmlnaW4iOiJsZGFwIiwidXNlcl9uYW1lIjoicGhpbGlwcC5sYW5kbGVyQGdtYWlsLmNvbSIsImVtYWlsIjoicGhpbGlwcC5sYW5kbGVyQGdtYWlsLmNvbSIsImF1dGhfdGltZSI6MTU2MTQ0Njk0OSwicmV2X3NpZyI6IjQ2Y2M1N2I5IiwiaWF0IjoxNTYxNDQ2OTQ5LCJleHAiOjE1NjE0OTAxNDksImlzcyI6Imh0dHA6Ly9wMjAwMTM0ODM3OXRyaWFsLmxvY2FsaG9zdDo4MDgwL3VhYS9vYXV0aC90b2tlbiIsInppZCI6IjViODBiZWE0LTQyZDEtNDI3OC1iZjZhLWUwMDYxNzE1OTYzMiIsImF1ZCI6WyJzYi1hODY3OThiZC00YmVjLTRiZmYtODYxNS0xOGExNzIwOGRjMzkhYjE1MTA2fG5hLTQyMGFkZmM5LWY5NmUtNDA5MC1hNjUwLTAzODY5ODhiNjdlMCFiMTgzNiIsIm9wZW5pZCJdfQ.YcNqO9_sSdUArN5pcRsz05x_SUynoQKt1a9cllvZbR7YvenZrQVT6KRN9gkEe822TbZefMv1R468rF1g1isrX146gHy7AfcFPFyMPPzUi3c9uoEJHDXccASzW3JqGBv6mmiB9l5OgAt05whL91LJ1vnls1F4YbZD1snxXZdLRWQtRdfNH0asQn5X9Xt1g2Bp02Ld62uTahB8CEFnuDB9dZKEPPwUfPYb0g5YKaVS2qkg6nkKvf-Bs0JZiR_VNfwK-WceaQ96nVJc5SbIwlKg-U2UePIowzCNQD5AXPSlOTFSMh9HIx0TN0trR2Zdgg1TgENCjRDq27uMtDlic1R0nvX0IW1mInpVM5GXfQk3kTZqMOQng3dLvnUK8-F-N7wW4RlTXCE-PqAi93-A7SBrlqIOsYrumiSxJRjiChaGAhWqI6eBLh7Iy2PAZ9H_n0BwSNJzeCgEmvRvAOA1hpoTW41y279v2jMaNNa4AndmAyY4nyksLYzDOeJyNZdM7FO9e3G_DJtO34ErO65jXn_4B5JhU4ixo3KhUh4_QV6M5O0zeuw1sRSIh6cl6A3AhtnACp_r05SOC1pIY3IfSQqa7C97QdxwdXB9UYdS2DMpUGnQme7t7yuV2cvQBGZ9VIe1ZrjLWCnb0ghE-hD5x4G4GlSu58aBYfexvljKsNYeuJc";
               //String basicAuth = "Bearer " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
               String basicAuth = "Bearer " + AccessToken;
               
               connection.setRequestProperty ("Authorization", basicAuth);


               connection.setDoInput(true);

               responseCode = connection.getResponseCode();
               in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
               String inputLine;
               StringBuffer res = new StringBuffer();
               while ((inputLine = in.readLine()) != null) {
                 res.append(inputLine);
               }

               //printing response
               System.out.println(res.toString());

             } catch (Exception e) {
               //do something with exception
               e.printStackTrace();
             } finally {
               try {
                 if(dataOut != null) {
                   dataOut.close();
                 }
                 if(in != null) {
                   in.close();
                 }

               } catch (IOException e) {
                 //do something with exception
                 e.printStackTrace();
               }
             }
             
            try (OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8")) {
				writer.write("Code: " + responseCode);
            }
	}

/*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		Connection conn = null;
		try {
			conn = getConnection();
		} catch (SQLException e) {
			throw new ServletException(e.getMessage(), e);
		}
		try (OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8")) {
			writer.write(HelloUtil.getHelloMessage());
			writer.write("\n\nJDBC connection available: ");
			if (conn != null) {
				writer.write("yes");
				writer.write("\n\nCurrent Hana DB user:\n");
				String userName = getCurrentUser(conn);
				writer.write(userName);
				writer.write("\n\nCurrent Hana schema:\n");
				writer.write(getCurrentSchema(conn));
			} else {
				writer.write("no");
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
	*/

	private String getCurrentUser(Connection conn) throws SQLException {
		String currentUser = "";
		PreparedStatement prepareStatement = conn.prepareStatement("SELECT CURRENT_USER \"current_user\" FROM DUMMY;");
		ResultSet resultSet = prepareStatement.executeQuery();
		int column = resultSet.findColumn("current_user");
		while (resultSet.next()) {
			currentUser += resultSet.getString(column);
		}
		return currentUser;
	}

	private String getCurrentSchema(Connection conn) throws SQLException {
		String currentSchema = "";
		PreparedStatement prepareStatement = conn.prepareStatement("SELECT CURRENT_SCHEMA \"current_schema\" FROM DUMMY;");
		ResultSet resultSet = prepareStatement.executeQuery();
		int column = resultSet.findColumn("current_schema");
		while (resultSet.next()) {
			currentSchema += resultSet.getString(column);
		}
		return currentSchema;
	}

	private Connection getConnection() throws SQLException {
		try {
			Context ctx = new InitialContext();
			Context xmlContext = (Context) ctx.lookup("java:comp/env");
			DataSource ds = (DataSource) xmlContext.lookup("jdbc/DefaultDB");
			Connection conn = ds.getConnection();
			System.out.println("Connected to database");
			return conn;
		} catch (NamingException ignorred) {
			// could happen if HDB support is not enabled
			return null;
		}
	}
}

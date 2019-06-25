package leonardo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlockchainServlet extends HttpServlet {

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse res)
			throws ServletException, IOException {
		DataOutputStream dataOut = null;
		BufferedReader in = null;

		try {

			String url = "https://blockchain-service.cfapps.eu10.hana.ondemand.com/blockchain/proofOfHistory/api/v1/histories/{id}";

			// Available API Endpoints
			// https://blockchain-service.cfapps.sap.hana.ondemand.com/blockchain/proofOfHistory/api/v1
			// https://blockchain-service.cfapps.eu10.hana.ondemand.com/blockchain/proofOfHistory/api/v1
			// https://blockchain-service.cfapps.us10.hana.ondemand.com/blockchain/proofOfHistory/api/v1

			URL urlObj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
			// setting request method
			connection.setRequestMethod("GET");

			// adding headers
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept", "application/json");

			// Available Security Schemes for productive API Endpoints
			// OAuth 2.0

			connection.setDoInput(true);

			int responseCode = connection.getResponseCode();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				//response.append("\n");
			}

			// printing response
			System.out.println(response.toString());

		} catch (Exception e) {
			// do something with exception
			e.printStackTrace();
		} finally {
			try {
				if (dataOut != null) {
					dataOut.close();
				}
				if (in != null) {
					in.close();
				}

			} catch (IOException e) {
				// do something with exception
				e.printStackTrace();
			}
		}
	}



	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse res)
			throws ServletException, IOException {
		DataOutputStream dataOut = null;
		BufferedReader in = null;

		try {

			String url = "https://blockchain-service.cfapps.eu10.hana.ondemand.com/blockchain/proofOfHistory/api/v1/histories/{id}";

			// Available API Endpoints
			// https://blockchain-service.cfapps.sap.hana.ondemand.com/blockchain/proofOfHistory/api/v1
			// https://blockchain-service.cfapps.eu10.hana.ondemand.com/blockchain/proofOfHistory/api/v1
			// https://blockchain-service.cfapps.us10.hana.ondemand.com/blockchain/proofOfHistory/api/v1

			URL urlObj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
			// setting request method
			connection.setRequestMethod("POST");

			// adding headers
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept", "application/json");

			// Available Security Schemes for productive API Endpoints
			// OAuth 2.0

			connection.setDoInput(true);

			// sending POST request
			connection.setDoOutput(true);

			int responseCode = connection.getResponseCode();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				//response.append("\n");
			}

			// printing response
			System.out.println(response.toString());

		} catch (Exception e) {
			// do something with exception
			e.printStackTrace();
		} finally {
			try {
				if (dataOut != null) {
					dataOut.close();
				}
				if (in != null) {
					in.close();
				}

			} catch (IOException e) {
				// do something with exception
				e.printStackTrace();
			}
		}
	}
}

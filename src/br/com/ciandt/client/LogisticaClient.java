package br.com.ciandt.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.json.JSONObject;

import br.com.ciandt.controller.ManipuladorDeArquivos;
import br.com.ciandt.model.Rota;

public class LogisticaClient {

	public static void main(String[] args) {
		String diretorio = "C:\\PV\\malha.txt";
		try {

			ManipuladorDeArquivos arquivos = new ManipuladorDeArquivos();
			List<Rota> listaRotas = arquivos.lerArquivo(diretorio);
			JSONObject mainObj = arquivos.listRotaToJsonList(listaRotas);

			System.out.println(mainObj);

			try {
				URL url = new URL("http://localhost:8080/CIandT/api/carregarRotas");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(mainObj.toString());
				out.close();

				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				while (in.readLine() != null) {
				}
				System.out.println("\nWebservices CIandT invocado com Sucesso");
				in.close();
			} catch (Exception e) {
				System.out.println("\nErro ao chamar Webservices CIandT");
				System.out.println(e);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

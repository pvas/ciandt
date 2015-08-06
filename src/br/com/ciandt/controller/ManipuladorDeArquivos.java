package br.com.ciandt.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.ciandt.model.Rota;

public class ManipuladorDeArquivos {
	/**
	 * Método responsável por ler um arquivo, manipular suas informações e
	 * retornar uma lista de rotas
	 * 
	 * @param caminho
	 * @return
	 */
	public List<Rota> lerArquivo(String caminho) {
		Scanner scanner = null;
		Rota rota;
		List<Rota> listaRotas = new ArrayList<>();

		try {
			scanner = new Scanner(new FileReader(caminho));
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao tentar abrir o arquivo. " + e.getMessage());
		}

		if (scanner != null) {
			while (scanner.hasNext()) {
				rota = new Rota();
				try {
					rota.setId(gerarGeoId());
					rota.setOrigem(scanner.next().trim());
					rota.setDestino(scanner.next().trim());
					rota.setDistancia(Integer.valueOf(scanner.next().trim()));
				} catch (Exception e) {
					System.out.println("Dados incorretos. " + e.getMessage());
				}
				listaRotas.add(rota);
			}
		}

		return listaRotas;
	}

	public JSONObject listRotaToJsonList(List<Rota> listaRota) {
		JSONObject mainObj = new JSONObject();

		try {
			JSONArray ja = new JSONArray();

			if (listaRota != null) {
				for (Rota rota : listaRota) {
					ja.put(rotaToJson(rota));
				}
			}

			mainObj.put("rotas", ja);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainObj;
	}

	public List<Rota> jsonListToListRota(String string) {
		List<Rota> listaRotas = new ArrayList<Rota>();
		try {

			if (string != null) {
				JSONObject json = new JSONObject(string);
				JSONArray jsonArray = json.getJSONArray("rotas");

				if (jsonArray != null) {
					int len = jsonArray.length();

					for (int i = 0; i < len; i++) {
						listaRotas.add(jsonToRota(jsonArray.get(i).toString()));
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return listaRotas;
	}

	public Rota jsonToRota(String string) {
		Rota rota = null;
		if (string != null) {
			try {
				JSONObject json = new JSONObject(string);
				rota = new Rota();
				rota.setId(json.getString("id"));
				rota.setOrigem(json.getString("origem"));
				rota.setDestino(json.getString("destino"));
				rota.setDistancia(json.getInt("distancia"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return rota;
	}

	public JSONObject rotaToJson(Rota rota) {
		JSONObject jo = null;
		try {

			if (rota != null) {
				jo = new JSONObject();
				jo.put("id", rota.getId());
				jo.put("origem", rota.getOrigem());
				jo.put("destino", rota.getDestino());
				jo.put("distancia", rota.getDistancia());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jo;
	}

	protected String gerarGeoId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}

package br.com.ciandt.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.ciandt.controller.ManipuladorDeArquivos;
import br.com.ciandt.entidade.RotaEntity;
import br.com.ciandt.model.Rota;

@Path("/")
public class LogisticaService {
	private ManipuladorDeArquivos arquivos = new ManipuladorDeArquivos();
	private RotaEntity rotaEntity = new RotaEntity();
	
	@POST
	@Path("/carregarRotas")
	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response crunchifyREST(InputStream entrada) {
		inicializar();
		
		StringBuilder strBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(entrada));
			String linha = null;

			while ((linha = in.readLine()) != null) {
				strBuilder.append(linha);
			}
			
			List<Rota> listaRota = arquivos.jsonListToListRota(strBuilder.toString());
			
			System.out.println("Rotas recebidas: " + listaRota.size());
			for (Rota rota : listaRota) {
				salvarRota(rota);
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao ler JSON: - " + e.getMessage());
		}
 
		return Response.status(200).entity(strBuilder.toString()).build();
	}
 
	@GET
	@Path("/buscarMelhorRota")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verifyRESTService(InputStream entrada) {
		inicializar();
		String retorno = "Webservices CIandT inicializado corretamente";
		return Response.status(200).entity(retorno).build();
	}
	
	private void salvarRota(Rota rota){
		if(rota != null){
			rotaEntity.inserirRota(rota);
			System.out.println("Rota: " + rota.toString());
		} else {
			System.out.println("Nao e possivel inserir uma Rota nula");
		}
	}
	
	private List<Rota> listarRotas(){
		return rotaEntity.getRotas();
	}
	
	private void inicializar(){
		try {
			org.h2.tools.Server.createTcpServer().start();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package br.com.ciandt.execucao;

import java.util.List;

import br.com.ciandt.controller.ManipuladorDeArquivos;
import br.com.ciandt.model.Rota;

public class Processador {

	public static void main(String[] args) {
		String diretorio = "E:\\PV\\malha.txt";
		Processador processador = new Processador();

		ManipuladorDeArquivos leitorArquivos = new ManipuladorDeArquivos();
		List<Rota> listaRotas = leitorArquivos.lerArquivo(diretorio);
		for (Rota rota : listaRotas) {
			System.out.println(rota.toString());
		}
	}

}

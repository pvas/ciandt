package br.com.ciandt.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.ciandt.estrutura.Grafo;
import br.com.ciandt.estrutura.Vertice;

public class Dijkstra {

	// Atributos usados na fun��o encontrarMenorCaminho

	// Lista que guarda os v�rtices pertencentes ao menor caminho encontrado
	List<Vertice> menorCaminho = new ArrayList<Vertice>();

	// Vari�vel que recebe os v�rtices pertencentes ao menor caminho
	Vertice verticeCaminho = new Vertice();

	// Vari�vel que guarda o v�rtice que est� sendo visitado
	Vertice atual = new Vertice();

	// Vari�vel que marca o vizinho do v�rtice atualmente visitado
	Vertice vizinho = new Vertice();

	// Corte de v�rtices que j� tiveram suas dist�ncias marcadas e cujos
	// vizinhos n�o foram visitados
	List<Vertice> fronteira = new ArrayList<Vertice>();

	// Guarda o n�mero de v�rtices n�o visitados
	int verticesNaoVisitados;

	// Algoritmo de Dijkstra
	public List<Vertice> encontrarMenorCaminhoDijkstra(Grafo grafo, Vertice v1, Vertice v2) {

		// No in�cio, todos os v�rtices do grafo n�o foram visitados
		verticesNaoVisitados = grafo.getVertices().size();

		// O primeiro n� a ser visitado � o da origem do caminho
		atual = v1;
		// Adiciona o primeiro n� no corte
		fronteira.add(atual);
		// Adiciona a origem na lista do menor caminho
		menorCaminho.add(atual);

		// Colocando a distancias iniciais
		for (int i = 0; i < grafo.getVertices().size(); i++) {

			// N� atual tem dist�ncia zero, e todos os outros, 9999(infinita)
			if (grafo.getVertices().get(i).getDescricao().equals(atual.getDescricao())) {

				grafo.getVertices().get(i).setDistancia(0);

			} else {

				grafo.getVertices().get(i).setDistancia(9999);

			}
		}

		// O algoritmo continua at� que todos os v�rtices sejam visitados
		while (verticesNaoVisitados != 0) {

			// Toma-se sempre o v�rtice com menor dist�ncia, que � o primeiro da
			// lista do corte
			atual = this.fronteira.get(0);
			/*
			 * Para cada vizinho (cada aresta), calcula-se a sua poss�vel
			 * dist�ncia, somando a dist�ncia do v�rtice atual com a da aresta
			 * correspondente. Se essa dist�ncia for menor que a dist�ncia do
			 * vizinho, esta � atualizada.
			 */
			for (int i = 0; i < atual.getArestas().size(); i++) {

				vizinho = atual.getArestas().get(i).getDestino();
				if (!vizinho.verificarVisita()) {

					vizinho.setPai(atual);

					// Comparando a dist�ncia do vizinho com a poss�vel
					// dist�ncia
					if (vizinho.getDistancia() > (atual.getDistancia() + atual.getArestas().get(i).getPeso())) {

						vizinho.setDistancia(atual.getDistancia() + atual.getArestas().get(i).getPeso());

						/*
						 * Se o vizinho � o v�rtice procurado, e foi feita uma
						 * mudan�a na dist�ncia, a lista com o menor caminho
						 * anterior � apagada, pois existe um caminho menor
						 * ainda. Cria-se a nova lista do menor caminho, com os
						 * v�rtices pais, at� o v�rtice origem.
						 */
						if (vizinho == v2) {
							menorCaminho.clear();
							verticeCaminho = vizinho;
							menorCaminho.add(vizinho);
							while (verticeCaminho.getPai() != null) {

								menorCaminho.add(verticeCaminho.getPai());
								verticeCaminho = verticeCaminho.getPai();

							}
							// Ordena a lista do menor caminho, para que ele
							// seja exibido da origem ao destino.
							Collections.sort(menorCaminho);

						}
					}
					// Cada vizinho, depois de visitado, � adicionado ao corte
					this.fronteira.add(vizinho);
				}

			}
			// Marca o v�rtice atual como visitado e o retira do corte
			atual.visitar();
			verticesNaoVisitados--;
			this.fronteira.remove(atual);
			/*
			 * Ordena a lista do corte, para que o v�rtice com menor dist�ncia
			 * fique na primeira posi��o
			 */

			Collections.sort(fronteira);

		}

		return menorCaminho;
	}

}
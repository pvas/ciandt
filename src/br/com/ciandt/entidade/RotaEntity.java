package br.com.ciandt.entidade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.ciandt.dao.BaseDao;
import br.com.ciandt.model.Rota;

public class RotaEntity extends BaseDao {
	
	private String BUSCA_ROTAS = "select * from ROTA where origem = '&origem' or destino = '&destino' order by origem, destino";

	public void inserirRota(Rota rota) {
		try {

			Statement stat = getConn().createStatement();

			StringBuilder stringBuilder = new StringBuilder("insert into ROTA values('");
			stringBuilder.append(rota.getId());
			stringBuilder.append("','");
			stringBuilder.append(rota.getOrigem());
			stringBuilder.append("','");
			stringBuilder.append(rota.getDestino());
			stringBuilder.append("',");
			stringBuilder.append(rota.getDistancia());
			stringBuilder.append(")");
			stat.execute(stringBuilder.toString());

			stat.close();
			getConn().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Rota> getRotas() {
		List<Rota> listaRotas = new ArrayList<Rota>();
		Rota rota;
		Statement stat;
		try {
			stat = getConn().createStatement();

			ResultSet rs;
			rs = stat.executeQuery("select * from ROTA");
			while (rs.next()) {
				rota = new Rota();
				rota.setId(rs.getString("id"));
				rota.setOrigem(rs.getString("origem"));
				rota.setDestino(rs.getString("destino"));
				rota.setDistancia(rs.getInt("distancia"));
				listaRotas.add(rota);
			}
			stat.close();
			getConn().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaRotas;
	}
	
	public List<Rota> getRotasPorOrigemEDestino(String origem, String destino) {
		List<Rota> listaRotas = new ArrayList<Rota>();
		Rota rota;
		Statement stat;
		try {
			stat = getConn().createStatement();

			ResultSet rs;
			rs = stat.executeQuery(BUSCA_ROTAS.replace("&origem", origem).replace("&destino", destino));
			while (rs.next()) {
				rota = new Rota();
				rota.setId(rs.getString("id"));
				rota.setOrigem(rs.getString("origem"));
				rota.setDestino(rs.getString("destino"));
				rota.setDistancia(rs.getInt("distancia"));
				listaRotas.add(rota);
			}
			stat.close();
			getConn().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaRotas;
	}
	
}
package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import classes.Produto;
import conexao.Conexao;

public class ProdutoDAO {
	private Conexao conexao;
	private String schema;
	
	PreparedStatement pInclusao;
	PreparedStatement pAlteracao;
	PreparedStatement pExclusao;
	
	public ProdutoDAO(Conexao conexao, String schema) {
		this.conexao = conexao;
		this.schema = schema;
		prepararSqlInclusao();
        prepararSqlAlteracao();
        prepararSqlExclusao();
	}
	
	private void prepararSqlExclusao() {
		String sql = "delete from "+ this.schema + ".produto";
		sql += " where idproduto = ?";
		
		try {
			this.pExclusao = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlInclusao() {
		String sql = "insert into "+ this.schema + ".produto";	
		sql += " (nome, codigo, descricao, quantidade, valorUni, valorDeVenda,dataHora)";
		sql += " values ";
		sql += " (?, ?, ?, ?, ?, ?,?)";
		
		try {
			this.pInclusao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlAlteracao() {
		String sql = "update "+ this.schema + ".produto";	
		sql += " set nome = ?,";
		sql += " codigo = ?,";
		sql += " descricao = ?,";
		sql += " quantidade = ?,";
		sql += " valorUni = ?,";
		sql += " valorDeVenda = ?";
		sql += " dataHora = ?";
		sql += " where idproduto = ?";
		
		try {
			this.pAlteracao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	public int alterarProduto(Produto produto) {
		try {
			pAlteracao.setString(1, produto.getNome());
			pAlteracao.setInt(2, produto.getCodigo());
			pAlteracao.setString(3, produto.getDescricao());
			pAlteracao.setInt(4,  produto.getQuantidade());
			pAlteracao.setDouble(5, produto.getValorun());
			pAlteracao.setDouble(6, produto.getValorvenda());
			pAlteracao.setString(7, produto.getData_hora());
			pAlteracao.setInt(8, produto.getIdproduto());
			
			return pAlteracao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nProduto nao alterado.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public int incluirProduto(Produto produto) {
		try {		
							
			pInclusao.setString(1, produto.getNome());
			pInclusao.setInt(2, produto.getCodigo());
			pInclusao.setString(3, produto.getDescricao());
			pInclusao.setInt(4, produto.getQuantidade());
			pInclusao.setDouble(5, produto.getValorun());
			pInclusao.setDouble(6, produto.getValorvenda());
			pInclusao.setString(7,  produto.getData_hora());
			
			return pInclusao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nProduto nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public int excluirProduto(Produto produto) {
		try {
			pExclusao.setInt(1, produto.getIdproduto());
			
			return pExclusao.executeUpdate();
		} catch  (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nProduto nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	@SuppressWarnings("exports")
	public ResultSet carregarProduto() {
		ResultSet tabela;				
		String sql = "select * from " + this.schema + ".produto order by idcliente";
		
		tabela = conexao.query(sql);
			
		return tabela;
	}
}

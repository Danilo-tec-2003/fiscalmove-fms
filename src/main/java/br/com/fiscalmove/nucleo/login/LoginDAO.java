package br.com.fiscalmove.nucleo.login;

import br.com.fiscalmove.nucleo.utils.ConexaoUtil;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Único lugar onde SQL de login é executado.
 * Nunca contém regra de negócio — só acessa o banco.
 */
public class LoginDAO {

    /**
     * Busca usuário ativo por login e valida a senha com BCrypt.
     * @return Usuario preenchido ou null se não encontrado.
     */
    public Usuario buscarPorLoginSenha(String login, String senha) throws SQLException {
        String sql  = "SELECT idusuario, nome, login, senha FROM usuario "
                    + "WHERE login = ? AND is_ativo = TRUE";
 
        try (Connection conn = ConexaoUtil.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setString(1, login);
 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && BCrypt.checkpw(senha, rs.getString("senha"))) {
                    return new Usuario(
                        rs.getInt("idusuario"),
                        rs.getString("nome"),
                        rs.getString("login")
                    );
                }
            }
        }
        return null;
    }

    public void inserirUsuario(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario (nome, login, senha, is_ativo) VALUES (?, ?, ?, true)";
        try (Connection conn = ConexaoUtil.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getLogin());
            ps.setString(3, BCrypt.hashpw(u.getSenha(), BCrypt.gensalt(12)));
            ps.executeUpdate();
        }
    }
    
    public boolean loginJaExiste(String login) throws SQLException {
        String sql = "SELECT 1 FROM usuario WHERE login = ?";
        try (Connection conn = ConexaoUtil.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }
}

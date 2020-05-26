/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Dao;

import br.com.rtec.Dal.ModuloConexao;
import br.com.rtec.Telas.TelaUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author ramsessf
 */
public class UsuarioDAO {
    
    private TelaUsuario view;
    // usando a variavel conexao do DAL
    Connection conexao = null;
    //Criando as variaves especiais para a conexao com o banco
    // Prepared Statement e ResultSet são frameworks do pacote java.sql
    //e servem para preparar e executar as instrucoes SQL
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public UsuarioDAO(TelaUsuario view){
        this.view = view;
        conexao = ModuloConexao.conector();
       
    }
    
    public UsuarioDAO(){
    }

     public void AdicionarDados(){

        view.getTxtUsuFone().setEnabled(true);
        view.getTxtUsuLogin().setEnabled(true);
        view.getTxtUsuSenha().setEnabled(true);
        view.getCmbUsuPerfil().setEnabled(true);
       
        view.getTxtUsuNome().setText("");
        view.getTxtUsuLogin().setText("");
        view.getTxtUsuSenha().setText("");
        view.getCmbUsuPerfil().setSelectedItem("");
        view.getTxtUsuFone().setText("");
    }
    
    public void cancelarOperacao(){
        view.getTxtUsuNome().setEnabled(false);
        view.getTxtUsuFone().setEnabled(false);
        view.getTxtUsuLogin().setEnabled(false);
        view.getTxtUsuSenha().setEnabled(false);
        view.getCmbUsuPerfil().setEnabled(false);
        view.getTxtUsuFone().setEnabled(false);
        
        view.getBtnUsuAdd().setEnabled(true);
        view.getBtnSalvar().setEnabled(false);
        view.getBtnUsuDel().setEnabled(false);
        view.getBtnUsuConsul().setEnabled(true);
               
    }

    public void consultarDados() {
        //Metodo para consulta usuarios na tela de cadastro de usuario
        String sql = "select * from tbusuarios where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, view.getTxtUsuId().getText());

            rs = pst.executeQuery();

            if (rs.next()) {
                //as linhas abaixo recuperam os valores dos respectivos campos
                //da tabela
                view.getTxtUsuNome().setText(rs.getString(2));
                view.getTxtUsuFone().setText(rs.getString(3));
                view.getTxtUsuLogin().setText(rs.getString(4));
                view.getTxtUsuSenha().setText(rs.getString(5));
                // a linha abaixo se refere ao combobox
                view.getCmbUsuPerfil().setSelectedItem(rs.getString(6));

            } else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                view.getTxtUsuNome().setText("");
                view.getTxtUsuFone().setText("");
                view.getTxtUsuLogin().setText("");
                view.getTxtUsuSenha().setText("");
                view.getCmbUsuPerfil().setSelectedItem("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
 

    public void gravarDados() {
        //Metodo para adicionar usuarios
        String sql = "insert into tbusuarios(iduser, usuario, fone, login, senha, perfil)values(?, ?, ?, ?, ?, ?);";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1,retornaIdAutomatico());
            pst.setString(2, view.getTxtUsuNome().getText());
            pst.setString(3, view.getTxtUsuFone().getText());
            pst.setString(4, view.getTxtUsuLogin().getText());
            pst.setString(5, view.getTxtUsuSenha().getText());
            pst.setString(6, view.getCmbUsuPerfil().getSelectedItem().toString());

            //valida se os campos estão preenchidos
            if ( view.getTxtUsuNome().getText().isEmpty()
                    || view.getTxtUsuLogin().getText().isEmpty()
                    || view.getTxtUsuSenha().getText().isEmpty()
                    || view.getCmbUsuPerfil().getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Prencha os campos obrigatórios!");
            } else {
                // a linha abaixo é usada para confirmar a insercao dos dados na tabela
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                   
                }
            }
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null,"Esse usuário já existe! Por favor tente outro.");

        }catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);

        }
    }

    //Método para Alterar os dados do usuário
    public void alterarDados() {
        view.getTxtUsuNome().setEnabled(true);
        view.getTxtUsuFone().setEnabled(true);
        view.getTxtUsuLogin().setEnabled(true);
        view.getTxtUsuSenha().setEnabled(true);
        view.getCmbUsuPerfil().setEnabled(true);
        view.getTxtUsuFone().setEnabled(true);
        view.getTxtUsuLogin().setEnabled(true);
        view.getTxtUsuSenha().setEnabled(true);
        
        String sql = "update tbusuarios set usuario=?, fone=?, login=?, senha=?, perfil=? where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, view.getTxtUsuNome().getText());
            pst.setString(2, view.getTxtUsuFone().getText());
            pst.setString(3, view.getTxtUsuLogin().getText());
            pst.setString(4, view.getTxtUsuSenha().getText());
            pst.setString(5, view.getCmbUsuPerfil().getSelectedItem().toString());
            pst.setString(6, view.getTxtUsuId().getText());

            //valida se os campos estão preenchidos
            if (view.getTxtUsuId().getText().isEmpty()
                    || view.getTxtUsuNome().getText().isEmpty()
                    || view.getTxtUsuLogin().getText().isEmpty()
                    || view.getTxtUsuSenha().getText().isEmpty()
                    || view.getCmbUsuPerfil().getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Prencha os campos obrigatórios!");
            } else {
                // a linha abaixo é usada para confirmar a insercao dos dados na tabela
                int alterado = pst.executeUpdate();
                if (alterado > 0) {
                    JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
                    view.getTxtUsuId().setText(null);
                    view.getTxtUsuNome().setText(null);
                    view.getTxtUsuFone().setText(null);
                    view.getTxtUsuLogin().setText(null);
                    view.getTxtUsuSenha().setText(null);
                    view.getCmbUsuPerfil().setSelectedItem(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void deletarDados() {

        // exitbe uma caixa de diálogo
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este usuário? ", "Atenção", JOptionPane.YES_NO_OPTION);

        String sql = "delete from tbusuarios where iduser =?";
        if (confirma == JOptionPane.YES_OPTION) {

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, view.getTxtUsuId().getText());

                if (view.getTxtUsuId().getText().isEmpty()
                        || view.getTxtUsuNome().getText().isEmpty()
                        || view.getTxtUsuLogin().getText().isEmpty()
                        || view.getTxtUsuSenha().getText().isEmpty()
                        || view.getCmbUsuPerfil().getSelectedItem().toString().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Prencha os campos obrigatórios!");
                } else {
                    // a linha abaixo é usada para confirmar a insercao dos dados na tabela
                    int deletado = pst.executeUpdate();
                    if (deletado > 0) {
                        JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso!");
                        view.getTxtUsuId().setText(null);
                        view.getTxtUsuNome().setText(null);
                        view.getTxtUsuFone().setText(null);
                        view.getTxtUsuLogin().setText(null);
                        view.getTxtUsuSenha().setText(null);
                        view.getCmbUsuPerfil().setSelectedItem(null);
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }

    }

    private String retornaIdAutomatico() {
         return "";
     }

    public void limparDadosDaTela() {
        
         view.getTxtUsuId().setText("");
         view.getTxtUsuNome().setText("");
         view.getTxtUsuFone().setText("");
         view.getTxtUsuLogin().setText("");
         view.getTxtUsuSenha().setText("");
         view.getCmbUsuPerfil().setSelectedItem("");
         
        }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Dao.UsuarioDAO;
import br.com.rtec.Telas.TelaUsuario;

/**
 *
 * @author ramsessf
 */
public class ControllerTelaUsuario {
    
    private TelaUsuario view;

    public ControllerTelaUsuario(TelaUsuario view){
        this.view = view;               
    }
    
    public void AdicionaNovosDadosDeUsuario(){
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       usuarioDAO.AdicionarDados();
    }
    
     public void GravaDadosDeUsuario(){
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       usuarioDAO.AdicionarDados();
    }
     
    public void DeletaDadosDeUsuario(){
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       usuarioDAO.deletarDados();
    }
    
    public void EditaDadosDeUsuario(){
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       usuarioDAO.alterarDados();
    }
    
    public void PesquisaDadosDeUsuario(){
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       usuarioDAO.consultarDados();
    }
    
    public void LimpaCamposDaTelaUsuario(){
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       usuarioDAO.limparDadosDaTela();
    }
    
    
    
}

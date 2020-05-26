/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rtec.Dal;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

/** 
 *
 * @author ramsessf
 */
public class ModuloConexao {
    //netodo responsavel por estabelecer uma conexao com o banco
    public  static java.sql.Connection conector(){
        java.sql.Connection conexao = null;
        //a linha abaixo "chama" o driver
        String driver = "com.mysql.jdbc.Driver";
        //armazenando informacoes referente ao banco
        String url = "jdbc:mysql://localhost:3306/dbinfox";
        String user = "root";
        String password = "";
        //Estabelecendo a conexao com o banco 
        try{
            Class.forName(driver);
            conexao =  DriverManager.getConnection(url,user,password);
            return conexao;
        
        }catch (Exception e){
            return null;
        }
    }
 }

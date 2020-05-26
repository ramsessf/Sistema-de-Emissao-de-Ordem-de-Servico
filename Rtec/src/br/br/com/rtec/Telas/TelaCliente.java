/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.rtec.Telas;

/**
 *
 * @author ramsessf
 */
import br.com.rtec.Validadores.ValidadorCpfCliente;
import java.sql.*;
import br.com.rtec.Dal.ModuloConexao;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
// A linha abaixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    boolean isEmailIdValid;

    String logradouro;
    String cidade;
    String bairro;
    String uf;

    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    public void consultarCpfCliente() {

        btnCliAdd.setEnabled(false);
        btnCliSalvar.setEnabled(false);
        btnCliDel.setEnabled(true);

        String numCliCpf = JOptionPane.showInputDialog("Digite o CPF do Cliente");

        String sql = "select * from tbcliente where cpf=" + numCliCpf;

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                txtCliId.setText(rs.getString(1));
                txtCliNome.setText(rs.getString(2));
                txtCliCpf.setText(rs.getString(3));
                txtCliCep.setText(rs.getString(4));
                txtCliLogra.setText(rs.getString(5));
                txtCliNumEnd.setText(rs.getString(6));
                txtCliBairro.setText(rs.getString(7));
                txtCliComp.setText(rs.getString(8));
                txtCliCidade.setText(rs.getString(9));
                txtCliUf.setText(rs.getString(10));
                txtCliFone.setText(rs.getString(11));
                txtCliEmail.setText(rs.getString(12));

            } else {
                JOptionPane.showMessageDialog(null, "Cliente nÃƒo cadastrado!");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void adicionarCliente() {

        String sql = "insert into tbcliente(nomecli,cpf,cep,logradouro,numero,bairro,complemento,cidade,uf, fonecli, emailcli)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliCpf.getText());
            pst.setString(3, txtCliCep.getText());
            pst.setString(4, txtCliLogra.getText());
            pst.setString(5, txtCliNumEnd.getText());
            pst.setString(6, txtCliBairro.getText());
            pst.setString(7, txtCliComp.getText());
            pst.setString(8, txtCliCidade.getText());
            pst.setString(9, txtCliUf.getText());
            pst.setString(10, txtCliFone.getText());
            pst.setString(11, txtCliEmail.getText());

            if (txtCliNome.getText().isEmpty()
                    || txtCliCpf.getText().isEmpty()
                    || txtCliCep.getText().isEmpty()
                    || txtCliNumEnd.getText().isEmpty()
                    || txtCliLogra.getText().isEmpty()
                    || txtCliFone.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Prencha os campos obrigatÃ³rios!");

            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
                    txtCliNome.setText(null);
                    txtCliCpf.setText(null);
                    txtCliCep.setText(null);
                    txtCliLogra.setText(null);
                    txtCliNumEnd.setText(null);
                    txtCliBairro.setText(null);
                    txtCliComp.setText(null);
                    txtCliCidade.setText(null);
                    txtCliUf.setText(null);
                    txtCliFone.setText(null);
                    txtCliEmail.setText(null);
                    txtCliPesquisar.setText(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void editarCliente() {

        txtCliId.setEnabled(true);
        txtCliNome.setEnabled(true);
        txtCliCpf.setEnabled(true);
        txtCliCep.setEnabled(true);
        txtCliLogra.setEnabled(true);
        txtCliNumEnd.setEnabled(true);
        txtCliBairro.setEnabled(true);
        txtCliComp.setEnabled(true);
        txtCliCidade.setEnabled(true);
        txtCliUf.setEnabled(true);
        txtCliFone.setEnabled(true);
        txtCliEmail.setEnabled(true);
        
        btnCliDel.setEnabled(false);
        btnCliAdd.setEnabled(false);
        btnCliAlt.setEnabled(true);
        btnCliSalvar.setEnabled(true);
        btnValidaCpf.setEnabled(true);
        btnLocCep.setEnabled(true);

        txtCliNome.setEditable(true);
        txtCliCep.setEditable(true);
        txtCliLogra.setEditable(true);
        txtCliNumEnd.setEditable(true);
        txtCliBairro.setEditable(true);
        txtCliComp.setEditable(true);
        txtCliCidade.setEditable(true);
        txtCliUf.setEditable(true);
        txtCliFone.setEditable(true);
        txtCliEmail.setEditable(true);

    }

    public void salvar() {

        btnCliAdd.setEnabled(true);
        btnCliAlt.setEnabled(false);

        String sql = "update tbcliente set nomecli=?, cpf=?, cep=?, logradouro=?, numero=?, bairro=?,complemento=?, "
                + "cidade=?, uf=?, fonecli=?, emailcli=? where idcli=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliCpf.getText());
            pst.setString(3, txtCliCep.getText());
            pst.setString(4, txtCliLogra.getText());
            pst.setString(5, txtCliNumEnd.getText());
            pst.setString(6, txtCliBairro.getText());
            pst.setString(7, txtCliComp.getText());
            pst.setString(8, txtCliCidade.getText());
            pst.setString(9, txtCliUf.getText());
            pst.setString(10, txtCliFone.getText());
            pst.setString(11, txtCliEmail.getText());
            pst.setString(12, txtCliId.getText());

            if (txtCliNome.getText().isEmpty()
                    || txtCliCpf.getText().isEmpty()
                    || txtCliCep.getText().isEmpty()
                    || txtCliLogra.getText().isEmpty()
                    || txtCliFone.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Prencha os campos obrigatÃ³rios!");

            } else if (lblValidaCpf.equals("CPF invÃ¡lido!")) {
                JOptionPane.showMessageDialog(null, "Por favor valide o CPF!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Alterado e gravado com sucesso!");

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void deletar() {

        String sql = "delete from tbcliente where idcli =?";

        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este usuÃ¡rio?", "AtenÃ§Ã£o", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCliId.getText());

                int deletado = pst.executeUpdate();
                if (deletado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do cliente deletados com sucesso!");
                    txtCliNome.setText(null);
                    txtCliCpf.setText(null);
                    txtCliCep.setText(null);
                    txtCliLogra.setText(null);
                    txtCliBairro.setText(null);
                    txtCliCidade.setText(null);
                    txtCliUf.setText(null);
                    txtCliFone.setText(null);
                    txtCliEmail.setText(null);

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    //Metodo para pesquisar cliente pelo nome com filtro
    private void pesquisar_cliente() {
        String sql = "select * from tbcliente where nomecli like ?";

        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteudo da caixa de pesquisa para o ?
            // e concatenando com o "%" para completar a instruÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o sql
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa o recurso da biblioteca rs2xml.jar para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        btnCliAlt.setEnabled(true);
        btnCliDel.setEnabled(true);

    }

    public void setar_campos_tabela() {

        btnCliDel.setEnabled(true);
        btnCliAdd.setEnabled(false);
        btnCliSalvar.setEnabled(false);
        btnValidaCpf.setEnabled(false);
        btnLocCep.setEnabled(false);

        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        txtCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        txtCliCpf.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
        txtCliCep.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
        txtCliLogra.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
        txtCliNumEnd.setText(tblClientes.getModel().getValueAt(setar, 5).toString());
        txtCliBairro.setText(tblClientes.getModel().getValueAt(setar, 6).toString());
        txtCliComp.setText(tblClientes.getModel().getValueAt(setar, 7).toString());
        txtCliCidade.setText(tblClientes.getModel().getValueAt(setar, 8).toString());
        txtCliUf.setText(tblClientes.getModel().getValueAt(setar, 9).toString());
        txtCliFone.setText(tblClientes.getModel().getValueAt(setar, 10).toString());
        txtCliEmail.setText(tblClientes.getModel().getValueAt(setar, 11).toString());

        txtCliId.setEnabled(false);
        txtCliNome.setEnabled(false);
        txtCliCpf.setEnabled(false);
        txtCliCep.setEnabled(false);
        txtCliLogra.setEnabled(false);
        txtCliNumEnd.setEnabled(false);
        txtCliBairro.setEnabled(false);
        txtCliComp.setEnabled(false);
        txtCliCidade.setEnabled(false);
        txtCliUf.setEnabled(false);
        txtCliFone.setEnabled(false);
        txtCliEmail.setEnabled(false);

    }

    //MÃ©todo para conectar no webservice e separar os valores da saida jSon dentro de um vetor.
    public void buscarCep(String cep) {
        String json;

        try {
            URL url = new URL("http://viacep.com.br/ws/" + cep + "/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonSb = new StringBuilder();

            br.lines().forEach(l -> jsonSb.append(l.trim()));
            json = jsonSb.toString();

            // JOptionPane.showMessageDialog(null, json);
            json = json.replaceAll("[{},:]", "");
            json = json.replaceAll("\"", "\n");
            String array[] = new String[30];
            array = json.split("\n");

            // JOptionPane.showMessageDialog(null, array);
            logradouro = array[7];
            bairro = array[15];
            cidade = array[19];
            uf = array[23];

            txtCliLogra.setText(logradouro);
            txtCliBairro.setText(bairro);
            txtCliCidade.setText(cidade);
            txtCliUf.setText(uf);
            //JOptionPane.showMessageDialog(null, logradouro + " " + bairro + " " + cidade + " " + uf);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isValidEmailAddressRegex(String email) {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        list1 = new java.awt.List();
        jScrollPane2 = new javax.swing.JScrollPane();
        lblCliId = new javax.swing.JLabel();
        lblCliNome = new javax.swing.JLabel();
        lblCliFone = new javax.swing.JLabel();
        lblCliEmail = new javax.swing.JLabel();
        lblAviso = new javax.swing.JLabel();
        btnLimpar = new javax.swing.JButton();
        txtCliPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCliNome = new javax.swing.JTextField();
        txtCliId = new javax.swing.JTextField();
        txtCliLogra = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCliBairro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCliCidade = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnLocCep = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCliNumEnd = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCliComp = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCliCpf = new javax.swing.JFormattedTextField();
        txtCliFone = new javax.swing.JFormattedTextField();
        txtCliCep = new javax.swing.JFormattedTextField();
        txtCliUf = new javax.swing.JTextField();
        lblValidaCpf = new javax.swing.JLabel();
        btnValidaCpf = new javax.swing.JButton();
        lblValidaEmail = new javax.swing.JLabel();
        txtCliEmail = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnCliAdd = new javax.swing.JButton();
        btnCliAlt = new javax.swing.JButton();
        btnCliDel = new javax.swing.JButton();
        btnCliSalvar = new javax.swing.JButton();
        btnCpfConsul = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setClosable(true);
        setForeground(java.awt.Color.lightGray);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastro de Clientes");
        setToolTipText("Gravar");
        setPreferredSize(new java.awt.Dimension(859, 673));
        setRequestFocusEnabled(false);
        setVerifyInputWhenFocusTarget(false);
        getContentPane().setLayout(null);

        lblCliId.setText("*Id:");
        getContentPane().add(lblCliId);
        lblCliId.setBounds(40, 320, 30, 14);

        lblCliNome.setText("*Nome:");
        getContentPane().add(lblCliNome);
        lblCliNome.setBounds(40, 370, 50, 20);

        lblCliFone.setText("*Telefone Celular:");
        getContentPane().add(lblCliFone);
        lblCliFone.setBounds(220, 440, 100, 14);

        lblCliEmail.setText("*Email:");
        getContentPane().add(lblCliEmail);
        lblCliEmail.setBounds(40, 510, 40, 14);

        lblAviso.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblAviso.setText("*Campos Obrigatórios");
        getContentPane().add(lblAviso);
        lblAviso.setBounds(440, 160, 140, 14);

        btnLimpar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/rtec/Icones/iconfinder_eraser_70987.png"))); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.setToolTipText("Limpar");
        btnLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });
        getContentPane().add(btnLimpar);
        btnLimpar.setBounds(680, 150, 120, 36);

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });
        getContentPane().add(txtCliPesquisar);
        txtCliPesquisar.setBounds(10, 160, 270, 30);

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "CEP", "Logradouro", "Numero", "Bairro", "Cidade", "UF", "Fone", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        tblClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblClientesKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);
        if (tblClientes.getColumnModel().getColumnCount() > 0) {
            tblClientes.getColumnModel().getColumn(0).setResizable(false);
            tblClientes.getColumnModel().getColumn(0).setPreferredWidth(1);
            tblClientes.getColumnModel().getColumn(1).setResizable(false);
            tblClientes.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblClientes.getColumnModel().getColumn(2).setResizable(false);
            tblClientes.getColumnModel().getColumn(2).setPreferredWidth(10);
            tblClientes.getColumnModel().getColumn(3).setResizable(false);
            tblClientes.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblClientes.getColumnModel().getColumn(4).setResizable(false);
            tblClientes.getColumnModel().getColumn(4).setPreferredWidth(3);
            tblClientes.getColumnModel().getColumn(5).setResizable(false);
            tblClientes.getColumnModel().getColumn(5).setPreferredWidth(20);
            tblClientes.getColumnModel().getColumn(6).setResizable(false);
            tblClientes.getColumnModel().getColumn(6).setPreferredWidth(20);
            tblClientes.getColumnModel().getColumn(7).setResizable(false);
            tblClientes.getColumnModel().getColumn(7).setPreferredWidth(1);
            tblClientes.getColumnModel().getColumn(8).setResizable(false);
            tblClientes.getColumnModel().getColumn(8).setPreferredWidth(5);
            tblClientes.getColumnModel().getColumn(9).setResizable(false);
            tblClientes.getColumnModel().getColumn(9).setPreferredWidth(15);
        }

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 210, 820, 100);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/rtec/Icones/pesquisar.png"))); // NOI18N
        getContentPane().add(jLabel8);
        jLabel8.setBounds(290, 150, 40, 40);

        jLabel2.setText("Informe o CEP:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(430, 330, 90, 14);

        txtCliNome.setForeground(new java.awt.Color(0, 0, 255));
        txtCliNome.setPreferredSize(new java.awt.Dimension(0, 20));
        getContentPane().add(txtCliNome);
        txtCliNome.setBounds(40, 390, 370, 30);

        txtCliId.setForeground(new java.awt.Color(0, 0, 255));
        txtCliId.setEnabled(false);
        txtCliId.setPreferredSize(new java.awt.Dimension(0, 20));
        getContentPane().add(txtCliId);
        txtCliId.setBounds(40, 340, 60, 30);

        txtCliLogra.setForeground(new java.awt.Color(0, 0, 255));
        txtCliLogra.setPreferredSize(new java.awt.Dimension(0, 20));
        getContentPane().add(txtCliLogra);
        txtCliLogra.setBounds(430, 410, 280, 30);

        jLabel3.setText("Bairro:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(430, 450, 50, 14);

        txtCliBairro.setForeground(new java.awt.Color(0, 0, 255));
        txtCliBairro.setPreferredSize(new java.awt.Dimension(0, 20));
        getContentPane().add(txtCliBairro);
        txtCliBairro.setBounds(430, 470, 210, 30);

        jLabel5.setText("Cidade:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(430, 510, 50, 14);

        txtCliCidade.setForeground(new java.awt.Color(0, 0, 255));
        txtCliCidade.setPreferredSize(new java.awt.Dimension(0, 20));
        getContentPane().add(txtCliCidade);
        txtCliCidade.setBounds(430, 530, 290, 30);

        jLabel1.setText("UF:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(740, 510, 20, 14);

        btnLocCep.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLocCep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/rtec/Icones/pesquisar.png"))); // NOI18N
        btnLocCep.setText("Localizar");
        btnLocCep.setBorder(null);
        btnLocCep.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLocCep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocCepActionPerformed(evt);
            }
        });
        btnLocCep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnLocCepKeyReleased(evt);
            }
        });
        getContentPane().add(btnLocCep);
        btnLocCep.setBounds(570, 340, 130, 40);

        jLabel4.setText("Logradouro:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(430, 390, 70, 14);

        jLabel6.setText("* Número:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(720, 390, 60, 14);

        txtCliNumEnd.setForeground(new java.awt.Color(0, 0, 255));
        getContentPane().add(txtCliNumEnd);
        txtCliNumEnd.setBounds(720, 410, 70, 30);

        jLabel7.setText("Complemento:");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(650, 450, 110, 14);

        txtCliComp.setForeground(new java.awt.Color(0, 0, 255));
        txtCliComp.setPreferredSize(new java.awt.Dimension(59, 30));
        getContentPane().add(txtCliComp);
        txtCliComp.setBounds(650, 470, 140, 30);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Buscar Cliente");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(10, 144, 90, 20);

        jLabel10.setText("CPF:");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(40, 440, 30, 14);

        txtCliCpf.setForeground(new java.awt.Color(0, 0, 255));
        try {
            txtCliCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCliCpf.setPreferredSize(new java.awt.Dimension(6, 20));
        txtCliCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliCpfActionPerformed(evt);
            }
        });
        txtCliCpf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliCpfKeyReleased(evt);
            }
        });
        getContentPane().add(txtCliCpf);
        txtCliCpf.setBounds(40, 460, 140, 30);

        txtCliFone.setForeground(new java.awt.Color(0, 0, 255));
        try {
            txtCliFone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(txtCliFone);
        txtCliFone.setBounds(220, 460, 150, 30);

        txtCliCep.setForeground(new java.awt.Color(0, 0, 255));
        try {
            txtCliCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCliCep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliCepKeyReleased(evt);
            }
        });
        getContentPane().add(txtCliCep);
        txtCliCep.setBounds(430, 350, 130, 30);

        txtCliUf.setForeground(new java.awt.Color(0, 0, 255));
        getContentPane().add(txtCliUf);
        txtCliUf.setBounds(740, 530, 50, 30);

        lblValidaCpf.setForeground(new java.awt.Color(255, 0, 51));
        getContentPane().add(lblValidaCpf);
        lblValidaCpf.setBounds(50, 490, 100, 10);

        btnValidaCpf.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnValidaCpf.setText("validar");
        btnValidaCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidaCpfActionPerformed(evt);
            }
        });
        getContentPane().add(btnValidaCpf);
        btnValidaCpf.setBounds(70, 430, 80, 20);
        getContentPane().add(lblValidaEmail);
        lblValidaEmail.setBounds(40, 510, 180, 20);

        txtCliEmail.setForeground(new java.awt.Color(0, 0, 255));
        getContentPane().add(txtCliEmail);
        txtCliEmail.setBounds(40, 530, 340, 30);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setToolTipText("");
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCliAdd.setIcon(new javax.swing.ImageIcon("C:\\Users\\ramsessf\\Desktop\\Curso Java aulaead\\icones_ferramentas\\icones\\iconfinder_new-file_85332.png")); // NOI18N
        btnCliAdd.setToolTipText("");
        btnCliAdd.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCliAdd.setBorderPainted(false);
        btnCliAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCliAdd.setPreferredSize(new java.awt.Dimension(70, 70));
        btnCliAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnCliAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 70));

        btnCliAlt.setIcon(new javax.swing.ImageIcon("C:\\Users\\ramsessf\\Desktop\\Curso Java aulaead\\icones_ferramentas\\icones\\iconfinder_edit-file_85307.png")); // NOI18N
        btnCliAlt.setToolTipText("");
        btnCliAlt.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCliAlt.setBorderPainted(false);
        btnCliAlt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCliAlt.setEnabled(false);
        btnCliAlt.setPreferredSize(new java.awt.Dimension(70, 70));
        btnCliAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliAltActionPerformed(evt);
            }
        });
        jPanel1.add(btnCliAlt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 80, 70));

        btnCliDel.setIcon(new javax.swing.ImageIcon("C:\\Users\\ramsessf\\Desktop\\Curso Java aulaead\\icones_ferramentas\\icones\\iconfinder_delete-file_85306.png")); // NOI18N
        btnCliDel.setToolTipText("");
        btnCliDel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCliDel.setBorderPainted(false);
        btnCliDel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCliDel.setEnabled(false);
        btnCliDel.setPreferredSize(new java.awt.Dimension(70, 70));
        btnCliDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliDelActionPerformed(evt);
            }
        });
        jPanel1.add(btnCliDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 70, -1));

        btnCliSalvar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ramsessf\\Desktop\\Curso Java aulaead\\icones_ferramentas\\icones\\iconfinder_floppy_disk_save_103863.png")); // NOI18N
        btnCliSalvar.setToolTipText("Salvar cadastro");
        btnCliSalvar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCliSalvar.setBorderPainted(false);
        btnCliSalvar.setContentAreaFilled(false);
        btnCliSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCliSalvar.setEnabled(false);
        btnCliSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliSalvarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCliSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 80, 70));

        btnCpfConsul.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnCpfConsul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/rtec/Icones/searchcpf.png"))); // NOI18N
        btnCpfConsul.setToolTipText("");
        btnCpfConsul.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCpfConsul.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCpfConsul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCpfConsulActionPerformed(evt);
            }
        });
        jPanel1.add(btnCpfConsul, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 70, -1));

        jLabel12.setText("ADICIONAR");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 60, -1));

        jLabel13.setText("EDITAR");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, -1, -1));

        jLabel14.setText("DELETAR");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, -1, -1));

        jLabel15.setText("PESQUISAR");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, -1, -1));

        jLabel16.setText("SALVAR");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 80, -1, -1));

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 30, 840, 100);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("CADASTRO DE CLIENTE");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(310, 0, 220, 30);

        setBounds(0, 0, 859, 673);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCliAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliAddActionPerformed
        adicionarCliente();
    }//GEN-LAST:event_btnCliAddActionPerformed


    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        txtCliId.setText(null);
        txtCliNome.setText(null);
        txtCliCpf.setText(null);
        txtCliCep.setText(null);
        txtCliLogra.setText(null);
        txtCliNumEnd.setText(null);
        txtCliBairro.setText(null);
        txtCliComp.setText(null);
        txtCliCidade.setText(null);
        txtCliUf.setText(null);
        txtCliFone.setText(null);
        txtCliEmail.setText(null);
        lblValidaCpf.setText(null);

        txtCliId.setEnabled(false);

        txtCliNome.setEnabled(true);
        txtCliCpf.setEnabled(true);
        txtCliCep.setEnabled(true);
        txtCliLogra.setEnabled(true);
        txtCliNumEnd.setEnabled(true);
        txtCliBairro.setEnabled(true);
        txtCliComp.setEnabled(true);
        txtCliCidade.setEnabled(true);
        txtCliUf.setEnabled(true);
        txtCliFone.setEnabled(true);
        txtCliEmail.setEnabled(true);

        btnCliAdd.setEnabled(true);
        btnCliAlt.setEnabled(false);
        btnCliDel.setEnabled(false);
        btnValidaCpf.setEnabled(true);
        btnLocCep.setEnabled(true);

        tblClientes.setModel(null);


    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnCliAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliAltActionPerformed
        editarCliente();
    }//GEN-LAST:event_btnCliAltActionPerformed

    private void btnCliDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliDelActionPerformed
        deletar();
    }//GEN-LAST:event_btnCliDelActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // chamando o metodo para popoluar a tabela com os dados do banco
        pesquisar_cliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblClientesKeyReleased

    }//GEN-LAST:event_tblClientesKeyReleased
    //evento que serÃƒÆ’Ã‚Â¡ usado para setar os campos da tabela com o click do mouse
    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        //chamando o mÃƒÆ’Ã‚Â©todo para setar os campos
        setar_campos_tabela();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnLocCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocCepActionPerformed
        buscarCep(txtCliCep.getText());

    }//GEN-LAST:event_btnLocCepActionPerformed

    private void btnLocCepKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLocCepKeyReleased

    }//GEN-LAST:event_btnLocCepKeyReleased

    private void btnCliSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliSalvarActionPerformed
        //chamando o metodo salvar
        salvar();
    }//GEN-LAST:event_btnCliSalvarActionPerformed

    private void btnCpfConsulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCpfConsulActionPerformed
        //chamando o metodo consultar por cpf
        consultarCpfCliente();


    }//GEN-LAST:event_btnCpfConsulActionPerformed

    private void txtCliCepKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliCepKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliCepKeyReleased

    private void txtCliCpfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliCpfKeyReleased


    }//GEN-LAST:event_txtCliCpfKeyReleased

    private void txtCliCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliCpfActionPerformed


    }//GEN-LAST:event_txtCliCpfActionPerformed

    private void btnValidaCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidaCpfActionPerformed
        String CPF = txtCliCpf.getText();

        while (CPF.contains(".") || CPF.contains("-")) {
            CPF = CPF.replace(".", "");
            CPF = CPF.replace("-", "");

            if (ValidadorCpfCliente.isCPF(CPF) == true) {
                lblValidaCpf.setText("CPF válido!");
            } else {
                lblValidaCpf.setText("CPF inválido!");

            }
        }


    }//GEN-LAST:event_btnValidaCpfActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCliAdd;
    private javax.swing.JButton btnCliAlt;
    private javax.swing.JButton btnCliDel;
    private javax.swing.JButton btnCliSalvar;
    private javax.swing.JButton btnCpfConsul;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnLocCep;
    private javax.swing.JButton btnValidaCpf;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JLabel lblCliEmail;
    private javax.swing.JLabel lblCliFone;
    private javax.swing.JLabel lblCliId;
    private javax.swing.JLabel lblCliNome;
    private javax.swing.JLabel lblValidaCpf;
    private javax.swing.JLabel lblValidaEmail;
    private java.awt.List list1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliBairro;
    private javax.swing.JFormattedTextField txtCliCep;
    private javax.swing.JTextField txtCliCidade;
    private javax.swing.JTextField txtCliComp;
    private javax.swing.JFormattedTextField txtCliCpf;
    private javax.swing.JTextField txtCliEmail;
    private javax.swing.JFormattedTextField txtCliFone;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliLogra;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliNumEnd;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtCliUf;
    // End of variables declaration//GEN-END:variables
}

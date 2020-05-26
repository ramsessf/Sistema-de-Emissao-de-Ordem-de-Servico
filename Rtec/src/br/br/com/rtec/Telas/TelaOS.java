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
import java.sql.*;
import br.com.rtec.Dal.ModuloConexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class TelaOS extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    //a linha abaixo cria uma variável para armazenar um texto de acordo com o
    //radio button selecionado
    private String tipo;
    private String model;

    public TelaOS() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    //Metodo para pesquisar cliente pelo nome com filtro
    private void pesquisar_cliente() {
        String sql = "select idcli as Id,nomecli as Nome,fonecli as Fone from tbcliente where nomecli like ?";

        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteudo da caixa de pesquisa para o ?
            // e concatenando com o "%" para completar a instrução sql
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa o recurso da biblioteca rs2xml.jar para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void setar_campos_tabela() {
        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());

    }

    private void emitir_os() {

        String sql = "insert into tbos (tipo,situacao,equipamento,defeito,"
                + "servico,tecnico,valor,idcli) values( ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cmbOsSituacao.getSelectedItem().toString());
            pst.setString(3, txtOsEquip.getText());
            pst.setString(4, txtOsDef.getText());
            pst.setString(5, txtOsServ.getText());
            pst.setString(6, txtOsTec.getText());
            pst.setString(7, txtOsValor.getText().replace(",", "."));
            pst.setString(8, txtCliId.getText());

            if (txtOsEquip.getText().isEmpty()
                    || txtOsDef.getText().isEmpty()
                    || txtOsServ.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS emitida com sucesso!");
                    txtIdOs.setText(null);
                    txtCliId.setText(null);
                    txtOsData.setText(null);
                    txtOsEquip.setText(null);
                    txtOsDef.setText(null);
                    txtOsServ.setText(null);
                    txtOsTec.setText(null);
                    txtOsValor.setText(null);
                    txtCliPesquisar.setText(null);

                } else {
                    JOptionPane.showMessageDialog(null, "Os não encontrada!");

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void pesquisar_os() {

        String num_os = JOptionPane.showInputDialog("Digite o código da OS");

        String sql = "select * from tbos where os=" + num_os;

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                txtIdOs.setText(rs.getString(1));
                txtOsData.setText(rs.getString(2));
                //setando os radio buttons
                String rbTipo = rs.getString(3);

                if (rbTipo.equals("Ordem de servico")) {
                    rbOrdServ.setSelected(true);
                } else {
                    rbOrcamento.setSelected(true);
                }
                cmbOsSituacao.setSelectedItem(rs.getString(4));
                txtOsEquip.setText(rs.getString(5));
                txtOsDef.setText(rs.getString(6));
                txtOsServ.setText(rs.getString(7));
                txtOsTec.setText(rs.getString(8));
                txtOsValor.setText(rs.getString(9));
                txtCliId.setText(rs.getString(10));

                txtIdOs.setEnabled(true);
                rbOrdServ.setEnabled(false);
                rbOrcamento.setEnabled(false);
                cmbOsSituacao.setEnabled(false);
                txtOsData.setEnabled(true);

                txtIdOs.setEditable(false);
                cmbOsSituacao.setEditable(false);
                txtOsData.setEditable(false);
                txtOsEquip.setEditable(false);
                txtOsDef.setEditable(false);
                txtOsServ.setEditable(false);
                txtOsTec.setEditable(false);
                txtOsValor.setEditable(false);

                btnOsAdd.setEnabled(false);
                btnOsAlt.setEnabled(true);
                btnOsDel.setEnabled(true);
                btnImpOs.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "Os não encontrada!");
            }

        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "Os Inválida");
            //System.out.println(e); recuperar a mensagem de exception

        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }

    }

    public void salvar_os() {
        String sql = "update tbos set tipo =?,situacao=?,equipamento=?,defeito=?,servico=?,tecnico=?,valor=? where os=? ";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cmbOsSituacao.getSelectedItem().toString());
            pst.setString(3, txtOsEquip.getText());
            pst.setString(4, txtOsDef.getText());
            pst.setString(5, txtOsServ.getText());
            pst.setString(6, txtOsTec.getText());
            pst.setString(7, txtOsValor.getText().replace(",", "."));
            pst.setString(8, txtIdOs.getText());

            if (txtOsEquip.getText().isEmpty()
                    || txtOsDef.getText().isEmpty()
                    || txtOsServ.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Os alterada com sucesso!");
                    txtIdOs.setText(null);
                    txtCliId.setText(null);
                    txtOsData.setText(null);
                    txtOsEquip.setText(null);
                    txtOsDef.setText(null);
                    txtOsServ.setText(null);
                    txtOsTec.setText(null);
                    txtOsValor.setText(null);
                    txtCliPesquisar.setText(null);

                    txtOsData.setEnabled(true);

                    btnOsAdd.setEnabled(true);
                    btnOsDel.setEnabled(false);
                    btnOsAlt.setEnabled(false);
                    btnImpOs.setEnabled(false);
                    btnOsSalvar.setEnabled(false);

                    txtCliPesquisar.setEnabled(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Os não encontrada!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void excluir_os() {
        String sql = "delete from tbos where os=?";

        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este usuário?", "Atenção", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtIdOs.getText());

                if (txtOsEquip.getText().isEmpty()
                        || txtOsDef.getText().isEmpty()
                        || txtOsServ.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
                } else {
                    int deletado = pst.executeUpdate();
                    if (deletado > 0) {
                        JOptionPane.showMessageDialog(null, "Dados do cliente excluídos com sucesso!");
                        txtIdOs.setText(null);
                        txtCliId.setText(null);
                        txtOsData.setText(null);
                        txtOsEquip.setText(null);
                        txtOsDef.setText(null);
                        txtOsServ.setText(null);
                        txtOsTec.setText(null);
                        txtOsValor.setText(null);

                        btnOsAdd.setEnabled(true);
                        btnOsAlt.setEnabled(false);
                        btnImpOs.setEnabled(false);

                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    public void imprimir_os() {
        // metodo para imprimir as os selecionada
        int imprimir = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja imprimir a OS ? ", "Atenção", JOptionPane.YES_NO_OPTION);

        if (imprimir == JOptionPane.YES_OPTION) {

            try {
                //Criando a classe HashMap para criar um filtro
                HashMap filtro = new HashMap();
                filtro.put("os", Integer.parseInt(txtIdOs.getText()));

                //usando a classe JasperPrint
                JasperPrint print = JasperFillManager.fillReport("C:/reports/ordemServico.jasper", filtro, conexao);

                JasperViewer.viewReport(print, false);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        buttonGroup2 = new javax.swing.ButtonGroup();
        panel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIdOs = new javax.swing.JTextField();
        txtOsData = new javax.swing.JTextField();
        lblOsValor = new javax.swing.JLabel();
        lblOsEquip = new javax.swing.JLabel();
        lblOsDef = new javax.swing.JLabel();
        lblOsServ = new javax.swing.JLabel();
        lblOsTec = new javax.swing.JLabel();
        btnLimpar = new javax.swing.JButton();
        lblOsSit = new javax.swing.JLabel();
        txtOsEquip = new javax.swing.JTextField();
        txtOsDef = new javax.swing.JTextField();
        txtOsTec = new javax.swing.JTextField();
        panel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        txtCliPesquisar = new javax.swing.JTextField();
        txtCliId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        rbOrcamento = new javax.swing.JRadioButton();
        rbOrdServ = new javax.swing.JRadioButton();
        cmbOsSituacao = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        btnOsAdd = new javax.swing.JButton();
        btnOsDel = new javax.swing.JButton();
        btnImpOs = new javax.swing.JButton();
        btnOsConsul = new javax.swing.JButton();
        btnOsSalvar = new javax.swing.JButton();
        btnOsAlt = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtOsServ = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        txtOsValor = new javax.swing.JFormattedTextField();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setTitle("Emissão de OS");
        setPreferredSize(new java.awt.Dimension(859, 673));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("NºOS:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Data:");

        txtIdOs.setBackground(new java.awt.Color(153, 204, 255));
        txtIdOs.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtIdOs.setForeground(new java.awt.Color(255, 0, 0));
        txtIdOs.setPreferredSize(new java.awt.Dimension(30, 30));

        txtOsData.setBackground(new java.awt.Color(240, 240, 240));
        txtOsData.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtOsData.setForeground(new java.awt.Color(102, 0, 0));
        txtOsData.setEnabled(false);
        txtOsData.setPreferredSize(new java.awt.Dimension(30, 30));

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(txtIdOs, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 75, Short.MAX_VALUE))
                    .addComponent(txtOsData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(30, 30, 30)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOsData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addGap(42, 42, 42))
        );

        lblOsValor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblOsValor.setText("Valor:");

        lblOsEquip.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblOsEquip.setText("Equipamento:");
        lblOsEquip.setPreferredSize(new java.awt.Dimension(150, 20));

        lblOsDef.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblOsDef.setText("Defeito:");

        lblOsServ.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblOsServ.setText("Servico:");

        lblOsTec.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblOsTec.setText("Técnico:");

        btnLimpar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/rtec/Icones/iconfinder_eraser_70987.png"))); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        lblOsSit.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblOsSit.setText("Situação:");

        txtOsEquip.setForeground(new java.awt.Color(0, 0, 255));
        txtOsEquip.setPreferredSize(new java.awt.Dimension(30, 30));

        txtOsDef.setForeground(new java.awt.Color(0, 0, 255));
        txtOsDef.setPreferredSize(new java.awt.Dimension(30, 30));

        txtOsTec.setForeground(new java.awt.Color(0, 0, 255));
        txtOsTec.setPreferredSize(new java.awt.Dimension(30, 30));

        panel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Fone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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
        jScrollPane3.setViewportView(tblClientes);
        if (tblClientes.getColumnModel().getColumnCount() > 0) {
            tblClientes.getColumnModel().getColumn(0).setResizable(false);
            tblClientes.getColumnModel().getColumn(0).setPreferredWidth(15);
            tblClientes.getColumnModel().getColumn(1).setResizable(false);
            tblClientes.getColumnModel().getColumn(2).setResizable(false);
        }

        txtCliPesquisar.setForeground(new java.awt.Color(0, 0, 255));
        txtCliPesquisar.setPreferredSize(new java.awt.Dimension(6, 30));
        txtCliPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliPesquisarActionPerformed(evt);
            }
        });
        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        txtCliId.setEditable(false);
        txtCliId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliIdActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("ID:");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/rtec/Icones/pesquisar.png"))); // NOI18N

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        buttonGroup1.add(rbOrcamento);
        rbOrcamento.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rbOrcamento.setText("Orçamento");
        rbOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbOrcamentoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbOrdServ);
        rbOrdServ.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rbOrdServ.setText("Ordem Servico");
        rbOrdServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbOrdServActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbOrdServ, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbOrcamento))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(rbOrcamento)
                .addGap(18, 18, 18)
                .addComponent(rbOrdServ)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        cmbOsSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Na Bancada", "Aguardando Aprovacao", "Orcamento ReprovadO", "Abandonado", "Entrega OK" }));
        cmbOsSituacao.setPreferredSize(new java.awt.Dimension(56, 30));
        cmbOsSituacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbOsSituacaoActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnOsAdd.setBackground(new java.awt.Color(204, 204, 204));
        btnOsAdd.setIcon(new javax.swing.ImageIcon("C:\\Users\\ramsessf\\Desktop\\Curso Java aulaead\\icones_ferramentas\\icones\\iconfinder_new-file_85332.png")); // NOI18N
        btnOsAdd.setToolTipText("Adicionar OS");
        btnOsAdd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOsAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAdd.setPreferredSize(new java.awt.Dimension(70, 70));
        btnOsAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAddActionPerformed(evt);
            }
        });
        jPanel2.add(btnOsAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 60));

        btnOsDel.setIcon(new javax.swing.ImageIcon("C:\\Users\\ramsessf\\Desktop\\Curso Java aulaead\\icones_ferramentas\\icones\\iconfinder_delete-file_85306.png")); // NOI18N
        btnOsDel.setToolTipText("Deletar OS");
        btnOsDel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOsDel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsDel.setEnabled(false);
        btnOsDel.setPreferredSize(new java.awt.Dimension(70, 70));
        btnOsDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsDelActionPerformed(evt);
            }
        });
        jPanel2.add(btnOsDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, 60));

        btnImpOs.setIcon(new javax.swing.ImageIcon("C:\\Users\\ramsessf\\Desktop\\Curso Java aulaead\\icones_ferramentas\\icones\\iconfinder_preferences-desktop-printer_8803.png")); // NOI18N
        btnImpOs.setToolTipText("Imprimir OS");
        btnImpOs.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnImpOs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImpOs.setEnabled(false);
        btnImpOs.setPreferredSize(new java.awt.Dimension(70, 70));
        btnImpOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImpOsActionPerformed(evt);
            }
        });
        jPanel2.add(btnImpOs, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 20, 90, 60));

        btnOsConsul.setIcon(new javax.swing.ImageIcon("C:\\Users\\ramsessf\\Desktop\\Curso Java aulaead\\icones_ferramentas\\icones\\iconfinder_search-file_85343.png")); // NOI18N
        btnOsConsul.setToolTipText("Consultar OS");
        btnOsConsul.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOsConsul.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsConsul.setPreferredSize(new java.awt.Dimension(70, 70));
        btnOsConsul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsConsulActionPerformed(evt);
            }
        });
        jPanel2.add(btnOsConsul, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, 60));

        btnOsSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/rtec/Icones/save.png"))); // NOI18N
        btnOsSalvar.setToolTipText("Gravar Edição OS");
        btnOsSalvar.setEnabled(false);
        btnOsSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsSalvarActionPerformed(evt);
            }
        });
        jPanel2.add(btnOsSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, 80, 60));

        btnOsAlt.setIcon(new javax.swing.ImageIcon("C:\\Users\\ramsessf\\Desktop\\Curso Java aulaead\\icones_ferramentas\\icones\\iconfinder_edit-file_85307.png")); // NOI18N
        btnOsAlt.setToolTipText("Editar OS");
        btnOsAlt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnOsAlt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAlt.setEnabled(false);
        btnOsAlt.setPreferredSize(new java.awt.Dimension(70, 70));
        btnOsAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAltActionPerformed(evt);
            }
        });
        jPanel2.add(btnOsAlt, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, -1, 60));

        jLabel7.setText("  IMPRIMIR OS");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 80, 80, -1));

        jLabel8.setText("SALVAR");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 50, -1));

        jLabel9.setText("EDITAR");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, -1, -1));

        jLabel10.setText("   PESQUISAR");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 70, -1));

        jLabel11.setText(" DELETAR");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 50, -1));

        jLabel12.setText("ADICIONAR");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, 20));

        jLabel4.setBackground(new java.awt.Color(51, 153, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("EMISSÃO DE ORDEM DE SERVIÇO");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Marca/Modelo:");

        txtOsValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOsValorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblOsDef)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtOsDef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblOsTec)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtOsTec, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblOsServ)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtOsServ)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(lblOsSit)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbOsSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(lblOsEquip, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtOsEquip, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addComponent(btnLimpar)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())))))
            .addGroup(layout.createSequentialGroup()
                .addGap(251, 251, 251)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblOsSit)
                                .addGap(27, 27, 27))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbOsSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtOsEquip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblOsEquip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblOsDef)
                            .addComponent(txtOsDef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblOsServ)
                            .addComponent(txtOsServ, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblOsValor)
                            .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtOsTec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblOsTec)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setBounds(0, 0, 852, 668);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // chamando o metodo para popoluar a tabela com os dados do banco
        pesquisar_cliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblClientesKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblClientesKeyReleased

    private void txtCliIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliIdActionPerformed

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        //chamando o método para setar os campos
        setar_campos_tabela();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void cmbOsSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbOsSituacaoActionPerformed

    }//GEN-LAST:event_cmbOsSituacaoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // Ao abrir o form um radio button é marcado automaticamente
        rbOrcamento.setSelected(true);
        tipo = "Orcamento";

    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOsAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAddActionPerformed
        emitir_os();

    }//GEN-LAST:event_btnOsAddActionPerformed

    private void btnOsConsulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsConsulActionPerformed
        //chama a caixa de dialogo para insetir o codigo da OS
        pesquisar_os();
    }//GEN-LAST:event_btnOsConsulActionPerformed

    private void btnOsAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAltActionPerformed

        txtIdOs.setEnabled(false);
        rbOrcamento.setEnabled(true);
        rbOrdServ.setEnabled(true);
        cmbOsSituacao.setEnabled(true);
        txtOsEquip.setEnabled(true);
        txtOsDef.setEnabled(true);
        txtOsServ.setEnabled(true);
        txtOsTec.setEnabled(true);
        txtOsValor.setEnabled(true);

        cmbOsSituacao.setEditable(true);
        txtOsEquip.setEditable(true);
        txtOsDef.setEditable(true);
        txtOsServ.setEditable(true);
        txtOsTec.setEditable(true);
        txtOsValor.setEditable(true);

        btnOsSalvar.setEnabled(true);
        btnOsDel.setEnabled(true);
        txtCliPesquisar.setEnabled(true);

    }//GEN-LAST:event_btnOsAltActionPerformed

    private void btnOsDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsDelActionPerformed
        // chamando o metodo para excluir os
        excluir_os();
    }//GEN-LAST:event_btnOsDelActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed

        txtCliId.setText(null);
        txtOsEquip.setText(null);
        txtOsDef.setText(null);
        txtOsServ.setText(null);
        txtOsTec.setText(null);
        txtOsValor.setText(null);
        txtIdOs.setText(null);
        txtOsData.setText(null);

        txtCliId.setEnabled(false);
        
        cmbOsSituacao.setEnabled(true);
        txtOsEquip.setEnabled(true);
        txtOsDef.setEnabled(true);
        txtOsServ.setEnabled(true);
        txtOsTec.setEnabled(true);
        txtOsValor.setEnabled(true);

        txtOsEquip.setEditable(true);
        txtOsDef.setEditable(true);
        txtOsServ.setEditable(true);
        txtOsTec.setEditable(true);
        txtOsValor.setEditable(true);

        btnOsAdd.setEnabled(true);
        btnOsAlt.setEnabled(false);
        btnOsDel.setEnabled(false);
        btnImpOs.setEnabled(false);

        tblClientes.setModel(null);


    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnImpOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpOsActionPerformed
        // chamando o metodo para impressao de OS
        imprimir_os();
    }//GEN-LAST:event_btnImpOsActionPerformed

    private void btnOsSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsSalvarActionPerformed
        salvar_os();
    }//GEN-LAST:event_btnOsSalvarActionPerformed

    private void txtCliPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliPesquisarActionPerformed

    private void rbOrdServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbOrdServActionPerformed
        //atribuindo um valor para o radio button selecionado
        tipo = "Ordem de servico";
    }//GEN-LAST:event_rbOrdServActionPerformed

    private void rbOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbOrcamentoActionPerformed
        //atribuindo um valor para o radio button selecionado
        tipo = "Orcamento";
    }//GEN-LAST:event_rbOrcamentoActionPerformed

    private void txtOsValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOsValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOsValorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImpOs;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnOsAdd;
    private javax.swing.JButton btnOsAlt;
    private javax.swing.JButton btnOsConsul;
    private javax.swing.JButton btnOsDel;
    private javax.swing.JButton btnOsSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cmbOsSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblOsDef;
    private javax.swing.JLabel lblOsEquip;
    private javax.swing.JLabel lblOsServ;
    private javax.swing.JLabel lblOsSit;
    private javax.swing.JLabel lblOsTec;
    private javax.swing.JLabel lblOsValor;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JRadioButton rbOrcamento;
    private javax.swing.JRadioButton rbOrdServ;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtIdOs;
    private javax.swing.JTextField txtOsData;
    private javax.swing.JTextField txtOsDef;
    private javax.swing.JTextField txtOsEquip;
    private javax.swing.JTextField txtOsServ;
    private javax.swing.JTextField txtOsTec;
    private javax.swing.JFormattedTextField txtOsValor;
    // End of variables declaration//GEN-END:variables
}

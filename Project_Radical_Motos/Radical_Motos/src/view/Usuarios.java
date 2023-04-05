package view;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;

public class Usuarios extends JDialog {
	
	// Criação de objetos (JDBC)
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNome;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JTextField txtID;
	private ResultSet rs; // Depois Ctrl Shift o //ele serve para pesquisa
	private JButton btnBuscar;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Usuarios dialog = new Usuarios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Usuarios() {
		setModal(true);
		setTitle("USERS");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/moto.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NOME");
		lblNewLabel.setBounds(10, 74, 48, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setBounds(10, 118, 48, 14);
		contentPanel.add(lblLogin);
		
		txtNome = new JTextField();
		txtNome.setBounds(81, 72, 292, 17);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblSenha = new JLabel("SENHA");
		lblSenha.setBounds(10, 161, 48, 14);
		contentPanel.add(lblSenha);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(81, 116, 127, 17);
		contentPanel.add(txtLogin);
		txtLogin.setColumns(10);
		
		btnCreate = new JButton("");
		btnCreate.setEnabled(false);
		btnCreate.setBorderPainted(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarUsuario();
			}
		});
		btnCreate.setContentAreaFilled(false);
		btnCreate.setIcon(new ImageIcon(Usuarios.class.getResource("/img/add-people.png")));
		btnCreate.setBounds(52, 202, 48, 48);
		contentPanel.add(btnCreate);
		
		JButton btnLimpar = new JButton("");
		btnLimpar.setBorderPainted(false);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/eraser.png")));
		btnLimpar.setBounds(218, 202, 48, 48);
		contentPanel.add(btnLimpar);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(81, 159, 234, 17);
		contentPanel.add(txtSenha);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setBounds(12, 44, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(81, 41, 59, 20);
		contentPanel.add(txtID);
		txtID.setColumns(10);
		
		btnBuscar = new JButton("");
		btnBuscar.setBorderPainted(false);
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/search-moto.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarContato();
			}
		});
		btnBuscar.setToolTipText("Pesquisar Contato");
		btnBuscar.setBounds(218, 100, 48, 48);
		contentPanel.add(btnBuscar);
		
		
		// substituir o click pela tecla <enter> em um botão
		getRootPane().setDefaultButton(btnBuscar);
		
		btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			editarUsuario();
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setIcon(new ImageIcon(Usuarios.class.getResource("/img/update-motos.png")));
		btnUpdate.setBounds(102, 202, 48, 48);
		contentPanel.add(btnUpdate);
		
		btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			excluirUsuario();
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setIcon(new ImageIcon(Usuarios.class.getResource("/img/trash.png")));
		btnDelete.setBounds(160, 202, 48, 48);
		contentPanel.add(btnDelete);

		
		
		
	}//fim do construtor
	
	
	/**
	 * Método para adicionar um contato no banco
	 */
	private void adicionarUsuario() {
		// System.out.println("Teste do botão adicionar");

		// validação de campos obrigatórios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login do Usuário");
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
		JOptionPane.showMessageDialog(null, "Preencha a senha do Usuário");
		txtLogin.requestFocus();
	} else {
			// lógica principal
			// a linha abaixo cria uma variável de nome create que recebe o código sql
			 String create = "insert into usuarios(nome,login,senha) values (?,?,?)";
			// tratamento de exceção			
			try {
				// abrir a conexão com o banco
				 con = dao.conectar();
				// Uso da classe PreparedStatement para executar a instrução sql e replicar no
				// banco
				 pst = con.prepareStatement(create);
				// setar(definir) os parâmetros (?,?,?) de acordo com o preenchimento das caixas
				// de texto
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				// executar a instrução sql (query)
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Usuário Adicionado com Sucesso");
				// limpar os campos
				limparCampos();
				// fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println();

			}
		}

	}// fim do método adicionarContato
	
	
	
	/**
	 * Metodo Responsavel por Buscar o Contato / Pesquisar contatos
	 */
	private void buscarContato() {
		// validação da busca
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Login do Usuario");
			txtLogin.requestFocus(); // setar o cursor na caixa do texto
		} else {

			// Teste
			// System.out.println("Teste do botão Buscar");
			String read = "select * from usuarios where login = ? ";
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a execuçao do Querry
				pst = con.prepareStatement(read);
				// egar o conteudo da caixa de texto e substituir o parametro ?
				pst.setString(1, txtLogin.getText());
				// uso do ResultSet para obter os dados do contatos
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					// preencher as caixas de texto com o fone e o email
					// ATENÇÃO o Nome (2 campo da Janela ja foi preenchido
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtSenha.setText(rs.getString(4));
					// desativar botão adiconar
					btnCreate.setEnabled(false);
					// ativar botão update e delete
					btnDelete.setEnabled(true);
					btnUpdate.setEnabled(true);
					// desativar botão buscar depois da pesquisa
					btnBuscar.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Usuário Inexistente");
					// ativar o botão adicionar
					btnCreate.setEnabled(true);
					// desativar botão pesquisar
					btnBuscar.setEnabled(false);
				}
				// Fechar Conexão
				con.close();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
		}
	}// fim do metodo buscar contato
	
	
	
	/**
	 * Metodo responsável pela edição de um contato / updade
	 */
	private void editarUsuario(){
		//System.out.println("Teste do Botão Editar");
		//validação de campos obrigatorios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login");	
		}else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Senha");	
		} else {
			//logica principal (CRUD UPDATE)
			//criando uma variavel do tipo string que irá receber a query
			String update = "update usuarios set nome=?, login=?, senha=? where iduser = ?";
			//tratamento de exceções 
			try {
				//abrir conexão com o banco
				con = dao.conectar();
				//preparar a query(substituir ????)
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, txtID.getText());
				//executar o update no banco
				pst.executeUpdate();
				//comfirmar para o usuario
				JOptionPane.showMessageDialog(null, "Dados do Usuário alterado com Sucesso");
				// Fechar conexão
				con.close();
				//limpar campos
				limparCampos();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}//fim do metodo editar contato
	
	/**
	 * Metodo Responsavel por excluir um contato
	 */
	private void excluirUsuario() {
		//System.out.println("Teste do Botao excluir");
		//confirmação de exclusão
		// A VARIÁVEL CONFIRMA A OPÇÃO ESCOLHIDA DO JOptionPane
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão desse usuário?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			//query (instrução sql)
			String delete = "delete from usuarios where iduser=?" ;
					try {
						
						//abrir conexão
						con = dao.conectar();
						// preparar a query 
						pst = con.prepareStatement(delete);
						pst.setString(1, txtID.getText());
						//executar a query
						pst.executeUpdate();
						//limpar campos
						limparCampos();
						JOptionPane.showMessageDialog(null, "Usuário excluído");
					} catch (Exception e) {
						System.out.println(e);
					}
		}
	}//fim do metodo excluir contato
	
	
	
	
	
	/**
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
		// setar botoes pesquisar e criar
		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnBuscar.setEnabled(true);
	} //fim do método limparCampos()
}//fim do codigo

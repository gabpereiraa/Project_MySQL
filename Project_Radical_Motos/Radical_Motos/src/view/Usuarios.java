package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DAO;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

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
		lblNewLabel.setBounds(10, 50, 48, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setBounds(10, 79, 48, 14);
		contentPanel.add(lblLogin);
		
		txtNome = new JTextField();
		txtNome.setBounds(81, 48, 292, 17);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblSenha = new JLabel("SENHA");
		lblSenha.setBounds(10, 110, 48, 14);
		contentPanel.add(lblSenha);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(81, 76, 86, 20);
		contentPanel.add(txtLogin);
		txtLogin.setColumns(10);
		
		JButton btnCreate = new JButton("");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarUsuario();
			}
		});
		btnCreate.setContentAreaFilled(false);
		btnCreate.setIcon(new ImageIcon(Usuarios.class.getResource("/img/add-people.png")));
		btnCreate.setBounds(52, 177, 48, 48);
		contentPanel.add(btnCreate);
		
		JButton btnLimpar = new JButton("");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/eraser.png")));
		btnLimpar.setBounds(110, 177, 48, 48);
		contentPanel.add(btnLimpar);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(81, 108, 232, 17);
		contentPanel.add(txtSenha);
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
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		txtNome.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
	} //fim do método limparCampos()
}//fim do codigo

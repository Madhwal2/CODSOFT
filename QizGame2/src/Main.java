import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Db {

	PreparedStatement pst;
	Statement stmt;
	Connection con;
	ResultSet rs;
	String sqlStr = "insert into signup(id,name,password)values(?,?,?)";
	String search = "select *from signup where name=? and password=?";
	String marks = "select *from signup marks where id=? and name=?";
	String update = "update signup set marks=? where name=?";

	Db() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xyz", "root", "ayush123");
		} catch (Exception e) {
		}

	}

	void insert(String a, String b, String c) throws Exception {

		pst=con.prepareStatement(sql);
		pst.setString(1, a);
		pst.setString(2, b);
		pst.setString(3, c);

		pst.executeUpdate();

	}

	boolean search(String uname, String pwd) throws Exception {

		pst = con.prepareStatement(search);
		pst.setString(1, uname);
		pst.setString(2, pwd);
		rs = pst.executeQuery();
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	void showsaved(String uname, String pwd) throws Exception {

		// store the value of the result generated from the query in a STRING
		pst = con.prepareStatement(search);
		// pst.setString(1, id);
		pst.setString(1, uname);
		pst.setString(2, pwd);
		
		rs=pst.executeQuery();
		
		
		while(rs.next()) {
			
			String id=rs.getString(1);
			String name=rs.getString(2);
			String password=rs.getString(3);
			String marks=rs.getString(4);
			

			
			System.out.println(id+" "+name+" "+password+" "+marks);
			
		}
		
		
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(400, 200, 500, 300);
		frame.setLayout(null);	
		frame.setVisible(true);
		JLabel show = new JLabel(" ");
	        

	}

	void updateresults(String marks, String name) throws Exception {
		pst = con.prepareStatement(update);
		pst.setString(1, marks);
		pst.setString(2, name);
		pst.executeUpdate();

	}
}
class Signup extends JFrame {
	private static final long serialVersionUID = 1L;
	JTextField idField, nameField;
	JPasswordField passwordField;
	JButton submitButton;
	JLabel idLabel, nameLabel, passwordLabel;

	Signup() {
		setTitle("Sign Up");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		idLabel = new JLabel("ID:");
		nameLabel = new JLabel("Name:");
		passwordLabel = new JLabel("Password:");

		idField = new JTextField(20);
		nameField = new JTextField(20);
		passwordField = new JPasswordField(20);

		submitButton = new JButton("Submit");

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		add(idLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		add(idField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		add(nameLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		add(nameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		add(passwordLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		add(passwordField, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		add(submitButton, gbc);

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					Db d = new Db();
					d.insert(idField.getText(), nameField.getText(), new String(passwordField.getPassword()));

					JOptionPane.showMessageDialog(Signup.this, "Registration successful");
					dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	JTextField usernameField;
	JPasswordField passwordField;
	JButton loginButton, signupButton;
	JLabel loginLabel, usernameLabel, passwordLabel;

	Login() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		loginLabel = new JLabel("LOGIN");
		loginLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		loginLabel.setForeground(Color.BLUE);

		usernameLabel = new JLabel("Username:");
		passwordLabel = new JLabel("Password:");

		usernameField = new JTextField(20);
		passwordField = new JPasswordField(20);

		loginButton = new JButton("Login");
		signupButton = new JButton("Sign Up");

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(loginLabel, gbc);

		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		add(usernameLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		add(usernameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		add(passwordLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		add(passwordField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(loginButton, gbc);

		gbc.gridy = 4;
		add(signupButton, gbc);

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean matched = false;
				String uname = usernameField.getText();
				String pwd = new String(passwordField.getPassword());
				try {
					Db d = new Db();
					if (d.search(uname, pwd)) {
						matched = true;
					}
				} catch (Exception ce) {
					ce.printStackTrace();
				}
				if (matched) {
					JFrame frame = new JFrame();
					JLabel details = new JLabel("Welcome " + uname);
					JButton saved = new JButton("Saved");
					JButton newgame = new JButton("New Game");

					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setSize(500, 300);
					frame.setLayout(new GridBagLayout());
					GridBagConstraints fg = new GridBagConstraints();
					fg.insets = new Insets(10, 10, 10, 10);

					fg.gridx = 0;
					fg.gridy = 0;
					frame.add(details, fg);

					fg.gridx = 0;
					fg.gridy = 1;
					frame.add(saved, fg);

					fg.gridx = 1;
					fg.gridy = 1;
					frame.add(newgame, fg);

					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					dispose();

					saved.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JFrame savedFrame = new JFrame();
							try {
								Db d = new Db();
								d.showsaved(uname, pwd);
							} catch (Exception e1) {
								e1.printStackTrace();
							}

							JLabel savedDetails = new JLabel("Welcome " + uname);
							savedFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							savedFrame.setSize(500, 300);
							savedFrame.setLayout(new GridBagLayout());
							GridBagConstraints sf = new GridBagConstraints();
							sf.insets = new Insets(10, 10, 10, 10);

							sf.gridx = 0;
							sf.gridy = 0;
							savedFrame.add(savedDetails, sf);

							savedFrame.setLocationRelativeTo(null);
							savedFrame.setVisible(true);
						}
					});

					newgame.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							new Quiz();
						}
					});
				} else {
					JOptionPane.showMessageDialog(Login.this, "Wrong username or password");
				}
			}
		});

		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Signup signup = new Signup();
				signup.setVisible(true);
			}
		});
	}
}

public class Main {
	public static void main(String[] args) throws Exception {
		Login login = new Login();
		login.setBounds(400, 200, 500, 300);
		login.setVisible(true);
	}
}
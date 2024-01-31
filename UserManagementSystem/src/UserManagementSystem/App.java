package UserManagementSystem;


//user management system
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class myapp extends Frame implements ActionListener {
	Label lblTitle, lblID, lblName, lblCity, lblAge, lblStatus;
	TextField txtID, txtName, txtCity, txtAge;
	Button btSave, btClear, btDelete;

	String qry;
	Connection con;
	Statement stmt;
	ResultSet rs;
	PreparedStatement st;

//Database connection
	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/company";
			String username = "root";
			String password = "root";
			con = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void clear() {
		txtID.setText("");
		txtName.setText("");
		txtCity.setText("");
		txtAge.setText("");
		txtName.requestFocus();
	}

	public myapp() {
		connect();
		setTitle("User ManageMent System");
		setSize(1000, 600);
		setLayout(null);
		setVisible(true);
		Color c = new Color(53, 59, 72);
		setBackground(c);

		Font titleFont = new Font("arial", Font.BOLD, 25);
		Font labelFont = new Font("arial", Font.PLAIN, 18);
		Font textFont = new Font("arail", Font.PLAIN, 18);

		lblTitle = new Label("User ManageMent System");
		lblTitle.setBounds(250, 40, 400, 50);
		lblTitle.setFont(titleFont);
		lblTitle.setForeground(Color.YELLOW);
		add(lblTitle);

		lblID = new Label("ID");
		lblID.setBounds(250, 100, 150, 30);
		lblID.setFont(labelFont);
		lblID.setForeground(Color.WHITE);
		add(lblID);

		txtID = new TextField();
		txtID.setBounds(400, 100, 400, 30);
		txtID.setFont(textFont);
		txtID.addActionListener(this);
		add(txtID);

		lblName = new Label("Name");
		lblName.setBounds(250, 150, 150, 30);
		lblName.setFont(labelFont);
		lblName.setForeground(Color.WHITE);
		add(lblName);

		txtName = new TextField();
		txtName.setBounds(400, 150, 400, 30);
		txtName.setFont(textFont);
		add(txtName);

		lblAge = new Label("Age");
		lblAge.setBounds(250, 200, 150, 30);
		lblAge.setFont(labelFont);
		lblAge.setForeground(Color.WHITE);
		add(lblAge);

		txtAge = new TextField();
		txtAge.setBounds(400, 200, 400, 30);
		txtAge.setFont(textFont);
		add(txtAge);

		lblCity = new Label("City");
		lblCity.setBounds(250, 250, 150, 30);
		lblCity.setFont(labelFont);
		lblCity.setForeground(Color.WHITE);
		add(lblCity);

		txtCity = new TextField();
		txtCity.setBounds(400, 250, 400, 30);
		txtCity.setFont(textFont);
		add(txtCity);

		btSave = new Button("Save");
		btSave.setBounds(400, 300, 100, 30);
		btSave.setBackground(Color.GREEN);
		btSave.setForeground(Color.WHITE);
		btSave.setFont(labelFont);
		btSave.addActionListener(this);
		add(btSave);

		btClear = new Button("Clear");
		btClear.setBounds(520, 300, 100, 30);
		btClear.setBackground(Color.ORANGE);
		btClear.setForeground(Color.WHITE);
		btClear.setFont(labelFont);
		btClear.addActionListener(this);
		add(btClear);

		btDelete = new Button("Delete");
		btDelete.setBounds(640, 300, 100, 30);
		btDelete.setBackground(Color.RED);
		btDelete.setForeground(Color.WHITE);
		btDelete.setFont(labelFont);
		btDelete.addActionListener(this);
		add(btDelete);

		lblStatus = new Label("---------------");
		lblStatus.setBounds(400, 350, 300, 30);
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setFont(labelFont);
		add(lblStatus);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String id = txtID.getText();
			String name = txtName.getText();
			String city = txtCity.getText();
			String age = txtAge.getText();

//			Read Details
			if (e.getSource().equals(txtID)) {
				qry = "select ID,NAME_,AGE,CITY from users where ID=" + txtID.getText();
				stmt = con.createStatement();
				rs = stmt.executeQuery(qry);
				if (rs.next()) {
					txtID.setText(rs.getString("ID"));
					txtName.setText(rs.getString("NAME_"));
					txtAge.setText(rs.getString("AGE"));
					txtCity.setText(rs.getString("CITY"));
				} else {
					clear();
					lblStatus.setText("Invalid Data!");
				}
			}
			if (e.getSource().equals(btClear)) {
				clear();
			} else if (e.getSource().equals(btSave)) {
				if (id.isEmpty() || id.equals("")) {
//			save Details
					qry = "insert into users(NAME_,AGE,CITY) values(?,?,?)";
					st = con.prepareStatement(qry);
					st.setString(1, name);
					st.setString(2, age);
					st.setString(3, city);
					st.executeUpdate();
					clear();
				
					lblStatus.setText("Data Inserted Successfully!");
				} else {
//			update Details	
					qry = "update users set NAME_=?,AGE=?,CITY=? where ID=?";
					st = con.prepareStatement(qry);
					st.setString(1, name);
					st.setString(2, age);
					st.setString(3, city);
					st.setString(4, id);
					st.executeUpdate();
					clear();
					lblStatus.setText("Data Updated Successfully!");
				}
			} else if (e.getSource().equals(btDelete)) {
				
//			 Delete Details
				if (!id.isEmpty() || !id.equals("")) {
					qry = "delete from users where ID=?";
					st = con.prepareStatement(qry);
					st.setString(1, id);
					st.execute();
					clear();
					lblStatus.setText("Data Deleted Successfully!");
				} else {
					lblStatus.setText("Please Enter the Correct ID!");
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}


public class App {

	public static void main(String[] args) {
		
		myapp m = new myapp();
	}

}

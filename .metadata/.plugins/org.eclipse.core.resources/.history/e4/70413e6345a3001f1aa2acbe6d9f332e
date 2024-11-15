import model.*;
import view.*;
import controller.*;



public class BloodDonationApp {
	public static void main(String[] args) {
		Connector conn = new Connector();
		LogIn view = new LogIn();
		User_account model = new User_account();
		LogInController Controller = new LogInController(conn.getConnection(),view,model);
		view.setVisible(true);
		view.setController(Controller);
	}
}

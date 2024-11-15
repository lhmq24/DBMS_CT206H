import model.*;
import view.*;
import controller.*;



public class BloodDonationApp {
	public static void main(String[] args) {
		Connector conn = new Connector();
		LogIn view = new LogIn();
		LogInController Controller = new LogInController(conn.getConnection(),view);
		view.setVisible(true);
		view.setController(Controller);
	}
}

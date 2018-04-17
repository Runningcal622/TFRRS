package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import Server.Athlete;
import Server.Result;
import Server.Tfrrs;
import javafx.application.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TGUI extends Application
{
	Tfrrs tfrrs = ToWeb.deceral();
	ToWeb toWeb = new ToWeb();
	/// text of what teams have been added
	private Text maleT = new Text();
	private Text femaleT = new Text();

	// arrays of added team names
	ArrayList<String> getMale = new ArrayList<String>();
	ArrayList<String> getFemale = new ArrayList<String>();

	ArrayList<String> remMale = new ArrayList<String>();
	ArrayList<String> remFemale = new ArrayList<String>();

	/// no added element warning
	Dialog<?> mess;
	//// boxes of all teams
	ComboBox<String> teamListF = new ComboBox<String>();
	ComboBox<String> teamListM = new ComboBox<String>();

	ComboBox<String> remF = new ComboBox<String>();
	ComboBox<String> remM = new ComboBox<String>();

	/// auto complete boxes
	AutoCompleteComboBoxListener<String> acMale = new AutoCompleteComboBoxListener<String>(teamListM);
	AutoCompleteComboBoxListener<String> acFemale = new AutoCompleteComboBoxListener<String>(teamListF);

	AutoCompleteComboBoxListener<String> reMale = new AutoCompleteComboBoxListener<String>(remM);
	AutoCompleteComboBoxListener<String> reFemale = new AutoCompleteComboBoxListener<String>(remF);

	/// get the process roling
	// same as all javafx applications
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		BorderPane bp = new BorderPane();// overall structure
		Scene front = new Scene(bp, 725, 600);
		stage.setMinHeight(600);
		stage.setMinWidth(725);

		maleT.setText("No male teams Selected");
		femaleT.setText("No Female teams Selected");
		VBox teamLabels = new VBox();
		teamLabels.setPadding(new Insets(10));
		teamLabels.getChildren().addAll(femaleT, maleT);

		/// make the boxes have the team names
		addTeamsToList(teamListF, teamListM);
		teamListM.getItems().add("Add Male Teams");
		teamListF.getItems().add("Add Female Teams");

		// make a HBox for team comboboxes and the add button
		HBox teamButtons = new HBox();
		teamButtons.getChildren().add(teamListF);
		teamButtons.getChildren().add(teamListM);
		teamButtons.setSpacing(10);
		// add button
		Button add = new Button();
		add.setText("Add Team");
		add.setOnAction(e -> addActionBtn(teamListF.getValue(), teamListM.getValue()));
		add.setMinWidth(83);

		teamButtons.getChildren().add(add);

		Button rem = new Button();
		rem.setText("Delete Team");
		rem.setOnAction(e -> remActionBtn(remF.getValue(), remM.getValue()));

		HBox removal = new HBox();
		remF.getItems().addAll(getFemale);
		remM.getItems().addAll(getMale);

		removal.getChildren().addAll(remF, remM, rem);
		removal.setSpacing(10);

		// make vbox for labels and comboboxes
		VBox namesCombo = new VBox();

		namesCombo.getChildren().addAll(teamLabels, teamButtons, removal);
		bp.setCenter(namesCombo);

		Button complete = new Button("Press when done editing teams");

		complete.setOnAction(e -> {
			try
			{
				completeActionBtn();
			} catch (IOException e1)
			{

				e1.printStackTrace();
			}
		});
		namesCombo.getChildren().add(complete);
		/// show to the screen
		stage.setScene(front);
		stage.setTitle("Add/Remove Teams");
		stage.show();
	}

	
	/*
	 * pass in a local server object for it to update it
	 */
	public void updateTeams(LocalServer local)
	{
		Encoder e = new Encoder("TeamData.txt");
		try
		{
			e.serialize(local);
		} catch (FileNotFoundException e1)
		{
			System.out.println("File not found");
		}
		

	}

	//// complete label action

	private void completeActionBtn() throws IOException
	{
		/// team web addresses
		Map<String, String> address = ToWeb.GuiInput(getFemale, getMale, tfrrs);

		/// athletes of those teams web addresses
		Map<String, ArrayList<String>> athletes = ToWeb.getAthletes(address);

		/// map of team to array of athletes
		Map<String, ArrayList<Athlete>> teamsToAthletes = ToWeb.getAthInfo(athletes);

		/// for each athlete of each team
		for (Entry<String, ArrayList<Athlete>> ent : teamsToAthletes.entrySet())
		{
			for (Athlete ath : ent.getValue())
			{
				ath = ToWeb.getStats(ath);
			}
		}

	}

	private void remActionBtn(String female, String male)
	{
		teamListM.setValue(null);
		teamListF.setValue(null);

		Object[] o = setNull(female, male);
		female = (String) o[0];
		male = (String) o[1];

		if (female == null && male == null)
		{
			mess = new Dialog<>();
			mess.setTitle("No removal made");
			mess.setContentText("You did not make a selection");
			mess.getDialogPane().getButtonTypes().add(new ButtonType("Close", ButtonData.CANCEL_CLOSE));
			mess.setOnCloseRequest(e -> mess.close());
			mess.show();
		}

		/// if female has input
		if (female != null)
		{
			String women;
			if (getFemale.size() == 1)
			{
				women = "Female Teams: ";
			} else
			{
				women = femaleT.getText().replace(female, "");
			}
			getFemale.remove(female);// add to get list
			remF.getItems().remove(female);
			teamListF.getItems().add(female);// remove from selection choices
			femaleT.setText(women);// set text to include team name
		}
		/// male has input
		if (male != null)
		{
			String men;
			if (getMale.size() == 1)
			{
				men = "Male Teams: ";
			} else
			{
				men = maleT.getText().replace(male, "");
			}
			getMale.remove(male);// add to get list
			remM.getItems().remove(male);
			teamListM.getItems().add(male);// remove from selection choices
			maleT.setText(men);// set text to include team name
		}
	}

	/// if input == "" sets it to null
	private Object[] setNull(String female, String male)
	{
		if (male != null)
		{
			if (male.equals(""))
			{
				male = null;
			}
		}
		if (female != null)
		{
			if (female.equals(""))
			{
				female = null;
			}
		}
		return new Object[] { female, male };
	}

	/// The action for adding a team to the list of teams to get data for
	private void addActionBtn(String female, String male)
	{
		teamListM.setValue(null);
		teamListF.setValue(null);

		/// this tells if it is actually null or not
		Object[] o = setNull(female, male);
		female = (String) o[0];
		male = (String) o[1];

		/// if no input
		if (female == null && male == null)
		{
			mess = new Dialog<>();
			mess.setTitle("No addition made");
			mess.setContentText("You did not make a selection");
			mess.getDialogPane().getButtonTypes().add(new ButtonType("Close", ButtonData.CANCEL_CLOSE));
			mess.setOnCloseRequest(e -> mess.close());
			mess.show();
		}

		/// if female has input
		if (female != null)
		{
			String women;
			if (getFemale.size() == 0)
			{
				women = "Female Teams" + ": " + female;
			} else
			{
				women = femaleT.getText() + ", " + female;
			}
			getFemale.add(female);// add to get list
			remF.getItems().add(female);
			teamListF.getItems().remove(female);// remove from selection choices
			femaleT.setText(women);// set text to include team name
		}
		if (male != null)
		{
			String men;
			if (getMale.size() == 0)
			{
				men = "Male Teams" + ": " + male;
			} else
			{
				men = maleT.getText() + ", " + male;
			}
			getMale.add(male);// add to get list
			remM.getItems().add(male);
			teamListM.getItems().remove(male);// remove from selection choices
			maleT.setText(men);// set text to include team name
		}

	}
	//// this takes the tffrs list and sorts for male teams and female teams

	private void addTeamsToList(ComboBox<String> teamListF, ComboBox<String> teamListM)
	{
		/// get a sorted female list
		ArrayList<String> sortedFemale = new ArrayList<String>(tfrrs.femaleTeams.keySet());
		Collections.sort(sortedFemale);
		/// get a sorted male list
		ArrayList<String> sortedMale = new ArrayList<String>(tfrrs.maleTeams.keySet());
		Collections.sort(sortedMale);

		teamListF.getItems().addAll(sortedFemale);
		teamListM.getItems().addAll(sortedMale);

	}
}

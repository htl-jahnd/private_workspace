package pkgController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pkgData.Activity;
import pkgData.Database;
import pkgData.Entry;
import pkgData.User;
import pkgMisc.ExceptionHandler;
import static java.util.stream.Collectors.*;

import java.time.temporal.ChronoUnit;

import static java.util.Map.Entry.*;

public class Statistics_Controller
{

	@FXML
	private JFXCheckBox chckbxShowWholeStatistic;

	@FXML
	private HBox paneUsername;

	@FXML
	private JFXComboBox<User> cmbxUser;

	@FXML
	private PieChart chartTime;

	@FXML
	private PieChart chartUsers;

	public static Stage mainStage;
	private Database db;
	private ObservableList<User> listUsers;
	private ObservableList<PieChart.Data> listActivityChartData;
	private ObservableList<PieChart.Data> listUserChartData;
	private ArrayList<Entry> chartDataEntries;

	@FXML
	void initialize()
	{
		try
		{
			db = Database.newInstance();
			listUsers = FXCollections.observableArrayList();
			listActivityChartData = FXCollections.observableArrayList();
			listUserChartData = FXCollections.observableArrayList();

			db.selectAllUsers();
			listUsers.setAll(db.getAllUsers());
			cmbxUser.setItems(listUsers);
			cmbxUser.getSelectionModel().selectFirst();

			db.selectAllEntries();
			chartDataEntries = new ArrayList<>(db.getAllEntries());

			chartTime.setData(listActivityChartData);
			chartTime.setLegendVisible(false);
			refreshActivityChart();

			chartUsers.setData(listUserChartData);
			refreshUserChart();
		} catch (Exception e)
		{
			handleException(e);
		}
	}

	private void refreshUserChart()
	{
		HashMap<User, Long> usrs = new HashMap<User, Long>();
		List<User> lstUsrs = new ArrayList<User>(db.getAllUsers());
		for (Entry etr : db.getAllEntries())
		{
			for (int i = 0; i < lstUsrs.size(); i++)
			{
				if (etr.getUser().equals(lstUsrs.get(i)))
				{
					long tmp =0;
					User tmpUsr = lstUsrs.get(i);
					if(usrs.get(tmpUsr) != null) {
						
						tmp = usrs.get(tmpUsr);
						long time = ChronoUnit.MINUTES.between(etr.getTimeStart(), etr.getTimeEnd());
						tmp+=time;
						usrs.replace(tmpUsr, tmp);
					}
					else {
						long time = ChronoUnit.MINUTES.between(etr.getTimeStart(), etr.getTimeEnd());
						tmp+=time;
						usrs.put(tmpUsr, tmp);
					}
				}
			}
		}
		
		Map<User, Long> sortedUserTime = (HashMap<User, Long>) usrs.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
		listUserChartData.clear();
		if (sortedUserTime.size() < 5)
		{
			for (User u : sortedUserTime.keySet())
			{
				listUserChartData.add(new Data(u.getUsername(), sortedUserTime.get(u)));
			}
		} else
		{
			int i = 0;
			int timeForOthers = 0;
			for (User u : sortedUserTime.keySet())
			{
				if (i >= 5)
				{
					timeForOthers += sortedUserTime.get(u);
				}
				if (i < 5)
				{
					listUserChartData.add(new Data(u.getUsername(), sortedUserTime.get(u)));
					i++;
				}
			}
			listUserChartData.add(new Data("Others", timeForOthers));
		}
	}

	private void refreshActivityChart()
	{
		HashMap<Activity, Integer> mapCounter = new HashMap<Activity, Integer>();
		List<Activity> listActs = new ArrayList<>(db.getAllActivities());
		for (Entry etr : chartDataEntries)
		{
			for (int i = 0; i < listActs.size(); i++)
			{
				if (etr.getActivity().equals(listActs.get(i)))
				{
					int tmp = 0;
					if (mapCounter.get(listActs.get(i)) != null)
					{
						tmp = mapCounter.get(listActs.get(i));
						tmp++;
						mapCounter.replace(listActs.get(i), tmp);
					} else
					{
						tmp++;
						mapCounter.put(listActs.get(i), tmp);
					}
					break;
				}
			}
		}

		Map<Activity, Integer> sortedActivityCounter = (HashMap<Activity, Integer>) mapCounter.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

		listActivityChartData.clear();
		if (sortedActivityCounter.size() < 5)
		{
			for (Activity a : sortedActivityCounter.keySet())
			{
				listActivityChartData.add(new Data(a.getName(), sortedActivityCounter.get(a)));
			}
		} else
		{
			int i = 0;
			int timeForOthers = 0;
			for (Activity a : sortedActivityCounter.keySet())
			{
				if (i >= 5)
				{
					timeForOthers += sortedActivityCounter.get(a);
				}
				if (i < 5)
				{
					listActivityChartData.add(new Data(a.getName(), sortedActivityCounter.get(a)));
					i++;
				}
			}
			listActivityChartData.add(new Data("Others", timeForOthers));
		}
	}

	@FXML
	void onSelectCheckbox(ActionEvent event)
	{
		if (event.getSource().equals(chckbxShowWholeStatistic))
		{
			if (chckbxShowWholeStatistic.isSelected())
			{
				paneUsername.setDisable(true);
				chartDataEntries = new ArrayList<>(db.getAllEntries());
				refreshActivityChart();
			} else
			{
				paneUsername.setDisable(false);
			}
		}
	}

	@FXML
	void onSelectComboBox(ActionEvent event)
	{
		try
		{
			if (event.getSource().equals(cmbxUser))
			{
				chartDataEntries.clear();
				db.selectUserEntries(cmbxUser.getSelectionModel().getSelectedItem());
				chartDataEntries.addAll(db.getUserEntries());
				refreshActivityChart();
			}
		} catch (Exception e)
		{
			handleException(e);
		}
	}

	private void handleException(Exception e)
	{
		ExceptionHandler.hanldeUnexpectedException(e);
	}

}

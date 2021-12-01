package com.example.demolr8;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class HelloController {
    public Button button;
    @FXML
    private Label correctLabel;
    @FXML
    private TableView<BankCurrency> table;
    @FXML
    private TableColumn<BankCurrency, Long> r030;
    @FXML
    private TableColumn<BankCurrency, String> txt;
    @FXML
    private TableColumn<BankCurrency, Double> rate;
    @FXML
    private TableColumn<BankCurrency, String> cc;
    @FXML
    private TableColumn<BankCurrency, String> exchangeDate;
    @FXML
    TextField searching;
    public static final int MS_TIMEOUT = 50;
    public static final int TIMEOUT = 30000;
    public static DataBase db;
    public void loadCurrency() throws InterruptedException, SQLException {
        JSONGetter jsonGetter = new JSONGetter();
        JSONGetter.url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
        jsonGetter.start();
        int msForWaiting = 0;
        do {
            msForWaiting += MS_TIMEOUT;
            Thread.sleep(MS_TIMEOUT); //milliseconds

        } while (msForWaiting <= TIMEOUT && jsonGetter.jsonIn.equals(""));
        if (msForWaiting >= TIMEOUT) {

            return;
        }
        String jsonString = jsonGetter.jsonIn;

        if(!jsonString.equalsIgnoreCase("Couldn't find API")) {

            Object tempObj = null;
            try {
                tempObj = new JSONParser().parse(jsonString);
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            JSONArray jsonArray = (JSONArray) tempObj;

            CurrencyObservableList metas = new CurrencyObservableList();

            assert jsonArray != null;
            //int temp=2;
            baseLoad();
           db.delete("DELETE FROM `currency` WHERE r030>0 ");
            for (Object jsonObject : jsonArray) {
                JSONObject getMeta = (JSONObject) jsonObject;
                long r030 = (long) getMeta.get("r030");
                String txt = (String) getMeta.get("txt");
                double rate = Double.parseDouble(Double.toString((Double) getMeta.get("rate")));
                String cc = (String) getMeta.get("cc");
                String exchangeDate = (String) getMeta.get("exchangedate");
                db.insert("INSERT INTO currency( r030, txt, rate, cc, exchangedate) VALUES('"+r030+"','"+txt+"','"+rate+"','"+cc+"','"+exchangeDate+"')");

                BankCurrency newMeta = new BankCurrency(r030, txt, rate, cc, exchangeDate);
                metas.add(newMeta);
            }

            r030.setCellValueFactory(new PropertyValueFactory<>("r030"));
            txt.setCellValueFactory(new PropertyValueFactory<>("txt"));
            rate.setCellValueFactory(new PropertyValueFactory<>("rate"));
            cc.setCellValueFactory(new PropertyValueFactory<>("cc"));
            exchangeDate.setCellValueFactory(new PropertyValueFactory<>("exchangeDate"));

            table.setItems(metas.getMetas());
            correctLabel.setText("Данные успешно загружены!");

        }else {
            correctLabel.setText("Данные не доступны, поэтому не были загружены!");
        }

    }

    public void baseLoad() throws SQLException {
        db = new DataBase("testjava");
        System.out.println(db.Connect() ? "Connected successfully" : "Connection failed");
        useDB();
    }
    public static void useDB() throws SQLException {
        db.executeQuery("use " + db.getDBname() + ";");


        System.out.println();
        //System.out.println(getStringFromResultSet(db.search("us")));
    }
     @FXML
     public void onTextEnterFinish(ActionEvent actionEvent) throws SQLException, InterruptedException {

         baseLoad();
         System.out.println();
         //System.out.println(getStringFromResultSet(db.search(searching.getText())));
         LoadData();
     }
    private static String getStringFromResultSet(ArrayList result) {
        String str = "";

        for (Object row : result) {
            for (String column : (String[]) row) {
                str += column + "\t";
            }
            str += "\n";
        }

        return str;
    }
    public void finishing(){
        System.exit(0);
    }
    public void LoadData() {

        try
        {
            //  Формирование запросов к БД
            Statement st = db.con.createStatement();

            ResultSet rs;// = st.executeQuery("use itschool;");

//            rs = st.executeQuery("SELECT * FROM users;");
            rs = st.executeQuery("select r030, txt, rate, cc, exchangedate FROM currency WHERE txt LIKE " +"\'%" + searching.getText()+ "%\'"+ " ;");
            //System.out.println("\nEXECUTING QUERY TO DB: "+rs.toString()+"\n");

            ObservableList<BankCurrency> data = FXCollections.observableArrayList();

            r030.setCellValueFactory(new PropertyValueFactory<>("r030"));
            txt.setCellValueFactory(new PropertyValueFactory<>("txt"));
            rate.setCellValueFactory(new PropertyValueFactory<>("rate"));
            cc.setCellValueFactory(new PropertyValueFactory<>("cc"));
            exchangeDate.setCellValueFactory(new PropertyValueFactory<>("exchangeDate"));


            while (rs.next())
            {

                // System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5));
                data.add(new BankCurrency(rs.getLong("r030"), rs.getString("txt"), rs.getDouble("rate"), rs.getString("cc"), rs.getString("exchangedate")));
            }


            table.setItems(data);

            rs.close();
            st.close();
            //  Закончили запрос
        } catch (SQLException e)
        {
            //header.setText("Error while loading data!!!");
            e.printStackTrace();
        }

    }
    public String Encrypt(String text, int key) {
        String result = "";
        char[] array = text.toCharArray();
        for (int i = 0; i < text.length(); i++) {
            //result += ""+(char)(((int)array[i])+3);
            result += (char) (array[i] + key);
        }
        return result;
    }
}

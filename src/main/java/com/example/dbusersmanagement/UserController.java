package com.example.dbusersmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import record.UserRecord;
import service.UserService;
import service.CompanyService;

public class UserController {

    //追加用
    @FXML
    private ComboBox<String> addCompany;    //コンボボックス（会社名）
    @FXML
    private TextField addName;              //テキストフィールド（名前）
    @FXML
    private TextField addScore;             //テキストフィールド（スコア）
    //編集用
    @FXML
    private ComboBox<String> editCompany;   //コンボボックス（会社名）
    @FXML
    private TextField editScore;            //テキストフィールド（名前）
    @FXML
    private TextField editName;             //テキストフィールド（スコア）
    //表示用
    @FXML
    private TableView<User> table;                  //テーブルビュー
    @FXML
    private TableColumn<User, String> viewCompany;  //カラムID（会社名）
    @FXML
    private TableColumn<User, String> viewName;     //カラムID（名前）
    @FXML
    private TableColumn<User, String> viewScore;    //カラムID（スコア）
    @FXML
    private GridPane all;
    @FXML
    private Label error;    //エラー文表示用ラベル
    private int id;         //
    private int oldId = 0;         //
    private ObservableList<User> data;
    int selectId;
    UserService UserService;
    CompanyService CompanyService;

    //初期設定
    @FXML
    private void initialize() {
        this.CompanyService = new CompanyService();
        this.UserService = new UserService();
        var companys = CompanyService.nameAll();
        //コンボボックス項目初期化
        ObservableList<String> items = FXCollections.observableArrayList();
        for (var i=0; i<companys.size(); i++) {
            items.add(companys.get(i).name());
        }
        addCompany.setItems(items);
        editCompany.setItems(items);
        viewCompany.setCellValueFactory(cell -> cell.getValue().companyProperty());
        viewName.setCellValueFactory(cell -> cell.getValue().nameProperty());
        viewScore.setCellValueFactory(cell -> cell.getValue().scoreProperty());
        //データ格納リスト
        data = FXCollections.observableArrayList();
        var users = UserService.nameAll();
        for (var i=0; i<users.size(); i++) {
            data.add(new User(users.get(i).id(), users.get(i).company_id(), users.get(i).name(), String.valueOf(users.get(i).score())));
        }
        table.setItems(data);
        //tableview選択時の処理
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // 選択されたUserオブジェクトの情報をTextFieldに表示
                id = newSelection.getId();
                selectId = newSelection.getId();
                editCompany.setValue(newSelection.getCompany());
                editName.setText(newSelection.getName());
                editScore.setText(newSelection.getScore());
            }
        });

        //選択中のテーブルがもう一度選択されたときに選択を解除
        table.setOnMouseClicked(event -> {
            if(oldId == id){
                allClear();
            } else {
                oldId = id;
            }
        });

        all.setOnMousePressed(event -> {
            System.out.println("a");
            editName.clear();
            editScore.clear();
            editCompany.getSelectionModel().clearSelection();
            table.getSelectionModel().clearSelection();
            oldId = 0;
        });
    }

    //ユーザー追加メソッド
    @FXML
    public void add(ActionEvent actionEvent) {
        error.setText("");
        String company = addCompany.getValue();
        String name = addName.getText().trim();
        String score = addScore.getText().trim();
        if (checkArgument(company, name, score)) {
            try {
                var company_id = companyId(company);
                UserService.insert(new UserRecord(0, company_id, name, Integer.parseInt(score)));
                data.clear();
                var users = UserService.nameAll();
                for (var i=0; i<users.size(); i++) {
                    data.add(new User(users.get(i).id(), users.get(i).company_id(), users.get(i).name(), String.valueOf(users.get(i).score())));
                }
            } catch (Exception e){
                throw new RuntimeException();
            }
            allClear();
        }
    }

    //ユーザー削除メソッド
    @FXML
    public void delete(ActionEvent actionEvent) {
        error.setText("");
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        try {
            if (selectedIndex >= 0) {
                UserService.delete(selectId);
                var users = UserService.nameAll();
                data.clear();
                for (var i=0; i<users.size(); i++) {
                    data.add(new User(users.get(i).id(), users.get(i).company_id(), users.get(i).name(), String.valueOf(users.get(i).score())));
                }
                allClear();
            } else if (selectedIndex == -1) {
                error.setText("※データを選択してください");
                allClear();
            }
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    //ユーザー編集メソッド
    @FXML
    public void update(ActionEvent actionEvent) {
        error.setText("");
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            String name = editName.getText().trim();
            String score = editScore.getText().trim();
            String company = editCompany.getValue();
            if (checkArgument(company, name, score)) {
                try {
                    var company_id = companyId(company);
                    UserService.update(new UserRecord(selectId, company_id, name, Integer.parseInt(score)));
                    data.clear();
                    var users = UserService.nameAll();
                    for (var i=0; i<users.size(); i++) {
                        data.add(new User(users.get(i).id(), users.get(i).company_id(), users.get(i).name(), String.valueOf(users.get(i).score())));
                    }
                } catch (Exception e){
                    throw new RuntimeException();
                }
                allClear();
            }
        } else if (selectedIndex == -1) {
            error.setText("※データを選択してください");
            allClear();
        }
    }

    //各項目チェックメソッド
    public boolean checkArgument(String company, String name, String score) {
        if (name.isEmpty() || score.isEmpty() || company == null) {
            error.setText("※未入力の項目があります");
            return false;
        } else {
            return (checkScore(score));
        }
    }

    //スコア範囲チェックメソッド
    public boolean checkScore(String score){
        try {
            var intScore = Double.parseDouble(score);
            if (intScore >= 0 && intScore <= 100){
                try {
                    Integer.parseInt(score);
                } catch (Exception e){
                    error.setText("※整数を入力してください");
                    return false;
                }
                return true;
            } else {
                error.setText("※数値の範囲が正しくありません(1～100)");
                return false;
            }
        } catch (Exception e){
            error.setText("※整数を入力してください");
            return false;
        }
    }

    public void allClear(){
        addName.clear();
        addScore.clear();
        editName.clear();
        editScore.clear();
        addCompany.getSelectionModel().clearSelection();
        editCompany.getSelectionModel().clearSelection();
        table.getSelectionModel().clearSelection();
        oldId = 0;
    }

    public int companyId(String company){
        var companys = CompanyService.nameAll();
        for (var i=0; i<companys.size(); i++) {
            if(companys.get(i).name().equals(company)){
                return companys.get(i).id();
            }
        }
        return 0;
    }
}
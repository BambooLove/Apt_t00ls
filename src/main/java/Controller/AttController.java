package Controller;

import Utilss.Kinds_Exp;
import Utilss.shell;
import core.Exploitlnterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class AttController {

  private final Kinds_Exp exp = new Kinds_Exp();//初始化EXP相关数据


  private boolean initialized = false;//是否初始化

  @FXML
  private RadioButton radioButton_getshell;

  @FXML
  private Button Button_Att;

  @FXML
  private TextArea textArea_attInfo;

  @FXML
  private TextField textField_url;

  @FXML
  private TextArea textArea_info;

  @FXML
  private ChoiceBox<String> choiceBox_exp;

  @FXML
  private ListView<String> listview_kinds;

  @FXML
  private ChoiceBox<String> choiceBox_kinds;

  @FXML
  private Button button_jsp;

  @FXML
  private Button button_jspx;

  @FXML
  private Button button_asp;

  @FXML
  private Button button_aspx;

  @FXML
  private Button button_dnslog_token;

  @FXML
  private Button button_dnslog;

  @FXML
  private Button button_php;

  @FXML
  void clicked_jsp(MouseEvent event) {
    Runtime run = Runtime.getRuntime();
    //path:文件路径
    try {
      run.exec("notepad " + shell.Jsppath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void clicked_jspx(MouseEvent event) {
    Runtime run = Runtime.getRuntime();
    //path:文件路径
    try {
      run.exec("notepad " + shell.Jspxpath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void clicked_asp(MouseEvent event) {
    Runtime run = Runtime.getRuntime();
    //path:文件路径
    try {
      run.exec("notepad " + shell.Asppath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void clicked_aspx(MouseEvent event) {
    Runtime run = Runtime.getRuntime();
    //path:文件路径
    try {
      run.exec("notepad " + shell.Aspxpath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void clicked_php(MouseEvent event) {
    Runtime run = Runtime.getRuntime();
    //path:文件路径
    try {
      run.exec("notepad " + shell.Phppath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void clicked_dnslog(MouseEvent event) {
    Runtime run = Runtime.getRuntime();
    //path:文件路径
    try {
      run.exec("notepad " + shell.dnspath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void clicked_dnslog_token(MouseEvent event) {
    Runtime run = Runtime.getRuntime();
    //path:文件路径
    try {
      run.exec("notepad " + shell.dnscofpath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void Att_clicked(MouseEvent event) {         //ATT按钮
    //初始清空
    textArea_attInfo.setText("");
    //获取url地址
    String url;
    if (textField_url.getText().trim().endsWith("/")) {
      url = textField_url.getText().trim()
          .substring(0, textField_url.getText().trim().length() - 1);
    } else {
      url = textField_url.getText().trim();
    }
    //获取需要利用的exp
    String vulname = choiceBox_exp.getValue();
    //获取get shell按钮是否被选中
    Boolean getshell = radioButton_getshell.selectedProperty().get();

    //如果是all
    if (vulname != null && vulname.equals("All")) {

      for (String val : choiceBox_exp.getItems()) {
        if (!val.equals("All")) {
          Exploitlnterface exploit = Kinds_Exp.getExploit(val);
          Boolean aBoolean = exploit.checkVul(url, textArea_attInfo);
          if (aBoolean) {
            textArea_attInfo.appendText("\n----" + val + "漏洞存在----\n");
          }
        }
      }
      textArea_attInfo.appendText("\n\n如需获取shell请勾选 getshell并选择具体漏洞");

    } else if (vulname != null) {

      //生成exp对应类对象
      Exploitlnterface exploit = Kinds_Exp.getExploit(vulname);
      //检查是否存在漏洞
      Boolean checkVul = exploit.checkVul(url, textArea_attInfo);
      if (checkVul) {
        if (!getshell) {
          textArea_attInfo.appendText("\n可以进行GetShell, 请选中getshell 单击ATT");
        }
        if (getshell) {
          Boolean shell_success = exploit.getshell(url, textArea_attInfo);
          if (shell_success) {
            textArea_attInfo.appendText("\n--Getshell 成功 若无特别说明则默认使用冰蝎4.0.3 aes--");
          }


        }
      }

    }
  }

  @FXML
  public void initialize() {

    textArea_info.setText(
        "------------------------------------目前EXP如下--------------------------------");
    textArea_info.appendText(
        "\ne-cology workrelate_uploadOperation.jsp-RCE (默认写入冰蝎4.0.3aes)");
    textArea_info.appendText("\ne-cology page_uploadOperation.jsp-RCE (暂未找到案例 仅供检测poc)");
    textArea_info.appendText("\ne-cology WorkflowServiceXml-RCE (默认写入内存马 冰蝎 3.0 beta11)");
    textArea_info.appendText("\ne-cology BshServlet-RCE (可直接执行系统命令)");
    textArea_info.appendText("\ne-office logo_UploadFile.php-RCE (默认写入冰蝎4.0.3aes)");
    textArea_info.appendText("\ne-office10 OfficeServer.php-RCE (默认写入冰蝎4.0.3aes)");
    textArea_info.appendText("\ne-mobile_6.6 messageType.do-SQlli (sqlmap利用，暂无直接shell的exp)");

    textArea_info.appendText(
        "\n\nlandray_sysSearchMain-RCE (多个payload，写入哥斯拉 3.03 密码 yes)");
    textArea_info.appendText("\nlandray_treexmlTmpl-RCE (可直接执行系统命令)");
    textArea_info.appendText("\nlandray_datajson-RCE (可直接执行系统命令)");

    textArea_info.appendText(
        "\n\nyongyou_chajet-RCE (用友畅捷通T+ rce 默认写入哥斯拉 Cshap/Cshap_aes_base64)");
    textArea_info.appendText("\nyongyou_NC_bsh.servlet.BshServlet-RCE (可直接执行系统命令)");
    textArea_info.appendText(
        "\nyongyou_NC_NCFindWeb 目录遍历漏洞 (可查看是否存在历史遗留webshell)");
    textArea_info.appendText("\nyongyou_NC_FileReceiveServlet-RCE (默认写入冰蝎4.0.3aes)");

    textArea_info.appendText(
        "\n\nIIS_PUT_RCE (emm暂时没办法getshell  仅支持检测 java没有MOVE方法)");

    textArea_info.appendText("\n\n综合安防_applyCT_fastjson-RCE(仅支持检测,自行使用ladp服务利用)");
    textArea_info.appendText("\n网康下一代防火墙_ngfw_waf_route-RCE(写入菜刀shell 密码:nishizhu)");

    textArea_info.appendText(
        "\n\n-------------------------------(禁止未授权恶意攻击)-----------------------------");

    textArea_info.appendText("\n\n---------小提醒，工具所用shell为冰蝎默认aes加密生成shell"
        + "\n 若工具提示shell写入成功 但访问不存在或连接不上 请考虑免杀，修改shell位置在工具目录下Apt_config"
        + "\n 工具判断shell是否写入依据md5 可自行打开查看 修改shell请保留md5 否则会影响漏洞判断");

    //设置自动换行
    textArea_info.setWrapText(true);
    textArea_attInfo.setWrapText(true);

    //适配屏幕
    System.setProperty("prism.allowhidpi", "true");

    //渲染Tab1 左上 下拉选
    //设置初始化数据
    choiceBox_kinds.setItems(exp.getFXKindList());
    //设置默认选项
    choiceBox_kinds.setValue(exp.getKindList().get(0));
    //选项绑定监听器，当左上 下拉选 数据发生改变，更新列表数据，同时更新exp下拉选数据
    choiceBox_kinds.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> buildChoiceBoxListener(newValue));

    // 第一次渲染该页面时渲染数据
    if (!initialized) {
      //更新列表数据
      buildChoiceBoxListener(exp.getKindList().get(0));
      initialized = true;
    }

  }

  private void buildChoiceBoxListener(String newValue) {
    switch (newValue) {
      case "OA":
        listview_kinds.setItems(Kinds_Exp.oa());
        break;
      case "中间件":
        listview_kinds.setItems(Kinds_Exp.middleware());
        break;
      case "安全设备":
        listview_kinds.setItems(Kinds_Exp.equipment());
        break;
    }
    updateListView(listview_kinds.getItems().get(0));
  }

  /**
   * 列表监听事件，监听所选的类型更新exp下拉选数据.
   */
  @FXML
  void listview_clicked() {
    String selectedItem = listview_kinds.getSelectionModel().getSelectedItem();
    updateListView(selectedItem);
  }

  /**
   * 根据列表所选类型给中间EXP下拉选赋值
   *
   * @param selectedItem 左边列表选的类型
   */
  private void updateListView(String selectedItem) {
    switch (selectedItem) {
      case "泛微-OA":
        choiceBox_exp.setItems(exp.buildWeaverOAList());
        break;
      case "蓝凌-OA":
        choiceBox_exp.setItems(exp.landrayoa());
        break;
      case "用友-OA":
        choiceBox_exp.setItems(exp.yongyouoa());
        break;
      case "IIS":
        choiceBox_exp.setItems(exp.iis());
        break;
      case "海康":
        choiceBox_exp.setItems(exp.hik());
        break;
      case "奇安信":
        choiceBox_exp.setItems(exp.qianxin());
        break;
      default:
        // 当所选项还没有exp给默认选项
        choiceBox_exp.setItems(exp.defaultList());
    }
    // 设置下拉选默认exp
    choiceBox_exp.setValue(exp.getExpList().get(1));
  }
}

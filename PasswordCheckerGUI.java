//簡易パスワードチェッカー
//これは、よく使われるパスワードと、単純なパスワードを「危険なパスワード」と定義し、複雑で長いパスワードほど「安全なパスワード」と
//表示するアプリケーションです。
//パスワード情報は保存・送信といったことはしません。
//このソフトは学習用に作ったものです。
// 万が一このソフトを使用したことによってパスワードが流出したとしても責任は一切取れません。自己責任でご使用ください。

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Set;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public class PasswordCheckerGUI extends JFrame {
    private JTextField passwordField;
    private JProgressBar strengthBar;
    private JLabel strengthLabel;
    private JLabel lengthLabel;
    private JLabel upperLabel;
    private JLabel lowerLabel;
    private JLabel digitLabel;
    private JLabel specialLabel;
    private JLabel dictionaryLabel;
    private JTextArea resultArea;

    private Set<String> badPasswords;

    public PasswordCheckerGUI() {
        setTitle("簡易パスワード強度チェッカー");
        setSize(500,450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //パスワード辞書を読み込み
        loadBadPasswords();

        //UIを構築する
        initUI();
    }

    private void loadBadPasswords() {
        badPasswords = new HashSet<>();
        try {
            Path path = Paths.get("bad_password.txt");
            java.util.List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                badPasswords.add(line.trim().toLowerCase());
            }
            System.out.println("パスワード辞書を読み込みました: " + badPasswords.size() + "件");
        }catch(IOException e){
            JOptionPane.showMessageDialog(this,
                    "bad_password.txt が見つかりません",
                    "エラー",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initUI() {
        setLayout(new BorderLayout(10,10));

        //上部パネル（パスワード入力欄）
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,5,10));

        JLabel inputLabel = new JLabel("パスワード強度を調べたい文字列を入力してください：");
        passwordField = new JTextField();
        passwordField.setFont(new Font("Monospaced", Font.PLAIN, 14));

        topPanel.add(inputLabel, BorderLayout.NORTH);
        topPanel.add(passwordField, BorderLayout.CENTER);

        //中央パネル（パスワード強度を表示する）
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

        //強度バー
        JPanel barPanel = new JPanel(new BorderLayout(5,5));
        strengthLabel = new JLabel("強度： --");
        strengthLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        strengthBar = new JProgressBar(0,100);
        strengthBar.setStringPainted(true);
        barPanel.add(strengthLabel, BorderLayout.NORTH);
        barPanel.add(strengthBar, BorderLayout.CENTER);

        //チェック項目
        JPanel checkPanel = new JPanel(new GridLayout(6,1,5,5));
        checkPanel.setBorder(BorderFactory.createTitledBorder("チェック項目"));

        lengthLabel = new JLabel("✕ 12文字以上");
        upperLabel = new JLabel("✕ 大文字を含む");
        lowerLabel = new JLabel("✕ 小文字を含む");
        digitLabel = new JLabel("✕ 数字を含む");
        specialLabel = new JLabel("✕ 記号を含む");
        dictionaryLabel = new JLabel("✕ 危険なパスワード");

        checkPanel.add(lengthLabel);
        checkPanel.add(upperLabel);
        checkPanel.add(lowerLabel);
        checkPanel.add(digitLabel);
        checkPanel.add(specialLabel);
        checkPanel.add(dictionaryLabel);

        centerPanel.add(checkPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(barPanel);

        //下部パネル（結果表示）
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5,10,10,10));

        resultArea = new JTextArea(3,40);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setFont(new Font("Dialog", Font.PLAIN, 22));
        resultArea.setBackground(new Color(240,240,240));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        //パネルを追加
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        //リアルタイム更新
        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { checkPassword(); }
            public void removeUpdate(DocumentEvent e) { checkPassword(); }
            public void changedUpdate(DocumentEvent e) { checkPassword(); }
        });
    }

    private void checkPassword() {
        String password = passwordField.getText();

        if (password.isEmpty()) {
            resetUI();
            return;
        }

        //チェック実行
        boolean hasLength = password.length() >= 12;
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
        boolean isDangerous = badPasswords.contains(password.toLowerCase());

        //ラベル更新
        lengthLabel.setText((hasLength ? "✓" : "✕") + "12文字以上");
        lengthLabel.setForeground(hasLength ? new Color(0,150,0): Color.RED);

        upperLabel.setText((hasUpper ? "✓" : "✕") + "大文字を含む");
        upperLabel.setForeground(hasUpper ? new Color(0,150,0): Color.RED);

        lowerLabel.setText((hasLower ? "✓" : "✕") + "小文字を含む");
        lowerLabel.setForeground(hasLower ? new Color(0,150,0): Color.RED);

        digitLabel.setText((hasDigit ? "✓" : "✕") + "数字を含む");
        digitLabel.setForeground(hasDigit ? new Color(0,150,0): Color.RED);

        specialLabel.setText((hasSpecial ? "✓" : "✕") + "記号を含む");
        specialLabel.setForeground(hasSpecial ? new Color(0,150,0): Color.RED);

        dictionaryLabel.setText((isDangerous ? "✕" : "✓") + " 危険なパスワード");
        dictionaryLabel.setForeground(isDangerous ? Color.RED : new Color(0,150,0));

        //スコア計算
        int score = 0;
        if (hasLength) score += 25;
        if (hasUpper) score += 15;
        if (hasLower) score += 15;
        if (hasDigit) score += 15;
        if (hasSpecial) score += 15;
        if (!isDangerous) score += 15;

        //強度判定
        String strength;
        String message;
        Color barColor;

        if(isDangerous){
            strength = "危険";
            message = "このパスワードは危険です。よく使われるパスワードのため、すぐに破られる可能性があります";
            barColor = Color.RED;
            score = Math.min(score, 30);

        } else if (score < 40) {
            strength = "弱い";
            message = "パスワードが弱すぎます。もっと複雑にしましょう。";
            barColor = Color.RED;
        } else if (score < 70) {
            strength = "普通";
            message = "まずまずですが、もう少し改善できます。";
            barColor = Color.ORANGE;
        } else if (score < 90) {
            strength = "強い";
            message = "良いパスワードです";
            barColor = new Color(100, 200, 100);
        } else {
            strength = "最強";
            message = "完璧なパスワードです";
            barColor = new Color(0, 150, 0);
        }
        strengthLabel.setText("強度: " + strength);
        strengthBar.setValue(score);
        strengthBar.setForeground(barColor);
        resultArea.setText(message);
    }

    private void resetUI() {
        strengthLabel.setText("強度: --");
        strengthBar.setValue(0);
        lengthLabel.setText("✗ 12文字以上");
        lengthLabel.setForeground(Color.BLACK);
        upperLabel.setText("✗ 大文字を含む");
        upperLabel.setForeground(Color.BLACK);
        lowerLabel.setText("✗ 小文字を含む");
        lowerLabel.setForeground(Color.BLACK);
        digitLabel.setText("✗ 数字を含む");
        digitLabel.setForeground(Color.BLACK);
        specialLabel.setText("✗ 記号を含む");
        specialLabel.setForeground(Color.BLACK);
        dictionaryLabel.setText("✗ 危険なパスワード");
        dictionaryLabel.setForeground(Color.BLACK);
        resultArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PasswordCheckerGUI gui = new PasswordCheckerGUI();
            gui.setVisible(true);
        });
    }
}

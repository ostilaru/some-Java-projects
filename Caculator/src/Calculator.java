import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Calculator implements ActionListener{

    JFrame frame;
    JTextField textField;
    JButton numberButtons[] = new JButton[10];// 0~9十个数字
    JButton functionButton[] = new JButton[9];
    JButton addButton, subButton, mulButton, divButton;// + - * /
    JButton decButton, equButton, delButton, clrButton, negButton;
    JPanel panel;

    Font myFont = new Font("Ink Free", Font.BOLD, 30);

    double num1 = 0, num2 = 0, result = 0;
    char operator;

    Calculator(){
        /*
        * First use JFrame to build a window frame
        * Second add the textField
        *       there are some limits for textFiled:
        *           the uesr cannot type words directly to the textField
        * */
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 550);
        frame.setLayout(null);

        textField = new JTextField();
        textField.setBounds(60, 30, 300, 50);
        textField.setFont(myFont);
        textField.setEditable(false);   //prohibit users to type words in textFields directly
        /* 1. create the functionButton */
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Del");
        clrButton = new JButton("Clear");
        negButton = new JButton("(-)");

        functionButton[0] = addButton;
        functionButton[1] = subButton;
        functionButton[2] = mulButton;
        functionButton[3] = divButton;
        functionButton[4] = decButton;
        functionButton[5] = equButton;
        functionButton[6] = delButton;
        functionButton[7] = clrButton;
        functionButton[8] = negButton;

        for(int i = 0; i < 9; i++){
            functionButton[i].addActionListener(this);
            functionButton[i].setFont(myFont);
            functionButton[i].setFocusable(false);
        }

        /* 2. create the number button */
        for(int i = 0; i < 10; i++){
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
        }

        /* 3. decide the location and size of all buttons */
        negButton.setBounds(60, 430, 100, 50);
        delButton.setBounds(160, 430, 100, 50);
        clrButton.setBounds(260, 430, 100, 50);


        panel = new JPanel();
        panel.setBounds(60, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        //panel.setBackground(Color.GRAY);

        /* 4. add all the buttons into the panel */
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);

        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);



        frame.add(panel);
        frame.add(negButton);
        frame.add(delButton);
        frame.add(clrButton);

        frame.add(textField);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
    }

    public void actionPerformed(ActionEvent e){

        for(int i = 0; i < 10; i++){
            if(e.getSource() == numberButtons[i]){
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }
        if(e.getSource() == decButton){
            textField.setText(textField.getText().concat("."));
        }
        if(e.getSource() == addButton){
            num1 = Double.parseDouble(textField.getText());
            operator = '+';
            textField.setText("");
        }
        if(e.getSource() == subButton){
            num1 = Double.parseDouble(textField.getText());
            operator = '-';
            textField.setText("");
        }
        if(e.getSource() == mulButton){
            num1 = Double.parseDouble(textField.getText());
            operator = '*';
            textField.setText("");
        }
        if(e.getSource() == divButton){
            num1 = Double.parseDouble(textField.getText());
            operator = '/';
            textField.setText("");
        }
        if(e.getSource() == equButton){
            num2 = Double.parseDouble(textField.getText());

            switch (operator){
                case'+':
                    result = num1 + num2;
                    break;
                case'-':
                    result = num1 - num2;
                    break;
                case'*':
                    result = num1 * num2;
                    break;
                case'/':
                    result = num1 / num2;
                    break;
            }
            textField.setText(String.valueOf(result));
            num1 = result;  //注意这里我们需要把上一次计算的结果作为下一次运算的num1
        }
        if(e.getSource() == clrButton){
            textField.setText("");
        }
        if(e.getSource() == delButton){
            String string = textField.getText();
            textField.setText("");
            for(int i = 0; i < string.length() - 1; i++){
                textField.setText(textField.getText() + string.charAt(i));
            }
        }
        if(e.getSource() == negButton){
            double temp = Double.parseDouble(textField.getText());
            temp *= -1;
            textField.setText(String.valueOf(temp));
        }
    }

}


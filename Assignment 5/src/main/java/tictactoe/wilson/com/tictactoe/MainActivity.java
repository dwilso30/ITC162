package tictactoe.wilson.com.tictactoe;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //define variables for widgets within the app
    private SharedPreferences savedValues;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private TextView displayText;
    private Button button10;
    private int turn;
    private boolean gameOver;
    private Button gameGrid[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // reference game buttons
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        displayText = (TextView) findViewById(R.id.displayText);

        //instance variables
        turn = 0;
        gameOver = false;

        //set listeners
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);

        //this a 2D array for the gameBoard
        gameGrid = new Button[][]{
                {button1,button2,button3},
                {button4,button5,button6},
                {button7,button8,button9}
        };


        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause(){
        SharedPreferences.Editor editor = savedValues.edit();

        editor.putString("displayText",displayText.getText().toString());
        editor.putInt("turn", turn);
        editor.putBoolean("gameOver",gameOver);

        editor.putString("1",button1.getText().toString());
        editor.putString("2",button2.getText().toString());
        editor.putString("3",button3.getText().toString());
        editor.putString("4",button4.getText().toString());
        editor.putString("5",button5.getText().toString());
        editor.putString("6",button6.getText().toString());
        editor.putString("7",button7.getText().toString());
        editor.putString("8",button8.getText().toString());
        editor.putString("9",button9.getText().toString());

        editor.commit();
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();

        //this will get the saved state of the app
        //and display it as it is
        displayText.setText(savedValues.getString("displayText",""));
        turn = savedValues.getInt("turn",0);
        gameOver = savedValues.getBoolean("gameOver",false);

        button1.setText(savedValues.getString("1",""));
        button2.setText(savedValues.getString("2",""));
        button3.setText(savedValues.getString("3",""));
        button4.setText(savedValues.getString("4",""));
        button5.setText(savedValues.getString("5",""));
        button6.setText(savedValues.getString("6",""));
        button7.setText(savedValues.getString("7",""));
        button8.setText(savedValues.getString("8",""));
        button9.setText(savedValues.getString("9",""));

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button10:
                startNewGame();
                break;
            default:
                if(!gameOver){

                    // check for X O or Draw
                    int gameState = 0;
                    Button b = (Button) v;
                    if (turn % 2 == 0 && b.getText().toString().equals("")){
                        b.setText("X");

                        gameState = checkGame();
                        if (gameState == 1 || gameState == 3)
                        {
                            break;
                        }
                        turn ++;
                        displayText.setText("Player O's Turn");
                    }
                    else if (turn % 2 == 1 && b.getText().toString().equals("")){
                        b.setText("O");
                        gameState = checkGame();
                        if (gameState == 2 || gameState == 3)
                        {
                            break;
                        }
                        turn ++;
                        displayText.setText("Player X's Turn");
                    }
                }

                break;
        }

    }

    //check for line wins
    private int checkGame(){


        boolean canPlay = false;

        // diagonals
        if ((
                (getSquareState(gameGrid[0][0]) == getSquareState(gameGrid[1][1]) && getSquareState(gameGrid[1][1]) == getSquareState(gameGrid[2][2]))
                        ||
                        (getSquareState(gameGrid[0][2]) == getSquareState(gameGrid[1][1]) && getSquareState(gameGrid[1][1]) == getSquareState(gameGrid[2][0]))
        ) && getSquareState(gameGrid[1][1]) != 0)
        {
            gameOver = true;
            if (getSquareState(gameGrid[1][1]) == 1){
                displayText.setText("Player X Wins!");
                return 1;
            }else {
                displayText.setText("Player O Wins!");
                return 2;
            }
        }

        //check rows and columns
        for(int i = 0; i < 3; i++){

            //check rows
            if (
                    getSquareState(gameGrid[i][0]) == getSquareState(gameGrid[i][1])
                            && getSquareState(gameGrid[i][1]) == getSquareState(gameGrid[i][2])
                            && getSquareState(gameGrid[i][0]) != 0)
            {
                gameOver = true;
                if (getSquareState(gameGrid[i][0]) == 1)
                {
                    displayText.setText("Player X Wins!");
                    return 1;
                }else {
                    displayText.setText("Player O Wins!");
                    return 2;
                }
            }

            //check columns
            if (
                    getSquareState(gameGrid[0][i]) == getSquareState(gameGrid[1][i])
                            && getSquareState(gameGrid[1][i]) == getSquareState(gameGrid[2][i])
                            && getSquareState(gameGrid[0][i]) != 0)
            {
                gameOver = true;
                if (getSquareState(gameGrid[0][i]) == 1){
                    displayText.setText("Player X Wins!");
                    return 1;
                }else {
                    displayText.setText("Player O Wins!");
                    return 2;
                }
            }


            for (int j = 0; j < 3; j++){
                if(getSquareState(gameGrid[i][j]) == 0){
                    canPlay = true;
                };
            }
        }
        if(canPlay){

            return 0;
        } else{
            //draw
            gameOver = true;
            displayText.setText("It's a Draw!");
            return 3;
        }

    }

    private int getSquareState(Button b){
        if (b.getText().toString().equals("X")){
            return 1;
        }
        else if (b.getText().toString().equals("O")){
            return 2;
        }
        else {
            return 0;
        }
    }

    private void startNewGame(){
        turn = 0;
        gameOver = false;
        //Player 1 = X
        displayText.setText("Player X's Turn");

        //start a new game
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                gameGrid[i][j].setText("");
            }
        }
    }

}



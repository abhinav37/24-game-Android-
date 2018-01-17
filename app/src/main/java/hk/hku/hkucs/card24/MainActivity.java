package hk.hku.hkucs.card24;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.singularsys.jep.EvaluationException;
import com.singularsys.jep.Jep;
import com.singularsys.jep.ParseException;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button rePick;
    private Button checkInput;
    private Button clear;
    private Button left;
    private Button right;
    private Button plus;
    private Button minus;
    private Button multiply;
    private Button divide;
    private TextView expression;
    private ImageButton[] cards;
    private int[] data = {-1, -1, -1, -1};
    private int[] card = {-1, -1, -1, -1};
    private int[] imageCount = {-1, -1, -1, -1};
    private int gameRule = 24;
    private String signs = "+-/*(";
    private boolean[] selected = {false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().hasExtra("number"))
            gameRule = getIntent().getIntExtra("number", 24);

        cards = new ImageButton[4];

        cards[0] = (ImageButton) findViewById(R.id.card1);
        cards[1] = (ImageButton) findViewById(R.id.card2);
        cards[2] = (ImageButton) findViewById(R.id.card3);
        cards[3] = (ImageButton) findViewById(R.id.card4);

        rePick = (Button) findViewById(R.id.repick);
        checkInput = (Button) findViewById(R.id.checkinput);
        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        multiply = (Button) findViewById(R.id.multiply);
        divide = (Button) findViewById(R.id.divide);
        clear = (Button) findViewById(R.id.clear);
        expression = (TextView) findViewById(R.id.input);

        expression.setHint("Please form an expression such that the result is " + gameRule);
        initCardImage();
        setListeners();
        pickCard();
    }

    private boolean checkInput(String input) {
        if (!checkAllCardsUsed()) {
            Toast.makeText(MainActivity.this,
                    "Please Use all Cards to complete Expression", Toast.LENGTH_SHORT).show();
            return false;
        }
        Jep jep = new Jep();
        Object res;
        try {
            jep.parse(input);
            res = jep.evaluate();
        } catch (ParseException | EvaluationException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,
                    "Wrong Expression", Toast.LENGTH_SHORT).show();
            return false;
        }
        Double ca = (Double) res;
        if (Math.abs(ca - gameRule) < 1e-6)
            return true;
        return false;
    }

    private boolean checkAllCardsUsed() {
        for (int i = 0; i < 4; i++) {
            if (!selected[i])
                return false;
        }
        return true;
    }

    private void setListeners() {
        cards[0].setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                clickCard(0);
            }
        });
        cards[1].setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                clickCard(1);
            }
        });
        cards[2].setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                clickCard(2);
            }
        });
        cards[3].setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                clickCard(3);
            }
        });

        left.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                String txt = expression.getText().toString();
                if (txt.length() != 0) {
                    if (signs.indexOf(txt.charAt(txt.length() - 1)) != -1) {
                        expression.append("(");
                    } else {
                        Toast.makeText(MainActivity.this, "Cannot Add Bracket ')'",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    expression.append("(");
                }

            }
        });

        right.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                String txt = expression.getText().toString();
                if (txt.length() != 0) {
                    if (signs.indexOf(txt.charAt(txt.length() - 1)) == -1) {
                        expression.append(")");
                    } else {
                        Toast.makeText(MainActivity.this, "Cannot Add Bracket ')'",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Cannot Add Bracket ')'",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        plus.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                String txt = expression.getText().toString();
                if (txt.length() != 0) {
                    if (signs.indexOf(txt.charAt(txt.length() - 1)) == -1) {
                        expression.append("+");
                    } else {
                        Toast.makeText(MainActivity.this, "Cannot Add sign '+'",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Cannot Add sign '+'",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        minus.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                String txt = expression.getText().toString();
                if (txt.length() != 0) {
                    if (signs.indexOf(txt.charAt(txt.length() - 1)) == -1) {
                        expression.append("-");
                    } else {
                        Toast.makeText(MainActivity.this, "Cannot Add sign '-'",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Cannot Add sign '-'",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        multiply.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        String txt = expression.getText().toString();
                        if (txt.length() != 0) {
                            if (signs.indexOf(txt.charAt(txt.length() - 1)) == -1) {
                                expression.append("*");
                            } else {
                                Toast.makeText(MainActivity.this, "Cannot Add sign '*'",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Cannot Add sign '*'",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        divide.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {

                String txt = expression.getText().toString();
                if (txt.length() != 0) {
                    if (signs.indexOf(txt.charAt(txt.length() - 1)) == -1) {
                        expression.append("/");
                    } else {
                        Toast.makeText(MainActivity.this, "Cannot Add sign '/'",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Cannot Add sign '/'",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        clear.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                setClear();
            }
        });
        rePick.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                pickCard();
            }
        });
        checkInput.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                evaluateExpression();
            }


        });
    }

    private void evaluateExpression() {
        String inputStr = expression.getText().toString();
        if (checkInput(inputStr)) {
            Toast.makeText(MainActivity.this, "Correct Answer",
                    Toast.LENGTH_SHORT).show();
            pickCard();
        } else {
            Toast.makeText(MainActivity.this, "Wrong Answer",
                    Toast.LENGTH_SHORT).show();
            setClear();
        }
    }

    private void initCardImage() {
        for (int i = 0; i < 4; i++) {
            int resID = getResources().getIdentifier("back_0", "drawable", "hk.hku.hkucs.card24");
            cards[i].setImageResource(resID);
        }
    }

    private void pickCard() {
        int i = 0;
        do {
            selected[i] = false;
            card[i] = (int) (1 + ((Math.random()) * 100) % 52);
            data[i] = card[i] % 13;
            i++;
        } while (i < 4);

        setClear();
    }

    private void setClear() {
        int resID;
        expression.setText("");
        for (int i = 0; i < 4; i++) {
            imageCount[i] = 0;
            resID = getResources().getIdentifier
                    ("card" + card[i], "drawable", "hk.hku.hkucs.card24");
            cards[i].setImageResource(resID);
            cards[i].setClickable(true);
        }
    }

    private void clickCard(int i) {
        selected[i] = true;
        int resId;
        String num;
        Integer value;
        if (imageCount[i] == 0) {
            resId = getResources().getIdentifier("back_0", "drawable", "hk.hku.hkucs.card24");
            cards[i].setImageResource(resId);
            cards[i].setClickable(false);
            value = data[i];
            num = value.toString();
            expression.append(num);
            imageCount[i]++;
        }
    }
}

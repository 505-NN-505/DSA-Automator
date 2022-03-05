package out.production.dsa_automator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {
    @FXML
    protected TextField input;

    @FXML
    private Label output;

    @FXML
    protected Button buttonPrime;

    @FXML
    protected Button buttonEtf;

    @FXML
    protected Button buttonNod;

    @FXML
    protected Button buttonSod;

    ArrayList<Integer> Primes = new ArrayList<Integer>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SievePrime(100000);
        buttonPrime.setOnAction(eventPrime -> getNthPrime());
    }

    int isNonPrime(int prime[], int x) {
        return (prime[x / 64] & (1 << ((x >> 1) & 31)));
    }

    void makeComposite(int prime[], int x) {
        prime[x / 64] |= (1 << ((x >> 1) & 31));
    }

    void SievePrime(int n) {
        int prime[] = new int[n / 64 + 1];
        for (int i = 3; i * i <= n; i += 2) {
            if (isNonPrime(prime, i) == 0)
                for (int j = i * i, k = i << 1; j < n; j += k)
                    makeComposite(prime, j);
        }

        Primes.add(2);
        for (int i = 3; i <= n; i += 2)
            if (isNonPrime(prime, i) == 0)
                Primes.add(i);
    }

    void getNthPrime() {
        String[] str1 = input.getText().trim().split("\\s+");
        Integer n = Integer.parseInt(str1[0]);
        Integer nthPrime = Primes.get(n - 1);
        output.setText(nthPrime.toString());
    }
}

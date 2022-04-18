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

    @FXML
    protected Button buttonMobius;

    @FXML
    protected Button buttonInvMod;

    @FXML
    protected Button buttonBigMod;

    @FXML
    protected Button buttongcd;

    @FXML
    protected Button buttonlcm;

    @FXML
    protected Button buttonnCr;

    @FXML
    protected Button buttondrng;

    @FXML
    protected Button buttonCatalan;

    @FXML
    protected Button buttondlog;

    @FXML
    protected Button buttonproot;

    @FXML
    protected Button buttonXor;

    ArrayList<Integer> Primes = new ArrayList<Integer>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SievePrime(100000);
        buttonPrime.setOnAction(eventPrime -> getNthPrime());
        buttonNod.setOnAction(eventNod -> getNod());
        buttonSod.setOnAction(eventSod -> getSod());
        buttonEtf.setOnAction(eventEtf -> getPhi());
        buttonMobius.setOnAction(eventMobius -> getMobius());
        buttonInvMod.setOnAction(eventInvMod -> getModInverse());
        buttonBigMod.setOnAction(eventBMod -> getBigMod());
        buttongcd.setOnAction(eventGcd -> getGcd());
        buttonlcm.setOnAction(eventLcm -> getLcm());
        buttonnCr.setOnAction(eventnCr -> getncr());
        buttondrng.setOnAction(eventdrng -> getDrng());
        buttonCatalan.setOnAction(eventCatalan -> getCatalan());
        buttondlog.setOnAction(eventdlog -> getdlog());
        buttonproot.setOnAction(eventproot -> getPrimitiveRoots());
        buttonXor.setOnAction(eventproot -> getXor());
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

    Integer countDivisors(long n) {
        Integer cnt = 0;
        for (long i = 1; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                if (n / i == i)
                    cnt++;
                else
                    cnt = cnt + 2;
            }
        }
        return cnt;
    }

    void getNod() {
        String[] str1 = input.getText().trim().split("\\s+");
        long n = Integer.parseInt(str1[0]);
        Integer nod = countDivisors(n);
        output.setText(nod.toString());
    }

    Long sumDivisors(Long num)  {
        Long result = 0L;
        for (Long i = 1L; i <= Math.sqrt(num); i++)  {
            if (num % i == 0) {
                if (i == (num / i))
                    result += i;
                else
                    result += (i + num / i);
                System.out.println(i + " " + num / i);
            }
        }
        return result;
    }

    void getSod() {
        String[] str1 = input.getText().trim().split("\\s+");
        Long n = Long.parseLong(str1[0]);
        Long sod = sumDivisors(n);
        output.setText(sod.toString());
    }

    Long phi(Long n) {
        Long result = n;
        for (Long p = 2l; p * p <= n; ++p)
        {
            if (n % p == 0) {
                while (n % p == 0) n /= p;
                result -= result / p;
            }
        }

        if (n > 1) result -= result / n;
        return result;
    }

    void getPhi() {
        String[] str1 = input.getText().trim().split("\\s+");
        Long n = Long.parseLong(str1[0]);
        Long p = phi(n);
        output.setText(p.toString());
    }

    Integer mobius(Long n) {
        int p = 0;
        if (n % 2 == 0) {
            n = n / 2;
            p++;
            if (n % 2 == 0)
                return 0;
        }

        for (int i = 3; i <= Math.sqrt(n); i = i + 2) {
            if (n % i == 0) {
                n = n / i;
                p++;
                if (n % i == 0)
                    return 0;
            }
        }

        return (p % 2 == 0)? -1 : 1;
    }
    void getMobius() {
        String[] str1 = input.getText().trim().split("\\s+");
        Long n = Long.parseLong(str1[0]);
        Integer p = mobius(n);
        output.setText(p.toString());
    }

    Long modInverse(Long a, Long m) {
        Long m0 = m;
        Long y = 0l, x = 1l;

        if (m == 1)
            return 0l;

        while (a > 1) {
            Long q = a / m;
            Long t = m;

            m = a % m;
            a = t;
            t = y;

            y = x - q * y;
            x = t;
        }

        if (x < 0)
            x += m0;

        return x;
    }

    void getModInverse() {
        String[] str1 = input.getText().trim().split("\\s+");
        Long a = Long.parseLong(str1[0]), m = Long.parseLong(str1[1]);
        Long p = modInverse(a, m);
        output.setText(p.toString());
    }

    Long aModM(String s, Long mod)
    {
        Long number = 0l;
        for (Integer i = 0; i < s.length(); i++) {
            number = (number*10l + (Character.getNumericValue(s.charAt(i))));
            number %= mod;
        }
        return number;
    }

    Long ApowBmodM(String a, String b,Long m)
    {
        Long res=1l;
        Long x = aModM(a,m);
        Long y = aModM(b,m);
        while(y>0) {
            if(y%2 == 1)
                res=(res*x)%m;
            y=y>>1;
            x=((x%m)*(x%m))%m;
        }
        return (res%m+m)%m;
    }

    void getBigMod() {
        String[] str1 = input.getText().trim().split("\\s+");
        String a = str1[0], b = str1[1];
        long m = Long.parseLong(str1[2]);
        Long p = ApowBmodM(a, b, m);
        output.setText(p.toString());
    }

    Long gcd(Long a, Long b)
    {
        if (a == 0l)
            return b;
        return gcd(b % a, a);
    }

    Long findGCD(ArrayList<Long> arr)
    {
        Long result = arr.get(0);
        for (Long element: arr){
            result = gcd(result, element);
            if(result == 1) {
                return 1l;
            }
        }
        return result;
    }

    void getGcd() {
        String[] str1 = input.getText().trim().split("\\s+");
        ArrayList<Long> arr = new ArrayList<>();
        for (int i = 0; i < str1.length; i++) {
            arr.add(Long.parseLong(str1[i]));
        }
        Long p = findGCD(arr);
        output.setText(p.toString());
    }

    Long lcmArray(ArrayList<Long> arr)
    {
        Long lcm_of_array_elements = 1l;
        Long divisor = 2l;

        while (true) {
            Long counter = 0l;
            boolean divisible = false;

            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i) == 0l) {
                    return 0l;
                }
                else if (arr.get(i)  < 0l) {
                    arr.set(i, arr.get(i)  * (-1l));
                }
                if (arr.get(i)  == 1) {
                    counter++;
                }
                if (arr.get(i)  % divisor == 0) {
                    divisible = true;
                    arr.set(i, arr.get(i)  / divisor);
                }
            }
            if (divisible) {
                lcm_of_array_elements = lcm_of_array_elements * divisor;
            }
            else {
                divisor++;
            }
            if (counter == arr.size()) {
                return lcm_of_array_elements;
            }
        }
    }
    void getLcm() {
        String[] str1 = input.getText().trim().split("\\s+");
        ArrayList<Long> arr = new ArrayList<>();
        for (int i = 0; i < str1.length; i++) {
            arr.add(Long.parseLong(str1[i]));
        }
        Long p = lcmArray(arr);
        output.setText(p.toString());
    }

    Long nCr(Long n, Long r) {
        Long c = 1l;
        for(Long i = 1l; i <= r; i++) c = ( c * (n - i +1l) / i);
        return c;
    }
    void getncr() {
        String[] str1 = input.getText().trim().split("\\s+");
        long a = Long.parseLong(str1[0]), b = Long.parseLong(str1[1]);
        Long p = nCr(a, b);
        output.setText(p.toString());
    }

    int discreteLogarithm(int a, int b, int m)
    {
        int n = (int) (Math.sqrt (m) + 1);

        int an = 1;
        for (int i = 0; i < n; ++i)
            an = (an * a) % m;

        int[] value=new int[m];

        for (int i = 1, cur = an; i <= n; ++i)
        {
            if (value[ cur ] == 0)
                value[ cur ] = i;
            cur = (cur * an) % m;
        }

        for (int i = 0, cur = b; i <= n; ++i)
        {
            if (value[cur] > 0)
            {
                int ans = value[cur] * n - i;
                if (ans < m)
                    return ans;
            }
            cur = (cur * a) % m;
        }
        return -1;
    }
    void getdlog() {
        String[] str1 = input.getText().trim().split("\\s+");
        int a = Integer.parseInt(str1[0]), b = Integer.parseInt(str1[1]), m = Integer.parseInt(str1[2]);
        Integer p = discreteLogarithm(a, b, m);
        output.setText(p.toString());
    }

    Long countDrng(Long n) {
        if(n == 1 || n == 2) {
            return n-1;
        }

        Long a = 0l;
        Long b = 1l;

        for (int i = 3; i <= n; ++i) {
            Long cur = (i-1)*(a+b);
            a = b;
            b = cur;
        }
        return b;
    }

    void getDrng() {
        String[] str1 = input.getText().trim().split("\\s+");
        Long a = Long.parseLong(str1[0]);
        Long p = countDrng(a);
        output.setText(p.toString());
    }

    Long catalan(int n) {
        Long cat = 1l;
        if (n == 1) return cat;
        for (int i = 1; i < n; i++) {
            cat *= (4 * i - 2);
            cat /= (i + 1);
            if ((i + 1) == n) return cat;
        }
        return cat;
    }

    void getCatalan() {
        String[] str1 = input.getText().trim().split("\\s+");
        int n = Integer.parseInt(str1[0]);
        Long p = catalan(n);
        output.setText(p.toString());
    }

    Long countPrimitiveRoots(Long p)
    {
        Long result = 1l;
        for (Long i = 2l; i < p; i++)
            if (gcd(i, p) == 1)
                result++;
        return result;
    }

    void getPrimitiveRoots() {
        String[] str1 = input.getText().trim().split("\\s+");
        Long n = Long.parseLong(str1[0]);
        Long p = countPrimitiveRoots(n);
        output.setText(p.toString());
    }

    Long rXOR(Long n) {
        Long mod = n % 4l;
        if (mod == 0l)
            return n;
        else if (mod == 1l)
            return 1l;
        else if (mod == 2l)
            return n + 1;
        else if (mod == 3l)
            return 0l;
        return 0l;
    }
    Long findXOR(Long l, Long r) {
        return (rXOR(l - 1) ^ rXOR(r));
    }

    void getXor() {
        String[] str1 = input.getText().trim().split("\\s+");
        Long n = Long.parseLong(str1[0]), m = Long.parseLong(str1[1]);
        Long p = findXOR(n, m);
        output.setText(p.toString());
    }
}

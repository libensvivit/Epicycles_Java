import java.util.ArrayList;

public class Discrete_Fourier_Transform {
    static ArrayList<Complex> dft(ArrayList<Complex> x){
        int N = x.size();
        ArrayList<Complex> X = new ArrayList<>(N);

        for (int k = 0; k < N; k++){
            Complex sum = new Complex(0, 0);

            for (int n = 0; n < N; n++){
                float phi = (float) ((2*Math.PI * k * n) / N);
                Complex c = new Complex((float)Math.cos(phi), (float)-Math.sin(phi));
                sum = sum.add(x.get(n).mult(c));
            }
            sum = sum.div(N);

            float amp = sum.mag();
            float phase = sum.heading();
            X.add(new Complex(sum.re, sum.im, k, amp, phase));
        }
        return X;
    }
}

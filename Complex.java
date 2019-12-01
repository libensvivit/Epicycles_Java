import java.util.ArrayList;

//This is the class for complex numbers, it has many function that aren't used in this program but they this class could be copied and used in another program
class Complex
{
    float re;
    float im;
    float freq;
    float amp;
    float phase;

    Complex(float r, float i)
    {
        re = r;
        im = i;
    }

    Complex(float r, float i, float f, float a, float p)
    {
        re = r;
        im = i;
        freq = f;
        amp = a;
        phase = p;
    }

    Complex mult(Complex other)
    {
        float rea = re * other.re - im * other.im;
        float ima = re * other.im + im * other.re;
        return new Complex(rea, ima);
    }

    Complex add(Complex other)
    {
        return new Complex(re + other.re, im + other.im);
    }

    Complex sub(Complex other)
    {
        return new Complex(re - other.re, im - other.im);
    }

    Complex pow(int n)
    {
        Complex result = this;
        for (int i = 0; i < n; i++)
        {
            result = result.mult(this);
        }
        return result;
    }

    Complex mult(float mult)
    {
        re *= Math.sqrt(mult);
        im *= Math.sqrt(mult);
        return new Complex(re, im);
    }

    Complex div(float divisor)
    {
        float r = re / divisor;
        float i = im / divisor;
        return new Complex(r, i);
    }

    void normalize()
    {
        re /= mag();
        im /= mag();
    }

    float heading()
    {
        return (float) Math.atan2(im, re);
    }


    float mag()
    {
        return (float) Math.sqrt(re * re + im * im);
    }

    void rotate(float theta)
    {
        re += Math.cos(theta);
        im += Math.sin(theta);
    }

    static void SortComplex(ArrayList<Complex> c){
        int n = c.size();
        for (int i = 0; i < n-1; i++)
        {
            int mindex = i;

            for (int j = i+1; j < n; j++)
            {
                if (c.get(j).amp > c.get(mindex).amp)
                    mindex = j;
            }
            Complex temp = c.get(mindex);
            c.set(mindex, c.get(i));
            c.set(i, temp);
        }
    }
}

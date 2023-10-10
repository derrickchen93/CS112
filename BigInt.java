/* 
 * BigInt.java
 *
 * A class for objects that represent non-negative integers of 
 * up to 20 digits.
 */

import java.util.Arrays; 
public class BigInt  {
    // the maximum number of digits in a BigInt -- and thus the length
    // of the digits array
    private static final int MAX_SIZE = 20;
    
    // the array of digits for this BigInt object
    private int[] digits;
    
    // the number of significant digits in this BigInt object
    private int numSigDigits;


    // Constructors

    // Default no-argument constructor -- creates a BigInt that represents the number 0
    public BigInt() {
        this.digits = new int[MAX_SIZE];
        this.numSigDigits = 1;
    }

    public BigInt(int[] arr) {
        if (arr.length > MAX_SIZE || arr.length < 0 || arr == null) 
            throw new IllegalArgumentException();

        this.digits = new int[MAX_SIZE];
        
        for (int i = 0; i < arr.length; i++) {
            if (arr[arr.length - 1 - i] >= 0 && arr[arr.length - 1 - i] < 10) {
                this.digits[MAX_SIZE-1-i] = arr[arr.length-1-i];
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        CalculateSigDigits(this.digits);
    }


    public BigInt(int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        
        this.digits = new int[MAX_SIZE];

        for (int i = 0; i < MAX_SIZE; i++) {
            this.digits[MAX_SIZE - (i + 1)] = n % 10;
            n /= 10;
        }

        CalculateSigDigits(this.digits);
    }

    public void CalculateSigDigits(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                this.numSigDigits = arr.length - i;
                break;
            }
        }
    }



    // Accessors \\
    public int getNumSigDigits() {
        return this.numSigDigits;
    }

    public int[] getDigits() {
        return this.digits;
    }

    public String toString() {
        String s = "";

        if (this.numSigDigits != 1) {
            for (int i = MAX_SIZE - this.numSigDigits; i < this.digits.length; i ++) {
                s += this.digits[i];
            }
        }

        else 
            s += this.digits[MAX_SIZE - 1];

        return s;
    }

    // Methods

    public int compareTo(BigInt other) {
        if (this.digits == null || other.getDigits() == null)
            throw new IllegalArgumentException();
        
        if (this.numSigDigits > other.getNumSigDigits())
            return 1;
        else {
            if (this.numSigDigits < other.getNumSigDigits())
                return -1;
            else {
                boolean equal = true;
                boolean larger = true;
                for (int i = MAX_SIZE - this.numSigDigits; i < MAX_SIZE; i++) {
                    if (this.digits[i] != other.getDigits()[i])
                        equal = false;
                    if (this.digits[i] < other.getDigits()[i])
                        larger = false;
                }
                if (equal) 
                    return 0;
                    else {
                    if (larger)
                        return 1;
                    else
                        return -1;
                }
            }
        }
    }

    public BigInt add(BigInt other) {
        if (this.digits == null || other.getDigits() == null) 
            throw new IllegalArgumentException();

        int[] TempArr = new int[MAX_SIZE];
        boolean CarryOne = false;


        for (int i = 0; i < MAX_SIZE; i++) {
            int Temp = 0;
            if (CarryOne) {
                Temp = this.digits[MAX_SIZE-i-1] + other.getDigits()[MAX_SIZE-i-1] + 1;
                TempArr[MAX_SIZE-i-1] = (Temp % 10);
        }
            else {
                Temp = this.digits[MAX_SIZE-i-1] + other.getDigits()[MAX_SIZE-i-1];
                TempArr[MAX_SIZE-i-1] = (Temp % 10);
            }

            if (Temp > 9)
                CarryOne = true;
            else
                CarryOne = false;
        }
        if (CarryOne)
            throw new ArithmeticException();
        else;
            return new BigInt(TempArr);
    }


    public BigInt mul(BigInt other) {
        if (this.digits == null || other.getDigits() == null)
            throw new IllegalArgumentException();

            if (this.CheckValue() == 0 || other.CheckValue() == 0)
                return new BigInt(0);
            else if (this.CheckValue() == 1 || other.CheckValue() == 1) {
                if (this.CheckValue() == 1)
                    return new BigInt(other.getDigits());
                else
                    return new BigInt(this.digits);
            }
        

        if (this.numSigDigits > other.getNumSigDigits()) {
            BigInt store = new BigInt(0);
            int counterX = 1;
            for (int i = MAX_SIZE - 1; i >= MAX_SIZE - other.getNumSigDigits();i--) {
                for (int j = 0; j < other.getDigits()[i] * counterX;j++) {
                    store = store.add(this);
                }
                counterX *= 10;
            }
            return store;
        }

        else {
            BigInt store = new BigInt(0);
            int counterX = 1;
            for (int i = MAX_SIZE - 1; i >= MAX_SIZE - this.numSigDigits;i--) {
                for (int j = 0; j < this.digits[i] * counterX;j++) {
                    store = store.add(other);
                }
                counterX *= 10;
            }
            return store;
        }
    }
            

    public int CheckValue() {
        if (this.toString().equals("0"))
            return 0;
        else if (this.toString().equals("1")) {
            return 1;
        }
        else
            return 2;
    }


















    public static void main(String[] args) {   
    }
}
    
    
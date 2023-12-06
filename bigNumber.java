import java.util.*;

public class BigNumber {
    int numberOfDigits;
    byte Digits[];
    //constructor 
    public BigNumber(int size){
        numberOfDigits=size;
         Digits = new byte[size]; 
        for (int i = 0; i < size; i++) {
            Digits[i] = 0;            
        }
    }
    //String sconstructor 
    public BigNumber(String s){
        //converting string into int
        numberOfDigits = s.length(); 

        int[] intArray = new int[numberOfDigits];
        for (int i = 0; i < numberOfDigits; i++) {
            intArray[i] = Character.getNumericValue(s.charAt(i));
        }
        //converting int into byte
        Digits = new byte[numberOfDigits];
        for (int i = 0; i < numberOfDigits; i++) {
            Digits[i] = (byte) intArray[i];
        }
    }
    //long constructor 
    public BigNumber(long n){
        long temp=n;
        numberOfDigits=1;
        while(temp>=10){
               temp/=10;
               numberOfDigits++;
           }
          Digits = new byte[numberOfDigits];
        for (int i = numberOfDigits-1; i >=0; i--) {
            Digits[i] = (byte) (n%10);
            n/=10;
        }
    }
    //a method to make numberOfDigits two arrays even
    public void extendZero(int m){
         byte[] newDigits = new byte[Digits.length + m];
        System.arraycopy(Digits, 0, newDigits, m, Digits.length);
        Digits = newDigits;
    } 
    
public BigNumber removeLeadingZeros() {
        int nonZeroIndex = -1;  // Initialize to -1 to detect if no non-zero elements are found

        // Find the index of the first non-zero element
        for (int i = 0; i < numberOfDigits; i++) {
            if (Digits[i] != 0) {
                nonZeroIndex = i;
                break;
            }
        }

        // If there are no non-zero elements, set nonZeroIndex to the last index
        if (nonZeroIndex == -1) {
            nonZeroIndex = numberOfDigits - 1;
        }

        // Return the array starting from the first non-zero element
        Digits = Arrays.copyOfRange(Digits, nonZeroIndex, numberOfDigits);

        // Update the number of digits
        numberOfDigits = Digits.length;

        // Return the current BigNumber object
        return this;
    }
   
    public BigNumber increment(){
        int i = numberOfDigits - 1;
        int carry = 1;

        while (i >= 0 && carry > 0) {
            int sum = Digits[i] + carry;
            Digits[i] = (byte) (sum % 10);
            carry = sum / 10;
            i--;
        }
        // If there is a carry after the loop, it means a new digit needs to be added.
        if (carry > 0) {
            byte[] newDigits = new byte[numberOfDigits + 1];
            newDigits[0] = (byte) carry;
            System.arraycopy(Digits, 0, newDigits, 1, numberOfDigits);
            Digits = newDigits;
            numberOfDigits++;
        }
        return this;        
    }
   public BigNumber decrement(){
       int i = 1;
        while(Digits[numberOfDigits - i] == 0){
            Digits[numberOfDigits - i] = 9;
            i++;
        }
            Digits[numberOfDigits - i] --;            

        System.out.println(java.util.Arrays.toString(Digits));
        return null;        
    }
  public BigNumber ShiftR(){
      for (int i = numberOfDigits - 1; i > 0; i--) {
            Digits[i] = Digits[i - 1];
        }
        Digits[0] = 0;
       System.out.println(java.util.Arrays.toString(Digits));
        return null;         
    }
  public BigNumber ShiftL() {
    byte[] newDigits = new byte[numberOfDigits + 1];
    for (int i = 0; i < numberOfDigits; i++) {
        newDigits[i] = Digits[i];
    }
    newDigits[numberOfDigits] = 0;
    Digits = newDigits;
    numberOfDigits++;
    System.out.println(java.util.Arrays.toString(Digits));
    return this;  
}
  
    public BigNumber ShiftR(int n){
        for (int i = 0; i < n; i++) {
            for (int j = numberOfDigits - 1; j > 0 ; j--) {
            Digits[j] = Digits[j - 1];
            }
            Digits[i] = 0;
        }   
        System.out.println(java.util.Arrays.toString(Digits));
        return null; 
    }
    
    public BigNumber ShiftL(int n){
    byte[] newDigits = new byte[numberOfDigits + 1];
    for (int i = 0; i < numberOfDigits; i++) {
        newDigits[i] = Digits[i];
    }
    newDigits[numberOfDigits] = 0;
    Digits = newDigits;
    numberOfDigits++;
    return this;   
    }
    
    public BigNumber sum(BigNumber a){        
        int n1 = numberOfDigits;
        int n2 = a.numberOfDigits;
        int diff = Math.abs(n1 - n2);
        int maxSize = Math.max(n1,n2);
        BigNumber result = new BigNumber(maxSize+1 ); 
        if (n1 > n2) 
            a.extendZero(diff);
        else if(n2>n1)
        this.extendZero(diff);
        int k=0 , carry=0;
        int j=maxSize;
        while(j>0){
            k++;
            int digitSum = Digits[maxSize - k] + a.Digits[maxSize - k] + carry;
            result.Digits[maxSize - k+1] = (byte) (digitSum%10);
            carry = digitSum/10;
            j--;
        }
        if(carry>0){
            result.Digits[maxSize-k] = (byte) carry;
            k++;
        } 
        return result;
    }
public BigNumber minus(BigNumber a) {
    int n1 = numberOfDigits;
    int n2 = a.numberOfDigits;
    int diff = Math.abs(n1 - n2);
    int maxSize = Math.max(n1, n2);
    BigNumber result = new BigNumber(maxSize);

    // Adjust the size of numbers if needed
    if (n1 > n2)
        a.extendZero(diff);
    else if (n2 > n1)
        this.extendZero(diff);

    int k = 0, borrow = 0;
    int j = maxSize;
    boolean foundNonZero = false;

    while (j > 0) {
        k++;
        int digitMinus = Digits[maxSize - k] - a.Digits[maxSize - k] - borrow;

        if (digitMinus < 0) {
            Digits[maxSize - k] += 10;
            digitMinus = Digits[maxSize - k] - a.Digits[maxSize - k] - borrow;
            Digits[maxSize - k - 1]--;
        }

        result.Digits[maxSize - k] = (byte) digitMinus;

        // Check if the current digit is non-zero
        if (digitMinus != 0) {
            foundNonZero = true;
        }

        borrow = (Digits[maxSize - k] < a.Digits[maxSize - k]) ? 1 : 0; // Update borrow

        j--;
    }

    // Find the first non-zero digit in the result
    int firstNonZero = 0;
    while (firstNonZero < maxSize && result.Digits[firstNonZero] == 0) {
        firstNonZero++;
    }

    // Create a new BigNumber with the correct size
    int newSize = maxSize - firstNonZero;
    byte[] newDigits = new byte[newSize];
    System.arraycopy(result.Digits, firstNonZero, newDigits, 0, newSize);
    result.Digits = newDigits;
    result.numberOfDigits = newSize;
    return result;
}

    public BigNumber multByOneDigit(int d){
        BigNumber res = new BigNumber(numberOfDigits+1);
        int i = 0,carry = 0;
        int j=numberOfDigits;
        while(j>0){
            i++;
            int m = Digits[numberOfDigits - i] * d + carry;
            res.Digits[numberOfDigits - i+1] = (byte) (m % 10);
            carry = m/10;
            j--;
          }

        if(carry > 0){
            res.Digits[numberOfDigits - i] = (byte) carry;
            i++;      
        }   
        return res;
    }
    
    public BigNumber multiply(BigNumber b){
        int n1 = Digits.length;
        int n2 = b.numberOfDigits;
        BigNumber temp = new BigNumber(n1+n2);
        BigNumber result = new BigNumber(1);
        for (int i = 1; i <= n2; i++) {
                temp=this.multByOneDigit(b.Digits[n2 - i]);
                result=result.sum(temp);
                this.ShiftL();
        }
        return result;
    }
    
     public BigNumber print(){
    boolean firstNonZero = false;
    // Find the index of the first non-zero digit
    for (int j = 0; j < numberOfDigits; j++) {
        if (Digits[j] == 0 && !firstNonZero) {
            continue;
        } else {
            firstNonZero = true;
            System.out.print(Digits[j]);
        }
    }
    return this; 
}
     
     
    public boolean compare(BigNumber other) {
     if (this.numberOfDigits < other.numberOfDigits) {
        return false; 
    } else if (this.numberOfDigits >= other.numberOfDigits) {
        return true; 
    }
    for (int i = 0; i < this.numberOfDigits; i++) {
        if (this.Digits[i] < other.Digits[i]) {
            return false; 
        } else if (this.Digits[i] > other.Digits[i]) {
            return true; 
        }
    }
    return true;
}
    public boolean equals(BigNumber other) {
    int m = Math.max(numberOfDigits, other.numberOfDigits);
    if (this.numberOfDigits != other.numberOfDigits) {
        return false;
    }

    for (int i = 1; i <= m; i++) {
        if (this.Digits[numberOfDigits - i] != other.Digits[other.numberOfDigits - i]) {
            return false;
        }
    }

    return true;
}
//TODO
public BigNumber div(BigNumber divisor) {
    BigNumber quotient = new BigNumber(this.numberOfDigits);
    BigNumber remainder = new BigNumber(this.numberOfDigits);

    while (this.compare(divisor)) {
        remainder = this.minus(divisor);
        remainder = remainder.removeLeadingZeros();
        remainder = this;
        //remainder.print(); // Uncomment if you want to print the remainder
        quotient.increment();
    }

    return quotient;
}
//TODO
   public BigNumber pow(BigNumber exponent) {
    BigNumber result = new BigNumber("1");
    BigNumber zero = new BigNumber("0");
    while (exponent.compare(zero)) {
        result = this.multiply(this).print();
        exponent = exponent.decrement();
    }

    return result;
}
   
    public static void main(String[] args) {

    }
}

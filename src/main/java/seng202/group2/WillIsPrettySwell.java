package seng202.group2;

/**
 * Created by wmu16 on 28/07/16.
 */
public class WillIsPrettySwell {

    String swellyness;
    String haircolour;
    String eyecolour;

    WillIsPrettySwell(){};

    WillIsPrettySwell(String mYSwellyness, String myHaircolour, String myEyecolour){
        swellyness = mYSwellyness;
        haircolour = myHaircolour;
        eyecolour = myEyecolour;
    }

    public void createWill(){

        System.out.println("Will has " + haircolour + " coloured hair with " + eyecolour + " eyes and he is " + swellyness + " swell!!!");
    }

    public void eminateSwellyness(){
        System.out.println("Will is a swell guy!");
    }

public static void main(String[] args) {

    System.out.println("Will is pretty bad at java turns out");
    WillIsPrettySwell myWill = new WillIsPrettySwell("8", "Ginge", "Brown");
    myWill.eminateSwellyness();
    myWill.createWill();
    }

}


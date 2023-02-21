package cpen221.mp1.cryptanalysis;

import cpen221.mp1.Document;

import java.util.ArrayList;
import java.util.List;

public abstract class Cryptography {

    /**
     * Encrypt a document by replacing the i-th character, c_i, with
     * c_i + A \times \sin{(i \times 2\pi / P + \pi/4)}
     * where A is the amplitude and P is the period for the sine wave.
     * When encrypting text with multiple sentences, exactly one space
     * is used to separate sentences.
     *
     * @param doc the document to encrypt
     * @param length the number of characters to encrypt.
     *               If {@code length} is less than the number of
     *               characters in the document then only that many
     *               characters are encrypted.
     *               If {@code length} is greater than the number
     *               of characters in the document then additional
     *               ' ' are added at the end and encrypted.
     * @param period is the period of the sine wave used for encryption
     *               and {@code period} must be a factor of
     *               {@code length} other than 1 and {@code length} itself.
     * @param amplitude is the amplitude of the encrypting sine wave
     *                  and can be 64, 128, 256 or 512.
     * @return the encrypted array, with exactly one encrypted space
     *                  separating sentences.
     */
    public static int[] encrypt(Document doc, int length, int period, int amplitude) {
        List<Integer> list=new ArrayList<>();
        int i=0;
        char c_i=0;
        String initialdoc="";

        for (int count1=0;count1<doc.numSentences();count1++) {
            initialdoc=initialdoc+doc.getSentence(count1+1)+" ";
        }

        for (int count1=initialdoc.length();count1<length;count1++){
            initialdoc=initialdoc+" ";
        }

        for (int count1=0;count1<length;count1++) {
            c_i = initialdoc.charAt(count1);
            i = (int) ((int) c_i + amplitude * Math.sin((count1 * 2 * Math.PI / period + Math.PI / 4)));
            list.add(i);
        }


        int[] encrypt=new int[list.size()];

        for (int a=0;a<list.size();a++) {
            encrypt[a]=list.get(a);
        }

        return encrypt;
    }

    /**
     * Decrypt a text that has been encrypted using {@code Cryptography#encrypt}.
     * @param codedText the data to decrypt.
     * @return the decrypted text.
     */
    public static String decrypt(int[] codedText) {
        // TODO: implement this method
        double[] modulus=new double [DFT.dft(codedText).length];
        double largest=0,indexLargest=0;
        int length=codedText.length;
        double period, amplitude;
        int medium;
        char c_i;
        String initial="";

        for (int i=0;i<DFT.dft(codedText).length;i++){
            modulus[i]=Math.sqrt(DFT.dft(codedText)[i].re()*DFT.dft(codedText)[i].re()+DFT.dft(codedText)[i].im()*DFT.dft(codedText)[i].im());
        }

        for (int i = 1; i < modulus.length; i++) {
            if (largest < modulus[i] ) {
                largest = modulus[i];
                indexLargest=i;
            }
        }

        period=Math.ceil(length/indexLargest);
        amplitude=2*largest/length;

        if (amplitude<120){
            amplitude = 64;
        }
        else if (amplitude <240){
            amplitude = 128;
        }else if (amplitude<500){
            amplitude=256;
        }else{
            amplitude=512;
        }

        for (int i=0; i< codedText.length; i++){
            double a =  Math.sin((i * 2 * Math.PI / period + Math.PI / 4));
            if (codedText[i]>=0) {
                medium = (int) Math.ceil(codedText[i] - amplitude * Math.sin((i * 2 * Math.PI / period + Math.PI / 4)));
            }
            else{
                medium = (int) Math.floor(codedText[i] - amplitude * Math.sin((i * 2 * Math.PI / period + Math.PI / 4)));
            }
            c_i=(char) medium;
            initial=initial+c_i;
        }

        return initial;
    }
}
import java.util.*;
import java.io.*;
public class Main {
  //methods
    public static boolean guessWord(String control, String guessed){
      //returns whether the string is guessed correctly or not
      return control.equals(guessed);
    }
    public static boolean guessletter(String control, String guessed){
      //returns whether the letter guessed is in the word or not
      if(control.indexOf(guessed) != -1){ //sees if the guess has an index to indicate that its there
        return true;
      }
      return false;
    }
    public static boolean guessedAlready(ArrayList<String> list, String guess){
      //checks if the letter has been guessed yet or not; the letter will be taken as a string instead of a char
      for(int i = 0; i < list.size(); i++){//traverses through the list
        if(list.get(i).equals(guess))
          return true;//cuts the loop off because it doesn't need to keep going once the letter is found
      }
      return false;
    }
    public static String draw(int count){
      //prints a depiction of the status of hangman at the time of where there is 'count' attempts left
      if(count == 1)
        return "|Face|" + "\n*.*";
      if(count == 2)
        return "|Face||Head|" + "\n|*.*|";
      if(count == 3)
        return "|Face||Head||Body|" + "\n|*.*|\n |";
      if(count == 4)
        return "|Face||Head||Right Arm|||Body|" + "\n|*.*|\n /|";
      if(count == 5)
        return "|Face||Head||Right Arm||Left Arm||Body|" + "\n|*.*|\n /||\n";
      if(count == 6)
        return "|Face||Head||Right Arm||Left Arm||Body||Right Leg|" + "\n|*.*|\n /||\n  /";
      if(count == 7)
        return "|Face||Head||Right Arm||Left Arm||Body||Right Leg||Left Leg|" + "\n|*.*|\n /||\n  /\\";
      return "Dead";
    }
    public static String printword(String word, ArrayList<String> guessedletters){
      //prints the word with the guessed letters - uses the full word for the parameter word
      String result = "";
      for(int i = 0; i < word.length(); i++){
        int place = guessedletters.indexOf(word.substring(i, i+1));
        if(place > -1)
          result+=word.substring(i, i+1); //checks if the letter has been used, and if so displays it
        else
          result+=" _ ";//puts a blank where a letter has not been guessed
      }
      return result;
      
    }
  //for testing and debugging purposes, the test.txt file is used, but the user may import their own file to use
    public static void main(String[] args) 
    throws IOException { //also borrowed (see below for link)
      Scanner gnab = new Scanner(System.in);
      System.out.println("Import your file into juicemind and enter its exact name (ex- filename.txt). Make sure its components are in lowercase for the best gameplay.");//to take a file given by the user
      FileReader fr = new FileReader(gnab.nextLine()); //all scanner parts borrowed from geeksforgeeks link: https://www.geeksforgeeks.org/java-io-bufferedreader-class-java/
      BufferedReader br = new BufferedReader(fr); 
      String str; //string to help read the file into the arraylist
      ArrayList <String> wordlist = new ArrayList<String>();
      while ((str = br.readLine()) != null){
          	wordlist.add(str); //transcribes the .txt file into an arraylist
      }
      int counter = 0; //counts every single round
      int score = 0; //only counts rounds that are correct
      //A loop to go through the entire list and stop when prompted
      for(int i = 0; i < wordlist.size(); i++){
        ArrayList<String> lettersdone = new ArrayList<String>(); //a list of the letters guessed correctly
        ArrayList<String> allguessed = new ArrayList<String>(); //a list of all the letters guessed
        System.out.println(draw(7)); //draws the base case in a sense
        System.out.println(printword(wordlist.get(i), lettersdone));
        System.out.println("Guess a letter:");
        for(int j = 7; j > 0; j--){ //a loop with 7 tries
          String guess = gnab.nextLine();//takes the guess and uses it where needed in this loop
          if(guessedAlready(allguessed, guess) == true){ //checks for previous guesses regardless of whether they were correct or incorrect
            System.out.println("Already Guessed.");
          }
          else{
          if(guessletter(wordlist.get(i), guess) == true){ //a loop for if the guess is correct
            lettersdone.add(guess);//adds it to the list with the correct letters
            allguessed.add(guess);//adds it to the overall list
            System.out.println("That was correct!");
            System.out.println(draw(j));//draws the hangman depiction at j tries
            j++; //makes sure that a try isn't taken off when it is correct by neutralizing the j--
            System.out.println("");
            System.out.println(printword(wordlist.get(i), lettersdone));//prints the word after adding the guess
            if(printword(wordlist.get(i), lettersdone).indexOf("_") == -1){//checks for if there is no more spaces
              j = 0;//terminates the loop
              score++;//adds to the score
            } 
          }
          else{//a loop for if the guess is incorrect
            System.out.println("That was incorrect.");
            allguessed.add(guess);//adds the word to the list with all the guesses
            System.out.println(draw(j-1)); //draws the hangman depiction at j-1 tries so that the less try they get is displayed becuase the for loop had not gone through yet
            System.out.println("");
            System.out.println(printword(wordlist.get(i), lettersdone));//prints the word
          }
          }
          System.out.println("Guess the whole word? yes or no");
          if(gnab.nextLine().equals("yes")){
            System.out.println("Go ahead:");
            if(guessWord(wordlist.get(i), gnab.nextLine()) == true){
              System.out.println("Correct!");
              j = 0;//terminates the loop
              score++;//adds to the score
            }
            else
              System.out.println("Incorrect guess.");
          }
          else
            System.out.println("Maybe next time.");
          System.out.println("Letters guessed: ");//prints the letters guessed
          for(int k = 0; k < allguessed.size(); k++){ //a for loop to do so ^
              System.out.print(allguessed.get(k) + " ");
            }
          System.out.println("");
          System.out.println("What's your next guess?");//asks the next guess
        }
        System.out.println("The word was " + wordlist.get(i)); //prints out the word in case the user got it wrong
        counter++;//adds one to the number of rounds played
        System.out.println("Play again? yes or no");//asks whether the player wants to play again or not.
        if(gnab.nextLine().equals("no"))
          i = wordlist.size();//terminates the loop
        else
          System.out.println("Next Word!"); //even if the player wants to play again, if the list's end is reached the loop terminates
      }
      System.out.println("You got " + score + " out of " + counter + "!");//prints their score
      System.out.println("That's it! See you later!");
      
    }
    
  }

package Game.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuestionCardModel {

    private List<String> words = new ArrayList<>();
    private List<Integer> newIDs = new ArrayList<>();
    private int correct;

    public QuestionCardModel() {
//        this.words = Arrays.asList("Prince Harry", "Led Zepplin", "Nelson Mandela", "Independence Day", "The Eifel Tower");
        this.words = new ArrayList<>();
        this.correct = 0;
    }

    public void resetCounters(){
        correct=0;
    }

    public List<String> getWords() {
        words = WordDataSource.getInstance().queryWords(newIDs);
        return words;
    }

    public void getRandomIds(List<Integer> availableIDs){
        newIDs.clear();
        Random rand = new Random();
        int index;
        while (newIDs.size()<5){
            index = rand.nextInt(availableIDs.size());
            newIDs.add(availableIDs.get(index));
            availableIDs.remove(index);
            }
        }


    public void setWords(List<String> words) {
        this.words = words;
    }

    public void correct(){
        this.correct++;
    }

    public void incorrect(){
        this.correct--;
    }

    public int getCorrect() {
        return correct;
    }
}

package Game.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionCardModel {

    private List<String> words = new ArrayList<>();
    private int correct;

    public QuestionCardModel() {
        this.words = Arrays.asList("Prince Harry", "Led Zepplin", "Nelson Mandela", "Independence Day", "The Eifel Tower");
        this.correct = 0;
    }

    public void resetCounters(){
        correct=0;
    }

    public List<String> getWords() {
        return words;
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

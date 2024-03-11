import org.apache.commons.lang3.time.StopWatch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SemanticMain {
    public List<String> listVocabulary = new ArrayList<>();  //List that contains all the vocabularies loaded from the csv file.
    public List<double[]> listVectors = new ArrayList<>(); //Associated vectors from the csv file.
    public List<Glove> listGlove = new ArrayList<>();
    public final List<String> STOPWORDS;

    private Vector v1;
    private Vector _firISRefVec, _firTORefVec, _secISRefVec;

    public SemanticMain() throws IOException {
        STOPWORDS = Toolkit.loadStopWords();
        Toolkit.loadGLOVE();
    }


    public static void main(String[] args) throws IOException {
        StopWatch mySW = new StopWatch();
        mySW.start();
        SemanticMain mySM = new SemanticMain();
        mySM.listVocabulary = Toolkit.getListVocabulary();
        mySM.listVectors = Toolkit.getlistVectors();
        mySM.listGlove = mySM.CreateGloveList();

        List<CosSimilarityPair> listWN = mySM.WordsNearest("computer");
        Toolkit.PrintSemantic(listWN, 5);

        listWN = mySM.WordsNearest("phd");
        Toolkit.PrintSemantic(listWN, 5);

        List<CosSimilarityPair> listLA = mySM.LogicalAnalogies("china", "uk", "london", 5);
        Toolkit.PrintSemantic("china", "uk", "london", listLA);

        listLA = mySM.LogicalAnalogies("woman", "man", "king", 5);
        Toolkit.PrintSemantic("woman", "man", "king", listLA);

        listLA = mySM.LogicalAnalogies("banana", "apple", "red", 3);
        Toolkit.PrintSemantic("banana", "apple", "red", listLA);
        mySW.stop();

        if (mySW.getTime() > 2000)
            System.out.println("It takes too long to execute your code!\nIt should take less than 2 second to run.");
        else
            System.out.println("Well done!\nElapsed time in milliseconds: " + mySW.getTime());
    }

    public List<Glove> CreateGloveList() throws IOException {
        List<Glove> listResult = new ArrayList<>();
        Vector v;
        //TODO Task 6.1
        Boolean sameword = false;
        for(int i=0; i<listVocabulary.size(); i++){

            for(int j=0; j<STOPWORDS.size(); j++){
                sameword = false;
                if(listVocabulary.get(i).equals(STOPWORDS.get(j))){
                    sameword = true;
                    break;
                }
            }
            if(sameword==false) {
                v = new Vector(Toolkit.getlistVectors().get(i));
                listResult.add(new Glove(Toolkit.getListVocabulary().get(i), v));
            }

        }


        //Method-2
        //Collection<>


        return listResult;
    }

    public List<CosSimilarityPair> WordsNearest(String _word) {
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<>();
        //TODO Task 6.2

        Boolean found = false;
        int pos;
        for (pos = 0; pos < listGlove.size(); pos++) {
            if (_word.equals(listGlove.get(pos).getVocabulary())) {
                Vector v_temp = listGlove.get(pos).getVector();
                v1 = v_temp;
                found = true;
            }
        }
        if (found == true) {

            for (int i = 0; i < listGlove.size(); i++) {
                Vector u = listGlove.get(i).getVector();
                if (u.equals(v1) != true) {
                    double cs = v1.cosineSimilarity(u);
                    CosSimilarityPair csp = new CosSimilarityPair(_word, listGlove.get(i).getVocabulary(),  cs);
                    listCosineSimilarity.add(csp);
                } else {
                    continue;
                }
            }
            HeapSort.doHeapSort(listCosineSimilarity);

        }
        else {
            String defaultword = "error";
            int defpos = 0;
            for (int i = 0; i < listGlove.size(); i++) {
                if (defaultword.equals(listGlove.get(i).getVocabulary())) {
                    v1 = listGlove.get(i).getVector();
                    defpos = i;
                }
            }


            Vector v1 = listGlove.get(defpos).getVector();

            for (int i = 0; i < listGlove.size(); i++) {
                Vector u = listGlove.get(i).getVector();
                if (u.equals(v1) != true) {
                    double cs = v1.cosineSimilarity(u);
                    CosSimilarityPair csp = new CosSimilarityPair(_word, listGlove.get(i).getVocabulary(),  cs);
                    listCosineSimilarity.add(csp);
                } else {
                    continue;
                }
            }
            HeapSort.doHeapSort(listCosineSimilarity);
        }

        return listCosineSimilarity;
    }
    public List<CosSimilarityPair> WordsNearest(Vector _vector) {
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<>();
        //TODO Task 6.3

        //check if the vector exists
        String _word="";

        Boolean found = false;
        for(int i=0; i<listGlove.size(); i++){
            if(listGlove.get(i).getVector().equals(_vector)){
                _word = listGlove.get(i).getVocabulary();
                found = true;
            }
        }

        if (found == true) {

            for (int i = 0; i < listGlove.size(); i++) {
                Vector u = listGlove.get(i).getVector();
                if (u.equals(_vector) != true) {
                    double cs = _vector.cosineSimilarity(u);
                    CosSimilarityPair csp = new CosSimilarityPair(_word, listGlove.get(i).getVocabulary(),  cs);
                    listCosineSimilarity.add(csp);
                } else {
                    continue;
                }
            }
            HeapSort.doHeapSort(listCosineSimilarity);

        }
        else {

            for (int i = 0; i < listGlove.size(); i++) {
                Vector u = listGlove.get(i).getVector();
                if (u.equals(_vector) != true) {
                    double cs = _vector.cosineSimilarity(u);
                    CosSimilarityPair csp = new CosSimilarityPair(_vector, listGlove.get(i).getVocabulary(),  cs);
                    listCosineSimilarity.add(csp);
                } else {
                    continue;
                }
            }
            HeapSort.doHeapSort(listCosineSimilarity);

        }
        return listCosineSimilarity;
    }

    /**
     * Method to calculate the logical analogies by using references.
     * <p>
     * Example: uk is to london as china is to XXXX.
     *       _firISRef  _firTORef _secISRef
     * In the above example, "uk" is the first IS reference; "london" is the first TO reference
     * and "china" is the second IS reference. Moreover, "XXXX" is the vocabulary(ies) we'd like
     * to get from this method.
     * <p>
     * If _top <= 0, then returns an empty listResult.
     * If the vocabulary list does not include _secISRef or _firISRef or _firTORef, then returns an empty listResult.
     *
     * @param _secISRef The second IS reference
     * @param _firISRef The first IS reference
     * @param _firTORef The first TO reference
     * @param _top      How many vocabularies to include.
     */
    public List<CosSimilarityPair> LogicalAnalogies(String _secISRef, String _firISRef, String _firTORef, int _top) {
        List<CosSimilarityPair> listResult = new ArrayList<>();
        List<CosSimilarityPair> listCosineSimilarity = new ArrayList<>();
        //TODO Task 6.4
        Vector Vnew;
        Boolean secISRefcheck = false;
        Boolean firISRefcheck = false;
        Boolean firTORefcheck = false;

        for (int i = 0; i < listGlove.size(); i++) {
            if (_firISRef.equals(listGlove.get(i).getVocabulary())) {
                _firISRefVec = listGlove.get(i).getVector();
                firISRefcheck = true;
            }

            if (_firTORef.equals(listGlove.get(i).getVocabulary())) {
                _firTORefVec = listGlove.get(i).getVector();
                firTORefcheck = true;
            }

            if (_secISRef.equals(listGlove.get(i).getVocabulary())) {
                _secISRefVec = listGlove.get(i).getVector();
                secISRefcheck = true;
            }

        }

        if(firISRefcheck && firTORefcheck && secISRefcheck) {
            Vector Vtemp = _secISRefVec.subtraction(_firISRefVec);
            Vnew = Vtemp.add(_firTORefVec);

            for (int i = 0; i < listGlove.size(); i++) {
                Vector u = listGlove.get(i).getVector();
                //check if the listGlove object is is_secISRef or _firISRef or _firTORef
                if (u.equals(_firISRefVec) || u.equals(_firTORefVec) || u.equals(_secISRefVec)) {
                    continue;
                } else {
                    if (u.equals(Vnew) != true) {
                        double cs = Vnew.cosineSimilarity(u);
                        CosSimilarityPair csp = new CosSimilarityPair(Vnew, listGlove.get(i).getVocabulary(), cs);
                        listCosineSimilarity.add(csp);
                    } else {
                        continue;
                    }
                }
            }

            HeapSort.doHeapSort(listCosineSimilarity);

            for (int i = 0; i < _top; i++) {
                listResult.add(listCosineSimilarity.get(i));
            }
        }
        else{
        //String words don't exists --> do nothing
        }
        return listResult;
    }

}
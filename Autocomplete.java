import java.io.*;
import java.util.*;

public class Autocomplete implements IAutocomplete {
    private Node root;
    private int k;

    public Autocomplete() {
        root = new Node("", 0);
    }
    @Override
    public void addWord(String word, long weight) {
        word = word.toLowerCase();
        for (char ch: word.toCharArray()) {
            if (!Character.isLetter(ch)) {
                return;
            }
        }
        Node curNode = root;
        for (int i = 0; i < word.length(); i++) {
            char curChar = word.charAt(i);
            int index = curChar - 'a';
            curNode.setPrefixes(curNode.getPrefixes() + 1);
            if (curNode.getReferences()[index] == null) {
                curNode.getReferences()[index] = new Node();
            }
            curNode = curNode.getReferences()[index];
        }
        curNode.setWords(1);
        curNode.setTerm(new Term(word, weight));
        curNode.setPrefixes(curNode.getPrefixes() + 1);
    }

    @Override
    public Node buildTrie(String filename, int k) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] parts = line.trim().split("\t");
                if (parts.length == 2) {
                    String word = parts[1];
                    long weight = Long.parseLong(parts[0]);
                    addWord(word, weight);
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    @Override
    public Node getSubTrie(String prefix) {
        if (prefix == null) {
            return null;
        }
        prefix = prefix.toLowerCase();

        Node curNode = root; // Start from the root of the trie.
        for (char ch : prefix.toCharArray()) {
            if (!Character.isLetter(ch)) {
                return null;
            }
            int index = ch - 'a'; // Calculate the index corresponding to the character.
            Node nextNode = curNode.getReferences()[index];
            // Attempt to get the next node from the current node's references.
            if (nextNode != null) {
                curNode = nextNode;
            } else {
                return null;
            }
        }
        return curNode; // Return the node corresponding to the last character of the prefix
    }

    @Override
    public int countPrefixes(String prefix) {
        Node node = getSubTrie(prefix);
        if (node != null) {
            return node.getPrefixes();
        } else {
            return 0;
        }
    }

    @Override
    public List<ITerm> getSuggestions(String prefix) {
        List<ITerm> suggestions = new ArrayList<>();
        Node startNode = getSubTrie(prefix); // Locate the starting node for the prefix
        if (startNode == null) {
            return suggestions; // If there's no such prefix, return an empty list
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(startNode);
        while (!queue.isEmpty()) {
            Node pulledOut = queue.poll();
            if (pulledOut.getWords() != 0) {
                suggestions.add(new Term(pulledOut.getTerm().getTerm(),
                        pulledOut.getTerm().getWeight()));
            }
            for (Node child: pulledOut.getReferences()) {
                if (child != null) {
                    queue.offer(child);
                }
            }
        }
        suggestions.sort(ITerm.byReverseWeightOrder());
        return suggestions;
    }
}

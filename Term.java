public class Term implements ITerm {
    private String term;
    private long weight;

    /**
     * Initialize a Term with a given query String and weight
     */
    public Term(String term, long weight) {
        if (weight < 0 || term == null) {
            throw new IllegalArgumentException("Your term is null or Your weight should be >= 0");
        }
        this.term = term;
        this.weight = weight;

    }

    @Override
    public int compareTo(ITerm that) {
        return this.getTerm().compareTo(that.getTerm());
    }

    @Override
    public String toString() {
        return this.weight + "\t" + this.term;
    }

    @Override
    public long getWeight() {
        return this.weight;
    }

    @Override
    public String getTerm() {
        return this.term;
    }

    @Override
    public void setWeight(long weight) {
        this.weight = weight;

    }

    @Override
    public String setTerm(String term) {
        return this.term = term;
    }
}

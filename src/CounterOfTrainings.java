public class CounterOfTrainings {
    private Coach coach;
    private int count;

    public CounterOfTrainings(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}

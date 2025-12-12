public class TrainingSession implements Comparable<TrainingSession> {

    private Group group;
    private Coach coach;
    private DayOfWeek dayOfWeek;
    private TimeOfDay timeOfDay;

    public TrainingSession(Group group, Coach coach, DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        this.group = group;
        this.coach = coach;
        this.dayOfWeek = dayOfWeek;

        if (timeOfDay.getHours() < 0 || timeOfDay.getHours() > 23) {
            System.out.println("Часы должны быть в диапазоне от 0 до 23");
        }
        if (timeOfDay.getMinutes() < 0 || timeOfDay.getMinutes() > 59) {
            System.out.println("Минуты должны быть в диапазоне от 0 до 59");
        }

        this.timeOfDay = timeOfDay;
    }


    public Group getGroup() {
        return group;
    }

    public Coach getCoach() {
        return coach;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    @Override
    public int compareTo(TrainingSession other) {

        int dayComparison = this.dayOfWeek.compareTo(other.dayOfWeek);
        if (dayComparison != 0) {
            return dayComparison;
        }

        return this.timeOfDay.compareTo(other.timeOfDay);
    }
}


import java.util.Objects;

public class TimeOfDay implements Comparable<TimeOfDay> {

    public int getHours() {
        return hours;
    }

    //часы (от 0 до 23)
    private int hours;

    public int getMinutes() {
        return minutes;
    }

    //минуты (от 0 до 59)
    private int minutes;

    public TimeOfDay(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    @Override
    public int compareTo(TimeOfDay other) {
        int hourComparison = Integer.compare(this.hours, other.hours);
        if (hourComparison != 0) {
            return hourComparison;
        }
        return Integer.compare(this.minutes, other.minutes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeOfDay timeOfDay = (TimeOfDay) o;
        return hours == timeOfDay.hours && minutes == timeOfDay.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }
}

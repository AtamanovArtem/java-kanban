import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        if (!timetable.containsKey(dayOfWeek)) {
            timetable.put(dayOfWeek, new TreeMap<>());
        }

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);

        if (sessionsForDay.containsKey(timeOfDay)) {
            sessionsForDay.get(timeOfDay).add(trainingSession);
        } else {
            ArrayList<TrainingSession> sessions = new ArrayList<>();
            sessions.add(trainingSession);
            sessionsForDay.put(timeOfDay, sessions);
        }
    }


    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (timetable.containsKey(dayOfWeek)) {
            return timetable.get(dayOfWeek);
        } else {
            return new TreeMap<>();
        }
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (timetable.containsKey(dayOfWeek)) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingSessions = timetable.get(dayOfWeek);
            ArrayList<TrainingSession> result = new ArrayList<>();
                if (trainingSessions.containsKey(timeOfDay)) {
                    result.addAll(trainingSessions.get(timeOfDay));
                }
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    public ArrayList<CounterOfTrainings> getCountByCoaches(ArrayList<TrainingSession> trainingSessions) {
        Map<Coach, Integer> coachCount = new HashMap<>();
        for (TrainingSession session : trainingSessions) {
            Coach coach = session.getCoach();
            coachCount.put(coach, coachCount.getOrDefault(coach, 0) + 1);
        }
        ArrayList<CounterOfTrainings> counterOfTrainingsList = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachCount.entrySet()) {
            CounterOfTrainings counter = new CounterOfTrainings(entry.getKey(), entry.getValue());
            counterOfTrainingsList.add(counter);
        }
        counterOfTrainingsList.sort(Comparator.comparingInt(CounterOfTrainings::getCount).reversed());
        return counterOfTrainingsList;
    }
}

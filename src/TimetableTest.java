import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.TreeMap;
import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {
    Timetable timetable = new Timetable();

    @Test
    void testGetTrainingSessionsForDaySingleSession() {

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());
        //Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySessions.size()); // Проверяем, что занятий два

        ArrayList<TrainingSession> sessionsAt13 = thursdaySessions.get(new TimeOfDay(13, 0));
        ArrayList<TrainingSession> sessionsAt20 = thursdaySessions.get(new TimeOfDay(20, 0));

        assertNotNull(sessionsAt13); // Убеждаемся, что занятия в 13:00 есть
        assertNotNull(sessionsAt20); // Убеждаемся, что занятия в 20:00 есть

        // Проверяем порядок времени в TreeMap

        assertTrue(thursdaySessions.firstKey().equals(new TimeOfDay(13, 0)));
        assertTrue(thursdaySessions.lastKey().equals(new TimeOfDay(20, 0)));

        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        ArrayList<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));
        assertEquals(1, mondaySessions.size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        ArrayList<TrainingSession> noSessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0));
        assertTrue(noSessions.isEmpty());
    }

    @Test
    void testGetNoneTrainingSessionsForDay() {

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(0, mondaySessions.size());
    }

    @Test
    void testGetTrainingSessionsForOneDayAndOneTime() {

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession1 = new TrainingSession(group1, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(singleTrainingSession1);

        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach coach2 = new Coach("Петров", "Сергей", "Иванович");
        TrainingSession singleTrainingSession2 = new TrainingSession(group2, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(singleTrainingSession2);
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY);
        ArrayList<TrainingSession> sessionsAt15 = mondaySessions.get(new TimeOfDay(15, 0));
        assertEquals(2, sessionsAt15.size());
    }

    @Test
    void testGetCountBy3Coaches() {

        ArrayList<TrainingSession> trainingSessions = new ArrayList<>();

        trainingSessions.add(new TrainingSession(new Group("Акробатика для взрослых", Age.ADULT, 90),
                new Coach("Петров", "Сергей", "Сергеевич"), DayOfWeek.MONDAY,
                new TimeOfDay(13,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для взрослых", Age.ADULT, 90),
                new Coach("Петров", "Сергей", "Сергеевич"), DayOfWeek.TUESDAY,
                new TimeOfDay(13,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для взрослых", Age.ADULT, 90),
                new Coach("Петров", "Сергей", "Сергеевич"), DayOfWeek.SATURDAY,
                new TimeOfDay(13,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для детей", Age.CHILD, 60),
                new Coach("Сергеев", "Анатолий", "Павлович"), DayOfWeek.THURSDAY,
                new TimeOfDay(10,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для детей", Age.CHILD, 60),
                new Coach("Сергеев", "Анатолий", "Павлович"), DayOfWeek.WEDNESDAY,
                new TimeOfDay(10,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для взрослых", Age.ADULT, 75),
                new Coach("Исмайлов", "Владимир", "Владимирович"), DayOfWeek.TUESDAY,
                new TimeOfDay(15,0)));

        ArrayList<CounterOfTrainings> counterOfTrainingsList = timetable.getCountByCoaches(trainingSessions);

        assertEquals(3, counterOfTrainingsList.get(0).getCount()); // coach1 должен иметь 3 тренировки
        assertEquals(2, counterOfTrainingsList.get(1).getCount()); // coach2 должен иметь 2 тренировки
        assertEquals(1, counterOfTrainingsList.get(2).getCount()); // coach2 должен иметь 1 тренировку
    }

    @Test
    void testGetCountByCoachesSort() {

        ArrayList<TrainingSession> trainingSessions = new ArrayList<>();

        trainingSessions.add(new TrainingSession(new Group("Акробатика для взрослых", Age.ADULT, 90),
                new Coach("Петров", "Сергей", "Сергеевич"), DayOfWeek.MONDAY,
                new TimeOfDay(13,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для взрослых", Age.ADULT, 90),
                new Coach("Петров", "Сергей", "Сергеевич"), DayOfWeek.TUESDAY,
                new TimeOfDay(13,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для взрослых", Age.ADULT, 90),
                new Coach("Петров", "Сергей", "Сергеевич"), DayOfWeek.SATURDAY,
                new TimeOfDay(13,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для детей", Age.CHILD, 60),
                new Coach("Сергеев", "Анатолий", "Павлович"), DayOfWeek.THURSDAY,
                new TimeOfDay(10,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для детей", Age.CHILD, 60),
                new Coach("Сергеев", "Анатолий", "Павлович"), DayOfWeek.WEDNESDAY,
                new TimeOfDay(10,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для взрослых", Age.ADULT, 75),
                new Coach("Исмайлов", "Владимир", "Владимирович"), DayOfWeek.TUESDAY,
                new TimeOfDay(15,0)));

        ArrayList<CounterOfTrainings> counterOfTrainingsList = timetable.getCountByCoaches(trainingSessions);
        assertTrue(counterOfTrainingsList.get(0).getCount() >= counterOfTrainingsList.get(2).getCount());
    }

    @Test
    void testGetCountByEmptyList() {

        ArrayList<TrainingSession> trainingSessions = new ArrayList<>();

        ArrayList<CounterOfTrainings> counterOfTrainingsList = timetable.getCountByCoaches(trainingSessions);
        assertTrue(counterOfTrainingsList.isEmpty());
    }

    @Test
    void testGetCountByOneCoach(){

        ArrayList<TrainingSession> trainingSessions = new ArrayList<>();
        trainingSessions.add(new TrainingSession(new Group("Акробатика для взрослых", Age.ADULT, 90),
                new Coach("Петров", "Сергей", "Сергеевич"), DayOfWeek.MONDAY,
                new TimeOfDay(8,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для взрослых", Age.ADULT, 90),
                new Coach("Петров", "Сергей", "Сергеевич"), DayOfWeek.MONDAY,
                new TimeOfDay(13,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для взрослых", Age.ADULT, 90),
                new Coach("Петров", "Сергей", "Сергеевич"), DayOfWeek.MONDAY,
                new TimeOfDay(17,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для взрослых", Age.ADULT, 90),
                new Coach("Петров", "Сергей", "Сергеевич"), DayOfWeek.FRIDAY,
                new TimeOfDay(8,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для взрослых", Age.ADULT, 90),
                new Coach("Петров", "Сергей", "Сергеевич"), DayOfWeek.FRIDAY,
                new TimeOfDay(13,0)));
        trainingSessions.add(new TrainingSession(new Group("Акробатика для взрослых", Age.ADULT, 90),
                new Coach("Петров", "Сергей", "Сергеевич"), DayOfWeek.FRIDAY,
                new TimeOfDay(17,0)));

        ArrayList<CounterOfTrainings> counterOfTrainingsList = timetable.getCountByCoaches(trainingSessions);

        assertEquals(6, counterOfTrainingsList.get(0).getCount());
    }
}



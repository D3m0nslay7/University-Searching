package uk.ac.aston.oop.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class DayTest.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DayTest {

	// TODO - add your tests here!

	@Test
	public void appointmentStartOfDay() {
		// Exact day number does not matter
		Day day = new Day(1);
		// Description does not matter
		Appointment app = new Appointment("meeting", 1);

		boolean success = day.makeAppointment(Day.START_OF_DAY, app);
		Appointment fetched = day.getAppointment(Day.START_OF_DAY);

		assertTrue(success, "Making an appointment at the start of a new day should work");
		assertSame(app, fetched, "It should be possible to fetch the appointment we just made");
	}

	@Test
	public void appointmentBeforeStartOfDay() {
		// Exact day number does not matter
		Day day = new Day(1);
		// Description does not matter
		Appointment app = new Appointment("meeting", 1);

		boolean failure = day.makeAppointment(8, app);
		Appointment notFetched = day.getAppointment(8);

		assertFalse(failure, "Making an appointment at the start of a new day should work");
		assertNull(notFetched, "It should be possible to fetch the appointment we just made");

	}

	@Test
	public void twoAppsOnSameTime() {
		// Exact day number does not matter
		Day day = new Day(1);
		// Description does not matter
		Appointment appA = new Appointment("meeting", 1);
		Appointment appB = new Appointment("meeting", 1);

		boolean successA = day.makeAppointment(Day.START_OF_DAY, appA);
		Appointment fetchedA = day.getAppointment(Day.START_OF_DAY);

		boolean successB = day.makeAppointment(Day.START_OF_DAY, appB);
		Appointment fetchedB = day.getAppointment(Day.START_OF_DAY);

		assertTrue(successA, "Making an appointment at the start of a new day should work");
		assertSame(appA, fetchedA, "It should be possible to fetch the appointment we just made");

		assertFalse(successB, "Appointment should be false becaues we have another one here");
		assertSame(appA, fetchedB, "We should get appA because we never overwrite the next one");
	}

	@Test
	public void twoHourAppointmentAtStart() {
		// Exact day number does not matter
		Day day = new Day(1);
		// Description does not matter
		Appointment app = new Appointment("meeting", 2);

		boolean success = day.makeAppointment(13, app);
		Appointment fetchedA = day.getAppointment(13);
		Appointment fetchedB = day.getAppointment(14);

		assertTrue(success, "Making a 2 hour appointment at the start of a new day should work ");

		assertSame(app, fetchedA, "It should be possible to fetch the appointment we just made for 13");
		assertSame(app, fetchedB,
				"It should be possible to fetch the appointment we just made for 14 since its 2 hours");
	}

	@Test
	public void twoHourAppointmentBeyondEnd() {
		// Exact day number does not matter
		Day day = new Day(1);
		// Description does not matter
		Appointment app = new Appointment("meeting", 2);

		boolean success = day.makeAppointment(Day.FINAL_APPOINTMENT_TIME, app);
		Appointment fetchedA = day.getAppointment(Day.FINAL_APPOINTMENT_TIME);
		Appointment fetchedB = day.getAppointment(18);

		assertFalse(success, "Making a 2 hour appointment at the end of the day shouldnt work");

		assertNull(fetchedA, "It shouldnt be possible to fetch the appointment we just made for 17");
		assertNull(fetchedB, "It shouldnt be ");
	}

	@Test
	public void overlappingTwoHourAppointments() {
		// Exact day number does not matter
		Day day = new Day(1);

		// appointment A

		// Description does not matter
		Appointment appA = new Appointment("meeting", 2);

		boolean successA = day.makeAppointment(10, appA);
		Appointment fetchedA1 = day.getAppointment(10);
		Appointment fetchedA2 = day.getAppointment(11);

		// appointment B

		// Description does not matter
		Appointment appB = new Appointment("meeting", 1);

		boolean successB = day.makeAppointment(10, appB);
		Appointment fetchedB1 = day.getAppointment(10);
		// Appointment fetchedB2 = day.getAppointment(12);

		// Description does not matter
		Appointment appC = new Appointment("meeting", 2);

		boolean successC = day.makeAppointment(11, appC);
		Appointment fetchedC1 = day.getAppointment(11);
		Appointment fetchedC2 = day.getAppointment(12);
		// Description does not matter
		Appointment appD = new Appointment("meeting", 2);

		boolean successD = day.makeAppointment(9, appD);
		Appointment fetchedD1 = day.getAppointment(10);
		Appointment fetchedD2 = day.getAppointment(11);
		
		// Description does not matter
		Appointment appE = new Appointment("meeting", 1);

		boolean successE = day.makeAppointment(9, appE);
		Appointment fetchedE1 = day.getAppointment(9);
		
		// results for a
		assertTrue(successA, "Making a 2 hour appointment should work");

		assertSame(appA, fetchedA1, "It should be possible to fetch the appointment we just made for A");
		assertSame(appA, fetchedA2, "It should be possible to fetch the appointment we just made for A 1 hour later");

		// results for b
		assertFalse(successB, "Making a 1 hour appointment overlapping with another shouldnt work");

		assertSame(appA, fetchedB1,
				"It shouldnt be possible to fetch the appointment we just made for B as it overlaps with another, we should get A");
		// assertSame(appA, fetchedB2, "It shouldnt be possible to fetch the appointment
		// we just made for B as it overlaps with another, we should get A");

		// results for b
		assertFalse(successC, "Making a 1 hour appointment overlapping with another shouldnt work");

		assertSame(appA, fetchedC1,
				"It shouldnt be possible to fetch the appointment we just made for C as it overlaps with another, we should get A");
		assertNull(fetchedC2,
				"It shouldnt be possible to fetch the appointment we just made for C as it overlaps with another, we should get null");

		// results for b
		assertFalse(successD, "Making a 1 hour appointment overlapping with another shouldnt work");

		assertSame(appA, fetchedD1,
				"It shouldnt be possible to fetch the appointment we just made for B as it overlaps with another, we should get A");
		assertSame(appA, fetchedD2,
				"It shouldnt be possible to fetch the appointment we just made for B as it overlaps with another, we should get A");
		// results for b
		assertTrue(successE, "Making a 1 hour appointment before with another should work");

		assertSame(appE, fetchedE1,
				"It shouldnt be possible to fetch the appointment we just made for B as it overlaps with another, we should get A");
	
	}

}

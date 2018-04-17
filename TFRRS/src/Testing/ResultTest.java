package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Server.*;
import application.RunningTime;

class ResultTest
{
	Result track;
	Result field;

	@BeforeEach
	void start()
	{
		track = new Result("3000 Steeple", "Rhodes Classic", "Outdoor", new RunningTime("9:49:22"),
				new LocalDate(2016, 3, 15));
		field = new Result("Long Jump", "Anderson", "Indoor", (float) 6.24, new LocalDate(2016, 2, 21));
	}

	@Test
	void testResultStringStringStringFloatLocalDate()
	{
		Result res = new Result("Triple Jump", "Centre Invitational", "Outdoor", (float) 12.88,
				new LocalDate(2015, 4, 2));
		assertNotNull(res);
		assertEquals("Triple Jump", res.getEvent());
		assertEquals("Centre Invitational", res.getMeet());
		assertEquals("Outdoor", res.getInOrOut());
		assertTrue(12.87 < res.getMark() && res.getMark() < 12.89);
		assertEquals("2015-04-02", res.getWhen().toString());
		assertNull(res.getTime());

	}

	@Test
	void testResultStringStringStringRunningTimeLocalDate()
	{
		Result res = new Result("5000 Meters", "Centre Invitational", "Outdoor", new RunningTime("16:38:34"),
				new LocalDate(2015, 4, 2));
		assertNotNull(res);
		assertEquals("5000 Meters", res.getEvent());
		assertEquals("Centre Invitational", res.getMeet());
		assertEquals("Outdoor", res.getInOrOut());
		assertEquals("16:38.34", res.getTime().toString());
		assertEquals("2015-04-02", res.getWhen().toString());
		assertEquals(0, res.getMark());
	}

	@Test
	void testGetMeetAndSetMeet()
	{
		assertEquals("Rhodes Classic", track.getMeet());
		assertEquals("Anderson", field.getMeet());
		field.setMeet("Depauw");
		track.setMeet("Centre Invite");
		assertEquals("Centre Invite", track.getMeet());
		assertEquals("Depauw", field.getMeet());

	}

	@Test
	void testGetMarkAndSetMark()
	{
		assertTrue(6.23 < field.getMark() && 6.25 > field.getMark());
		assertTrue(-1 < track.getMark() && 1 > track.getMark());
		field.setMark((float) 6.25);
		track.setMark((float) 3.3);
		assertTrue(field.getMark() < 6.26 && field.getMark() > 6.24);
		assertTrue(track.getMark() < 3.31 && track.getMark() > 3.29);

	}

	@Test
	void testGetAndSetInOrOut()
	{
		assertEquals("Indoor", field.getInOrOut());
		assertEquals("Outdoor", track.getInOrOut());
		field.setInOrOut("indoor");
		track.setInOrOut("outdoor");
		assertEquals("indoor", field.getInOrOut());
		assertEquals("outdoor", track.getInOrOut());
	}

	@Test
	void testGetEventAndSetEvent()
	{
		assertEquals("Long Jump", field.getEvent());
		assertEquals("3000 Steeple", track.getEvent());
		field.setEvent("long jump");
		track.setEvent("steeple");
		assertEquals("long jump", field.getEvent());
		assertEquals("steeple", track.getEvent());
	}

	@Test
	void testGetTimeAndSetTime()
	{
		assertEquals(null, field.getTime());
		assertEquals("09:49.22", track.getTime().toString());
		field.setTime(new RunningTime("0:0"));
		track.setTime(new RunningTime("9:49:20"));
		assertEquals("Did not finish", field.getTime().toString());
		assertEquals("09:49.2", track.getTime().toString());
	}

	@Test
	void testGetWhenAndSetWhen()
	{
		assertEquals("2016-02-21", field.getWhen().toString());
		assertEquals("2016-03-15", track.getWhen().toString());
		field.setWhen(new LocalDate(2016, 2, 22));
		track.setWhen(new LocalDate(2016, 3, 16));
		assertEquals("2016-02-22", field.getWhen().toString());
		assertEquals("2016-03-16", track.getWhen().toString());
	}

}

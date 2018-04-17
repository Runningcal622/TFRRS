package Testing;
import application.*;
import Server.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class ToWebTest
{
	Tfrrs t;
	@Before
	void start()
	{
		t = ToWeb.deceral();
	}

	@Test
	void testTeamCreation()
	{
		fail("Not yet implemented");
	}

	@Test
	void testGetAthletes()
	{
		fail("Not yet implemented");
	}

	@Test
	void testFind_Team_Male()
	{
		fail("Not yet implemented");
	}

	@Test
	void testFind_Team_Female()
	{
		assertEquals("centreurl",ToWeb.find_Team_Female("centre", t.femaleTeams));
		assertEquals(null,ToWeb.find_Team_Female("anskn", t.femaleTeams));
	}

}

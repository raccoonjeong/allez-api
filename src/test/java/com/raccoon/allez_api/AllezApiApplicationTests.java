package com.raccoon.allez_api;

import com.raccoon.allez_api.entity.Match;
import com.raccoon.allez_api.repository.MatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AllezApiApplicationTests {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private MatchRepository matchRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void givenDatasourceAvailableWhenAccessDetailsThenExpectDetails() throws SQLException {
		assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
		assertThat(dataSource.getConnection().getMetaData().getDatabaseProductName()).isEqualTo("H2");
	}

	@Test
	public void whenCountAllTeamsThenExpectTwentyFiveTeams() throws SQLException {
		ResultSet rs = null;
		int noOfTeams = 0;

		try (PreparedStatement ps = dataSource.getConnection().prepareStatement("select count(1) from teams")) {
			rs = ps.executeQuery();
			while(rs.next()) {
				noOfTeams = rs.getInt(1);
			}
			assertThat(noOfTeams).isEqualTo(25L);
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	@Test
	public void givenReadMatchWhenLoadTheMatchThenExpectMatch() throws Exception{


		Optional<Match> optionalMatch = matchRepository.findById(1L);
		assertThat(optionalMatch).isPresent();
		Match match = optionalMatch.get();

		LocalDate expectedDate = LocalDate.of(2009, 3, 8);

		assertThat(match.getMatchDate()).isEqualTo(expectedDate);
	}

	@Test
	public void findMatchByTeamIdAndSeasonId() throws Exception{

		List<Match> matchesByTeamIdAndSeasonId = matchRepository.findMatchesByTeamIdAndSeasonId(8L, 42L);

		assertThat(matchesByTeamIdAndSeasonId).isNotNull();
		assertThat(matchesByTeamIdAndSeasonId.size()).isEqualTo(38);

	}
}

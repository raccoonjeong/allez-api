package com.raccoon.allez_api.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.raccoon.allez_api.dto.MatchupSummaryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class MatchRepositoryImplTest {

    private JPAQueryFactory queryFactory;
    private MatchRepositoryImpl matchRepository;

    @BeforeEach
    void setUp() {
        queryFactory = mock(JPAQueryFactory.class, RETURNS_DEEP_STUBS);
        matchRepository = new MatchRepositoryImpl(queryFactory);
    }

    @Test
    void getMatchupSummary_returnsSummary() {
        // given
        String team1 = "서울";
        String team2 = "광주";
        MatchupSummaryDTO expected = new MatchupSummaryDTO(1L, "", 2L, "", 10, 5, 2, 3, 15, 8);

//        when(queryFactory
//                .select(any())
//                .from(any())
//                .join(any(), any())
//                .join(any(), any())
//                .where(any(), any())
//                .fetchOne()
//        ).thenReturn(expected);

        // when
        MatchupSummaryDTO result = matchRepository.getMatchupSummary(team1, team2);

        // then
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
}
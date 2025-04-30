package com.raccoon.allez_api.repository;

import com.raccoon.allez_api.dto.MatchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchRepositoryCustom {
    Page<MatchDTO> searchMatches(Long teamId, Integer seasonYear, Pageable pageble);
}

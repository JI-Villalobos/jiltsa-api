package com.jiltsa.admin.common;

import com.jiltsa.admin.branch.domain.Profile;
import com.jiltsa.admin.branch.persistence.entity.BranchConfiguration;
import com.jiltsa.admin.branch.persistence.repository.BranchConfigurationRepository;
import com.jiltsa.admin.cashproof.domain.CheckType;
import com.jiltsa.admin.cashproof.persistence.entity.CheckList;
import com.jiltsa.admin.cashproof.persistence.repository.CheckListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * V3 converts the ordinal-stored enums to names. The test seed (V1.1) inserts rows
 * with the old ordinals before V3 runs, so reading them back proves the conversion.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class EnumStorageMigrationTest {
    @Autowired private BranchConfigurationRepository branchConfigurations;
    @Autowired private CheckListRepository checkLists;
    @Autowired private JdbcTemplate jdbc;

    @Test
    void ordinalRowsWereConvertedToNames() {
        assertThat(jdbc.queryForObject("select profile from branch_config where id = 1", String.class)).isEqualTo("NURSERY");
        assertThat(jdbc.queryForObject("select check_type from check_list where id = 1000", String.class)).isEqualTo("CHECK_OUT");
    }

    @Test
    void entitiesReadAndWriteByName() {
        assertThat(branchConfigurations.findByProfile(Profile.NURSERY)).extracting(BranchConfiguration::getBranchId).contains(3);
        assertThat(checkLists.findById(1000)).map(CheckList::getCheckType).contains(CheckType.CHECK_OUT);

        BranchConfiguration water = branchConfigurations.save(new BranchConfiguration(2, 0.0, false, false, Profile.PURIFIED_WATER, null));
        assertThat(jdbc.queryForObject("select profile from branch_config where id = ?", String.class, water.getId())).isEqualTo("PURIFIED_WATER");
    }
}
